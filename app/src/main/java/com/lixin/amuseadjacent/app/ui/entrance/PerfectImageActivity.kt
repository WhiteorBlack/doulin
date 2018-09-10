package com.lixin.amuseadjacent.app.ui.entrance

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.CommunityDialog
import com.lixin.amuseadjacent.app.ui.dialog.DatePop
import com.lixin.amuseadjacent.app.ui.dialog.SexDialog
import com.lixin.amuseadjacent.app.ui.entrance.model.CommunityModel
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.ui.entrance.request.CommunityMsg_16
import com.lixin.amuseadjacent.app.ui.entrance.request.Community_17
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_perfect_image.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 完善个人形象
 * Created by Slingge on 2018/8/15
 */
class PerfectImageActivity : BaseActivity(), View.OnClickListener, DatePop.WheelViewCallBack2 {
    private var datePop: DatePop? = null
    private var commModel = UnityModel.unitModel()

    private var headerBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfect_image)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        iv_back.setOnClickListener(this)
        rl_community.setOnClickListener(this)
        rl_sex.setOnClickListener(this)
        tv_inCommunity.setOnClickListener(this)
        iv_header.setOnClickListener(this)

        tv_age.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_header -> {
                SelectPictureUtil.selectPicture(this, 1, 0, true)
            }
            R.id.rl_sex -> {
                SexDialog.dialog(this)
            }
            R.id.tv_age -> {
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(cl_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.rl_community -> {
                Community_17.getCommunity(this)
            }
            R.id.tv_inCommunity -> {//进入社区
                if (headerBitmap == null) {
                    ToastUtil.showToast("请选择头像")
                    return
                }
                val name = AbStrUtil.etTostr(et_name)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入您的名字")
                    return
                }
                var sex = -1
                if (TextUtils.isEmpty(AbStrUtil.tvTostr(tv_sex))) {
                    ToastUtil.showToast("请选择性别")
                    return
                }
                if (AbStrUtil.tvTostr(tv_sex) == "女") {
                    sex = 0
                } else {
                    sex = 1
                }

                val age = AbStrUtil.tvTostr(tv_age)
                if (TextUtils.isEmpty(age)) {
                    ToastUtil.showToast("请选择您的出生日期")
                    return
                }
                if (TextUtils.isEmpty(AbStrUtil.tvTostr(tv_community))) {
                    ToastUtil.showToast("请选择您的社区")
                    return
                }
                CommunityMsg_16.communitMsg(this, name, headerBitmap!!, sex, age, commModel)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            headerBitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].cutPath)
            iv_header.setImageBitmap(headerBitmap)
        }
    }


    //选择性别
    @Subscribe
    fun onEvent(sex: String) {
        tv_sex.text = sex
    }


    //选择社区
    @Subscribe
    fun onEvent(model: CommunityModel) {
        CommunityDialog.communityDialog(this, model.dataList)
    }

    //出生日期
    override fun position(position1: String, position2: String, position3: String) {
        tv_age.text = "$position1-$position2-$position3"
    }


    /**
     * 选中的社区、单元
     *  model.unitName//社区id
     * model.unitId//单元id
     */
    @Subscribe
    fun onEvent(model: UnityModel.unitModel) {
        commModel = model
        tv_community.text = model.communityName + model.unitName + model.num
    }


    override fun onDestroy() {
        super.onDestroy()
        if (headerBitmap != null) {
            headerBitmap!!.recycle()
            headerBitmap = null
        }
        CommunityDialog.dissCommunity()
        EventBus.getDefault().unregister(this)
    }
}