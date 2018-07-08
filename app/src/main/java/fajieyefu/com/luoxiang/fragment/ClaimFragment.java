package fajieyefu.com.luoxiang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;


import com.zhy.http.okhttp.OkHttpUtils;

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
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import fajieyefu.com.luoxiang.widget.XListView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by qiancheng on 2016/9/21.
 */
public class ClaimFragment extends Fragment implements XListView.IXListViewListener {
	private final int TYPE_TODAY = 0;
	private int count = 1;
	private ClueListAdapter adapter;
	private List<ClueBean> list;
	public XListView listView;
	private ImageView emptyView;
	private UserInfo userInfo;
	private String first_load_time;
	private int curr=1;
	private JSONObject startFollowJson= new JSONObject();
	private ClueListAdapter.OnButtonOnClick buttonOnClick = new ClueListAdapter.OnButtonOnClick() {
		@Override
		public void startFollowClue(int position) {
			ClueBean clueBean=list.get(position);
			userInfo= DaoBean.getUseInfoById(1);
			try {
				startFollowJson.put("username",userInfo.getUsername());
				startFollowJson.put("password",userInfo.getPassword());
				startFollowJson.put("clue_code",clueBean.getClue_code());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			OkHttpUtils.postString()
					.url(CommonData.startFollowClue)
					.content(startFollowJson.toString())
					.mediaType(MediaType.parse("application/json;charset=utf-8"))
					.build()
					.execute(new startFollowCallBack(position));
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view=initView(inflater,container);
		return view;

	}
	private View initView(LayoutInflater inflater,ViewGroup container) {
		View view = inflater.inflate(R.layout.my_clue_info_fragment, container, false);
		emptyView = (ImageView) view.findViewById(R.id.empty_view);
		listView = (XListView) view.findViewById(R.id.my_clue_info_lv);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
//		listView.setAutoLoadEnable(true);
		listView.setXListViewListener(this);
		//第一次加载时间，1.用于显示上次更新时间 2.用于后台取数据
		first_load_time= ToolUtil.getTime();

		listView.setRefreshTime(first_load_time);
		listView.setEmptyView(emptyView);
		list = new ArrayList<>();
		adapter = new ClueListAdapter(getActivity(),list,1,buttonOnClick);
		/*SwingLeftInAnimationAdapter animationAdapter = new SwingLeftInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);*/
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String clue_code = list.get(position - 1).getClue_code();
				Intent intent = new Intent(getActivity(),ClueInfoDetailActivity.class);
				intent.putExtra("clue_code", clue_code);
				intent.putExtra("no_edit_layout_flag",1);
				startActivity(intent);
			}
		});
		initData();
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
				.url(CommonData.areaClueInfoURL)
				.content(jsonObject.toString())
				.mediaType(MediaType.parse("application/json;charset=utf-8"))
				.build()
				.execute(new CallBack());

	}
	@Override
	public void onRefresh() {
		curr=1;
		list.clear();
		first_load_time = ToolUtil.getTime();
		adapter.notifyDataSetChanged();
		initData();

	}

	@Override
	public void onLoadMore() {
		curr++;
		initData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
				stopRefresh();
				adapter.notifyDataSetChanged();
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

	private class startFollowCallBack extends MyCallback {
		private int position;
		startFollowCallBack(int position){
			this.position=position;
		}
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ReponseBean response, int id) {
			if (response.getCode()==0){
				list.get(position).setFollow_first_time("1");
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();

			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
