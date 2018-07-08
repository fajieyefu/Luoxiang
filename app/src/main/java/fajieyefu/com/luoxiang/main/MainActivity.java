package fajieyefu.com.luoxiang.main;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.fragment.BangongFragment;
import fajieyefu.com.luoxiang.fragment.PersonFragment;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity  implements EasyPermissions.PermissionCallbacks{


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
//        initHuaWeiPush();
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

            // 已经申请过权限，做想做的事
        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "申请读写权限，部分功能受限",
                    0, perms);
        }

    }

    private void initHuaWeiPush() {
        HMSAgent.connect(this, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {

            }
        });
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override //申请成功时调用
    public void onPermissionsGranted(int requestCode, List<String> list) {
        //请求成功执行相应的操作

        switch (requestCode){
            case 0:
                Toast.makeText(this, "已获取WRITE_EXTERNAL_STORAGE权限", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
