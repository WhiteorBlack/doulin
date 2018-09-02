package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_hobby.*

/**
 * 兴趣爱好
 * Created by Slingge on 2018/9/1
 */
class HobbyActivity : BaseActivity() ,View.OnClickListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hobby)
        init()
    }


    private fun init() {
        inittitle("兴趣爱好")
        StatusBarWhiteColor()

        edit_sport.setOnClickListener(this)
        edit_music.setOnClickListener(this)
        edit_food.setOnClickListener(this)
        edit_film.setOnClickListener(this)
        edit_book.setOnClickListener(this)

        for (i in 0 until 1) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "运动" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_sport)
//            tv.visibility= View.INVISIBLE
            fl_sport.addView(tv)
        }

        for (i in 0 until 1) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "音乐" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_music)
//            tv.visibility= View.INVISIBLE
            fl_music.addView(tv)
        }
        for (i in 0 until 1) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "美食" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_food)
//            tv.visibility= View.INVISIBLE
            fl_food.addView(tv)
        }
        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "电影" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_film)
//            tv.visibility= View.INVISIBLE
            fl_film.addView(tv)
        }

        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "书籍" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_book)
//            tv.visibility= View.INVISIBLE
            fl_book.addView(tv)
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.edit_sport->{
                val bundle=Bundle()
                bundle.putInt("flag",0)
                MyApplication.openActivity(this,AddLabelActivity::class.java)
            }
            R.id.edit_music->{
                val bundle=Bundle()
                bundle.putInt("flag",1)
                MyApplication.openActivity(this,AddLabelActivity::class.java)
            }
            R.id.edit_food->{
                val bundle=Bundle()
                bundle.putInt("flag",2)
                MyApplication.openActivity(this,AddLabelActivity::class.java)
            }
            R.id.edit_film->{
                val bundle=Bundle()
                bundle.putInt("flag",3)
                MyApplication.openActivity(this,AddLabelActivity::class.java)
            }
            R.id.edit_book->{
                val bundle=Bundle()
                bundle.putInt("flag",4)
                MyApplication.openActivity(this,AddLabelActivity::class.java)
            }
        }
    }


}