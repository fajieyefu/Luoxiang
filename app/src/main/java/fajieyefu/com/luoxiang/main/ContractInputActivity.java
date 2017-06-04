package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.InventoryClassAdapter;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.ObtainBean;
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
 * Created by Administrator on 2017-05-06.
 */

public class ContractInputActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.inventory_lv)
    LinearLayoutForListView inventoryLv;
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.caculator)
    Button caculator;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.contacts_man)
    TextView contactsMan;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.applyDate)
    TextView applyDate;
    @BindView(R.id.select_info)
    LinearLayout selectInfo;
    @BindView(R.id.obtain_type)
    Spinner obtainType;
    @BindView(R.id.deposit)
    EditText deposit;
    @BindView(R.id.parent)
    ScrollView parent;
    @BindView(R.id.amt)
    EditText amt;
    @BindView(R.id.remark)
    EditText remark;
    private InventoryClassAdapter inventoryClassAdapter;
    private List<InventoryClass> inventorys = new ArrayList<>();
    private Button more;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePickerDialog datePicker;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private ObtainBean customer_bean;
    private ToolUtil toolUtil;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_details);
        ButterKnife.bind(this);
        initView();
        inventorys = DaoBean.loadAllInventoryClass();
        inventoryClassAdapter = new InventoryClassAdapter(this, inventorys);
        inventoryLv.setAdapter(inventoryClassAdapter);


    }

    @SuppressWarnings("ResourceType")
    private void initView() {
        userInfo = DaoBean.getUseInfoById(1);
        title.setTitleText("合同信息录入");
        Intent intent = getIntent();
        customer_bean = (ObtainBean) intent.getSerializableExtra("customer");
        customer.setText(customer_bean.getName());
        mobile.setText(customer_bean.getcCusHand());
        address.setText(customer_bean.getcCusAddress());
        contactsMan.setText(customer_bean.getcCusLPerson());
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ContractInputActivity.this);
                View view = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.more_layout, null);
                TextView save = (TextView) view.findViewById(R.id.save);
                TextView commit = (TextView) view.findViewById(R.id.commit);
                TextView modify = (TextView) view.findViewById(R.id.modify);
                save.setOnClickListener(ContractInputActivity.this);
                commit.setOnClickListener(ContractInputActivity.this);
                modify.setOnClickListener(ContractInputActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog.show();
            }
        });
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datePicker = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, mDateSetListener, mYear, mMonth, mDay);
        datePicker.setCancelable(true);
        datePicker.setCanceledOnTouchOutside(true);
        applyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });
        list = new ArrayList<String>();
        list.add("自提");
        list.add("配送");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        obtainType.setAdapter(adapter);
    }

//    private DatePicker findDatePicker(ViewGroup group) {
//        if (group != null) {
//            for (int i = 0, j = group.getChildCount(); i < j; i++) {
//                View child = group.getChildAt(i);
//                if (child instanceof DatePicker) {
//                    return (DatePicker) child;
//                } else if (child instanceof ViewGroup) {
//                    DatePicker result = findDatePicker((ViewGroup) child);
//                    if (result != null)
//                        return result;
//                }
//            }
//        }
//        return null;
//    }


    @OnClick(R.id.caculator)
    public void onViewClicked() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.price_weight_layout, null);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView weight = (TextView) view.findViewById(R.id.weight);
        int standarMoney = Integer.parseInt(DaoBean.getInventoryClassById(1).getStandardMoney());
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
        int actualPrice = standarMoney;
        int actualWeight=0;
        for (Inventory inventory : inventories) {
            actualPrice = actualPrice + Integer.parseInt(inventory.getDeMoney());
        }
        inventories =DaoBean.loadInventoryByCurrentAndShiJia();
        for (Inventory inventory:inventories){
            actualPrice=actualPrice+Integer.parseInt(inventory.getRealMoney())*Integer.parseInt(inventory.getCounts());
            actualWeight=actualWeight+Integer.parseInt(inventory.getWeight());
        }

        price.setText(actualPrice + "");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();

    }

    @Override
    public void onClick(final View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("你确定进行本次操作吗？");
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (v.getId()) {
                    case R.id.save:
                        Toast.makeText(ContractInputActivity.this, "save", Toast.LENGTH_SHORT).show();
                        commitData("save");
                        break;
                    case R.id.modify:
                        Toast.makeText(ContractInputActivity.this, "modify", Toast.LENGTH_SHORT).show();
                        commitData("modify");
                        break;
                    case R.id.commit:
                        Toast.makeText(ContractInputActivity.this, "commit", Toast.LENGTH_SHORT).show();
                        commitData("commit");
                        break;
                }
            }
        });
        dialog.show();
    }

    private void commitData(String msg) {
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
        JSONObject json = new JSONObject();
        JSONObject basic_info = new JSONObject();
        JSONArray config_info = new JSONArray();
        Gson gson = new GsonBuilder().create();
        try {
            switch (msg){
                case "save":
                    basic_info.put("apply_type","A");
                    break;
                case "commit":
                    basic_info.put("apply_type","B");
                    break;
            }
            basic_info.put("cCusCode", customer_bean.getCode());
            basic_info.put("apply_date", applyDate.getText().toString());
            basic_info.put("obtain_type", obtainType.getSelectedItem().toString());
            basic_info.put("deposit", deposit.getText().toString());
            basic_info.put("amt", amt.getText().toString());
            basic_info.put("remark", remark.getText().toString());
            json.put("basic_info", basic_info);
            for (Inventory inventory :inventories){
                config_info.put(new JSONObject(gson.toJson(inventory,Inventory.class)));
            }
            json.put("config_info",config_info);
            json.put("username",userInfo.getUsername());
            json.put("password",userInfo.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("data",json.toString());

        OkHttpUtils.postString()
                .url(CommonData.CommitAuditRuslt)
                .content(json.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDateDisplay();
        }
    };

    private void updateDateDisplay() {
        String expect_time = new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                .append("-").append(mDay)
                .toString();
        applyDate.setText(expect_time);
    }

    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractInputActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode()==0){
                Toast.makeText(ContractInputActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ContractInputActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
