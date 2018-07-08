package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.PictureBean;
import fajieyefu.com.luoxiang.layout.CustomImageView;
import fajieyefu.com.luoxiang.layout.NineGridlayout;
import fajieyefu.com.luoxiang.main.PhotoPreviewActivity;
import fajieyefu.com.luoxiang.model.PhotoModel;
import fajieyefu.com.luoxiang.util.CommonUtils;

/**
 * Created by Administrator on 2017-10-14.
 */

public class PicturePreviewAdapter extends BaseAdapter {
    private List<PictureBean> list ;
    private Context context;
    private String title;

    public PicturePreviewAdapter(Context context, List<PictureBean> list,String title) {
        this.context = context;
        this.list = list;
        this.title=title;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int postion) {
        return list.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(final int positon, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_pre_view_item, null);
            holder = new ViewHolder();
            holder.preview_title= (TextView) convertView.findViewById(R.id.pre_view_title);
            holder.iv_oneimage  = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            holder.iv_ngrid_layout  = (NineGridlayout)convertView. findViewById(R.id.iv_ngrid_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.preview_title.setText(title);
        if (listIsEmpty(list.get(positon).getPic())) {
            holder.iv_ngrid_layout.setVisibility(View.GONE);
            holder.iv_oneimage.setVisibility(View.GONE);
        } else {

            if (list.get(positon).getPic().size() == 1) {

                holder.iv_oneimage.setTag(list.get(positon).getPic());
                holder.iv_oneimage.setVisibility(View.VISIBLE);
                holder.iv_ngrid_layout.setVisibility(View.GONE);
                holder.iv_oneimage.setImageUrl(list.get(positon).getPic().get(0));

                holder.iv_oneimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        List<PhotoModel> tempList = new ArrayList<PhotoModel>();

                        for (int i = 0; i <list.get(positon).getPic().size() ; i++) {
                            PhotoModel photoModel = new PhotoModel();
                            photoModel.setOriginalPath(list.get(positon).getPic().get(i));
                            tempList.add(photoModel);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos",(ArrayList<PhotoModel>)tempList);
                        bundle.putInt("position", 0);
                        bundle.putBoolean("isSave",true);
                        CommonUtils.launchActivity(context, PhotoPreviewActivity.class, bundle);
                    }
                });
            } else {
                holder.iv_oneimage.setVisibility(View.GONE);
                holder.iv_ngrid_layout.setVisibility(View.VISIBLE);
                final ArrayList<String> list1 = (ArrayList<String>) list.get(positon).getPic();

                List<PhotoModel> tempList = new ArrayList<PhotoModel>();

                for (int i = 0; i <list1.size() ; i++) {
                    PhotoModel photoModel = new PhotoModel();
                    photoModel.setOriginalPath(list.get(positon).getPic().get(i));
                    tempList.add(photoModel);
                }

                holder.iv_ngrid_layout.setImagesData(tempList,context);
            }
        }
        return convertView;
    }


    class ViewHolder{
        CustomImageView iv_oneimage;
        NineGridlayout iv_ngrid_layout;
        TextView preview_title;
    }

    private boolean listIsEmpty(List list) {
        if (list != null && list.size() > 0)
            return false;
        return true;
    }
}
