package com.leslienan.wireshark.db

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.leslienan.wireshark.NetLogModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class WiresharkDao private constructor() {

    companion object {
        @Volatile
        private var instance: WiresharkDao? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: WiresharkDao().also { instance = it }
            }
    }

    private object What {
        const val selectAll = 1//查询所有
    }

    private var sqlHelper: SQLHelper? = null
    private var db: SQLiteDatabase? = null
    private var threadPool: ExecutorService? = null
    private var handler: Handler? = null

    private class WiresharkHandler(context: Context) :
        Handler(context.mainLooper) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                What.selectAll -> {
                    (msg.obj as? (list: List<NetLogModel>) -> Unit)?.apply {
                        invoke(
                            msg.data.getParcelableArrayList("netLogList")
                                ?: listOf()
                        )
                    }
                }
            }
        }
    }

    fun init(context: Context) {
        sqlHelper = sqlHelper ?: SQLHelper(context)
        db = db ?: sqlHelper!!.writableDatabase
        handler = handler ?: WiresharkHandler(context)
        threadPool = threadPool ?: Executors.newSingleThreadExecutor()
    }

    fun release() {
        threadPool?.shutdown()
        handler?.removeCallbacksAndMessages(null)
        db?.close()
        sqlHelper?.close()
        threadPool = null
        handler = null
        db = null
        sqlHelper = null
    }

    fun add(netLog: NetLogModel) {
        threadPool?.submit {
            val sql =
                "insert into ${SQLHelper.TABLE_NAME}(${SQLHelper.FIELD_METHOD}," +
                        "${SQLHelper.FIELD_REQUEST},${SQLHelper.FIELD_REQUEST_HEADER},${SQLHelper.FIELD_REQUEST_BODY}," +
                        "${SQLHelper.FIELD_RESPONSE},${SQLHelper.FIELD_RESPONSE_HEADER},${SQLHelper.FIELD_RESPONSE_BODY}," +
                        "${SQLHelper.FIELD_DURATION},${SQLHelper.FIELD_TIME})" +
                        " values(" +
                        "'${netLog.method}','${netLog.request}'," +
                        "'${netLog.requestHeader}'," +
                        "'${netLog.requestBody}'," +
                        "'${netLog.response}'," +
                        "'${netLog.responseHeader}'," +
                        "'${netLog.responseBody}'," +
                        "${netLog.duration}," +
                        "${netLog.time}" +
                        ")"
            try {
                db?.execSQL(sql)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun selectAll(invoke: (list: List<NetLogModel>) -> Unit) {
        threadPool?.submit {
            val cursor = db?.query(
                SQLHelper.TABLE_NAME, SQLHelper.FIELD_LIST,
                null, null, null,
                null, "${SQLHelper.FIELD_ID} desc"
            )
            val netLogList = ArrayList<NetLogModel>()
            cursor?.apply {
                if (moveToFirst()) {
                    do {
                        netLogList.add(
                            NetLogModel(
                                getString(1),
                                getString(2),
                                getString(3),
                                getString(4),
                                getString(5),
                                getString(6),
                                getString(7),
                                getLong(8),
                                getLong(9),
                                getLong(0)
                            )
                        )
                    } while (moveToNext())
                }
                cursor.close()
                handler?.let {
                    val message = it.obtainMessage()
                    message.what = What.selectAll
                    message.obj = invoke
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("netLogList", netLogList)
                    message.data = bundle
                    it.sendMessage(message)
                }
            }
        }
    }

    fun delete(ids: List<Long>) {
        if (ids.isNotEmpty()) {
            threadPool?.submit {
                val sb = StringBuilder()
                ids.forEach {
                    sb.append(it).append(",")
                }
                sb.deleteAt(sb.length - 1)
                val sql =
                    "delete from ${SQLHelper.TABLE_NAME} where ${SQLHelper.FIELD_ID} in($sb)"
                db?.execSQL(sql)
            }
        }
    }
}