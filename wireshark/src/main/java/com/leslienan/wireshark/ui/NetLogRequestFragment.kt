package com.leslienan.wireshark.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.leslienan.wireshark.LogInterceptor
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.base.BaseFragment
import com.leslienan.wireshark.databinding.FragmentNetLogRequestBinding


/**
 * Copyright (c) 2022, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
internal class NetLogRequestFragment : BaseFragment<FragmentNetLogRequestBinding>() {

    companion object {
        const val KEY_NET_LOG = "KEY_NET_LOG"
        fun getFragment(netLogModel: NetLogModel?): NetLogRequestFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_NET_LOG, netLogModel)
            val fragment = NetLogRequestFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var netLogModel: NetLogModel

    override fun initArgument() {
        netLogModel = arguments?.getParcelable(KEY_NET_LOG)!!
    }

    override fun initView() {
        binding.tvUrl.text = netLogModel.request
        binding.tvHeader.text = netLogModel.requestHeader
        binding.tvBody.text = netLogModel.requestBody
    }

    override fun initClick() {
        var isFormat = false
        binding.tvJson.setOnClickListener {
            if (!isFormat) {
                binding.tvBody.text = LogInterceptor.formatJson(netLogModel.requestBody)
                isFormat = true
            }
        }
        binding.tvCopy.setOnClickListener {
            val text = binding.tvBody.text
            if (text.isEmpty()) return@setOnClickListener
            try {
                val manager: ClipboardManager =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val mClipData = ClipData.newPlainText("Label", text)
                // 将ClipData内容放到系统剪贴板里。
                manager.setPrimaryClip(mClipData)
                Toast.makeText(requireContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}