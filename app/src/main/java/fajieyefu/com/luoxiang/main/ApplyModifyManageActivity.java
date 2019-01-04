package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.HistoryAdapter;
import fajieyefu.com.luoxiang.adapter.HistoryHideCustomerAdapter;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.ResponseBean2;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.MyCallback2;
import fajieyefu.com.luoxiang.util.ToolUtil;
import fajieyefu.com.luoxiang.widget.XListView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-12-10.
 */

public class ApplyModifyManageActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.history_lv)
    ListView historyLv;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.customer_name)
    EditText customerName;
    @BindView(R.id.orderNumber)
    EditText orderNumber;
    @BindView(R.id.endCustomerName)
    EditText endCustomerName;
    @BindView(R.id.endCustomerPhone)
    EditText endCustomerPhone;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.filterLayout)
    LinearLayout filterLayout;
    @BindView(R.id.shade_layout)
    LinearLayout shadeLayout;
    private ArrayList<ContractBean> contracts = new ArrayList<>();
    private HistoryHideCustomerAdapter contractAdapter;
    private ToolUtil toolUtil = new ToolUtil();
    private Button more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_contract_manage);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        title.setTitleText("到厂变更申请");
        contractAdapter = new HistoryHideCustomerAdapter(contracts,this);
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(this);
        shadeLayout.setOnClickListener(this);
        historyLv.setAdapter(contractAdapter);
        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int orderId = contracts.get(position).getOrderId();
                Intent intent = new Intent();
                if (contracts.get(position).getIsSkeleton() == 1) {
                    intent.setClass(ApplyModifyManageActivity.this, HistorySkeletonDetailsActivity.class);
                } else {
                    intent.setClass(ApplyModifyManageActivity.this, HistoryDetailsActivity.class);
                }
                intent.putExtra("last_activity","ApplyModifyManageActivity");
                intent.putExtra("orderId", orderId);
                intent.putExtra("type",1);
                intent.putExtra("dpc", contracts.get(position).getDpc());
                intent.putExtra("dtc", contracts.get(position).getDdtc());
                intent.putExtra("wc", contracts.get(position).getWc());
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        String orderString = orderNumber.getText().toString().trim();
        String endCustomer = endCustomerName.getText().toString().trim();
        String customer = customerName.getText().toString().trim();
        String endphone =endCustomerPhone.getText().toString().trim();
        searchOrder(orderString, endCustomer,endphone,customer);
    }

    private void searchOrder(String orderString, String endCustomer, String endphone, String customer) {
        if (TextUtils.isEmpty(orderString)&&TextUtils.isEmpty(endCustomer)&&TextUtils.isEmpty(endphone)&&TextUtils.isEmpty(customer)){
            Toast.makeText(this, "请至少填写一个搜索内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", DaoBean.getUseInfoById(1).getUsername());
            jsonObject.put("password", DaoBean.getUseInfoById(1).getPassword());
            jsonObject.put("endCustomerName",endCustomer);
            jsonObject.put("endCustomerPhone",endphone);
            jsonObject.put("cCusName",customer);
            jsonObject.put("orderNumber",orderString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.searchOrderUrl)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new MyCallBack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                if (filterLayout.getVisibility() == View.VISIBLE) {
                    filterLayout.setVisibility(View.GONE);
                    historyLv.setEnabled(true);
                } else {
                    filterLayout.setVisibility(View.VISIBLE);
                    historyLv.setEnabled(false);
                }
                break;
            case R.id.shade_layout:
                notifyThisPagesListView();
                break;

        }
    }
    void notifyThisPagesListView() {
        if (contracts.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            historyLv.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            historyLv.setVisibility(View.VISIBLE);
        }
        filterLayout.setVisibility(View.GONE);
        historyLv.setEnabled(true);
        contractAdapter.notifyDataSetChanged();
    }

    private class MyCallBack extends MyCallback2 {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ApplyModifyManageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onResponse(ResponseBean2 response, int id) {
            if (response.getCode() == 0) {
                contracts.clear();
                if (response.getData() != null) {
                    contracts.addAll(response.getData());
                }
            } else {
                Toast.makeText(ApplyModifyManageActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            if (contracts.size() == 0) {
                emptyLayout.setVisibility(View.VISIBLE);
                historyLv.setVisibility(View.GONE);
            } else {
                emptyLayout.setVisibility(View.GONE);
                historyLv.setVisibility(View.VISIBLE);
            }
            notifyThisPagesListView();
            toolUtil.dismissProgressDialog();
        }
    }
}
