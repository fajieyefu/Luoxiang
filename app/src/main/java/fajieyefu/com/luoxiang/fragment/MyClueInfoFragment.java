package fajieyefu.com.luoxiang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.ClueListAdapter;
import fajieyefu.com.luoxiang.bean.ClueBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.main.ClueInfoDetailActivity;
import fajieyefu.com.luoxiang.main.HistoryActivity;
import fajieyefu.com.luoxiang.main.HistoryDetailsActivity;
import fajieyefu.com.luoxiang.main.HistorySkeletonDetailsActivity;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import fajieyefu.com.luoxiang.widget.XListView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by qiancheng on 2016/9/21.
 */
public class MyClueInfoFragment extends Fragment implements XListView.IXListViewListener{
	private final int TYPE_HISTORY = 1;
	private int count = 1;
	private List<ClueBean> list;
	private ClueListAdapter adapter;
	private XListView listView;
	private Button leadMore;
	private ImageView emptyView;
	private UserInfo userInfo;
	private String first_load_time;
	private int curr=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view=initView(inflater,container);
		initData();
		return view;
	}

	private View initView(LayoutInflater inflater,ViewGroup container) {
		View view = inflater.inflate(R.layout.my_clue_info_fragment, container, false);
		emptyView = (ImageView) view.findViewById(R.id.empty_view);
		listView = (XListView) view.findViewById(R.id.my_clue_info_lv);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setAutoLoadEnable(true);
		listView.setXListViewListener(this);
		//第一次加载时间，1.用于显示上次更新时间 2.用于后台取数据
		first_load_time=ToolUtil.getTime();

		listView.setRefreshTime(first_load_time);
		listView.setEmptyView(emptyView);
		list = new ArrayList<>();
		adapter = new ClueListAdapter(getActivity(),list);
		SwingLeftInAnimationAdapter animationAdapter = new SwingLeftInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int clue_code = list.get(position - 1).getClue_code();
				Intent intent = new Intent(getActivity(),ClueInfoDetailActivity.class);
				intent.putExtra("clue_code", clue_code);
				startActivity(intent);
			}
		});
		return view;


	}

	private void initData() {
		userInfo=DaoBean.getUseInfoById(1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", userInfo.getUsername());
			jsonObject.put("password", userInfo.getPassword());
			jsonObject.put("first_load_time",first_load_time);
			jsonObject.put("curr",curr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		OkHttpUtils.postString()
				.url(CommonData.myClueInfoURL)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new CallBack());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onRefresh() {
		curr=1;
		list.clear();
		initData();

	}

	@Override
	public void onLoadMore() {
		curr++;
		initData();
	}


	private class CallBack extends MyCallback {
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ReponseBean response, int id) {
			if (response.getCode()==0){
				list.addAll(response.getData().clueInfo);
				adapter.notifyDataSetChanged();
				stopRefresh();
			}else{
				Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void stopRefresh() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(ToolUtil.getTime());
	}
}
