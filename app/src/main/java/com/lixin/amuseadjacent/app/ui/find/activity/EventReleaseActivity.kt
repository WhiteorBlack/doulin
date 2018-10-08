package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.DatePop
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_event_release.*
import java.util.ArrayList

/**
 * 发布活动
 * Created by Slingge on 2018/8/25.
 */
class EventReleaseActivity : BaseActivity(), AlbumAdapter.ImageRemoveCallback, TextWatcher
        , View.OnClickListener, DatePop.WheelViewCallBack2 {

    private var flag = 0//0开始时间，1结束时间，2截止时间

    private var albumAdapter: AlbumAdapter? = null
    private val imageList = ArrayList<LocalMedia>()
    private val maxNum = 9

    private var datePop: DatePop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_release)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("活动发布")

        val linearLayoutManager = GridLayoutManager(this, 3)

        rv_album.layoutManager = linearLayoutManager

        imageList.add(LocalMedia())

        albumAdapter = AlbumAdapter(this, imageList, maxNum, this)
        rv_album.adapter = albumAdapter

        et_details.addTextChangedListener(this)

        tv_startTime.setOnClickListener(this)
        tv_endTime.setOnClickListener(this)
        tv_signEnd.setOnClickListener(this)

        tv_send.setOnClickListener { v ->
            val title = AbStrUtil.etTostr(et_title)
            if (TextUtils.isEmpty(title)) {
                ToastUtil.showToast("请输入活动主题")
                return@setOnClickListener
            }
            val starTime = AbStrUtil.tvTostr(tv_startTime)
            if (TextUtils.isEmpty(starTime)) {
                ToastUtil.showToast("请选择活动开始时间")
                return@setOnClickListener
            }
            val endTime = AbStrUtil.tvTostr(tv_endTime)
            if (TextUtils.isEmpty(endTime)) {
                ToastUtil.showToast("请选择活动结束时间")
                return@setOnClickListener
            }

            if ((starTime.replace("-", "")).toInt() >= (endTime.replace("-", "")).toInt()) {
                ToastUtil.showToast("活动结束时间要晚于开始时间")
                return@setOnClickListener
            }
            val signTime = AbStrUtil.tvTostr(tv_signEnd)
            if (TextUtils.isEmpty(signTime)) {
                ToastUtil.showToast("请选择报名截止时间")
                return@setOnClickListener
            }
            if ((starTime.replace("-", "")).toInt() >= (signTime.replace("-", "")).toInt()) {
                ToastUtil.showToast("报名截止时间不能早于开始时间")
                return@setOnClickListener
            }
            if ((signTime.replace("-", "")).toInt() >= (endTime.replace("-", "")).toInt()) {
                ToastUtil.showToast("报名截止时间不能晚于结束时间")
                return@setOnClickListener
            }
            val address = AbStrUtil.etTostr(et_address)
            if (TextUtils.isEmpty(address)) {
                ToastUtil.showToast("请输入活动地点")
                return@setOnClickListener
            }

            var phone = ""
            if (sw_phone.isOpened) {
                phone = SharedPreferencesUtil.getSharePreStr(this, SharedPreferencesUtil.Phone)
            }
            val peoNum = AbStrUtil.etTostr(et_peoNum)
            if (TextUtils.isEmpty(peoNum)) {
                ToastUtil.showToast("请输入人数限制")
                return@setOnClickListener
            }
            val cost = AbStrUtil.etTostr(et_cost)
            if (TextUtils.isEmpty(cost)) {
                ToastUtil.showToast("请输入费用")
                return@setOnClickListener
            }
            val details = AbStrUtil.etTostr(et_details)
            if (TextUtils.isEmpty(details)) {
                ToastUtil.showToast("请输入活动详情")
                return@setOnClickListener
            }

            ProgressDialog.showDialog(this)
            Event_221222223224.EventEstablish(this, title, starTime, endTime, signTime, address, phone
                    , peoNum, cost, details, imageList)
        }


    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_startTime -> {
                flag = 0
                showDatePop()
            }
            R.id.tv_endTime -> {
                flag = 1
                showDatePop()
            }
            R.id.tv_signEnd -> {
                flag = 2
                showDatePop()
            }
        }
    }

    //出生日期
    override fun position(position1: String, position2: String, position3: String) {
        if (flag == 0) {
            tv_startTime.text = "$position1-$position2-$position3"
        } else if (flag == 1) {
            tv_endTime.text = "$position1-$position2-$position3"
        } else {
            tv_signEnd.text = "$position1-$position2-$position3"
        }
    }

    override fun afterTextChanged(s: Editable?) {
        val str = s.toString()
        if (TextUtils.isEmpty(str)) {
            return
        }
        tv_num.text = str.length.toString() + "/10000"
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }


    private fun showDatePop() {
        if (datePop == null) {
            datePop = DatePop(this, this)
        }
        if (!datePop!!.isShowing) {
            datePop!!.showAtLocation(ll_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
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