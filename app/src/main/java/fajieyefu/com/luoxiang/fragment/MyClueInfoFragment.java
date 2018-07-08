package fajieyefu.com.luoxiang.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import fajieyefu.com.luoxiang.adapter.SpinnerChoosyTypeAdapter;
import fajieyefu.com.luoxiang.bean.ClueBean;
import fajieyefu.com.luoxiang.bean.ContractTypeBean;
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
public class MyClueInfoFragment extends Fragment implements XListView.IXListViewListener,View.OnClickListener{
	private final int TYPE_HISTORY = 1;
	private int count = 1;
	private LinearLayout filterLayout;
	private LinearLayout shadeLayout;
	private List<ClueBean> list;
	private ClueListAdapter adapter;
	private XListView listView;
	private ImageView emptyView;
	private Button queryClueBtn;
	private Button clientLevelBtn;
	private EditText clientName;
	private EditText clientPhone;
	private UserInfo userInfo;
	private String first_load_time;
	private Button search;
	private int curr=1;
	private JSONObject startFollowJson= new JSONObject();
	private Dialog dialog;
	private ListView clientLevelListView;
	private int client_level_code=0;
	private List<ContractTypeBean> contractTypes = new ArrayList<>();
	private ClueListAdapter.OnButtonOnClick buttonOnClick = new ClueListAdapter.OnButtonOnClick() {
		@Override
		public void startFollowClue(int position) {
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
		queryClueBtn= (Button) view.findViewById(R.id.queryClueBtn);
		RelativeLayout queryClueLayout = (RelativeLayout) view.findViewById(R.id.queryClue);
		queryClueLayout.setVisibility(View.VISIBLE);
		clientLevelBtn= (Button) view.findViewById(R.id.choose_client_level);
		search= (Button) view.findViewById(R.id.search);
		search.setOnClickListener(this);
		queryClueBtn.setOnClickListener(this);
		clientLevelBtn.setOnClickListener(this);
		filterLayout= (LinearLayout) view.findViewById(R.id.filterLayout);
		shadeLayout= (LinearLayout) view.findViewById(R.id.shade_layout);
		shadeLayout.setOnClickListener(this);
		emptyView = (ImageView) view.findViewById(R.id.empty_view);
		clientName= (EditText) view.findViewById(R.id.clientName);
		clientPhone= (EditText) view.findViewById(R.id.clientPhone);
		listView = (XListView) view.findViewById(R.id.my_clue_info_lv);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
//		listView.setAutoLoadEnable(true);
		listView.setXListViewListener(this);
		//第一次加载时间，1.用于显示上次更新时间 2.用于后台取数据
		first_load_time=ToolUtil.getTime();

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
				Intent intent = new Intent(getActivity(),ClueInfoDetailActivity.class);
				intent.putExtra("clue_code", list.get(position - 1).getClue_code());
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
			jsonObject.put("client_level_code",client_level_code);
			jsonObject.put("clientName",clientName.getText().toString());
			jsonObject.put("clientPhone",clientPhone.getText().toString());
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
		first_load_time= ToolUtil.getTime();
		list.clear();
		adapter.notifyDataSetChanged();
		initData();

	}

	@Override
	public void onLoadMore() {
		curr++;
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.queryClueBtn:
				if (filterLayout.getVisibility() == View.VISIBLE) {
					filterLayout.setVisibility(View.GONE);
					listView.setEnabled(true);
				} else {
					filterLayout.setVisibility(View.VISIBLE);
					listView.setEnabled(false);
				}
				break;
			case R.id.choose_client_level:
				initTypeDialog();
				break;
			case R.id.search:
				filterLayout.setVisibility(View.GONE);
				onRefresh();
				break;
			case R.id.shade_layout:
				if (filterLayout.getVisibility() == View.VISIBLE) {
					filterLayout.setVisibility(View.GONE);
					listView.setEnabled(true);
				} else {
					filterLayout.setVisibility(View.VISIBLE);
					listView.setEnabled(false);
				}
				break;
		}
	}


	private class CallBack extends MyCallback {
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ReponseBean response, int id) {
			if (response.getCode()==0){
				if (contractTypes.size()==0){
					contractTypes.add(new ContractTypeBean("全部  ",0,1));
					contractTypes.addAll(response.getData().client_level);
					contractTypes.add(new ContractTypeBean("待跟进",-1,0));
				}
				list.addAll(response.getData().clueInfo);
				adapter.notifyDataSetChanged();
				listView.setEnabled(true);
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

	private void initTypeDialog() {

		dialog = new Dialog(getActivity());
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.spinner_dialog, null);
		clientLevelListView = (ListView) view.findViewById(R.id.spinner_list);
		SpinnerChoosyTypeAdapter spinnerAdapter = new SpinnerChoosyTypeAdapter(getActivity(), contractTypes);
		clientLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				clientLevelBtn.setText(contractTypes.get(position).getTypeName());
				for (ContractTypeBean contractTypeBean : contractTypes) {
					contractTypeBean.setSelect_flag(0);
				}
				client_level_code=contractTypes.get(position).getTypeCode();
				onRefresh();
				contractTypes.get(position).setSelect_flag(1);
				dialog.dismiss();
			}
		});

		clientLevelListView.setAdapter(spinnerAdapter);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		dialog.show();

	}
}
