package com.netease.nim.uikit.business.recent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.drop.DropFake;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MessListModel.msgModel> list;

    public RecyclerViewAdapter( Context context,ArrayList<MessListModel.msgModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_msg1, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MessListModel.msgModel model = list.get(position);

        if (model.getType().equals("0")){
            holder.tv_type.setText("官方消息");
            holder.image.setImageResource(R.drawable.ic_official_msg);
        }else  if (model.getType().equals("1")){
            holder.tv_type.setText("订单消息");
            holder.image.setImageResource(R.drawable.ic_order);
        }else  if (model.getType().equals("2")){
            holder.tv_type.setText("评论消息");
            holder.image.setImageResource(R.drawable.ic_comment);
        }else  if (model.getType().equals("3")){
            holder.tv_type.setText("点赞消息");
            holder.image.setImageResource(R.drawable.ic_zan_msg);
        }

        holder.tv_info.setText(model.getMessageTitle());
        holder.tv_time.setText(model.getMessageTime());

        int unreadNum = Integer.parseInt(model.getMessagenum());
        holder.tvUnread.setVisibility(unreadNum > 0 ? View.VISIBLE : View.GONE);
        holder.tvUnread.setText(unreadCountShowRule(unreadNum));

        holder.ll_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getType().equals("0")){
                    Intent intent = new Intent();
                    intent.setClassName("com.lixin.amuseadjacent", "com.lixin.amuseadjacent.app.ui.message.activity.OfficialNewsActivity");
                    intent.putExtra("type","0");
                    context.startActivity(intent);

                }else  if (model.getType().equals("1")){
                    Intent intent = new Intent();
                    intent.setClassName("com.lixin.amuseadjacent", "com.lixin.amuseadjacent.app.ui.message.activity.OrderNewsActivity");
                    intent.putExtra("type","1");
                    context.startActivity(intent);
                }else  if (model.getType().equals("2")){
                    Intent intent = new Intent();
                    intent.setClassName("com.lixin.amuseadjacent", "com.lixin.amuseadjacent.app.ui.message.activity.CommentNewsActivity");
                    intent.putExtra("type","2");
                    context.startActivity(intent);
                }else  if (model.getType().equals("3")){
                    Intent intent = new Intent();
                    intent.setClassName("com.lixin.amuseadjacent", "com.lixin.amuseadjacent.app.ui.message.activity.CommentNewsActivity");
                    intent.putExtra("type","3");
                    context.startActivity(intent);
                }
                list.get(position).setMessagenum("0");
                notifyDataSetChanged();
            }
        });
    }

    protected String unreadCountShowRule(int unread) {
        unread = Math.min(unread, 99);
        return String.valueOf(unread);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public ImageView image;
        public TextView tv_type;
        public TextView tv_info;
        public TextView tv_time;
        public ConstraintLayout ll_bank;
        public DropFake tvUnread;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            ll_bank = (ConstraintLayout) itemView.findViewById(R.id.ll_bank);
            tvUnread = (DropFake) itemView.findViewById(R.id.tv_msgNum_1);
        }
    }
}
