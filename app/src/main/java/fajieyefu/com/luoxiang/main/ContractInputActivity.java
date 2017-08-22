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
import fajieyefu.com.luoxiang.adapter.InventoryClassAdapter;
import fajieyefu.com.luoxiang.bean.Area;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.MySpinnerForInventry;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.NumberToCN;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-05-06.
 */

public class ContractInputActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.contacts_man)
    TextView contactsMan;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.customer_info)
    LinearLayout customerInfo;
    @BindView(R.id.qianyinche)
    MySpinnerForInventry qianyinche;
    @BindView(R.id.powerType)
    MySpinnerForFree powerType;
    @BindView(R.id.ql)
    LinearLayout ql;
    @BindView(R.id.carStyle)
    MySpinnerForFree carStyle;
    @BindView(R.id.len)
    MySpinnerForFree len;
    @BindView(R.id.wid)
    MySpinnerForFree wid;
    @BindView(R.id.hit)
    MySpinnerForFree hit;
    @BindView(R.id.chexxiang_color)
    EditText chexxiangColor;
    @BindView(R.id.daliang_color)
    EditText daliangColor;
    @BindView(R.id.num)
    EditText num;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.up)
    MySpinnerForInventry up;
    @BindView(R.id.down)
    MySpinnerForInventry down;
    @BindView(R.id.mid)
    MySpinnerForInventry mid;
    @BindView(R.id.lidigao)
    EditText lidigao;
    @BindView(R.id.hzbj)
    EditText hzbj;
    @BindView(R.id.qianxuan)
    EditText qianxuan;
    @BindView(R.id.zhongxinju)
    EditText zhongxinju;
    @BindView(R.id.qianyinxiao)
    MySpinnerForInventry qianyinxiao;
    @BindView(R.id.qianyinzuo)
    MySpinnerForFree qianyinzuo;
    @BindView(R.id.banhuang)
    MySpinnerForInventry banhuang;
    @BindView(R.id.bianliang)
    MySpinnerForInventry bianliang;
    @BindView(R.id.wcheng1)
    MySpinnerForInventry wcheng1;
    @BindView(R.id.wcheng2)
    EditText wcheng2;
    @BindView(R.id.fangshuicao)
    MySpinnerForFree fangshuicao;
    @BindView(R.id.diban)
    MySpinnerForInventry diban;
    @BindView(R.id.liangbaojiao)
    EditText liangbaojiao;

    @BindView(R.id.celanban)
    MySpinnerForInventry celanban;
    @BindView(R.id.celanban_mark)
    EditText celanbanMark;
    @BindView(R.id.houmen)
    MySpinnerForInventry houmen;
    @BindView(R.id.houmen_mark)
    EditText houmenMark;
    @BindView(R.id.penggan)
    MySpinnerForInventry penggan;
    @BindView(R.id.pati)
    MySpinnerForFree pati;
    @BindView(R.id.xiaokuang)
    MySpinnerForInventry xiaokuang;
    @BindView(R.id.jinshengqi_num)
    EditText jinshengqiNum;
    @BindView(R.id.jinshenqi_mark)
    EditText jinshenqiMark;
    @BindView(R.id.gjx_left)
    MySpinnerForInventry gjxLeft;
    @BindView(R.id.gjx_right)
    MySpinnerForInventry gjxRight;
    @BindView(R.id.btzj)
    MySpinnerForFree btzj;
    @BindView(R.id.btsjq)
    MySpinnerForFree btsjq;
    @BindView(R.id.abs)
    MySpinnerForInventry abs;
    @BindView(R.id.shuibaokong)
    EditText shuibaokong;
    @BindView(R.id.gangquan)
    MySpinnerForInventry gangquan;
    @BindView(R.id.gangquan_num)
    EditText gangquanNum;
    @BindView(R.id.luntai)
    MySpinnerForInventry luntai;
    @BindView(R.id.luntai_num)
    EditText luntaiNum;
    @BindView(R.id.chezhou)
    MySpinnerForInventry chezhou;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.deposit)
    EditText deposit;
    @BindView(R.id.amt)
    EditText amt;
    @BindView(R.id.applyDate)
    TextView applyDate;
    @BindView(R.id.obtain_type)
    MySpinnerForFree obtainType;
    @BindView(R.id.select_info)
    LinearLayout selectInfo;
    @BindView(R.id.caculator)
    Button caculator;
    @BindView(R.id.parent)
    ScrollView parent;
    @BindView(R.id.wcheng_num)
    EditText wchengNum;
    @BindView(R.id.amt_dx)
    EditText amtDx;
    @BindView(R.id.dingjin)
    CheckBox dingjin;
    @BindView(R.id.yunfei)
    CheckBox yunfei;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.area)
    MySpinnerForFree area;

    @BindView(R.id.isNew)
    CheckBox isNew;
    @BindView(R.id.fangguan)
    EditText fangguan;
    private InventoryClassAdapter inventoryClassAdapter;
    private List<InventoryClass> inventorys = new ArrayList<>();
    private Button more;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePickerDialog datePicker;
    private List<String> list_obtaintype;
    private List<String> list_powerType;
    private List<String> list_qyz;
    private List<String> list_len;
    private List<String> list_wid;
    private List<String> list_hgt;
    private List<String> list_fsc;
    private List<String> list_model;
    private List<String> list_pati;
    private List<String> list_beitaizhijia;
    private List<String> list_btsjq;
    private List<String> list_area;
    private ObtainBean customer_bean;
    private ToolUtil toolUtil;
    private UserInfo userInfo;
    private String standardId;
    private String orderNumber;
    private Dialog dialog;
    private int orderId;
    private int commitType;
    private Button preview;
    TextView celanban_text;
    TextView bianliang_text;
    TextView qyc_text;
    TextView len_text;
    TextView hgt_text;
    TextView wid_text;
    TextView car_style_text;
    TextView luntaiSpinnerText;
    TextView gqSpinnerText;
    TextView btsjqSpinnerText;
    TextView wchengSpinnerText;
    JSONObject basic_info = new JSONObject();
    JSONArray config_info = new JSONArray();
    private Button back;
    private ArrayList<Area> areaList = new ArrayList<>();
    private String cDCCode;
    private String zText;
    private String eText;
    private String zlText;
    private String ejText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_details);
        ButterKnife.bind(this);
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        initView();
        addTextChangeListener();
        initSpinner();
        initFreeSpinner();
        toolUtil.dismissProgressDialog();

