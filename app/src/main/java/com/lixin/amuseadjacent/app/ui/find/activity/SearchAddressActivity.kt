package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.poi.*
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.AddressAdapter
import com.lixin.amuseadjacent.app.ui.find.adapter.SearchAddressAdapter
import kotlinx.android.synthetic.main.activity_forgetpass.*
import kotlinx.android.synthetic.main.layout.*

/**
 * Created by Slingge on 2018/8/15
 */
class SearchAddressActivity : BaseActivity(), OnGetPoiSearchResultListener, View.OnClickListener {

    private var mPoiSearch: PoiSearch? = null
    private var adapter: SearchAddressAdapter? = null
    private var city:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("位置搜索")

        city = intent.extras.getString("city")
        iv_back.setOnClickListener(this)
        tv_search.setOnClickListener(this)
        iv_search.setOnClickListener(this)
        iv_cancel.setOnClickListener(this)

        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                (et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(
                                currentFocus
                                        .windowToken,
                                InputMethodManager.HIDE_NOT_ALWAYS)

                val content = et_search.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show()
                    return@OnEditorActionListener true
                } else {
                    val citySearchOption = PoiCitySearchOption()
                    citySearchOption.city(city!!.replace("市", ""))
                    citySearchOption.keyword(content)
                    citySearchOption.pageCapacity(50)
                    mPoiSearch!!.searchInCity(citySearchOption)
                }
                return@OnEditorActionListener true
            }
            false
        })

        mPoiSearch = PoiSearch.newInstance()
        mPoiSearch!!.setOnGetPoiSearchResultListener(this)

        /**
         * 使用建议搜索服务获取建议列表
         * city.replace("市","")
         */

        adapter = SearchAddressAdapter(this)
        recycler_view.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        recycler_view.adapter = adapter
        adapter!!.setOnClickListener(object: SearchAddressAdapter.OnItemClickListener{
            override fun itemClick(address: String) {
                val intent = Intent()
                intent.putExtra("address",address)
                setResult(200,intent)
                finish()
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_search -> {
                finish()
            }
            R.id.iv_cancel -> {
                et_search.setText("")
            }
            R.id.iv_search ->{
                if (TextUtils.isEmpty(et_search.text.toString().trim())){
                    return
                }
                val citySearchOption = PoiCitySearchOption()
                citySearchOption.city(city!!.replace("市", ""))
                citySearchOption.keyword(et_search.text.toString().trim())
                citySearchOption.pageCapacity(50)
                mPoiSearch!!.searchInCity(citySearchOption)
            }
        }
    }

    override fun onGetPoiResult(result: PoiResult?) {
        if (result == null || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
            Toast.makeText(applicationContext, "未搜索到结果", Toast.LENGTH_SHORT).show()
            return
        }
        val text = ("共" + result.getTotalPageNum() + "页，共"
                + result.getTotalPoiNum() + "条，当前第"
                + result.getCurrentPageNum() + "页，当前页"
                + result.getCurrentPageCapacity() + "条")
        if (result != null && !result.allPoi.isEmpty()) {
            adapter!!.refresh(result)
        } else {
            Toast.makeText(this, "抱歉，未搜索到相关位置信息", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
    }
    override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {
    }
    override fun onDestroy() {
        super.onDestroy()
        if (mPoiSearch != null){
            mPoiSearch!!.destroy()
        }
    }
}