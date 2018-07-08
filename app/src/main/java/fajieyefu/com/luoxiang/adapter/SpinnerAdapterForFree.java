package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ObtainBean;

/**
 * Created by Administrator on 2017-05-06.
 */

public class SpinnerAdapterForFree extends BaseAdapter {
    private List<String> data;
    private Context context;

    public SpinnerAdapterForFree(List<String> data, Context context) {
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
        String info = data.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_list_item, null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_text);
        textView.setText(info);
        return convertView;
    }

}