//  inventorys = DaoBean.loadAllInventoryClass();
//  inventoryClassAdapter = new InventoryClassAdapter(this, inventorys);
//  inventoryLv.setAdapter(inventoryClassAdapter);
        standardId = DaoBean.getInventoryClassById(1).getStandardId();


    }


    private void addTextChangeListener() {
        /**
         * 牵引车，高度，宽度，挂车样式，边梁，
         */
        qyc_text = (TextView) qianyinche.findViewById(R.id.spinner_text);
        qyc_text.addTextChangedListener(watcher_qianyinche);
        hgt_text = (TextView) hit.findViewById(R.id.spinner_text);
        hgt_text.addTextChangedListener(watcher_hgt);
        wid_text = (TextView) wid.findViewById(R.id.spinner_text);
        wid_text.addTextChangedListener(watcher_width);
        car_style_text = (TextView) carStyle.findViewById(R.id.spinner_text);
        car_style_text.addTextChangedListener(watcher2);
//        bianliang_text = (TextView) bianliang.findViewById(R.id.spinner_text);
//        bianliang_text.addTextChangedListener(watcher_bianliang);

        //大小写转换
        amt.addTextChangedListener(watcher);
//        /**
//         * 数量发生改变监听事件
//         */
//        wchengNum.addTextChangedListener(watcher);
//        jinshengqiNum.addTextChangedListener(watcher);
//        gangquanNum.addTextChangedListener(watcher);
//        luntaiNum.addTextChangedListener(watcher);

//          轮胎、钢圈、w称,紧绳器,备胎升降器数量发生改变


//        luntaiSpinnerText = (TextView) luntai.findViewById(R.id.spinner_text);
//        luntaiSpinnerText.addTextChangedListener(watcher);
//        gqSpinnerText = (TextView) gangquan.findViewById(R.id.spinner_text);
//        gqSpinnerText.addTextChangedListener(watcher);
//        wchengSpinnerText = (TextView) wcheng1.findViewById(R.id.spinner_text);
//        wchengSpinnerText.addTextChangedListener(watcher);
//        jinshengqiNum.addTextChangedListener(watcher);
//        btsjqSpinnerText = (TextView) btsjq.findViewById(R.id.spinner_text);
//        btsjqSpinnerText.addTextChangedListener(watcher);
    }

    @SuppressWarnings("ResourceType")
    private void initView() {
        zText= this.getResources().getString(R.string.z);
        eText= this.getResources().getString(R.string.e);
        zlText= this.getResources().getString(R.string.zl);
        ejText= this.getResources().getString(R.string.ej);
        
        userInfo = DaoBean.getUseInfoById(1);
        title.setTitleText(this.getResources().getString(R.string.orderInput));
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
        commitType = intent.getIntExtra("commitType", 0);
        orderNumber = intent.getStringExtra("orderNumber");
        orderId = intent.getIntExtra("orderId", 0);
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
                Button preview = (Button) view.findViewById(R.id.preview);
                commit.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                preview.setOnClickListener(ContractInputActivity.this);
                commit.setOnClickListener(ContractInputActivity.this);
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

    /**
     * 加载下拉的从数据库读取的选项
     */
    private void initSpinner() {
        //上
        List<Inventory> upInventorys = DaoBean.getInventoryLikeCCode("1508");
        if (upInventorys != null && upInventorys.size() != 0) {
            up.setData(upInventorys);
        }
        //下
        List<Inventory> downInventorys = DaoBean.getInventoryLikeCCode("1509");
        if (downInventorys != null && downInventorys.size() != 0) {
            down.setData(downInventorys);
        }
        //立板
        List<Inventory> midInventorys = DaoBean.getInventoryLikeCCode("1510");
        if (midInventorys != null && midInventorys.size() != 0) {
            mid.setData(midInventorys);
        }


        //牵引销
        List<Inventory> qianyinzuoInventorys = DaoBean.getInventoryLikeCCode("0410");
        if (qianyinzuoInventorys != null && qianyinzuoInventorys.size() != 0) {
            qianyinxiao.setData(qianyinzuoInventorys);
        }
        //板簧
        List<Inventory> banhuangInventorys = DaoBean.getInventoryLikeCCode("1514");
        if (banhuangInventorys != null && banhuangInventorys.size() != 0) {
            banhuang.setData(banhuangInventorys);
        }
        //边梁
        List<Inventory> bianliangInventorys = DaoBean.getInventoryLikeCCode("1512");
        if (bianliangInventorys != null && bianliangInventorys.size() != 0) {
            bianliang.setData(bianliangInventorys);
        }
        //底板
        List<Inventory> dibanInventorys = DaoBean.getInventoryLikeCCode("1505");
        if (dibanInventorys != null && dibanInventorys.size() != 0) {
            diban.setData(dibanInventorys);
        }
        //侧挡板
        List<Inventory> cdbInventorys = DaoBean.getInventoryLikeCCode("020105");
        if (cdbInventorys != null && cdbInventorys.size() != 0) {
            celanban.setData(cdbInventorys);
        }
        //后门
        List<Inventory> houmenInventorys = DaoBean.getInventoryLikeCCode("020106");
        if (houmenInventorys != null && houmenInventorys.size() != 0) {
            houmen.setData(houmenInventorys);
        }
        //工具箱左
        List<Inventory> gjxzInventorys = DaoBean.getInventoryLikeCCode("1503");
        if (gjxzInventorys != null && gjxzInventorys.size() != 0) {
            gjxLeft.setData(gjxzInventorys);
        }
        //工具箱右
        List<Inventory> gjxyInventorys = DaoBean.getInventoryLikeCCode("1504");
        if (gjxyInventorys != null && gjxyInventorys.size() != 0) {
            gjxRight.setData(gjxyInventorys);
        }
        //ABS
        List<Inventory> absInventorys = DaoBean.getInventoryLikeCCode("0411");
        if (absInventorys != null && absInventorys.size() != 0) {
            abs.setData(absInventorys);
        }
        //钢圈
        List<Inventory> gangquanInventorys = DaoBean.getInventoryLikeCCode("0405");
        if (gangquanInventorys != null && gangquanInventorys.size() != 0) {
            gangquan.setData(gangquanInventorys);
        }
        //轮胎
        List<Inventory> luntaiInventorys = DaoBean.getInventoryLikeCCode("0404");
        if (luntaiInventorys != null && luntaiInventorys.size() != 0) {
            luntai.setData(luntaiInventorys);
        }
        //车轴
        List<Inventory> axisInventorys = DaoBean.getInventoryLikeCCode("1513");
        if (axisInventorys != null && axisInventorys.size() != 0) {
            chezhou.setData(axisInventorys);
        }
        //蓬杆
        List<Inventory> pengganInventorys = DaoBean.getInventoryLikeCCode("1502");
        if (pengganInventorys != null && pengganInventorys.size() != 0) {
            penggan.setData(pengganInventorys);
        }
        //篷布框
        List<Inventory> pbkInventorys = DaoBean.getInventoryLikeCCode("1507");
        if (pbkInventorys != null && pbkInventorys.size() != 0) {
            xiaokuang.setData(pbkInventorys);
        }
        //W称
        List<Inventory> WCInventorys = DaoBean.getInventoryLikeCCode("0308");
        if (WCInventorys != null && WCInventorys.size() != 0) {
            wcheng1.setData(WCInventorys);
        }
        //牵引车
        List<Inventory> qianyincheInventorys = DaoBean.getInventoryLikeCCode("14");
        if (qianyincheInventorys != null && qianyincheInventorys.size() != 0) {
            qianyinche.setData(qianyincheInventorys);
        }

    }

    /**
     * 加载下拉选项框的固定的选项
     */
    private void initFreeSpinner() {
        /**
         * 获取下拉选项框固定值
         */
        list_powerType = Arrays.asList(getResources().getStringArray(R.array.power_type));
        list_len = Arrays.asList(getResources().getStringArray(R.array.len));
        list_wid = Arrays.asList(getResources().getStringArray(R.array.wid));
        list_hgt = Arrays.asList(getResources().getStringArray(R.array.hgt));
        list_qyz = Arrays.asList(getResources().getStringArray(R.array.qyz));
        list_obtaintype = Arrays.asList(getResources().getStringArray(R.array.obtaintype));
        list_fsc = Arrays.asList(getResources().getStringArray(R.array.fangshuicao));
        list_pati = Arrays.asList(getResources().getStringArray(R.array.pati));
        list_beitaizhijia = Arrays.asList(getResources().getStringArray(R.array.beitaizhijia));
        list_model = Arrays.asList(getResources().getStringArray(R.array.model));
        list_btsjq = Arrays.asList(getResources().getStringArray(R.array.btsjq));
        list_area = new ArrayList<>();
        for (Area area : areaList) {
            list_area.add(area.getcDCName());
        }
        /**
         * spinner设置参数
         */
        powerType.setData(list_powerType);
        len.setData(list_len);
        wid.setData(list_wid);
        hit.setData(list_hgt);
        qianyinzuo.setData(list_qyz);
        obtainType.setData(list_obtaintype);
        fangshuicao.setData(list_fsc);
        pati.setData(list_pati);
        btzj.setData(list_beitaizhijia);
        carStyle.setData(list_model);
        btsjq.setData(list_btsjq);
        area.setData(list_area);

    }

    @OnClick(R.id.caculator)
    public void onViewClicked() {
        isNewCar();
        dealWithData();
        monitorEditNum();
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.price_weight_layout, null);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView weight = (TextView) view.findViewById(R.id.weight);
        int standarMoney = Integer.parseInt(DaoBean.getInventoryClassById(1).getStandardMoney());
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
        int actualPrice = standarMoney;
        double actualWeight = 0;
        //计算重量
        for (Inventory inventory : inventories) {
            actualPrice = actualPrice + Integer.parseInt(inventory.getDeMoney());
            if (inventory.getWeight() != null) {
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
                        Intent intent2 = new Intent(ContractInputActivity.this, ContractPreviewActivity.class);
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
     * 提交数据
     *
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
                config_info.put(new JSONObject(gson.toJson(inventoryTemp, Inventory.class)));
                String incstd = inventoryTemp.getCInvStd();
                Log.i(inventoryTemp.getCInvName(), incstd == null ? "nn" : incstd);
                Log.i("數量", inventoryTemp.getCounts());
                Log.i("重量", inventoryTemp.getWeight() + "");
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
            basic_info.put("orderNumber", orderNumber);
            if (orderId != 0) {
                basic_info.put("orderId", orderId);
            }
            content.put("basic_info", basic_info);
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        Log.i("传入数据", content.toString());


        OkHttpUtils.postString()
                .url(CommonData.CommitContract)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());

    }

    private void dealWithData() {
        String ze;
        String ze_text;
        if (carStyle.getText().contains(zText)) {
            ze = "Z";
        } else {
            ze = "E";
        }
        if (ze.equals("Z")) {
            ze_text = zlText;
        } else {
            ze_text = ejText;
        }
        List<Inventory> lmjInventorys2 = DaoBean.getInventoryLikeCCode("020104", hit.getText(), ze);
        if (lmjInventorys2 != null && lmjInventorys2.size() != 0) {
        }

        List<Inventory> zzInventorys2 = DaoBean.getInventoryLikeCCode("020107", hit.getText(), ze);
        Inventory inventoryZhanzhu = getZhanZhuInventory(zzInventorys2);
        if (inventoryZhanzhu != null) {
            DaoBean.upNoSelected(inventoryZhanzhu.getCInvCCode(), inventoryZhanzhu.getCInvCode());
        }
        List<Inventory> diPanInventorys2 = DaoBean.getInventoryLikeCCode("1511", celanban.getText(), ze_text);
        if (diPanInventorys2 != null && diPanInventorys2.size() != 0) {
        }

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
            Toast.makeText(ContractInputActivity.this, ContractInputActivity.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                Toast.makeText(ContractInputActivity.this, ContractInputActivity.this.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ContractInputActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //监听牵引车
    private TextWatcher watcher_qianyinche = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Inventory qianyinche_cur = DaoBean.getSelectedInventoryLikeCCode("14");
            if (qianyinche_cur != null) {
                lidigao.setText(qianyinche_cur.getCInvStd());
            }

        }
    };
    //监听高度变化
    private TextWatcher watcher_hgt = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            //判断挂车样式为直或者鹅
            String ze;
            if (carStyle.getText().contains(zText)) {
                ze = "Z";
            } else {
                ze = "E";
            }
            //根据高度、直梁（鹅颈）取出侧栏板样式
            List<Inventory> cedangbanInventorys2 = DaoBean.getInventoryLikeCCode("020105", hit.getText(), ze);
            celanban.setData(cedangbanInventorys2);
            //根据高度、直梁（鹅颈）取出后门样式
            List<Inventory> houmenInventorys2 = DaoBean.getInventoryLikeCCode("020106", hit.getText(), ze);
            houmen.setData(houmenInventorys2);

        }
    };

    private TextWatcher watcher_width = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            //判断挂车样式为直或者鹅
            String ze;
            if (carStyle.getText().contains(zText)) {
                ze = "Z";
            } else {
                ze = "E";
            }
            liangbaojiao.setText(wid.getText());
            //根据宽度，直梁（鹅颈）取出W称样式
            List<Inventory> wchengInventorys2 = DaoBean.getInventoryLikeCCode("0308", Integer.parseInt(wid.getText()) - 20 + "", ze);
            wcheng1.setData(wchengInventorys2);
            //根据宽度，直梁（鹅颈）取出底板样式
            List<Inventory> dibanInventorys2 = DaoBean.getInventoryLikeCCode("1505", Integer.parseInt(wid.getText()) + "", ze);
            diban.setData(dibanInventorys2);

        }
    };
