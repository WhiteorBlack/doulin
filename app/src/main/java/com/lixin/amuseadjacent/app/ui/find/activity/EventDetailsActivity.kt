package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 活动详情
 * Created by Slingge on 2018/8/25.
 */
class EventDetailsActivity : BaseActivity(), View.OnClickListener {

    private var commentAdapter: DynamicCommentAdapter? = null

    internal var urls = arrayOf("http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("活动名称")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "收藏"
        tv_right.setOnClickListener(this)

        tv_sign.setOnClickListener(this)
        iv_signInfo.setOnClickListener(this)

        tv_info.setTextColor(resources.getColor(R.color.colorTabText))
        tv_info.setTextSize(15)
        tv_info.setTextMaxLine(2)
        tv_info.setText("科技本身蕴含着一种碾压一切的力量，而这种力量会导致我们加速奔向某种设定好的结局。+这篇文章尝试预测未来最可能的5种结局：黄金时代，虚拟世界，冷平衡，生化人，大寂灭。")

        if (tv_info.line() >= 2) {
            iv_open.setOnClickListener(this)
        } else {
            iv_open.visibility = View.INVISIBLE
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager

        commentAdapter = DynamicCommentAdapter(this)
        rv_comment.adapter = commentAdapter

        val inflater = LayoutInflater.from(this)
        for (i in urls.indices) {
            val imageView = inflater.inflate(R.layout.item_praise, pl_header, false) as CircleImageView
            ImageLoader.getInstance().displayImage(urls[i], imageView)
            pl_header.addView(imageView)
        }


    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_right -> {

            }
            R.id.iv_open -> {
                tv_info.switchs()
                iv_open.visibility = View.INVISIBLE
            }
            R.id.tv_sign -> {//报名
                MyApplication.openActivity(this, EventSginUpActivity::class.java)
            }
            R.id.iv_signInfo -> {//报名列表
                MyApplication.openActivity(this, EventEnteredActivity::class.java)
            }
        }
    }


}