package fajieyefu.com.luoxiang.application;

import android.app.Application;

import fajieyefu.com.luoxiang.util.DaoManager;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.getInstance().init(getApplicationContext());

    }
}
