package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我的相册
 * Created by Slingge on 2018/9/1
 */
class MyAlbumActivity : BaseActivity() {

    private var list = ArrayList<LocalMedia>()
    private var albumAdapter: AlbumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("我的相册")
        StatusBarWhiteColor()

        tv_right.visibility= View.VISIBLE
        tv_right.text = "编辑"
        tv_right.setOnClickListener { v-> }

        val gridLayoutManager = GridLayoutManager(this, 4)
        xrecyclerview.layoutManager = gridLayoutManager

        list.add(LocalMedia())
        albumAdapter = AlbumAdapter(this, list, 3, null)
        xrecyclerview.adapter = albumAdapter
    }


}