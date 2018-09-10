package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.model.UserInfoModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.entity.LocalMedia
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_personal_data.*
import java.util.ArrayList

/**
 * 个人资料
 * Created by Slingge on 2018/9/1
 */
class PersonalDataActivity : BaseActivity(), View.OnClickListener {

    private var albumAdapter: AlbumAdapter? = null

    private val imageList = ArrayList<LocalMedia>()

    private var userModel: UserInfoModel? = null
    private var pageModel: HomePageModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        init()
    }


    override fun onStart() {
        super.onStart()
        ImageLoader.getInstance().displayImage(StaticUtil.headerUrl, iv_header)
        if (!TextUtils.isEmpty(StaticUtil.nickName)) {
            tv_name.text = StaticUtil.nickName
        }
    }

    private fun init() {
        inittitle("个人资料")
        StatusBarWhiteColor()

        val gridLayoutManager = GridLayoutManager(this, 4)
        rv_album.layoutManager = gridLayoutManager

        userModel = intent.getSerializableExtra("userModel") as UserInfoModel
        tv_name.text = userModel!!.nickname
        tv_effect.text = "影响力" + userModel!!.effectNum


        pageModel = intent.getSerializableExtra("pageModel") as HomePageModel
        et_autograph.setText(pageModel!!.autograph)//签名
        et_occupation.setText(pageModel!!.occupation)//职业

        tv_sex.text = pageModel!!.age
        tv_constellation.text = pageModel!!.constellation
        tv_address.text = pageModel!!.communityName + pageModel!!.unitName + pageModel!!.doorNumber

        for (i in 0 until pageModel!!.albumList.size) {
            val localMedia = LocalMedia()
            localMedia.path = pageModel!!.albumList[i].imgUrl
            localMedia.pictureType = pageModel!!.albumList[i].imgId
            imageList.add(localMedia)
        }
        imageList.add(LocalMedia())
        abLog.e("相册", Gson().toJson(imageList))

        albumAdapter = AlbumAdapter(this, imageList, 9, null)
        rv_album.adapter = albumAdapter
        albumAdapter!!.setFlag(0)
        albumAdapter!!.setDelShow(false)

        if (!pageModel!!.sportList.isEmpty()) {
            tv_sport.text = pageModel!!.sportList[0]
        }

        if (!pageModel!!.musicList.isEmpty()) {
            tv_music.text = pageModel!!.musicList[0]
        }

        if (!pageModel!!.foodsList.isEmpty()) {
            tv_food.text = pageModel!!.foodsList[0]
        }

        if (!pageModel!!.movieList.isEmpty()) {
            tv_film.text = pageModel!!.movieList[0]
        }

        if (!pageModel!!.booksList.isEmpty()) {
            tv_book.text = pageModel!!.booksList[0]
        }
        if (!pageModel!!.otherList.isEmpty()) {
            tv_label.text = pageModel!!.otherList[0]
        }


        iv_edite.setOnClickListener(this)

        sport.setOnClickListener(this)
        music.setOnClickListener(this)
        food.setOnClickListener(this)
        film.setOnClickListener(this)
        book.setOnClickListener(this)
        label.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_edite -> {//个人信息
                MyApplication.openActivity(this, PersonalInfoActivity::class.java)
            }
            R.id.sport, R.id.music, R.id.food, R.id.film, R.id.book -> {//爱好资料
                val bundle = Bundle()
                bundle.putSerializable("model",pageModel)
                MyApplication.openActivity(this, HobbyActivity::class.java,bundle)
            }
            R.id.label -> {
                val bundle = Bundle()
                bundle.putInt("flag", 6)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            imageList.clear()
            imageList.addAll(data.getParcelableArrayListExtra("LocalMedia"))
            albumAdapter!!.notifyDataSetChanged()
        }

    }


}