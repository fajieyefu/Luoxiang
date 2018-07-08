package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import fajieyefu.com.luoxiang.adapter.ContractDetailsAdapter;
import fajieyefu.com.luoxiang.bean.ContractDetail;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-05-16.
 */

public class ContractAuditDetailsActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.qianyinche)
    TextView qianyinche;
    @BindView(R.id.powerType)
    TextView powerType;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.customer)
    TextView customer;
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
    @BindView(R.id.shenpixinxi)
    LinearLayout shenpixinxi;
    @BindView(R.id.rb_ok)
    RadioButton rbOk;
    @BindView(R.id.rb_fail)
    RadioButton rbFail;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.audit_msg)
    EditText auditMsg;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.check_layout)
    LinearLayout checkLayout;
    private String username;
    private String password;
    private int orderId;
    private ToolUtil toolUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_details_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        loadData();
    }

    /**
     * 初始化数据
     */
    private void loadData() {
        toolUtil = new ToolUtil();
        toolUtil.showProgressDialog(this, this.getResources().getString(R.string.waitting), this.getResources().getString(R.string.loading));
        username = DaoBean.getUseInfoById(1).getUsername();
        password = DaoBean.getUseInfoById(1).getPassword();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("orderId", orderId);
            Log.i("json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.contractDetails)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
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
                commit();
            }
        });
        dialog.show();


    }

    /**
     * 提交审批结果
     */
    private void commit() {
        toolUtil.showProgressDialog(this, this.getResources().getString(R.string.waitting), this.getResources().getString(R.string.loading));
        int result = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_ok:
                result = 1;
                break;
            case R.id.rb_fail:
                result = 0;
                break;
        }
        if (result == 0 && TextUtils.isEmpty(auditMsg.getText().toString())) {
            Toast.makeText(this, "退回修改请填写审批说明！", Toast.LENGTH_SHORT).show();
            toolUtil.dismissProgressDialog();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("orderId", orderId);
            jsonObject.put("code", result);
            jsonObject.put("beizhu", auditMsg.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("json", jsonObject.toString());
        OkHttpUtils.postString()
                .url(CommonData.CommitAuditRuslt)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack2());
    }

    /**
     * 返回初始化加载数据
     */
    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractAuditDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<Inventory> data = new ArrayList<>();
                if (response.getData().order.size() == 0) {
                    Toast.makeText(ContractAuditDetailsActivity.this, "沒有配置信息", Toast.LENGTH_SHORT).show();
                } else {
                    List<ContractDetail> contractDetails = response.getData().order;
                    data.addAll(contractDetails.get(0).getInventoryDetails());
                    ContractDetail contractDetail = contractDetails.get(0);
                    setTextView(qianyinche, contractDetail.getQianyinche());
                    setTextView(customer, contractDetail.getcCusName());
                    setTextView(phone, contractDetail.getcCusHand());
                    setTextView(color, contractDetail.getColor() + "/" + contractDetail.getLiang_color());
                    setTextView(carStyle, contractDetail.getModel());
                    setTextView(model, contractDetail.getCar_long() + "*" + contractDetail.getCar_width() + "*" + contractDetail.getCar_height());
                    setTextView(booknum, contractDetail.getBooknum());
                    setTextView(price, contractDetail.getSingleprice());
                    setTextView(chejia, "上:" + contractDetail.getUp().trim() + "下:" + contractDetail.getDown().trim() + "立:" + contractDetail.getMid().trim());
                    setTextView(lanban, contractDetail.getCelanban() + contractDetail.getCelanban_mark());
                    setTextView(lidigao, "离地高:" + contractDetail.getLidigao() + "\n后传半径:" + contractDetail.getHouxuan());
                    setTextView(qianxuan, "前悬:" + contractDetail.getZhongxinju() + "\n中心距:" + contractDetail.getQianxuan());
                    setTextView(qianyinxiao, contractDetail.getQianyinxiao() + "\n牵引座:" + contractDetail.getQianyinzuo());
                    setTextView(banhuang, contractDetail.getBanhuang());
                    setTextView(wcheng, contractDetail.getWcheng_type() + contractDetail.getWcheng_num() + contractDetail.getWcheng_mark());
                    setTextView(bianliang, contractDetail.getBianliang());
                    setTextView(fangshuicao, contractDetail.getFangshuicao());
                    setTextView(chexiangchicun, "量包角:" + contractDetail.getLiangbaojiao() + "\n方管:" + contractDetail.getFangguan());
                    setTextView(diban, contractDetail.getDiban());
                    setTextView(houmen, contractDetail.getHoumen());
                    setTextView(penggan, contractDetail.getPenggan() + "根,爬梯:" + contractDetail.getPati() + " 小框:" + contractDetail.getXiaokuang());
                    setTextView(jinshengqi, "数量:" + contractDetail.getJinshengqi() + "," + contractDetail.getJinshengqi_mark());
                    setTextView(gongjuxiang, contractDetail.getBox_left() + ",\n" + contractDetail.getBox_right());
                    setTextView(btsjq, contractDetail.getBtsjq());
                    setTextView(abs, contractDetail.getAbs());
                    setTextView(gangquan, contractDetail.getGangquan_num().trim() + "个\n" + contractDetail.getGangquan());
                    setTextView(luntai, contractDetail.getLuntai_num().trim() + "个\n" + contractDetail.getLuntai());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(mark, contractDetail.getMarks());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(chezhou, contractDetail.getChezhou());
                    setTextView(deposit, contractDetail.getPayedmoney() + "");
                    setTextView(powerType, "("+contractDetail.getPowerType()+")");
                    setTextView(amt, contractDetail.getOrderMoney() + "");
                    setTextView(amtDx, contractDetail.getOrdermoney_dx() + "");
                    setTextView(applyDate, contractDetail.getLeaveTime());
                    setTextView(obtainType, contractDetail.getCarstyle());
                    urgent.setClickable(false);
                    if (contractDetail.getUrgent_flag() == 0) {
                        urgent.setChecked(false);
                    } else {
                        urgent.setChecked(true);
                    }
                    dingjin.setClickable(false);
                    if (Integer.parseInt(contractDetail.getBmustbook()) == 0) {
                        dingjin.setChecked(false);
                    } else {
                        dingjin.setChecked(true);
                    }
                    yunfei.setClickable(false);
                    if (Integer.parseInt(contractDetail.getcDefine1()) == 0) {
                        yunfei.setChecked(false);
                    } else {
                        yunfei.setChecked(true);
                    }
                    if (contractDetail.getNq_flag() == 1) {
                        auditResult.setText("审核通过");
                    } else if (contractDetail.getNq_flag() == 2) {
                        auditResult.setText("退回");
                    } else {
                        auditResult.setText("等待审批");
                    }
                    setTextView(createTime, contractDetail.getOrdercreatetime());
                }
            } else {
                Toast.makeText(ContractAuditDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 返回提交审核结果
     */
    private class ResponseCallBack2 extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractAuditDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();

        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {

            } else {
                Toast.makeText(ContractAuditDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
            finish();

        }
    }

    private void setTextView(TextView textView, String text) {
        if (text == null) {
            textView.setText("");
        } else {
            textView.setText(text);
        }
    }
}
