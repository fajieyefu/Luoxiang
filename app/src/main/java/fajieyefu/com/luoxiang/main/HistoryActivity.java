package fajieyefu.com.luoxiang.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.HistoryAdapter;
import fajieyefu.com.luoxiang.adapter.SpinnerChoosyTypeAdapter;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ContractTypeBean;
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

public class HistoryActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.history_lv)
    XListView historyLv;
    @BindView(R.id.chooseType)
    Button chooseType;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.orderNumber)
    EditText orderNumber;
    @BindView(R.id.endCustomerName)
    EditText endCustomerName;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.filterLayout)
    LinearLayout filterLayout;
    @BindView(R.id.shade_layout)
    LinearLayout shadeLayout;
    private int pagesCount = 1;
    private ArrayList<ContractBean> contracts = new ArrayList<>();
    private ArrayList<ContractBean> contractResult = new ArrayList<>();
    private HistoryAdapter contractAdapter;
    private int type = 0; //0上拉刷新、或者1下拉加载
    private ToolUtil toolUtil = new ToolUtil();
    private int flag;
    private Dialog dialog;
    private ListView listView;
    private List<ContractTypeBean> contractTypes = new ArrayList<>();
    private Button more;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_contract);
        ButterKnife.bind(this);
        initView();
        loadData();

    }

    private void initView() {
        ContractTypeBean contractTypeBean = new ContractTypeBean("全部  ", 0, 1);
        ContractTypeBean contractTypeBean1 = new ContractTypeBean("待审核  ", 1, 0);
        ContractTypeBean contractTypeBean2 = new ContractTypeBean("驳回  ", 2, 0);
        ContractTypeBean contractTypeBean3 = new ContractTypeBean("待排产  ", 3, 0);
        ContractTypeBean contractTypeBean4 = new ContractTypeBean("待提车  ", 4, 0);
        ContractTypeBean contractTypeBean5 = new ContractTypeBean("已完成  ", 5, 0);
        contractTypes.clear();
        contractTypes.add(contractTypeBean);
        contractTypes.add(contractTypeBean1);
        contractTypes.add(contractTypeBean2);
        contractTypes.add(contractTypeBean3);
        contractTypes.add(contractTypeBean4);
        contractTypes.add(contractTypeBean5);
        historyLv.setPullRefreshEnable(true);
        historyLv.setPullLoadEnable(true);
        historyLv.setAutoLoadEnable(true);
        historyLv.setXListViewListener(this);
        historyLv.setRefreshTime(ToolUtil.getTime());
        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int orderId = contracts.get(position - 1).getOrderId();
                Intent intent = new Intent();
                if (contracts.get(position - 1).getIsSkeleton() == 1) {
                    intent.setClass(HistoryActivity.this, HistorySkeletonDetailsActivity.class);
                } else {
                    intent.setClass(HistoryActivity.this, HistoryDetailsActivity.class);
                }
                intent.putExtra("orderId", orderId);
                intent.putExtra("dpc", contracts.get(position - 1).getDpc());
                intent.putExtra("dtc", contracts.get(position - 1).getDdtc());
                intent.putExtra("wc", contracts.get(position - 1).getWc());
                startActivity(intent);
            }
        });
        contractAdapter = new HistoryAdapter(contracts, this);
        historyLv.setAdapter(contractAdapter);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        if (flag == 0) {
            title.setTitleText(getString(R.string.daichulidingdan));
        } else {
            title.setTitleText(getString(R.string.historyOrder));

        }
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        more.setOnClickListener(this);
        shadeLayout.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    private void loadData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", DaoBean.getUseInfoById(1).getUsername());
            jsonObject.put("password", DaoBean.getUseInfoById(1).getPassword());
            jsonObject.put("curr", pagesCount);//页数
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.historyContract)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new MyCallBack());
    }

    @Override
    public void onRefresh() {
        pagesCount = 1;
        type = 0;
        contracts.clear();
        contractResult.clear();
        loadData();
    }

    @Override
    public void onLoadMore() {
//        type=1;
//        pagesCount++;
//        loadData();
    }

    private void StopLoad() {
        historyLv.stopRefresh();
        historyLv.stopLoadMore();
        historyLv.setRefreshTime(ToolUtil.getTime());
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


    @OnClick({R.id.chooseType, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chooseType:
                initTypeDialog();
                break;
            case R.id.search:
                String orderString = orderNumber.getText().toString().trim();
                String endCustomer = endCustomerName.getText().toString().trim();
                searchOrder(orderString, endCustomer);
                break;

        }
    }

    private void searchOrder(String orderString, String endCustomer) {
        contracts.clear();
        if (!TextUtils.isEmpty(orderString) || !TextUtils.isEmpty(endCustomer)) {
            for (ContractBean contractBean : contractResult) {
                if (!TextUtils.isEmpty(orderString) && !contractBean.getOrderNumber().contains(orderString)) {
                    continue;
                }
                if (!TextUtils.isEmpty(endCustomer) && (contractBean.getEndCustomerName()==null||!contractBean.getEndCustomerName().contains(endCustomer))) {
                    continue;
                }
                contracts.add(contractBean);
            }
        }else{
            contracts.addAll(contractResult);
        }
        notifyThisPagesListView();

    }


    private class MyCallBack extends MyCallback2 {


        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ResponseBean2 response, int id) {

            if (response.getCode() == 0) {
                if (response.getData() != null) {
//                    if (flag == 1) {
//                        for (ContractBean contractBean : response.getData()) {
//                            if (contractBean.getWc() == 1) {
//                                contracts.add(contractBean);
//                            }
//                        }
//                    } else {
                    contracts.addAll(response.getData());
                    contractResult.addAll(response.getData());
                    chooseType.setText("全部  ");
//                    }
                }
            } else {
                Toast.makeText(HistoryActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            if (contracts.size() == 0) {
                emptyLayout.setVisibility(View.VISIBLE);
                historyLv.setVisibility(View.GONE);
            } else {
                emptyLayout.setVisibility(View.GONE);
                historyLv.setVisibility(View.VISIBLE);
            }
            notifyThisPagesListView();
            StopLoad();
            toolUtil.dismissProgressDialog();

        }
    }

    @Override
    protected void onRestart() {
        onRefresh();
        super.onRestart();
    }

    private void initTypeDialog() {

        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.spinner_dialog, null);
        listView = (ListView) view.findViewById(R.id.spinner_list);
        SpinnerChoosyTypeAdapter spinnerAdapter = new SpinnerChoosyTypeAdapter(this, contractTypes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseType.setText(contractTypes.get(position).getTypeName());
                for (ContractTypeBean contractTypeBean : contractTypes) {
                    contractTypeBean.setSelect_flag(0);
                }
                selectData(contractTypes.get(position).getTypeCode());
                contractTypes.get(position).setSelect_flag(1);
                dialog.dismiss();
            }
        });

        listView.setAdapter(spinnerAdapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();

    }

    private void selectData(int position) {
        contracts.clear();
        switch (position) {
            case 0:
                contracts.addAll(contractResult);
                break;
            case 1:
                for (ContractBean contractBean : contractResult) {
                    if (contractBean.getNq_flag() == 0) {
                        contracts.add(contractBean);
                    }
                }
                break;
            case 2:
                for (ContractBean contractBean : contractResult) {
                    if (contractBean.getNq_flag() == 2) {
                        contracts.add(contractBean);
                    }
                }
                break;
            case 3:
                for (ContractBean contractBean : contractResult) {
                    if (contractBean.getNq_flag() == 1 && contractBean.getDdtc() == 0) {
                        contracts.add(contractBean);
                    }
                }
                break;
            case 4:
                for (ContractBean contractBean : contractResult) {
                    if (contractBean.getDdtc() == 1 && contractBean.getWc() == 0) {
                        contracts.add(contractBean);
                    }
                }
                break;
            case 5:
                for (ContractBean contractBean : contractResult) {
                    if (contractBean.getWc() == 1) {
                        contracts.add(contractBean);
                    }
                }
                break;
        }
        notifyThisPagesListView();
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
}
