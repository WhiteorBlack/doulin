package com.lixin.amuseadjacent.app.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.amuseadjacent.R;
import com.lixin.amuseadjacent.app.MyApplication;
import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog;
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter;
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1;
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclDetailsModel;
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134;
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224;
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139;
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity;
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter;
import com.lixin.amuseadjacent.app.util.*;
import com.lixin.amuseadjacent.app.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 使用的实体类中有 “object”kotlin关键字，所以只能用java类
 * 动态、帮帮详情
 * Created by Slingge on 2018/9/15
 */
public class DynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv_comment, rv_image;
    private DynamicCommentAdapter commentAdapter;
    private ArrayList<ActivityCommentModel1.commModel> commentList = new ArrayList();

    private ArrayList imageList = new ArrayList<String>();

    private ImageView image0, image1, image2;
    private TextView tv_more, tv_send;

    private CircleImageView iv_header;
    private TextView tv_name, tv_effect, tv_follow, tv_info;
    private LinearLayout ll_image;
    private ImageView player, iv_start;

    private TextView tv_time, tv_comment, tv_zan;
    private TextView tv_right, tv_title;
    private ImageView iv_back;

    private EditText et_comment;

    private String dynaId;
    private int zanNUm = 0;
    private String isZan = "0";//0未赞过 1已赞过
    private int commNum = 0;

    private String flag;// 0动态，1帮帮,2话题

    private DynamiclDetailsModel model;

    private int position = -1;//列表所在位置

    private int isEdit = -1;//是否操作

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EventBus.getDefault().register(this);
        init();
    }


    private void init() {
        flag = getIntent().getStringExtra("flag");
        position = getIntent().getIntExtra("position", -1);
        dynaId = getIntent().getStringExtra("id");

        tv_comment = findViewById(R.id.tv_comment);

        tv_right = findViewById(R.id.tv_right);
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        if (flag.equals("0")) {
            tv_title.setText("动态详情");
        } else if (flag.equals("1")) {
            tv_right.setVisibility(View.VISIBLE);
            tv_title.setText("帮帮详情");
            tv_right.setText("收藏");
        }

        StatusBarWhiteColor();
        findViewById(R.id.view_staus).setVisibility(View.GONE);

        rv_comment = findViewById(R.id.rv_comment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_comment.setLayoutManager(linearLayoutManager);

        commentAdapter = new DynamicCommentAdapter(this, commentList);
        rv_comment.setAdapter(commentAdapter);
        commentAdapter.setId(dynaId, "0");
        commentAdapter.setDelCommentCallBack(new DynamicCommentAdapter.DelCommentCallBack() {
            @Override
            public void delComment(int position) {
            }

            @Override
            public void delComment() {
                commNum = Integer.valueOf(model.object.commentNum) - 1;
                model.object.commentNum = commNum + "";
                tv_comment.setText(model.object.commentNum);
                isEdit = 2;
            }
        });

        tv_zan = findViewById(R.id.tv_zan);
        image0 = findViewById(R.id.image0);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        tv_more = findViewById(R.id.tv_more);
        tv_send = findViewById(R.id.tv_send);

        tv_zan.setOnClickListener(this);
        image0.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);

        tv_more.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        ConstraintLayout cl_1 = findViewById(R.id.cl_1);
        ConstraintLayout cl_2 = findViewById(R.id.cl_2);
        cl_1.setVisibility(View.VISIBLE);
        cl_2.setVisibility(View.GONE);

        iv_header = findViewById(R.id.iv_header);
        tv_name = findViewById(R.id.tv_name);
        tv_effect = findViewById(R.id.tv_effect);
        tv_follow = findViewById(R.id.tv_follow);
        tv_info = findViewById(R.id.tv_info);
        tv_info.setMaxLines(Integer.MAX_VALUE);
        ll_image = findViewById(R.id.ll_image);
        player = findViewById(R.id.player);
        iv_start = findViewById(R.id.iv_start);
        ll_image = findViewById(R.id.ll_image);
        tv_time = findViewById(R.id.tv_time);
        tv_comment = findViewById(R.id.tv_comment);
        tv_comment.setOnClickListener(this);

        et_comment = findViewById(R.id.et_comment);

        tv_follow.setOnClickListener(this);

        rv_image = findViewById(R.id.rv_image);
        rv_image.setLayoutManager(new GridLayoutManager(this, 3));

        rv_image.addOnItemTouchListener(new RecyclerItemTouchListener(rv_image) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int i = vh.getAdapterPosition();
                if (i < 0 || i >= imageList.size()) {
                    return;
                }
                PreviewPhoto.INSTANCE.preview(DynamicDetailsActivity.this, imageList, i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog.INSTANCE.showDialog(this);
        DynaComment_133134.INSTANCE.dynamicDetail(this, dynaId);
    }

    @Subscribe
    public void onEvent(DynamiclDetailsModel model) {
        this.model = model;
        if (model.object.type.equals("0")) {//0动态 1帮帮
            tv_title.setText("动态详情");
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_title.setText("帮帮详情");
            tv_right.setText("收藏");
        }

        ImageLoader.getInstance().displayImage(model.object.dynamicIcon, iv_header, ImageLoaderUtil.HeaderDIO());
        iv_header.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("auid", model.object.dynamicUid);
            bundle.putString("isAttention", model.object.isAttention);
            MyApplication.openActivity(DynamicDetailsActivity.this, PersonalHomePageActivity.class, bundle);
        });

        tv_name.setText(model.object.dynamicName);
        tv_effect.setText("影响力" + model.object.userEffectNum);
        if (TextUtils.isEmpty(model.object.dynamicContent)) {
            tv_info.setVisibility(View.GONE);
        } else {
            tv_info.setText(model.object.dynamicContent);
        }

        tv_time.setText(model.object.time);
        tv_comment.setText(model.object.commentNum);
        commNum = Integer.valueOf(model.object.commentNum);
        zanNUm = Integer.valueOf(model.object.zanNum);
        tv_zan.setText(model.object.zanNum);

        if (model.object.isAttention.equals("0")) {// 0未关注 1已关注
            tv_follow.setText("关注");
            tv_follow.setVisibility(View.VISIBLE);
        } else {
            tv_follow.setText("已关注");
            tv_follow.setVisibility(View.INVISIBLE);
        }

        if (StaticUtil.INSTANCE.getUid().equals(model.object.dynamicUid)) {
            tv_follow.setVisibility(View.INVISIBLE);
        }

        if (flag.equals("0")) {//0帮帮
            if (model.object.dynamicUid.equals(StaticUtil.INSTANCE.getUid())) {
                tv_right.setVisibility(View.VISIBLE);
                if (model.object.state.equals("0")) {//0正常显示 1隐藏
                    tv_right.setText("隐藏");
                } else {
                    tv_right.setText("已隐藏");
                }
            }
        } else {
            if (model.object.iscang.equals("0")) {//0未收藏 1已收藏
                tv_right.setText("收藏");
            } else {
                tv_right.setText("已收藏");
            }
        }

        isZan = model.object.isZan;
        if (model.object.isZan.equals("0")) {//0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan, tv_zan, 5);
        } else {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5);
        }

        if (TextUtils.isEmpty(model.object.dynamicVideo)) {
            player.setVisibility(View.GONE);
            if (model.object.dynamicImgList.size() > 0) {
                if (model.object.dynamicImgList.size() < 3) {
                    rv_image.setVisibility(View.GONE);
                    ll_image.setVisibility(View.VISIBLE);
                    if (model.object.dynamicImgList.size() == 1) {
                        image0.setVisibility(View.VISIBLE);

                        image1.setVisibility(View.GONE);
                        image2.setVisibility(View.GONE);

                        ImageLoader.getInstance().displayImage(model.object.dynamicImgList.get(0), image0);
                    } else {
                        image0.setVisibility(View.GONE);

                        image1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.VISIBLE);

                        ImageLoader.getInstance().displayImage(model.object.dynamicImgList.get(0), image1);
                        ImageLoader.getInstance().displayImage(model.object.dynamicImgList.get(1), image2);
                    }
                } else {
                    ll_image.setVisibility(View.GONE);
                    rv_image.setVisibility(View.VISIBLE);
                    ImageAdapter imageAdapter = new ImageAdapter(this, model.object.dynamicImgList, 0);
                    rv_image.setAdapter(imageAdapter);
                }
            } else {
                rv_image.setVisibility(View.GONE);
                ll_image.setVisibility(View.GONE);
            }
        } else {
            player.setVisibility(View.VISIBLE);
            iv_start.setVisibility(View.VISIBLE);

            player.setOnClickListener(view -> JCVideoPlayerStandard.startFullscreen(DynamicDetailsActivity.this, JCVideoPlayerStandard.class, model.object.dynamicVideo, ""));
            ImageLoader.getInstance().displayImage(model.object.dynamicImg, player);
        }
        imageList = model.object.dynamicImgList;
        if (!commentList.isEmpty()) {
            commentList.clear();
            commentAdapter.notifyDataSetChanged();
        }
        commentList.addAll(model.dataList);
        commentAdapter.notifyDataSetChanged();
        if (commentList.isEmpty()) {
            tv_more.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_follow:
                ProgressDialog.INSTANCE.showDialog(this);
                Mail_138139.INSTANCE.follow(model.object.dynamicUid, () -> {
                    if (model.object.isAttention.equals("0")) {// 0未关注 1已关注
                        model.object.isAttention = "1";
                        tv_follow.setText("已关注");
                    } else {
                        model.object.isAttention = "0";
                        tv_follow.setText("关注");
                    }
                });
                break;
            case R.id.tv_right:
                ProgressDialog.INSTANCE.showDialog(this);
                if (model.object.type.equals("0")) {//0动态 1帮帮
                    DynaComment_133134.INSTANCE.hide(dynaId, () -> {//隐藏自己的动态
                        if (model.object.state.equals("0")) {////0正常显示 1隐藏
                            tv_right.setText("已隐藏");
                            model.object.state = "1";
                        } else {
                            tv_right.setText("隐藏");
                            model.object.state = "0";
                        }
                    });
                } else {
                    Event_221222223224.INSTANCE.EventCollect("0", dynaId, () -> {
                        isEdit = 0;
                        if (model.object.iscang.equals("0")) {//没有收藏
                            tv_right.setText("已收藏");
                            model.object.iscang = "1";
                        } else {
                            tv_right.setText("收藏");
                            model.object.iscang = "0";
                        }
                    });
                }
                break;
            case R.id.iv_back:
                back();
                break;
            case R.id.image0:
                PreviewPhoto.INSTANCE.preview(DynamicDetailsActivity.this, imageList, 0);
                break;
            case R.id.image1:
                PreviewPhoto.INSTANCE.preview(DynamicDetailsActivity.this, imageList, 0);
                break;
            case R.id.image2:
                PreviewPhoto.INSTANCE.preview(DynamicDetailsActivity.this, imageList, 1);
                break;
            case R.id.tv_zan:
                ProgressDialog.INSTANCE.showDialog(this);
                DynaComment_133134.INSTANCE.zan(dynaId, "", () -> {
                    isEdit = 1;
                    if (model.object.isZan.equals("1")) {
                        zanNUm--;
                        model.object.isZan = "0";
                        AbStrUtil.setDrawableLeft(DynamicDetailsActivity.this, R.drawable.ic_zan, tv_zan, 5);
                    } else {
                        zanNUm++;
                        model.object.isZan = "1";
                        AbStrUtil.setDrawableLeft(DynamicDetailsActivity.this, R.drawable.ic_zan_hl, tv_zan, 5);
                    }
                    model.object.zanNum = zanNUm + "";
                    tv_zan.setText(zanNUm + "");

                });
                break;
            case R.id.tv_send:
                String content = AbStrUtil.etTostr(et_comment);
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                ProgressDialog.INSTANCE.showDialog(this);
                DynaComment_133134.INSTANCE.comment(dynaId, "", content, (commentId) -> {
                    isEdit = 2;
                    commNum++;
                    tv_comment.setText(commNum + "");
                    et_comment.setText("");

                    model.object.commentNum = commNum + "";
                    tv_comment.setText(commNum + "");

                    ActivityCommentModel1.commModel model = new ActivityCommentModel1.commModel();
                    model.setCommentContent(content);
                    model.setCommentIcon(StaticUtil.INSTANCE.getHeaderUrl());
                    model.setCommentTime(GetDateTimeUtil.getYMDHMS());
                    model.setSecondNum("0");
                    model.setCommentName(StaticUtil.INSTANCE.getNickName());
                    model.setZanNum("0");
                    model.setCommentId(commentId);
                    model.setCommentUid(StaticUtil.INSTANCE.getUid());
                    commentList.add(model);
                    commentAdapter.notifyDataSetChanged();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//输入法在窗口上已经显示，则隐藏，反之则显示
                });
                break;
            case R.id.tv_comment:
                if (timer == null) {
                    timer = new Timer();
                }
                et_comment.requestFocus();//获取焦点 光标出现
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) DynamicDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(et_comment, 0);
                    }
                }, 100);
                break;
            case R.id.tv_more://更多评论
                Bundle bundle = new Bundle();
                bundle.putString("id", dynaId);
                bundle.putString("flag", flag);
                MyApplication.openActivity(this, DynamicAllCommentsActivity.class, bundle);
                break;
        }
    }

    private Timer timer;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 303) {//二级回复
            if (data.getStringExtra("type").equals("del")) {
                commentList.remove(data.getIntExtra("position", 0));
            } else {
                commentList.set(data.getIntExtra("position", 0), (ActivityCommentModel1.commModel) data.getSerializableExtra("model"));
            }
            commentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                back();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (isEdit != -1) {
            Intent intent = new Intent();
            intent.putExtra("model", model);
            abLog.INSTANCE.e("传送", new Gson().toJson(model));
            intent.putExtra("position", position);
            setResult(3, intent);
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
