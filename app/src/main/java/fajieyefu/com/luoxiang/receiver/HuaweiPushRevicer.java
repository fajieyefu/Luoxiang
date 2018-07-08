package fajieyefu.com.luoxiang.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huawei.hms.support.api.push.PushReceiver;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.PushBean;
import fajieyefu.com.luoxiang.bean.PushNewsInfo;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.main.ClueManageActivity;
import fajieyefu.com.luoxiang.main.HistoryActivity;
import fajieyefu.com.luoxiang.util.MyCallback;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.content.Context.NOTIFICATION_SERVICE;
import static fajieyefu.com.luoxiang.util.ToolUtil.showNotification;

/**
 * Created by meng on 2018/4/23.
 */

public class HuaweiPushRevicer extends PushReceiver {
    String token;

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        super.onEvent(context, event, extras);
    }

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        this.token= token;
        initHWToken(token);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msgBytes, Bundle extras) {
        /*try {
            String content = new String(msgBytes,"UTF-8");
            Intent intent = null;
            ReponseBean reponseBean= new Gson().fromJson(content,ReponseBean.class);
            PushNewsInfo pushNewsInfo = new PushNewsInfo();
            List<PushNewsInfo> pushNewsInfos = DaoBean.getTargetPushNewsInfo(reponseBean.getData().pushBean.getPushId());
            if (pushNewsInfos!=null&&pushNewsInfos.size()>0){
                return true;
            }
            pushNewsInfo.setPushId(reponseBean.getData().pushBean.getPushId());
            pushNewsInfo.setPushType(reponseBean.getData().pushBean.getPushType());
            DaoBean.insertPushNewsInfo(pushNewsInfo);
            PushBean pushBean = null;
            switch(reponseBean.getCode()){
                case 0://新电话通知
                    intent = new Intent(context, ClueManageActivity.class);
                    showNotification(context,"锣响汽车","有新的电话信息需要跟进",intent,1);
                    break;
                case 1:
                    if (reponseBean.getData().pushBean.getFlag()==1){
                        content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"审批通过!";
                    }else{
                        content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"被驳回!";
                    }
                    intent = new Intent(context, HistoryActivity.class);

                    showNotification(context,"锣响汽车",content,intent,2);
                    break;
                case 3:
                    if (reponseBean.getData().pushBean.getFlag()==1){
                        content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"变更信息通过审核";
                    }else{
                        content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"变更信息被驳回!";
                    }
                    intent = new Intent(context, HistoryActivity.class);

                    showNotification(context,"锣响汽车",content,intent,3);
                    break;
                case 4:
                    pushBean  = reponseBean.getData().pushBean;
                    content="客戶:"+pushBean.getClientName()+"电话:"+pushBean.getClientPhone()+" 抓紧时间联系！";
                    intent = new Intent(context, ClueManageActivity.class);
                    showNotification(context,"锣响汽车",content,intent,4);
                    break;
            }







            System.out.println(content);
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        return true;
    }

    @Override
    public void onPushMsg(Context context, byte[] msg, String token) {
        super.onPushMsg(context, msg, token);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
    }


    private void initHWToken(String token) {
        JSONObject content = new JSONObject();
        UserInfo userInfo= DaoBean.getUseInfoById(1);
        if(userInfo!=null){
            try {
                content.put("username",userInfo.getUsername());
                content.put("HW_token",token);
            } catch (JSONException e) {
            }

            OkHttpUtils.postString()
                    .url(CommonData.initHWToken)
                    .content(content.toString())
                    .mediaType(MediaType.parse("application/json;charset=utf-8"))
                    .build()
                    .execute(new ResponseCallBack());
        }else{
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e1) {
            }

            initHWToken(token);

        }


    }
    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000*60);
                    } catch (InterruptedException e1) {
                    }

                    initHWToken(token);
                }
            }).start();

        }

        @Override
        public void onResponse(ReponseBean response, int id) {


        }
    }

}
