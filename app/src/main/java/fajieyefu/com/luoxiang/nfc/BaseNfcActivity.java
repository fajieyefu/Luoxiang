package fajieyefu.com.luoxiang.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;

import fajieyefu.com.luoxiang.main.BaseActivity;

/**
 * Created by meng on 2017/12/28.
 */

public class BaseNfcActivity extends BaseActivity {
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    //启动Activity,界面可见时
    @Override
    protected void onStart() {
        super.onStart();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this,0,new Intent(this,getClass()),0);

    }
    //获得焦点，按钮可点击时
    @Override
    protected void onResume() {
        super.onResume();
        //设置处理优于所有其他NFC的处理
        if (mNfcAdapter !=null){
            mNfcAdapter.enableForegroundDispatch(this,mPendingIntent,null,null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //恢复默认状态
        if (mNfcAdapter!=null){
            mNfcAdapter.disableForegroundDispatch(this);
        }

    }
    protected void diableDispatch(){
        mNfcAdapter.disableForegroundDispatch(this);
    }
    protected void enableDispatch(){
        mNfcAdapter.enableForegroundDispatch(this,mPendingIntent,null,null);
    }
}
