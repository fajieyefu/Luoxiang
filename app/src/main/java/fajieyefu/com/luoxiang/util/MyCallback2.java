package fajieyefu.com.luoxiang.util;

import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.ResponseBean2;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/30.
 */

public  abstract class MyCallback2 extends Callback<ResponseBean2> {
    @Override
    public ResponseBean2 parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.i("返回数据",string);
        ResponseBean2 reponseBean= new Gson().fromJson(string,ResponseBean2.class);
        return reponseBean;

    }



}
