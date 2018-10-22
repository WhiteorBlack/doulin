package com.lixin.amuseadjacent.photoView.imagepage;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import android.Manifest;

import com.lixin.amuseadjacent.R;
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog;
import com.lixin.amuseadjacent.app.util.ImageFileUtil;
import com.lixin.amuseadjacent.app.util.StaticUtil;
import com.lixin.amuseadjacent.app.util.abLog;
import com.lixin.amuseadjacent.photoView.PhotoViewAttacher;
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    private String ImageName = "";

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener((arg0, arg1, arg2) -> getActivity().finish());

        progressBar = v.findViewById(R.id.loading);

        ImageName = mImageUrl.substring(mImageUrl.lastIndexOf("/") + 1, mImageUrl.length());
        abLog.INSTANCE.e("图片名", ImageName);
        TextView tv_save = v.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(view -> {
            if (ApplyPermissionAlbum()) {
                new Task().execute(mImageUrl);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;

                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络有问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知的错误";
                        break;
                }
                if (!TextUtils.isEmpty(message)) {
                    ToastUtil.INSTANCE.showToast(message);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }


    private Bitmap bitmap;

    /**
     * 异步线程下载图片
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            bitmap = ImageFileUtil.GetImageInputStream(params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message = new Message();
            message.what = 0x123;
            handler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                ImageFileUtil.SavaImage(bitmap, ImageName);
                ToastUtil.INSTANCE.showToast("已保存至" + StaticUtil.INSTANCE.getDownImagePath());
            }
        }
    };

    /**
     * 申请权限结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            new Task().execute(mImageUrl);
        } else {
            PermissionsDialog.INSTANCE.dialog(getActivity(), "需要访问内存卡和拍照权限");
        }
    }

    public boolean ApplyPermissionAlbum() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                | ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, 0);
            return false;
        } else {
            return true;
        }
    }

}
