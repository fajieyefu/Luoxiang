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

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.HistoryAdapter;
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

public class HistoryActivity extends BaseActivity implements XListView.IXListViewListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.history_lv)
    XListView historyLv;
    private int pagesCount =1;
    private ArrayList<ContractBean> contracts = new ArrayList<>();
    private HistoryAdapter contractAdapter;
    private int type=0; //0上拉刷新、或者1下拉加载
    private ToolUtil toolUtil =new ToolUtil();
    private int flag ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_contract);
        ButterKnife.bind(this);
        initView();
        loadData();

    }

    private void initView() {
        historyLv.setPullRefreshEnable(true);
        historyLv.setPullLoadEnable(true);
        historyLv.setAutoLoadEnable(true);
        historyLv.setXListViewListener(this);
        historyLv.setRefreshTime(ToolUtil.getTime());
        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(HistoryActivity.this,HistoryDetailsActivity.class);
                intent.putExtra("orderId",contracts.get(position-1).getOrderId());
                intent.putExtra("dpc",contracts.get(position-1).getDpc());
                intent.putExtra("dtc",contracts.get(position-1).getDdtc());
                intent.putExtra("wc",contracts.get(position-1).getWc());
                startActivity(intent);
            }
        });
        contractAdapter = new HistoryAdapter(contracts,this);
        historyLv.setAdapter(contractAdapter);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",0);
        if (flag==0){
            title.setTitleText("待处理订单");
        }else{
            title.setTitleText("历史订单");

        }
        toolUtil.showProgressDialog(this,"请稍等","正在加载数据...");


    }

    /**
     * 初始化数据
     */
    private void loadData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", DaoBean.getUseInfoById(1).getUsername());
            jsonObject.put("password", DaoBean.getUseInfoById(1).getPassword());
            jsonObject.put("curr",pagesCount);//页数
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
        pagesCount=1;
        type=0;
        contracts.clear();
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
    private class MyCallBack extends MyCallback2 {


        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ResponseBean2 response, int id) {

            if (response.getCode()==0){
                if (response.getData()!=null){
                    if (flag==1){
                        for (ContractBean contractBean:response.getData()){
                            if (contractBean.getWc()==1){
                                contracts.add(contractBean);
                            }
                        }
                    }else{
                        contracts.addAll(response.getData());
                        for (ContractBean contractBean:contracts){
                            System.out.println("contractBean:"+contractBean.getOrderNumber());
                        }
                    }
                }
            }else{
                Toast.makeText(HistoryActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            contractAdapter.notifyDataSetChanged();
            StopLoad();
            toolUtil.dismissProgressDialog();

        }
    }

    @Override
    protected void onRestart() {
        onRefresh();
        super.onRestart();
    }
}
