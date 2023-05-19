package com.leslienan.wireshark.ui

import android.content.Intent
import com.leslienan.wireshark.R
import com.leslienan.wireshark.WiresharkUtil
import com.leslienan.wireshark.base.BaseActivity
import com.leslienan.wireshark.databinding.ActivityWiresharkBinding

internal class NetCaptureActivity : BaseActivity<ActivityWiresharkBinding>() {

    override fun initView() {
        WiresharkUtil.init(this)
        changeBtn(WiresharkUtil.isStart)
    }

    override fun initClick() {
        binding.tvStart.setOnClickListener {
            WiresharkUtil.isStart = !WiresharkUtil.isStart
            changeBtn(WiresharkUtil.isStart)
        }
        binding.tvHistory.setOnClickListener {
            startActivity(Intent(this, NetLogListActivity::class.java))
        }
    }

    private fun changeBtn(isSelect: Boolean) {
        binding.tvStart.text = if (WiresharkUtil.isStart) {
            getString(R.string.end_grab_bag)
        } else {
            getString(R.string.start_grab_bag)
        }
        binding.tvStart.isSelected = isSelect
    }

    override fun onDestroy() {
        super.onDestroy()
        WiresharkUtil.release()
    }
}