//    //监听边梁
//    private TextWatcher watcher_bianliang = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            //边梁
//            Inventory hgt_cur = DaoBean.getSelectedInventoryLikeCCode("1512");
//            if (hgt_cur!=null){
//                fangguan.setText(hgt_cur.getCInvStd());
//            }
//
//        }
//    };

    private TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

          /*  List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
            if (inventories != null) {
                for (Inventory inentory : inventories) {
                    if (inentory.getCInvCCode() != null && inentory.getCInvCCode().equals("14")) {
                        lidigao.setText(inentory.getCInvStd());
                        break;
                    }
                }
                for (Inventory inentory : inventories) {
                    if (inentory.getCInvCCode() != null && inentory.getCInvCCode().equals("1512")) {
                        fangguan.setText(inentory.getCInvStd());
                        break;
                    }

                }
            }*/


            //判断挂车样式为直或者鹅
            String ze;
            if (carStyle.getText().contains(zText)) {
                ze = "Z";
            } else {
                ze = "E";
            }
            liangbaojiao.setText(wid.getText());
            //根据高度、直梁（鹅颈）取出侧栏板样式
            List<Inventory> cedangbanInventorys2 = DaoBean.getInventoryLikeCCode("020105", hit.getText(), ze);
            celanban.setData(cedangbanInventorys2);

            /*//根据高度、直梁（鹅颈）取出后门样式
            List<Inventory> houmenInventorys2 = DaoBean.getInventoryLikeCCode("020106", hit.getText(), ze);
            houmen.setData(houmenInventorys2);*/

           /* //根据宽度，直梁（鹅颈）取出W称样式
            List<Inventory> wchengInventorys2 = DaoBean.getInventoryLikeCCode("0308", Integer.parseInt(wid.getText()) - 20 + "", ze);
            wcheng1.setData(wchengInventorys2);
            //根据宽度，直梁（鹅颈）取出底板样式
            List<Inventory> dibanInventorys2 = DaoBean.getInventoryLikeCCode("1505", Integer.parseInt(wid.getText()) + "", ze);
            diban.setData(dibanInventorys2);*/
            //根据高度、直梁（鹅颈）取出龙门架样式

