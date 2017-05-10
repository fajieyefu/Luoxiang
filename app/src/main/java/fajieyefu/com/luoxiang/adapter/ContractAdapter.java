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
import fajieyefu.com.luoxiang.bean.ContractBean;

/**
 * Created by Administrator on 2017-05-08.
 */
public class ContractAdapter extends BaseAdapter {
    private List<ContractBean> data;
    private Context context;

    public ContractAdapter(List<ContractBean> data, Context context) {
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
        ViewHolder viewHolder =null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.contract_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.contractId.setText(contractBean.getcDCCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.applyDate.setText(sdf.format(contractBean.getCreatetime()));
        viewHolder.customer.setText(sdf.format(contractBean.getOrderName()));
        return convertView;
    }

    static class ViewHolder {
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
