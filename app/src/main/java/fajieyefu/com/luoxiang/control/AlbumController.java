package fajieyefu.com.luoxiang.control;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fajieyefu.com.luoxiang.model.AlbumModel;
import fajieyefu.com.luoxiang.model.PhotoModel;


/**
 * Created by fengyongge on 2016/5/24
 */
public class AlbumController {

	private ContentResolver resolver;
	public AlbumController(Context context) {
		resolver = context.getContentResolver();
	}

	/** 获取最近照片列表 */
	public List<PhotoModel> getCurrent() {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { MediaColumns.DATA,
				MediaColumns.DATE_ADDED, MediaColumns.SIZE }, null, null, MediaColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(MediaColumns.SIZE)) > 1024) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(MediaColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}

	/** 获取所有相册列表 */
	public List<AlbumModel> getAlbums() {
		List<AlbumModel> albums = new ArrayList<AlbumModel>();
		Map<String, AlbumModel> map = new HashMap<String, AlbumModel>();
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { MediaColumns.DATA,
				ImageColumns.BUCKET_DISPLAY_NAME, MediaColumns.SIZE }, null, null, null);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<AlbumModel>();
		cursor.moveToLast();
		AlbumModel current = new AlbumModel("最近照片", 0, cursor.getString(cursor.getColumnIndex(MediaColumns.DATA)), true); // "最近照片"相册
		albums.add(current);
		do {
			if (cursor.getInt(cursor.getColumnIndex(MediaColumns.SIZE)) < 1024)
				continue;
			current.increaseCount();
			String name = cursor.getString(cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
			if (map.keySet().contains(name))
				map.get(name).increaseCount();
			else {
				AlbumModel album = new AlbumModel(name, 1, cursor.getString(cursor.getColumnIndex(MediaColumns.DATA)));
				map.put(name, album);
				albums.add(album);
			}
		} while (cursor.moveToPrevious());
		return albums;
	}

	/** 获取对应相册下的照片 */
	public List<PhotoModel> getAlbum(String name) {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.BUCKET_DISPLAY_NAME,
				MediaColumns.DATA, MediaColumns.DATE_ADDED, MediaColumns.SIZE }, "bucket_display_name = ?",
				new String[] { name }, MediaColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(MediaColumns.SIZE)) > 1024) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(MediaColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}
}
