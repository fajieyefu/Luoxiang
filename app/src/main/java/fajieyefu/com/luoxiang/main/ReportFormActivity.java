package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.reportForm.ClueAnalysisByAreaActivity;
import fajieyefu.com.luoxiang.reportForm.ClueAnalysisByPersonActivity;

/**
 * Created by meng on 2018/4/8.
 */

public class ReportFormActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.clue_area)
    Button clueArea;
    @BindView(R.id.clue_person)
    Button cluePerson;
    @BindView(R.id.clue_data)
    Button clueData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form_select_layout);
        ButterKnife.bind(this);
        initView();
        addClickListener();
    }

    private void addClickListener() {
        clueArea.setOnClickListener(this);
        cluePerson.setOnClickListener(this);
        clueData.setOnClickListener(this);
    }

    private void initView() {
        title.setTitleText("报表中心");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.clue_area:
                intent.setClass(this,ClueAnalysisByAreaActivity.class);
                break;
            case R.id.clue_person:
                intent.setClass(this, ClueAnalysisByPersonActivity.class);
                break;
            case R.id.clue_data:
                break;
        }
        startActivity(intent);
    }
}
