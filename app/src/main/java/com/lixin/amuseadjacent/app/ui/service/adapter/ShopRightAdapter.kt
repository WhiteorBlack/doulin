package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.ShopCar_12412537
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 店铺右菜单
 * Created by Slingge on 2018/8/30
 */
class ShopRightAdapter(val context: Context, val titleList: String, val rightList: ArrayList<ShopGoodsModel.dataModel>, val addShopCar: AddShopCar
) : RecyclerView.Adapter<ShopRightAdapter.ViewHolder>() {

    private var type = ""//0新鲜果蔬 1洗衣洗鞋 2超市便利

    interface ShopOnClickListtener {
        fun add(view: View, position: Int)
    }

    var shoponclickListtener: ShopOnClickListtener? = null
    fun setShopOnClickListtener(shoponclickListtener: ShopOnClickListtener) {
        this.shoponclickListtener = shoponclickListtener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_right2, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rightList.size
    }

    interface AddShopCar {
        fun addCar(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.tv_title.text = titleList
            holder.tv_title.visibility = View.VISIBLE
        } else {
            holder.tv_title.visibility = View.GONE
        }
        val model = rightList[position]

        model.UnitPrice = if (TextUtils.isEmpty(model.goodsCuprice)) {
            model.goodsPrice.toDouble()
        } else {
            model.goodsCuprice.toDouble()
        }

        if (model.money == 0.0) {
            model.money = model.UnitPrice
        }

        var money = model.money
        holder.tv_money.text = " ￥：$money"


        holder.tv_name.text = model.goodsName
        ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)

        holder.tv_volume.text = "销量：" + model.goodsSallnum

        if (model.goodsNum <= 0) {
            holder.tv_num.visibility = View.GONE
        } else {
            holder.tv_num.visibility = View.VISIBLE
            holder.tv_num.text = model.goodsNum.toString()
        }


        holder.iv_add.setOnClickListener { v ->
            if (rightList[position].isSelect) {
                return@setOnClickListener
            }
            ShopCar_12412537.addCar(type, model.goodsId, "1", object : ShopCar_12412537.AddCarCallback {
                override fun addCar() {
                    addShopCar.addCar(position)
                    if (shoponclickListtener != null) {
                        shoponclickListtener!!.add(v, position)
                    }
                    rightList[position].isSelect = true
                }
            })
        }

        holder.image.setOnClickListener { v ->
            PreviewPhoto.preview(context, model.goodsImg)
        }

    }

    fun setType(type: String) {
        this.type = type
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)

        val image = view.findViewById<ImageView>(R.id.image)
        val tv_volume = view.findViewById<TextView>(R.id.tv_volume)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val iv_add = view.findViewById<ImageView>(R.id.iv_add)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)
    }


}
