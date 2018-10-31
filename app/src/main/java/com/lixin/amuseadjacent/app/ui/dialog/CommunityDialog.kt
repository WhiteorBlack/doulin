package com.lixin.amuseadjacent.app.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import com.lixin.amuseadjacent.R
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.entrance.model.CommunityModel
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus


/**
 * 选择社区
 * Created by Slingge on 2018/8/15
 */
object CommunityDialog {

    private var builder: AlertDialog? = null


    fun communityDialog(context: Activity, list: ArrayList<CommunityModel.dataModel>) {
        var CommunityId = ""
        var CommunityName = ""
        var UnitName = ""
        var UnitId = ""

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_community, null)
//        if (builder == null) {
        builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
//        }
        builder!!.show()
        builder!!.window.setContentView(view)


        val strList1 = ArrayList<String>()
        for (i in 0 until list.size) {
            strList1.add(list[i].communityName)
        }
        val spinnerAdapter = ArrayAdapter<String>(context,
                R.layout.item_spinner_text, strList1)

        var unitList = ArrayList<UnityModel.unitModel>()//单元数据集合


        val sp_unit = view.findViewById<Spinner>(R.id.sp_unit)//单元
        val sp_community = view.findViewById<Spinner>(R.id.sp_community)//社区
        sp_community.adapter = spinnerAdapter

        sp_community.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CommunityId = list[p2].communityId
                CommunityName = list[p2].communityName
                ProgressDialog.showDialog(context)

                abLog.e("社区id", CommunityId)

                val json = "{\"cmd\":\"getCommunitMenuList\",\"communityId\":\"" + list[p2].communityId + "\"}"

                OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, UnityModel::class.java)
                        if (model.result == "0") {
                            val strList = ArrayList<String>()
                            unitList = model.dataList
                            for (i in 0 until unitList.size) {
                                strList.add(unitList[i].unitName)
                            }
                            val spinnerAdapter = ArrayAdapter<String>(context,
                                    R.layout.item_spinner_text, strList)
                            sp_unit.adapter = spinnerAdapter
                        } else {
                            ToastUtil.showToast(model.resultNote)
                        }
                    }
                })

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



        sp_unit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                UnitName = unitList[p2].unitName
                UnitId = unitList[p2].unitId

                abLog.e("社区id", CommunityId + "," + UnitId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val et_num = view.findViewById<EditText>(R.id.et_num)
        val tc_next = view.findViewById<TextView>(R.id.tc_next)
        tc_next.setOnClickListener { v ->
            if (TextUtils.isEmpty(CommunityId)) {
                ToastUtil.showToast("请选择社区")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(UnitId)) {
                ToastUtil.showToast("请选择社区")
                return@setOnClickListener
            }
            val num = AbStrUtil.etTostr(et_num)
            if (TextUtils.isEmpty(num)) {
                ToastUtil.showToast("请输入门牌号")
                return@setOnClickListener
            }

            val model = UnityModel.unitModel()
            model.unitName = UnitName
            model.unitId = UnitId
            model.communityId = CommunityId
            model.communityName = CommunityName
            model.num = num
            EventBus.getDefault().post(model)

            builder!!.dismiss()
            builder = null
        }

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.85).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p


        builder!!.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }


    fun dissCommunity() {
        if (builder != null) {
            builder!!.dismiss()
//            builder = null
        }

    }

}