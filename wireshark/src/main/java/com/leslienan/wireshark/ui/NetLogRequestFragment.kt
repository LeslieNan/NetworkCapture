package com.leslienan.wireshark.ui

import android.os.Bundle
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.base.BaseFragment
import com.leslienan.wireshark.databinding.FragmentNetLogRequestBinding

/**
 * Copyright (c) 2022, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
internal class NetLogRequestFragment :
    BaseFragment<FragmentNetLogRequestBinding>() {

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

    private var netLogModel: NetLogModel? = null

    override fun initArgument() {
        netLogModel = arguments?.getParcelable(KEY_NET_LOG)
    }

    override fun initView() {
        binding.tvUrl.text = netLogModel?.request
        binding.tvHeader.text = netLogModel?.requestHeader
        binding.tvBody.text = netLogModel?.requestBody
    }
}