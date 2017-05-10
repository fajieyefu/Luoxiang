package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import fajieyefu.com.luoxiang.bean.AuditBean;

/**
 * Created by Administrator on 2017/4/15.
 */

public class AuditAdapter extends BaseAdapter {

    private Context context;
    private List<AuditBean> data;

    public AuditAdapter(Context context, List<AuditBean> data) {
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

        return null;
    }
}
