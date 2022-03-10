package com.leslienan.wireshark

import android.content.Context
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class WiresharkInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val oldRequest = chain.request()
        val method = oldRequest.method
        var postBodyString: String? = ""
        val newRequestBuild: Request.Builder
        if ("GET" == method) {
            // 添加新的参数
            val commonParamsUrlBuilder: HttpUrl.Builder = oldRequest.url
                .newBuilder()
                .scheme(oldRequest.url.scheme)
                .host(oldRequest.url.host)
            newRequestBuild = oldRequest.newBuilder()
                .method(oldRequest.method, oldRequest.body)
                .url(commonParamsUrlBuilder.build())
        } else {
            postBodyString = bodyToString(oldRequest.body)
            newRequestBuild = oldRequest.newBuilder()
        }
        //获取request信息
        val requestUrl = oldRequest.url.toUrl().toExternalForm()
        val requestHeader = oldRequest.headers.toString()
        //新request
        val newRequest = newRequestBuild.build()
        val response = chain.proceed(newRequest)
        val mediaType = response.body?.contentType()
        val responseInfo = response.code
        val responseHeader = response.headers.toString()
        val responseBody = response.body?.string()
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        //发送
        val model = NetLogModel(
            method,
            requestUrl, requestHeader, postBodyString ?: "",
            responseInfo.toString(), responseHeader, responseBody ?: "",
            duration, startTime
        )
        WiresharkUtil.addNetLog(model)
        return response.newBuilder()
            .body(responseBody?.toResponseBody(mediaType))
            .build()
    }


    private fun bodyToString(request: RequestBody?): String? {
        return try {
            val buffer = Buffer()
            if (request != null) {
                request.writeTo(buffer)
            } else {
                return ""
            }
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }
}