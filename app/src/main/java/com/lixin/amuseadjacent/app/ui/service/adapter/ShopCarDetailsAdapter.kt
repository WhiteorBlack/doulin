package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.ShopCarModel
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 购物车商品
 * flag 0超市便利 1洗衣洗鞋 2新鲜果蔬
 * Created by Slingge on 2018/8/30
 */
class ShopCarDetailsAdapter(val context: Context, val flag: Int, val carList: ArrayList<ShopCarModel.carModel>,
                            val selectCallBack: SelectCallBack, val editNumCallBack: EditNumCallBack, val delCarCallBack: DelCarCallBack) : RecyclerView.Adapter<ShopCarDetailsAdapter.ViewHolder>() {

    private var isEdit = false//true 编辑状态

    interface SelectCallBack {
        fun select(flag: Int, i: Int, isSelect: Boolean)
    }

    interface EditNumCallBack {
        fun num(flag: Int, i: Int, num: Int)
    }

    interface DelCarCallBack {
        fun del(flag: Int, i: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_car_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = carList[position]

        if (isEdit) {//编辑状态
            holder.iv_del.visibility = View.VISIBLE
            holder.tv_plus.visibility = View.VISIBLE
            holder.num.visibility = View.VISIBLE
            holder.tv_reduce.visibility = View.VISIBLE

            holder.tv_num.visibility = View.GONE

        } else {
            holder.iv_del.visibility = View.GONE
            holder.tv_plus.visibility = View.GONE
            holder.num.visibility = View.GONE
            holder.tv_reduce.visibility = View.GONE

            holder.tv_num.visibility = View.VISIBLE
        }

        var NUM = model.count.toInt()

        ImageLoader.getInstance().displayImage(model.goodsImage, holder.image)
        holder.tv_name.text = model.goodsTitle
        holder.tv_money.text = "￥" + model.goodsPrice
        holder.tv_num.text = "x" + NUM.toString()
        holder.num.text = NUM.toString()

        if (model.isSelect) {
            holder.ic_chack.setImageResource(R.drawable.ic_check_hl)
        } else {
            holder.ic_chack.setImageResource(R.drawable.ic_check_nor)
        }

        holder.ic_chack.setOnClickListener { v ->
            if (model.isSelect) {
                selectCallBack.select(flag, position, false)
                holder.ic_chack.setImageResource(R.drawable.ic_check_nor)
            } else {
                selectCallBack.select(flag, position, true)
                holder.ic_chack.setImageResource(R.drawable.ic_check_hl)
            }
        }

        holder.iv_del.setOnClickListener { v ->
            delCarCallBack.del(flag, position)
        }


        holder.tv_plus.setOnClickListener { v ->
            NUM++
            holder.num.text = NUM.toString()
            holder.tv_num.text = NUM.toString()
            holder.tv_money.text = "￥" + DoubleCalculationUtil.mul(model.goodsPrice.toDouble(), model.count.toDouble())
            editNumCallBack.num(flag, position, NUM)
        }
        holder.tv_reduce.setOnClickListener { v ->
            if (NUM == 1) {
                return@setOnClickListener
            }
            NUM--
            holder.tv_num.text = NUM.toString()
            holder.num.text = NUM.toString()
            holder.tv_money.text = "￥" + DoubleCalculationUtil.mul(model.goodsPrice.toDouble(), model.count.toDouble())
            editNumCallBack.num(flag, position, NUM)
        }


        if (position == carList.size - 1) {
            holder.line2.visibility = View.GONE
        }

    }

    fun setEdite(isEdit: Boolean) {
        this.isEdit = isEdit
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_num = view.findViewById<TextView>(R.id.tv_num)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)

        val ic_chack = view.findViewById<ImageView>(R.id.ic_chack)

        val image = view.findViewById<ImageView>(R.id.image)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)

        val tv_plus = view.findViewById<TextView>(R.id.tv_plus)
        val num = view.findViewById<TextView>(R.id.num)
        val tv_reduce = view.findViewById<TextView>(R.id.tv_reduce)


        val line2 = view.findViewById<View>(R.id.line2)
    }


}
