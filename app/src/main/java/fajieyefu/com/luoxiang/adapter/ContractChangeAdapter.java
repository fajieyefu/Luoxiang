package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.text.TextUtils;
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
        String obtain_type="";
        if (!TextUtils.isEmpty(contractChange.getObtain_type())){
            obtain_type="\n变更提车方式为："+contractChange.getObtain_type();
        }
        viewHolder.contractChangeContent.setText(contractChange.getChange_content()+obtain_type);
        long createTime = Long.parseLong(contractChange.getCreate_time());
        String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(createTime);
        String nq_mark = TextUtils.isEmpty(contractChange.getNq_mark())?"":" "+contractChange.getNq_mark();
        String pro_mark = TextUtils.isEmpty(contractChange.getNq_mark())?"":" "+contractChange.getPro_mark();
        String zp_mark = TextUtils.isEmpty(contractChange.getNq_mark())?"":" "+contractChange.getZp_mark();


        viewHolder.createTime.setText(time);
        viewHolder.amt.setText(contractChange.getAmt()+"");
        viewHolder.amtDx.setText(contractChange.getAmt_dx()+"");
        switch (contractChange.getNq_flag()){
            case 0:
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.dsh)+nq_mark);
                break;
            case 1:
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.tongguo)+nq_mark);
                break;
            case 2:
                viewHolder.nqFlag.setText(context.getResources().getString(R.string.tuihui)+nq_mark);
                break;
        }
        switch (contractChange.getPro_flag()){
            case 0:
                viewHolder.proFlag.setText(context.getResources().getString(R.string.dsh)+pro_mark);
                break;
            case 1:
                viewHolder.proFlag.setText(context.getResources().getString(R.string.tongguo)+pro_mark);
                break;
            case 2:
                viewHolder.proFlag.setText(context.getResources().getString(R.string.tuihui)+pro_mark);
                break;
        }
        switch (contractChange.getZp_flag()){
            case 0:
                viewHolder.zpFlag.setText(context.getResources().getString(R.string.dsh)+zp_mark);
                break;
            case 1:
                viewHolder.proFlag.setText(context.getResources().getString(R.string.tongguo)+zp_mark);
                break;
            case 2:
                viewHolder.proFlag.setText(context.getResources().getString(R.string.tuihui)+zp_mark);
                break;
        }
        switch (contractChange.getFinal_flag()){
            case 0:
                viewHolder.finalFlag.setText(context.getResources().getString(R.string.dsh));
                break;
            case 1:
                viewHolder.finalFlag.setText(context.getResources().getString(R.string.tongguo));
                break;
            case 2:
                viewHolder.finalFlag.setText(context.getResources().getString(R.string.tuihui));
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
        @BindView(R.id.zp_flag)
        TextView zpFlag;
        @BindView(R.id.final_flag)
        TextView finalFlag;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
