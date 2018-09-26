package com.lixin.amuseadjacent.app.ui.message.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.dialog.ScreenFriendsPop
import com.lixin.amuseadjacent.app.ui.message.adapter.AddFeriendAdapter
import com.lixin.amuseadjacent.app.ui.message.model.CommunityUserModel
import com.lixin.amuseadjacent.app.ui.message.request.CommunityUser_20
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.PermissionUtil
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.zxing.activity.CaptureActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_addfriends.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.lixin.amuseadjacent.app.util.AbStrUtil


/**
 * Created by Slingge on 2018/8/16
 */
class AddFriendsActivity : BaseActivity(), View.OnClickListener {

    //打开扫描界面请求码
    private val REQUEST_CODE = 0x01
    //扫描成功返回码
    private val RESULT_OK = 0xA1

    private var addAdapter: AddFeriendAdapter? = null
    private var userList = ArrayList<CommunityUserModel.userModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var sex = ""//搜索性别
    private var search = ""//搜索内容

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfriends)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("添加好友")
        StatusBarWhiteColor()

        tv_screen.setOnClickListener(this)
        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_scan)
        iv_right.setOnClickListener(this)

        val gridlineam = GridLayoutManager(this, 3)
        rv_user.layoutManager = gridlineam

        addAdapter = AddFeriendAdapter(this, userList)
        rv_user.adapter = addAdapter

        rv_user.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (userList.isNotEmpty()) {
                    userList.clear()
                    addAdapter!!.notifyDataSetChanged()
                }
                CommunityUser_20.user(sex, search, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    rv_user.noMoreLoading()
                    return
                }
                onRefresh = 2
                CommunityUser_20.user(sex, search, nowPage)
            }
        })

        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                search = AbStrUtil.etTostr(et_search)
                if (userList.isNotEmpty()) {
                    userList.clear()
                    addAdapter!!.notifyDataSetChanged()
                }
                CommunityUser_20.user(sex, search, nowPage)

                // 搜索功能
                return@OnEditorActionListener true
            }
            false
        })

        ProgressDialog.showDialog(this)
        CommunityUser_20.user(sex, search, nowPage)
    }


    @Subscribe
    fun onEvent(model: CommunityUserModel) {
        totalPage = model.totalPage
        userList.addAll(model.dataList)

        if (onRefresh == 1) {
            rv_user.refreshComplete()
        } else if (onRefresh == 2) {
            rv_user.loadMoreComplete()
        }

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_user.layoutAnimation = controller
        addAdapter!!.notifyDataSetChanged()
        rv_user.scheduleLayoutAnimation()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_screen -> {
                ScreenFriendsPop.screenDialog(this, tv_screen, object : ScreenFriendsPop.ScreenCallBack {
                    override fun screen(flag: String) {
                        sex = flag
                        if (userList.isNotEmpty()) {
                            userList.clear()
                            addAdapter!!.notifyDataSetChanged()
                        }
                        nowPage = 1
                        CommunityUser_20.user(sex, search, nowPage)
                    }
                })
            }
            R.id.iv_right -> {
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
            val scanResult = data.extras!!.getString("qr_scan_result")
            val bundle = Bundle()
            bundle.putString("auid", scanResult)
            bundle.putString("isAttention", "0")
            MyApplication.openActivity(this, PersonalHomePageActivity::class.java, bundle)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}