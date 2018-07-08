package fajieyefu.com.luoxiang.main;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.UserInfoDao;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.util.DaoManager;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017/4/15.
 */

public class LoginActivity extends BaseActivity  {
    private static final int IMAGE_PICKER = 1;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.logo)
    ImageView logo;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;
    private UserInfo userInfo;
    private String[] permissions = {Manifest.permission.ACCESS_NOTIFICATION_POLICY};
    private AlertDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        initData();
        checkMyPermission();


    }

    private void checkMyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_NOTIFICATION_POLICY);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
    }

    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("获取通知")
                .setMessage("需要获取通话权限\n否则，您将无法正常在应用中获取信息通知")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginActivity.this, "信息通知受限！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).setCancelable(false).show();
    }


        private void startRequestPermission() {
            ActivityCompat.requestPermissions(this, permissions, 322);
        }


    private void initData() {
        daoSession = DaoManager.getInstance().getDaoSession();
        userInfoDao = daoSession.getUserInfoDao();
        userInfo = getInfoById(1);
        if (userInfo != null && userInfo.getUsername() != null) {
            username.setText(userInfo.getUsername());
            if (userInfo.getRembPsw() == true) {
                password.setText(userInfo.getPassword());
                checkbox.setChecked(true);
            }
        }

    }

    public UserInfo getInfoById(long id) {
        QueryBuilder<UserInfo> queryBuilder = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (UserInfo) query.unique();

    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        login.setClickable(false);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username.getText());
            jsonObject.put("password", password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils.postString()
                .url(CommonData.loginURL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ReponseCallBack());


    }

    private class ReponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.i("数据请求出现错误", e.getMessage());
            Toast.makeText(LoginActivity.this, "网络连接出现错误", Toast.LENGTH_SHORT).show();
            login.setClickable(true);
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                userInfo = new UserInfo();
                userInfo.setId((long) 1);
                userInfo.setUsername(username.getText().toString());
                userInfo.setPassword(password.getText().toString());
                if (checkbox.isChecked()) {
                    userInfo.setRembPsw(true);
                } else {
                    userInfo.setRembPsw(false);
                }
                int rId = response.getData().userInfo.getRId();
                userInfoDao.deleteAll();
                userInfo.setRId(rId);
                userInfoDao.insert(userInfo);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                initxiaomiPush();
                finish();

            } else {
                Toast.makeText(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            login.setClickable(true);
        }
    }

    private void initxiaomiPush() {
        if(shouldInit()) {
            MiPushClient.registerPush(this, CommonData.APP_ID, CommonData.APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(CommonData.TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(CommonData.TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 322) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许锣响汽车使用通知权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginActivity.this, "信息通知受限！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).setCancelable(false).show();
    }
    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
