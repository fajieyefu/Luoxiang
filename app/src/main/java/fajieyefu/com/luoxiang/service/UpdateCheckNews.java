package fajieyefu.com.luoxiang.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.AuditCount;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ContractDetail;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.ResponseBean2;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.AuditCountDao;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.main.ContractAuditActivity;
import fajieyefu.com.luoxiang.main.ContractAuditDetailsActivity;
import fajieyefu.com.luoxiang.main.HistoryActivity;
import fajieyefu.com.luoxiang.receiver.AutoUpdateReceiver;
import fajieyefu.com.luoxiang.util.DaoManager;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.MyCallback2;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-05.
 */

public class UpdateCheckNews extends Service {
    private UserInfo userInfo;
    private String username;
    private String password;
    private JSONObject content;
    private int originalCount;//审批消息的数量

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        content = new JSONObject();
        userInfo = DaoBean.getUseInfoById(1);
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        try {
            content.put("username", username);
            content.put("password", password);
            content.put("crr", 1);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        OkHttpUtils.postString()
//                .url(CommonData.OrderAplyListURL)
//                .content(content.toString())
//                .mediaType(MediaType.parse("application/json;charset=utf-8"))
//                .build()
//                .execute(new ResponseCallBack());
        OkHttpUtils.postString()
                .url(CommonData.historyContract)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 2 * 60 * 1000;//2分钟请求一次数据
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);

    }

    private class ResponseCallBack extends MyCallback2 {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(UpdateCheckNews.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ResponseBean2 response, int id) {
            if (response.getCode() == 0) {
                int counts = 0;
                if (response.getData() != null) {
                    List<ContractBean> list = response.getData();
                    for (ContractBean contractBean : list) {
                        if (contractBean.getStatues() == 1 || contractBean.getStatues() == 2) {
                            counts++;
                        }
                    }
                }
                AuditCount auditCount = DaoBean.getAuditCountByUserName(username);
                DaoSession daoSession = DaoManager.getInstance().getDaoSession();
                AuditCountDao auditCountDao = daoSession.getAuditCountDao();
                int originalCount = 0;
                if (auditCount != null) {
                    originalCount = auditCount.getCounts();
                    if (counts > originalCount) {
                        updateCounts();
                        auditCount.setCounts(counts);
                        auditCountDao.update(auditCount);
                    }
                } else {
                    auditCount = new AuditCount();
                    auditCount.setUsername(username);
                    auditCount.setCounts(originalCount);
                    if (counts > originalCount) {
                        updateCounts();
                        auditCount.setCounts(counts);
                        auditCountDao.insert(auditCount);
                    }

                }

            }

        }


    }

    private void updateCounts() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(UpdateCheckNews.this, HistoryActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(UpdateCheckNews.this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker(getString(R.string.newAction));
        builder.setContentTitle(getString(R.string.luoxiangqiche));
        builder.setContentText(getString(R.string.newsOrderAudited));
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
        notificationManager.notify(1, notification);

    }

}
