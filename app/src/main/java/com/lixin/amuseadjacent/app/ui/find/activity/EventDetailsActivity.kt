package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.EventDetailsModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.util.*
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 活动详情
 * Created by Slingge on 2018/8/25.
 */
class EventDetailsActivity : BaseActivity(), View.OnClickListener {

    private var commentAdapter: DynamicCommentAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var eventModel = EventDetailsModel()

    private var eventId = ""// 活动id


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle(intent.getStringExtra("name"))
        eventId = intent.getStringExtra("id")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "收藏"
        tv_right.setOnClickListener(this)

        tv_sign.setOnClickListener(this)
        iv_signInfo.setOnClickListener(this)
        tv_send.setOnClickListener(this)

        image.setOnClickListener(this)

        tv_info.setTextColor(resources.getColor(R.color.colorTabText))
        tv_info.setTextSize(15)
        tv_info.setTextMaxLine(2)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager
        rv_comment.setPullRefreshEnabled(false)
        rv_comment.setLoadingMoreEnabled(false)

        commentAdapter = DynamicCommentAdapter(this, commentList)
        rv_comment.adapter = commentAdapter
        rv_comment.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_comment) {
            override fun onItemClick(vh: RecyclerView.ViewHolder) {
//                if (eventModel.`object`.issignup == "0") {//未报名
//                    ToastUtil.showToast("请先报名")
//                    return
//                }
                val i = vh.adapterPosition
                if (i < 0 || i >= commentList.size) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("id", eventId)
                bundle.putSerializable("model", eventModel.commList[i])
                MyApplication.openActivity(this@EventDetailsActivity, EventDetailsReplyActivity::class.java, bundle)
            }
        })

        ProgressDialog.showDialog(this)
        Event_221222223224.EventDetails(eventId)
    }

    @Subscribe
    fun onEvent(model: EventDetailsModel) {
        eventModel = model
        tv_info.setText(model.`object`.activityDesc)
        if (tv_info.line() >= 2) {
            iv_open.setOnClickListener(this)
            iv_open.visibility = View.VISIBLE
        } else {
            iv_open.visibility = View.INVISIBLE
        }

        if (model.`object`.activityState == "0") {//0报名中 1进行中 2已结束
            tv_type.text = "活动报名中"
        } else if (model.`object`.activityState == "1") {
            tv_type.text = "活动进行中"
        } else {
            tv_type.text = "活动已结束"
        }

        if (model.`object`.activityImg.isNotEmpty()) {
            ImageLoader.getInstance().displayImage(model.`object`.activityImg[0], image)
        }

        if (model.`object`.issignup == "0") {// 0未报名 1已报名
            tv_sign.visibility = View.VISIBLE
            rl_3.visibility = View.GONE
        } else {
            tv_sign.visibility = View.GONE
            rl_3.visibility = View.VISIBLE
        }
        if (model.`object`.iscang == "1") {//0未收藏 1已收藏
            tv_right.visibility = View.GONE
        }
        tv_startTime.text = model.`object`.activityStarttime
        tv_endTime.text = model.`object`.activityEndtime
        tv_signEnd.text = model.`object`.activitySignEndtime
        tv_address.text = model.`object`.activityAddress
        tv_phone.text = model.`object`.activityPhone
        tv_initiator.text = model.`object`.userName
        tv_peoNum.text = model.`object`.activityAllnum + "人"
        tv_cost.text = model.`object`.activityMoney + "元"
        tv_signInfo.text = "（" + model.`object`.activityNownum + "/" + model.`object`.activityAllnum + "）"
        tv_initiator.text = model.`object`.userName

        tv_time.text = model.`object`.time
        tv_zan.text = model.`object`.zanNum
        tv_comment.text = model.`object`.commentNum

        if (model.`object`.isZan == "1") {//0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan, tv_zan, 5)
        }
        if (model.`object`.iscang === "0") {//没有收藏
            tv_right.text = "已收藏"
            model.`object`.iscang = "1"
        } else {
            tv_right.text = "收藏"
            model.`object`.iscang = "0"
        }

        val inflater = LayoutInflater.from(this)

        var maxNun = 7
        if (model.signList.size < 7) {
            maxNun = model.signList.size
        }

        for (i in 0 until maxNun) {
            val imageView = inflater.inflate(R.layout.item_praise, pl_header, false) as CircleImageView
            ImageLoader.getInstance().displayImage(model.signList[i].userImg, imageView)
            pl_header.addView(imageView)
        }

        commentList.addAll(model.commList)
        commentAdapter!!.notifyDataSetChanged()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_right -> {//收藏
                ProgressDialog.showDialog(this)
                Event_221222223224.EventCollect("1", eventId, object : Event_221222223224.CollectCallBack {
                    override fun collect() {
                        if (eventModel.`object`.iscang === "0") {//没有收藏
                            tv_right.text = "已收藏"
                            eventModel.`object`.iscang = "1"
                        } else {
                            tv_right.text = "收藏"
                            eventModel.`object`.iscang = "0"
                        }
                    }
                })
            }
            R.id.tv_send->{
                val content = AbStrUtil.etTostr(et_comment)
                if (TextUtils.isEmpty(content)) {
                    return
                }
                ProgressDialog.showDialog(this)
                ActivityComment_272829210.comment("0",eventId,"", content, object : ActivityComment_272829210.CommentCallBack {
                    override fun commemt() {
                        et_comment.setText("")
                        val model = ActivityCommentModel1.commModel()
                        model.commentContent = content
                        model.commentIcon = StaticUtil.headerUrl
                        model.commentTime = GetDateTimeUtil.getYMDHMS()
                        model.secondNum = "0"
                        model.commentName = StaticUtil.nickName
                        model.zanNum = "0"
                        commentList.add(0, model)
                        commentAdapter!!.notifyDataSetChanged()
                    }
                })
            }
            R.id.image -> {
                PreviewPhoto.preview(this, eventModel.`object`.activityImg, 0)
            }
            R.id.iv_open -> {
                tv_info.switchs()
                iv_open.visibility = View.INVISIBLE
            }
            R.id.tv_sign -> {//报名
                if (eventModel.`object`.activityState != "") {
                    ToastUtil.showToast("报名已结束")
                    return
                }
                val bundle = Bundle()
                bundle.putString("id", eventId)
                MyApplication.openActivityForResult(this, EventSginUpActivity::class.java, bundle, 0)
            }
            R.id.iv_signInfo -> {//报名列表
                val bundle = Bundle()
                bundle.putSerializable("list", eventModel.signList)
                MyApplication.openActivity(this, EventEnteredActivity::class.java, bundle)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {//报名成功
            tv_sign.visibility = View.GONE
            rl_3.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}