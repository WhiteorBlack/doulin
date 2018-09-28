package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.AlbumDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.request.EditeNote_167
import com.lixin.amuseadjacent.app.ui.mine.request.HomePage_110
import com.lixin.amuseadjacent.app.ui.mine.request.MyAlbum_112113114
import com.lixin.amuseadjacent.app.ui.mine.request.UserMessage_111
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

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
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
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

        view_person.setOnClickListener(this)

        sport.setOnClickListener(this)
        music.setOnClickListener(this)
        food.setOnClickListener(this)
        film.setOnClickListener(this)
        book.setOnClickListener(this)
        label.setOnClickListener(this)

        et_note.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val s = p0!!.toString()
                if (TextUtils.isEmpty(s)) {
                    return
                }
                EditeNote_167.note(StaticUtil.uid, s)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        rv_album.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_album) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0 || i >= imageList.size || i == imageList.size - 1) {
                    return
                }
                AlbumDialog.dialog(this@PersonalDataActivity, object : AlbumDialog.AlbumEditCallBal {
                    override fun albumedit(flag: Int) {
                        if (flag == 0) {//设置为头像
                            if (imageList[i].path.contains("http://")) {
                                ProgressDialog.showDialog(this@PersonalDataActivity)
                                MyAlbum_112113114.setHeaderImage(imageList[i].pictureType, object : MyAlbum_112113114.HeaderImageCallBack {
                                    override fun headerIcon(iconUrl: String) {
                                        Collections.swap(imageList, 0, i)
                                        albumAdapter!!.notifyDataSetChanged()
                                    }
                                })
                            } else {
                                ToastUtil.showToast("请先上传")
                            }
                        } else {//删除
                            if (imageList[i].path.contains("http://")) {
                                MyAlbum_112113114.delAlbum(imageList[i].pictureType, i, object : MyAlbum_112113114.DelAlbumCallBacl {
                                    override fun delAlbum(i: Int) {
                                        imageList.removeAt(i)
                                        albumAdapter!!.notifyDataSetChanged()
                                    }
                                })
                            } else {
                                imageList.removeAt(i)
                                albumAdapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                })
            }
        })
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

        et_note.setText(model.remarks)

        tv_sex.text = pageModel.age
        tv_constellation.text = pageModel.constellation
        tv_address.text = pageModel.communityName + pageModel.unitName + pageModel.doorNumber

        if (imageList.isEmpty()) {
            for (i in 0 until pageModel.albumList.size) {
                val localMedia = LocalMedia()
                localMedia.path = pageModel.albumList[i].imgUrl
                localMedia.pictureType = pageModel.albumList[i].imgId
                imageList.add(localMedia)
            }
            imageList.add(LocalMedia("", 1L, 0, ""))
            abLog.e("相册", Gson().toJson(imageList))

            albumAdapter = AlbumAdapter(this, imageList, 8, null)
            rv_album.adapter = albumAdapter
            albumAdapter!!.setFlag(0)
            albumAdapter!!.setDelShow(false)
        }

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
            R.id.view_person -> {//个人信息
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
                MyAlbum_112113114.AddAlbum(this, imageList)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            for (i in 0 until PictureSelector.obtainMultipleResult(data).size) {
                imageList.add(imageList.size - 1, PictureSelector.obtainMultipleResult(data)[i])
            }
            abLog.e("相册路径", Gson().toJson(imageList))
            albumAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}