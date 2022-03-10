package com.leslienan.wireshark.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.ui.NetLogRequestFragment
import com.leslienan.wireshark.ui.NetLogResponseFragment

/**
 * Copyright (c) 2022, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class NetLogDetailAdapter(
    activity: FragmentActivity,
    private val netLogModel: NetLogModel?
) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NetLogRequestFragment.getFragment(netLogModel)
            }
            else -> {
                NetLogResponseFragment.getFragment(netLogModel)
            }
        }
    }
}