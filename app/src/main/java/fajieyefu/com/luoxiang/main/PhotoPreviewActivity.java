package fajieyefu.com.luoxiang.main;

import android.os.Bundle;


import java.util.List;

import fajieyefu.com.luoxiang.control.PhotoSelectorDomain;
import fajieyefu.com.luoxiang.model.PhotoModel;
import fajieyefu.com.luoxiang.util.StringUtils;


/**
 * Created by fengyongge on 2016/5/24
 * 图片预览
 */
public class PhotoPreviewActivity extends BasePhotoPreviewActivity implements PhotoSelectorActivity.OnLocalReccentListener {
	private PhotoSelectorDomain photoSelectorDomain;
	private boolean isSave;//是否保存图片


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
		init(getIntent().getExtras());
	}

	@SuppressWarnings("unchecked")
	protected void init(Bundle extras) {
		if (extras == null)
			return;

		isSave = extras.getBoolean("isSave",false);

		if (extras.containsKey("photos")) { // 预览图片，选择需要的图片

			this.photos = (List<PhotoModel>) extras.getSerializable("photos");
			this.current = extras.getInt("position", 0);

			if(isSave){ // 是否保存（一般保存网络图片，本地图片只能查看）
				bindData(true);
				updatePercent();
			}else{
				bindData(false);
			}


		} else if (extras.containsKey("album")) { // 点击图片查看
			String albumName = extras.getString("album"); // 相册
			this.current = extras.getInt("position");
			if (!StringUtils.isNull(albumName) && albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)) {
				photoSelectorDomain.getReccent(this);
			} else {
				photoSelectorDomain.getAlbum(albumName, this);
			}
		}
	}


	@Override
	public void onPhotoLoaded(List<PhotoModel> photos) {
		this.photos = photos;
		updatePercent();
		bindData(false); // 更新界面
	}


}
