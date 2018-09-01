package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.android.synthetic.main.activity_personal_data.*

/**
 * Created by Slingge on 2018/9/1
 */
class PersonalDataActivity : BaseActivity() ,View.OnClickListener{

    private var albumAdapter: AlbumAdapter? = null

    private var list = ArrayList<LocalMedia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        init()
    }


    private fun init() {
        inittitle("个人资料")
        StatusBarWhiteColor()

        val gridLayoutManager = GridLayoutManager(this, 4)
        rv_album.layoutManager = gridLayoutManager

        list.add(LocalMedia())
        albumAdapter = AlbumAdapter(this, list, 3, null)
        rv_album.adapter = albumAdapter
        albumAdapter!!.setFlag(0)

        iv_edite.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_edite->{//个人信息
                    MyApplication.openActivity(this,PersonalInfoActivity::class.java)
            }
        }
    }



}