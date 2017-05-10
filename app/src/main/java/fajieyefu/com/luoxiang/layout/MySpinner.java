package fajieyefu.com.luoxiang.layout;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.SpinnerAdapter;
import fajieyefu.com.luoxiang.bean.ObtainBean;

/**
 * Created by Administrator on 2017-05-07.
 */

public class MySpinner extends LinearLayout {
    TextView spinnerText;
    private SpinnerAdapter spinnerAdapter;
    private List<ObtainBean> data = new ArrayList<>();
    private Context context;
    private String selected_code;
    private String selected_name;



    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.spinner_layout, this);
        spinnerText= (TextView) findViewById(R.id.spinner_text);
        spinnerText.setOnClickListener(new MyOnClickListener());
    }


    public void setData(List<ObtainBean> data){
        this.data= data;
    }
    public String getSelected_code(){
        return selected_code;
    }

    private class MyOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

             final Dialog dialog = new Dialog(context);
            spinnerAdapter = new SpinnerAdapter(data,context);
            View view =LayoutInflater.from(context).inflate(R.layout.spinner_dialog_layout,null);
            ListView  lv = (ListView) view.findViewById(R.id.spinner_list);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected_name= data.get(position).getName();
                    selected_code=data.get(position).getCode();
                    spinnerText.setText(selected_name);
                    Log.i("点击选项",selected_name);
                    dialog.dismiss();
                }
            });
            lv.setAdapter(spinnerAdapter);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view,new ViewGroup.LayoutParams(600, 800) );
            dialog.show();

        }
    }
}
