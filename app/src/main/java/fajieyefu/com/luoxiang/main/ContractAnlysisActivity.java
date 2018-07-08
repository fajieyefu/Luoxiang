package fajieyefu.com.luoxiang.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.AnalysisAddressAdapter;
import fajieyefu.com.luoxiang.bean.AnalysisAddressBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.Rotate3dAnimation;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import fajieyefu.com.luoxiang.util.YearMonthDatePickerDialog;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-04.
 */
public class ContractAnlysisActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.chart)
    ColumnChartView chart;
    @BindView(R.id.page_data)
    LinearLayout pageData;
    @BindView(R.id.content)
    LinearLayout content;
    private UserInfo userInfo;
    private String queryTime;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int duration = 600;
    private ToolUtil toolUtil;
    private boolean hasAxes;
    private boolean hasAxesNames;
    private boolean hasLabels;
    private boolean hasLabelForSelected;
    private YearMonthDatePickerDialog datePicker;
    private ColumnChartData data;
    private List<AnalysisAddressBean> list = new ArrayList<>();
    private AnalysisAddressAdapter addressAdapter = new AnalysisAddressAdapter(this, list);
    private AnalysisAddressBean addressBean;
    private Button more;
    public  static String[] addresses;

    private Rotate3dAnimation openAnimation, closeAnimation;
    private int centerX, centerY;
    private int depthZ = 400;
    private boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_analysis_layout);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        queryTime = mYear + "-" + (mMonth+1);
        toolUtil = new ToolUtil();
        datePicker = new YearMonthDatePickerDialog(this,DatePickerDialog.THEME_HOLO_DARK,mDateSetListener,mYear,mMonth,mDay);
        TextView titleText = (TextView) title.findViewById(R.id.titleText);
        title.setTitleText(this.getResources().getString(R.string.analysisByAddress));
        more = (Button) title.findViewById(R.id.more);
        titleText.setOnClickListener(this);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(this);
        chart.setOnValueTouchListener(new ValueTouchListener());
        setChartParameters();
        listview.setAdapter(addressAdapter);
    }

    private void initData() {
        toolUtil.showProgressDialog(this);
        JSONObject content = new JSONObject();
        userInfo = DaoBean.getUseInfoById(1);
        Log.i("getCountsByAddressURL", queryTime);
        try {
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());
            content.put("time", queryTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.getCountsByAddressURL)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }


    private void setChartParameters() {
        hasAxes = true;
        hasAxesNames = true;
        hasLabels = false;
        hasLabelForSelected = false;
        chart.setValueSelectionEnabled(hasLabelForSelected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                datePicker.show();
                break;
            case R.id.titleText:
                AnimView();
                break;
        }


    }

    private void AnimView() {


        //以旋转对象的中心点为旋转中心点，这里主要不要再onCreate方法中获取，因为视图初始绘制时，获取的宽高为0
        centerX = content.getWidth() / 2;
        centerY = content.getHeight() / 2;
        if (openAnimation == null) {
            initOpenAnim();
            initCloseAnim();
        }

        //用作判断当前点击事件发生时动画是否正在执行
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return;
        }
        if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) {
            return;
        }

        //判断动画执行
        if (isOpen) {
            content.startAnimation(closeAnimation);

        } else {
            content.startAnimation(openAnimation);
        }

        isOpen = !isOpen;


    }


    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(ContractAnlysisActivity.this, ContractAnlysisActivity.this.getResources().getString(R.string.abnormal), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode() == 0) {
                list.clear();
                list.addAll(response.getData().orders);
                addresses = new String[list.size()];
                for (int i=0; i<list.size();i++){
                    if (TextUtils.isEmpty(list.get(i).getAddress())){
                        list.get(i).setAddress( ContractAnlysisActivity.this.getResources().getString(R.string.test));
                    }
                    addresses[i]=list.get(i).getAddress();
                }
                addressAdapter.notifyDataSetChanged();
                generateDefaultData();
            } else {
                Toast.makeText(ContractAnlysisActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private void generateDefaultData() {

        int numSubcolumns = 1;
        int numColumns = addresses.length;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<AxisValue> axisValues = new ArrayList<>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(list.get(i).getCounts(), ChartUtils.pickColor()));
            }
            axisValues.add(new AxisValue(i).setLabel(addresses[i]));
            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis(axisValues);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName( ContractAnlysisActivity.this.getResources().getString(R.string.address));
                axisY.setName( ContractAnlysisActivity.this.getResources().getString(R.string.orderCounts));
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(ContractAnlysisActivity.this, addresses[columnIndex] + ":" + value.getValue(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    /**
     * 3D旋转打开效果，注意旋转角度
     */
    private void initOpenAnim() {
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                chart.setVisibility(View.GONE);
                pageData.setVisibility(View.VISIBLE);
                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                content.startAnimation(rotateAnimation);
            }
        });
    }

    /**
     * 3D旋转关闭效果，注意旋转角度
     */
    private void initCloseAnim() {
        closeAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, true);
        closeAnimation.setDuration(duration);
        closeAnimation.setFillAfter(true);
        closeAnimation.setInterpolator(new AccelerateInterpolator());
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                chart.setVisibility(View.VISIBLE);
                pageData.setVisibility(View.GONE);

                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                content.startAnimation(rotateAnimation);
            }
        });
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            queryTime = new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    .toString();
            initData();

        }
    };

}