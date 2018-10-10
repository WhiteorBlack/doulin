package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.EventDetailsModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.mine.activity.FeedbackActivity
import com.lixin.amuseadjacent.app.util.*
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * 活动详情
 * Created by Slingge on 2018/8/25.
 */
class EventDetailsActivity : BaseActivity(), View.OnClickListener {

    private var commentAdapter: DynamicCommentAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var eventModel = EventDetailsModel()

    private var eventId = ""// 活动id

    private var imageList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AndroidBug5497Workaround.assistActivity(this)//底部EditText不能被软键盘顶起
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
        temp_comment.setOnClickListener(this)
        pl_header.setOnClickListener(this)
        tv_zan.setOnClickListener(this)

        tv_info.setTextColor(resources.getColor(R.color.black))


        banner.setOnBannerListener { i ->
            PreviewPhoto.preview(this, imageList, i)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager
        rv_comment.setPullRefreshEnabled(false)
        rv_comment.setLoadingMoreEnabled(false)

        commentAdapter = DynamicCommentAdapter(this, commentList)
        rv_comment.adapter = commentAdapter
        commentAdapter!!.setId(eventId, "1")

        ProgressDialog.showDialog(this)
        Event_221222223224.EventDetails(eventId)
    }

    @Subscribe
    fun onEvent(model: EventDetailsModel) {
        eventModel = model
        tv_info.text = model.`object`.activityDesc

        if (model.`object`.activityState == "0") {//0报名中 1进行中 2已结束
            tv_type.text = "活动报名中"
        } else if (model.`object`.activityState == "1") {
            tv_type.text = "活动进行中"
        } else {
            tv_type.text = "活动已结束"
        }

        imageList = model.`object`.activityImgurl
        banner.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        if (model.`object`.issignup == "1" || StaticUtil.uid == model.`object`.userid) {// 0未报名 1已报名
            tv_sign.visibility = View.GONE
            rl_3.visibility = View.VISIBLE
        } else {
            tv_sign.visibility = View.VISIBLE
            rl_3.visibility = View.GONE
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
        tv_cost.text ="￥" +model.`object`.activityMoney + "/人"
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

        var maxNun = 10
        if (model.signList != null) {
            if(model.signList.size < 10){
                maxNun = model.signList.size
            }
            pl_header.setAvatarListListener { imageViewList ->
                imageViewList.reverse()
                for (i in 0 until maxNun) {
                    ImageLoader.getInstance().displayImage(model.signList[i].userImg, imageViewList!![i])
                    imageViewList[i].visibility = View.VISIBLE
                }
            }
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
            R.id.tv_send -> {
                val content = AbStrUtil.etTostr(et_comment)
                if (TextUtils.isEmpty(content)) {
                    return
                }
                ProgressDialog.showDialog(this)
                ActivityComment_272829210.comment("0", eventId, "", content, object : ActivityComment_272829210.CommentCallBack {
                    override fun commemt(commentId: String) {
                        et_comment.setText("")
                        val model = ActivityCommentModel1.commModel()
                        model.commentContent = content
                        model.commentIcon = StaticUtil.headerUrl
                        model.commentTime = GetDateTimeUtil.getYMDHMS()
                        model.secondNum = "0"
                        model.commentName = StaticUtil.nickName
                        model.zanNum = "0"
                        model.commentUid = StaticUtil.uid
                        model.commentId = commentId
                        commentList.add(0, model)
                        commentAdapter!!.notifyDataSetChanged()
                    }
                })
            }
            R.id.image -> {
                PreviewPhoto.preview(this, eventModel.`object`.activityImgurl, 0)
            }
            R.id.tv_zan -> {//赞
                ActivityComment_272829210.zan("0", eventModel.`object`.activityId, "", object : Find_26.ZanCallback {
                    override fun zan() {
                        if (eventModel.`object`.isZan == "1") {
                            eventModel.`object`.isZan = "0"
                            tv_zan.text = (eventModel.`object`.zanNum.toInt() - 1).toString()
                            AbStrUtil.setDrawableLeft(this@EventDetailsActivity, R.drawable.ic_zan, tv_zan, 5)
                        } else {
                            AbStrUtil.setDrawableLeft(this@EventDetailsActivity, R.drawable.ic_zan_hl, tv_zan, 5)
                            tv_zan.text = (eventModel.`object`.zanNum.toInt() + 1).toString()
                            eventModel.`object`.isZan = "1"
                        }

                    }
                })

            }
            R.id.temp_comment -> {//评论
                val bundle = Bundle()
                bundle.putString("id", eventModel.`object`.activityId)
                MyApplication.openActivityForResult(this, FeedbackActivity::class.java, bundle, 1)
            }
            R.id.tv_sign -> {//报名
                if (eventModel.`object`.activityState != "0") {
                    ToastUtil.showToast("报名已结束")
                    return
                }
                val bundle = Bundle()
                bundle.putString("id", eventId)
                MyApplication.openActivityForResult(this, EventSginUpActivity::class.java, bundle, 0)
            }
            R.id.iv_signInfo, R.id.pl_header -> {//报名列表
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
        } else if (requestCode == 1) {//评论
            val content = data.getStringExtra("content")
            val commentId = data.getStringExtra("id")
            val model = ActivityCommentModel1.commModel()
            model.commentContent = content
            model.commentIcon = StaticUtil.headerUrl
            model.commentId = commentId
            model.commentName = StaticUtil.nickName
            model.commentTime = GetDateTimeUtil.getYMDHMS()
            model.commentUid = StaticUtil.uid
            model.secondNum = "0"
            model.zanNum = "0"
            model.isZan = "0"
            commentList.add(0, model)
            commentAdapter!!.notifyDataSetChanged()
            commentAdapter
        } else if (requestCode == 303) {//二级回复
            if (data.getStringExtra("type") == "del") {
                commentList.removeAt(data.getIntExtra("position", 0))
            } else {
                commentList[data.getIntExtra("position", 0)] = data.getSerializableExtra("model") as ActivityCommentModel1.commModel
            }
            commentAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}