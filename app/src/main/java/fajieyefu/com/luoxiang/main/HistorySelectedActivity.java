package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ActionId;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.main.query.ContractQueryListActivity;

/**
 * Created by Administrator on 2017-05-13.
 */

public class HistorySelectedActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.inProcessContract)
    Button inProcessContract;
    @BindView(R.id.historyContract)
    Button historyContract;
    @BindView(R.id.invoiceManage)
    Button invoiceManage;
    public int flag = 0;
    @BindView(R.id.applyModify)
    Button applyModify;
    @BindView(R.id.contractQuery)
    Button contractQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_selected);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        title.setTitleText(getString(R.string.historyTyepSelect));
        invoiceManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistorySelectedActivity.this, InvoiceManageActivity.class);
                startActivity(intent);
            }
        });
        UserInfo userInfo = DaoBean.getUseInfoById(1);
        int rId = userInfo.getRId();
        if (DaoBean.getActionsById(51).size()>0){
            applyModify.setVisibility(View.VISIBLE);
            applyModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistorySelectedActivity.this, ApplyModifyManageActivity.class);
                    startActivity(intent);
                }
            });
        }

        /**
         * 是否有合同查询功能
         */
        if (DaoBean.getActionsById(29).size()>0){
            contractQuery.setVisibility(View.VISIBLE);
            contractQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistorySelectedActivity.this, ContractQueryListActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @OnClick({R.id.inProcessContract, R.id.historyContract})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.inProcessContract:
                flag = 0;
                break;
            case R.id.historyContract:
                flag = 1;
                break;

        }
        intent.putExtra("flag", flag);
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);

    }
}
