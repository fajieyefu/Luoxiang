package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ContractBean;
import fajieyefu.com.luoxiang.bean.ContractTypeBean;

/**
 * Created by qiancheng on 2016/11/30.
 */
public class SpinnerChoosyTypeAdapter extends BaseAdapter {
	private List<ContractTypeBean> mData;
	private Context mContext;

	public SpinnerChoosyTypeAdapter(Context context, List<ContractTypeBean> data) {
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContractTypeBean contractTypeBean = mData.get(position);
		convertView= LayoutInflater.from(mContext).inflate(R.layout.spinner_choose_type,null);
		TextView textView = (TextView) convertView.findViewById(R.id.item_text);
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
		if (!TextUtils.isEmpty(contractTypeBean.getTypeName())){
			textView.setText(contractTypeBean.getTypeName());
		}

		if (contractTypeBean.getSelect_flag()==1){
			layout.setBackgroundColor(mContext.getResources().getColor(R.color.Myorange));
		}
		return convertView;
	}
}

