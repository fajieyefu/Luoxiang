package fajieyefu.com.luoxiang.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ContractAdapter;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import fajieyefu.com.luoxiang.widget.XListView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017/4/15.
 */

public class ContractAuditActivity extends BaseActivity implements XListView.IXListViewListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.apply_lv)
    XListView applyLv;
    private String username;
    private String password;
    private int pagesCount =1;
    private ArrayList<ContractBean> contracts = new ArrayList<>();
    private ContractAdapter contractAdapter;
    private int type=0; //0上拉刷新、或者1下拉加载

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_audit);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData() {
        username = DaoBean.getUseInfoById(1).getUsername();
        password = DaoBean.getUseInfoById(1).getPassword();
        Log.i("username",username);
        Log.i("password",password);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.OrderAplyListURL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }

    private void initView() {
        title.setTitleText("订单审核");
        applyLv.setPullRefreshEnable(true);
        applyLv.setPullLoadEnable(true);
        applyLv.setAutoLoadEnable(true);
        applyLv.setXListViewListener(this);
        applyLv.setRefreshTime(ToolUtil.getTime());
        contractAdapter = new ContractAdapter(contracts,this);
        applyLv.setAdapter(contractAdapter);
    }

    /**
     * 上拉刷新的逻辑
     */
    @Override
    public void onRefresh() {
        pagesCount=1;
        type=0;
        loadData();

    }

    /**
     * 下拉加载的逻辑
     */
    @Override
    public void onLoadMore() {
        type=1;
        pagesCount++;
        loadData();

    }
    private void StopLoad() {
        applyLv.stopRefresh();
        applyLv.stopLoadMore();
        applyLv.setRefreshTime(ToolUtil.getTime());
    }
    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ContractAuditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
                if (response.getCode()==0){
                    if (type==0){
                        contracts = response.getData().contracts;
                    }else{
                        contracts.addAll(response.getData().contracts);
                    }
                }else{
                    Toast.makeText(ContractAuditActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
            StopLoad();
        }
    }
}
