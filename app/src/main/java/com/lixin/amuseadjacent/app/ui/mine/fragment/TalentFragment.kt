package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ExperienceAdapter
import kotlinx.android.synthetic.main.fragment_designer.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 个人主页 达人
 * Created by Slingge on 2018/8/18
 */
class TalentFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var talentExpList = ArrayList<TalenExperienceModel.dataModel>()
    private var experienceAdapter: ExperienceAdapter? = null//达人经历

    private var  auid=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_designer, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_experience.layoutManager = linearLayoutManager

        experienceAdapter = ExperienceAdapter(activity!!,talentExpList)
        rv_experience.adapter = experienceAdapter

        ProgressDialog.showDialog(activity!!)
        Talent212_218225.TalenExperience(auid)
    }


    private fun init() {
        auid=arguments!!.getString("auid")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }

    @Subscribe
    fun onEvent(model: TalenExperienceModel) {

        tv_name.text = model.realname
        if (model.type == "0") {
            tv_type.text = "技能类"
        } else if (model.type == "1") {
            tv_type.text = "职业类"
        } else if (model.type == "2") {
            tv_type.text = "商业类"
        }
        tv_abst.text = model.userDesc

        talentExpList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_experience.layoutAnimation = controller
        experienceAdapter!!.notifyDataSetChanged()
        rv_experience.scheduleLayoutAnimation()

    }

    override fun loadData() {

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}