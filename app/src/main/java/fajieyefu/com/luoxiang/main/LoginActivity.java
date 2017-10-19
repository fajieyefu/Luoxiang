package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        initData();
//        initNeedData();

    }

//    private void initNeedData() {
//        File file = new File(CommonData.PIC_TEMP);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        for (int i = 1; i < 11; i++) {
//            file = new File(CommonData.PIC_TEMP + i + ".jpg");
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

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
                userInfoDao.deleteAll();
                userInfoDao.insert(userInfo);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }


}
