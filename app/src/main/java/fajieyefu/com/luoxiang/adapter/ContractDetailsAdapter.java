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
import fajieyefu.com.luoxiang.bean.Inventory;

/**
 * Created by Administrator on 2017-05-16.
 */

public class ContractDetailsAdapter extends BaseAdapter {
    private List<Inventory> list;
    private Context context;

    public ContractDetailsAdapter(List<Inventory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Inventory inventory = list.get(position);
        ViewHolder viewHolder =null;
        if (viewHolder==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.contract_details_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.modelName.setText(inventory.getCInvName()+":   ");
        viewHolder.modelCode.setText(inventory.getCInvStd());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.model_name)
        TextView modelName;
        @BindView(R.id.model_code)
        TextView modelCode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
