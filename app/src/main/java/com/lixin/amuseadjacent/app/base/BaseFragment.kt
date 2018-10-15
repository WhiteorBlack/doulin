package com.lixin.amuseadjacent.app.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Slingge on 2017/4/21 0021.
 */

abstract class BaseFragment : Fragment() {

    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun loadData()

    @JvmOverloads
    fun prepareFetchData(forceUpdate: Boolean = refreshData): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData()
            isDataInitiated = true
            refreshData = false//false,只加载一次，true每次进入页面都加载
            return true
        }
        return false
    }

    companion object {

        var refreshData: Boolean = false
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

}
