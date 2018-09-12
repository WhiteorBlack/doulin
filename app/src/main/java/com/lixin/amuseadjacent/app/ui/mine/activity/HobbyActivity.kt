package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.request.HomePage_110
import com.lixin.amuseadjacent.app.util.StaticUtil
import kotlinx.android.synthetic.main.activity_hobby.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 兴趣爱好
 * Created by Slingge on 2018/9/1
 */
class HobbyActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hobby)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        HomePage_110.userInfo(StaticUtil.uid)
    }

    private fun init() {
        inittitle("兴趣爱好")
        StatusBarWhiteColor()

        edit_sport.setOnClickListener(this)
        edit_music.setOnClickListener(this)
        edit_food.setOnClickListener(this)
        edit_film.setOnClickListener(this)
        edit_book.setOnClickListener(this)

    }

    @Subscribe
    fun onEvent(pageModel: HomePageModel) {

        fl_sport.removeAllViews()
        fl_music.removeAllViews()
        fl_food.removeAllViews()
        fl_film.removeAllViews()
        fl_book.removeAllViews()
        fl_music.removeAllViews()

        if (pageModel.sportList.isEmpty()) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.visibility = View.INVISIBLE
            fl_sport.addView(tv)
        } else {
            for (i in 0 until pageModel.sportList.size) {
                val tv = LayoutInflater.from(this).inflate(
                        R.layout.layout_flow_talent_type, ll_main, false) as TextView
                tv.text = pageModel.sportList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_sport)
                fl_sport.addView(tv)
            }
        }


        if (pageModel.musicList.isEmpty()) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.visibility = View.INVISIBLE
            fl_music.addView(tv)
        } else {
            for (i in 0 until pageModel.musicList.size) {
                val tv = LayoutInflater.from(this).inflate(
                        R.layout.layout_flow_talent_type, ll_main, false) as TextView
                tv.text = pageModel.musicList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_music)
                fl_music.addView(tv)
            }
        }


        if (pageModel.foodsList.isEmpty()) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.visibility = View.INVISIBLE
            fl_food.addView(tv)
        } else {
            for (i in 0 until pageModel.foodsList.size) {
                val tv = LayoutInflater.from(this).inflate(
                        R.layout.layout_flow_talent_type, ll_main, false) as TextView
                tv.text = pageModel.foodsList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_food)
                fl_food.addView(tv)
            }
        }

        if (pageModel.movieList.isEmpty()) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.visibility = View.INVISIBLE
            fl_film.addView(tv)
        } else {
            for (i in 0 until pageModel.movieList.size) {
                val tv = LayoutInflater.from(this).inflate(
                        R.layout.layout_flow_talent_type, ll_main, false) as TextView
                tv.text = pageModel.movieList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_film)
                fl_film.addView(tv)
            }
        }

        if (pageModel.booksList.isEmpty()) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.visibility = View.INVISIBLE
            fl_book.addView(tv)
        } else {
            for (i in 0 until pageModel.booksList.size) {
                val tv = LayoutInflater.from(this).inflate(
                        R.layout.layout_flow_talent_type, ll_main, false) as TextView
                tv.text = pageModel.booksList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_book)
                fl_book.addView(tv)
            }
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.edit_sport -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
            R.id.edit_music -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
            R.id.edit_food -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
            R.id.edit_film -> {
                val bundle = Bundle()
                bundle.putInt("flag", 4)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
            R.id.edit_book -> {
                val bundle = Bundle()
                bundle.putInt("flag", 5)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}