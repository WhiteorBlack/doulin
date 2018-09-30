package com.lixin.amuseadjacent.app.ui.service.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by Slingge on 2018/9/18
 */
object OfficialShopGoodsList_35 {

    //0新鲜果蔬 1洗衣洗鞋 2超市便利
    fun shop(type: String) {
        val json = "{\"cmd\":\"getFreshList\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"type\":\"" + type + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ShopGoodsListModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    interface ShopGoodsCallback{
        fun GoodList(List: ArrayList<ShopGoodsModel.dataModel>)
    }


    //商品
    /**
     * @param type 0新鲜果蔬 1洗衣洗鞋 2超市便利
     * @param categoryId 分类id(洗衣洗鞋是二级分类id，其它是一级分类id
     * @param content 商品名称(搜索用,可为空)
     * */
    fun ShopGoods(type:String,categoryId:String,content:String,shopGoodsCallback: ShopGoodsCallback?) {
        val json = "{\"cmd\":\"getCategoryGoods\",\"uid\":\"" + StaticUtil.uid + "\",\"categoryId\":\"" + categoryId+
                "\",\"communityId\":\"" + StaticUtil.communityId+ "\",\"content\":\"" + content +"\",\"type\":\"" + type + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ShopGoodsModel::class.java)
                if (model.result == "0") {
                    if(shopGoodsCallback==null){
                        EventBus.getDefault().post(model)
                    }else{
                        shopGoodsCallback.GoodList(model.dataList)
                    }
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }


}