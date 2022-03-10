package com.leslienan.wireshark.ui

import android.app.Dialog
import android.content.Context
import android.view.*
import com.leslienan.wireshark.R
import com.leslienan.wireshark.databinding.DialogFilterBinding

/**
 * Copyright (c) 2021, Lollitech
 * All rights reserved
 * Author: lihaonan@lollitech.com
 */
class FilterDialog(context: Context) : Dialog(context) {
    private val binding =
        DialogFilterBinding.inflate(LayoutInflater.from(context))

    var onFilterListener: ((filter: FilterModel) -> Unit)? = null

    init {
        setContentView(binding.root)
        window?.apply {
            setGravity(Gravity.BOTTOM)
            setWindowAnimations(R.style.ActionSheetDialogAnimation)
            val screenHeight = context.resources.displayMetrics.heightPixels
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (screenHeight * 2f / 3).toInt()
            )
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        binding.tvFilterSure.setOnClickListener {
            onFilterListener?.invoke(getFilterCondition())
            dismiss()
        }
        binding.tvFilterReset.setOnClickListener {
            resetFilterCondition()
            onFilterListener?.invoke(FilterModel())
            dismiss()
        }
    }

    private fun getFilterCondition(): FilterModel {
        val method = when (binding.radioGroup.rgMethod.checkedRadioButtonId) {
            R.id.rb_get -> "GET"
            R.id.rb_post -> "POST"
            R.id.rb_head -> "HEAD"
            R.id.rb_put -> "PUT"
            R.id.rb_delete -> "DELETE"
            R.id.rb_connect -> "CONNECT"
            R.id.rb_options -> "OPTIONS"
            R.id.rb_trace -> "TRACE"
            else -> ""
        }
        return FilterModel(
            binding.etFilterKeyword.text.toString(),
            method, ""
        )
    }

    private fun resetFilterCondition() {
        binding.radioGroup.rgMethod.check(R.id.rb_none)
        binding.etFilterKeyword.setText("")
    }

    fun setMethodGroup(methodList: List<String>) {
        var get = false
        var post = false
        var head = false
        var put = false
        var delete = false
        var connect = false
        var optioins = false
        var trace = false
        for (method in methodList) {
            when (method) {
                "GET" -> get = true
                "POST" -> post = true
                "HEAD" -> head = true
                "PUT" -> put = true
                "DELETE" -> delete = true
                "CONNECT" -> connect = true
                "OPTIONS" -> optioins = true
                "TRACE" -> trace = true
            }
        }
        binding.radioGroup.apply {
            rbGet.visibility = if (get) View.VISIBLE else View.GONE
            rbPost.visibility = if (post) View.VISIBLE else View.GONE
            rbHead.visibility = if (head) View.VISIBLE else View.GONE
            rbPut.visibility = if (put) View.VISIBLE else View.GONE
            rbDelete.visibility = if (delete) View.VISIBLE else View.GONE
            rbConnect.visibility = if (connect) View.VISIBLE else View.GONE
            rbOptions.visibility = if (optioins) View.VISIBLE else View.GONE
            rbTrace.visibility = if (trace) View.VISIBLE else View.GONE
        }
    }
}

data class FilterModel(
    val keyword: String = "",
    val method: String = "",
    val responseType: String = ""
)