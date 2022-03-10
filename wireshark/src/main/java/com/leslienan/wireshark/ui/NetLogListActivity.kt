package com.leslienan.wireshark.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.leslienan.wireshark.WiresharkUtil
import com.leslienan.wireshark.adapter.NetLogAdapter
import com.leslienan.wireshark.base.BaseActivity
import com.leslienan.wireshark.databinding.ActivityLogListBinding

internal class NetLogListActivity : BaseActivity<ActivityLogListBinding>() {

    private val adapter: NetLogAdapter by lazy { NetLogAdapter(this) }
    private val filterDialog: FilterDialog by lazy { FilterDialog(this) }

    override fun initView() {
        binding.rvWire.apply {
            layoutManager = LinearLayoutManager(this@NetLogListActivity)
            adapter = this@NetLogListActivity.adapter
        }
        adapter.onItemClickListener = {
            NetLogDetailActivity.goTo(this, it)
        }
        filterDialog.onFilterListener = {
            adapter.filter(it)
        }
    }

    override fun initClick() {
        binding.tvFilter.setOnClickListener {
            filterDialog.show()
        }
        binding.tvEdit.setOnClickListener {
            binding.tvEditCancel.visibility = View.VISIBLE
            binding.tvEditAll.visibility = View.VISIBLE
            binding.tvEditDelete.visibility = View.VISIBLE
            binding.tvEdit.visibility = View.GONE
            binding.tvFilter.visibility = View.GONE
            adapter.changeMode(true)
        }
        binding.tvEditCancel.setOnClickListener {
            binding.tvEditCancel.visibility = View.GONE
            binding.tvEditAll.visibility = View.GONE
            binding.tvEditDelete.visibility = View.GONE
            binding.tvEdit.visibility = View.VISIBLE
            binding.tvFilter.visibility = View.VISIBLE
            adapter.changeMode(false)
        }
        binding.tvEditAll.setOnClickListener {
            adapter.selectAll()
        }
        binding.tvEditDelete.setOnClickListener {
            val selectIds = adapter.getSelectIds()
            WiresharkUtil.deleteNetLog(selectIds)
            adapter.deleteSelected()
        }
    }

    override fun initData() {
        WiresharkUtil.selectAll {
            adapter.setData(it)
            val methods = mutableListOf<String>()
            it.forEach { netLogModel ->
                methods.add(netLogModel.method)
            }
            filterDialog.setMethodGroup(methods)
        }
    }

    override fun handleData() {
        WiresharkUtil.setOnAddListener {
            adapter.addData(it)
        }
    }
}