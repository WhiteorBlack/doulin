package com.lixin.amuseadjacent.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 类说明:
 * 
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015-12-20 上午9:27:28
 */
public class AppVersionUtil {
	/**
	 * 获取应用程序的版本名称.
	 * 
	 * @param context
	 *            the context
	 */
	public static String getVersionName(Context context) {
		// 用来管理手机的APK
		PackageManager pm = context.getPackageManager();
		try {
			// 得到知道APK的功能清单文件
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取应用程序的版本号.
	 * 
	 * @param context
	 *            the context
	 */
	public static int getVersionCode(Context context) {
		// 用来管理手机的APK
		PackageManager pm = context.getPackageManager();
		try {
			// 得到知道APK的功能清单文件
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
