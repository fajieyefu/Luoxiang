package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ProNumBean;

/**
 * Created by Administrator on 2017-09-26.
 */

public class ProNumAdapter extends BaseAdapter {
    private List<ProNumBean> data;
    private Context context;

    public ProNumAdapter(Context context, List<ProNumBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProNumBean proNumBean = data.get(position);
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.query_pro_num_item,null);
            viewHolder.proDateTV = (TextView) convertView.findViewById(R.id.pro_date);
            viewHolder.normalNumTV = (TextView) convertView.findViewById(R.id.normal_num);
            viewHolder.skeletonNumTV = (TextView) convertView.findViewById(R.id.skeleton_num);
            viewHolder.urgentNumTV = (TextView) convertView.findViewById(R.id.urgent_num);
            viewHolder.firstNumTV = (TextView) convertView.findViewById(R.id.first_num);
            viewHolder.itemLayout= (LinearLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position%2==0){
            viewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.ivory));
        }else{
            viewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.lightyellow));
        }
        viewHolder.proDateTV.setText(proNumBean.getPro_date());
        if (proNumBean.getNormal_num()>proNumBean.getNormal_flag()&&proNumBean.getEnable_flag()==1){
            viewHolder.normalNumTV.setText(proNumBean.getNormal_num()-proNumBean.getNormal_flag()+"");
        }else{
            viewHolder.normalNumTV.setText("0");
        }
        if (proNumBean.getUrgent_num()>proNumBean.getUrgent_flag()&&proNumBean.getEnable_flag()==1){
            viewHolder.urgentNumTV.setText(proNumBean.getUrgent_num()-proNumBean.getUrgent_flag()+"");
        }else{
            viewHolder.urgentNumTV.setText("0");
        }
        if (proNumBean.getFirst_num()>proNumBean.getFirst_flag()&&proNumBean.getEnable_flag()==1){
            viewHolder.firstNumTV.setText(proNumBean.getFirst_num()-proNumBean.getFirst_flag()+"");
        }else{
            viewHolder.firstNumTV.setText("0");
        }
        if (proNumBean.getSkeleton_num()>proNumBean.getSkeleton_flag()&&proNumBean.getEnable_flag()==1){
            viewHolder.skeletonNumTV.setText(proNumBean.getSkeleton_num()-proNumBean.getSkeleton_flag()+"");
        }else{
            viewHolder.skeletonNumTV.setText("0");
        }
        return  convertView;

    }
    class ViewHolder{
        TextView proDateTV;
        TextView normalNumTV;
        TextView skeletonNumTV;
        TextView urgentNumTV;
        TextView firstNumTV;
        LinearLayout itemLayout;

    }
}
