package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.jiguang.d.c.s
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.ui.mine.fragment.DataFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.TalentFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.InteractionFragment
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.request.EditeNote_167
import com.lixin.amuseadjacent.app.ui.mine.request.HomePage_110
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.netease.nim.uikit.api.NimUIKit
import com.nostra13.universalimageloader.core.ImageLoader
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import kotlinx.android.synthetic.main.activity_personal_home_page.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 个人主页
 * Created by Slingge on 2018/8/16
 */
class PersonalHomePageActivity : BaseActivity(), View.OnClickListener {

    var auid = ""//与StaticUtil.uid相同查看自己的，不同别人的

    private var imgaeAdapter: ImageAdapter? = null
    private var imageList = ArrayList<String>()

    private var dataFragment: DataFragment? = null

    private var pageModel: HomePageModel? = null

    private var isAttention = ""// 0未关注 1已关注

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)//禁止软禁盘顶起对话、关注控件
        setContentView(R.layout.activity_personal_home_page)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("个人主页")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        auid = intent.getStringExtra("auid")

        if (auid == StaticUtil.uid) {
            iv_right.visibility = View.VISIBLE
            iv_right.setImageResource(R.drawable.ic_edit2)
            iv_right.setOnClickListener { v ->
                MyApplication.openActivity(this, PersonalDataActivity::class.java)
            }
            cl_3.visibility = View.GONE

            note.visibility = View.GONE
            et_note.visibility = View.GONE
        }

        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("达人")
        tabList.add("互动")

        val list = ArrayList<Fragment>()
        dataFragment = DataFragment()
        list.add(dataFragment!!)

        val bundle = Bundle()
        bundle.putString("auid", auid)

        val fragment2 = DynamicFragment()
        fragment2.arguments = bundle
        list.add(fragment2)

        val fragment3 = TalentFragment()
        fragment3.arguments = bundle
        list.add(fragment3)

        val fragment4 = InteractionFragment()
        fragment4.arguments = bundle
        list.add(fragment4)


        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tab.setupWithViewPager(viewPager)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                JCVideoPlayer.releaseAllVideos()
            }
        })


        val gridLayoutManager = GridLayoutManager(this, 4)
        rv_album.layoutManager = gridLayoutManager
        imgaeAdapter = ImageAdapter(this, imageList, 0)
        rv_album.adapter = imgaeAdapter


        if (intent != null && intent.getIntExtra("position", -1) == 1) {
            viewPager.currentItem = intent.getIntExtra("position", 0)
        }

        tv_follow.setOnClickListener(this)
        tv_dialogue.setOnClickListener(this)

        et_note.setOnEditorActionListener(TextView.OnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH || p1 == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val keytag = AbStrUtil.etTostr(et_note)
                // 搜索功能
                EditeNote_167.note(auid, keytag)
                return@OnEditorActionListener true
            }
            false
        })
    }


    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        HomePage_110.userInfo(auid)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_follow -> {
                ProgressDialog.showDialog(this)
                Mail_138139.follow(auid, object : Mail_138139.FollowCallBack {
                    override fun follow() {
                        if (isAttention == "0") {//0未关注 1已关注
                            isAttention = "1"
                            tv_follow.text = "已关注"
                        } else {
                            isAttention = "0"
                            tv_follow.text = "未关注"
                        }
                    }
                })
            }
            R.id.tv_dialogue -> {
                NimUIKit.startP2PSession(this, auid)
            }
        }
    }


    @Subscribe
    fun onEvent(model: HomePageModel) {
        pageModel = model
        if (!imageList.isEmpty()) {
            imageList.clear()
        }
        for (i in 0 until model.albumList.size) {
            imageList.add(model.albumList[i].imgUrl)
        }
        imgaeAdapter!!.notifyDataSetChanged()

        if (model.sex == "0") {//女
            tv_sex.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_girl, tv_sex, 3)
        } else {
            tv_sex.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_boy, tv_sex, 3)
        }

        ImageLoader.getInstance().displayImage(model.icon, iv_header)

        isAttention = model.isAttention

        if (isAttention == "0") {//0未关注 1已关注
            tv_follow.text = "未关注"
        } else {
            tv_follow.text = "已关注"
        }

        tv_sex.text = model.age
        tv_constellation.text = model.constellation
        tv_address.text = model.communityName
        et_note.setText(model.remarks)
        tv_name.text = model.nickname
        tv_effect.text = "影响力" + model.effectNum

        dataFragment!!.setDate(model)
    }


    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) return
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}