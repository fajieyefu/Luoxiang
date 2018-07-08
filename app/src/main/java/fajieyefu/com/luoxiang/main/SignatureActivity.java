package fajieyefu.com.luoxiang.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.DrawView;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;

/**
 * Created by qiancheng on 2016/10/17.
 */
public class SignatureActivity extends BaseActivity implements View.OnClickListener {
	private Button save,back,clear;
	private DrawView drawView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signature_layout);
		drawView= (DrawView) findViewById(R.id.drawView);
		save= (Button) findViewById(R.id.save);
		back= (Button) findViewById(R.id.back);
		clear= (Button) findViewById(R.id.clear);
		save.setOnClickListener(this);
		back.setOnClickListener(this);
		clear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.save:
				try {
					String sdState = Environment.getExternalStorageState(); // 判断sd卡是否存在
					// 检查SD卡是否可用
					if (!sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
						Toast.makeText(this, "SD卡未准备好！", Toast.LENGTH_SHORT).show();
						break;
					}
					// 获取系统图片存储路径

					File path = StorageUtils.getCacheDirectory(this);
					Log.i("signatureActivity", "getExternalStoragePublicDirectory = "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
					if (path.exists()){
						Log.i("存放路径；","路径存在");
					}
					if (!path.exists()) {
						path.mkdirs();
					}
					// 根据用户名生成图片名称
					UserInfo userInfo =DaoBean.getUseInfoById(1);
					String username =userInfo.getUsername();
					String name = username+".png";
					// 合成完整路径，注意 / 分隔符
					String image_path = path.getPath() + "/" + name;
					SharedPreferences sps = getSharedPreferences("image_info",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = sps.edit();
					editor.putString("image_path", image_path);
					editor.putString("image_name", name);
					editor.apply();
					drawView.saveToFile(name);
					SignatureActivity.this.finish();
				} catch (FileNotFoundException e) {
					Toast.makeText(this, "保存失败！\n" + e, Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.clear:
				drawView.clearSignature();
				break;
			case R.id.back:
				finish();
				break;
		}

	}
}
