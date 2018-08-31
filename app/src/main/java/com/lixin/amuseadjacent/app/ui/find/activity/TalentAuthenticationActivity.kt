package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.TalentExperienceAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_talent_authentication.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 达人认证
 * Created by Slingge on 2018/8/22
 */
class TalentAuthenticationActivity : BaseActivity(), View.OnClickListener {

    private var talentExpAdapter: TalentExperienceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_authentication)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("达人认证")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "提交"
        tv_right.setOnClickListener(this)

        tv_type.setOnClickListener(this)
        tv_addExperience.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_experience.layoutManager = linearLayoutManager

        talentExpAdapter = TalentExperienceAdapter(this)
        rv_experience.adapter = talentExpAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_experience.layoutAnimation = controller
        talentExpAdapter!!.notifyDataSetChanged()
        rv_experience.scheduleLayoutAnimation()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_addExperience -> {
                MyApplication.openActivity(this, TalentAuthenticationAddActivity::class.java)
            }
            R.id.tv_type -> {
                MyApplication.openActivityForResult(this, TalentTypeActivity::class.java,0)
            }
            R.id.tv_right -> {

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null){
            return
        }
        ToastUtil.showToast(data.getStringExtra("flag"))

    }

}