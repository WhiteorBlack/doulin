package com.lixin.amuseadjacent.app.ui.message.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ScreenFriendsPop
import com.lixin.amuseadjacent.app.ui.message.adapter.AddFeriendAdapter
import com.lixin.amuseadjacent.app.util.PermissionUtil
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.zxing.activity.CaptureActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_addfriends.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/8/16
 */
class AddFriendsActivity : BaseActivity(), View.OnClickListener {

    //打开扫描界面请求码
    private val REQUEST_CODE = 0x01
    //扫描成功返回码
    private val RESULT_OK = 0xA1

    private var addAdapter: AddFeriendAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfriends)
        init()
    }


    private fun init() {
        inittitle("添加好友")
        StatusBarWhiteColor()

        tv_screen.setOnClickListener(this)
        iv_setting.visibility = View.VISIBLE
        iv_setting.setImageResource(R.drawable.ic_scan)
        iv_setting.setOnClickListener(this)

        val gridlineam = GridLayoutManager(this, 3)
        rv_user.layoutManager = gridlineam

        addAdapter = AddFeriendAdapter(this)
        rv_user.adapter = addAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_user.layoutAnimation = controller
        addAdapter!!.notifyDataSetChanged()
        rv_user.scheduleLayoutAnimation()

        rv_user.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_user) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val position = vh!!.adapterPosition
                ToastUtil.showToast(position.toString())
                if (position < 0) {
                    return
                }
                MyApplication.openActivity(this@AddFriendsActivity, PersonalHomePageActivity::class.java)
            }

        })
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_screen -> {
                ScreenFriendsPop.screenDialog(this, tv_screen, object : ScreenFriendsPop.ScreenCallBack {
                    override fun screen(flag: Int) {
                        ToastUtil.showToast(flag.toString())
                    }
                })
            }
            R.id.iv_setting -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {//请求权限
                    val intent = Intent(this, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            val intent = Intent(this, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            val bundle = data.extras
            val scanResult = bundle!!.getString("qr_scan_result")
            ToastUtil.showToast(scanResult)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}