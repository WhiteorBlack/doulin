package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import kotlinx.android.synthetic.main.fragment_data.*

/**
 * 个人主页，资料
 * Created by Slingge on 2018/8/18
 */
class DataFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        return view
    }



    fun setDate(model: HomePageModel) {
        tv_autograph.text = model.autograph
        tv_num.text = StaticUtil.uid
        tv_industry.text = model.occupation

        fl_sport.removeAllViews()
        fl_music.removeAllViews()
        fl_food.removeAllViews()
        fl_video.removeAllViews()
        fl_book.removeAllViews()
        rv_label.removeAllViews()


        if (model.sportList == null || model.sportList.isEmpty()) {
            var tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.text="运动"
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_sport)
            tv.visibility = View.INVISIBLE
            fl_sport.addView(tv)
        } else {

            for (i in 0 until model.sportList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_sport)
                tv.text=model.sportList[i]
                fl_sport.addView(tv)
            }
        }

        if (model.musicList == null || model.musicList.isEmpty()) {
            var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.text="音乐"
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_music)
            tv.visibility = View.INVISIBLE
            fl_music.addView(tv)
        } else {
            for (i in 0 until model.musicList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.text=model.musicList[i]
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_music)
                fl_music.addView(tv)
            }
        }

        if (model.foodsList == null || model.foodsList.isEmpty()) {
            var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_food)
            tv.text="食物"
            tv.visibility = View.INVISIBLE
            fl_food.addView(tv)
        } else {
            for (i in 0 until model.foodsList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_food)
                tv.text=model.foodsList[i]
                fl_food.addView(tv)
            }
        }

        if (model.movieList == null || model.movieList.isEmpty()) {
            var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.text="电影"
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_film)
            tv.visibility = View.INVISIBLE
            fl_video.addView(tv)
        } else {
            for (i in 0 until model.movieList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_film)
                tv.text=model.movieList[i]
                fl_video.addView(tv)
            }
        }

        if (model.booksList == null || model.booksList.isEmpty()) {
            var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.text="书籍"
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_book)
            tv.visibility = View.INVISIBLE
            fl_book.addView(tv)
        } else {
            for (i in 0 until model.booksList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_book)
                tv.text=model.booksList[i]
                fl_book.addView(tv)
            }
        }

        if (model.otherList == null || model.otherList.isEmpty()) {
            var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                    nsv, false) as TextView
            tv.visibility = View.INVISIBLE
            tv.text="其他"
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_other)
            rv_label.addView(tv)
        } else {
            for (i in 0 until model.otherList.size) {
                var  tv = LayoutInflater.from(activity).inflate(R.layout.layout_flow_talent_type,
                        nsv, false) as TextView
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_hobby_other)
                tv.text=model.otherList[i]
                rv_label.addView(tv)
            }
        }


    }


    override fun loadData() {

    }




}