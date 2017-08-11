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
    private ContractBean contractBean;
    private ViewHolder viewHolder;

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
        contractBean = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_contract_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (contractBean.getWc()) {
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_5));
                viewHolder.nqFlag.setText("完成");
                break;
            case 0:
                isDTC();
                break;

        }
        viewHolder.contractId.setText(contractBean.getOrderNumber());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.applyDate.setText(sdf.format(contractBean.getCreatetime()));

        viewHolder.customer.setText(contractBean.getcCusName());
        return convertView;
    }

    private void isDDPC() {
        switch (contractBean.getDpc()) {
            case 0:
                isRefuse();
                break;
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_3));
                viewHolder.nqFlag.setText("等待排产");
                break;
        }
    }

    private void isRefuse() {
        switch (contractBean.getNq_flag()) {
            case 2:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
                viewHolder.nqFlag.setText("退回修改");
                break;
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_1));
                viewHolder.nqFlag.setText("审核通过");
                break;
            case 0:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_0));
                viewHolder.nqFlag.setText("待审核");
                break;
        }
    }

    private void isDTC() {
        switch (contractBean.getDdtc()) {
            case 0:
                isDDPC();
                break;
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_4));
                viewHolder.nqFlag.setText("等待提车");
                break;
        }
    }

    static class ViewHolder {
        @BindView(R.id.dot)
        ImageView dot;
        @BindView(R.id.contractId)
        TextView contractId;
        @BindView(R.id.apply_date)
        TextView applyDate;
        @BindView(R.id.nq_flag)
        TextView nqFlag;
        @BindView(R.id.customer)
        TextView customer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
