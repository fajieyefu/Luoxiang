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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.main.ClueManageActivity;
import fajieyefu.com.luoxiang.main.ContractAnlysisActivity;
import fajieyefu.com.luoxiang.main.HistorySelectedActivity;
import fajieyefu.com.luoxiang.main.QueryOrderPcActivity;
import fajieyefu.com.luoxiang.main.ReportFormActivity;
import fajieyefu.com.luoxiang.nfc.ReadMUNfcActivity;
import fajieyefu.com.luoxiang.test.ContractInputPage1Test;

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
    @BindView(R.id.clueResource)
    RadioButton clueResource;
    @BindView(R.id.queryOrderPc)
    RadioButton queryOrderPc;
    @BindView(R.id.check_stock)
    RadioButton checkStock;
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
        context = getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.contract_input, R.id.contract_edit, R.id.contract_history, R.id.all_contract, R.id.clueResource, R.id.queryOrderPc,R.id.check_stock})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.contract_input:
                intent.setClass(context, ContractInputPage1Test.class);
                startActivity(intent);
                break;
            case R.id.contract_edit:
                Toast.makeText(context, context.getResources().getString(R.string.audit_in_computer), Toast.LENGTH_SHORT).show();
//                intent.setClass(context, ContractAuditActivity.class);
//                startActivity(intent);
                break;
            case R.id.contract_history:
                intent.setClass(context, HistorySelectedActivity.class);
                startActivity(intent);
                break;
            case R.id.all_contract:
                intent.setClass(context, ReportFormActivity.class);
                startActivity(intent);
//                Toast.makeText(context, "整改中，近期开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clueResource:
                intent.setClass(context, ClueManageActivity.class);
                startActivity(intent);
                break;
            case R.id.queryOrderPc:
                intent.setClass(context, QueryOrderPcActivity.class);
                startActivity(intent);
                break;
            case R.id.check_stock:
                intent.setClass(context, ReadMUNfcActivity.class);
                startActivity(intent);
                break;

        }
    }

}
