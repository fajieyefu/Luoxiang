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
import fajieyefu.com.luoxiang.bean.ContractChange;

/**
 * Created by Administrator on 2017-08-08.
 */

public class ContractChangeAdapter extends BaseAdapter {
    private List<ContractChange> data;
    private Context context;

    public ContractChangeAdapter(List<ContractChange> data, Context context) {
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
        ContractChange contractChange = data.get(position);
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.contract_change_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.contractChangeContent.setText(contractChange.getChange_content());
        long createTime = Long.parseLong(contractChange.getCreate_time());
        String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(createTime);

        viewHolder.createTime.setText(time);
        viewHolder.amt.setText(contractChange.getAmt()+"");
        viewHolder.amtDx.setText(contractChange.getAmt_dx()+"");
        switch (contractChange.getNq_flag()){
            case 0:
                viewHolder.nqFlag.setText("待审核");
                break;
            case 1:
                viewHolder.nqFlag.setText("通过");
                break;
            case 2:
                viewHolder.nqFlag.setText("退回");
                break;
        }
        switch (contractChange.getPro_flag()){
            case 0:
                viewHolder.proFlag.setText("待审核");
                break;
            case 1:
                viewHolder.proFlag.setText("通过");
                break;
            case 2:
                viewHolder.proFlag.setText("退回");
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.createTime)
        TextView createTime;
        @BindView(R.id.contractChangeContent)
        TextView contractChangeContent;
        @BindView(R.id.amt)
        TextView amt;
        @BindView(R.id.amt_dx)
        TextView amtDx;
        @BindView(R.id.nq_flag)
        TextView nqFlag;
        @BindView(R.id.pro_flag)
        TextView proFlag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
