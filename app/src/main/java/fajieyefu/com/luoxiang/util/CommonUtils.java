package fajieyefu.com.luoxiang.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.DisplayMetrics;

/**
 * 通用工具类
 */
public class CommonUtils {

	/**
	 * 开启activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, Bundle bundle) {
		Intent intent = new Intent(context, activity);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, String key, int value) {
		Bundle bundle = new Bundle();
		bundle.putInt(key, value);
		launchActivity(context, activity, bundle);
	}

	public static void launchActivity(Context context, Class<?> activity, String key, String value) {
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		launchActivity(context, activity, bundle);
	}

	public static void launchActivityForResult(Activity context, Class<?> activity, int requestCode) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("limit", 3);
		context.startActivityForResult(intent, requestCode);
	}

	public static void launchActivityForResult(Activity activity, Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);
	}

	/** 启动一个服务 */
	public static void launchService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.startService(intent);
	}

	public static void stopService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.stopService(intent);
	}


	/** 获取屏幕宽度 */
	public static int getWidthPixels(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/** 获取屏幕高度 */
	public static int getHeightPixels(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/** 通过Uri获取图片路径 */
	public static String query(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, new String[] { MediaColumns.DATA }, null, null, null);
		cursor.moveToNext();
		return cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));
	}

}
