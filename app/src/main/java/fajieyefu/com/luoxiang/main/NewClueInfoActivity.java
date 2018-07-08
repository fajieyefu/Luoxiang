package fajieyefu.com.luoxiang.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.Region;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinner;
import fajieyefu.com.luoxiang.layout.MySpinnerForRegion;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-10-23.
 */

public class NewClueInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
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
    @BindView(R.id.area_edit)
    MySpinner areaEdit;
    @BindView(R.id.commitEditClueInfo)
    Button commitEditClueInfo;
    @BindView(R.id.province)
    MySpinnerForRegion province;
    @BindView(R.id.city)
    MySpinnerForRegion city;
    @BindView(R.id.country)
    MySpinnerForRegion country;
    private UserInfo userInfo;
    private ToolUtil toolUtil;
    private int regionFlag = 0;
    private List<Region> provinceList = new ArrayList<>();
    private List<Region> cityList= new ArrayList<>();
    private List<Region> countryList= new ArrayList<>();
    private TextView province_text;
    private TextView city_text;
    private TextView country_text;
    private String toastMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_clue_info_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        title.setTitleText("新增电话信息");
        userInfo = DaoBean.getUseInfoById(1);
        toolUtil = new ToolUtil();
        commitEditClueInfo.setOnClickListener(this);
        province_text= (TextView) province.findViewById(R.id.spinner_text);
        city_text= (TextView) city.findViewById(R.id.spinner_text);
        country_text= (TextView) country.findViewById(R.id.spinner_text);
        province_text.addTextChangedListener(new ProvinceTextChangeListener());
        city_text.addTextChangedListener(new CityTextChangeListener());


    }

    private void initData() {

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
            case R.id.commitEditClueInfo:
                commit();
                break;
        }
    }

    private void commit() {
        if (!judgeEditInput()){
            Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject commitJson = new JSONObject();
        try {
            commitJson.put("username", userInfo.getUsername());
            commitJson.put("password", userInfo.getPassword());
            commitJson.put("clue_title", clueTitleEdit.getText().toString());
            commitJson.put("clue_content", clueContentEdit.getText().toString());
            commitJson.put("clientName", clientNameEdit.getText().toString());
            commitJson.put("clientPhone", clientPhoneEdit.getText().toString());
            commitJson.put("client_level_code", clientLevelEdit.getSelected_code());
            commitJson.put("car_type_code", carTypeEdit.getSelected_code());
            commitJson.put("cDCCode", areaEdit.getSelected_code());
            commitJson.put("province_code", province.getSelected_code());
            commitJson.put("province", province.getSelected_name());
            commitJson.put("city_code", city.getSelected_code());
            commitJson.put("city", city.getSelected_name());
            commitJson.put("country_code", country.getSelected_code());
            commitJson.put("country", country.getSelected_name());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.newClueInfo)
                .content(commitJson.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new newClueCallBack());

    }

    private boolean judgeEditInput() {
        if (TextUtils.isEmpty(clientPhoneEdit.getText().toString())){
            toastMsg="请输入客户电话!";
            return false;
        }
        if (TextUtils.isEmpty(carTypeEdit.getSelected_name())){
            toastMsg="请选择意向类型!";
            return false;
        }
        if (TextUtils.isEmpty(areaEdit.getSelected_name())){
            toastMsg="请选择所属大区!";
            return false;
        }
        if (TextUtils.isEmpty(province.getSelected_name())){
            toastMsg="请选择客户所属省份!";
            return false;
        }
        if (TextUtils.isEmpty(city.getSelected_name())){
            toastMsg="请选择客户所属市区!";
            return false;
        }
        return true;
    }

    private class loadInitClueInfoCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(NewClueInfoActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
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

                if (regions != null && regionFlag == 0) {
                    DaoBean.deleteRegionAll();
                    DaoBean.insertRegionList(regions);
                }
                initRegion();
                carTypeEdit.setData(carTypeList);
                clientLevelEdit.setData(clientLevelList);
                areaEdit.setData(areaList);

            } else {
                Toast.makeText(NewClueInfoActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initRegion() {
        provinceList= DaoBean.getRegionByParentCode("0");
        province.setData(provinceList);

    }

    private class newClueCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(NewClueInfoActivity.this, "服务器异常，请重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                Toast.makeText(NewClueInfoActivity.this, "新增成功，奖励好员工勋章一枚！", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(NewClueInfoActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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
            List<Region> cityList=DaoBean.getRegionByParentCode(province_code);
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
            List<Region> countryList=DaoBean.getRegionByParentCode(city_code);
            country.setData(countryList);
        }
    }
}
