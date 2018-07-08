package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinner;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-19.
 */

public class CustomerSelectActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.pick_custom)
    MySpinner pickCustom;
    @BindView(R.id.next)
    Button next;
    private int orderId;
    private String standardId;
    private ToolUtil toolUtil;
    private UserInfo userInfo;
    private List<Inventory> intentInventory;
    private String orderNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_input_select);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", 0);
        standardId = intent.getStringExtra("standardId");
        orderNumber=intent.getStringExtra("orderNumber");
        intentInventory= (List<Inventory>) intent.getSerializableExtra("data");
        loadData();

    }

    private void loadData() {
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        userInfo = DaoBean.getUseInfoById(1);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userInfo.getUsername());
            jsonObject.put("password", userInfo.getPassword());
        } catch (Exception e) {
        }

        OkHttpUtils.postString()
                .url(CommonData.contractInputURL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());

    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        getStandardsData();
    }
    private void getStandardsData() {
        String cCusCode = pickCustom.getSelected_code();
        String username = DaoBean.getUseInfoById(1).getUsername();
        String password = DaoBean.getUseInfoById(1).getPassword();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("standardId", standardId);
            jsonObject.put("cCusCode", cCusCode);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            Log.i("content", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("content", jsonObject.toString());
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        OkHttpUtils.postString()
                .url(CommonData.StandardDetails)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack2());
    }
    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(CustomerSelectActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<ObtainBean> customers = response.getData().customers;
                pickCustom.setData(customers);
            } else {
                Toast.makeText(CustomerSelectActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ResponCallBack2 extends MyCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(CustomerSelectActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<InventoryClass> inventoryClass = response.getData().inventory;
                DaoBean.deleteInventoryClassAll();
                DaoBean.insertInventoryClassList(inventoryClass);
                DaoBean.deleteInventoryAll();
                for (int i = 0; i < inventoryClass.size(); i++) {
                    List<Inventory> inventory = inventoryClass.get(i).getInventoryDetails();
                    switch (inventory.get(0).getStyleflag()){
                        case 0:
                        case 1:
                        case 3:
                            break;
                        case 2:
                            if (inventory != null) {
                                DaoBean.insertInventoryList(inventory);
                            }
                            break;

                    }

                }
                List<Inventory> removeData = new ArrayList<>();
                for (Inventory i :intentInventory){
                    if(i.getStyleflag()==2){
                        removeData.add(i);
                    }
                }
                intentInventory.removeAll(removeData);
                DaoBean.insertInventoryList(intentInventory);
                Intent intent = new Intent(CustomerSelectActivity.this, StockContractInputActivity.class);
                intent.putExtra("customer", response.getData().customer);
                intent.putExtra("orderNumber",orderNumber);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(CustomerSelectActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }


        }
    }
}
