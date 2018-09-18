package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.DatePop
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.find.model.AddTalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_talent_authentication_add.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.*

/**
 * 达人经历添加
 * Created by Slingge on 2018/8/22
 */
class TalentAuthenticationAddActivity : BaseActivity(), View.OnClickListener, AlbumAdapter.ImageRemoveCallback
        , DatePop.WheelViewCallBack2 {

    private var model: TalenExperienceModel.dataModel? = null

    private var albumAdapter: AlbumAdapter? = null
    private val imageList = ArrayList<LocalMedia>()
    private val maxNum = 3

    private var datePop: DatePop? = null
    private var flag = 0//0 开始时间，1结束时间

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_authentication_add)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("添加经历")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "添加"
        tv_right.setOnClickListener(this)

        tv_startTime.setOnClickListener(this)
        tv_endTime.setOnClickListener(this)

        val linearLayoutManager = GridLayoutManager(this, 3)
        rv_image.layoutManager = linearLayoutManager

        imageList.add(LocalMedia())

        if (intent.getSerializableExtra("model") != null) {
            tv_right.text = "修改"
            model = intent.getSerializableExtra("model") as TalenExperienceModel.dataModel
            tv_startTime.text = model!!.startTime
            tv_endTime.text = model!!.endTime
            et_title.setText(model!!.title)
            et_info.setText(model!!.content)
            model!!.imgurl.reverse()//倒叙排列
            for (i in 0 until model!!.imgurl.size) {
                val localMedia = LocalMedia()
                localMedia.path = model!!.imgurl[i]
                imageList.add(0, localMedia)
            }
        }

        albumAdapter = AlbumAdapter(this, imageList, maxNum, this)
        rv_image.adapter = albumAdapter
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_startTime -> {
                flag = 0
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(ll_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_endTime -> {
                flag = 1
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(ll_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_right -> {
                val statTime = AbStrUtil.tvTostr(tv_startTime)
                if (TextUtils.isEmpty(statTime) || statTime == "请选择") {
                    ToastUtil.showToast("请选择开始时间")
                    return
                }
                val endTime = AbStrUtil.tvTostr(tv_endTime)
                if (TextUtils.isEmpty(endTime) || endTime == "请选择") {
                    ToastUtil.showToast("请选择结束时间")
                    return
                }
                val name = AbStrUtil.tvTostr(et_title)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入达人主题")
                    return
                }
                val info = AbStrUtil.tvTostr(et_info)
                if (TextUtils.isEmpty(info)) {
                    ToastUtil.showToast("请输入达人描述")
                    return
                }

                val model = AddTalenExperienceModel()
                model.title = name
                model.content = info
                model.startTime = statTime
                model.endTime = endTime
                ProgressDialog.showDialog(this)
                if (intent.getSerializableExtra("model") == null) {//新增经历
                    if (imageList.size == 1) {
                        Talent212_218225.AddTalenExperience(this, model)
                    } else {
                        Talent212_218225.AddTalenExperienceImage(this, imageList, model)
                    }
                } else {//修改经历
                    model.experienceId = this.model!!.experienceId
                    model.cmd = "editExperience"
                    if (imageList.size == 1) {
                        Talent212_218225.EditExperience(this,model,imageList)
                    } else {
                        Talent212_218225.AddTalenExperienceImage(this,imageList,model)
                    }
                }
            }
        }
    }


    //出生日期
    override fun position(position1: String, position2: String, position3: String) {
        if (flag == 0) {
            tv_startTime.text = "$position1-$position2-$position3"
        } else {
            tv_endTime.text = "$position1-$position2-$position3"
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, maxNum, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            for (i in 0 until PictureSelector.obtainMultipleResult(data).size) {
                imageList.add(imageList.size - 1, PictureSelector.obtainMultipleResult(data)[i])
            }
            albumAdapter!!.notifyDataSetChanged()
        }
    }


    override fun imageRemove(i: Int) {
        imageList.removeAt(i)
        albumAdapter!!.notifyDataSetChanged()
    }


}