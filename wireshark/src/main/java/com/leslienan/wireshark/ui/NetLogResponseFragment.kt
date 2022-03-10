package com.leslienan.wireshark.ui

import android.os.Bundle
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.base.BaseFragment
import com.leslienan.wireshark.databinding.FragmentNetLogResponseBinding

/**
 * Copyright (c) 2022, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
internal class NetLogResponseFragment :
    BaseFragment<FragmentNetLogResponseBinding>() {

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

    private var netLogModel: NetLogModel? = null

    override fun initArgument() {
        netLogModel = arguments?.getParcelable(KEY_NET_LOG)
    }

    override fun initView() {
        binding.tvUrl.text = netLogModel?.response+"\n响应时间："+netLogModel?.duration+"ms"
        binding.tvHeader.text = netLogModel?.responseHeader
        binding.tvBody.text = netLogModel?.responseBody
    }
}