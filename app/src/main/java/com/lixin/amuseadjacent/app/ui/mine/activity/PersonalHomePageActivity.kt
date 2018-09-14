package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.ui.mine.fragment.DataFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.DesignerFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.InteractionFragment
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.model.UserInfoModel
import com.lixin.amuseadjacent.app.ui.mine.request.HomePage_110
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.nostra13.universalimageloader.core.ImageLoader
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

    private var auid = ""//与StaticUtil.uid相同查看自己的，不同别人的

    private var imgaeAdapter: ImageAdapter? = null
    private var imageList = ArrayList<String>()

    private var dataFragment: DataFragment? = null

    private var pageModel: HomePageModel? = null

    private var isAttention = ""// 0未关注 1已关注

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        } else {
            isAttention = intent.getStringExtra("isAttention")
            if (isAttention == "0") {//0未关注 1已关注
                tv_follow.text = "未关注"
            } else {
                tv_follow.text = "已关注"
            }
        }

        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("达人")
        tabList.add("互动")

        val list = ArrayList<Fragment>()
        dataFragment = DataFragment()
        list.add(dataFragment!!)

        val fragment2 = DynamicFragment()
        list.add(fragment2)

        val fragment3 = DesignerFragment()
        list.add(fragment3)

        val fragment4 = InteractionFragment()
        list.add(fragment4)


        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)


        val gridLayoutManager = GridLayoutManager(this, 4)
        rv_album.layoutManager = gridLayoutManager
        imgaeAdapter = ImageAdapter(this, imageList)
        rv_album.adapter = imgaeAdapter


        if (intent != null && intent.getIntExtra("position", -1) == 1) {
            viewPager.currentItem = intent.getIntExtra("position", 0)
        }

        tv_follow.setOnClickListener(this)
        tv_dialogue.setOnClickListener(this)
    }


    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        HomePage_110.userInfo(auid)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_follow -> {
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

        isAttention = model.icon

        tv_sex.text = model.age
        tv_constellation.text = model.constellation
        tv_address.text = model.communityName + model.unitName + model.doorNumber

        tv_name.text = model.nickname
        tv_effect.text = "影响力" + model.effectNum

        dataFragment!!.setDate(model)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}