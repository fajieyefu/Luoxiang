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
        if (contractBean.getNq_flag()==0){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_0));
            viewHolder.nqFlag.setText(context.getResources().getString(R.string.wait_audit));
        }else if (contractBean.getNq_flag()==2){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
            viewHolder.nqFlag.setText(context.getResources().getString(R.string.back_audit));
        }else if (contractBean.getNq_flag()==3){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
            viewHolder.nqFlag.setText("作废");
        }
        else if (contractBean.getNq_flag()==1&&contractBean.getDdtc()==0){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_3));
            viewHolder.nqFlag.setText(context.getResources().getString(R.string.ddpc));
        }else if (contractBean.getDdtc()==1&&contractBean.getWc()==0){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_4));
            viewHolder.nqFlag.setText(context.getResources().getString(R.string.dtc));
        }else if (contractBean.getWc()==1){
            viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_5));
            viewHolder.nqFlag.setText(context.getResources().getString(R.string.complete));
        }
         if (contractBean.getStop_flag()==1){
             viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
             viewHolder.nqFlag.setText("生产驳回");
         }
        viewHolder.contractId.setText(contractBean.getOrderNumber());
        viewHolder.endCustomerName.setText(contractBean.getEndCustomerName()==null?"未填写":contractBean.getEndCustomerName());


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
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.ddpc));
                break;
        }
    }

    private void isRefuse() {
        switch (contractBean.getNq_flag()) {
            case 2:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_2));
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.back_audit));
                break;
            case 1:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_1));
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.ok));
                break;
            case 0:
                viewHolder.dot.setBackground(context.getResources().getDrawable(R.drawable.dot_0));
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.wait_audit));
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
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.dtc));
                break;
        }
    }

    static class ViewHolder {
        @BindView(R.id.dot)
        ImageView dot;
        @BindView(R.id.contractId)
        TextView contractId;
        @BindView(R.id.endCustomerName)
        TextView endCustomerName;
        @BindView(R.id.nq_flag)
        TextView nqFlag;
        @BindView(R.id.customer)
        TextView customer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
