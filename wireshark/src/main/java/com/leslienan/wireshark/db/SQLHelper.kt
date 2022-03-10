package com.leslienan.wireshark.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class SQLHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "Wireshark_DB"
        const val DB_VERSION = 1
        const val TABLE_NAME = "netLog"
        const val FIELD_ID = "id"
        const val FIELD_METHOD = "method"
        const val FIELD_REQUEST = "request"
        const val FIELD_REQUEST_HEADER = "requestHeader"
        const val FIELD_REQUEST_BODY = "requestBody"
        const val FIELD_RESPONSE = "response"
        const val FIELD_RESPONSE_HEADER = "responseHeader"
        const val FIELD_RESPONSE_BODY = "responseBody"
        const val FIELD_DURATION = "duration"
        const val FIELD_TIME = "time"
        val FIELD_LIST = Array(10) { "" }.apply {
            set(0, FIELD_ID)
            set(1, FIELD_METHOD)
            set(2, FIELD_REQUEST)
            set(3, FIELD_REQUEST_HEADER)
            set(4, FIELD_REQUEST_BODY)
            set(5, FIELD_RESPONSE)
            set(6, FIELD_RESPONSE_HEADER)
            set(7, FIELD_RESPONSE_BODY)
            set(8, FIELD_DURATION)
            set(9, FIELD_TIME)
        }

        const val DB_TABLE_NET_LOG =
            "create table $TABLE_NAME($FIELD_ID integer primary key autoincrement," +
                    "$FIELD_METHOD text(8), " +
                    "$FIELD_REQUEST text, " +
                    "$FIELD_REQUEST_HEADER text, " +
                    "$FIELD_REQUEST_BODY text, " +
                    "$FIELD_RESPONSE text, " +
                    "$FIELD_RESPONSE_HEADER text, " +
                    "$FIELD_RESPONSE_BODY text, " +
                    "$FIELD_DURATION integer, " +
                    "$FIELD_TIME integer)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DB_TABLE_NET_LOG)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
    }
}