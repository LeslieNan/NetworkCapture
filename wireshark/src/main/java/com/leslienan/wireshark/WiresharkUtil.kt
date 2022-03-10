package com.leslienan.wireshark

import android.content.Context
import com.leslienan.wireshark.db.WiresharkDao

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
object WiresharkUtil {

    private var listener: ((model: NetLogModel) -> Unit)? = null
    private var dbDao: WiresharkDao? = null
    var isStart = false

    fun init(context: Context) {
        dbDao = WiresharkDao.getInstance()
        dbDao?.init(context)
    }

    fun setOnAddListener(listener: (model: NetLogModel) -> Unit) {
        this.listener = listener
    }

    fun addNetLog(model: NetLogModel) {
        if (isStart) {
            listener?.invoke(model)
            dbDao?.add(model)
        }
    }

    fun selectAll(invoke: (list: List<NetLogModel>) -> Unit) {
        dbDao?.selectAll {
            invoke.invoke(it)
        }
    }

    fun deleteNetLog(ids: List<Long>) {
        dbDao?.delete(ids)
    }

    fun release() {
        dbDao?.release()
    }

}