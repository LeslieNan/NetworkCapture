package com.leslienan.wireshark.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * Author by haonan, Date on 3/23/21.
 * Email:leslienan@qq.com
 * PS:
 */
internal abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val cls = type.actualTypeArguments[0] as Class<*>
            val inflate = cls.getDeclaredMethod("inflate", LayoutInflater::class.java)
            binding = inflate.invoke(null, layoutInflater) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initArgument()
        initView()
        initClick()
        handlerData()
    }

    open fun initClick() {}
    open fun initView() {}
    open fun initArgument() {}
    open fun initData() {}
    open fun handlerData() {}

    private var isFirst = true

    /**
     * 在viewpager2中实现懒加载
     */
    override fun onResume() {
        super.onResume()
        if (isFirst) {
            initData()
            isFirst = false
        }
    }
}