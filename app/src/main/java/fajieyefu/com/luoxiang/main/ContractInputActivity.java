package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
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
import android.widget.CompoundButton;
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
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.MySpinnerForFreeInventory;
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
    MySpinnerForFreeInventory up;
    @BindView(R.id.down)
    MySpinnerForFreeInventory down;
    @BindView(R.id.mid)
    MySpinnerForFreeInventory mid;
    @BindView(R.id.lidigao)
    EditText lidigao;
    @BindView(R.id.hzbj)
    EditText hzbj;
    @BindView(R.id.qianxuan)
    EditText qianxuan;
    @BindView(R.id.zhongxinju)
    EditText zhongxinju;
    @BindView(R.id.qianyinxiao)
    MySpinnerForFreeInventory qianyinxiao;
    @BindView(R.id.qianyinzuo)
    MySpinnerForFree qianyinzuo;
    @BindView(R.id.banhuang)
    MySpinnerForFreeInventory banhuang;
    @BindView(R.id.bianliang)
    MySpinnerForFreeInventory bianliang;
    @BindView(R.id.wcheng1)
    MySpinnerForFreeInventory wcheng1;
    @BindView(R.id.wcheng2)
    EditText wcheng2;
    @BindView(R.id.fangshuicao)
    MySpinnerForFree fangshuicao;
    @BindView(R.id.diban)
    MySpinnerForFreeInventory diban;
    @BindView(R.id.liangbaojiao)
    EditText liangbaojiao;

    @BindView(R.id.celanban)
    MySpinnerForFreeInventory celanban;
    @BindView(R.id.celanban_mark)
    EditText celanbanMark;
    @BindView(R.id.houmen)
    MySpinnerForFreeInventory houmen;
    @BindView(R.id.houmen_mark)
    EditText houmenMark;
    @BindView(R.id.penggan)
    MySpinnerForFreeInventory penggan;
    @BindView(R.id.pati)
    MySpinnerForFree pati;
    @BindView(R.id.xiaokuang)
    MySpinnerForFreeInventory xiaokuang;
    @BindView(R.id.jinshengqi_num)
    EditText jinshengqiNum;
    @BindView(R.id.jinshenqi_mark)
    EditText jinshenqiMark;
    @BindView(R.id.gjx_left)
    MySpinnerForFreeInventory gjxLeft;
    @BindView(R.id.gjx_right)
    MySpinnerForFreeInventory gjxRight;
    @BindView(R.id.btzj)
    MySpinnerForFree btzj;
    @BindView(R.id.btsjq)
    MySpinnerForFree btsjq;
    @BindView(R.id.abs)
    MySpinnerForFreeInventory abs;
    @BindView(R.id.shuibaokong)
    EditText shuibaokong;
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
    @BindView(R.id.orderNumber)
    EditText orderNumberEdit;
    @BindView(R.id.endCustomerName)
    EditText endCustomerName;
    @BindView(R.id.endCustomerPhone)
    EditText endCustomerPhone;
    @BindView(R.id.qianyinche)
    EditText qianyinche;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.huangyouzui)
    EditText huangyouzui;
    @BindView(R.id.houzhuan_layout)
    LinearLayout houzhuanLayout;
    @BindView(R.id.huangyouzui_layout)
    LinearLayout huangyouzuiLayout;
    @BindView(R.id.outlinesize)
    EditText outlinesize;
    @BindView(R.id.outlinesizelayout)
    LinearLayout outlinesizelayout;
    @BindView(R.id.others)
    TextView others;
    @BindView(R.id.oldPrice)
    EditText oldPrice;
    @BindView(R.id.oldPriceLayout)
    LinearLayout oldPriceLayout;
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
    private List<String> list_celanban;
    private List<String> list_houmen;
    private List<String> list_wcheng;
    private List<String> list_diban;
    private List<String> list_up;
    private List<String> list_down;
    private List<String> list_mid;
    private List<String> list_qianyinxiao;
    private List<String> list_banhuang;
    private List<String> list_bianliang;
    private List<String> list_boxLeft;
    private List<String> list_boxRight;
    private List<String> list_abs;
    private List<String> list_gangquan;
    private List<String> list_luntai;
    private List<String> list_axis;
    private List<String> list_penggan;
    private List<String> list_xiaokuang;
    private ObtainBean customer_bean;
    private ToolUtil toolUtil;
    private UserInfo userInfo;
    private String standardId;
    private String orderNumber;
    private Dialog dialog;
    private int orderId;
    private int commitType;

    JSONObject basic_info = new JSONObject();
    JSONArray config_info = new JSONArray();
    private Button back;
    private ArrayList<Area> areaList = new ArrayList<>();
    private String cDCCode;
    private String zText;
    private String eText;
    private String zlText;
    private String ejText;
    private String ordinaryContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_details);
        ButterKnife.bind(this);
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        initView();
        addTextChangeListener();
        initFreeSpinner();
        toolUtil.dismissProgressDialog();
        standardId = DaoBean.getInventoryClassById(1).getStandardId();


    }


    private void addTextChangeListener() {
        isNew.setOnCheckedChangeListener(isNewCheckListener);
        amt.addTextChangedListener(watcher);
    }

    @SuppressWarnings("ResourceType")
    private void initView() {
        zText = this.getResources().getString(R.string.z);
        eText = this.getResources().getString(R.string.e);
        zlText = this.getResources().getString(R.string.zl);
        ejText = this.getResources().getString(R.string.ej);

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
     * 加载下拉选项框的固定的选项
     */
    private void initFreeSpinner() {
        /**
         * 获取下拉选项框固定值
         */
        //上
        list_up= DaoBean.getInventoryNameByCCode("1508");
        up.setData(list_up);
        //下
        list_down = DaoBean.getInventoryNameByCCode("1509");
        down.setData(list_down);
        //立板
        list_mid = DaoBean.getInventoryNameByCCode("1510");
        mid.setData(list_mid);
        //侧栏板
        list_celanban= DaoBean.getInventoryNameByCCode("020105");
        celanban.setData(list_celanban);
       //后门
        list_houmen= DaoBean.getInventoryNameByCCode("020106");
        houmen.setData(list_houmen);
        //w称
        list_wcheng = DaoBean.getInventoryNameByCCode("0308");
        wcheng1.setData(list_wcheng);
        //底板
        list_diban = DaoBean.getInventoryNameByCCode("1505");
        diban.setData(list_diban);
        //牵引销
        list_qianyinxiao =DaoBean.getInventoryNameByCCode("0410");
        qianyinxiao.setData(list_qianyinxiao);
        //板簧
        list_banhuang =DaoBean.getInventoryNameByCCode("1514");
        banhuang.setData(list_banhuang);
        //边梁
        list_bianliang =DaoBean.getInventoryNameByCCode("1512");
        bianliang.setData(list_bianliang);

        //工具箱左
        list_boxLeft =DaoBean.getInventoryNameByCCode("1503");
        gjxLeft.setData(list_boxLeft);
        //工具箱右
        list_boxRight =DaoBean.getInventoryNameByCCode("1504");
        gjxRight.setData(list_boxRight);
        //ABS
        list_abs =DaoBean.getInventoryNameByCCode("0411");
       abs.setData(list_abs);
        //钢圈
        list_gangquan =DaoBean.getInventoryNameByCCode("0405");
        gangquan.setData(list_gangquan);
        //轮胎
        list_luntai =DaoBean.getInventoryNameByCCode("0404");
        luntai.setData(list_luntai);
        //车轴
        list_axis =DaoBean.getInventoryNameByCCode("1513");
        chezhou.setData(list_axis);
        //蓬杆
        list_penggan =DaoBean.getInventoryNameByCCode("1502");
        penggan.setData(list_penggan);
        //篷布框
        list_xiaokuang =DaoBean.getInventoryNameByCCode("1507");
        xiaokuang.setData(list_xiaokuang);


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
        if (orderId!=0){
            JSONArray ordinaryJson=null;
             JSONObject json=null;
            try {
                 ordinaryJson = new JSONArray(ordinaryContent);
                 json = (JSONObject) ordinaryJson.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            endCustomerPhone.setText(json.optString("endCustomerPhone").trim());
            endCustomerName.setText(json.optString("endCustomerName").trim());
            area.setText(json.optString("cDCName").trim());
            if (json.optInt("isNew")==0){
                isNew.setChecked(false);
            }
            qianyinche.setText(json.optString("qianyinche").trim());
            powerType.setText(json.optString("powerType").trim());
            carStyle.setText(json.optString("model").trim());
            up.setText(json.optString("up").trim());
            down.setText(json.optString("down").trim());
            mid.setText(json.optString("mid").trim());
            outlinesize.setText(json.optString("outlinesize").trim());
            chexxiangColor.setText(json.optString("color").trim());
            daliangColor.setText(json.optString("liang_color").trim());
            num.setText(json.optString("booknum").trim());
            if (!TextUtils.isEmpty(json.optString("singleprice"))){
                price.setText((int)( Float.parseFloat(json.optString("singleprice".trim())))+"");
            }
            len.setText(json.optString("car_long").trim());
            wid.setText(json.optString("car_width").trim());
            hit.setText(json.optString("car_height").trim());
            lidigao.setText(json.optString("lidigao").trim());
            hzbj.setText(json.optString("houxuan").trim());
            huangyouzui.setText(json.optString("butterMouthHeight").trim());
            qianxuan.setText(json.optString("qianxuan").trim());
            zhongxinju.setText(json.optString("zhongxinju").trim());
            qianyinxiao.setText(json.optString("qianyinxiao").trim());
            qianyinzuo.setText(json.optString("qianyinzuo").trim());
            banhuang.setText(json.optString("banhuang").trim());
            bianliang.setText(json.optString("bianliang").trim());
            wcheng1.setText(json.optString("wcheng_type").trim());
            wchengNum.setText(json.optString("wcheng_num").trim());
            wcheng2.setText(json.optString("wcheng_mark").trim());
            fangshuicao.setText(json.optString("fangshuicao").trim());
            diban.setText(json.optString("diban").trim());
            liangbaojiao.setText(json.optString("liangbaojiao").trim());
            fangguan.setText(json.optString("fangguan").trim());
            celanban.setText(json.optString("celanban").trim());
            celanbanMark.setText(json.optString("celanban_mark").trim());
            houmen.setText(json.optString("houmen").trim());
            houmenMark.setText(json.optString("houmen_mark").trim());
            penggan.setText(json.optString("penggan").trim());
            pati.setText(json.optString("pati").trim());
            xiaokuang.setText(json.optString("xiaokuang").trim());
            jinshengqiNum.setText(json.optString("jinshengqi").trim());
            jinshenqiMark.setText(json.optString("jinshengqi_mark").trim());
            gjxLeft.setText(json.optString("box_left").trim());
            gjxRight.setText(json.optString("box_right").trim());
            btzj.setText(json.optString("btzj").trim());
            btsjq.setText(json.optString("btsjq").trim());
            abs.setText(json.optString("abs").trim());
            shuibaokong.setText(json.optString("shuibaokong").trim());
            gangquan.setText(json.optString("gangquan").trim());
            gangquanNum.setText(json.optString("gangquan_num").trim());
            luntai.setText(json.optString("luntai").trim());
            luntaiNum.setText(json.optString("luntai_num").trim());
            chezhou.setText(json.optString("chezhou").trim());
            remark.setText(json.optString("marks").trim());
            if (json.optInt("bmustbook")==0){
                dingjin.setChecked(false);
            }
            if (json.optInt("cDefine1")==1){
                yunfei.setChecked(true);
            }
            if (!TextUtils.isEmpty(json.optString("payedmoney"))){
                deposit.setText((int)Float.parseFloat(json.optString("payedmoney").trim())+"");
            }
            if (!TextUtils.isEmpty(json.optString("orderMoney"))){
                amt.setText((int)Float.parseFloat(json.optString("orderMoney").trim())+"");
            }

            amtDx.setText(json.optString("ordermoney_dx").trim());
            applyDate.setText(json.optString("leaveTime").trim());
            obtainType.setText(json.optString("carstyle").trim());
            if (!TextUtils.isEmpty(json.optString("oldPrice"))){
                oldPrice.setText((int) Float.parseFloat(json.optString("oldPrice").trim())+"");
            }
            if (json.optInt("urgent_flag")==1){
                urgent.setChecked(true);
            }





        }
    }

    @OnClick(R.id.caculator)
    public void onViewClicked() {
        isNewCar();
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

    private boolean dealWithData() {
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
        String width = wid.getText();
        String height = hit.getText();
        //确定车架上
        if (DaoBean.getInventoryLikeCCode("1508",up.getText(),ze,width,height).size()!=1&&!up.getText().equals("不选")){
            Toast.makeText(this, "车架（上）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定车架下
        if (DaoBean.getInventoryLikeCCode("1509",down.getText(),ze,width,height).size()!=1&&!down.getText().equals("不选")){
            Toast.makeText(this, "车架（下）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;

        }
        //确定车架立
        if (DaoBean.getInventoryLikeCCode("1510",mid.getText(),ze,width,height).size()!=1&&!mid.getText().equals("不选")){
            Toast.makeText(this, "车架（立）有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }

        //确定牵引销
        if(DaoBean.getInventoryLikeCCode("0410",qianyinxiao.getText(),ze,width,height).size()!=1&&!qianyinxiao.getText().equals("不选")){
            Toast.makeText(this, "牵引销有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定板簧
        if (DaoBean.getInventoryLikeCCode("1514",banhuang.getText(),ze,width,height).size()!=1&&!banhuang.getText().equals("不选")){
            Toast.makeText(this, "板簧有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }

        //确定边梁
        if (DaoBean.getInventoryLikeCCode("1512",bianliang.getText(),ze,width,height).size()!=1&&!bianliang.getText().equals("不选")){
            Toast.makeText(this, "边梁有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定W称
        int wcheng_size = DaoBean.getInventoryLikeCCode("0308",wcheng1.getText(),ze,width,height).size();
        if (!wcheng1.getText().equals("不选")&&wcheng_size!=1){
            if (wcheng_size==0){
                Toast.makeText(this, "此宽度下没有该W称样式", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(this, "W称有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        //确定底板
        int diban_size = DaoBean.getInventoryLikeCCode("1505",diban.getText(),ze,width,height).size();
        if (!diban.getText().equals("不选")&&diban_size!=1){
            if (diban_size==0){
                Toast.makeText(this, "此宽度下没有该底板样式", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "底板有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        //确定侧栏板
        int celanban_size = DaoBean.getInventoryLikeCCode("020105",celanban.getText(),ze,width,height).size();
        if (!celanban.getText().equals("不选")&&celanban_size!=1){
            if (celanban_size==0){
                Toast.makeText(this, "此高度下没有该侧栏板样式", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "侧栏板数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        //确定后门
        int houmen_size = DaoBean.getInventoryLikeCCode("020106",houmen.getText(),ze,width,height).size();
        if (!houmen.getText().equals("不选")&&houmen_size!=1){
            if (houmen_size==0){
                Toast.makeText(this, "此高度下没有该后门样式", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "后门数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        //确定蓬杆
        if (DaoBean.getInventoryLikeCCode("1502",penggan.getText(),ze,width,height).size()!=1&&!penggan.getText().equals("不选")){
            Toast.makeText(this, "蓬杆数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定小框
        if (DaoBean.getInventoryLikeCCode("1507",xiaokuang.getText(),ze,width,height).size()!=1&&!xiaokuang.getText().equals("不选")){
            Toast.makeText(this, "小框数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定左工具箱
        if (DaoBean.getInventoryLikeCCode("1503",gjxLeft.getText(),ze,width,height).size()!=1&&!gjxLeft.getText().equals("不选")){
            Toast.makeText(this, "左工具箱数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定右工具箱
        if (DaoBean.getInventoryLikeCCode("1504",gjxRight.getText(),ze,width,height).size()!=1&&!gjxRight.getText().equals("不选")){
            Toast.makeText(this, "右工具箱数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //ABS
        if (DaoBean.getInventoryLikeCCode("0411",abs.getText(),ze,width,height).size()!=1&&!abs.getText().equals("不选")){
            Toast.makeText(this, "ABS数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定钢圈
        if (DaoBean.getInventoryLikeCCode("0405",gangquan.getText(),ze,width,height).size()!=1&&!gangquan.getText().equals("不选")){
            Toast.makeText(this, "钢圈数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定轮胎
        if (DaoBean.getInventoryLikeCCode("0404",luntai.getText(),ze,width,height).size()!=1&&!luntai.getText().equals("不选")){
            Toast.makeText(this, "轮胎数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        //确定车轴
        if (DaoBean.getInventoryLikeCCode("1513",chezhou.getText(),ze,width,height).size()!=1&&!chezhou.getText().equals("不选")){
            Toast.makeText(this, "车轴数据有错误，请联系管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<Inventory> lmjInventorys2 = DaoBean.getInventoryLikeCCode("020104", hit.getText(), ze,width,height);
        if (lmjInventorys2 != null && lmjInventorys2.size() != 0) {
        }

        List<Inventory> zzInventorys2 = DaoBean.getInventoryLikeCCode("020107", hit.getText(), ze,width,height);
        Inventory inventoryZhanzhu = getZhanZhuInventory(zzInventorys2);
        if (inventoryZhanzhu != null) {
            DaoBean.upNoSelected(inventoryZhanzhu.getCInvCCode(), inventoryZhanzhu.getCInvCode());
        }
        List<Inventory> diPanInventorys2 = DaoBean.getInventoryLikeCCode("1511", celanban.getText(), ze_text,width,height);
        if (diPanInventorys2 != null && diPanInventorys2.size() != 0) {
        }
        return true;

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
//    private TextWatcher watcher_qianyinche = new TextWatcher() {
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
//            Inventory qianyinche_cur = DaoBean.getSelectedInventoryLikeCCode("14");
//            if (qianyinche_cur != null) {
//                lidigao.setText(qianyinche_cur.getCInvStd());
//            }
//
//        }
//    };
    //监听高度变化
//    private TextWatcher watcher_hgt = new TextWatcher() {
//
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
//            //判断挂车样式为直或者鹅
//            String ze;
//            if (carStyle.getText().contains(zText)) {
//                ze = "Z";
//            } else {
//                ze = "E";
//            }
//            //根据高度、直梁（鹅颈）取出侧栏板样式
//            List<Inventory> cedangbanInventorys2 = DaoBean.getInventoryLikeCCode("020105", hit.getText(), ze);
//            celanban.setData(cedangbanInventorys2);
//            //根据高度、直梁（鹅颈）取出后门样式
//            List<Inventory> houmenInventorys2 = DaoBean.getInventoryLikeCCode("020106", hit.getText(), ze);
//            houmen.setData(houmenInventorys2);
//
//        }
//    };



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
    private CompoundButton.OnCheckedChangeListener isNewCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                huangyouzuiLayout.setVisibility(View.GONE);
                outlinesizelayout.setVisibility(View.GONE);
                houzhuanLayout.setVisibility(View.VISIBLE);
                oldPriceLayout.setVisibility(View.GONE);
                others.setVisibility(View.VISIBLE);


            } else {
                outlinesizelayout.setVisibility(View.VISIBLE);
                huangyouzuiLayout.setVisibility(View.VISIBLE);
                houzhuanLayout.setVisibility(View.GONE);
                oldPriceLayout.setVisibility(View.VISIBLE);
                others.setVisibility(View.GONE);
            }
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
        } else {
            DaoBean.updateCounts("0308", wchengCounts - 10);
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
        if (!TextUtils.isEmpty(judgeEdit())) {
            Toast.makeText(this, judgeEdit(), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!dealWithData()){
            return  false;
        }
        monitorEditNum();
        isNewCar();
        basic_info = new JSONObject();

        try {
            basic_info.put("customer", customer_bean.getName());
            basic_info.put("mobile", customer_bean.getcCusHand());
            basic_info.put("address", customer_bean.getcCusAddress());
            basic_info.put("contractsMan", customer_bean.getcCusLPerson());
            basic_info.put("huangyouzui", huangyouzui.getText().toString());
            basic_info.put("endCustomerName", endCustomerName.getText().toString());
            basic_info.put("endCustomerPhone", endCustomerPhone.getText().toString());
            basic_info.put("qianyinche", qianyinche.getText().toString());
            basic_info.put("powerType", powerType.getText());
            basic_info.put("model", carStyle.getText());
            basic_info.put("car_long", len.getText());
            basic_info.put("car_width", wid.getText());
            basic_info.put("car_height", hit.getText());
            basic_info.put("color", chexxiangColor.getText());
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
            basic_info.put("orderNumber", orderNumberEdit.getText());
            basic_info.put("butterMouthHeight", huangyouzui.getText().toString());
            basic_info.put("outlinesize", outlinesize.getText().toString());
            basic_info.put("oldPrice", oldPrice.getText().toString());

            if (carStyle.getText().contains("直")) {
                basic_info.put("classCode", "0101");

            } else {
                basic_info.put("classCode", "0102");
            }
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

    /**
     * 判断必要输入框是否填写完全
     *
     * @return
     */
    private String judgeEdit() {
        String toastInfo = "";
        if (TextUtils.isEmpty(qianyinche.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.qinyinche);
            return toastInfo;
        }
        if (TextUtils.isEmpty(endCustomerName.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.endCustomerName);
            return toastInfo;
        }
        if (TextUtils.isEmpty(endCustomerPhone.getText().toString())) {
            toastInfo = ContractInputActivity.this.getResources().getString(R.string.endCustomerPhone);
            return toastInfo;
        }
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
        if (TextUtils.isEmpty(outlinesize.getText().toString())) {
            if (!isNew.isChecked()) {
                toastInfo = ContractInputActivity.this.getResources().getString(R.string.outlinesize);
            }
        }
        if (TextUtils.isEmpty(huangyouzui.getText().toString())) {
            if (!isNew.isChecked()) {
                toastInfo = ContractInputActivity.this.getResources().getString(R.string.huangyouzuiTips);
            }
        }
        if (TextUtils.isEmpty(oldPrice.getText().toString())) {
            if (!isNew.isChecked()) {
                toastInfo = ContractInputActivity.this.getResources().getString(R.string.oldWeightTips);
            }
        }

        return toastInfo;
    }


    private void isNewCar() {
        if (isNew.isChecked()) {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.newCar), null,null,null);
        } else {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.zhihuan), null,null,null);
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