//            List<Inventory> lmjInventorys2 = DaoBean.getInventoryLikeCCode("020104", hit.getText(), ze);

//            List<Inventory> zzInventorys2 = DaoBean.getInventoryLikeCCode("020107", hit.getText(), ze);
//            Inventory inventoryZhanzhu =getZhanZhuInventory(zzInventorys2);
//            if (inventoryZhanzhu!=null){
//                DaoBean.upNoSelected(inventoryZhwhcenganzhu.getCInvCCode(), inventoryZhanzhu.getCInvCode());
//                Log.i("匹配站住",inventoryZhanzhu.getCInvName());
//            }
//            List<Inventory> diPanInventorys2 = DaoBean.getInventoryLikeCCode("1511", celanban.getText(), ze);

        }
    };
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

    /**
     * 根据侧栏板样式确定站柱
     *
     * @param inventoryList
     * @return
     */
    private Inventory getZhanZhuInventory(List<Inventory> inventoryList) {
        String celanbanstyle = celanban.getText();
        Inventory lmjInventoryResult = null;
        if (inventoryList != null) {
            for (Inventory in2 : inventoryList) {
//                Log.i("龙门架", in2.getCInvName() + in2.getCInvStd());
                if (celanbanstyle.contains(ContractInputActivity.this.getResources().getString(R.string.tianzhige)) && in2.getCInvName().contains(ContractInputActivity.this.getResources().getString(R.string.tianzhige))) {
                    lmjInventoryResult = in2;
                    break;
                }
                if (celanbanstyle.contains(ContractInputActivity.this.getResources().getString(R.string.fentiduikai)) && in2.getCInvName().contains(ContractInputActivity.this.getResources().getString(R.string.fentiduikai))) {
                    lmjInventoryResult = in2;
                    break;
                }
                if (celanbanstyle.contains(ContractInputActivity.this.getResources().getString(R.string.liantiduikai)) && in2.getCInvName().contains(ContractInputActivity.this.getResources().getString(R.string.liantiduikai))) {
                    lmjInventoryResult = in2;
                    break;
                }
                if (celanbanstyle.contains(ContractInputActivity.this.getResources().getString(R.string.neizhi)) && in2.getCInvName().contains(ContractInputActivity.this.getResources().getString(R.string.neizhi))) {
                    lmjInventoryResult = in2;
                    break;
                }
                if (celanbanstyle.contains(ContractInputActivity.this.getResources().getString(R.string.waizhi)) && in2.getCInvName().contains(ContractInputActivity.this.getResources().getString(R.string.waizhi))) {
                    lmjInventoryResult = in2;
                    break;
                }

            }

        }
        return lmjInventoryResult;
    }

