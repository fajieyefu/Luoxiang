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
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.layout.MySpinnerForInventry;

/**
 * Created by Administrator on 2017-05-11.
 */

public class InventoryClassAdapter extends BaseAdapter {
    private Context context;
    private List<InventoryClass> data;

    public InventoryClassAdapter(Context context, List<InventoryClass> data) {
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
        InventoryClass inventoryClass = data.get(position);
        if (inventoryClass.getCInvCCode().startsWith("14")){
            return null;
        }
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.inventory_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.inventoryClassName.setText(inventoryClass.getCInvCName());
        List<Inventory> inventories = DaoBean.getInventoryLikeCCode(inventoryClass.getCInvCCode());
        //根据inventory
        viewHolder.inventorySpinner.setData(inventories);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.inventoryClassName)
        TextView inventoryClassName;
        @BindView(R.id.inventory_spinner)
        MySpinnerForInventry inventorySpinner;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
