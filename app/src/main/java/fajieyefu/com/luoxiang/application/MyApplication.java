package fajieyefu.com.luoxiang.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import fajieyefu.com.luoxiang.util.DaoManager;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.getInstance().init(getApplicationContext());
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//				.showImageOnFail(R.drawable.test)
//				.showImageOnFail(R.drawable.test)
                .cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();

        ImageLoader.getInstance().init(config);

    }
}
