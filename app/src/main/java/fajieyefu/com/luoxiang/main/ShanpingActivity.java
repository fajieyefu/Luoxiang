package fajieyefu.com.luoxiang.main;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.huawei.hms.support.api.push.TokenResult;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-06-05.
 */

public class ShanpingActivity extends BaseActivity {
    private Context context;
    private String apkUrl;
    private String updateMsg;
    private File apkFile;
    private int screenWidth;
    private int screenHeight;
    private UserInfo userInfo;
    private Intent intentLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shanping_layout);
        scaleImage(this, findViewById(R.id.rootView), R.drawable.rootview);
        initData();
        /*HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rst) {
            }
        });*/
        /**
         * 获取token
         */

    }


    private void initData() {
        int[] hm= new ToolUtil().getScreenWH(this);
        screenWidth=hm[0];
        screenHeight=hm[1];
        context = this;
        intentLogin = new Intent(context, LoginActivity.class);
        userInfo = DaoBean.getUseInfoById(1);
        checkApkVersion();
    }

    private void checkApkVersion() {
        OkHttpUtils.postString()
                .url(CommonData.UpdateApkURL)
                .content("")
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new ResponCallBack());
    }

    private class ResponCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(ShanpingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            context.startActivity(intentLogin);
            finish();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                apkUrl = response.getData().version.getUrl();
                String version_code = response.getData().version.getVersion();
                updateMsg = response.getData().version.getMessage();
                String versionName = "";
                try {
                    versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if (version_code.compareTo(versionName) >0 ) {
                    Dialog dialog = updateDialog(ShanpingActivity.this);
                    dialog.show();
                }else{


                    Timer time = new Timer();
                    TimerTask tk = new TimerTask() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            judgeLogin();

                        }
                    };
                    time.schedule(tk, 2000);




                }
            } else {
                Toast.makeText(ShanpingActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void judgeLogin() {
        if (userInfo!=null){
            String username = userInfo.getUsername();
            String pssword = userInfo.getPassword();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", pssword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OkHttpUtils.postString()
                    .url(CommonData.loginURL)
                    .content(jsonObject.toString())
                    .mediaType(MediaType.parse("application/json;charset=utf-8"))
                    .build()
                    .execute(new ReponseCallBack());
        }else{

            context.startActivity(intentLogin);
            finish();
        }

    }

    public Dialog updateDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.update_apk_dialog, null);
        final TextView content = (TextView) view.findViewById(R.id.content);
        Button update = (Button) view.findViewById(R.id.update);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        content.setText(updateMsg);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下载apk
                new UpdateApkAsyncTask(context).execute();
//                RxPermissions.getInstance(ShanpingActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
//                    if (granted) {
//                        Intent service = new Intent(ShanpingActivity.this, DownLoadService.class);
//                        service.putExtra("downloadurl", apkUrl);
//                        Toast.makeText(ShanpingActivity.this,"正在下载中",Toast.LENGTH_SHORT).show();
//                        startService(service);
//                    } else {
//                        Toast.makeText(ShanpingActivity.this,"SD卡下载权限被拒绝",Toast.LENGTH_SHORT).show();
//                    }
//                });


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    finish();

                }
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new ViewGroup.LayoutParams(screenWidth / 3 * 2, screenHeight / 3));
        dialog.setCanceledOnTouchOutside(false);

        return dialog;

    }

    private class UpdateApkAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        private Context mContext;
        private int file_size;//文件大小
        private int progress;
        private Dialog dialog;
        private ProgressBar progressBar;
        private Button setup;

        public UpdateApkAsyncTask(Context context) {
            mContext= context;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(mContext);
            View view = LayoutInflater.from(mContext).inflate(R.layout.update_progress_dialog, null);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            setup = (Button) view.findViewById(R.id.setup);
            setup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    installApk();//安装Apk
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view, new ViewGroup.LayoutParams(screenWidth/3*2
                    , screenHeight/3));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream in = null;
            FileOutputStream out = null;
            URL url = null;
            try {
                url = new URL(apkUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(false);
                urlConnection.setConnectTimeout(10 * 1000);
                urlConnection.setReadTimeout(10 * 1000);
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setRequestProperty("Charset", "UTF-8");
                urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                urlConnection.connect();
                file_size = urlConnection.getContentLength();
                long bytesum = 0;
                int byteread = 0;
                in = urlConnection.getInputStream();
                File dir = Environment.getExternalStorageDirectory();

                String apkName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1, apkUrl.length());
                apkFile = new File(dir, apkName);
                out = new FileOutputStream(apkFile);
                byte[] buffer = new byte[1024];
//				int oldProgress = 0;
                while ((byteread = in.read(buffer)) != -1) {
                    bytesum += byteread;
                    //计算进度条位置
                    progress = (int) (((float) bytesum / file_size) * 100);
                    publishProgress(progress);
                    out.write(buffer, 0, byteread);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setup.setVisibility(View.VISIBLE);
        }
    }

    private void installApk() {
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        if (!apkFile.exists()) {
            return;
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(context, "fajieyefu.com.luoxiang.fileprovider", apkFile);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            i.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        }else{
            i.setDataAndType(Uri.fromFile(apkFile), getMIMEType(apkFile));
        }
        context.startActivity(i);
      /*  // 通过Intent安装APK文件
        Log.i("APK", "apk文件存在");
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
    }
    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }
    public static void scaleImage(final Activity activity, final View view, int drawableResId) {

        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);

        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);

        if (resourceBitmap == null) {
            return;
        }

        // 开始对图片进行拉伸或者缩放

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;


                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;
                if (offset<0){
                    offset=1;
                }
                int height = (scaledBitmap.getHeight() - offset * 2)>0?(scaledBitmap.getHeight() - offset * 2):1;

                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(), height
                        );


                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }


                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), finallyBitmap));
                return true;
            }
        });
    }

    private class ReponseCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(context, "请检查网络是否畅通", Toast.LENGTH_SHORT).show();
            context.startActivity(intentLogin);
            finish();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            if (response.getCode() == 0) {
                Intent intent = new Intent(ShanpingActivity.this, MainActivity.class);
                startActivity(intent);
                initxiaomiPush();
                finish();

            } else {
                Toast.makeText(ShanpingActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                context.startActivity(intentLogin);
                finish();
            }

        }
    }
    private void initxiaomiPush() {
        if(shouldInit()) {
            MiPushClient.registerPush(this, CommonData.APP_ID, CommonData.APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(CommonData.TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(CommonData.TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
