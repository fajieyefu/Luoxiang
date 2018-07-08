package fajieyefu.com.luoxiang.reportForm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import fajieyefu.com.luoxiang.adapter.ClueAnalysisAdapter;
import fajieyefu.com.luoxiang.bean.ClueAnalysisBean;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.Option;
import fajieyefu.com.luoxiang.bean.OptionItem;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.Rotate3dAnimation;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MySpinnerForCommon;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.main.BaseActivity;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by meng on 2018/4/8.
 */

public class ClueAnalysisByPersonActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.page_data)
    LinearLayout pageData;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.chart)
    LineChartView chart;
    UserInfo userInfo;
    private Button more;
    private TextView titleText;
    private DatePickerDialog datePicker_start, datePicker_end;
    private int mYear, mMonth, mDay;
    private String start_time, end_time;
    private ToolUtil toolUtil;
    private int centerX, centerY;
    private int depthZ = 400;
    private boolean isOpen = false;
    private Rotate3dAnimation openAnimation, closeAnimation;
    private int duration = 600;
    private Dialog dialog;
    private MySpinnerForCommon rankType;
    private MySpinnerForCommon followTime;
    private EditText startTimeEdit, endTimeEdit;
    private List<Inventory> rank_type_list = new ArrayList<>();
    private List<Inventory> follow_time_list = new ArrayList<>();
    private List<ClueAnalysisBean> clueData = new ArrayList<>();
    private ClueAnalysisAdapter clueAnalysisAdapter;

    private String[] xAxis;
    private int[] yAxis;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();
    private String rank_type = "follow_per";
    private String follow_time="30";
    private String rank_type_name = "跟进率";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clue_area_analysis_layout);
        ButterKnife.bind(this);
        initView();
        initData();





    }

    /**
     * 设置X轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < xAxis.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(xAxis[i]));
        }
        for (int i=0; i<100;i++){
            mAxisYValues.add(new AxisValue(i).setValue(i));
        }
    }

    /**
     ** 图表的每个点的显示
     */
    private void getAxisPoints() {
        mPointValues.clear();
        for (int i = 0; i < yAxis.length; i++) {
            mPointValues.add(new PointValue(i, yAxis[i]));
        }
    }

    private void initData() {
        userInfo = DaoBean.getUseInfoById(1);
        JSONObject content = new JSONObject();
        try {
            content.put("username", userInfo.getUsername());
            content.put("password", userInfo.getPassword());
            content.put("start_time", start_time);
            content.put("end_time", end_time);
            content.put("rank_type", rank_type);
            content.put("check_status", follow_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        OkHttpUtils.postString()
                .url(CommonData.getClueDataByPerson)
                .content(content.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponseCallBack());
    }

    private void initView() {


        more = (Button) title.findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        more.setOnClickListener(this);
        title.setTitleText("业务员跟进电话信息情况分析");
        titleText = (TextView) title.findViewById(R.id.titleText);
        titleText.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        start_time = mYear + "-" + (mMonth + 1 < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "-" + (mDay < 10 ? ("0" + mDay) : mDay);
        end_time = mYear + "-" + (mMonth + 1 < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "-" + (mDay < 10 ? ("0" + mDay) : mDay);
        toolUtil = new ToolUtil();
        datePicker_start = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, mDateSetListener_start, mYear, mMonth, mDay);
        datePicker_end = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, mDateSetListener_end, mYear, mMonth, mDay);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                initFilterDialog();
                break;
            case R.id.titleText:
                AnimView();
                break;
            case R.id.start_time:
                datePicker_start.show();
                break;
            case R.id.end_time:
                datePicker_end.show();
                break;
            case R.id.close:
                dialog.dismiss();
                break;
            case R.id.commit:
                rank_type = rankType.getSelected_code();
                follow_time = followTime.getSelected_code();
                rank_type_name=rankType.getText();
                initData();
                dialog.dismiss();
                break;

        }
    }

    private void initFilterDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.clue_fiter_layout, null);
            TextView close = (TextView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            Button commit = (Button) view.findViewById(R.id.commit);
            commit.setOnClickListener(this);
            startTimeEdit = (EditText) view.findViewById(R.id.start_time);
            startTimeEdit.setOnClickListener(this);
            endTimeEdit = (EditText) view.findViewById(R.id.end_time);
            endTimeEdit.setOnClickListener(this);
            //初始开始日期和截至日期为当天
            startTimeEdit.setText(start_time);
            endTimeEdit.setText(end_time);
            rankType = (MySpinnerForCommon) view.findViewById(R.id.rank_type);
            followTime = (MySpinnerForCommon) view.findViewById(R.id.follow_time);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rankType.setData(rank_type_list);
            followTime.setData(follow_time_list);

        } else {
            dialog.show();
        }


    }

    private class ResponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ClueAnalysisByPersonActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                List<Option> options = response.getData().optionItems;
                rank_type_list.clear();
                follow_time_list.clear();
                for (Option option : options) {
                    //排序方式
                    if (option.getOption_code() == 17) {
                        for (OptionItem optionItem : option.getOptionItems()) {
                            Inventory inventory = new Inventory();
                            inventory.setCInvCode(optionItem.getItem_content());
                            inventory.setCInvName(optionItem.getItem_name());
                            inventory.setCInvCCode("17");
                            rank_type_list.add(inventory);
                        }
                    }
                    //跟进频率
                    if (option.getOption_code() == 18) {
                        for (OptionItem optionItem : option.getOptionItems()) {
                            Inventory inventory = new Inventory();
                            inventory.setCInvCode(optionItem.getItem_content());
                            inventory.setCInvName(optionItem.getItem_name());
                            inventory.setCInvCCode("18");
                            follow_time_list.add(inventory);
                        }
                    }
                }
                clueData = response.getData().clue_analysis_data;
                clueAnalysisAdapter = new ClueAnalysisAdapter(ClueAnalysisByPersonActivity.this, clueData);
                listview.setAdapter(clueAnalysisAdapter);
                xAxis = new String[clueData.size()];
                yAxis = new int[clueData.size()];
                for (int i = 0; i < clueData.size(); i++) {
                    xAxis[i] = clueData.get(i).getName();
                    switch (rank_type) {
                        case "fail_per":
                            yAxis[i] = clueData.get(i).getFail_per();
                            break;
                        case "success_per":
                            yAxis[i] = clueData.get(i).getSuccess_per();
                            break;
                        case "follow_per":
                            yAxis[i] = clueData.get(i).getFollow_per();
                            break;
                    }


                }
                initLineChart();//初始化

            } else {
                Toast.makeText(ClueAnalysisByPersonActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

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

    private DatePickerDialog.OnDateSetListener mDateSetListener_start = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            start_time = new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    .append("-")
                    .append((mDay) < 10 ? "0" + mDay : mDay)
                    .toString();
            startTimeEdit.setText(start_time);

        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener_end = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            end_time = new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    .append("-")
                    .append((mDay) < 10 ? "0" + mDay : mDay)
                    .toString();
            endTimeEdit.setText(end_time);

        }
    };

    private void initLineChart() {
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        axisX.setName("所选日期"+rank_type_name+"排名");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setValues(mAxisYValues);
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        chart.setMaxZoom((float) 2);//最大方法比例
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chart.setLineChartData(data);
        chart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(chart.getMaximumViewport());
        v.left = 0;
        v.right = 20;
        chart.setCurrentViewport(v);
    }
}