//    private TextWatcher watcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    /**
     * 监听数量变化
     */
    public void monitorEditNum() {
        int wchengCounts = getEditNum(wchengNum);
        int jsqCounts = getEditNum(jinshengqiNum);
        int gqCounts = getEditNum(gangquanNum);
        int ltCounts = getEditNum(luntaiNum);
        String celanbanString = celanban.getText();

        if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
            DaoBean.updateCounts("0308", wchengCounts - 5);
        } else if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
            DaoBean.updateCounts("0308", wchengCounts - 10);
        } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
            DaoBean.updateCounts("0308", wchengCounts - 9);
        } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
            DaoBean.updateCounts("0308", wchengCounts - 11);
        }else{
            DaoBean.updateCounts("0308", wchengCounts-10);
        }


        DaoBean.updateCounts("0413", jsqCounts);
        DaoBean.updateCounts("0405", gqCounts);
        DaoBean.updateCounts("0404", ltCounts);
        DaoBean.updateCounts("0402", Integer.parseInt(btsjq.getText().equals("") ? "0" : btsjq.getText()));

    }


    /**
     * 获取所有输入项的值
     *
     * @return
     */
    public boolean createBaseJsonData() {
        dealWithData();
        monitorEditNum();
        isNewCar();
        basic_info = new JSONObject();
        if (!TextUtils.isEmpty(judgeEdit())) {
            Toast.makeText(this, judgeEdit(), Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            basic_info.put("qianyinche", qianyinche.getText());
            basic_info.put("powerType", powerType.getText());
            basic_info.put("model", carStyle.getText());
            basic_info.put("car_long", len.getText());
            basic_info.put("car_width", wid.getText());
            basic_info.put("car_height", hit.getText());
            basic_info.put("color", chexxiangColor.getText());//
            basic_info.put("liang_color", daliangColor.getText());
            basic_info.put("booknum", num.getText().toString());
            basic_info.put("singleprice", price.getText().toString());
            basic_info.put("up", up.getText());
            basic_info.put("down", down.getText());
            basic_info.put("mid", mid.getText());
            basic_info.put("lidigao", lidigao.getText().toString());
            basic_info.put("houxuan", hzbj.getText().toString());
            basic_info.put("qianxuan", qianxuan.getText().toString());
            basic_info.put("zhongxinju", zhongxinju.getText().toString());
            basic_info.put("qianyinxiao", qianyinxiao.getText());
            basic_info.put("qianyinzuo", qianyinzuo.getText());
            basic_info.put("banhuang", banhuang.getText());
            basic_info.put("bianliang", bianliang.getText());
            basic_info.put("wcheng_type", wcheng1.getText());
            basic_info.put("wcheng_num", wchengNum.getText().toString());
            basic_info.put("wcheng_mark", wcheng2.getText().toString());
            basic_info.put("fangshuicao", fangshuicao.getText());
            basic_info.put("diban", diban.getText());
            basic_info.put("liangbaojiao", liangbaojiao.getText().toString());
            basic_info.put("fangguan", fangguan.getText().toString());
            basic_info.put("celanban", celanban.getText());
            basic_info.put("celanban_mark", celanbanMark.getText());
            basic_info.put("houmen", houmen.getText());
            basic_info.put("houmen_mark", houmenMark.getText());
            basic_info.put("penggan", penggan.getText());
            basic_info.put("pati", pati.getText());
            basic_info.put("xiaokuang", xiaokuang.getText());
            basic_info.put("jinshengqi", jinshengqiNum.getText());
            basic_info.put("jinshengqi_mark", jinshenqiMark.getText());
            basic_info.put("box_left", gjxLeft.getText());
            basic_info.put("box_right", gjxRight.getText());
            basic_info.put("btzj", btzj.getText());
            basic_info.put("btsjq", btsjq.getText());
            basic_info.put("abs", abs.getText());
            basic_info.put("shuibaokong", shuibaokong.getText());
            basic_info.put("gangquan", gangquan.getText());
            basic_info.put("gangquan_num", gangquanNum.getText().toString());
            basic_info.put("luntai", luntai.getText());
            basic_info.put("luntai_num", luntaiNum.getText().toString());
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

    /**
     * 判断必要输入框是否填写完全
     *
     * @return
     */
    private String judgeEdit() {
        String toastInfo = "";
        if (TextUtils.isEmpty(chexxiangColor.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditcolor);
            return toastInfo;
        }
        if (TextUtils.isEmpty(daliangColor.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlcolor);
            return toastInfo;
        }
        if (TextUtils.isEmpty(num.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditOrderCount);
            return toastInfo;
        }
        if (TextUtils.isEmpty(price.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlprice);
            return toastInfo;
        }
        if (TextUtils.isEmpty(lidigao.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlidigao);
            return toastInfo;
        }
        if (TextUtils.isEmpty(wchengNum.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlWchengNum);
            return toastInfo;
        }
        if (TextUtils.isEmpty(jinshengqiNum.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdljinshengqi);
            return toastInfo;
        }
        if (TextUtils.isEmpty(amtDx.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlamtDx);
            return toastInfo;
        }
        if (dingjin.isChecked()) {
            if (TextUtils.isEmpty(deposit.getText().toString())) {
                toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdldingjin);
                return toastInfo;
            }
        }
        if (TextUtils.isEmpty(deposit.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdldingjin);
            return toastInfo;
        }

        if (TextUtils.isEmpty(amt.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdlamt);
            return toastInfo;
        }
        if (TextUtils.isEmpty(applyDate.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.auditdleaveTime);
            return toastInfo;
        }

        return toastInfo;
    }


    private void isNewCar() {
        if (isNew.isChecked()) {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.newCar), null);
        } else {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.zhihuan), null);
        }
    }

    /**
     * 获取某个输入框的输入值，没有输入时赋值为0
     *
     * @param editText
     * @return
     */
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

    /**
     * 监听点击退出按钮
     */
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
                ContractInputActivity.this.finish();
            }
        });
        dialog.show();

    }


}
