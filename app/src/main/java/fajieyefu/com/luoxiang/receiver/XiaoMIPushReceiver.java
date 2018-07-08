package fajieyefu.com.luoxiang.receiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import fajieyefu.com.luoxiang.util.ImageFactory;
import fajieyefu.com.luoxiang.util.MyCallback;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.content.Context.NOTIFICATION_SERVICE;
import static fajieyefu.com.luoxiang.util.ToolUtil.showNotification;

/**
 * Created by Administrator on 2017-11-18.
 */

public class XiaoMIPushReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        Intent intent=null;
        String content="";
        ReponseBean reponseBean= new Gson().fromJson(mMessage,ReponseBean.class);
        PushNewsInfo pushNewsInfo = new PushNewsInfo();
        List<PushNewsInfo> pushNewsInfos = DaoBean.getTargetPushNewsInfo(reponseBean.getData().pushBean.getPushId());
        if (pushNewsInfos!=null&&pushNewsInfos.size()>0){
            return;
        }
        pushNewsInfo.setPushId(reponseBean.getData().pushBean.getPushId());
        pushNewsInfo.setPushType(reponseBean.getData().pushBean.getPushType());
        DaoBean.insertPushNewsInfo(pushNewsInfo);
        PushBean pushBean = null;
        switch(reponseBean.getCode()){
            case 0://新电话通知
                intent = new Intent(context, ClueManageActivity.class);
                showNotification(context,message.getTitle(),message.getDescription(),intent,1);
                break;
            case 1:
                if (reponseBean.getData().pushBean.getFlag()==1){
                    content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"审批通过!";
                }else{
                    content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"被驳回!";
                }
                intent = new Intent(context, HistoryActivity.class);

                showNotification(context,message.getTitle(),content,intent,2);
                break;
            case 3:
                if (reponseBean.getData().pushBean.getFlag()==1){
                    content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"变更信息通过审核";
                }else{
                    content="合同号:"+reponseBean.getData().pushBean.getOrderNumber()+"变更信息被驳回!";
                }
                intent = new Intent(context, HistoryActivity.class);

                showNotification(context,message.getTitle(),content,intent,3);
                break;
            case 4:
                pushBean  = reponseBean.getData().pushBean;
                content="客戶:"+pushBean.getClientName()+"电话:"+pushBean.getClientPhone()+" 抓紧时间联系！";
                intent = new Intent(context, ClueManageActivity.class);
                showNotification(context,message.getTitle(),content,intent,4);
                break;
        }
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initRegid();
                    }
                }).start();


            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }



    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
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

                    initRegid();
                }
            }).start();

        }

        @Override
        public void onResponse(ReponseBean response, int id) {


        }
    }
    private void initRegid() {
        JSONObject content = new JSONObject();
        UserInfo userInfo=DaoBean.getUseInfoById(1);
        if(userInfo!=null){
            try {
                content.put("username",userInfo.getUsername());
                content.put("regid",mRegId);
            } catch (JSONException e) {
            }

            OkHttpUtils.postString()
                    .url(CommonData.initRegid)
                    .content(content.toString())
                    .mediaType(MediaType.parse("application/json;charset=utf-8"))
                    .build()
                    .execute(new ResponseCallBack());
        }else{
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e1) {
            }

            initRegid();

        }


    }

}
