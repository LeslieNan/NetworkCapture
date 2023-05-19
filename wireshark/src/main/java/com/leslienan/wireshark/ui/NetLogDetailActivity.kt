package com.leslienan.wireshark.ui

import android.app.Activity
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.adapter.NetLogDetailAdapter
import com.leslienan.wireshark.base.BaseActivity
import com.leslienan.wireshark.databinding.ActivityNetLogDetailBinding

internal class NetLogDetailActivity :
    BaseActivity<ActivityNetLogDetailBinding>() {
    companion object {
        private const val KEY_NET_LOG = "KEY_NET_LOG"
        fun goTo(activity: Activity, netLogModel: NetLogModel) {
            val intent = Intent(activity, NetLogDetailActivity::class.java)
            intent.putExtra(KEY_NET_LOG, netLogModel)
            activity.startActivity(intent)
        }
    }

    private var netLogModel: NetLogModel? = null

    private val adapter: NetLogDetailAdapter by lazy {
        NetLogDetailAdapter(this, netLogModel)
    }

    override fun initIntent() {
        netLogModel = intent.getParcelableExtra(KEY_NET_LOG)
    }

    override fun initView() {
        binding.tabTop.apply {
            removeAllTabs()
            addTab(newTab().setText("请求"), true)
            addTab(newTab().setText("响应"), false)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpContent.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
        binding.vpContent.apply {
            offscreenPageLimit = 2
            adapter = this@NetLogDetailActivity.adapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tabTop.setScrollPosition(position, 0f, true)
                }
            })
        }
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.tvTitle.text = netLogModel?.request
    }

}