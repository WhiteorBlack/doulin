package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.DatePop
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.dialog.SexDialog
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.ui.mine.request.UserMessage_111
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 个人信息
 * Created by Slingge on 2018/9/1
 */
class PersonalInfoActivity : BaseActivity(), View.OnClickListener, DatePop.WheelViewCallBack2 {

    private var datePop: DatePop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onStart() {
        super.onStart()
        tv_name.text = StaticUtil.nickName
        ImageLoader.getInstance().displayImage(StaticUtil.headerUrl, iv_header)
    }

    private fun init() {
        inittitle("个人信息")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener(this)
        tv_right.text = "完成"

        val model=intent.getSerializableExtra("model") as HomePageModel
        et_nick.setText(model.nickname)
        tv_birthday.text=model.birthday
        if(model.sex=="0"){
            tv_srx.text="女"
        }else{
            tv_srx.text="男"
        }
        ImageLoader.getInstance().displayImage(StaticUtil.headerUrl, iv_header)

        tv_srx.setOnClickListener(this)
        tv_birthday.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_srx -> {
                SexDialog.dialog(this)
            }
            R.id.tv_birthday -> {
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(cl_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_right -> {
                val name = AbStrUtil.etTostr(et_nick)
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入昵称")
                    return
                }

                val sex = AbStrUtil.tvTostr(tv_srx)
                if (TextUtils.isEmpty(sex)) {
                    ToastUtil.showToast("请选择性别")
                    return
                }
                var sexInt = 0
                if (sex == "男") {
                    sexInt = 1
                }

                val birthday = AbStrUtil.tvTostr(tv_birthday)
                if (TextUtils.isEmpty(birthday)) {
                    ToastUtil.showToast("请选择出生日期")
                    return
                }
                ProgressDialog.showDialog(this)
                UserMessage_111.userInfo(this, name, sexInt.toString(), birthday, "", "","")
            }
        }
    }


    //选择性别
    @Subscribe
    fun onEvent(sex: String) {
        tv_srx.text = sex
    }

    //出生日期
    override fun position(position1: String, position2: String, position3: String) {
        tv_birthday.text = "$position1-$position2-$position3"
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}