package fajieyefu.com.luoxiang.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.FollowClueAdapter;
import fajieyefu.com.luoxiang.bean.ClueBean;
import fajieyefu.com.luoxiang.bean.ClueFollowBean;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.Region;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.LinearLayoutForListView;
import fajieyefu.com.luoxiang.layout.MySpinner;
import fajieyefu.com.luoxiang.layout.MySpinnerForRegion;
import fajieyefu.com.luoxiang.layout.RingUpDialog;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-10-19.
 */
public class ClueInfoDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.base_content)
    TextView baseContent;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.clientPhone)
    TextView clientPhone;
    @BindView(R.id.l2)
    LinearLayout l2;
    @BindView(R.id.clientArea)
    TextView clientArea;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.ll5)
    LinearLayout ll5;
    @BindView(R.id.create_time)
    TextView createTime;
    @BindView(R.id.ll7)
    LinearLayout ll7;
    @BindView(R.id.clue_title)
    TextView clueTitle;
    @BindView(R.id.ll8)
    LinearLayout ll8;
    @BindView(R.id.L2)
    LinearLayout L2;
    @BindView(R.id.follow_info)
    TextView followInfo;

    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.addFollow)
    Button addFollow;
    @BindView(R.id.clientName)
    TextView clientName;
    @BindView(R.id.client_level_name)
    TextView clientLevelName;
    @BindView(R.id.car_type_name)
    TextView carTypeName;
    @BindView(R.id.clue_content)
    TextView clueContent;
    @BindView(R.id.shade_layout_edit)
    LinearLayout shadeLayoutEdit;

    @BindView(R.id.clue_title_edit)
    EditText clueTitleEdit;
    @BindView(R.id.clue_content_edit)
    EditText clueContentEdit;
    @BindView(R.id.clientName_edit)
    EditText clientNameEdit;
    @BindView(R.id.clientPhone_edit)
    EditText clientPhoneEdit;
    @BindView(R.id.clientLevel_edit)
    MySpinner clientLevelEdit;
    @BindView(R.id.car_type_edit)
    MySpinner carTypeEdit;
    @BindView(R.id.commitEditClueInfo)
    Button commitEditClueInfo;
    @BindView(R.id.editLayout)
    LinearLayout editLayout;
    @BindView(R.id.follow_content)
    EditText followContent;
    @BindView(R.id.commitFollowClueInfo)
    Button commitFollowClueInfo;
    @BindView(R.id.editFollowLayout)
    LinearLayout editFollowLayout;
    @BindView(R.id.editRelayout)
    RelativeLayout editRelayout;
    @BindView(R.id.editFollowRelLayout)
    RelativeLayout editFollowRelLayout;
    @BindView(R.id.area_edit)
    MySpinner areaEdit;
    @BindView(R.id.cDCName)
    TextView cDCName;
    @BindView(R.id.shade_layout_follow)
    LinearLayout shadeLayoutFollow;
    @BindView(R.id.follow_lv)
    LinearLayoutForListView followLv;
    @BindView(R.id.province)
    MySpinnerForRegion province;
    @BindView(R.id.city)
    MySpinnerForRegion city;
    @BindView(R.id.country)
    MySpinnerForRegion country;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.bottom_layout_2)
    LinearLayout bottomLayout2;
    @BindView(R.id.doFollow)
    Button doFollow;
    @BindView(R.id.clientLevel_edit_follow)
    MySpinner clientLevelEditFollow;
    @BindView(R.id.endBrandType_follow)
    MySpinner endBrandTypeFollow;
    @BindView(R.id.phone)
    ImageView phone;
    private String clue_code;
    private int no_edit_layout_flag;
    private UserInfo userInfo;
    private ClueBean clueBean = new ClueBean();
    private List<ClueFollowBean> followList = new ArrayList();
    private FollowClueAdapter followClueAdapter;
    private ToolUtil toolUtil;
    private int regionFlag = 0;
    private List<Region> provinceList = new ArrayList<>();
    private List<Region> cityList = new ArrayList<>();
    private List<Region> countryList = new ArrayList<>();
    private String[] permissions = {Manifest.permission.CALL_PHONE};
    private AlertDialog dialog;
    private String toastMsg;
    private TextView province_text;
    private TextView city_text;
    private TextView country_text;
    private int pageType = 1;
    private Button cancelFollow;
    private Dialog cancelFollowDialog;
    private int screenWidth;
    private int screenHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clue_info_detail);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initData() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("username", userInfo.getUsername());
            jsonObject.put("password", userInfo.getPassword());
            jsonObject.put("clue_code", clue_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.clueInfoDetail)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new CallBack());
    }

    private void initView() {
        int[] hm = new ToolUtil().getScreenWH(this);
        screenWidth = hm[0];
        screenHeight = hm[1];
        toolUtil = new ToolUtil();
        title.setTitleText("客户资源信息");
        cancelFollow = (Button) title.findViewById(R.id.cancelFollow);
        cancelFollow.setOnClickListener(this);
        Intent intent = getIntent();
        clue_code = intent.getStringExtra("clue_code");
        no_edit_layout_flag = intent.getIntExtra("no_edit_layout_flag", 0);
        phone.setOnClickListener(this);
        clientPhone.setOnClickListener(this);
        userInfo = DaoBean.getUseInfoById(1);

        screenWidth = hm[0];
        screenHeight = hm[1];
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) editLayout.getLayoutParams();
        linearParams.width = screenWidth / 10 * 9;
        linearParams.height = screenHeight / 4 * 3;// 控件的高强制设成20
        editLayout.setLayoutParams(linearParams);


        RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) editFollowLayout.getLayoutParams();
        linearParams2.width = screenWidth / 10 * 9;
        linearParams2.height = screenHeight / 2;
        editFollowLayout.setLayoutParams(linearParams2);

        cancelFollow.setOnClickListener(this);
        shadeLayoutFollow.setOnClickListener(this);
        shadeLayoutEdit.setOnClickListener(this);
        province_text = (TextView) province.findViewById(R.id.spinner_text);
        city_text = (TextView) city.findViewById(R.id.spinner_text);
        country_text = (TextView) country.findViewById(R.id.spinner_text);
        province_text.addTextChangedListener(new ProvinceTextChangeListener());
        city_text.addTextChangedListener(new CityTextChangeListener());


    }

    @OnClick({R.id.edit, R.id.addFollow, R.id.commitFollowClueInfo, R.id.doFollow, R.id.commitEditClueInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit:
                pageType = 1;
                initEditData();
                break;
            case R.id.addFollow:
                pageType = 2;
                initEditData();
                break;

            case R.id.commitEditClueInfo:
                commitEditClueInfo();
                break;
            case R.id.commitFollowClueInfo:
                commitFollow();
                break;
            case R.id.doFollow:
                startFollow();
                break;
        }
    }

    private void startFollow() {
        JSONObject startFollowJson = new JSONObject();
        try {
            startFollowJson.put("username", userInfo.getUsername());
            startFollowJson.put("password", userInfo.getPassword());
            startFollowJson.put("clue_code", clue_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.startFollowClue)
                .content(startFollowJson.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new startFollowCallBack());
    }

    private void commitEditClueInfo() {
        if (!judegeEditInput()) {
            Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject commitEditJson = new JSONObject();
        try {
            commitEditJson.put("username", userInfo.getUsername());
            commitEditJson.put("password", userInfo.getPassword());
            commitEditJson.put("clue_code", clue_code);
            commitEditJson.put("clue_content", clueContentEdit.getText().toString());
            commitEditJson.put("clue_title", clueTitleEdit.getText().toString());
            commitEditJson.put("clientName", clientNameEdit.getText().toString());
            commitEditJson.put("clientPhone", clientPhoneEdit.getText().toString());
            commitEditJson.put("client_level_code", clientLevelEdit.getSelected_code());
            commitEditJson.put("car_type_code", carTypeEdit.getSelected_code());
            commitEditJson.put("cDCCode", areaEdit.getSelected_code());
            commitEditJson.put("province", province.getSelected_name());
            commitEditJson.put("province_code", province.getSelected_code());
            commitEditJson.put("city", city.getSelected_name());
            commitEditJson.put("city_code", city.getSelected_code());
            commitEditJson.put("country", country.getSelected_name());
            commitEditJson.put("country_code", country.getSelected_code());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.editClueInfo)
                .content(commitEditJson.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new EditInfoCallBack());

    }

    private boolean judegeEditInput() {

        if (TextUtils.isEmpty(clientPhoneEdit.getText().toString())) {
            toastMsg = "请输入客户电话!";
            return false;
        }
        if (TextUtils.isEmpty(carTypeEdit.getSelected_name())) {
            toastMsg = "请选择意向类型!";
            return false;
        }
        if (TextUtils.isEmpty(areaEdit.getSelected_name())) {
            toastMsg = "请选择所属大区!";
            return false;
        }
        if (TextUtils.isEmpty(province.getSelected_name())) {
            toastMsg = "请选择客户所属省份!";
            return false;
        }
        if (TextUtils.isEmpty(city.getSelected_name())) {
            toastMsg = "请选择客户所属市区!";
            return false;
        }
        return true;
    }

    private void commitFollow() {
        if (TextUtils.isEmpty(followContent.getText().toString())) {
            Toast.makeText(this, "请填写跟进信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject followContentJSON = new JSONObject();
        try {
            followContentJSON.put("username", userInfo.getUsername());
            followContentJSON.put("password", userInfo.getPassword());
            followContentJSON.put("clue_code", clue_code);
            followContentJSON.put("follow_content", followContent.getText().toString());
            followContentJSON.put("client_level_code", clientLevelEditFollow.getSelected_code());
            followContentJSON.put("brand_code", endBrandTypeFollow.getSelected_code());
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.addClueFollow)
                .content(followContentJSON.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new CallBack());

    }


    private void initEditData() {
        JSONObject content1 = new JSONObject();
        try {
            if (DaoBean.getRegionByParentCode("0").size() != 0) {
                regionFlag = 1;
            }
            content1.put("regionFlag", regionFlag);
            content1.put("username", userInfo.getUsername());
            content1.put("password", userInfo.getPassword());
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.loadInitClueInfo)
                .content(content1.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new loadInitClueInfoCallBack());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shade_layout_edit:
                editRelayout.setVisibility(View.GONE);
                break;
            case R.id.shade_layout_follow:
                editFollowRelLayout.setVisibility(View.GONE);
                break;
            case R.id.phone: case R.id.clientPhone:
                ringUp();
                break;
            case R.id.cancelFollow:
                cancelFollowDialog();
                break;
        }
    }

    private void cancelFollowDialog() {
        cancelFollowDialog  =new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.cancel_follow_dialog,null);
        cancelFollowDialog.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cancelFollowDialog.show();
        Button confirm = (Button) view.findViewById(R.id.confirm);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        EditText cancelReason = (EditText) view.findViewById(R.id.content);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (cancelFollowDialog!=null && cancelFollowDialog.isShowing()){
                  cancelFollowDialog.dismiss();
              }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cancelReason.getText().toString())){
                    Toast.makeText(ClueInfoDetailActivity.this, "请填写报错原因！", Toast.LENGTH_SHORT).show();
                    return;
                }
                cancelFollow(cancelReason.getText().toString());
            }
        });

    }

    private void cancelFollow(String cancelContent) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("username", userInfo.getUsername());
            jsonObject.put("password", userInfo.getPassword());
            jsonObject.put("clue_code", clue_code);
            jsonObject.put("error_report_reason",cancelContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.cancelFollow)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new cancelFollowCallBack());
    }

    private void ringUp() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + clueBean.getClientPhone()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(ClueInfoDetailActivity.this, Manifest.permission.CALL_PHONE);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            } else {
                ClueInfoDetailActivity.this.startActivity(intent);
            }
        } else {
            ClueInfoDetailActivity.this.startActivity(intent);
        }
    }


    private class CallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ClueInfoDetailActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                editFollowRelLayout.setVisibility(View.GONE);
                clueBean = response.getData().clueInfoDetail;
                if (clueBean != null) {
                    setFileText(clueBean);
                    removeListViewChild(followLv);
                    followList = response.getData().clueFollow;
                    followClueAdapter = new FollowClueAdapter(ClueInfoDetailActivity.this, followList);
                    followLv.setAdapter(followClueAdapter);
                    followClueAdapter.notifyDataSetChanged();
                    followClueAdapter.notifyDataSetInvalidated();
                }


            } else {

                Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void removeListViewChild(LinearLayoutForListView followLv) {
        int counts = followLv.getChildCount();
        if (counts > 0) {
            followLv.removeAllViews();
        }
    }

    private void setFileText(ClueBean clueBean) {
        if (no_edit_layout_flag == 1 && clueBean.getFollow_man_name() != null) {
            bottomLayout.setVisibility(View.GONE);
            bottomLayout2.setVisibility(View.GONE);
        } else if (no_edit_layout_flag == 1 && clueBean.getFollow_man_name() == null) {
            bottomLayout2.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        } else if (no_edit_layout_flag == 0) {
            bottomLayout2.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
            cancelFollow.setVisibility(View.VISIBLE);
        }
        setTextView(clientName, clueBean.getClientName());
        setTextView(clientPhone, clueBean.getClientPhone());
        String clientAreaStr = clueBean.getProvince() == null ? "" : clueBean.getProvince();
        clientAreaStr += clueBean.getCity() == null ? "" : clueBean.getCity();
        clientAreaStr += clueBean.getCountry() == null ? "" : clueBean.getCountry();
        setTextView(clientArea, clientAreaStr);
        setTextView(clientName, clueBean.getClientName());
        setTextView(carTypeName, clueBean.getCar_type_name());
        setTextView(createTime, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Long.parseLong(clueBean.getCreate_time())));
        setTextView(clueTitle, clueBean.getClue_title());
        setTextView(clueContent, clueBean.getClue_content());
        setTextView(clientLevelName, clueBean.getClient_level_name());
        setTextView(cDCName, clueBean.getcDCName());
    }

    private void setTextView(TextView textView, String text) {
        if (text == null) {
            textView.setText("");
        } else {
            textView.setText(text);
        }
    }

    private class loadInitClueInfoCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ClueInfoDetailActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<ObtainBean> carBrandList = response.getData().initClueInfo.carBrand;
                List<ObtainBean> carTypeList = response.getData().initClueInfo.car_type;
                List<ObtainBean> clientLevelList = response.getData().initClueInfo.client_level;
                List<ObtainBean> areaList = response.getData().initClueInfo.area;
                List<Region> regions = response.getData().initClueInfo.regions;

                clientNameEdit.setText(clueBean.getClientName());
                clientPhoneEdit.setText(clueBean.getClientPhone());
                clueTitleEdit.setText(clueBean.getClue_title());
                clueContentEdit.setText(clueBean.getClue_content());
                clientLevelEdit.setSpinnerText(clueBean.getClient_level_name());
                carTypeEdit.setSpinnerText(clueBean.getCar_type_name());
                areaEdit.setSpinnerText(clueBean.getcDCName());
                endBrandTypeFollow.setSpinnerText(clueBean.getBrand_name());
                clientLevelEditFollow.setSpinnerText(clueBean.getClient_level_name());

                endBrandTypeFollow.setData(carBrandList);
                clientLevelEditFollow.setData(clientLevelList);
                carTypeEdit.setData(carTypeList);
                clientLevelEdit.setData(clientLevelList);
                areaEdit.setData(areaList);
                if (pageType == 1) {
                    editRelayout.setVisibility(View.VISIBLE);
                } else {
                    editFollowRelLayout.setVisibility(View.VISIBLE);
                }


                if (regions != null && regionFlag == 0) {
                    DaoBean.deleteRegionAll();
                    DaoBean.insertRegionList(regions);
                }
                initRegion();
                if (!TextUtils.isEmpty(clueBean.getProvince_code()) && !TextUtils.isEmpty(clueBean.getProvince())) {
                    province.setSpinnerCode(clueBean.getProvince_code());
                    province.setSpinnerText(clueBean.getProvince());
                }
                if (!TextUtils.isEmpty(clueBean.getCity()) && !TextUtils.isEmpty(clueBean.getCity_code())) {
                    city.setSpinnerCode(clueBean.getCity_code());
                    city.setSpinnerText(clueBean.getCity());
                }
                if (!TextUtils.isEmpty(clueBean.getCountry()) && !TextUtils.isEmpty(clueBean.getCountry_code())) {
                    country.setSpinnerCode(clueBean.getCountry_code());
                    country.setSpinnerText(clueBean.getCountry());
                }

            } else {
                Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initRegion() {
        provinceList = DaoBean.getRegionByParentCode("0");
        province.setData(provinceList);
    }

    private class EditInfoCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ClueInfoDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                Toast.makeText(ClueInfoDetailActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                initData();
            } else {
                Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }

        }
    }

    private class startFollowCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ClueInfoDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                bottomLayout2.setVisibility(View.GONE);
                Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                RingUpDialog ringUpdialog = new RingUpDialog.Builder(ClueInfoDetailActivity.this)
                        .setPhone(clueBean.getClientPhone())
                        .setTitle("请速速联系客户")
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + clueBean.getClientPhone()));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    // 检查该权限是否已经获取
                                    int i = ContextCompat.checkSelfPermission(ClueInfoDetailActivity.this, Manifest.permission.CALL_PHONE);
                                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                                    if (i != PackageManager.PERMISSION_GRANTED) {
                                        // 如果没有授予该权限，就去提示用户请求
                                        showDialogTipUserRequestPermission();
                                    } else {
                                        ClueInfoDetailActivity.this.startActivity(intent);
                                    }
                                } else {
                                    ClueInfoDetailActivity.this.startActivity(intent);
                                }
                            }
                        })
                        .create();
                ringUpdialog.show();
            } else {
                Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("获取通话权限不可用")
                .setMessage("需要获取通话权限\n否则，您将无法正常在应用中调起通话")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许锣响汽车使用通话权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private class ProvinceTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String province_code = province.getSelected_code();
            List<Region> cityList = DaoBean.getRegionByParentCode(province_code);
            city.setData(cityList);
        }
    }

    private class CityTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String city_code = city.getSelected_code();
            List<Region> countryList = DaoBean.getRegionByParentCode(city_code);
            country.setData(countryList);
        }
    }

    private class cancelFollowCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ClueInfoDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
                if(response.getCode()==0){
                    Toast.makeText(ClueInfoDetailActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    ClueInfoDetailActivity.this.finish();
                }else{
                    Toast.makeText(ClueInfoDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

                }
        }
    }
}
