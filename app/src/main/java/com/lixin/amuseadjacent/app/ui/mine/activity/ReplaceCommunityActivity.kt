package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_replace_community.*

/**
 * 更换社区
 * Created by Slingge on 2018/9/4
 */
class ReplaceCommunityActivity : BaseActivity() {

    private var ruleAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_replace_community)
        init()
    }

    private fun init() {
        inittitle("更换社区")
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



        val spinnerItems = arrayOf("恒大", "保利", "万达","碧桂园")
        val spinnerAdapter = ArrayAdapter<String>(this,
                R.layout.item_spinner_text, spinnerItems)

        sp_bank.adapter = spinnerAdapter

        sp_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ToastUtil.showToast(p2.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


}