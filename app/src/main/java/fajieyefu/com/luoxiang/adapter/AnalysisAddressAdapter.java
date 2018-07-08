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

/**
 * Created by Administrator on 2017-06-04.
 */

public class AnalysisAddressAdapter extends BaseAdapter {
    private Context context;
    private List<AnalysisAddressBean> data;

    public AnalysisAddressAdapter(Context context, List<AnalysisAddressBean> data) {
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
        AnalysisAddressBean bean = data.get(position);
        if (convertView== null){
            convertView = LayoutInflater.from(context).inflate(R.layout.analysis_address_item, null);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.address.setText(bean.getAddress());
        viewHolder.counts.setText(bean.getCounts()+"");
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.counts)
        TextView counts;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
