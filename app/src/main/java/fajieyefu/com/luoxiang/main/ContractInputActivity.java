package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
import fajieyefu.com.luoxiang.layout.MySpinnerForInventry;
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
    @BindView(R.id.color)
    EditText color;
    @BindView(R.id.dingjin)
    CheckBox dingjin;
    @BindView(R.id.yunfei)
    CheckBox yunfei;
    @BindView(R.id.standardOr)
    CheckBox standardOr;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.carsCount)
    EditText carsCount;
    @BindView(R.id.powerType)
    Spinner powerType;
    @BindView(R.id.model)
    Spinner model;
    @BindView(R.id.height)
    Spinner height;
    @BindView(R.id.shuibaokong)
    EditText shuibaokong;
    @BindView(R.id.liangbaojiao)
    EditText liangbaojiao;
    @BindView(R.id.qianyinche)
    MySpinnerForInventry qianyinche;
    @BindView(R.id.orderNum)
    EditText orderNum;
    @BindView(R.id.pati)
    Spinner pati;
    @BindView(R.id.penggan)
    Spinner penggan;
    @BindView(R.id.beitaizhijia)
    Spinner beitaizhijia;
    private InventoryClassAdapter inventoryClassAdapter;
    private List<InventoryClass> inventorys = new ArrayList<>();
    private Button more;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePickerDialog datePicker;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter_powerType;
    private ArrayAdapter<String> adapter_model;
    private ArrayAdapter<String> adapter_height;
    private ArrayAdapter<String> adapter_pati;
    private ArrayAdapter<String> adapter_penggan;
    private ArrayAdapter<String> adapter_beitaizhijia;
    private ArrayAdapter<String> adapter_fangshuicao;
    private List<String> list;
    private List<String> list_powerType;
    private List<String> list_model;
    private List<String> list_height;
    private List<String> list_pati;
    private List<String> list_penggan;
    private List<String> list_beitaizhijia;
    private ObtainBean customer_bean;
    private ToolUtil toolUtil;
    private UserInfo userInfo;
    private String standardId;
    private String orderNumber;
    private Dialog dialog;
    private String orderId;
    private int commitType;
    private  Button preview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_details);
        ButterKnife.bind(this);
        initView();
        inventorys = DaoBean.loadAllInventoryClass();
