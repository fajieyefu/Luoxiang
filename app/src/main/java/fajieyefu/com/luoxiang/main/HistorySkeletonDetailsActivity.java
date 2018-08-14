package fajieyefu.com.luoxiang.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ContractChangeAdapter;
import fajieyefu.com.luoxiang.adapter.PicturePreviewAdapter;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ContractChange;
import fajieyefu.com.luoxiang.bean.ContractDetail;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.bean.PictureBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.LinearLayoutForListView;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.test.ContractModifyActivity;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-05-16.
 */

public class HistorySkeletonDetailsActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.productType)
    TextView productType;
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
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.lv_sign)
    ListView lvSign;
    @BindView(R.id.downWord)
    Button downWord;
    @BindView(R.id.downingLayout)
    LinearLayout downingLayout;
    @BindView(R.id.wordPic)
    LinearLayout wordPic;
    @BindView(R.id.wordLayout)
    RelativeLayout wordLayout;
    @BindView(R.id.docName)
    TextView docName;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.stopFlag)
    TextView stopFlag;
    @BindView(R.id.pabhyj)
    TextView pabhyj;
    @BindView(R.id.pabhyj_layout)
    LinearLayout pabhyjLayout;
    @BindView(R.id.carriage)
    TextView carriage;
    @BindView(R.id.discountFee)
    TextView discountFee;
    @BindView(R.id.change_content)
    TextView changeContent;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.order_history_createtime)
    TextView orderHistoryCreatetime;
    @BindView(R.id.contractChangeContent)
    EditText contractChangeContent;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.nq_flag)
    TextView nqFlag;
    @BindView(R.id.pro_flag)
    TextView proFlag;
    @BindView(R.id.zp_flag)
    TextView zpFlag;
    @BindView(R.id.final_flag)
    TextView finalFlag;
    @BindView(R.id.contract_history_change_layout)
    LinearLayout contractHistoryChangeLayout;
    @BindView(R.id.areaStanName)
    TextView areaStanName;
    @BindView(R.id.standard_pro_text)
    TextView standardProText;
    @BindView(R.id.extra_change_content)
    TextView extraChangeContent;
    @BindView(R.id.isQingZang)
    CheckBox isQingZang;
    @BindView(R.id.dingjin_state)
    TextView dingjinState;
    @BindView(R.id.certificate_flag)
    TextView certificateFlag;
    private String username;
    private String password;
    private int orderId;
    private ToolUtil toolUtil;
    private Button more;
    private String orderNumber;
    private String cCusCode;//客户编码
    private String standardId;//标准编码
    private Button modify;
    private Button applyModifty;
    private Button delete;
    private Dialog dialog;
    private int commitType = 0;
    int nq_flag = 0;
    private List<ContractChange> contractChange = new ArrayList<>();
    private ContractChangeAdapter adapter;
    private String ordinaryContent;
    private PicturePreviewAdapter pictureAdapter;
    private PicturePreviewAdapter signatureAdapter;
    List<PictureBean> listPicture = new ArrayList<>();
    List<PictureBean> signListPicture = new ArrayList<>();
    private ContractDetail contractDetail;
    private File dir;
    private List<ContractBean> sameOrders = new ArrayList<>();
    private JSONArray sameOrdersArray;
    private int screenWidth;
    private int screenHeight;
    List<String> otainTypesList;
    private String last_activity;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_contract_skeleton);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        type = intent.getIntExtra("type", 0);
        last_activity = intent.getStringExtra("last_activity");
        initView();
        loadData();


    }

    private void initView() {
        String[] otainTypes = new String[]{"自提", "配送"};
        otainTypesList = Arrays.asList(otainTypes);
        int[] hm = new ToolUtil().getScreenWH(this);
        screenWidth = hm[0];
        screenHeight = hm[1];
        dir = Environment.getExternalStorageDirectory();
        wordLayout.setVisibility(View.VISIBLE);
        pictureAdapter = new PicturePreviewAdapter(this, listPicture, "附件");
        signatureAdapter = new PicturePreviewAdapter(this, signListPicture, "客户签字");
        lv.setAdapter(pictureAdapter);
        lvSign.setAdapter(signatureAdapter);
        shenpixinxi.setVisibility(View.VISIBLE);
        toolUtil = new ToolUtil();
        title.setTitleText(getString(R.string.historyDetals));
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        initDialog();
        more = (Button) title.findViewById(R.id.more);
        if (!TextUtils.isEmpty(last_activity) && last_activity.equals("StockCarListActivity")) {
            more.setVisibility(View.GONE);
        } else {
            more.setVisibility(View.VISIBLE);
        }

        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });
        downWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownWordAsyncTask(HistorySkeletonDetailsActivity.this, CommonData.loadImageFile + "?fileName=" + contractDetail.getWordPath()).execute();
            }
        });
        wordPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.addCategory("android.intent.category.DEFAULT");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                File dir = StorageUtils.getCacheDirectory(ContractCheckSkeletonActivity.this);
                String fileName = contractDetail.getOrderNumber() + ".docx";
                File wordFile = new File(dir, fileName);
                if (!wordFile.exists()) {
                    Toast.makeText(HistorySkeletonDetailsActivity.this, "请重新下载文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uriForFile = FileProvider.getUriForFile(HistorySkeletonDetailsActivity.this, "fajieyefu.com.luoxiang.fileprovider", wordFile);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    i.setDataAndType(uriForFile, "application/msword");
                } else {
                    i.setDataAndType(Uri.fromFile(wordFile), "application/msword");
                }
                HistorySkeletonDetailsActivity.this.startActivity(i);
            }
        });

    }


    private void initDialog() {
        dialog = new Dialog(HistorySkeletonDetailsActivity.this);
        View view = LayoutInflater.from(HistorySkeletonDetailsActivity.this).inflate(R.layout.more_layout, null);
        applyModifty = (Button) view.findViewById(R.id.applyModify);
        applyModifty.setOnClickListener(HistorySkeletonDetailsActivity.this);
        modify = (Button) view.findViewById(R.id.modify);
        modify.setOnClickListener(HistorySkeletonDetailsActivity.this);
        modify.setVisibility(View.GONE);
        delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(HistorySkeletonDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void loadData() {
        username = DaoBean.getUseInfoById(1).getUsername();
        password = DaoBean.getUseInfoById(1).getPassword();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("orderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("请求数据", jsonObject.toString());
        OkHttpUtils.postString()
                .url(CommonData.contractDetails)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify:
                commitType = CommonData.modifyFlag;
                modify();
                break;
            case R.id.delete:
                delete();
                break;
            /*case R.id.applyModify:
                initApplyModifyDialog();
                break;*/
            case R.id.modifyAfterApply:
                commitType = 2;
                modify();
                break;
            case R.id.applyModify:
                commitType = CommonData.changeFlag;
                modify();
                break;


        }

    }


    /**
     * 刪除合同
     */
    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除该合同？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteContract();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();

    }

    private void deleteContract() {
        String username = DaoBean.getUseInfoById(1).getUsername();
        String password = DaoBean.getUseInfoById(1).getPassword();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            Log.i("content", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        OkHttpUtils.postString()
                .url(CommonData.DELETECONTRACT)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack3());
    }

    private void modify() {
        String username = DaoBean.getUseInfoById(1).getUsername();
        String password = DaoBean.getUseInfoById(1).getPassword();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("standardId", standardId);
            jsonObject.put("cCusCode", cCusCode);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            Log.i("content", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolUtil.showProgressDialog(this, getString(R.string.waitting), getString(R.string.loading));
        OkHttpUtils.postString()
                .url(CommonData.StandardDetails)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack2());
    }

    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(HistorySkeletonDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                List<ContractDetail> contractDetails = response.getData().order;
                sameOrders = response.getData().sameOrders;
                List<ContractDetail> orderHistory = response.getData().orderHistory;
                judgeContractHistoryChange(orderHistory);

                ordinaryContent = new Gson().toJson(contractDetails);
                contractDetail = contractDetails.get(0);
                orderNumber = contractDetail.getOrderNumber();
                cCusCode = contractDetail.getcCusCode();
                standardId = contractDetail.getStandardId();
                setTextView(customer, contractDetail.getcCusName());
                setTextView(phone, contractDetail.getcCusHand());
                setTextView(address, contractDetail.getcCusAddress());
                setTextView(person, contractDetail.getcCusPerson());
                setTextView(endCustomerName, contractDetail.getEndCustomerName());
                setTextView(endCustomerPhone, contractDetail.getEndCustomerPhone());

                setTextView(qianyinche, contractDetail.getQianyinche());
                setTextView(area, contractDetail.getcDCName());
                if (contractDetail.getIsNew() == 1) {
                    setTextView(isNew, "是");

                } else {
                    setTextView(isNew, "否");
                }
                setTextView(axisCount, contractDetail.getAxisCount().trim());
                setTextView(color, contractDetail.getColor().trim());
                setTextView(powerType, "(" + contractDetail.getPowerType().trim() + ")");

                setTextView(model, contractDetail.getModel().trim());
                setTextView(productType, contractDetail.getSkeletonType().trim());

                setTextView(booknum, contractDetail.getBooknum());
                String priceStr = contractDetail.getSingleprice();
                setTextView(price, priceStr.substring(0, priceStr.length() - 2));
                setTextView(chejia, contractDetail.getUp().trim() + contractDetail.getDown().trim() + contractDetail.getMid().trim());
                setTextView(qzhuanwan, contractDetail.getQianxuan().trim());
                setTextView(qianyinxiao, contractDetail.getQianyinxiao());
                setTextView(lidgao, contractDetail.getLidigao() + "后转弯半径：" + contractDetail.getHouxuan() + "前回转空间:" + contractDetail.getFront_space());
                setTextView(banhuang, contractDetail.getBanhuang().trim() + "  " + nullToString(contractDetail.getBanhuangMark()).trim());
                setTextView(fendar, contractDetail.getFender());
                setTextView(realValue, contractDetail.getRelayValveType());
                setTextView(airCylinderType, contractDetail.getAirCylinderType());
                setTextView(brakeChamberType, contractDetail.getBrakeChamberType());
                setTextView(lockType, contractDetail.getLockType() + "  备注:" + contractDetail.getLock_mark());
                setTextView(unloadingPlatform, contractDetail.getUnloadingPlatform());
                setTextView(suspensionType, contractDetail.getSuspensionType());
                setTextView(box, contractDetail.getBox_left() + " " + contractDetail.getBox_right());
                setTextView(btsjq, contractDetail.getBtsjq());
                setTextView(btzj, contractDetail.getBtzj());
                setTextView(legType, contractDetail.getLegType());
                setTextView(abs, contractDetail.getAbs() + " " + contractDetail.getAbsMark());
                setTextView(gangquan, contractDetail.getGangquan().trim() + " " + contractDetail.getGangquan_mark().trim());
                setTextView(gangquanNum, contractDetail.getGangquan_num().trim());
                setTextView(luntai, contractDetail.getLuntai().trim() + " " + contractDetail.getLuntai_mark().trim());
                setTextView(luntaiNum, contractDetail.getLuntai_num().trim());
                setTextView(chezhou, contractDetail.getChezhou().trim() + " " + nullToString(contractDetail.getChezhouMark()).trim());
                setTextView(mark, contractDetail.getMarks().trim());
                setTextView(deposit, contractDetail.getPayedmoney() + "");
                setTextView(amt, contractDetail.getOrderMoney() + "");
                setTextView(amtDx, contractDetail.getOrdermoney_dx() + "");
                setTextView(applyDate, contractDetail.getLeaveTime().split(" ")[0]);
                setTextView(obtainType, contractDetail.getCarstyle());
                setTextView(carriage, "运费：" + contractDetail.getCarriage());
                setTextView(changeContent, contractDetail.getChange_content());
                setTextView(discountFee, contractDetail.getDiscountFee() == null ? "" : contractDetail.getDiscountFee().toString());
                setTextView(areaStanName, contractDetail.getAreaStanName());
                setTextView(standardProText, contractDetail.getStandard_pro_text());
                setTextView(extraChangeContent, contractDetail.getExtra_change_content());
                if (contractDetail.getIsQingZang() == 0) {
                    isQingZang.setChecked(false);
                } else {
                    isQingZang.setChecked(true);
                }
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
                nq_flag = Integer.parseInt(contractDetail.getNq_flag() + "");
                if (nq_flag == 1) {
                    applyModifty.setVisibility(View.VISIBLE);
                    auditResult.setText(getString(R.string.tongguo));
                    delete.setVisibility(View.GONE);
                } else if (nq_flag == 2 && type == 0) {
                    auditResult.setText(getString(R.string.tuihui));
                    modify.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.GONE);
                } else if (nq_flag == 3) {
                    auditResult.setText("作废");
                    modify.setVisibility(View.GONE);
                    delete.setVisibility(View.GONE);
                } else {
                    auditResult.setText(getString(R.string.dsh));
                    if (type == 0) {
                        delete.setVisibility(View.VISIBLE);
                    }


                }
                setTextView(createTime, contractDetail.getOrdercreatetime());
                setTextView(auditMsg, contractDetail.getSpmark());
                contractChange = response.getData().orderChange;
                int size = contractChange.size();
                if (contractChange != null && contractChange.size() > 0) {
                    adapter = new ContractChangeAdapter(contractChange, HistorySkeletonDetailsActivity.this);
                    contractChangeLv.setAdapter(adapter);
                }

                if (!TextUtils.isEmpty(contractDetail.getFilePath())) {
                    PictureBean pictureBean = new PictureBean();
                    List<String> list2 = new ArrayList<>();
                    String[] filesPath = contractDetail.getFilePath().split(",");
                    for (int i = 0; i < filesPath.length; i++) {
                        list2.add(CommonData.loadImageFile + "?fileName=" + filesPath[i]);
                    }
                    pictureBean.setPic(list2);
                    listPicture.add(pictureBean);
                    pictureAdapter.notifyDataSetChanged();
                }
                if (!TextUtils.isEmpty(contractDetail.getSignaturePath())) {
                    PictureBean signBean = new PictureBean();
                    List<String> signlist = new ArrayList<>();
                    signlist.add(CommonData.loadImageFile + "?fileName=" + contractDetail.getSignaturePath());
                    signBean.setPic(signlist);
                    signListPicture.add(signBean);
                    signatureAdapter.notifyDataSetChanged();
                }
                if (contractDetail.getStop_flag() == 0) {
                    stopFlag.setText("正常");
                    pabhyjLayout.setVisibility(View.GONE);
                } else {
                    stopFlag.setText("驳回");
                    pabhyj.setText(contractDetail.getPgbhyj());
                    pabhyjLayout.setVisibility(View.VISIBLE);
                }
//                File dir = StorageUtils.getCacheDirectory(ContractCheckSkeletonActivity.this);
                String fileName = contractDetail.getOrderNumber() + ".docx";
                File wordFile = new File(dir, fileName);
                if (wordFile.exists()) {
                    downWord.setText("重新下载");
                    wordPic.setVisibility(View.VISIBLE);
                    docName.setText(fileName);
                } else {
                    downWord.setText("合同下载");
                    wordPic.setVisibility(View.GONE);
                }
                judgeWordDownAble();
                if (contractDetail.getCertificate_flag()==1){
                    certificateFlag.setText("合格");
                }else{
                    certificateFlag.setText("不合格");
                }

            } else {
                Toast.makeText(HistorySkeletonDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }


        }


    }

    private void judgeContractHistoryChange(List<ContractDetail> orderHistory) {
        contractHistoryChangeLayout.setVisibility(View.GONE);
        if (orderHistory.size() > 0) {
            ContractDetail tempContractDetail = orderHistory.get(0);
            orderHistoryCreatetime.setText("申请时间：" + tempContractDetail.getCreatetime());
            contractHistoryChangeLayout.setVisibility(View.VISIBLE);
            String change_content = TextUtils.isEmpty(tempContractDetail.getChange_content()) ? "" : tempContractDetail.getChange_content();
            String extra_change_content = TextUtils.isEmpty(tempContractDetail.getExtra_change_content()) ? "" : tempContractDetail.getExtra_change_content();
            contractChangeContent.setText(change_content + "\n" + extra_change_content);
            String nq_mark = TextUtils.isEmpty(tempContractDetail.getNq_mark()) ? "" : " " + tempContractDetail.getNq_mark();
            String pro_mark = TextUtils.isEmpty(tempContractDetail.getPro_mark()) ? "" : " " + tempContractDetail.getPro_mark();
            String zp_mark = TextUtils.isEmpty(tempContractDetail.getZp_mark()) ? "" : " " + tempContractDetail.getZp_mark();
            switch (tempContractDetail.getNq_flag()) {
                case 0:
                    nqFlag.setText(this.getString(R.string.dsh) + nq_mark);
                    break;
                case 1:
                    nqFlag.setText(this.getString(R.string.tongguo) + nq_mark);
                    break;
                case 2:
                    nqFlag.setText(this.getString(R.string.tuihui) + nq_mark);
                    break;
            }
            switch (tempContractDetail.getPro_flag()) {
                case 0:
                    proFlag.setText(this.getString(R.string.dsh) + pro_mark);
                    break;
                case 1:
                    proFlag.setText(this.getString(R.string.tongguo) + pro_mark);
                    break;
                case 2:
                    proFlag.setText(this.getString(R.string.tuihui) + pro_mark);
                    break;
            }
            switch (tempContractDetail.getZp_flag()) {
                case 0:
                    zpFlag.setText(this.getString(R.string.dsh) + zp_mark);
                    break;
                case 1:
                    proFlag.setText(this.getString(R.string.tongguo) + zp_mark);
                    break;
                case 2:
                    proFlag.setText(this.getString(R.string.tuihui) + zp_mark);
                    break;
            }
            switch (tempContractDetail.getFinal_flag()) {
                case 0:
                    finalFlag.setText(this.getString(R.string.dsh));
                    break;
                case 1:
                    finalFlag.setText(this.getString(R.string.tongguo));
                    break;
                case 2:
                    finalFlag.setText(this.getString(R.string.tuihui));
                    break;
            }
        }

    }

    private void setTextView(TextView textView, String text) {
        if (text == null) {
            textView.setText("");
        } else {
            textView.setText(text);
        }
    }

    private class ResponCallBack2 extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(HistorySkeletonDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                List<InventoryClass> inventoryClass = response.getData().inventory;
                DaoBean.deleteInventoryClassAll();
                DaoBean.insertInventoryClassList(inventoryClass);
                DaoBean.deleteInventoryAll();
                for (int i = 0; i < inventoryClass.size(); i++) {
                    List<Inventory> inventory = inventoryClass.get(i).getInventoryDetails();
                    if (inventory != null) {
                        DaoBean.insertInventoryList(inventory);
                    }
                }

                Intent intent = new Intent(HistorySkeletonDetailsActivity.this, ContractModifyActivity.class);
                ObtainBean customer = response.getData().customer;
                intent.putExtra("customer", customer);
                intent.putExtra("orderNumber", orderNumber);
                intent.putExtra("orderId", orderId);
                intent.putExtra("commitType", commitType);
                intent.putExtra("area", (Serializable) response.getData().area);
                intent.putExtra("ordinaryContent", ordinaryContent);
                intent.putExtra("sameOrders", (Serializable) sameOrders);
                intent.putExtra("isSkeleton", 1);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(HistorySkeletonDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }

        }
    }


    private class ResponCallBack3 extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(HistorySkeletonDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            finish();
            if (response.getCode() == 0) {
                Toast.makeText(HistorySkeletonDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(HistorySkeletonDetailsActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

            }

        }
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    private class DownWordAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        private Context mContext;
        private int file_size;//文件大小
        private int progress;
        private ProgressBar progressBar;
        private String urlPath;

        public DownWordAsyncTask(Context context, String urlPath) {
            mContext = context;
            this.urlPath = urlPath;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            View view = LayoutInflater.from(mContext).inflate(R.layout.downing_word, downingLayout);
            downingLayout.setVisibility(View.VISIBLE);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream in = null;
            FileOutputStream out = null;
            URL url = null;
            try {
                url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(false);
                urlConnection.setConnectTimeout(10 * 1000);
                urlConnection.setReadTimeout(10 * 1000);
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setRequestProperty("Charset", "UTF-8");
                urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                urlConnection.connect();
                file_size = urlConnection.getContentLength();
                long bytesum = 0;
                int byteread = 0;
                in = urlConnection.getInputStream();
//                File dir = StorageUtils.getCacheDirectory(mContext);
                String fileName = contractDetail.getOrderNumber() + ".docx";
                File wordFile = new File(dir, fileName);
                out = new FileOutputStream(wordFile);
                byte[] buffer = new byte[1024];
//				int oldProgress = 0;
                while ((byteread = in.read(buffer)) != -1) {
                    bytesum += byteread;
                    //计算进度条位置
                    progress = (int) (((float) bytesum / file_size) * 100);
                    publishProgress(progress);
                    out.write(buffer, 0, byteread);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                wordPic.setVisibility(View.VISIBLE);
                docName.setText(contractDetail.getOrderNumber() + ".docx");
                downingLayout.setVisibility(View.GONE);
            } else {
                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT);
            }
        }
    }

    private void judgeWordDownAble() {
        if (contractDetail.getNq_flag() != 1) {
            downWord.setVisibility(View.GONE);
            dingjinState.setText("不合格");
        } else if (contractDetail.getNq_flag() == 1) {
            if (contractDetail.getFbooksum() != null && (int) Float.parseFloat(contractDetail.getFbooksum().toString()) != 0) {
                if (contractDetail.getiAmt_f() == null) {
//                    downWord.setVisibility(View.GONE);
                    downWord.setVisibility(View.VISIBLE);
                    dingjinState.setText("不合格");
                } else if (contractDetail.getiAmt_f() >= contractDetail.getFbooksum()) {
                    downWord.setVisibility(View.VISIBLE);
                    dingjinState.setText("合格");
                } else {
//                    downWord.setVisibility(View.GONE);
                    downWord.setVisibility(View.VISIBLE);
                    dingjinState.setText("不合格");
                }
            } else {
                downWord.setVisibility(View.VISIBLE);
                dingjinState.setText("合格");
            }

        }


    }

    private String nullToString(String text) {
        return TextUtils.isEmpty(text) ? "" : text;
    }
}
