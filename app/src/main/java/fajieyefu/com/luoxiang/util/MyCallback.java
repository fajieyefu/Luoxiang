package fajieyefu.com.luoxiang.util;

import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import fajieyefu.com.luoxiang.bean.ReponseBean;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/30.
 */

public  abstract class MyCallback extends Callback<ReponseBean> {
    @Override
    public ReponseBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.i("返回数据",string);
        ReponseBean reponseBean= new Gson().fromJson(string,ReponseBean.class);
        return reponseBean;
    }



}
