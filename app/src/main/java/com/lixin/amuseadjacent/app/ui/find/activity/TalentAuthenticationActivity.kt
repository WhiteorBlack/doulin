package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.TalentExperienceAdapter
import com.lixin.amuseadjacent.app.ui.find.model.AddTalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_talent_authentication.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 达人认证
 * Created by Slingge on 2018/8/22
 */
class TalentAuthenticationActivity : BaseActivity(), View.OnClickListener {

    private var talentExpAdapter: TalentExperienceAdapter? = null
    private var talentExpList = ArrayList<TalenExperienceModel.dataModel>()

    private var type = "0"// 0未申请达人 1达人审核中 2审核通过

    private var lableName = ""//达人类型id
    private var lableType = ""//达人类型 商业职业等

    var editPosition = -1//修改经历的下标

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_talent_authentication)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("达人认证")

        type = intent.getStringExtra("type")

        if (type == "0" || type == "3") {
            tv_right.visibility = View.VISIBLE
            tv_right.text = "提交"
            tv_right.setOnClickListener(this)
        }

        tv_type.setOnClickListener(this)
        tv_addExperience.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_experience.layoutManager = linearLayoutManager

        talentExpAdapter = TalentExperienceAdapter(this, talentExpList, object : TalentExperienceAdapter.PositionCallBack {
            override fun select(i: Int) {
                editPosition = i
            }
        })
        rv_experience.adapter = talentExpAdapter

        ProgressDialog.showDialog(this)
        Talent212_218225.TalenExperience(StaticUtil.uid)
    }

    @Subscribe
    fun onEvent(model: TalenExperienceModel) {

        et_name.setText(model.realname)
        et_phone.setText(model.phone)
        tv_type.text = model.userlabel
        et_info.setText(model.userDesc)

        if (type!="0") {
            NoInput(et_name)
            NoInput(et_phone)
            NoInput(et_info)
        }

        talentExpList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_experience.layoutAnimation = controller
        talentExpAdapter!!.notifyDataSetChanged()
        rv_experience.scheduleLayoutAnimation()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_addExperience -> {
                if (type == "0") {
                    ToastUtil.showToast("请先申请达人认证")
                    return
                }
                MyApplication.openActivityForResult(this, TalentAuthenticationAddActivity::class.java, 1)
            }
            R.id.tv_type -> {//达人类型选择
                if (type !="0") {
                    return
                }
                MyApplication.openActivityForResult(this, TalentTypeActivity::class.java, 0)
            }
            R.id.tv_right -> {
                val name = AbStrUtil.etTostr(et_name)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入姓名")
                    return
                }
                val phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }
                if (TextUtils.isEmpty(lableName) || TextUtils.isEmpty(lableType)) {
                    ToastUtil.showToast("请选择达人类型")
                    return
                }
                val content = AbStrUtil.etTostr(et_info)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入简介")
                    return
                }

                Talent212_218225.applyTalen(lableType, name, phone, content, lableName, object : Talent212_218225.ApplyTalenCallBack {
                    override fun Apply() {
                        if (type == "0") {
                            type = "2"
                            TalentActivity().type = "2"
                        }
                    }
                })
            }
        }

    }


    private fun NoInput(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false // user touches widget on phone with touch screen
        editText.isClickable = false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            lableType = data.getStringExtra("type")
            lableName = data.getStringExtra("name")
            tv_type.text = lableName
        } else if (requestCode == 1) {//添加
            if (talentExpList.isNotEmpty()) {
                talentExpList.clear()
                talentExpAdapter!!.notifyDataSetChanged()
            }
            ProgressDialog.showDialog(this)
            Talent212_218225.TalenExperience(StaticUtil.uid)
        } else if (requestCode == 2) {

            if (editPosition == -1) {
                return
            }
            val model = data.getSerializableExtra("model") as AddTalenExperienceModel
            talentExpList[editPosition].imgurl = model.file
            talentExpList[editPosition].content = model.content
            talentExpList[editPosition].endTime = model.endTime
            talentExpList[editPosition].startTime = model.startTime
            talentExpList[editPosition].experienceId = model.experienceId
            talentExpList[editPosition].title = model.title
            talentExpAdapter!!.notifyItemChanged(editPosition)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}