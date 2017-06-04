package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ContractBean;

/**
 * Created by Administrator on 2017-05-08.
 */
public class HistoryAdapter extends BaseAdapter {
    private List<ContractBean> data;
    private Context context;

    public HistoryAdapter(List<ContractBean> data, Context context) {
        this.data = data;
        this.context = context;
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
        ContractBean contractBean = data.get(position);
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_contract_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (contractBean.getStatues()){
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_0));
                break;
            case 2:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_1));
                break;
            case 3:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
                break;
        }
        viewHolder.contractId.setText(contractBean.getcDCCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.applyDate.setText(sdf.format(contractBean.getCreatetime()));
        viewHolder.customer.setText(contractBean.getOrderName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.dot)
        ImageView dot;
        @BindView(R.id.contractId)
        TextView contractId;
        @BindView(R.id.customer)
        TextView customer;
        @BindView(R.id.apply_date)
        TextView applyDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
