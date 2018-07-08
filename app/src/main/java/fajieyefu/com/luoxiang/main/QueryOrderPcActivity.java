package fajieyefu.com.luoxiang.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ProNumAdapter;
import fajieyefu.com.luoxiang.bean.ProNumBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-11-07.
 */

public class QueryOrderPcActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.proNumLv)
    ListView proNumLv;
    private UserInfo userInfo;
    private ToolUtil toolUtil = new ToolUtil();
    private List<ProNumBean> ProNumData = new ArrayList<>();
    private ProNumAdapter proNumAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_orderpc_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        JSONObject param = new JSONObject();
        userInfo = DaoBean.getUseInfoById(1);
        try {
            param.put("username", userInfo.getUsername());
            param.put("password", userInfo.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
         proNumAdapter = new ProNumAdapter(QueryOrderPcActivity.this, ProNumData);
        proNumLv.setAdapter(proNumAdapter);

        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.getEnableNumOfDays)
                .content(param.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new EnableNumOfDaysResponCallBack());
    }

    private void initView() {
        title.setTitleText("工期查询");

    }

    private class EnableNumOfDaysResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            ProNumData.clear();
            if (response.getCode() == 0) {
                for (ProNumBean proNumBean : response.getData().proNum) {
                    if ((proNumBean.getFirst_flag() >= proNumBean.getFirst_num())
                            && (proNumBean.getUrgent_flag() >= proNumBean.getUrgent_num())
                            && (proNumBean.getNormal_flag() >= proNumBean.getNormal_num())) {
                        continue;
                    }
                    if (proNumBean.getEnable_flag() == 0) {
                        continue;
                    }
                    ProNumData.add(proNumBean);
                }
                proNumAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(QueryOrderPcActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
