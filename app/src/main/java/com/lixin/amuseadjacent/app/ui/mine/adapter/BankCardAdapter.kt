package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.activity.BankCardAddActivity
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.ui.mine.request.Bank_155156157158164
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus

/**
 * 银行卡
 * flag1选择银行卡
 * Created by Slingge on 2018/8/18
 */
class BankCardAdapter(val context: Activity, val bankList: ArrayList<MyBankModel.detailsModel>, val flag: Int) : RecyclerView.Adapter<BankCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bankcard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return bankList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val num = bankList[position].cardNum
        holder.tv_name.text = bankList[position].cardName + "（" + num.substring(num.length - 4, num.length) + "）"

        holder.ll_bank.setOnClickListener { v ->
            //编辑
            if (flag == 1) {
                val bundle = Bundle()
                bundle.putSerializable("DynamiclDetailsModel", bankList[position])
                val intent = Intent()
                intent.putExtras(bundle)
                context.setResult(0, intent)
                context.finish()
            } else {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                bundle.putSerializable("DynamiclDetailsModel", bankList[position])
                MyApplication.openActivity(context, BankCardAddActivity::class.java, bundle)
            }
        }

        holder.tv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            Bank_155156157158164.delBank(bankList[position].cardId, position, object : Bank_155156157158164.DelbankCallack {
                override fun delBank(i: Int) {
                    bankList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ll_bank = view.findViewById<LinearLayout>(R.id.ll_bank)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)

        val line = view.findViewById<View>(R.id.line)
    }

}