//        for (InventoryClass ic : inventorys) {
//            Log.i("", ic.getCInvCCode());
//        }
        inventoryClassAdapter = new InventoryClassAdapter(this, inventorys);
        inventoryLv.setAdapter(inventoryClassAdapter);
        standardId = DaoBean.getInventoryClassById(1).getStandardId();


    }

    @SuppressWarnings("ResourceType")
    private void initView() {
        userInfo = DaoBean.getUseInfoById(1);
        title.setTitleText("合同信息录入");
        Intent intent = getIntent();
        customer_bean = (ObtainBean) intent.getSerializableExtra("customer");
        commitType = intent.getIntExtra("commitType", 0);
        orderNumber = intent.getStringExtra("orderNumber");
        orderId = intent.getStringExtra("orderId");
        customer.setText(customer_bean.getName());
        mobile.setText(customer_bean.getcCusHand());
        address.setText(customer_bean.getcCusAddress());
        contactsMan.setText(customer_bean.getcCusLPerson());
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ContractInputActivity.this);
                View view = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.more_layout, null);
                TextView commit = (TextView) view.findViewById(R.id.commit);
                TextView modify = (TextView) view.findViewById(R.id.modify);
                Button preview = (Button) view.findViewById(R.id.preview);
                preview.setVisibility(View.VISIBLE);
                modify.setVisibility(View.INVISIBLE);
                commit.setVisibility(View.VISIBLE);
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
        initSpinner();

    }

    private void initSpinner() {
        List<Inventory> qianyincheInventorys = DaoBean.getInventoryLikeCCode("14");
        if (qianyincheInventorys != null) {

            qianyinche.setData(qianyincheInventorys);
        }
        list = Arrays.asList(new String[]{"自提", "配送"});
        list_height = Arrays.asList(new String[]{"1.2", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0", "2.1", "2.2"});
        list_model = Arrays.asList(new String[]{"鹅", "直"});
        list_powerType = Arrays.asList(new String[]{"汽", "油"});
        list_pati = Arrays.asList(new String[]{"左", "右", "左右", "竖"});
        list_penggan = Arrays.asList(new String[]{"6", "10", "12"});
        list_beitaizhijia = Arrays.asList(new String[]{"左后", "右后", "后2","左中","右中"});


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter_powerType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_powerType);
        adapter_height = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_height);
        adapter_model = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_model);
        adapter_pati = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_pati);
        adapter_penggan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_penggan);
        adapter_beitaizhijia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_beitaizhijia);

        adapter_powerType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_height.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_pati.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_penggan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_beitaizhijia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        obtainType.setAdapter(adapter);
        model.setAdapter(adapter_model);
        powerType.setAdapter(adapter_powerType);
        height.setAdapter(adapter_height);
        penggan.setAdapter(adapter_penggan);
        pati.setAdapter(adapter_pati);
        beitaizhijia.setAdapter(adapter_beitaizhijia);

    }


    @OnClick(R.id.caculator)
    public void onViewClicked() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.price_weight_layout, null);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView weight = (TextView) view.findViewById(R.id.weight);
        int standarMoney = Integer.parseInt(DaoBean.getInventoryClassById(1).getStandardMoney());
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
        int actualPrice = standarMoney;
        double actualWeight = 0;
        for (Inventory inventory : inventories) {
            actualPrice = actualPrice + Integer.parseInt(inventory.getDeMoney());
            if (inventory.getWeight() != null) {
                actualWeight = actualWeight + Double.parseDouble(inventory.getWeight());
            }
        }
        inventories = DaoBean.loadInventoryByCurrentAndShiJia();
        for (Inventory inventory : inventories) {
            actualPrice = actualPrice + Integer.parseInt(inventory.getRealMoney()) * Integer.parseInt(inventory.getCounts());
        }
        weight.setText(actualWeight + "");
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
                    case R.id.modify:
                        commitData("modify");
                        break;
                    case R.id.commit:
                        commitData("commit");
                        break;
                }
            }
        });
        dialog.show();
    }

    private void commitData(String msg) {
        if (dialog != null) {
            dialog.dismiss();
        }
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
        JSONObject json = new JSONObject();
        JSONObject basic_info = new JSONObject();
        JSONArray config_info = new JSONArray();
        Gson gson = new GsonBuilder().create();
        try {
            switch (msg) {
                case "save":
                    basic_info.put("apply_type", "A");
                    break;
                case "commit":
                    basic_info.put("apply_type", "0");
                    break;
            }
            if (dingjin.isChecked()) {
                basic_info.put("dingjin_flag", 1);
                if (TextUtils.isEmpty(deposit.getText().toString())){
                    Toast.makeText(this, "请输入定金", Toast.LENGTH_SHORT).show();
                    return;
                }

                int dingjin_money = Integer.parseInt(deposit.getText().toString());
                int amt_money = Integer.parseInt(amt.getText().toString());
                float dingjin_rate = dingjin_money / (float) amt_money;
                if (amt_money != 0) {
                    basic_info.put("dingjin_rate", dingjin_rate);
                } else {
                    Toast.makeText(this, "金额不能为0", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                basic_info.put("dingjin_flag", 0);
            }
            if (yunfei.isChecked()) {
                basic_info.put("yunfei_flag", 1);
            } else {
                basic_info.put("yunfei_flag", 0);
            }
            if (standardOr.isChecked()) {
                basic_info.put("standard", 1);
            } else {
                if (TextUtils.isEmpty(remark.getText().toString())) {
                    Toast.makeText(this, "请输入备注信息！", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    basic_info.put("standard", 0);
                }
            }
            basic_info.put("cCusCode", customer_bean.getCode());
            if (TextUtils.isEmpty(amt.getText().toString())){
                Toast.makeText(this, "请输入总金额", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(carsCount.getText().toString())) {
                Toast.makeText(this, "请输入数量", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(shuibaokong.getText().toString())) {
                Toast.makeText(this, "请输入水包空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(liangbaojiao.getText().toString())) {
                Toast.makeText(this, "请输入量包角", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(penggan.getSelectedItem().toString())) {
                Toast.makeText(this, "请输入蓬杆数量", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(price.getText().toString())) {
                Toast.makeText(this, "请输入单价", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(applyDate.getText().toString())) {
                Toast.makeText(this, "请输入提车日期", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(obtainType.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择提车方式", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(beitaizhijia.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择备胎支架", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pati.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择爬梯数量", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(model.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择型号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(height.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择高度", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(powerType.getSelectedItem().toString())) {
                Toast.makeText(this, "请选择动力类型", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(amt.getText().toString())) {
                Toast.makeText(this, "请输入车款", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(color.getText().toString())) {
                Toast.makeText(this, "请输入颜色", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(deposit.getText().toString())) {
                basic_info.put("deposit", "0");
            } else {
                basic_info.put("deposit", deposit.getText().toString());
            }
            if (TextUtils.isEmpty(orderNum.getText().toString())) {
                Toast.makeText(this, "请输入订单号", Toast.LENGTH_SHORT).show();
                return;
            }

            basic_info.put("beitaizhijia", beitaizhijia.getSelectedItem().toString());
            basic_info.put("penggan", penggan.getSelectedItem().toString());
            basic_info.put("pati", pati.getSelectedItem().toString());
            basic_info.put("carsCount", carsCount.getText().toString());
            basic_info.put("price", price.getText().toString());
            basic_info.put("apply_date", applyDate.getText().toString());
            basic_info.put("obtain_type", obtainType.getSelectedItem().toString());
            basic_info.put("amt", amt.getText().toString());
            basic_info.put("remark", remark.getText().toString());
            basic_info.put("color", color.getText().toString());
            basic_info.put("standardId", standardId);
            basic_info.put("orderNumber", orderNumber);
            basic_info.put("orderNumber", orderNum.getText().toString());//库存车编码
            basic_info.put("orderId", orderId);
            basic_info.put("height", height.getSelectedItem().toString());
            basic_info.put("powerType", powerType.getSelectedItem().toString());
            basic_info.put("model", model.getSelectedItem().toString());
            basic_info.put("shuibaokong", shuibaokong.getText().toString());
            basic_info.put("liangbaojiao", liangbaojiao.getText().toString());
            /**
             * 中间信息为侧栏板，后门，底板
             */
            Inventory inven = DaoBean.getSelectedInventoryLikeCCode("020105");
            if (inven == null) {
                basic_info.put("celanban", "");

            } else {
                basic_info.put("celanban", inven.getCInvName() + inven.getCInvStd());

            }
            inven = DaoBean.getSelectedInventoryLikeCCode("020106");
            if (inven == null) {
                basic_info.put("houmen", "");

            } else {
                basic_info.put("houmen", inven.getCInvName() + inven.getCInvStd());

            }
            inven = DaoBean.getSelectedInventoryLikeCCode("0301");
            if (inven == null) {
                basic_info.put("diban", "");

            } else {
                basic_info.put("diban", inven.getCInvName() + inven.getCInvStd());

            }
            /**
             *
             */


            json.put("basic_info", basic_info);
            for (Inventory inventory : inventories) {
                config_info.put(new JSONObject(gson.toJson(inventory, Inventory.class)));
            }
            json.put("config_info", config_info);
            json.put("username", userInfo.getUsername());
            json.put("password", userInfo.getPassword());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("data", json.toString());
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        Log.i("传入数据", json.toString());

        OkHttpUtils.postString()
                .url(CommonData.CommitContract)
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
            if (response.getCode() == 0) {
                Toast.makeText(ContractInputActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ContractInputActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
