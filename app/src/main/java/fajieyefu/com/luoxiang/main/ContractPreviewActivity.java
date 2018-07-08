package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * Created by Administrator on 2017-06-29.
 */

public class ContractPreviewActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.qianyinche)
    TextView qianyinche;
    @BindView(R.id.powerType)
    TextView powerType;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.carStyle)
    TextView carStyle;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.booknum)
    TextView booknum;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.chejia)
    TextView chejia;
    @BindView(R.id.lanban)
    TextView lanban;
    @BindView(R.id.lidigao)
    TextView lidigao;
    @BindView(R.id.houmen)
    TextView houmen;
    @BindView(R.id.qianxuan)
    TextView qianxuan;
    @BindView(R.id.penggan)
    TextView penggan;
    @BindView(R.id.qianyinxiao)
    TextView qianyinxiao;
    @BindView(R.id.jinshengqi)
    TextView jinshengqi;
    @BindView(R.id.banhuang)
    TextView banhuang;
    @BindView(R.id.gongjuxiang)
    TextView gongjuxiang;
    @BindView(R.id.wcheng)
    TextView wcheng;
    @BindView(R.id.btzj)
    TextView btzj;
    @BindView(R.id.bianliang)
    TextView bianliang;
    @BindView(R.id.btsjq)
    TextView btsjq;
    @BindView(R.id.fangshuicao)
    TextView fangshuicao;
    @BindView(R.id.abs)
    TextView abs;
    @BindView(R.id.chexiangchicun)
    TextView chexiangchicun;
    @BindView(R.id.gangquan)
    TextView gangquan;
    @BindView(R.id.diban)
    TextView diban;
    @BindView(R.id.luntai)
    TextView luntai;
    @BindView(R.id.chezhou)
    TextView chezhou;
    @BindView(R.id.mark)
    TextView mark;
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
    @BindView(R.id.dingjin)
    CheckBox dingjin;
    @BindView(R.id.yunfei)
    CheckBox yunfei;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.person)
    TextView person;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.endCustomerName)
    TextView endCustomerName;
    @BindView(R.id.endCustomerPhone)
    TextView endCustomerPhone;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.isNew)
    TextView isNew;
    @BindView(R.id.outlinesize)
    TextView outlinesize;
    @BindView(R.id.outlinesizelayout)
    LinearLayout outlinesizelayout;
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
    @BindView(R.id.others)
    TextView others;
    @BindView(R.id.oldPrice)
    TextView oldPrice;
    @BindView(R.id.oldPriceLayout)
    LinearLayout oldPriceLayout;
    @BindView(R.id.carriage)
    TextView carriage;
    @BindView(R.id.discountFee)
    TextView discountFee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_contract);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setTitleText(this.getResources().getString(R.string.contractpreview));
        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("data");
        Log.i("jsonData", jsonData);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonData);
            if (json.optInt("isNew") == 1) {
                isNew.setText("是");
                lidigao.setText(getString(R.string.lidigao) + json.optString("lidigao") + getString(R.string.houxuan) + json.optString("houxuan"));
            } else {
                isNew.setText("否");
                lidigao.setText(getString(R.string.lidigao) + json.optString("lidigao") + getString(R.string.huangyouzui) + json.optString("huangyouzui"));
                outlinesizelayout.setVisibility(View.VISIBLE);
                outlinesize.setText(json.optString("outlinesize"));
                others.setVisibility(View.GONE);
                oldPriceLayout.setVisibility(View.VISIBLE);
                oldPrice.setText(json.optString("oldPrice"));

            }
            area.setText(json.optString("cDCName"));
            customer.setText(json.optString("customer"));
            phone.setText(json.optString("mobile"));
            person.setText(json.optString("contractsMan"));
            address.setText(json.optString("address"));
            endCustomerName.setText(json.optString("endCustomerName"));
            endCustomerPhone.setText(json.optString("endCustomerPhone"));

            qianyinche.setText(json.optString("qianyinche"));
            powerType.setText("(" + json.optString("powerType") + ")");
            carStyle.setText(json.optString("model"));
            model.setText(json.optString("car_long") + "*" + json.optString("car_width") + "*" + json.optString("car_height") + "*" + json.optString("cantrail"));
            color.setText(json.optString("color") + "/" + json.optString("liang_color"));
            booknum.setText(json.optString("booknum"));
            price.setText(json.optString("singleprice"));
            chejia.setText(json.optString("up") + json.optString("down") + json.optString("mid"));
            lanban.setText(json.optString("celanban") + json.optString("celanban_mark") + "");

            qianxuan.setText(getString(R.string.qianxuan) + json.optString("qianxuan") + getString(R.string.zhongxinju) + json.optString("zhongxinju"));
            qianyinxiao.setText(json.optString("qianyinxiao") + getString(R.string.qianyinzuo) + json.optString("qianyinzuo"));
            banhuang.setText(json.optString("banhuang"));
            wcheng.setText(json.optString("wcheng_type") + json.optString("wcheng_num") + json.optString("wcheng_mark"));
            bianliang.setText(json.optString("bianliang"));
            fangshuicao.setText(json.optString("fangshuicao"));
            chexiangchicun.setText(getString(R.string.liangbaojiao) + json.optString("liangbaojiao") + getString(R.string.fangguan) + json.optString("fangguan"));
            diban.setText(json.optString("diban"));
            houmen.setText(json.optString("houmen") + " " + json.optString("houmen_mark"));
            penggan.setText(json.optString("penggan") + getString(R.string.paiti) + json.optString("pati") + getString(R.string.xiaokuang) + json.optString("xiaokuang"));
            jinshengqi.setText(getString(R.string.counts) + json.optString("jinshengqi") + "," + json.optString("jinshengqi_mark"));
            gongjuxiang.setText(json.optString("box_left") + "\n" + json.optString("box_right"));
            btsjq.setText(json.optString("btsjq"));
            btzj.setText(json.optString("btzj"));
            abs.setText(json.optString("abs") + " " + json.optString("absMark") + getString(R.string.shuibaokong) + json.optString("shuibaokong"));
            gangquan.setText(json.optString("gangquan_num") + getString(R.string.ge) + json.optString("gangquan"));
            luntai.setText(json.optString("luntai_num") + getString(R.string.ge) + json.optString("luntai"));
            chezhou.setText(json.optString("chezhou"));
            mark.setText(json.optString("remark"));
            deposit.setText(json.optString("payedmoney"));
            amt.setText(json.optString("ordermoney"));
            amtDx.setText(json.optString("ordermoney_dx"));
            applyDate.setText(json.optString("leaveTime"));
            obtainType.setText(json.optString("carstyle"));
            carriage.setText("运送费用:" + json.optString("carriage"));
            discountFee.setText(json.optString("discountFee"));
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
