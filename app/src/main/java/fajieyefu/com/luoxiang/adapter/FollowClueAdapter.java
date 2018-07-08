package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ClueFollowBean;

/**
 * Created by Administrator on 2017-10-19.
 */

public class FollowClueAdapter extends BaseAdapter {
    private Context context;
    private List<ClueFollowBean> data;

    public FollowClueAdapter(Context context, List<ClueFollowBean> data) {
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
        ClueFollowBean clueFollowBean = data.get(position);
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_clue_item, null);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        long times = Long.parseLong(clueFollowBean.getCreate_time());
            viewHolder.followContent.setText(clueFollowBean.getFollow_content());
            viewHolder.followTime.setText(new SimpleDateFormat("yyy-MM-dd HH:mm").format(times));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.follow_content)
        TextView followContent;
        @BindView(R.id.follow_time)
        TextView followTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
