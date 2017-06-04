package fajieyefu.com.luoxiang.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.main.BaseActivity;
import fajieyefu.com.luoxiang.main.ModifyActivity;

/**
 * Created by Administrator on 2017/4/15.
 */

public class PersonFragment extends Fragment {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.employee_code)
    TextView employeeCode;
    @BindView(R.id.modify)
    TextView modify;
    @BindView(R.id.exit)
    TextView exit;
    Unbinder unbinder;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context= getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.modify, R.id.exit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.modify:
                intent.setClass(context, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
    }
}
