package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.AnalysisAddressBean;
import fajieyefu.com.luoxiang.bean.ClueAnalysisBean;

/**
 * Created by Administrator on 2017-06-04.
 */

public class ClueAnalysisAdapter extends BaseAdapter {
    private Context context;
    private List<ClueAnalysisBean> data;

    public ClueAnalysisAdapter(Context context, List<ClueAnalysisBean> data) {
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
        ViewHolder viewHolder;
        ClueAnalysisBean bean = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.analysis_clue_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.name.setText(bean.getName());
        viewHolder.total.setText(bean.getTotal() + "");
        viewHolder.yes.setText(bean.getYes()+"");
        viewHolder.followPer.setText(bean.getFollow_per()+"%");
        viewHolder.success.setText(bean.getSuccess()+"");
        viewHolder.successPer.setText(bean.getSuccess_per()+"%");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.total)
        TextView total;
        @BindView(R.id.yes)
        TextView yes;
        @BindView(R.id.follow_per)
        TextView followPer;
        @BindView(R.id.success)
        TextView success;
        @BindView(R.id.success_per)
        TextView successPer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
