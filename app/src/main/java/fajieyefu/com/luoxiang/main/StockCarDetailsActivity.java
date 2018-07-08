package fajieyefu.com.luoxiang.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ContractDetailsAdapter;
import fajieyefu.com.luoxiang.bean.ContractDetail;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.LinearLayoutForListView;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-19.
 */

public class StockCarDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.contract_details)
    LinearLayoutForListView contractDetailsLV;
    @BindView(R.id.title)
    TitleLayout title;
    private int orderId;
    private UserInfo userInfo;
    private Button more;
    private ToolUtil toolUtil;
    private Dialog dialog;
    private String standardId;
    private  List<Inventory> data;
    private String orderNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_car_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId",0);
        initView();
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.more_layout, null);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        commit.setVisibility(View.GONE);
        TextView modify = (TextView) view.findViewById(R.id.modify);
        modify.setVisibility(View.GONE);
        TextView dealWith = (TextView) view.findViewById(R.id.dealWith);
        dealWith.setVisibility(View.VISIBLE);
        dealWith.setOnClickListener(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void initView() {
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(this);
        title.setTitleText("库存车配置");
        userInfo = DaoBean.getUseInfoById(1);
        JSONObject content = new JSONObject();
        try {
            content.put("orderId", orderId);
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.contractDetails)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dealWith:
                Intent intent = new Intent(this,CustomerSelectActivity.class);
                intent.putExtra("standardId",standardId);
                intent.putExtra("orderId",orderId);
                intent.putExtra("data", (Serializable)data);
                intent.putExtra("orderNumber",orderNumber);
                startActivity(intent);
                finish();
                break;
            case R.id.more:
                dialog.show();
            break;

        }

    }


    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(StockCarDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
               data = new ArrayList<>();
                List<ContractDetail> contractDetails = response.getData().order;
                data.addAll(contractDetails.get(0).getInventoryDetails());
                ContractDetailsAdapter adapter = new ContractDetailsAdapter(data,StockCarDetailsActivity.this);
                contractDetailsLV.setAdapter(adapter);
                standardId=contractDetails.get(0).getStandardId();
                orderNumber=contractDetails.get(0).getOrderNumber();
            } else {
                Toast.makeText(StockCarDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
