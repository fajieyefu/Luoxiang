package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.util.ImageFactory;

/**
 * Created by Administrator on 2017-11-08.
 */

public class EffectContractAdapter extends BaseAdapter {
    private Context context;
    private List<ContractBean> data = new ArrayList<>();
//    private OnCheckedListener onCheckedListener;

    public EffectContractAdapter(Context context, List<ContractBean> data/*,OnCheckedListener onCheckedListener*/) {
        this.context = context;
        this.data = data;
//        this.onCheckedListener = onCheckedListener;
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.effect_contract_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (contractBean.getSelect_flag()==0){
            viewHolder.checkbox.setChecked(false);
        }else {
            viewHolder.checkbox.setChecked(true);
        }
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    contractBean.setSelect_flag(1);
                }
                if (isChecked==false){
                    contractBean.setSelect_flag(0);
                }
            }
        });
        viewHolder.orderNumber.setText(contractBean.getOrderNumber());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.checkbox)
        CheckBox checkbox;
        @BindView(R.id.orderNumber)
        TextView orderNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    /*public interface OnCheckedListener {

        public void onCheckedListener(int position);
        public void cancelCheckedListener(int position);
    }
    private class MyOnCheckedChangeListener implements CheckBox.OnCheckedChangeListener {
        int position;
        public MyOnCheckedChangeListener(int position) {
            this.position=position;
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked==true){
                onCheckedListener.onCheckedListener(position);
            }else{
                onCheckedListener.cancelCheckedListener(position);
            }

        }
    }*/
}
