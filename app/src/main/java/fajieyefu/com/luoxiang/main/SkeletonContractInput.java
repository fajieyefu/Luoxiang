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
    MySpinnerForFreeInventory unloadingPlatform;
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
    MySpinnerForFree productType;
    @BindView(R.id.airCylinderType)
    MySpinnerForFreeInventory airCylinderType;
    @BindView(R.id.sign_pic)
    ImageView signPic;
    @BindView(R.id.sign)
    Button sign;
    @BindView(R.id.sign_layout)
    LinearLayout signLayout;
    @BindView(R.id.shade_layout)
    LinearLayout shadeLayout;
    @BindView(R.id.proNumLv)
    ListView proNumLv;
    @BindView(R.id.queryNumLayout)
    RelativeLayout queryNumLayout;
    @BindView(R.id.queryLinear)
    LinearLayout queryLinear;
    @BindView(R.id.closeProNum)
    Button closeProNum;
    @BindView(R.id.my_goods_GV)
    MyGridView myGoodsGV;
    @BindView(R.id.file_layout)
    LinearLayout fileLayout;
    @BindView(R.id.otherContractLV)
    LinearLayoutForListView otherContractLV;
    @BindView(R.id.sameTextView)
    TextView sameTextView;
    @BindView(R.id.absMark)
    MySpinnerForFree absMark;
    @BindView(R.id.carriage)
    EditText carriage;
    @BindView(R.id.discountFee)
    EditText discountFee;
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
    private List<String> list_relayValveType = new ArrayList<>();
    private List<String> list_legType = new ArrayList<>();
    private List<String> list_brakeChamberType = new ArrayList<>();
    private List<String> list_powerType = new ArrayList<>();
    private List<String> list_btzj = new ArrayList<>();
    private List<String> list_btsjq = new ArrayList<>();
    private List<String> list_absMark = new ArrayList<>();
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
    private int screenWidth;
    private int screenHeight;
    private List<ProNumBean> ProNumData = new ArrayList<>();
    private String image_path;
    private File signatureFile;
    private Bitmap bitmap;
    private ImageView imageView;
    private List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
    private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();
    private int screen_widthOffset;
    GridImgAdapter gridImgsAdapter;
    private List<ContractBean> sameOrders = new ArrayList<>();
    private EffectContractAdapter effectContractAdapter;
    private JSONArray sameOrdersArray;
    private String commitUrl = CommonData.CommitContract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skeleton_input_layout);
        ButterKnife.bind(this);
        initView();
        initData();
        initFile();
        addTextChangeListener();

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

    private void initFile() {

        //方法一
        Config.ScreenMap = Config.getScreenSize(this, this);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screen_widthOffset = (display.getWidth() - (4 * DbTOPxUtils.dip2px(this, 2))) / 4;

        gridImgsAdapter = new GridImgAdapter();
        myGoodsGV.setAdapter(gridImgsAdapter);
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {

        powerType.setData(list_powerType);
        productType.setData(list_productType);
        carStyle.setData(list_carStyle);
        obtainType.setData(list_obtainType);
        axisCount.setData(list_axisCounts);
        axisCount.setText("2");
        btsjq.setData(list_btsjq);
        btzj.setData(list_btzj);
        absMark.setData(list_absMark);

        //装卸平台
//        list_xiehuo = Arrays.asList(getResources().getStringArray(R.array.xiehuo));
        list_xiehuo = DaoBean.getInventoryNameByCCode("1516");
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
        list_brakeChamberType = DaoBean.getInventoryNameByCCode("1521");
        brakeChamberType.setData(list_brakeChamberType);
        //继动阀
        list_relayValveType = DaoBean.getInventoryNameByCCode("1522");
        relayValve.setData(list_relayValveType);
        //支腿
        list_legType = DaoBean.getInventoryNameByCCode("1523");
        legType.setData(list_legType);


        list_area = new ArrayList<>();
        for (Area area : areaList) {
            list_area.add(area.getcDCName());
        }
        area.setData(list_area);
        area.setText(customer_bean.getcDCName());

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
            qianhuizhuan.setText(json.optString("front_space").trim());
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
            absMark.setText(json.optString("absMark").trim());
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
            applyDate.setText(json.optString("leaveTime").trim().split(" ")[0]);
            obtainType.setText(json.optString("carstyle").trim());
            if (json.optInt("urgent_flag") == 1) {
                urgent.setChecked(true);
            }
            sameTextView.setVisibility(View.VISIBLE);
            effectContractAdapter = new EffectContractAdapter(this, sameOrders);
            otherContractLV.setAdapter(effectContractAdapter);
            commitUrl = CommonData.modifyContract;

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

    @SuppressWarnings("ResourceType")
    private void initView() {
        toolUtil = new ToolUtil();
        zText = this.getResources().getString(R.string.z);
        eText = this.getResources().getString(R.string.e);
        zlText = this.getResources().getString(R.string.zl);
        ejText = this.getResources().getString(R.string.ej);
        title.setTitleText("骨架车合同录入");
        int[] hm = new ToolUtil().getScreenWH(this);
        screenWidth = hm[0];
        screenHeight = hm[1];
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) queryLinear.getLayoutParams();
        linearParams.width = screenWidth / 10 * 9;
        linearParams.height = screenHeight / 3 * 2;// 控件的高强制设成20
        queryLinear.setLayoutParams(linearParams);
        userInfo = DaoBean.getUseInfoById(1);
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
        sameOrders = (List<ContractBean>) intent.getSerializableExtra("sameOrders");
        customer.setText(customer_bean.getName());
        mobile.setText(customer_bean.getcCusHand());
        address.setText(customer_bean.getcCusAddress());
        contactsMan.setText(customer_bean.getcCusLPerson());
        sign.setOnClickListener(this);
        signPic.setOnClickListener(this);
        closeProNum.setOnClickListener(this);
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(SkeletonContractInput.this);
                View view = LayoutInflater.from(SkeletonContractInput.this).inflate(R.layout.more_layout, null);
                TextView commit = (TextView) view.findViewById(R.id.commit);
                Button preview = (Button) view.findViewById(R.id.preview);
                Button queryProNum = (Button) view.findViewById(R.id.queryProNum);
                queryProNum.setVisibility(View.VISIBLE);
                commit.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                preview.setOnClickListener(SkeletonContractInput.this);
                commit.setOnClickListener(SkeletonContractInput.this);
                queryProNum.setOnClickListener(SkeletonContractInput.this);
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
        AlertDialog.Builder comfirmDialog = new AlertDialog.Builder(this);
        comfirmDialog.setMessage(this.getResources().getString(R.string.isExit));
        comfirmDialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        comfirmDialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SkeletonContractInput.this.finish();
            }
        });
        comfirmDialog.show();

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
        switch (v.getId()) {
            case R.id.queryProNum:
                initQueryProNumView();
                break;
            case R.id.commit:
                doConfirm();
                break;
            case R.id.preview:
                Intent intent2 = new Intent(SkeletonContractInput.this, ContractPreviewActivity.class);
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
                Intent intent3 = new Intent(SkeletonContractInput.this, SignatureActivity.class);
                startActivity(intent3);
                break;
            case R.id.sign_pic:
                if (bitmap == null) {
                    Toast.makeText(SkeletonContractInput.this, "没有签名，请添加", Toast.LENGTH_SHORT).show();
                } else {
                    Display display = SkeletonContractInput.this.getWindowManager().getDefaultDisplay();
                    int screenWidth = display.getWidth();
                    Dialog signDialog = new Dialog(this);
                    signDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    imageView = new ImageView(this);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    signDialog.setContentView(imageView, new ViewGroup.LayoutParams(screenWidth / 3 * 2, screenWidth / 3 * 2));
                    signDialog.show();
                }
                break;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }


    }

    private void doConfirm() {
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setMessage(this.getResources().getString(R.string.sureOperate));
        exitDialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        exitDialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toolUtil.showProgressDialog(SkeletonContractInput.this);
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
        exitDialog.show();
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
     * 提交订单
     *
     * @param msg
     */
    private void commitData(String msg) throws Exception {
        config_info = new JSONArray();
        sameOrdersArray = new JSONArray();
        //判断必填项是否填写
        if (!createBaseJsonData()) {
            toolUtil.dismissProgressDialog();
            return;
        }
        userInfo = DaoBean.getUseInfoById(1);

        List<Inventory> inventories = DaoBean.loadInventoryByCurrent();

        JSONObject content = new JSONObject();
        Gson gson = new GsonBuilder().create();

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
        if (!signatureFile.exists()){
            toolUtil.dismissProgressDialog();
            showToastOnUi("签名无效，请重新签名！");
            return;
        }
        signatureMap.put("signature", signatureFile);

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

//        OkHttpUtils.postString()
//                .url(CommonData.CommitContract)
//                .content(content.toString())
//                .mediaType(MediaType.parse("application/json;charset=utf-8"))
//                .build()
//                .execute(new ResponCallBack());

    }

    /**
     * 获取所有basic_info
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
            basic_info.put("front_space", qianhuizhuan.getText());
            basic_info.put("banhuang", banhuang.getText());
            basic_info.put("fender", fender.getText());
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
            basic_info.put("absMark", absMark.getText());
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
            String discountFeeString  = discountFee.getText().toString();
            basic_info.put("discountFee",TextUtils.isEmpty(discountFeeString)?0:discountFeeString);
            basic_info.put("orderNumber", orderNumberEdit.getText());
            basic_info.put("classCode", "0106");
            String carriageString = carriage.getText().toString();
            basic_info.put("carriage", TextUtils.isEmpty(carriageString) ? 0 : carriageString);
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
        if (productType.getText().equals("不选")) {
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
        if (DaoBean.getInventoryLikeCCodeSkeleton("1508", up.getText(), ze_text, productType.getText(), null).size() != 1 && !up.getText().equals("不选")) {
            showToastOnUi("车架（上）有错误，请联系管理员");
            return false;
        }
        //确定车架下
        if (DaoBean.getInventoryLikeCCodeSkeleton("1509", down.getText(), ze_text, productType.getText(), null).size() != 1 && !down.getText().equals("不选")) {
            showToastOnUi("车架（下）有错误，请联系管理员");
            return false;

        }
        //确定车架立
        if (DaoBean.getInventoryLikeCCodeSkeleton("1510", mid.getText(), ze_text, productType.getText(), null).size() != 1 && !mid.getText().equals("不选")) {
            showToastOnUi("车架（立）有错误，请联系管理员");
            return false;
        }

        //确定牵引销
        if (DaoBean.getInventoryLikeCCodeSkeleton("0410", qianyinxiao.getText(), ze, null, null).size() != 1 && !qianyinxiao.getText().equals("不选")) {
            showToastOnUi("牵引销有错误，请联系管理员");
            return false;
        }
        //确定板簧
        if (DaoBean.getInventoryLikeCCodeSkeleton("1514", banhuang.getText(), ze, productType.getText(), axisCount.getText()).size() != 1 && !banhuang.getText().equals("不选")) {
            showToastOnUi("板簧有错误，请联系管理员");
            return false;
        }

        //确定左工具箱
        if (DaoBean.getInventoryLikeCCodeSkeleton("1503", gjxLeft.getText(), ze, null, null).size() != 1 && !gjxLeft.getText().equals("不选")) {
            showToastOnUi("左工具箱数据有错误，请联系管理员");
            return false;
        }
        //确定右工具箱
        if (DaoBean.getInventoryLikeCCodeSkeleton("1504", gjxRight.getText(), ze, null, null).size() != 1 && !gjxRight.getText().equals("不选")) {
            showToastOnUi("右工具箱数据有错误，请联系管理员");
            return false;
        }
        //ABS
        if (DaoBean.getInventoryLikeCCodeSkeleton("0411", abs.getText(), ze, null, null).size() != 1 && !abs.getText().equals("不选")) {
            showToastOnUi("ABS数据有错误，请联系管理员");
            return false;
        }
        //确定钢圈
        if (DaoBean.getInventoryLikeCCodeSkeleton("0405", gangquan.getText(), ze, null, null).size() != 1 && !gangquan.getText().equals("不选")) {
            showToastOnUi("钢圈数据有错误，请联系管理员");
            return false;
        }
        //确定轮胎
        if (DaoBean.getInventoryLikeCCodeSkeleton("0404", luntai.getText(), ze, null, null).size() != 1 && !luntai.getText().equals("不选")) {
            showToastOnUi("轮胎数据有错误，请联系管理员");
            return false;
        }
        //确定车轴
        if (DaoBean.getInventoryLikeCCodeSkeleton("1513", chezhou.getText(), ze, null, null).size() != 1 && !chezhou.getText().equals("不选")) {
            showToastOnUi("车轴数据有错误，请联系管理员");
            return false;
        }
        //确定装卸平台
        if (DaoBean.getInventoryLikeCCodeSkeleton("1516", unloadingPlatform.getText(), ze, null, null).size() != 1 && !unloadingPlatform.getText().equals("不选")) {
            showToastOnUi("装卸平台数据有错误，请联系管理员");
            return false;
        }
        //确定悬挂
        if (DaoBean.getInventoryLikeCCodeSkeleton("1517", suspensionType.getText(), ze_text, productType.getText(), null).size() != 1 && !suspensionType.getText().equals("不选")) {
            showToastOnUi("悬挂数据有错误，请联系管理员");
            return false;
        }
        //确定锁具
        if (DaoBean.getInventoryLikeCCodeSkeleton("1518", lockType.getText(), ze, null, null).size() != 1 && !lockType.getText().equals("不选")) {
            showToastOnUi("锁具数据有错误，请联系管理员");
            return false;
        }
        //确定挡泥板
        if (DaoBean.getInventoryLikeCCodeSkeleton("1519", fender.getText(), ze, null, null).size() != 1 && !fender.getText().equals("不选")) {
            showToastOnUi("挡泥板数据有错误，请联系管理员");
            return false;
        }
        //确定储气筒
        if (DaoBean.getInventoryLikeCCodeSkeleton("1520", airCylinderType.getText(), ze, null, null).size() != 1 && !airCylinderType.getText().equals("不选")) {
            showToastOnUi("储气筒数据有错误，请联系管理员");
            return false;
        }
        //确定制动气室
        if (DaoBean.getInventoryLikeCCodeSkeleton("1521", brakeChamberType.getText(), ze, null, null).size() != 1 && !brakeChamberType.getText().equals("不选")) {
            showToastOnUi("制动气室数据有错误，请联系管理员");
            return false;
        }
        //确定继动阀
        if (DaoBean.getInventoryLikeCCodeSkeleton("1522", relayValve.getText(), ze, null, null).size() != 1 && !relayValve.getText().equals("不选")) {
            showToastOnUi("继动阀数据有错误，请联系管理员");
            return false;
        }
        //确定支腿
        if (DaoBean.getInventoryLikeCCodeSkeleton("1523", legType.getText(), ze, null, null).size() != 1 && !legType.getText().equals("不选")) {
            showToastOnUi("支腿数据有错误，请联系管理员");
            return false;
        }


        //确定底盘
        String tempString = "20";
        if (productType.getText().contains("20")) {
            tempString = "20";
        } else if (productType.getText().contains("30")) {
            tempString = "30";
        } else if (productType.getText().contains("40")) {
            tempString = "40";
        } else if (productType.getText().contains("45")) {
            tempString = "45";
        } else if (productType.getText().contains("48")) {
            tempString = "48";
        }
        if (DaoBean.getInventoryLikeCCodeSkeleton("1511", tempString, ze_text, null, null).size() != 1 && !productType.getText().equals("不选")) {
            showToastOnUi("底盘数据有错误，请联系管理员");
            return false;
        }
        //确定行走机构
        if (DaoBean.getInventoryLikeCCodeSkeleton("1506", tempString, ze_text, axisCount.getText() + "轴", null).size() != 1 && !productType.getText().equals("不选")) {
            showToastOnUi("行走机构数据有错误，请联系管理员");
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
        if (!dealWithData()) {
            return;
        }
        monitorEditNum();
        Dialog caculatorDialog = new Dialog(this);
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
        caculatorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        caculatorDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        caculatorDialog.show();

    }

    //查询出车计划回调
    private class EnableNumOfDaysResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(SkeletonContractInput.this, SkeletonContractInput.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                ProNumData.clear();
                for (ProNumBean proNumBean : response.getData().proNum) {
                    if ((proNumBean.getFirst_flag() >= proNumBean.getFirst_num())
                            && (proNumBean.getUrgent_flag() >= proNumBean.getUrgent_num())
                            && (proNumBean.getNormal_flag() >= proNumBean.getNormal_num())) {
                        continue;
                    }
                    if (proNumBean.getEnable_flag() == 0) {
                        continue;
                    }
                    ProNumData.add(proNumBean);
                }
                ProNumAdapter proNumAdapter = new ProNumAdapter(SkeletonContractInput.this, ProNumData);
                proNumLv.setAdapter(proNumAdapter);
                queryNumLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(SkeletonContractInput.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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
        signatureFile = new File(image_path);
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
            convertView = LayoutInflater.from(SkeletonContractInput.this).inflate(R.layout.activity_addstory_img_item, null);
            ViewHolder holder;

            if (convertView != null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SkeletonContractInput.this).inflate(R.layout.activity_addstory_img_item, null);
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
                        Intent intent = new Intent(SkeletonContractInput.this, PhotoSelectorActivity.class);
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
                        CommonUtils.launchActivity(SkeletonContractInput.this, PhotoPreviewActivity.class, bundle);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                File dcimFile = ImageFactory.getDCIMFile(SkeletonContractInput.this, imageName);
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Toast.makeText(SkeletonContractInput.this, "图片下载至:" + dcimFile, Toast.LENGTH_SHORT).show();
//                FilesInfo fileInfo = new FilesInfo();
//                fileInfo.setAdd_file_path(dcimFile.getPath());
//                list.add(fileInfo);
                if (type == 0) {
                    img_uri.add(new UploadGoodsBean(dcimFile.getPath(), false));
                    single_photos.add(new PhotoModel(dcimFile.getPath(), true));
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("image_info",
                            MODE_PRIVATE).edit();
                    editor.putString("image_path", dcimFile.getPath());
                    editor.apply();
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

    private class InitDataResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(SkeletonContractInput.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<Option> initList = response.getData().optionItems;
                for (Option option : initList) {
                    switch (option.getOption_code()) {
                        case 1:
                            list_powerType = new ArrayList<>();
                            addList(option.getOptionItems(), list_powerType);
                            break;
                        case 6:
                            list_obtainType = new ArrayList<>();
                            addList(option.getOptionItems(), list_obtainType);
                            break;
                        case 8:
                            list_btzj = new ArrayList<>();
                            addList(option.getOptionItems(), list_btzj);
                            break;
                        case 11:
                            list_btsjq = new ArrayList<>();
                            addList(option.getOptionItems(), list_btsjq);
                            break;
                        case 12:
                            list_productType = new ArrayList<>();
                            addList(option.getOptionItems(), list_productType);
                            break;
                        case 13:
                            list_axisCounts = new ArrayList<>();
                            addList(option.getOptionItems(), list_axisCounts);
                            break;
                        case 14:
                            list_carStyle = new ArrayList<>();
                            addList(option.getOptionItems(), list_carStyle);
                            break;
                        case 15:
                            list_powerType = new ArrayList<>();
                            addList(option.getOptionItems(), list_powerType);
                            break;
                        case 16:
                            list_absMark = new ArrayList<>();
                            addList(option.getOptionItems(), list_absMark);
                            break;

                    }

                }
                initSpinner();
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
                Toast.makeText(SkeletonContractInput.this,msg , Toast.LENGTH_SHORT).show();
            }

        });
    }
}
