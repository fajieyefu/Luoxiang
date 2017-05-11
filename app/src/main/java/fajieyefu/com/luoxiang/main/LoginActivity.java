package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.dao.UserInfoDao;
import fajieyefu.com.luoxiang.util.DaoManager;
import fajieyefu.com.luoxiang.util.MyCallback;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017/4/15.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        daoSession= DaoManager.getInstance().getDaoSession();
        userInfoDao = daoSession.getUserInfoDao();
         userInfo = getInfoById(1);
        if (userInfo!=null){
            if (userInfo.getRembPsw()==true){
                username.setText(userInfo.getUsername());
                password.setText(userInfo.getPassword());
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
            if (response.getCode()==0){
                userInfo= new UserInfo();
                userInfo.setId((long)1);
                userInfo.setUsername(username.getText().toString());
                userInfo.setRembPsw(true);
                if (checkbox.isChecked()){
                    userInfo.setPassword(password.getText().toString());
                }else{
                    userInfo.setPassword(null);
                }
                if (getInfoById(1)==null){
                    userInfoDao.insert(userInfo);
                }else{
                    userInfoDao.update(userInfo);
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else{
                Toast.makeText(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
