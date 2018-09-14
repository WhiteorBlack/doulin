package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.request.HomePage_110
import com.lixin.amuseadjacent.app.ui.mine.request.UserMessage_111
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.entity.LocalMedia
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 个人资料
 * Created by Slingge on 2018/9/1
 */
class PersonalDataActivity : BaseActivity(), View.OnClickListener {


    private var albumAdapter: AlbumAdapter? = null

    private val imageList = ArrayList<LocalMedia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        EventBus.getDefault().register(this)
        init()
    }


    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        HomePage_110.userInfo(StaticUtil.uid)
    }

    private fun init() {
        inittitle("个人资料")
        StatusBarWhiteColor()

        val gridLayoutManager = GridLayoutManager(this, 4)
        rv_album.layoutManager = gridLayoutManager

        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"
        tv_right.setOnClickListener(this)

        iv_edite.setOnClickListener(this)

        sport.setOnClickListener(this)
        music.setOnClickListener(this)
        food.setOnClickListener(this)
        film.setOnClickListener(this)
        book.setOnClickListener(this)
        label.setOnClickListener(this)
    }


    @Subscribe
    fun onEvent(model: HomePageModel) {
        val pageModel = model

        ImageLoader.getInstance().displayImage(model.icon, iv_header)

        if (model.sex == "0") {//女
            tv_sex.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_girl, tv_sex, 3)
        } else {
            tv_sex.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_boy, tv_sex, 3)
        }

        tv_name.text = StaticUtil.nickName
        tv_effect.text = "影响力" + StaticUtil.effectNum

        et_autograph.setText(pageModel.autograph)//签名
        et_occupation.setText(pageModel.occupation)//职业

        tv_sex.text = pageModel.age
        tv_constellation.text = pageModel.constellation
        tv_address.text = pageModel.communityName + pageModel.unitName + pageModel.doorNumber

        if (!imageList.isEmpty()) {
            imageList.clear()
        }
        for (i in 0 until pageModel.albumList.size) {
            val localMedia = LocalMedia()
            localMedia.path = pageModel.albumList[i].imgUrl
            localMedia.pictureType = pageModel.albumList[i].imgId
            imageList.add(localMedia)
        }
        imageList.add(LocalMedia())
        abLog.e("相册", Gson().toJson(imageList))

        albumAdapter = AlbumAdapter(this, imageList, 9, null)
        rv_album.adapter = albumAdapter
        albumAdapter!!.setFlag(0)
        albumAdapter!!.setDelShow(false)

        if (!pageModel.sportList.isEmpty()) {
            tv_sport.text = pageModel.sportList[0]
        }

        if (!pageModel.musicList.isEmpty()) {
            tv_music.text = pageModel.musicList[0]
        }

        if (!pageModel.foodsList.isEmpty()) {
            tv_food.text = pageModel.foodsList[0]
        }

        if (!pageModel.movieList.isEmpty()) {
            tv_film.text = pageModel.movieList[0]
        }

        if (!pageModel.booksList.isEmpty()) {
            tv_book.text = pageModel.booksList[0]
        }
        if (!pageModel.otherList.isEmpty()) {
            tv_label.text = pageModel.otherList[0]
        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_edite -> {//个人信息
                MyApplication.openActivity(this, PersonalInfoActivity::class.java)
            }
            R.id.sport, R.id.music, R.id.food, R.id.film, R.id.book -> {//爱好资料
                MyApplication.openActivity(this, HobbyActivity::class.java)
            }
            R.id.label -> {
                val bundle = Bundle()
                bundle.putInt("flag", 6)
                MyApplication.openActivity(this, AddLabelActivity::class.java, bundle)
            }
            R.id.tv_right -> {//保存签名、职业
                val autograph = AbStrUtil.etTostr(et_autograph)
                val occupation = AbStrUtil.etTostr(et_occupation)
                UserMessage_111.userInfo(this, "", "", "", autograph, occupation)
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


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}