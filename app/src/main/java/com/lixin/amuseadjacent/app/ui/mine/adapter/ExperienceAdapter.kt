package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R

/**
 * Created by Slingge on 2018/8/18
 */
class ExperienceAdapter(val context: Context) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_experience, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val imageAdapter = ImageAdapter(context)
        holder.rv_image.adapter = imageAdapter
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        init {
            val linearLayoutManager = GridLayoutManager(context,3)
            rv_image.layoutManager = linearLayoutManager
        }

    }


}