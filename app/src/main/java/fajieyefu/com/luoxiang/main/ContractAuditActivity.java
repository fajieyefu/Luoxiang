package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import fajieyefu.com.luoxiang.bean.ResponseBean2;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback2;
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
    private List<ContractBean> contracts = new ArrayList<>();
    private ContractAdapter contractAdapter;
    private int type=0; //0上拉刷新、或者1下拉加载
    private ToolUtil toolUtil;

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
        contractAdapter = new ContractAdapter(contracts,ContractAuditActivity.this);
        applyLv.setAdapter(contractAdapter);
        applyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(ContractAuditActivity.this,ContractAuditDetailsActivity.class);
                intent.putExtra("orderId",contracts.get(position).getOrderId());
                startActivity(intent);

            }
        });
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this,"请稍等","正在加载数据...");

    }

    /**
     * 下拉刷新的逻辑
     */
    @Override
    public void onRefresh() {
        pagesCount=1;
        type=0;
        loadData();

    }

    /**
     * 上拉加载的逻辑
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
    private class ResponseCallBack extends MyCallback2 {

        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractAuditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ResponseBean2 response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode()==0){
                contracts.clear();
                if (response.getData()!=null){
                    contracts.addAll(response.getData());
                }
                contractAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(ContractAuditActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            StopLoad();
        }
    }
}
