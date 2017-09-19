package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.layout.LinearLayoutForListView;
import fajieyefu.com.luoxiang.layout.TitleLayout;

/**
 * Created by Administrator on 2017-09-16.
 */

public class SkeletonPreviewActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.person)
    TextView person;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.endCustomerName)
    TextView endCustomerName;
    @BindView(R.id.endCustomerPhone)
    TextView endCustomerPhone;
    @BindView(R.id.qianyinche)
    TextView qianyinche;
    @BindView(R.id.powerType)
    TextView powerType;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.isNew)
    TextView isNew;
    @BindView(R.id.productType)
    TextView productType;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.axisCount)
    TextView axisCount;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.booknum)
    TextView booknum;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.chejia)
    TextView chejia;
    @BindView(R.id.qzhuanwan)
    TextView qzhuanwan;
    @BindView(R.id.qianyinxiao)
    TextView qianyinxiao;
    @BindView(R.id.lidgao)
    TextView lidgao;
    @BindView(R.id.banhuang)
    TextView banhuang;
    @BindView(R.id.fendar)
    TextView fendar;
    @BindView(R.id.realValue)
    TextView realValue;
    @BindView(R.id.airCylinderType)
    TextView airCylinderType;
    @BindView(R.id.brakeChamberType)
    TextView brakeChamberType;
    @BindView(R.id.lockType)
    TextView lockType;
    @BindView(R.id.unloadingPlatform)
    TextView unloadingPlatform;
    @BindView(R.id.suspensionType)
    TextView suspensionType;
    @BindView(R.id.box)
    TextView box;
    @BindView(R.id.btzj)
    TextView btzj;
    @BindView(R.id.btsjq)
    TextView btsjq;
    @BindView(R.id.legType)
    TextView legType;
    @BindView(R.id.abs)
    TextView abs;
    @BindView(R.id.gangquan)
    TextView gangquan;
    @BindView(R.id.gangquan_num)
    TextView gangquanNum;
    @BindView(R.id.luntai)
    TextView luntai;
    @BindView(R.id.luntai_num)
    TextView luntaiNum;
    @BindView(R.id.chezhou)
    TextView chezhou;
    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.others)
    TextView others;
    @BindView(R.id.dingjin)
    CheckBox dingjin;
    @BindView(R.id.yunfei)
    CheckBox yunfei;
    @BindView(R.id.deposit)
    TextView deposit;
    @BindView(R.id.amt)
    TextView amt;
    @BindView(R.id.amt_dx)
    TextView amtDx;
    @BindView(R.id.applyDate)
    TextView applyDate;
    @BindView(R.id.obtain_type)
    TextView obtainType;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.createTime)
    TextView createTime;
    @BindView(R.id.audit_result)
    TextView auditResult;
    @BindView(R.id.audit_msg)
    EditText auditMsg;
    @BindView(R.id.shenpixinxi)
    LinearLayout shenpixinxi;
    @BindView(R.id.contract_change_lv)
    LinearLayoutForListView contractChangeLv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_contract_skeleton);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setTitleText("骨架车合同预览");
        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("data");
        JSONObject json = null;
        try {
            json = new JSONObject(jsonData);
            if (json.optInt("isNew") == 1) {
                isNew.setText("是");
            } else {
                isNew.setText("否");

            }
            qianyinche.setText(json.optString("qianyinche"));
            area.setText(json.optString("cDCName"));
            customer.setText(json.optString("customer"));
            phone.setText(json.optString("mobile"));
            person.setText(json.optString("contractsMan"));
            address.setText(json.optString("address"));
            endCustomerName.setText(json.optString("endCustomerName"));
            endCustomerPhone.setText(json.optString("endCustomerPhone"));
            axisCount.setText(json.optString("axisCount")+"轴");
            powerType.setText("(" + json.optString("powerType") + ")");
            model.setText(json.optString("model"));
            productType.setText(json.optString("skeletonType"));
            color.setText(json.optString("color"));
            booknum.setText(json.optString("booknum"));
            price.setText(json.optString("singleprice"));
            chejia.setText(json.optString("up") + json.optString("down") + json.optString("mid"));
            qzhuanwan.setText(json.optString("qianxuan"));
            qianyinxiao.setText(json.optString("qianyinxiao"));
            lidgao.setText(json.optString("lidigao")+"后转弯半径:"+json.optString("houxuan")+"前回转距离"+json.optString("front_space"));
            banhuang.setText(json.optString("banhuang"));
            fendar.setText(json.optString("fender"));
            realValue.setText(json.optString("relayValveType"));
            airCylinderType.setText(json.optString("airCylinderType"));
            brakeChamberType.setText(json.optString("brakeChamberType"));
            lockType.setText(json.optString("lockType")+"  备注："+json.optString("lock_mark"));
            unloadingPlatform.setText(json.optString("unloadingPlatform"));
            suspensionType.setText(json.optString("suspensionType"));
            box.setText(json.optString("box_left")+" "+json.optString("box_right"));
            btzj.setText(json.optString("btzj"));
            btsjq.setText(json.optString("btsjq"));
            legType.setText(json.optString("legType"));
            abs.setText(json.optString("abs"));
            gangquan.setText(json.optString("gangquan"));
            gangquanNum.setText(json.optString("gangquan_num"));
            luntai.setText(json.optString("luntai"));
            luntaiNum.setText(json.optString("luntai_num"));
            chezhou.setText(json.optString("chezhou"));
            mark.setText(json.optString("remark"));
            deposit.setText(json.optString("payedmoney"));
            amt.setText(json.optString("ordermoney"));
            amtDx.setText(json.optString("ordermoney_dx"));
            applyDate.setText(json.optString("leaveTime"));
            obtainType.setText(json.optString("carstyle"));
            if (json.optInt("urgent_flag") == 0) {
                urgent.setChecked(false);
            } else {
                urgent.setChecked(true);
            }
            if (json.optInt("bmustbook") == 0) {
                dingjin.setChecked(false);
            } else {
                dingjin.setChecked(true);
            }
            if (json.optInt("cDefine1") == 0) {
                yunfei.setChecked(false);
            } else {
                yunfei.setChecked(true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
