package com.leslienan.wireshark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.databinding.ItemNetLogBinding
import com.leslienan.wireshark.ui.FilterModel

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class NetLogAdapter(context: Context) :
    RecyclerView.Adapter<NetLogAdapter.ViewHolder>() {

    private val list = mutableListOf<NetLogModel>()
    private val tempList = mutableListOf<NetLogModel>()
    private val selectList = mutableListOf<Boolean>()
    private val layoutInflater = LayoutInflater.from(context)
    private var isSelectMode = false
    private var isSelectAll = false
    var onItemClickListener: ((item: NetLogModel) -> Unit)? = null

    fun setData(data: List<NetLogModel>) {
        list.clear()
        list.addAll(data)
        tempList.addAll(data)
        selectList.clear()
        repeat(list.size) {
            selectList.add(false)
        }
        notifyDataSetChanged()
    }

    fun addData(data: NetLogModel) {
        val position = list.size
        list.add(0, data)
        tempList.add(0, data)
        selectList.add(0, false)
        notifyItemInserted(position)
    }

    fun changeMode(isSelect: Boolean) {
        isSelectMode = isSelect
        notifyDataSetChanged()
    }

    fun selectAll() {
        isSelectAll = !isSelectAll
        if (isSelectAll) {
            for (i in selectList.indices) {
                if (!selectList[i]) selectList[i] = true
            }
        } else {
            for (i in selectList.indices) {
                if (selectList[i]) selectList[i] = false
            }
        }
        notifyDataSetChanged()
    }

    fun getSelectIds(): List<Long> {
        val ids = mutableListOf<Long>()
        for (i in list.indices) {
            if (selectList[i]) {
                ids.add(list[i].id)
            }
        }
        return ids
    }

    fun deleteSelected() {
        for (i in selectList.size - 1 downTo 0) {
            if (selectList[i]) {
                list.removeAt(i)
                selectList.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun filter(filter: FilterModel) {
        list.clear()
        list.addAll(tempList.filter {
            val methodF = if (filter.method == "") {
                true
            } else {
                filter.method == it.method
            }
            val keywordF = it.request.contains(filter.keyword)
            //val responseTypeF = it.request.contains(filter.keyword)
            methodF && keywordF
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder =
        ViewHolder(ItemNetLogBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            if (isSelectMode) {
                ivSelect.visibility = View.VISIBLE
                ivSelect.isSelected = selectList[position]
            } else {
                ivSelect.visibility = View.GONE
            }
            tvItemUrl.text = item.request
            tvItemContent.text = item.method
            ivSelect.setOnClickListener {
                ivSelect.isSelected = !ivSelect.isSelected
                selectList[position] = ivSelect.isSelected
                if (!ivSelect.isSelected && isSelectAll) {
                    isSelectAll = false
                }
            }
            root.setOnClickListener {
                if (!isSelectMode) {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: ItemNetLogBinding) :
        RecyclerView.ViewHolder(binding.root)
}