package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zzti.fengyongge.imagepicker.PhotoPreviewActivity;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;
import com.zzti.fengyongge.imagepicker.model.PhotoModel;
import com.zzti.fengyongge.imagepicker.util.CommonUtils;
import com.zzti.fengyongge.imagepicker.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.EffectContractAdapter;
import fajieyefu.com.luoxiang.adapter.ProNumAdapter;
import fajieyefu.com.luoxiang.bean.Area;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.Option;
import fajieyefu.com.luoxiang.bean.OptionItem;
import fajieyefu.com.luoxiang.bean.ProNumBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UploadGoodsBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.LinearLayoutForListView;
import fajieyefu.com.luoxiang.layout.MyGridView;
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.MySpinnerForFreeInventory;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.Config;
import fajieyefu.com.luoxiang.util.DbTOPxUtils;
import fajieyefu.com.luoxiang.util.ImageFactory;
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
    @BindView(R.id.proNumLv)
    ListView proNumLv;
    @BindView(R.id.queryNumLayout)
    RelativeLayout queryNumLayout;
    @BindView(R.id.shade_layout)
    LinearLayout shadeLayout;
    @BindView(R.id.queryLinear)
    LinearLayout queryLinear;
    @BindView(R.id.sign_pic)
    ImageView signPic;
    @BindView(R.id.sign)
    Button sign;
    @BindView(R.id.sign_layout)
    LinearLayout signLayout;
    @BindView(R.id.closeProNum)
    Button closeProNum;
    @BindView(R.id.file_layout)
    LinearLayout fileLayout;
    @BindView(R.id.banhuang_mark)
    MySpinnerForFree banhuangMark;
    @BindView(R.id.chezhou_mark)
    MySpinnerForFree chezhouMark;
    @BindView(R.id.otherContractLV)
    LinearLayoutForListView otherContractLV;
    @BindView(R.id.sameTextView)
    TextView sameTextView;
    @BindView(R.id.cantrail)
    MySpinnerForFree cantrail;
    @BindView(R.id.carriage)
    EditText carriage;
    @BindView(R.id.discountFee)
    EditText discountFee;

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
    private List<String> list_cantrail;
    private List<String> list_banhuangMark;
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
    private String zText = this.getResources().getString(R.string.z);
    private String eText = this.getResources().getString(R.string.e);
    private String zlText = this.getResources().getString(R.string.zl);
    private String ejText = this.getResources().getString(R.string.ej);
    private String ordinaryContent;
    private int screenWidth;
    private int screenHeight;
    private List<ProNumBean> ProNumData = new ArrayList<>();
    private String image_path;
    private File signaturefile;
    private Bitmap bitmap;
    private ImageView imageView;
    private List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
    private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();
    private int screen_widthOffset;
    private MyGridView my_imgs_GV;
    private GridImgAdapter gridImgsAdapter;
    private List<ContractBean> sameOrders = new ArrayList<>();
    private EffectContractAdapter effectContractAdapter;
    private JSONArray sameOrdersArray;
    private String commitUrl = CommonData.CommitContract;
    private int actualPrice;
    private double actualWeight;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_input_details);
        ButterKnife.bind(this);
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this);
        initView();
        initData();
        addTextChangeListener();
        toolUtil.dismissProgressDialog();


    }

    private void initData() {
        JSONObject param = new JSONObject();
        try {
            param.put("username", userInfo.getUsername());
            param.put("password", userInfo.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);

        OkHttpUtils.postString()
                .url(CommonData.initFreeSpinnerUrl)
                .content(param.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new InitDataResponCallBack());
    }


    private void addTextChangeListener() {
        isNew.setOnCheckedChangeListener(isNewCheckListener);
        amt.addTextChangedListener(watcher);
    }


    private void initView() {
        userInfo = DaoBean.getUseInfoById(1);
        standardId = DaoBean.getInventoryClassById(1).getStandardId();
        initIntentData();
        initViewParam();
        initTitleView();
        initOnClickListener();
    }

    /**
     * 初始化点击事件监听
     */
    private void initOnClickListener() {
        back.setOnClickListener(this);
        sign.setOnClickListener(this);
        signPic.setOnClickListener(this);
        closeProNum.setOnClickListener(this);
        shadeLayout.setOnClickListener(this);
    }

    /**
     * 获取Intent携带参数
     */
    private void initIntentData() {
        Intent intent = getIntent();
        customer_bean = (ObtainBean) intent.getSerializableExtra("customer");
        areaList = (ArrayList<Area>) intent.getSerializableExtra("area");
        commitType = intent.getIntExtra("commitType", 0);
        orderNumber = intent.getStringExtra("orderNumber");
        orderId = intent.getIntExtra("orderId", 0);
        ordinaryContent = intent.getStringExtra("ordinaryContent");
        sameOrders = (List<ContractBean>) intent.getSerializableExtra("sameOrders");
        customer.setText(customer_bean.getName());
        mobile.setText(customer_bean.getcCusHand());
        address.setText(customer_bean.getcCusAddress());
        contactsMan.setText(customer_bean.getcCusPerson());

        if (commitType==1){
            commitUrl = CommonData.modifyContract;
        }else{
            commitUrl = CommonData.applyChangeContract;
        }
    }

    /**
     * 初始化标题
     */
    private void initTitleView() {
        title.setTitleText(this.getResources().getString(R.string.orderInput));
        back = (Button) title.findViewById(R.id.back);
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ContractInputActivity.this);
                View view = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.more_layout, null);
                TextView commit = (TextView) view.findViewById(R.id.commit);
                Button preview = (Button) view.findViewById(R.id.preview);
                Button queryProNum = (Button) view.findViewById(R.id.queryProNum);
                commit.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                queryProNum.setVisibility(View.VISIBLE);
                preview.setOnClickListener(ContractInputActivity.this);
                commit.setOnClickListener(ContractInputActivity.this);
                queryProNum.setOnClickListener(ContractInputActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog.show();
            }
        });


    }
    @SuppressWarnings("ResourceType")
    private void initViewParam() {
        //获取手机屏幕尺寸
        int[] hm = new ToolUtil().getScreenWH(this);
        screenWidth = hm[0];
        screenHeight = hm[1];
        /**
         * 初始化订单查询界面的参数
         */
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) queryLinear.getLayoutParams();
        linearParams.width = screenWidth / 10 * 9;
        linearParams.height = screenHeight / 3 * 2;
        queryLinear.setLayoutParams(linearParams);
        /**
         * 初始化日期选择dialog参数
         */
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

        //初始化附件控件参数
        Config.ScreenMap = Config.getScreenSize(this, this);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screen_widthOffset = (display.getWidth() - (4 * DbTOPxUtils.dip2px(this, 2))) / 4;
        my_imgs_GV = (MyGridView) findViewById(R.id.my_goods_GV);
        gridImgsAdapter = new GridImgAdapter();
        my_imgs_GV.setAdapter(gridImgsAdapter);
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();
    }

    /**
     * 加载下拉选项框的固定的选项
     */
    private void initFreeSpinner() {
        /**
         * 获取下拉选项框固定值
         */
        //上
        list_up = DaoBean.getInventoryNameByCCode("1508");
        up.setData(list_up);
        //下
        list_down = DaoBean.getInventoryNameByCCode("1509");
        down.setData(list_down);
        //立板
        list_mid = DaoBean.getInventoryNameByCCode("1510");
        mid.setData(list_mid);
        //侧栏板
        list_celanban = DaoBean.getInventoryNameByCCode("020105");
        celanban.setData(list_celanban);
        //后门
        list_houmen = DaoBean.getInventoryNameByCCode("020106");
        houmen.setData(list_houmen);
        //w称
        list_wcheng = DaoBean.getInventoryNameByCCode("0308");
        wcheng1.setData(list_wcheng);
        //底板
        list_diban = DaoBean.getInventoryNameByCCode("1505");
        diban.setData(list_diban);
        //牵引销
        list_qianyinxiao = DaoBean.getInventoryNameByCCode("0410");
        qianyinxiao.setData(list_qianyinxiao);
        //板簧
        list_banhuang = DaoBean.getInventoryNameByCCode("1514");
        banhuang.setData(list_banhuang);
        //边梁
        list_bianliang = DaoBean.getInventoryNameByCCode("1512");
        bianliang.setData(list_bianliang);

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
        //蓬杆
        list_penggan = DaoBean.getInventoryNameByCCode("1502");
        penggan.setData(list_penggan);
        //篷布框
        list_xiaokuang = DaoBean.getInventoryNameByCCode("1507");
        xiaokuang.setData(list_xiaokuang);
        //上边梁
        list_cantrail = DaoBean.getInventoryNameByCCode("1524");
        cantrail.setData(list_cantrail);
        cantrail.setText("0");
        //大区列表
        list_area = new ArrayList<>();
        for (Area area : areaList) {
            list_area.add(area.getcDCName());
        }
        /**
         * spinner设置参数
         */
        powerType.setData(list_powerType);
        len.setData(list_len);
        len.setText("13000");
        wid.setData(list_wid);
        wid.setText("2550");
        hit.setData(list_hgt);
        qianyinzuo.setData(list_qyz);
        banhuangMark.setData(list_banhuangMark);
        chezhouMark.setData(list_banhuangMark);
        obtainType.setData(list_obtaintype);
        fangshuicao.setData(list_fsc);
        pati.setData(list_pati);
        btzj.setData(list_beitaizhijia);
        carStyle.setData(list_model);
        btsjq.setData(list_btsjq);
        area.setData(list_area);
        area.setText(customer_bean.getcDCName());
        banhuangMark.setText("厂配");
        chezhouMark.setText("厂配");
        initModifyData();

    }

    /**
     * 判断是否为修改或者变更数据，将合同原数据还原
     */
    private void initModifyData() {
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
            if (!TextUtils.isEmpty(json.optString("singleprice"))) {
                price.setText((int) (Float.parseFloat(json.optString("singleprice".trim()))) + "");
            }
            len.setText(json.optString("car_long").trim());
            wid.setText(json.optString("car_width").trim());
            hit.setText(json.optString("car_height").trim());
            lidigao.setText(json.optString("lidigao").trim());
            hzbj.setText(json.optString("houxuan").trim());
            huangyouzui.setText(json.optString("butterMouthHeight").trim());
            chezhouMark.setText(json.optString("chezhouMark").trim());
            banhuangMark.setText(json.optString("banhuangMark").trim());
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
            cantrail.setText(json.optString("cantrail").trim());
            carriage.setText(json.optString("carriage").trim());
            discountFee.setText(json.optString("discountFee").trim());
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
            applyDate.setText(json.optString("leaveTime").trim());
            obtainType.setText(json.optString("carstyle").trim());
            if (!TextUtils.isEmpty(json.optString("oldPrice"))) {
                oldPrice.setText(json.optString("oldPrice").trim() + "");
            }
            if (json.optInt("urgent_flag") == 1) {
                urgent.setChecked(true);
            }
            sameTextView.setVisibility(View.VISIBLE);
            effectContractAdapter = new EffectContractAdapter(this, sameOrders);
            otherContractLV.setAdapter(effectContractAdapter);
            if (!TextUtils.isEmpty(json.optString("filePath"))) {
                if (img_uri.size() > 0) {
                    img_uri.remove(img_uri.size() - 1);
                }
                String[] fileArray = json.optString("filePath").split(",");
                for (int i = 0; i < fileArray.length; i++) {
                    String[] arr = fileArray[i].split("//");
                    String fileName = arr[arr.length - 1];
                    download(fileArray[i], ImageFactory.PATH_PHOTOGRAPH, fileName, 0);

                }
                img_uri.add(null);
                gridImgsAdapter.notifyDataSetChanged();
            }
            if (!TextUtils.isEmpty(json.optString("signaturePath"))) {
                SharedPreferences sps = getSharedPreferences("image_info",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = sps.edit();
                image_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + userInfo.getUsername() + ".jpg";
                editor.putString("image_path", image_path);
                editor.apply();
                download(json.optString("signaturePath"), Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/", userInfo.getUsername() + ".jpg", 1);
            }


        }
    }

    @OnClick(R.id.caculator)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                caculator();
            }
        }).start();


    }

    /**
     * 计算价格和重量
     */
    private void caculator() {
        if (!dealWithData()) {
            return;
        }
        monitorEditNum();


        int standarMoney = Integer.parseInt(DaoBean.getInventoryClassById(1).getStandardMoney());
        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();
         actualPrice = standarMoney;
         actualWeight = 0;

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
        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                //更新UI
                View view = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.price_weight_layout, null);
                TextView price = (TextView) view.findViewById(R.id.price);
                TextView weight = (TextView) view.findViewById(R.id.weight);
                weight.setText((int) actualWeight + "");
                price.setText(actualPrice + "");
                Dialog caculatorDialog = new Dialog(ContractInputActivity.this);
                caculatorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                caculatorDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                caculatorDialog.show();
            }

        });

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.queryProNum:
                initQueryProNumView();
                break;
            case R.id.commit:
                doConfirm();
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
            case R.id.shade_layout:
                queryNumLayout.setVisibility(View.GONE);
                break;
            case R.id.closeProNum:
                queryNumLayout.setVisibility(View.GONE);
                break;
            case R.id.sign:
                Intent intent3 = new Intent(ContractInputActivity.this, SignatureActivity.class);
                startActivity(intent3);
                break;
            case R.id.sign_pic:
                if (bitmap == null) {
                    Toast.makeText(ContractInputActivity.this, "没有签名，请添加", Toast.LENGTH_SHORT).show();
                } else {
                    Display display = ContractInputActivity.this.getWindowManager().getDefaultDisplay();
                    int screenWidth = display.getWidth();
                    Dialog signDialog = new Dialog(this);
                    signDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    imageView = new ImageView(this);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    signDialog.setContentView(imageView, new ViewGroup.LayoutParams(screenWidth / 2, screenWidth / 2));
                    signDialog.show();
                }
                break;
            case R.id.back:
                isExit();
                break;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }


    }

    private void doConfirm() {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage(this.getResources().getString(R.string.sureOperate));
        confirmDialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toolUtil.showProgressDialog(ContractInputActivity.this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            commitData("commit");
                        } catch (Exception e) {
                            toolUtil.dismissProgressDialog();
                            showToastOnUi(e.getCause() +"......................"+e.getMessage());
                        }
                    }
                }).start();


            }
        });
        confirmDialog.show();
    }

    private void initQueryProNumView() {
        JSONObject param = new JSONObject();
        try {
            param.put("username", userInfo.getUsername());
            param.put("password", userInfo.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.getEnableNumOfDays)
                .content(param.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new EnableNumOfDaysResponCallBack());
    }


    /**
     * 提交数据
     *
     * @param msg
     */
    private void commitData(String msg) throws Exception {


        JSONObject content = new JSONObject();
        Gson gson = new GsonBuilder().create();
        sameOrdersArray = new JSONArray();
        config_info = new JSONArray();

        //判断必填项是否填写
        if (!createBaseJsonData()) {
            toolUtil.dismissProgressDialog();
            return;
        }
        userInfo = DaoBean.getUseInfoById(1);

        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();


            for (Inventory inventoryTemp : inventories) {
                if (Integer.parseInt(inventoryTemp.getCounts()) == 0) {
                    continue;
                }
                config_info.put(new JSONObject(gson.toJson(inventoryTemp, Inventory.class)));
            }
            content.put("config_info", config_info);


            if (sameOrders != null) {
                for (ContractBean contractBean : sameOrders) {
                    if (contractBean.getSelect_flag() == 0) {
                        continue;
                    }
                    sameOrdersArray.put(new JSONObject(gson.toJson(contractBean, ContractBean.class)));
                }
            }

            content.put("sameOrders", sameOrdersArray);



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



        Log.i("传入数据", content.toString());
        Map<String, File> map = new HashMap<>();
        Map<String, File> signatureMap = new HashMap<>();
        if (!signaturefile.exists()){
            toolUtil.dismissProgressDialog();
            showToastOnUi("签名无效，请重新签名！");
            return;
        }
        signatureMap.put("signature", signaturefile);
        ImageFactory imageFactory = new ImageFactory();

        for (int i = 0; i < img_uri.size() - 1; i++) {
            File file = new File(img_uri.get(i).getUrl());
            map.put(i + ".jpg", file);
        }
        OkHttpUtils.post()
                .url(commitUrl)
                .files("file", map)
                .files("signature", signatureMap)
                .addParams("content", content.toString())
                .build()
                .execute(new ResponCallBack());
    }

    /**
     * 判断所选配置是否有对应的值，是否是唯一项
     * @return
     */
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
        String width = wid.getText();
        String height = hit.getText();


        //确定车架上
        if (DaoBean.getInventoryLikeCCode("1508", up.getText(), ze, width, height).size() != 1 && !up.getText().equals("不选")) {
            showToastOnUi("车架（上）有错误，请联系管理员");
            return false;
        }
        //确定车架下
        if (DaoBean.getInventoryLikeCCode("1509", down.getText(), ze, width, height).size() != 1 && !down.getText().equals("不选")) {
            showToastOnUi("车架（下）有错误，请联系管理员");
            return false;

        }
        //确定车架立
        if (DaoBean.getInventoryLikeCCode("1510", mid.getText(), ze, width, height).size() != 1 && !mid.getText().equals("不选")) {
            showToastOnUi("车架（立）有错误，请联系管理员");
            return false;
        }

        //确定牵引销
        if (DaoBean.getInventoryLikeCCode("0410", qianyinxiao.getText(), ze, width, height).size() != 1 && !qianyinxiao.getText().equals("不选")) {
            showToastOnUi("牵引销有错误，请联系管理员");
            return false;
        }
        //确定板簧
        if (DaoBean.getInventoryLikeCCode("1514", banhuang.getText(), banhuangMark.getText(), width, height).size() != 1 && !banhuang.getText().equals("不选") && !banhuangMark.getText().equals("自备")) {
            showToastOnUi("板簧有错误，请联系管理员");
            return false;
        }

        //确定边梁
        if (DaoBean.getInventoryLikeCCode("1512", bianliang.getText(), ze, width, height).size() != 1 && !bianliang.getText().equals("不选")) {
            showToastOnUi("边梁有错误，请联系管理员");
            return false;
        }
        //确定W称
        int wcheng_size = DaoBean.getInventoryLikeCCode("0308", wcheng1.getText(), ze, width, height).size();
        if (!wcheng1.getText().equals("不选") && wcheng_size != 1) {
            if (wcheng_size == 0) {
                showToastOnUi("此宽度下没有该W称样式");
            } else {
                showToastOnUi("W称有错误，请联系管理员");
            }
            return false;
        }

        //确定底板
        int diban_size = DaoBean.getInventoryLikeCCode("1505", diban.getText(), ze, width, height).size();
        if (!diban.getText().equals("不选") && diban_size != 1) {
            if (diban_size == 0) {
                showToastOnUi("此宽度下没有该底板样式");
            } else {
                showToastOnUi("底板有错误，请联系管理员");
            }
            return false;
        }
        //确定侧栏板
        int celanban_size = DaoBean.getInventoryLikeCCode("020105", celanban.getText(), ze, width, height).size();
        if (!celanban.getText().equals("不选") && celanban_size != 1) {
            if (celanban_size == 0) {
                showToastOnUi("此高度下没有该侧栏板样式");
            } else {
                showToastOnUi("侧栏板数据有错误，请联系管理员");
            }
            return false;
        }
        //确定后门
        int houmen_size = DaoBean.getInventoryLikeCCode("020106", houmen.getText(), ze, width, height).size();
        if (!houmen.getText().equals("不选") && houmen_size != 1) {
            if (houmen_size == 0) {
                showToastOnUi("此高度下没有该后门样式");
            } else {
                showToastOnUi("后门数据有错误，请联系管理员");
            }
            return false;
        }
        //确定蓬杆
        if (DaoBean.getInventoryLikeCCode("1502", penggan.getText(), ze, width, height).size() != 1 && !penggan.getText().equals("不选")) {
            showToastOnUi("蓬杆数据有错误，请联系管理员");
            return false;
        }
        //确定小框
        if (DaoBean.getInventoryLikeCCode("1507", xiaokuang.getText(), ze, width, height).size() != 1 && !xiaokuang.getText().equals("不选")) {
            showToastOnUi("小框数据有错误，请联系管理员");
            return false;
        }
        //确定左工具箱
        if (DaoBean.getInventoryLikeCCode("1503", gjxLeft.getText(), ze, width, height).size() != 1 && !gjxLeft.getText().equals("不选")) {
            showToastOnUi("左工具箱数据有错误，请联系管理员");
            return false;
        }
        //确定右工具箱
        if (DaoBean.getInventoryLikeCCode("1504", gjxRight.getText(), ze, width, height).size() != 1 && !gjxRight.getText().equals("不选")) {
            showToastOnUi("右工具箱数据有错误，请联系管理员");
            return false;
        }
        //ABS
        if (DaoBean.getInventoryLikeCCode("0411", abs.getText(), ze, width, height).size() != 1 && !abs.getText().equals("不选")) {
            showToastOnUi("ABS数据有错误，请联系管理员");
            return false;
        }
        //确定钢圈
        if (DaoBean.getInventoryLikeCCode("0405", gangquan.getText(), ze, width, height).size() != 1 && !gangquan.getText().equals("不选")) {
            showToastOnUi("钢圈数据有错误，请联系管理员");
            return false;
        }
        //确定轮胎
        if (DaoBean.getInventoryLikeCCode("0404", luntai.getText(), ze, width, height).size() != 1 && !luntai.getText().equals("不选")) {
            showToastOnUi("轮胎数据有错误，请联系管理员");
            return false;
        }
        //确定车轴
        if (DaoBean.getInventoryLikeCCode("1513", chezhou.getText(), chezhouMark.getText(), width, height).size() != 1 && !chezhou.getText().equals("不选") && !chezhouMark.getText().equals("自备")) {
            showToastOnUi("车轴数据有错误，请联系管理员");
            return false;
        }
        //确定底盘
        if (DaoBean.getInventoryLikeCCode("1511", celanban.getText(), ze_text, width, height).size() != 1 && !celanban.getText().equals("不选")) {
            showToastOnUi("底盘数据有错误，请联系管理员");
            return false;
        }

        List<Inventory> lmjInventorys2 = DaoBean.getInventoryLikeCCode("020104", hit.getText(), ze, width, height);
        if (!height.equals("无栏板")&&(lmjInventorys2 == null || lmjInventorys2.size() != 1)) {
            showToastOnUi("龙门架数据有错误，请联系管理员");
            return false;
        }
        //确定站柱
        if (DaoBean.getInventoryLikeCCode("020107", celanban.getText(), null, width, height).size() != 1 && !celanban.getText().equals("不选")) {
            showToastOnUi("站柱数据有错误，请联系管理员");
            return false;
        }
        //确定上花栏
        if (DaoBean.getInventoryLikeCCode("1524", cantrail.getText(), null, null, null).size() != 1 && !cantrail.getText().equals("不选") && !cantrail.getText().equals("0") && !cantrail.getText().equals("")) {
            showToastOnUi("上花栏有错误，请联系管理员");
            return false;
        }
        //是否新车选用底盘
        if (isNew.isChecked()) {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.newCar), null, null, null);
        } else {
            DaoBean.getInventoryLikeCCode("1506", this.getResources().getString(R.string.zhihuan), null, null, null);
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
                .append("-").append(mDay < 10 ? "0" + mDay : mDay)
                .toString();
        applyDate.setText(expect_time);
    }

    /**
     * 提交合同返回
     */
    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractInputActivity.this, e.getLocalizedMessage()+"......"+e.getCause(), Toast.LENGTH_SHORT).show();
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

    /**
     * 大小写转化监听
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

    /**
     * 是否新车按钮监听
     */
    private CompoundButton.OnCheckedChangeListener isNewCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                huangyouzuiLayout.setVisibility(View.GONE);
                outlinesizelayout.setVisibility(View.GONE);
                houzhuanLayout.setVisibility(View.VISIBLE);
                oldPriceLayout.setVisibility(View.GONE);
                others.setVisibility(View.VISIBLE);
                banhuangMark.setVisibility(View.GONE);
                chezhouMark.setVisibility(View.GONE);
                banhuangMark.setText("厂配");
                chezhouMark.setText("厂配");

            } else {
                outlinesizelayout.setVisibility(View.VISIBLE);
                huangyouzuiLayout.setVisibility(View.VISIBLE);
                houzhuanLayout.setVisibility(View.GONE);
                oldPriceLayout.setVisibility(View.VISIBLE);
                others.setVisibility(View.GONE);
                banhuangMark.setVisibility(View.VISIBLE);
                chezhouMark.setVisibility(View.VISIBLE);
            }
        }
    };


    /**
     * 监听数量变化
     */
    public void monitorEditNum() {
        int wchengCounts = getEditNum(wchengNum);
        int jsqCounts = getEditNum(jinshengqiNum);
        int gqCounts = getEditNum(gangquanNum);
        int ltCounts = getEditNum(luntaiNum);
        String celanbanString = celanban.getText();
        if (!celanbanString.equals("不选")) {
            if (wcheng1.getText().contains("W称")) {
                if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts - 5);
                } else if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts - 7);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts - 9);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts - 12);
                }
            } else if (wcheng1.getText().contains("槽钢")) {
                if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts);
                } else if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts - 1);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts - 1);
                }
            } else if (wcheng1.getText().contains("工字")) {
                if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts - 3);
                } else if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts - 3);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                    DaoBean.updateCounts("0308", wchengCounts - 7);
                } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                    DaoBean.updateCounts("0308", wchengCounts - 7);
                }
            }
        } else {
            if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                DaoBean.updateCounts("0308", wchengCounts);
            } else if (carStyle.getText().contains(zText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                DaoBean.updateCounts("0308", wchengCounts);
            } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.duikai))) {
                DaoBean.updateCounts("0308", wchengCounts - 1);
            } else if (carStyle.getText().contains(eText) && celanbanString.contains(ContractInputActivity.this.getResources().getString(R.string.xaida))) {
                DaoBean.updateCounts("0308", wchengCounts - 1);
            }
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
           showToastOnUi(judgeEdit());
            return false;
        }
        if (!dealWithData()) {
            return false;
        }
        monitorEditNum();
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
            basic_info.put("cantrail", cantrail.getText());
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
            basic_info.put("isSkeleton", 0);//内勤审核标志
            basic_info.put("cDCName", area.getText());
            basic_info.put("orderNumber", orderNumberEdit.getText());
            basic_info.put("butterMouthHeight", huangyouzui.getText().toString());
            basic_info.put("outlinesize", outlinesize.getText().toString());
            basic_info.put("oldPrice", oldPrice.getText().toString());
            basic_info.put("banhuangMark", banhuangMark.getText());
            basic_info.put("chezhouMark", chezhouMark.getText());
            String carriageString = carriage.getText().toString();
            basic_info.put("carriage", TextUtils.isEmpty(carriageString) ? 0 : carriageString);
            String discountFeeString  = discountFee.getText().toString();
            basic_info.put("discountFee",TextUtils.isEmpty(discountFeeString)?0:discountFeeString);
            if (carStyle.getText().contains("直")) {
                basic_info.put("classCode", "0102");

            } else {
                basic_info.put("classCode", "0101");
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
                basic_info.put("payedmoney", 0);

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

    /**
     * 重写点击返回按钮
     * @param event
     * @return
     */
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
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setMessage(this.getResources().getString(R.string.isExit));
        exitDialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        exitDialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContractInputActivity.this.finish();
            }
        });
        exitDialog.show();

    }

    private class EnableNumOfDaysResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractInputActivity.this, ContractInputActivity.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            ProNumData.clear();
            if (response.getCode() == 0) {
                for (ProNumBean proNumBean : response.getData().proNum) {
                    if ((proNumBean.getFirst_flag() >= proNumBean.getFirst_num())
                            && (proNumBean.getUrgent_flag() >= proNumBean.getUrgent_num())
                            && (proNumBean.getNormal_flag() >= proNumBean.getNormal_num())
                            && (proNumBean.getSkeleton_flag() >= proNumBean.getSkeleton_num())) {
                        continue;
                    }
                    if (proNumBean.getEnable_flag() == 0) {
                        continue;
                    }
                    ProNumData.add(proNumBean);
                }
                ProNumAdapter proNumAdapter = new ProNumAdapter(ContractInputActivity.this, ProNumData);
                proNumLv.setAdapter(proNumAdapter);
                queryNumLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(ContractInputActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 重新onResume方法，实现保存手写签名后，显示在签名的ImageView上
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sps = getSharedPreferences("image_info",
                MODE_PRIVATE);
        image_path = sps.getString("image_path", "");
        signaturefile = new File(image_path);
        if (!TextUtils.isEmpty(image_path)) {
            InputStream is;
            bitmap = null;
            try {
                is = new FileInputStream(image_path);
                bitmap = BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                return;
            }
            if (bitmap != null) {
                signPic.setImageBitmap(bitmap);
            } else {
                System.out.println("bitmap is null");
            }
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //选择照片返回结果
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                    if (img_uri.size() > 0) {
                        img_uri.remove(img_uri.size() - 1);
                    }

                    for (int i = 0; i < paths.size(); i++) {
                        if (img_uri.size() >= 9) {
                            Toast.makeText(this, "最多可以上传9张附件!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        img_uri.add(new UploadGoodsBean(paths.get(i), false));
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setOriginalPath(paths.get(i));
                        photoModel.setChecked(true);
                        single_photos.add(photoModel);
                        //上传参数
                    }

                    if (img_uri.size() < 9) {
                        img_uri.add(null);
                    }
                    gridImgsAdapter.notifyDataSetChanged();
                }
                break;
        }


    }

    private void download(String fileUrl, String filePath, String imageName, int type) {


        //Target
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File dcimFile = ImageFactory.getDCIMFile(ContractInputActivity.this, imageName);
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (type == 0) {
                    img_uri.add(new UploadGoodsBean(dcimFile.getPath(), false));
                    single_photos.add(new PhotoModel(dcimFile.getPath(), true));
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("image_info",
                            MODE_PRIVATE).edit();
                    editor.putString("image_path", dcimFile.getPath());
                    editor.apply();
                    signaturefile = new File(dcimFile.getPath());
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (type == 0) {
                    img_uri.add(new UploadGoodsBean("drawable://" + R.drawable.more_item_unpress, false));
                    single_photos.add(new PhotoModel("drawable://" + R.drawable.more_item_unpress, true));
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {


            }
        };

        //Picasso下载
        fileUrl = CommonData.loadImageFile + "?fileName=" + fileUrl;
        Picasso.with(this).load(fileUrl).into(target);
        if (type == 1) {
            Picasso.with(this).load(fileUrl).into(signPic);
        }

    }

    /**
     * 图片附件展示适配器
     */
    class GridImgAdapter extends BaseAdapter implements ListAdapter {
        @Override
        public int getCount() {
            return img_uri.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.activity_addstory_img_item, null);
            ViewHolder holder;

            if (convertView != null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ContractInputActivity.this).inflate(R.layout.activity_addstory_img_item, null);
                convertView.setTag(holder);
                holder.add_IB = (ImageView) convertView.findViewById(R.id.add_IB);
                holder.delete_IV = (ImageView) convertView.findViewById(R.id.delete_IV);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            AbsListView.LayoutParams param = new AbsListView.LayoutParams(screen_widthOffset, screen_widthOffset);
            convertView.setLayoutParams(param);
            if (img_uri.get(position) == null) {
                holder.delete_IV.setVisibility(View.GONE);

                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.iv_add_the_pic, holder.add_IB);

                holder.add_IB.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(ContractInputActivity.this, PhotoSelectorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("limit", 9 - (img_uri.size() - 1));
                        startActivityForResult(intent, 0);
                    }
                });

            } else {
                ImageLoader.getInstance().displayImage("file://" + img_uri.get(position).getUrl(), holder.add_IB);

                holder.delete_IV.setOnClickListener(new View.OnClickListener() {
                    private boolean is_addNull;

                    @Override
                    public void onClick(View arg0) {
                        is_addNull = true;
                        String img_url = img_uri.remove(position).getUrl();
                        single_photos.remove(position);
                        for (int i = 0; i < img_uri.size(); i++) {
                            if (img_uri.get(i) == null) {
                                is_addNull = false;
                                continue;
                            }
                        }
                        if (is_addNull) {
                            img_uri.add(null);
                        }

                        FileUtils.DeleteFolder(img_url);//删除在emulate/0文件夹生成的图片

                        gridImgsAdapter.notifyDataSetChanged();
                    }
                });

                holder.add_IB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos", (Serializable) single_photos);
                        bundle.putInt("position", position);
                        bundle.putBoolean("isSave", false);
                        CommonUtils.launchActivity(ContractInputActivity.this, PhotoPreviewActivity.class, bundle);
                    }
                });

            }
            return convertView;
        }

        class ViewHolder {
            ImageView add_IB;
            ImageView delete_IV;
        }
    }

    /**
     * 初始加载自由选项的参数，比如车长，车宽，提车方式（自备，厂配）
     */
    private class InitDataResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractInputActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<Option> initList = response.getData().optionItems;
                for (Option option : initList) {
                    switch (option.getOption_code()) {
                        case 1:
                            list_len = new ArrayList<>();
                            addList(option.getOptionItems(), list_len);
                            break;
                        case 2:
                            list_wid = new ArrayList<>();
                            addList(option.getOptionItems(), list_wid);
                            break;
                        case 3:
                            list_hgt = new ArrayList<>();
                            addList(option.getOptionItems(), list_hgt);
                            break;
                        case 4:
                            list_qyz = new ArrayList<>();
                            addList(option.getOptionItems(), list_qyz);
                            break;
                        case 5:
                            list_banhuangMark = new ArrayList<>();
                            addList(option.getOptionItems(), list_banhuangMark);
                            break;
                        case 6:
                            list_obtaintype = new ArrayList<>();
                            addList(option.getOptionItems(), list_obtaintype);
                            break;
                        case 7:
                            list_pati = new ArrayList<>();
                            addList(option.getOptionItems(), list_pati);
                            break;
                        case 8:
                            list_beitaizhijia = new ArrayList<>();
                            addList(option.getOptionItems(), list_beitaizhijia);
                            break;
                        case 9:
                            list_fsc = new ArrayList<>();
                            addList(option.getOptionItems(), list_fsc);
                            break;
                        case 10:
                            list_model = new ArrayList<>();
                            addList(option.getOptionItems(), list_model);
                            break;
                        case 11:
                            list_btsjq = new ArrayList<>();
                            addList(option.getOptionItems(), list_btsjq);
                            break;
                        case 15:
                            list_powerType = new ArrayList<>();
                            addList(option.getOptionItems(), list_powerType);
                            break;

                    }

                }
                initFreeSpinner();
            } else {

            }

        }

        private void addList(List<OptionItem> optionItems, List<String> freeSpinnerList) {
            for (OptionItem optionItem : optionItems) {
                freeSpinnerList.add(optionItem.getItem_name());
            }
        }
    }
    void showToastOnUi(String msg){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Toast.makeText(ContractInputActivity.this,msg , Toast.LENGTH_SHORT).show();
            }

        });
 }

}
