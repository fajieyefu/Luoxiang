package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.Area;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.MySpinnerForFreeInventory;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.NumberToCN;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-09-06.
 */

public class SkeletonContractInput extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.contacts_man)
    TextView contactsMan;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.endCustomerName)
    EditText endCustomerName;
    @BindView(R.id.endCustomerPhone)
    EditText endCustomerPhone;
    @BindView(R.id.area)
    MySpinnerForFree area;
    @BindView(R.id.isNew)
    CheckBox isNew;
    @BindView(R.id.customer_info)
    LinearLayout customerInfo;
    @BindView(R.id.qianyinche)
    EditText qianyinche;
    @BindView(R.id.powerType)
    MySpinnerForFree powerType;
    @BindView(R.id.ql)
    LinearLayout ql;
    @BindView(R.id.caculator)
    Button caculator;
    @BindView(R.id.carStyle)
    MySpinnerForFree carStyle;
    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.axisCount)
    MySpinnerForFree axisCount;
    @BindView(R.id.chexxiang_color)
    EditText chexxiangColor;
    @BindView(R.id.num)
    EditText num;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.up)
    MySpinnerForFreeInventory up;
    @BindView(R.id.down)
    MySpinnerForFreeInventory down;
    @BindView(R.id.mid)
    MySpinnerForFreeInventory mid;
    @BindView(R.id.qianxuan)
    EditText qianxuan;
    @BindView(R.id.qianyinxiao)
    MySpinnerForFreeInventory qianyinxiao;
    @BindView(R.id.lidigao)
    EditText lidigao;
    @BindView(R.id.hzbj)
    EditText hzbj;
    @BindView(R.id.houzhuan_layout)
    LinearLayout houzhuanLayout;
    @BindView(R.id.qianhuizhuan)
    EditText qianhuizhuan;
    @BindView(R.id.banhuang)
    MySpinnerForFreeInventory banhuang;
    @BindView(R.id.fender)
    MySpinnerForFreeInventory fender;
    @BindView(R.id.relayValve)
    MySpinnerForFreeInventory relayValve;

    @BindView(R.id.brakeChamberType)
    MySpinnerForFreeInventory brakeChamberType;
    @BindView(R.id.lockType)
    MySpinnerForFreeInventory lockType;
    @BindView(R.id.lockType_mark)
    EditText lockTypeMark;
    @BindView(R.id.Unloading_platform)
    MySpinnerForFree unloadingPlatform;
    @BindView(R.id.Unloading_platform_mark)
    EditText unloadingPlatformMark;
    @BindView(R.id.suspensionType)
    MySpinnerForFreeInventory suspensionType;
    @BindView(R.id.gjx_left)
    MySpinnerForFreeInventory gjxLeft;
    @BindView(R.id.gjx_right)
    MySpinnerForFreeInventory gjxRight;
    @BindView(R.id.btzj)
    MySpinnerForFree btzj;
    @BindView(R.id.btsjq)
    MySpinnerForFree btsjq;
    @BindView(R.id.legType)
    MySpinnerForFreeInventory legType;
    @BindView(R.id.abs)
    MySpinnerForFreeInventory abs;
    @BindView(R.id.gangquan)
    MySpinnerForFreeInventory gangquan;
    @BindView(R.id.gangquan_num)
    EditText gangquanNum;
    @BindView(R.id.luntai)
    MySpinnerForFreeInventory luntai;
    @BindView(R.id.luntai_num)
    EditText luntaiNum;
    @BindView(R.id.chezhou)
    MySpinnerForFreeInventory chezhou;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.dingjin)
    CheckBox dingjin;
    @BindView(R.id.yunfei)
    CheckBox yunfei;
    @BindView(R.id.deposit)
    EditText deposit;
    @BindView(R.id.amt)
    EditText amt;
    @BindView(R.id.amt_dx)
    EditText amtDx;
    @BindView(R.id.applyDate)
    TextView applyDate;
    @BindView(R.id.obtain_type)
    MySpinnerForFree obtainType;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.orderNumber)
    EditText orderNumberEdit;
    @BindView(R.id.select_info)
    LinearLayout selectInfo;
    @BindView(R.id.parent)
    ScrollView parent;
    @BindView(R.id.productType)
    MySpinnerForFreeInventory productType;
    @BindView(R.id.airCylinderType)
    MySpinnerForFreeInventory airCylinderType;
    private Button back;
    private int mYear;
    private int mMonth;
    private int mDay;
    private ObtainBean customer_bean;
    private ArrayList<Area> areaList = new ArrayList<>();
    private String orderNumber;
    private int orderId;
    private String ordinaryContent;
    private Button more;
    private Dialog dialog;
    private DatePickerDialog datePicker;
    private List<String> list_carStyle = new ArrayList<>();
    private List<String> list_productType = new ArrayList<>();
    private List<String> list_obtainType = new ArrayList<>();
    private List<String> list_axisCounts = new ArrayList<>();
    private List<String> list_up = new ArrayList<>();
    private List<String> list_down = new ArrayList<>();
    private List<String> list_mid = new ArrayList<>();
    private List<String> list_qianyinxiao = new ArrayList<>();
    private List<String> list_banhuang = new ArrayList<>();
    private List<String> list_boxLeft = new ArrayList<>();
    private List<String> list_boxRight = new ArrayList<>();
    private List<String> list_abs = new ArrayList<>();
    private List<String> list_gangquan = new ArrayList<>();
    private List<String> list_luntai = new ArrayList<>();
    private List<String> list_axis = new ArrayList<>();
    private List<String> list_lockType = new ArrayList<>();
    private List<String> list_xiehuo = new ArrayList<>();
    private List<String> list_suspensionType = new ArrayList<>();
    private List<String> list_fendar = new ArrayList<>();
    private List<String> list_airCylinderType = new ArrayList<>();
    private List<String> list_relayValveType= new ArrayList<>();
    private List<String> list_legType = new ArrayList<>();
    private List<String> list_brakeChamberType = new ArrayList<>();
    private List<String> list_powerType = new ArrayList<>();
    private List<String> list_btzj = new ArrayList<>();
    private List<String> list_btsjq = new ArrayList<>();
    private List<String> list_area = new ArrayList<>();
    JSONObject basic_info = new JSONObject();
    JSONArray config_info = new JSONArray();
    private String standardId;
    private UserInfo userInfo;
    private ToolUtil toolUtil;
    private String cDCCode;
    private String zText;
    private String eText;
    private String zlText;
    private String ejText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skeleton_input_layout);
        ButterKnife.bind(this);
        initView();
        addTextChangeListener();
        initSpinner();

    }

    private void initSpinner() {
        list_powerType = Arrays.asList(getResources().getStringArray(R.array.power_type));
        powerType.setData(list_powerType);

        list_productType = Arrays.asList(getResources().getStringArray(R.array.productListArray));
        productType.setData(list_productType);
        list_carStyle = Arrays.asList(getResources().getStringArray(R.array.carStyleArray));
        carStyle.setData(list_carStyle);

        list_obtainType = Arrays.asList(getResources().getStringArray(R.array.obtaintype));
        obtainType.setData(list_obtainType);
        list_axisCounts = Arrays.asList(getResources().getStringArray(R.array.axisCounts));
        axisCount.setData(list_axisCounts);

        //装卸平台
        list_xiehuo = Arrays.asList(getResources().getStringArray(R.array.xiehuo));
        unloadingPlatform.setData(list_xiehuo);
        //上
        list_up = DaoBean.getInventoryNameByCCode("1508");
        up.setData(list_up);
        //下
        list_down = DaoBean.getInventoryNameByCCode("1509");
        down.setData(list_down);
        //立板
        list_mid = DaoBean.getInventoryNameByCCode("1510");
        mid.setData(list_mid);
        //牵引销
        list_qianyinxiao = DaoBean.getInventoryNameByCCode("0410");
        qianyinxiao.setData(list_qianyinxiao);
        //板簧
        list_banhuang = DaoBean.getInventoryNameByCCode("1514");
        banhuang.setData(list_banhuang);
        //工具箱左
        list_boxLeft = DaoBean.getInventoryNameByCCode("1503");
        gjxLeft.setData(list_boxLeft);
        //工具箱右
        list_boxRight = DaoBean.getInventoryNameByCCode("1504");
        gjxRight.setData(list_boxRight);
        //ABS
        list_abs = DaoBean.getInventoryNameByCCode("0411");
        abs.setData(list_abs);
        //钢圈
        list_gangquan = DaoBean.getInventoryNameByCCode("0405");
        gangquan.setData(list_gangquan);
        //轮胎
        list_luntai = DaoBean.getInventoryNameByCCode("0404");
        luntai.setData(list_luntai);
        //车轴
        list_axis = DaoBean.getInventoryNameByCCode("1513");
        chezhou.setData(list_axis);

        //悬挂
        list_suspensionType = DaoBean.getInventoryNameByCCode("1517");
        suspensionType.setData(list_suspensionType);
        //锁具
        list_lockType = DaoBean.getInventoryNameByCCode("1518");
        lockType.setData(list_lockType);
        //挡泥板
        list_fendar = DaoBean.getInventoryNameByCCode("1519");
        fender.setData(list_fendar);

        //储气筒
        list_airCylinderType = DaoBean.getInventoryNameByCCode("1520");
        airCylinderType.setData(list_airCylinderType);

        //制动气室
        list_brakeChamberType= DaoBean.getInventoryNameByCCode("1521");
        brakeChamberType.setData(list_brakeChamberType);
        //继动阀
        list_relayValveType= DaoBean.getInventoryNameByCCode("1522");
        relayValve.setData(list_relayValveType);
        //支腿
        list_legType= DaoBean.getInventoryNameByCCode("1523");
        legType.setData(list_legType);
        //备胎升降器
        list_btsjq = Arrays.asList(getResources().getStringArray(R.array.btsjq));
        btsjq.setData(list_btsjq);
        //备胎支架
        list_btzj = Arrays.asList(getResources().getStringArray(R.array.beitaizhijia));
        btzj.setData(list_btzj);


        list_area = new ArrayList<>();
        for (Area area : areaList) {
            list_area.add(area.getcDCName());
        }
        area.setData(list_area);

        //判断是否为退回修改状态，填补内容
        executeModify();



    }

    private void executeModify() {
        if (orderId != 0) {
            JSONArray ordinaryJson = null;
            JSONObject json = null;
            try {
                ordinaryJson = new JSONArray(ordinaryContent);
                json = (JSONObject) ordinaryJson.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            endCustomerPhone.setText(json.optString("endCustomerPhone").trim());
            endCustomerName.setText(json.optString("endCustomerName").trim());
            area.setText(json.optString("cDCName").trim());
            if (json.optInt("isNew") == 0) {
                isNew.setChecked(false);
            }
            lockType.setText(json.optString("lockType").trim());
            productType.setText(json.optString("skeletonType").trim());
            axisCount.setText(json.optString("axisCount").trim());

            fender.setText(json.optString("fender").trim());
            relayValve.setText(json.optString("relayValveType").trim());
            airCylinderType.setText(json.optString("airCylinderType").trim());
            brakeChamberType.setText(json.optString("brakeChamberType").trim());
            unloadingPlatform.setText(json.optString("unloadingPlatform").trim());

            suspensionType.setText(json.optString("suspensionType").trim());
            legType.setText(json.optString("legType").trim());
            unloadingPlatform.setText(json.optString("unloadingPlatform").trim());

            qianyinche.setText(json.optString("qianyinche").trim());
            powerType.setText(json.optString("powerType").trim());
            carStyle.setText(json.optString("model").trim());
            up.setText(json.optString("up").trim());
            down.setText(json.optString("down").trim());
            mid.setText(json.optString("mid").trim());
            chexxiangColor.setText(json.optString("color").trim());
            num.setText(json.optString("booknum").trim());
            if (!TextUtils.isEmpty(json.optString("singleprice"))) {
                price.setText((int) (Float.parseFloat(json.optString("singleprice".trim()))) + "");
            }
            lidigao.setText(json.optString("lidigao").trim());
            hzbj.setText(json.optString("houxuan").trim());
            qianxuan.setText(json.optString("qianxuan").trim());
            qianyinxiao.setText(json.optString("qianyinxiao").trim());
            banhuang.setText(json.optString("banhuang").trim());
            gjxLeft.setText(json.optString("box_left").trim());
            gjxRight.setText(json.optString("box_right").trim());
            btzj.setText(json.optString("btzj").trim());
            btsjq.setText(json.optString("btsjq").trim());
            abs.setText(json.optString("abs").trim());
            gangquan.setText(json.optString("gangquan").trim());
            gangquanNum.setText(json.optString("gangquan_num").trim());
            luntai.setText(json.optString("luntai").trim());
            luntaiNum.setText(json.optString("luntai_num").trim());
            chezhou.setText(json.optString("chezhou").trim());
            remark.setText(json.optString("marks").trim());
            if (json.optInt("bmustbook") == 0) {
                dingjin.setChecked(false);
            }
            if (json.optInt("cDefine1") == 1) {
                yunfei.setChecked(true);
            }
            if (!TextUtils.isEmpty(json.optString("payedmoney"))) {
                deposit.setText((int) Float.parseFloat(json.optString("payedmoney").trim()) + "");
            }
            if (!TextUtils.isEmpty(json.optString("orderMoney"))) {
                amt.setText((int) Float.parseFloat(json.optString("orderMoney").trim()) + "");
            }

            amtDx.setText(json.optString("ordermoney_dx").trim());
            applyDate.setText(json.optString("leaveTime").trim().split(" ")[0]);
            obtainType.setText(json.optString("carstyle").trim());
            if (json.optInt("urgent_flag") == 1) {
                urgent.setChecked(true);
            }


        }
    }

    @SuppressWarnings("ResourceType")
    private void initView() {
        zText = this.getResources().getString(R.string.z);
        eText = this.getResources().getString(R.string.e);
        zlText = this.getResources().getString(R.string.zl);
        ejText = this.getResources().getString(R.string.ej);
        title.setTitleText("骨架车合同录入");
        standardId = DaoBean.getInventoryClassById(1).getStandardId();
        back = (Button) title.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExit();
            }
        });
        Intent intent = getIntent();
        customer_bean = (ObtainBean) intent.getSerializableExtra("customer");
        areaList = (ArrayList<Area>) intent.getSerializableExtra("area");
        orderNumber = intent.getStringExtra("orderNumber");
        orderId = intent.getIntExtra("orderId", 0);
        ordinaryContent = intent.getStringExtra("ordinaryContent");
        customer.setText(customer_bean.getName());
        mobile.setText(customer_bean.getcCusHand());
        address.setText(customer_bean.getcCusAddress());
        contactsMan.setText(customer_bean.getcCusLPerson());
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(SkeletonContractInput.this);
                View view = LayoutInflater.from(SkeletonContractInput.this).inflate(R.layout.more_layout, null);
                TextView commit = (TextView) view.findViewById(R.id.commit);
                Button preview = (Button) view.findViewById(R.id.preview);
                commit.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                preview.setOnClickListener(SkeletonContractInput.this);
                commit.setOnClickListener(SkeletonContractInput.this);
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

    }

    private void addTextChangeListener() {
        amt.addTextChangedListener(watcher);
    }

    private void isExit() {

        //按键的抬起事件
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(this.getResources().getString(R.string.isExit));
        dialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SkeletonContractInput.this.finish();
            }
        });
        dialog.show();

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

    /**
     * 大小写监听
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String moneyStr = amt.getText().toString();
            if (TextUtils.isEmpty(moneyStr)) {
                moneyStr = "0";
            }
            BigDecimal money = new BigDecimal(moneyStr);
            amtDx.setText(NumberToCN.number2CNMontrayUnit(money));
        }
    };

    @Override
    public void onClick(final View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(this.getResources().getString(R.string.sureOperate));
        dialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (v.getId()) {
                    case R.id.commit:
                        commitData("commit");
                        break;
                    case R.id.preview:
                        Intent intent2 = new Intent(SkeletonContractInput.this, SkeletonPreviewActivity.class);
                        if (!createBaseJsonData()) {
                            return;
                        }
                        String data = basic_info.toString();
                        intent2.putExtra("data", data);
                        startActivity(intent2);
                        break;


                }
            }
        });
        dialog.show();


    }

    /**
     * 提交订单
     * @param msg
     */
    private void commitData(String msg) {

        config_info = new JSONArray();
        if (dialog != null) {
            dialog.dismiss();
        }
        //判断必填项是否填写
        if (!createBaseJsonData()) {
            return;
        }
        userInfo = DaoBean.getUseInfoById(1);

        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();

        JSONObject content = new JSONObject();
        Gson gson = new GsonBuilder().create();
        try {
            for (Inventory inventoryTemp : inventories) {
                if (Integer.parseInt(inventoryTemp.getCounts()) == 0) {
                    continue;
                }
                config_info.put(new JSONObject(gson.toJson(inventoryTemp, Inventory.class)));
                String incstd = inventoryTemp.getCInvStd();
                System.out.println(inventoryTemp.getCInvName()+"规格型号:"+(incstd == null ? "无规格型号" : incstd)+"数量:"+inventoryTemp.getCounts());
            }
            content.put("config_info", config_info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            switch (msg) {
                case "commit":
                    basic_info.put("apply_type", "0");
                    break;
            }
            basic_info.put("cCusCode", customer_bean.getCode());
            basic_info.put("standardId", standardId);
            if (!TextUtils.isEmpty(orderNumber)) {
                basic_info.put("orderNumber", orderNumber);
            }
            if (orderId != 0) {
                basic_info.put("orderId", orderId);
            }
            content.put("basic_info", basic_info);
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("传入数据", content.toString());

        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);


        OkHttpUtils.postString()
                .url(CommonData.CommitContract)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());

    }

    /**
     * 获取所有basic_info
     * @return
     */
    public boolean createBaseJsonData() {
        if (!TextUtils.isEmpty(judgeEdit())) {
            Toast.makeText(this, judgeEdit(), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!dealWithData()){
            return  false;
        }
        monitorEditNum();
        basic_info = new JSONObject();

        try {
            basic_info.put("customer", customer_bean.getName());
            basic_info.put("mobile", customer_bean.getcCusHand());
            basic_info.put("address", customer_bean.getcCusAddress());
            basic_info.put("contractsMan", customer_bean.getcCusPerson());
            basic_info.put("endCustomerName", endCustomerName.getText().toString());
            basic_info.put("endCustomerPhone", endCustomerPhone.getText().toString());
            basic_info.put("qianyinche", qianyinche.getText().toString());
            basic_info.put("powerType", powerType.getText());
            basic_info.put("model", carStyle.getText());
            basic_info.put("skeletonType", productType.getText());
            basic_info.put("axisCount", axisCount.getText());
            basic_info.put("color", chexxiangColor.getText());
            basic_info.put("booknum", num.getText().toString());
            basic_info.put("singleprice", price.getText().toString());
            basic_info.put("up", up.getText());
            basic_info.put("down", down.getText());
            basic_info.put("mid", mid.getText());
            basic_info.put("qianxuan", qianxuan.getText().toString());
            basic_info.put("qianyinxiao", qianyinxiao.getText());
            basic_info.put("lidigao", lidigao.getText().toString());
            basic_info.put("houxuan", hzbj.getText().toString());
            basic_info.put("front_space",qianhuizhuan.getText());
            basic_info.put("banhuang", banhuang.getText());
            basic_info.put("fender",fender.getText());
            basic_info.put("relayValveType", relayValve.getText());
            basic_info.put("airCylinderType", airCylinderType.getText());
            basic_info.put("brakeChamberType", brakeChamberType.getText());
            basic_info.put("lockType", lockType.getText());
            basic_info.put("lock_mark", lockTypeMark.getText());
            basic_info.put("unloadingPlatform", unloadingPlatform.getText());
            basic_info.put("suspensionType", suspensionType.getText());
            basic_info.put("box_left", gjxLeft.getText());
            basic_info.put("box_right", gjxRight.getText());
            basic_info.put("btzj", btzj.getText());
            basic_info.put("btsjq", btsjq.getText());
            basic_info.put("legType", legType.getText());
            basic_info.put("abs", abs.getText());
            basic_info.put("gangquan", gangquan.getText());
            basic_info.put("gangquan_num", gangquanNum.getText());
            basic_info.put("luntai", luntai.getText());
            basic_info.put("luntai_num", luntaiNum.getText());
            basic_info.put("btsjq", btsjq.getText());
            basic_info.put("btsjq", btsjq.getText());
            basic_info.put("chezhou", chezhou.getText());
            basic_info.put("remark", remark.getText().toString());
            basic_info.put("ordermoney", amt.getText().toString());
            basic_info.put("ordermoney_dx", amtDx.getText().toString());
            basic_info.put("leaveTime", applyDate.getText().toString());
            basic_info.put("carstyle", obtainType.getText());
            basic_info.put("pcflag", 0);//排产标志
            basic_info.put("sc_flag", 0);//生产审核标志
            basic_info.put("nq_flag", 0);//内勤审核标志
            basic_info.put("cDCName", area.getText());
            basic_info.put("isSkeleton", 1);
            basic_info.put("car_height", "骨架车");

            basic_info.put("orderNumber", orderNumberEdit.getText());
            basic_info.put("classCode", "0106");

            if (!TextUtils.isEmpty(orderNumberEdit.getText())) {
                basic_info.put("order_type", 1);
            } else {
                basic_info.put("order_type", 0);
            }
            for (Area ar : areaList) {
                if (ar.getcDCName().equals(area.getText())) {
                    cDCCode = ar.getcDCCode();
                    break;
                }
            }
            basic_info.put("cDCCode", cDCCode);
            if (dingjin.isChecked()) {
                basic_info.put("bmustbook", 1);
                basic_info.put("payedmoney", deposit.getText().toString());
            } else {
                basic_info.put("bmustbook", 0);

            }
            if (urgent.isChecked()) {
                basic_info.put("urgent_flag", 1);
            } else {
                basic_info.put("urgent_flag", 0);

            }
            if (yunfei.isChecked()) {
                basic_info.put("cDefine1", 1);
            } else {
                basic_info.put("cDefine1", 0);
            }
            if (isNew.isChecked()) {
                basic_info.put("isNew", 1);
            } else {
                basic_info.put("isNew", 0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
    private String judgeEdit() {
        String toastInfo = "";
        if (TextUtils.isEmpty(qianyinche.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.qinyinche);
            return toastInfo;
        }
        if (TextUtils.isEmpty(endCustomerName.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.endCustomerName);
            return toastInfo;
        }
        if (TextUtils.isEmpty(endCustomerPhone.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.endCustomerPhone);
            return toastInfo;
        }
        if (TextUtils.isEmpty(chexxiangColor.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditcolor);
            return toastInfo;
        }
        if (TextUtils.isEmpty(num.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditOrderCount);
            return toastInfo;
        }
        if (TextUtils.isEmpty(price.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdlprice);
            return toastInfo;
        }
        if (TextUtils.isEmpty(lidigao.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdlidigao);
            return toastInfo;
        }
        if (TextUtils.isEmpty(amtDx.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdlamtDx);
            return toastInfo;
        }
        if (dingjin.isChecked()) {
            if (TextUtils.isEmpty(deposit.getText().toString())) {
                toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdldingjin);
                return toastInfo;
            }
        }
        if (TextUtils.isEmpty(deposit.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdldingjin);
            return toastInfo;
        }

        if (TextUtils.isEmpty(amt.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdlamt);
            return toastInfo;
        }
        if (TextUtils.isEmpty(applyDate.getText().toString())) {
            toastInfo = SkeletonContractInput.this.getResources().getString(R.string.auditdleaveTime);
            return toastInfo;
        }
        if (productType.getText().equals("不选")){
            toastInfo = "产品型号不可以不选";
            return toastInfo;
        }


        return toastInfo;
    }
    public int getEditNum(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            return 0;
        } else {
            return Integer.parseInt(editText.getText().toString());
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {
                isExit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
    private boolean dealWithData() {
        String ze;
        String ze_text;
        if (carStyle.getText().contains(zText)) {
            ze = "Z";
            ze_text = zlText;
        } else {
            ze = "E";
            ze_text = ejText;
        }


        //确定车架上
        if (DaoBean.getInventoryLikeCCodeSkeleton("1508",up.getText(),ze_text,productType.getText(),null).size()!=1&&!up.getText().equals("不选")){
            Toast.makeText(this, "车架（上）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定车架下
        if (DaoBean.getInventoryLikeCCodeSkeleton("1509",down.getText(),ze_text,productType.getText(),null).size()!=1&&!down.getText().equals("不选")){
            Toast.makeText(this, "车架（下）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;

        }
        //确定车架立
        if (DaoBean.getInventoryLikeCCodeSkeleton("1510",mid.getText(),ze_text,productType.getText(),null).size()!=1&&!mid.getText().equals("不选")){
            Toast.makeText(this, "车架（立）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }

        //确定牵引销
        if(DaoBean.getInventoryLikeCCodeSkeleton("0410",qianyinxiao.getText(),ze,null,null).size()!=1&&!qianyinxiao.getText().equals("不选")){
            Toast.makeText(this, "牵引销有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定板簧
        if (DaoBean.getInventoryLikeCCodeSkeleton("1514",banhuang.getText(),ze,productType.getText(),axisCount.getText()).size()!=1&&!banhuang.getText().equals("不选")){
            Toast.makeText(this, "板簧有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }

        //确定左工具箱
        if (DaoBean.getInventoryLikeCCodeSkeleton("1503",gjxLeft.getText(),ze,null,null).size()!=1&&!gjxLeft.getText().equals("不选")){
            Toast.makeText(this, "左工具箱数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定右工具箱
        if (DaoBean.getInventoryLikeCCodeSkeleton("1504",gjxRight.getText(),ze,null,null).size()!=1&&!gjxRight.getText().equals("不选")){
            Toast.makeText(this, "右工具箱数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //ABS
        if (DaoBean.getInventoryLikeCCodeSkeleton("0411",abs.getText(),ze,null,null).size()!=1&&!abs.getText().equals("不选")){
            Toast.makeText(this, "ABS数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定钢圈
        if (DaoBean.getInventoryLikeCCodeSkeleton("0405",gangquan.getText(),ze,null,null).size()!=1&&!gangquan.getText().equals("不选")){
            Toast.makeText(this, "钢圈数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定轮胎
        if (DaoBean.getInventoryLikeCCodeSkeleton("0404",luntai.getText(),ze,null,null).size()!=1&&!luntai.getText().equals("不选")){
            Toast.makeText(this, "轮胎数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定车轴
        if (DaoBean.getInventoryLikeCCodeSkeleton("1513",chezhou.getText(),ze,null,null).size()!=1&&!chezhou.getText().equals("不选")){
            Toast.makeText(this, "车轴数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定装卸平台
        if (DaoBean.getInventoryLikeCCodeSkeleton("1516",unloadingPlatform.getText(),ze,null,null).size()!=1){
            Toast.makeText(this, "装卸平台数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定悬挂
        if (DaoBean.getInventoryLikeCCodeSkeleton("1517",suspensionType.getText(),ze_text,productType.getText(),null).size()!=1&&!suspensionType.getText().equals("不选")){
            Toast.makeText(this, "悬挂数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定锁具
        if (DaoBean.getInventoryLikeCCodeSkeleton("1518",lockType.getText(),ze,null,null).size()!=1&&!lockType.getText().equals("不选")){
            Toast.makeText(this, "锁具数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定挡泥板
        if (DaoBean.getInventoryLikeCCodeSkeleton("1519",fender.getText(),ze,null,null).size()!=1&&!fender.getText().equals("不选")){
            Toast.makeText(this, "挡泥板数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定储气筒
        if (DaoBean.getInventoryLikeCCodeSkeleton("1520",airCylinderType.getText(),ze,null,null).size()!=1&&!airCylinderType.getText().equals("不选")){
            Toast.makeText(this, "储气筒数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定制动气室
        if (DaoBean.getInventoryLikeCCodeSkeleton("1521",brakeChamberType.getText(),ze,null,null).size()!=1&&!brakeChamberType.getText().equals("不选")){
            Toast.makeText(this, "制动气室数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定继动阀
        if (DaoBean.getInventoryLikeCCodeSkeleton("1522",relayValve.getText(),ze,null,null).size()!=1&&!relayValve.getText().equals("不选")){
            Toast.makeText(this, "继动阀数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定支腿
        if (DaoBean.getInventoryLikeCCodeSkeleton("1523",legType.getText(),ze,null,null).size()!=1&&!legType.getText().equals("不选")){
            Toast.makeText(this, "支腿数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }




        //确定底盘
         String tempString="20";
        if (productType.getText().contains("20")){
            tempString="20";
        }else if (productType.getText().contains("40")){
            tempString="40";
        }else if (productType.getText().contains("45")){
            tempString="45";
        }else if (productType.getText().contains("48")){
            tempString="48";
        }
        if (DaoBean.getInventoryLikeCCodeSkeleton("1511",tempString,ze_text,null,null).size()!=1&&!productType.getText().equals("不选")){
            Toast.makeText(this, "底盘数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定行走机构
        if (DaoBean.getInventoryLikeCCodeSkeleton("1506",tempString,ze_text,axisCount.getText()+"轴",null).size()!=1&&!productType.getText().equals("不选")){
            Toast.makeText(this, "行走机构数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }




        return true;

    }
    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(SkeletonContractInput.this, SkeletonContractInput.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                Toast.makeText(SkeletonContractInput.this, SkeletonContractInput.this.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SkeletonContractInput.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 监听数量变化
     */
    public void monitorEditNum() {
        //int wchengCounts = getEditNum(wchengNum);
        int gqCounts = getEditNum(gangquanNum);
        int ltCounts = getEditNum(luntaiNum);
        DaoBean.updateCounts("0405", gqCounts);
        DaoBean.updateCounts("0404", ltCounts);
        DaoBean.updateCounts("0402", Integer.parseInt(btsjq.getText().equals("") ? "0" : btsjq.getText()));
        DaoBean.updateCounts("1513", Integer.parseInt(axisCount.getText().equals("") ? "0" : axisCount.getText()));
    }

    @OnClick(R.id.caculator)
    public void onViewClicked() {
        if (!dealWithData()){
            return;
        }
        monitorEditNum();
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
            //计算重量
            if (inventory.getWeight() != null) {
                Log.i(inventory.getCInvName(), inventory.getWeight() + "");
                actualWeight = actualWeight + inventory.getWeight() * (Integer.parseInt(inventory.getCounts()));
            }
        }
        //计算价格
        inventories = DaoBean.loadInventoryByCurrentAndShiJia();
        for (Inventory inventory : inventories) {
            actualPrice = actualPrice + Integer.parseInt(inventory.getRealMoney()) * Integer.parseInt(inventory.getCounts());
        }
        weight.setText((int) actualWeight + "");
        price.setText(actualPrice + "");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();

    }
}
