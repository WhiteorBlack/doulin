package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (i in 0 until 1) {
            val tv = LayoutInflater.from(activity).inflate(
                    R.layout.layout_flow_talent_type, nsv, false) as TextView
            tv.text = "运动" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_sport)
            tv.visibility=View.INVISIBLE
            fl_sport.addView(tv)
        }

           for (i in 0 until 1) {
               val tv = LayoutInflater.from(activity).inflate(
                       R.layout.layout_flow_talent_type, nsv, false) as TextView
               tv.text = "音乐" + i.toString()
               tv.setTextColor(resources.getColor(R.color.white))
               tv.setBackgroundResource(R.drawable.bg_hobby_music)
               tv.visibility=View.INVISIBLE
               fl_music.addView(tv)
           }
           for (i in 0 until 1) {
               val tv = LayoutInflater.from(activity).inflate(
                       R.layout.layout_flow_talent_type, nsv, false) as TextView
               tv.text = "美食" + i.toString()
               tv.setTextColor(resources.getColor(R.color.white))
               tv.setBackgroundResource(R.drawable.bg_hobby_food)
               tv.visibility=View.INVISIBLE
               fl_food.addView(tv)
           }
           for (i in 0 until 1) {
               val tv = LayoutInflater.from(activity).inflate(
                       R.layout.layout_flow_talent_type, nsv, false) as TextView
               tv.text = "电影" + i.toString()
               tv.setTextColor(resources.getColor(R.color.white))
               tv.setBackgroundResource(R.drawable.bg_hobby_film)
               tv.visibility=View.INVISIBLE
               fl_video.addView(tv)
           }

           for (i in 0 until 1) {
               val tv = LayoutInflater.from(activity).inflate(
                       R.layout.layout_flow_talent_type, nsv, false) as TextView
               tv.text = "书籍" + i.toString()
               tv.setTextColor(resources.getColor(R.color.white))
               tv.setBackgroundResource(R.drawable.bg_hobby_book)
               tv.visibility=View.INVISIBLE
               fl_book.addView(tv)
           }


    }

    override fun loadData() {

    }


}