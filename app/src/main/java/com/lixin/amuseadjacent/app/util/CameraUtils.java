package com.lixin.amuseadjacent.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.lxkj.linxintechnologylibrary.app.util.ToastUtil;

import java.io.File;


/**
 * 相机拍照
 * Created by Slingge on 2016/10/14 0014.
 */

public class CameraUtils {
    private static final String IMAGE_TYPE = "image/*";

    private static Context mContext;

    public static String photoPath = Environment.getExternalStorageDirectory().getPath() + "/com.lxkj.loanthrough/image";
    ;

    public CameraUtils(Context context) {
        mContext = context;
    }

    /**
     * 打开照相机，没有照片存储路径
     *
     * @param activity
     * @param requestCode
     */
    public static void openCamera(Activity activity, int requestCode) {
        if (!hasSDcard()) {
            Toast.makeText(activity, "未找到存储卡", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);  // 调用前置摄像头
        activity.startActivityForResult(intent, requestCode);
    }

    public static boolean hasSDcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 打开照相机
     *
     * @param activity    当前的activity
     * @param requestCode 拍照成功时activity forResult 的时候的requestCode
     *                    照片名
     */
    public static void openCamera(Activity activity, int requestCode, String photoName) {
        if (!hasSDcard()) {
            Toast.makeText(activity, "未找到存储卡", Toast.LENGTH_SHORT).show();
            return;
        }
        File destDir = new File(photoPath);
        if (!destDir.exists()) {//如果不存在则创建
            destDir.mkdirs();
        }
        ToastUtil.INSTANCE.showToast("拍摄肖像并上传");
        File file = new File(photoPath, photoName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 本地照片调用
     *
     * @param activity
     * @param requestCode
     */
    public static void openPhotos(Activity activity, int requestCode) {
        if (openPhotosNormal(activity, requestCode) && openPhotosBrowser(activity, requestCode) && openPhotosFinally())
            ;
    }

    /**
     * PopupMenu打开本地相册.
     */
    private static boolean openPhotosNormal(Activity activity, int actResultCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_TYPE);
        try {
            activity.startActivityForResult(intent, actResultCode);
        } catch (android.content.ActivityNotFoundException e) {
            return true;
        }
        return false;
    }

    /**
     * 打开其他的一文件浏览器,如果没有本地相册的话
     */
    private static boolean openPhotosBrowser(Activity activity, int requestCode) {
        Toast.makeText(mContext, "没有相册软件，运行文件浏览器", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
        intent.setType(IMAGE_TYPE);
        Intent wrapperIntent = Intent.createChooser(intent, null);
        try {
            activity.startActivityForResult(wrapperIntent, requestCode);
        } catch (android.content.ActivityNotFoundException e1) {
            return true;
        }
        return false;
    }

    /**
     * 这个是找不到相关的图片浏览器,或者相册
     */
    private static boolean openPhotosFinally() {
        Toast.makeText(mContext, "您的系统没有文件浏览器或则相册支持,请安装！", Toast.LENGTH_LONG).show();
        return false;
    }

    /**
     * 获取从本地图库返回来的时候的URI解析出来的文件路径
     *
     * @return
     */
    public static String getPhotoPathByLocalUri(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return picturePath;
        } else {
            String string = selectedImage.toString();
            if (string.startsWith("file:///")) {
                return string.substring(7);
            }
            return string;
            //return selectedImage.toString();
        }

    }


    /**
     * 裁剪选择的图片
     *
     * @param uri
     */
    public static void startPhotoZoom(Activity context, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, 1);
    }

}
