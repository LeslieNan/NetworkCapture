package com.leslienan.wireshark

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
open class NetLogModel(
    val method: String,
    val request: String,
    val requestHeader: String,
    val requestBody: String,
    val response: String,
    val responseHeader: String,
    val responseBody: String,
    val duration: Long,
    val time: Long,
    val id: Long = 0L
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(method)
        parcel.writeString(request)
        parcel.writeString(requestHeader)
        parcel.writeString(requestBody)
        parcel.writeString(response)
        parcel.writeString(responseHeader)
        parcel.writeString(responseBody)
        parcel.writeLong(duration)
        parcel.writeLong(time)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetLogModel> {
        override fun createFromParcel(parcel: Parcel): NetLogModel {
            return NetLogModel(parcel)
        }

        override fun newArray(size: Int): Array<NetLogModel?> {
            return arrayOfNulls(size)
        }
    }
}