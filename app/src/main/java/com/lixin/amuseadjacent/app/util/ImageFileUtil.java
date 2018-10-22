package com.lixin.amuseadjacent.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Matrix;
import android.util.Log;

import com.lxkj.linxintechnologylibrary.app.util.ToastUtil;


/**
 * 图片工具类
 * Created by Administrator on 2016/8/24 0024.
 */
public class ImageFileUtil {


    /**
     * 将图片转成字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        if (bitmap == null)
            return "";
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }


    /**
     * 压缩生成的Bitmap大小防止OOM
     * path 图片路径
     * scalSize 压缩倍数
     */
    public static Bitmap loadResBitmap(String path, int scalSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        if (scalSize != 0) {
            options.inSampleSize = scalSize;
        }
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }


    /**
     * 质量压缩方法
     *
     * @return
     */
    public static Bitmap compressImage(String path) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, option);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        if (baos.toByteArray().length / 1024 > 200) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        } else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    public static void clearFile() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/com.lxkj.loanthrough/image";
        File destDir = new File(path);
        if (!destDir.exists()) {//如果不存在则创建
            return;
        }
        destDir.delete();
    }


    /**
     * 压缩图片
     * path 路径
     */
    public static Bitmap getBitmapFromPath(String path) {
        Bitmap bitmap = loadResBitmap(path, 0);
        if (bitmap == null) {
            ToastUtil.INSTANCE.showToast("图片错误");
            return null;
        }
        int width = bitmap.getWidth();
        int hight = bitmap.getHeight();


        Bitmap newbm = null;
        if (width < 100 | hight < 100) {
            newbm = bitmap;
        } else if ((width < 500 && width >= 100) | (hight < 500 && hight >= 100)) {
            float scaleWidth = ((float) width * 1 / 2) / width;
            float scaleHeight = ((float) hight * 1 / 2) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 500 && width < 1000) | (hight >= 500 && hight < 1000)) {
            float scaleWidth = ((float) width * 1 / 4) / width;
            float scaleHeight = ((float) hight * 1 / 4) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 1000 && width < 1500) | (hight >= 1000 && hight < 1500)) {
            float scaleWidth = ((float) width * 1 / 8) / width;
            float scaleHeight = ((float) hight * 1 / 8) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 1500 && width < 2000) | (hight >= 1500 && hight < 2000)) {
            float scaleWidth = ((float) width * 1 / 10) / width;
            float scaleHeight = ((float) hight * 1 / 10) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 2000 && width < 5000) | (hight >= 2000 && hight < 5000)) {
            float scaleWidth = ((float) width * 1 / 12) / width;
            float scaleHeight = ((float) hight * 1 / 12) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else {
            float scaleWidth = ((float) width * 1 / 18) / width;
            float scaleHeight = ((float) hight * 1 / 18) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        }
        return newbm;
    }


    /**
     * 保存bitmap文件
     */
    public static File saveFilePath(String path) {
        int tag;
        Bitmap bitmap = loadResBitmap(path, 0);
        if (bitmap == null) {
            ToastUtil.INSTANCE.showToast("图片错误");
            return null;
        }

        int width = bitmap.getWidth();
        int hight = bitmap.getHeight();
        Bitmap newbm = null;

        Log.e("图片宽高..............", width + "," + hight);
        if (width < 500 | hight < 500) {
            tag = 100;
            newbm = bitmap;
        } else if ((width >= 500 && width < 1000) | (hight >= 500 && hight < 1000)) {
            tag = 90;
            float scaleWidth = ((float) width * 9 / 10) / width;
            float scaleHeight = ((float) hight * 9 / 10) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 1000 && width < 1500) | (hight >= 1000 && hight < 1500)) {
            tag = 80;
            float scaleWidth = ((float) width * 8 / 10) / width;
            float scaleHeight = ((float) hight * 8 / 10) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else if ((width >= 1500 && width < 2000) | (hight >= 1500 && hight < 2000)) {
            tag = 70;
            float scaleWidth = ((float) width * 7 / 10) / width;
            float scaleHeight = ((float) hight * 7 / 10) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        } else {
            tag = 50;
            float scaleWidth = ((float) width * 5 / 10) / width;
            float scaleHeight = ((float) hight * 5 / 10) / hight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
        }


        String filePath = Environment.getExternalStorageDirectory().getPath() + "/mockingbot/image";
        File destDir = new File(filePath);
        if (!destDir.exists()) {//如果不存在则创建
            destDir.mkdirs();
        }
        String name = path.substring(path.lastIndexOf("/"), path.length());
        File file = new File(filePath, name);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            newbm.compress(Bitmap.CompressFormat.JPEG, tag, bos);
            try {
                bitmap.recycle();
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 保存bitmap文件
     */
    public static File saveFileBitmap(Bitmap bitmap) {

        if (bitmap == null) {
            ToastUtil.INSTANCE.showToast("图片错误");
            return null;
        }

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.lixin.amuseadjacent/image";
        File destDir = new File(filePath);
        if (!destDir.exists()) {//如果不存在则创建
            destDir.mkdirs();
        }
        String name = "/PreviewingVideo.jpg";
        File file = new File(filePath, name);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            try {
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 把bitmap转换成圆形
     * 生成透明背景的圆形图片,！注意要生成透明背景的圆形，图片一定要png类型的，不能是jpg类型
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
        if (bitmap == null) {
            Log.e("toRoundBitmap", "toRoundBitmap==null");
        }
        return output;

    }


    public static Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        URLConnection con = null;
        InputStream is = null;
        try {
            URL imageURL = new URL(url);
            con = imageURL.openConnection();
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            // 获取资源图片
            bitmap = BitmapFactory.decodeStream(is, null, null);
        } catch (Exception e) {

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap GetImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 保存位图到本地
     *
     * @return void
     */
    public static void SavaImage(Bitmap bitmap, String imageName) {
        File file = new File(StaticUtil.INSTANCE.getDownImagePath());
        FileOutputStream fileOutputStream;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(StaticUtil.INSTANCE.getDownImagePath() + imageName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
