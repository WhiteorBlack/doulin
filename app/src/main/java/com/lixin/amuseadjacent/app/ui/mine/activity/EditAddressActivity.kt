package com.lixin.amuseadjacent.app.ui.mine.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.AddressPop
import com.lixin.amuseadjacent.app.ui.dialog.CityModel
import com.lixin.amuseadjacent.app.ui.mine.model.AddressModel
import com.lixin.amuseadjacent.app.ui.mine.request.Address_140141142143
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.AppJsonFileReader
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_edit_address.*
import java.util.ArrayList

/**
 * 编辑地址
 * Created by Slingge on 2018/9/4
 */
class EditAddressActivity : BaseActivity(), View.OnClickListener, AddressPop.WheelViewCallBack2 {

    private var flag = -1//0新增地址，1修改地址

    private var addressPop: AddressPop? = null
    private var cityList: List<CityModel> = ArrayList()//全国城市

    private var addId = ""//地址id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        init()
    }

    override fun onStart() {
        super.onStart()
        if (flag == 1) {
            val mode = intent.getSerializableExtra("DynamiclModel") as AddressModel.addModel
            addId = mode.addressId
            et_name.setText(mode.username)
            et_phone.setText(mode.userPhone)
            et_address.setText(mode.address)
            sv_default.isOpened = mode.isdefault != "0"
            tv_region.text=mode.city
        }
    }

    private fun init() {
        StatusBarWhiteColor()
        flag = intent.getIntExtra("flag", 0)
        if (flag == 0) {
            inittitle("新增收货地址")
        } else {
            inittitle("修改收货地址")
        }

        tv_region.setOnClickListener(this)
        tv_save.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_region -> {
                if (cityList.isEmpty()) {
                    cityList = Gson().fromJson(AppJsonFileReader.getJsons(this, 0), object : TypeToken<List<CityModel>>() {
                    }.type)
                }
                if (addressPop == null) {
                    addressPop = AddressPop(this, cityList, this)
                }
                if (!addressPop!!.isShowing) {
                    addressPop!!.showAtLocation(cl_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_save -> {
                val name = AbStrUtil.etTostr(et_name)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入收货人姓名")
                    return
                }
                val phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入收货人手机号码")
                    return
                }

                val city = AbStrUtil.tvTostr(tv_region)
                if (TextUtils.isEmpty(city)) {
                    ToastUtil.showToast("请选择所在地区")
                    return
                }

                val address = AbStrUtil.etTostr(et_address)
                if (TextUtils.isEmpty(address)) {
                    ToastUtil.showToast("请输入收货人详细地址")
                    return
                }

                val isDefault: String
                if (sv_default.isOpened) {// 0非默认，1默认
                    isDefault = "1"
                } else {
                    isDefault = "0"
                }
                Address_140141142143.editAddress(this, flag, addId, name, phone, city, address, isDefault)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun position(position1: Int, position2: Int, position3: Int) {
        tv_region.text = cityList[position1].areaName +
                cityList[position1].cities!![position2].areaName +
                cityList[position1].cities!![position2].counties!![position3].areaName
    }


}


