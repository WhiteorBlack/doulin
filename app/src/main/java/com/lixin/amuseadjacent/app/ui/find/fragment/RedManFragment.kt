package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.RedManAdapter
import com.lixin.amuseadjacent.app.ui.find.model.RedmanModel
import com.lixin.amuseadjacent.app.ui.find.request.Redman_211
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.fragment_redman_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 红人榜
 * Created by Slingge on 2018/8/30
 */
class RedManFragment : BaseFragment(), View.OnClickListener, RedManAdapter.FollowCallBack,
        Redman_211.RedManCallBack {


    private var flag = -1

    private var linearLayoutManager: LinearLayoutManager? = null
    private var redManAdapter: RedManAdapter? = null
    private var redmanList = ArrayList<RedmanModel.dataModel>()

    private var auid0 = ""//第一名uid
    private var auid1 = ""//第二名uid
    private var auid2 = ""//第三名uid
    private var isAttention0 = ""
    private var isAttention1 = ""
    private var isAttention2 = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_redman_list, container, false)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_follow_center.setOnClickListener(this)
        tv_follow_left.setOnClickListener(this)
        tv_follow_right.setOnClickListener(this)

        ic_header_center.setOnClickListener(this)
        ic_header_left.setOnClickListener(this)
        ic_header_right.setOnClickListener(this)

        rv_radman.layoutManager = linearLayoutManager

        rv_radman.isFocusable = false
        redManAdapter = RedManAdapter(activity!!, redmanList, this)
        rv_radman.adapter = redManAdapter
    }

    private fun init() {
        flag = arguments!!.getInt("flag")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }


    private fun top3(model: RedmanModel.dataModel, ic_header_center: CircleImageView, tv_name_center: TextView,
                     tv_effect_center: TextView, tv_follow_center: TextView) {
        ImageLoader.getInstance().displayImage(model.userImg, ic_header_center, ImageLoaderUtil.HeaderDIO())
        tv_name_center.text = model.userName
        tv_effect_center.text = "影响力" + model.userEffectNum

        if (model.userId == StaticUtil.uid) {
            tv_follow_center.visibility = View.INVISIBLE
        } else {
            tv_follow_center.visibility = View.VISIBLE
            if (model.isAttention == "0") {// 0未关注 1已关注
                tv_follow_center.text = "+ 关注"
                tv_follow_center.visibility = View.VISIBLE
            } else {
                tv_follow_center.text = "已关注"
                tv_follow_center.visibility = View.INVISIBLE
            }
        }
    }


    private fun topFollow(i: Int, topFollow: TextView?) {
        ProgressDialog.showDialog(activity!!)
        Mail_138139.follow(redmanList[i].userId, object : Mail_138139.FollowCallBack {
            override fun follow() {
                if (redmanList[i].isAttention == "0") {// 0未关注 1已关注
                    redmanList[i].isAttention = "1"
                    if (topFollow != null) {
                        topFollow.text = "已关注"
                    }
                } else {
                    redmanList[i].isAttention = "0"
                    if (topFollow != null) {
                        topFollow.text = "+ 关注"
                    }
                }
                redManAdapter!!.notifyDataSetChanged()
                if (i == 0) {
                    isAttention0 = redmanList[i].isAttention
                } else if (i == 1) {
                    isAttention1 = redmanList[i].isAttention
                } else if (i == 2) {
                    isAttention2 = redmanList[i].isAttention
                }
            }
        })
    }


    override fun follow(i: Int) {
        if (i == 0) {
            topFollow(0, tv_follow_center)
        } else if (i == 1) {
            topFollow(1, tv_follow_left)
        } else if (i == 2) {
            topFollow(2, tv_follow_right)
        } else {
            topFollow(i, null)
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_follow_center -> {
                topFollow(0, tv_follow_center)
            }
            R.id.tv_follow_left -> {
                topFollow(1, tv_follow_left)
            }
            R.id.tv_follow_right -> {
                topFollow(2, tv_follow_right)
            }
            R.id.ic_header_center -> {
                if (TextUtils.isEmpty(auid0)) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("auid", auid0)
                bundle.putString("isAttention", isAttention0)
                MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
            }
            R.id.ic_header_left -> {
                if (TextUtils.isEmpty(auid1)) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("auid", auid1)
                bundle.putString("isAttention", isAttention1)
                MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
            }
            R.id.ic_header_right -> {
                if (TextUtils.isEmpty(auid2)) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("auid", auid2)
                bundle.putString("isAttention", isAttention2)
                MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
            }
        }
    }

    override fun redMan(model: RedmanModel) {
        if (!isViewInitiated) {
            return
        }

        redmanList.addAll(model.dataList)

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_radman.layoutAnimation = controller
        redManAdapter!!.notifyDataSetChanged()
        rv_radman.scheduleLayoutAnimation()

        if (redmanList.size >= 3) {
            top3(model.dataList[0], ic_header_center, tv_name_center, tv_effect_center, tv_follow_center)
            top3(model.dataList[1], ic_header_left, tv_name_left, tv_effect_left, tv_follow_left)
            top3(model.dataList[2], ic_header_right, tv_name_right, tv_effect_right, tv_follow_right)
            auid0 = model.dataList[0].userId
            auid1 = model.dataList[1].userId
            auid2 = model.dataList[2].userId
            isAttention0 = model.dataList[0].isAttention
            isAttention1 = model.dataList[1].isAttention
            isAttention2 = model.dataList[2].isAttention
        } else if (redmanList.size == 2) {
            top3(model.dataList[0], ic_header_center, tv_name_center, tv_effect_center, tv_follow_center)
            top3(model.dataList[1], ic_header_left, tv_name_left, tv_effect_left, tv_follow_left)
            auid0 = model.dataList[0].userId
            auid1 = model.dataList[1].userId
            isAttention0 = model.dataList[0].isAttention
            isAttention1 = model.dataList[1].isAttention
        } else if (redmanList.size == 1) {
            top3(model.dataList[0], ic_header_center, tv_name_center, tv_effect_center, tv_follow_center)
            auid0 = model.dataList[0].userId
            isAttention0 = model.dataList[0].isAttention
        }
    }

    override fun loadData() {
        ProgressDialog.showDialog(activity!!)
        if (redmanList.isNotEmpty()) {
            redmanList.clear()
            redManAdapter!!.notifyDataSetChanged()
        }
        Redman_211.redList(flag, this)
    }




}