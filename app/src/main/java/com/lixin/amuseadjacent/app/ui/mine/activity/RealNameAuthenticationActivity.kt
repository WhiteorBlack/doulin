package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.request.RealnameAuthentication_126
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.SMSVerificationCode
import com.lixin.amuseadjacent.app.util.TimerUtil
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_realname_authentication.*

/**
 * Created by Slingge on 2018/9/3
 */
class RealNameAuthenticationActivity : BaseActivity(), View.OnClickListener {

    private var VCode: String? = null
    private var timerUtil: TimerUtil? = null

    private var bitmap: Bitmap? = null
    private var justPath = ""
    private var backPath = ""

    private var isBack: Boolean? = null//true 正面，fasle 反面

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realname_authentication)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        timerUtil = TimerUtil(get_verification)

        get_verification.setOnClickListener(this)
        id_just.setOnClickListener(this)
        id_back.setOnClickListener(this)
        tv_enter.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.id_just -> {//正面
                isBack = true
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.id_back -> {//反面
                isBack = false
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.get_verification -> {
                var phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }
                VCode = timerUtil!!.num
                SMSVerificationCode.sendSMS(this, phone, VCode!!)
                timerUtil!!.timersStart()
            }
            R.id.tv_enter -> {//

                val name = AbStrUtil.etTostr(et_name)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入身份证姓名")
                    return
                }

                val num = AbStrUtil.etTostr(et_num)
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.showToast("请输入身份证号码")
                    return
                }

                val phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }

                val code = AbStrUtil.etTostr(et_verification)
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }
                if (VCode != code) {
                    ToastUtil.showToast("验证码错误")
                    return
                }

                if (justPath == "") {
                    ToastUtil.showToast("请选择身份证正面照")
                    return
                }
                if (backPath == "") {
                    ToastUtil.showToast("请选择身份证正面照")
                    return
                }

                ProgressDialog.showDialog(this)
                RealnameAuthentication_126.authentication(this, name, num, phone, justPath, backPath)

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
            bitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].compressPath)
            if (isBack!!) {
                justPath = PictureSelector.obtainMultipleResult(data)[0].path
                id_just.setImageBitmap(bitmap)
                id_just.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                backPath = PictureSelector.obtainMultipleResult(data)[0].path
                id_back.setImageBitmap(bitmap)
                id_just.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (bitmap != null) {
            bitmap!!.recycle()
            bitmap = null
        }
    }

}