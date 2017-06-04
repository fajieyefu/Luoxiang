package fajieyefu.com.luoxiang.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.main.ContractAnlysisActivity;
import fajieyefu.com.luoxiang.main.ContractAuditActivity;
import fajieyefu.com.luoxiang.main.ContractInputPage1;
import fajieyefu.com.luoxiang.main.HistoryActivity;
import fajieyefu.com.luoxiang.main.HistorySelectedActivity;

/**
 * Created by Administrator on 2017/4/14.
 */

public class BangongFragment extends Fragment {
    @BindView(R.id.contract_input)
    RadioButton contractInput;
    @BindView(R.id.contract_edit)
    RadioButton contractEdit;
    @BindView(R.id.contract_history)
    RadioButton contractHistory;
    @BindView(R.id.all_contract)
    RadioButton allContract;
    Unbinder unbinder;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bangong, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        context= getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.contract_input, R.id.contract_edit, R.id.contract_history, R.id.all_contract})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.contract_input:
                intent.setClass(context, ContractInputPage1.class);
                startActivity(intent);
                break;
            case R.id.contract_edit:
                intent.setClass(context, ContractAuditActivity.class);
                startActivity(intent);
                break;
            case R.id.contract_history:
                intent.setClass(context, HistorySelectedActivity.class);
                startActivity(intent);
                break;
            case R.id.all_contract:
                intent.setClass(context,ContractAnlysisActivity.class);
                startActivity(intent);
                break;
        }
    }
}
