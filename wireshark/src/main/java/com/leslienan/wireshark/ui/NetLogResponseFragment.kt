package com.leslienan.wireshark.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.leslienan.wireshark.LogInterceptor
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.base.BaseFragment
import com.leslienan.wireshark.databinding.FragmentNetLogResponseBinding

/**
 * Copyright (c) 2022, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
internal class NetLogResponseFragment : BaseFragment<FragmentNetLogResponseBinding>() {

    companion object {
        const val KEY_NET_LOG = "KEY_NET_LOG"
        fun getFragment(netLogModel: NetLogModel?): NetLogResponseFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_NET_LOG, netLogModel)
            val fragment = NetLogResponseFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var netLogModel: NetLogModel

    override fun initArgument() {
        netLogModel = arguments?.getParcelable(KEY_NET_LOG)!!
    }

    override fun initView() {
        binding.tvUrl.text = netLogModel.response + "\n响应时间：" + netLogModel?.duration + "ms"
        binding.tvHeader.text = netLogModel.responseHeader
        binding.tvBody.text = netLogModel.responseBody
    }

    override fun initClick() {
        var isFormat = false
        binding.tvJson.setOnClickListener {
            if (!isFormat) {
                binding.tvBody.text = LogInterceptor.formatJson(netLogModel.responseBody)
                isFormat = true
            }
        }
        binding.tvCopy.setOnClickListener {
            try {
                val manager: ClipboardManager =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val mClipData = ClipData.newPlainText("Label", binding.tvBody.text)
                // 将ClipData内容放到系统剪贴板里。
                manager.setPrimaryClip(mClipData)
                Toast.makeText(requireContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}