package fajieyefu.com.luoxiang.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.main.HistoryDetailsActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by qiancheng on 2016/11/24.
 */
public class ToolUtil {
    private  ProgressDialog progressDialog;
    private Context mContext;
    private int[] hm;

    public void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setTitle(context.getString(R.string.waitting));
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public int[] getScreenWH(Context context) {
        if (hm != null) {
            return hm;
        } else {

            int screenHeight;
            int screenWidth;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
            screenWidth = dm.widthPixels;
            hm = new int[2];
            hm[0] = screenWidth;
            hm[1] = screenHeight;
            return hm;
        }

    }

    public int[] getScreenWHDP(Context context) {
        if (hm != null) {
            return hm;
        } else {

            int screenHeight;
            int screenWidth;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
            screenWidth = dm.widthPixels;
            hm = new int[2];
            hm[0] = px2dp(context,screenWidth);
            hm[1] = px2dp(context,screenHeight);
            return hm;
        }

    }
    /**
     * 显示 ProgressDialog
     *
     * @param context
     * @param title
     * @param content
     */
    public void showProgressDialog(Context context, String title, String content) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(content);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 关闭 ProgressDialog
     */
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 动态测量listview的高度，解决ScrollView和ListView嵌套的出现的问题
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        if (listAdapter.getCount() == 0) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 判断是否为手机号
     *
     * @param mobiles
     * @return
     */

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }
    //判断服务是否运行
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            System.out.println(mName);
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static boolean isAPPALive(Context applicationContext, Object packageName) {
        ActivityManager activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;

    }

    public static AlertDialog.Builder applyPermission(Context context, String title,String msg,String[] permissions, String appointPermission){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int result = ContextCompat.checkSelfPermission(context, appointPermission);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (result != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                return showDialogTipUserRequestPermission(context,title,msg,permissions);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
    private static AlertDialog.Builder showDialogTipUserRequestPermission(Context context, String title, String msg, String[] permissions) {

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, permissions, 333);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
    }



    public static void showNotification(Context context, String title, String content, Intent intent, int flag){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        PendingIntent contentIntent = PendingIntent.getActivity(context, flag, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setTicker(context.getString(R.string.newAction));
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.logo);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.ledARGB = Color.BLUE;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 1000;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults = Notification.DEFAULT_SOUND;
        long[] vibrates = {0, 500, 500, 500};
        notification.vibrate = vibrates;
        notificationManager.notify(flag, notification);

    }



}
