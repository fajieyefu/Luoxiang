package fajieyefu.com.luoxiang.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.fragment.BangongFragment;
import fajieyefu.com.luoxiang.fragment.PersonFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.bangong)
    RadioButton bangong;
    @BindView(R.id.person)
    RadioButton person;
    private Map<Integer, Fragment> map = new HashMap<Integer, Fragment>();
    Fragment bangongFragment;
    private Fragment personFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bangong.setChecked(true);
        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        initxiaomiPush();
    }

    private void initxiaomiPush() {

    }



    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bangongFragment = new BangongFragment();
        map.put(1, bangongFragment);
        transaction.replace(R.id.content, bangongFragment);
        transaction.commit();
    }


    @OnClick({R.id.bangong, R.id.person})
    public void onViewClicked(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Map.Entry<Integer, Fragment> entry : map.entrySet()) {
            transaction.hide(entry.getValue());
        }
        switch (view.getId()) {
            case R.id.bangong:

                if (bangongFragment == null) {
                    bangongFragment = new BangongFragment();
                    map.put(1, bangongFragment);
                    transaction.add(R.id.content, bangongFragment);

                } else {

                    transaction.show(bangongFragment);
                }
                break;
            case R.id.person:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    map.put(2, personFragment);
                    transaction.add(R.id.content, personFragment);

                } else {

                    transaction.show(personFragment);
                    transaction.hide(bangongFragment);
                }
                break;
        }
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
