package fajieyefu.com.luoxiang.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ContractAdapter;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ContractDetail;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-16.
 */

public class StockCarListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.queryInfo)
    TextView queryInfo;
    @BindView(R.id.stock_lv)
    ListView stockLv;
    @BindView(R.id.empty)
    RelativeLayout empty;
    private Button more;
    private Dialog dialog;
    private List<String> list_powerType;
    private List<String> list_model;
    private List<String> list_height;
    private List<String> list_houmen;
    private List<String> list_celanban;
    private List<String> list_diban;
    private ArrayAdapter<String> adapter_powerType;
    private ArrayAdapter<String> adapter_model;
    private ArrayAdapter<String> adapter_height;
    private ArrayAdapter<String> adapter_houmen;
    private ArrayAdapter<String> adapter_celanban;
    private ArrayAdapter<String> adapter_diban;
    private Spinner model;
    private Spinner powerType;
    private Spinner height;
    private EditText color;
    private Spinner celanban;
    private Spinner diban;
    private Spinner houmen;
    private Button search;
    private UserInfo useInfo;
    private ToolUtil toolUtil;
    private ContractAdapter adapter;
    private List<ContractBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_list_layout);
        ButterKnife.bind(this);
        initDialog();
        initView();
    }

    private void initDialog() {
        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.search_stock, null);
        model = (Spinner) view.findViewById(R.id.model);
        powerType = (Spinner) view.findViewById(R.id.powerType);
        height = (Spinner) view.findViewById(R.id.height);
        search = (Button) view.findViewById(R.id.search);
        color = (EditText) view.findViewById(R.id.color);
        houmen = (Spinner) view.findViewById(R.id.houmen);
        celanban = (Spinner) view.findViewById(R.id.celanban);
        diban = (Spinner) view.findViewById(R.id.diban);

        list_height = Arrays.asList(new String[]{"1400", "1500", "1600", "1700", "1800", "1900", "2000"});
        list_model = Arrays.asList(new String[]{"鹅", "直"});
        list_powerType = Arrays.asList(new String[]{"汽", "油"});
        list_celanban = Arrays.asList(new String[]{"标厢","两层分体","两层连体","田字格","内置对开","外置对开","上边梁"});
        list_diban = Arrays.asList(new String[]{"高强1.6","高强1.8","高强2.0","高强2.5","花纹2.0","花纹2.5","花纹3.0","花纹4.0"});
        list_houmen = Arrays.asList(new String[]{"两层分体","两层连体","田字格","对开半封","对开全封"});
        adapter_powerType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_powerType);
        adapter_height = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_height);
        adapter_model = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_model);
        adapter_diban = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_diban);
        adapter_houmen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_houmen);
        adapter_celanban = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_celanban);

        adapter_powerType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_height.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_diban.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_houmen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_celanban.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        model.setAdapter(adapter_model);
        powerType.setAdapter(adapter_powerType);
        height.setAdapter(adapter_height);
        diban.setAdapter(adapter_diban);
        celanban.setAdapter(adapter_celanban);
        houmen.setAdapter(adapter_houmen);

        search.setOnClickListener(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        title.setTitleText("库存车查询列表");
        adapter = new ContractAdapter(list,this);
        toolUtil = new ToolUtil();
        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(this);
        stockLv.setEmptyView(empty);
        stockLv.setAdapter(adapter);
        stockLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("last_activity","StockCarListActivity");
                intent.putExtra("orderId",list.get(position).getOrderId());
                if (list.get(position ).getIsSkeleton() == 1) {
                    intent.setClass(StockCarListActivity.this, HistorySkeletonDetailsActivity.class);
                } else {
                    intent.setClass(StockCarListActivity.this, HistoryDetailsActivity.class);
                }
                    startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                setSearchText();
                loadData();
                break;
            case R.id.more:
                dialog.show();
                break;
        }

    }

    private void setSearchText() {
        StringBuilder sb = new StringBuilder("当前查询信息为：");
        if (!TextUtils.isEmpty(height.getSelectedItem().toString())) {
            sb.append("*高度" + height.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(model.getSelectedItem().toString())) {
            sb.append("*型号" + model.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(powerType.getSelectedItem().toString())) {
            sb.append("*动力类型" + powerType.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(celanban.getSelectedItem().toString())) {
            sb.append("*侧栏板" + celanban.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(houmen.getSelectedItem().toString())) {
            sb.append("*后门" + houmen.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(diban.getSelectedItem().toString())) {
            sb.append("*底板" + diban.getSelectedItem().toString());
        }
        if (!TextUtils.isEmpty(color.getText().toString())) {
            sb.append("*颜色" + color.getText().toString());
        }
        queryInfo.setText(sb.toString());
        queryInfo.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        toolUtil.showProgressDialog(this);
        JSONObject content = new JSONObject();
        useInfo = DaoBean.getUseInfoById(1);
        try {
            content.put("username", useInfo.getUsername());
            content.put("password", useInfo.getPassword());
            if (TextUtils.isEmpty(height.getSelectedItem().toString())) {
                content.put("height", "");
            } else {
                content.put("height", height.getSelectedItem().toString());
            }
            if (TextUtils.isEmpty(model.getSelectedItem().toString())) {
                content.put("model", "");
            } else {
                content.put("model", model.getSelectedItem().toString());
            }
            if (TextUtils.isEmpty(powerType.getSelectedItem().toString())) {
                content.put("powerType", "");
            } else {
                content.put("powerType", powerType.getSelectedItem().toString());
            }
            if (TextUtils.isEmpty(celanban.getSelectedItem().toString())) {
                content.put("celanban", "");
            } else {
                content.put("celanban", celanban.getSelectedItem().toString());
            }
            if (TextUtils.isEmpty(diban.getSelectedItem().toString())) {
                content.put("diban", "");
            } else {
                content.put("diban", diban.getSelectedItem().toString());
            }
            if (TextUtils.isEmpty(color.getText().toString())) {
                content.put("color", "");
            } else {
                content.put("color", color.getText().toString());
            }
            if (TextUtils.isEmpty(houmen.getSelectedItem().toString())) {
                content.put("houmen", "");
            } else {
                content.put("houmen", houmen.getSelectedItem().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("请求数据", content.toString());
        OkHttpUtils.postString()
                .url(CommonData.getStockList)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());
    }

    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (dialog!=null){
                dialog.dismiss();
            }

            toolUtil.dismissProgressDialog();
            Toast.makeText(StockCarListActivity.this, "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (dialog!=null){
                dialog.dismiss();
            }
            toolUtil.dismissProgressDialog();
            List<ContractBean> contractDetails = response.getData().stockList;
            list.clear();
            list.addAll(contractDetails);
            adapter.notifyDataSetChanged();

        }
    }
}
