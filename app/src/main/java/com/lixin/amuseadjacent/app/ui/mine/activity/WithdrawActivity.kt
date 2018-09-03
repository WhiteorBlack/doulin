package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.WithdrawDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import kotlinx.android.synthetic.main.activity_withdraw.*

/**
 * 提现
 * Created by Slingge on 2018/9/2.
 */
class WithdrawActivity : BaseActivity(),View.OnClickListener {


    private var ruleAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)
        init()
    }


    private fun init() {
        inittitle("提现")
        StatusBarWhiteColor()


        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_rule.layoutManager = linelayout
        ruleAdapter = RuleAdapter(this)
        rv_rule.adapter = ruleAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_rule.layoutAnimation = controller
        ruleAdapter!!.notifyDataSetChanged()
        rv_rule.scheduleLayoutAnimation()

        tv_recharge.setOnClickListener(this)


    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_recharge->{
                WithdrawDialog.communityDialog(this)
            }
        }
    }



}