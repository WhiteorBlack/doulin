package com.lixin.amuseadjacent.app.ui.find.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.PermissionHelper
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_selection_address.*
import kotlinx.android.synthetic.main.include_basetop.*
import com.baidu.mapapi.search.core.RouteNode.location
import com.baidu.location.Poi
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.search.poi.PoiCitySearchOption
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.entrance.ForgetPassActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.AddressAdapter
import com.netease.nim.uikit.common.ui.recyclerview.listener.OnItemClickListener
import kotlinx.android.synthetic.main.layout.*


/**
 * 动态发布选择地址
 * Created by Slingge on 2018/8/25.
 */
class SelectionAddressActivity : BaseActivity() ,View.OnClickListener{
    private var mLocationClient: LocationClient? = null
    private val location = MyLocation()
    private var geoCoder: GeoCoder? = null
    private var regeocodeResult: ReverseGeoCodeResult? = null
    private var adapter: AddressAdapter? = null
    private var city:String? = null
    private var isChecked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_address)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("选择位置")

        if (intent != null){
            isChecked = intent.extras.getBoolean("isChecked")
        }

        if (isChecked){
            cb_address.isChecked = true
        }else{
            cb_address.isChecked = false
        }

        cb_address.setOnClickListener(this)

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_search1)
        iv_right.setOnClickListener { v ->
            var bundle = Bundle()
            if (city == null){
                return@setOnClickListener
            }
            bundle.putString("city",city)
            MyApplication.openActivityForResult(this, SearchAddressActivity::class.java,bundle,200)
        }

        adapter = AddressAdapter(this)
        rv_address.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        rv_address.adapter = adapter
        adapter!!.setOnClickListener(object:AddressAdapter.OnItemClickListener{
            override fun itemClick(address: String) {
                val intent = Intent()
                intent.putExtra("address",address)
                setResult(200,intent)
                finish()
            }
        })
        checkPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200 && resultCode == 200 && data != null){
            val intent = Intent()
            intent.putExtra("address",data.getStringExtra("address"))
            setResult(200,intent)
            finish()
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.cb_address -> {
                val intent = Intent()
                intent.putExtra("address","")
                setResult(200,intent)
                finish()
            }
        }
    }

    /**
     * 检查权限
     */
    private fun checkPermission() {
        val helper= PermissionHelper(this)
        helper.requestPermissions(object:PermissionHelper.PermissionListener{
            override fun doAfterGrand(vararg permission: String?) {
                initMap()
            }

            override fun doAfterDenied(vararg permission: String?) {
            }

        }, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)
    }

    private fun initMap(){
        geoCoder = GeoCoder.newInstance()
        geoCoder!!.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult) {}

            override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult) {
                this@SelectionAddressActivity.regeocodeResult = reverseGeoCodeResult
                if (reverseGeoCodeResult != null){
                    adapter!!.refresh(reverseGeoCodeResult)
                }else{
                    ToastUtil.showToast("请重新定位")
                }
            }
        })
        initMyLocation()
    }
    private fun initMyLocation() {
        mLocationClient = LocationClient(applicationContext)
        //声明LocationClient类
        mLocationClient!!.registerLocationListener(location)
        val option = LocationClientOption()

        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        //获取位置信息必须要
        option.setIsNeedAddress(true)
        option.setIsNeedLocationPoiList(true)
        option.setIsNeedLocationDescribe(true)
        option.isOpenGps = true
        option.isLocationNotify = true
        option.setIgnoreKillProcess(false)
        option.SetIgnoreCacheException(false)
        option.setWifiCacheTimeOut(5 * 60 * 1000)
        option.setEnableSimulateGps(false)
        mLocationClient!!.setLocOption(option)

        mLocationClient!!.start()
    }

    private inner class MyLocation : BDAbstractLocationListener() {

        override fun onReceiveLocation(location: BDLocation?) {
            if (location != null) {
                mLocationClient!!.stop()
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                val code = location.locType
                if (code == BDLocation.TypeNetWorkLocation || code == BDLocation.TypeOffLineLocation || code == BDLocation.TypeGpsLocation) {
                    val latitude = location.latitude    //获取纬度信息
                    val longitude = location.longitude    //获取经度信息
                    val radius = location.radius    //获取定位精度，默认值为0.0f
                    city = location.city
                    val latLng = LatLng(latitude, longitude)
                    geoCoder!!.reverseGeoCode(ReverseGeoCodeOption().location(latLng))
                } else {
                    ToastUtil.showToast("定位失败,请检查网络情况与位置权限")
                }
            }
        }
    }

}