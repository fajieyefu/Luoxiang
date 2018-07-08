package fajieyefu.com.luoxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.ClueBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.fragment.MyClueInfoFragment;
import fajieyefu.com.luoxiang.main.ClueInfoDetailActivity;
import fajieyefu.com.luoxiang.util.MyCallback;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-10-17.
 */

public class ClueListAdapter extends BaseAdapter {
    private List<ClueBean> data;
    private Context context;
    private int model;
    private static int myClueModel = 0;
    private static int areaCludeModel = 1;
    private OnButtonOnClick onButtonOnClick;

    public ClueListAdapter(Context context, List<ClueBean> data, int model,OnButtonOnClick onButtonOnClick) {
        this.context = context;
        this.data = data;
        this.model = model;
        this.onButtonOnClick = onButtonOnClick;

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
        ClueBean clueBean = data.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.clue_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(clueBean.getFollow_first_time())) {
//            viewHolder.follow.setVisibility(View.VISIBLE);
            viewHolder.details.setVisibility(View.VISIBLE);
        }else if (clueBean.getFollow_first_time().equals("1")){
            viewHolder.followMan.setVisibility(View.VISIBLE);
            viewHolder.followMan.setText("跟进成功");
            viewHolder.followMan.setTextColor(ContextCompat.getColor(context, R.color.Myorange));
        }else{
            viewHolder.followMan.setVisibility(View.VISIBLE);
            viewHolder.followMan.setText("跟进人:"+clueBean.getFollow_man_name());
            viewHolder.details.setVisibility(View.GONE);
//            viewHolder.followMan.setText("张三");

        }
        viewHolder.follow.setOnClickListener(new MyOnClick(position));

        viewHolder.clientArea.setText(clueBean.getProvince() + " " + clueBean.getCity() + " " + clueBean.getCountry());
        viewHolder.clientLevel.setText(clueBean.getClient_level_name());
        viewHolder.clientName.setText(clueBean.getClientName());
        viewHolder.carStyle.setText(clueBean.getCar_type_name());
        viewHolder.clientInfo.setText(clueBean.getClue_title());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String create_time = dateFormat.format(Long.parseLong(clueBean.getCreate_time()));
        String current_time = dateFormat.format(System.currentTimeMillis());
        if (create_time.equals(current_time)){
            viewHolder.newIcon.setVisibility(View.VISIBLE);
        }else{
            viewHolder.newIcon.setVisibility(View.GONE);
        }
        if (clueBean.getRemind_follow_complete_flag()==1){
            viewHolder.erci.setVisibility(View.VISIBLE);
        }else{
            viewHolder.erci.setVisibility(View.GONE);
        }
        viewHolder.createTime.setText(create_time);
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClueInfoDetailActivity.class);
                intent.putExtra("clue_code",clueBean.getClue_code());
                intent.putExtra("no_edit_layout_flag",1);
                context.startActivity(intent);


            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.clientLevel)
        TextView clientLevel;
        @BindView(R.id.clientArea)
        TextView clientArea;
        @BindView(R.id.clientName)
        TextView clientName;
        @BindView(R.id.follow)
        Button follow;
        @BindView(R.id.carStyle)
        TextView carStyle;
        @BindView(R.id.clientInfo)
        TextView clientInfo;
        @BindView(R.id.follow_man)
        TextView followMan;
        @BindView(R.id.details)
        Button details;
        @BindView(R.id.create_time)
        TextView createTime;
        @BindView(R.id.new_icon)
        ImageView newIcon;
        @BindView(R.id.erci)
        TextView erci;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnButtonOnClick {

        public void startFollowClue(int position);
    }

    private class MyOnClick implements View.OnClickListener {
        int position;
        public MyOnClick(int position) {
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            onButtonOnClick.startFollowClue(position);
        }
    }
}
