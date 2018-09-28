package com.lixin.amuseadjacent.app.ui.entrance.fagment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment

/**
 * Created by Slingge on 2018/9/28
 */
class WelFragment1 : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome1, container, false)
        return view

    }

    override fun loadData() {

    }


}