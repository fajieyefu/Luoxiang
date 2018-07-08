package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import fajieyefu.com.luoxiang.model.AlbumModel;
import fajieyefu.com.luoxiang.view.AlbumItem;


/**
 * Created by fengyongge on 2016/5/24
 */
public class AlbumAdapter extends MBaseAdapter<AlbumModel> {
	public AlbumAdapter(Context context, ArrayList<AlbumModel> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumItem albumItem = null;
		if (convertView == null) {
			albumItem = new AlbumItem(context);
			convertView = albumItem;
		} else
			albumItem = (AlbumItem) convertView;
		albumItem.update(models.get(position));
		return convertView;
	}

}
