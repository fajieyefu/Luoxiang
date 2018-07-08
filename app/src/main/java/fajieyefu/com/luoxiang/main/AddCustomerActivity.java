package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
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
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.Area;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.U8HrCt007;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.MySpinnerForU8HrCt007;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-24.
 */

public class AddCustomerActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.customer)
    EditText customer;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.customerAddress)
    EditText customerAddress;
    @BindView(R.id.person)
    EditText person;
    @BindView(R.id.addCustomer)
    Button addCustomer;
    @BindView(R.id.select_area)
    MySpinnerForFree selectArea;
    @BindView(R.id.isQM)
    CheckBox isQM;
    @BindView(R.id.province)
    MySpinnerForU8HrCt007 province;
    @BindView(R.id.city)
    MySpinnerForU8HrCt007 city;
    @BindView(R.id.country)
    MySpinnerForU8HrCt007 country;
    @BindView(R.id.credit_code)
    EditText creditCode;
    @BindView(R.id.t7)
    TextView t7;
    private ToolUtil toolUtil;

    private UserInfo userInfo;
    private String username;
    private String password;
    private String cDCCode;
    private List<Area> areaList = new ArrayList<>();
    private List<U8HrCt007> u8HrCt007List = new ArrayList<>();
    private ArrayList<String> list_area = new ArrayList<>();
    private ArrayList<String> list_u8HrCt007 = new ArrayList<>();
    private List<U8HrCt007> provinceList = new ArrayList<>();
    private TextView provinceText;
    private TextView cityText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_layout);
        ButterKnife.bind(this);
        initView();
        loadArea();
    }

    private void loadArea() {
        toolUtil.showProgressDialog(this);
        userInfo = DaoBean.getUseInfoById(1);
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        JSONObject content = new JSONObject();
        OkHttpUtils.postString()
                .url(CommonData.LOAD_AREA)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack2());
    }

    private void initView() {
        toolUtil = new ToolUtil();
        title.setTitleText(this.getResources().getString(R.string.addCustomer));
        provinceText = (TextView) province.findViewById(R.id.spinner_text);
        cityText = (TextView) city.findViewById(R.id.spinner_text);
        provinceText.addTextChangedListener(new ProvinceTextChangeListener());
        cityText.addTextChangedListener(new CityTextChangeListener());


    }

    @OnClick(R.id.addCustomer)
    public void onViewClicked() {
        if (TextUtils.isEmpty(customer.getText().toString())) {
            Toast.makeText(this, this.getResources().getString(R.string.auditCustmoerName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(creditCode.getText().toString())) {
            Toast.makeText(this, "请填写统一社会信用代码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            Toast.makeText(this, this.getResources().getString(R.string.auditCustmoerPhone), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(customerAddress.getText().toString())) {
            Toast.makeText(this, this.getResources().getString(R.string.auditCustmoerAddress), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(person.getText().toString())) {
            Toast.makeText(this, this.getResources().getString(R.string.auditCustmoerPerson), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(selectArea.getText())) {
            Toast.makeText(this, this.getResources().getString(R.string.auditCustmoercName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(province.getSelected_name())) {
            Toast.makeText(this, "请填写客户的省份", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city.getSelected_name())) {
            Toast.makeText(this, "请填写客户的市区", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(country.getSelected_name())) {
            Toast.makeText(this, "请填写客户的县区", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Area ar : areaList) {
            if (ar.getcDCName().equals(selectArea.getText())) {
                cDCCode = ar.getcDCCode();
                break;
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String customerString = customer.getText().toString();
        String mobileString = mobile.getText().toString();
        String addressString = customerAddress.getText().toString();
        String personString = person.getText().toString();
        String selectAreaString = selectArea.getText();
        String dialogMessage = "客户姓名：" + customerString + "\n";
        dialogMessage += "联系方式：" + mobileString + "\n";
        dialogMessage += "客户地址：" + addressString + "\n";
        dialogMessage += "联系人：" + personString + "\n";
        dialogMessage += "所属地区：" + selectAreaString + "\n";
        dialog.setMessage(dialogMessage);
        dialog.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton(this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commitCustomer();
            }
        });
        dialog.show();


    }

    private void commitCustomer() {
        JSONObject content = new JSONObject();
        UserInfo userInfo = DaoBean.getUseInfoById(1);
        try {
            if (isQM.isChecked()) {
                content.put("isQM", 1);
            } else {
                content.put("isQM", 0);
            }
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());
            content.put("cCusName", customer.getText().toString());
            content.put("mobile", mobile.getText().toString());
            content.put("customerAddress", customerAddress.getText().toString());
            content.put("person", person.getText().toString());
            content.put("cDCCode", cDCCode);
            content.put("cCusRegCode",creditCode.getText().toString());
            content.put("province_code",province.getSelected_code());
            content.put("city_code",city.getSelected_code());
            content.put("country_code",country.getSelected_code());
            content.put("province",province.getSelected_name());
            content.put("city_name",city.getSelected_name());
            content.put("country_name",country.getSelected_name());
            content.put("cCusDefine1", province.getSelected_name() + city.getSelected_name() + country.getSelected_name());
        } catch (JSONException e) {
        }
        toolUtil.showProgressDialog(this);
        OkHttpUtils.postString()
                .url(CommonData.addCustomerUrl)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }


    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
//            Toast.makeText(AddCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            Toast.makeText(AddCustomerActivity.this, "该客户已维护！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                Toast.makeText(AddCustomerActivity.this, AddCustomerActivity.this.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddCustomerActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }

        }
    }

    private class ResponseCallBack2 extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(AddCustomerActivity.this, AddCustomerActivity.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                areaList = response.getData().area;
                u8HrCt007List = response.getData().u8HrCt007;
                for (Area area : areaList) {
                    list_area.add(area.getcDCName());
                }
                provinceList = DaoBean.getU8HrCt007ListByIlevels(0);
                if (provinceList != null && provinceList.size() == 0) {
                    DaoBean.deleteU8HrCt007All();
                    DaoBean.insertU8HrCt007List(u8HrCt007List);
                    provinceList = DaoBean.getU8HrCt007ListByIlevels(0);
                }
                selectArea.setData(list_area);
                province.setData(provinceList);

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
            List<U8HrCt007> cityList = DaoBean.getU8HrCt007ListByParentCode(province_code);
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
            List<U8HrCt007> countryList = DaoBean.getU8HrCt007ListByParentCode(city_code);
            country.setData(countryList);
        }
    }
}
