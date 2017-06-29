package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.dao.UserInfoDao;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.layout.MySpinner;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.DaoManager;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-05-05.
 */

public class ContractInputPage1 extends BaseActivity {


    @BindView(R.id.pick_custom)
    MySpinner pickCustom;
    @BindView(R.id.pick_biaozhun)
    MySpinner pickBiaozhun;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.stockCar)
    Button stockCar;
    @BindView(R.id.addCustomer)
    Button addCustomer;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;
    private UserInfo userInfo;
    private ToolUtil toolUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_select);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void initView() {
        title.setTitleText("选择标准类型和客户");
    }

    /**
     * 加载客户信息和标准
     */
    private void loadData() {
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this, "请稍后", "正在加载数据...");
        daoSession = DaoManager.getInstance().getDaoSession();
        userInfoDao = daoSession.getUserInfoDao();
        userInfo = getInfoById(1);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userInfo.getUsername());
            jsonObject.put("password", userInfo.getPassword());
        } catch (Exception e) {
//            e.printStackTrace();
        }


        OkHttpUtils.postString()
                .url(CommonData.contractInputURL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());

    }

    public UserInfo getInfoById(long id) {
        QueryBuilder<UserInfo> queryBuilder = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (UserInfo) query.unique();

    }

    @OnClick({R.id.next, R.id.stockCar,R.id.addCustomer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                getStandardsData();
                break;
            case R.id.stockCar:
                Intent intent = new Intent(this, StockCarListActivity.class);
                startActivity(intent);
                finish();
                break;
            case  R.id.addCustomer:
                Intent intent2 = new Intent(this,AddCustomerActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    private void getStandardsData() {
        String standardId = pickBiaozhun.getSelected_code();
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
        toolUtil.showProgressDialog(this, "请稍后", "正在获取数据...");
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
            Toast.makeText(ContractInputPage1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<ObtainBean> customers = response.getData().customers;
                List<ObtainBean> standards = response.getData().standards;
                pickBiaozhun.setData(standards);
                pickCustom.setData(customers);
            } else {
                Toast.makeText(ContractInputPage1.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class ResponCallBack2 extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractInputPage1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    if (inventory != null) {
                        DaoBean.insertInventoryList(inventory);
                    }
                }

                Intent intent = new Intent(ContractInputPage1.this, ContractInputActivity.class);
                intent.putExtra("customer", response.getData().customer);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(ContractInputPage1.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }

        }
    }
}
