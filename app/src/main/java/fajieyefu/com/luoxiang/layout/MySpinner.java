package fajieyefu.com.luoxiang.layout;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.SpinnerAdapter;
import fajieyefu.com.luoxiang.bean.ObtainBean;
import fajieyefu.com.luoxiang.util.ToolUtil;

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
    private TextView exit;
    private EditText editText;
    private List<ObtainBean>  editData = new ArrayList<>();
    private int[] screenWH;


    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.spinner_layout, this);
        spinnerText= (TextView) findViewById(R.id.spinner_text);
        spinnerText.setOnClickListener(new MyOnClickListener());
        screenWH=new ToolUtil().getScreenWH(context);

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
            editData.clear();
            editData.addAll(data);
            final Dialog dialog = new Dialog(context);
            spinnerAdapter = new SpinnerAdapter(editData,context);
            View view =LayoutInflater.from(context).inflate(R.layout.spinner_dialog_layout,null);
            ListView  lv = (ListView) view.findViewById(R.id.spinner_list);
                exit = (TextView) view.findViewById(R.id.close);
                exit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog!=null){
                            dialog.dismiss();
                        }
                    }
                });
            editText = (EditText) view.findViewById(R.id.edit_text);
            editText.addTextChangedListener(watcher);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected_name= editData.get(position).getName();
                    selected_code=editData.get(position).getCode();
                    spinnerText.setText(selected_name);
                    Log.i("点击选项",selected_name);
                    dialog.dismiss();
                }
            });
            lv.setAdapter(spinnerAdapter);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWH[1]/3*2) );
            dialog.show();

        }
        private TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editData.clear();
                if (!TextUtils.isEmpty(editText.getText())){

                    for (ObtainBean bean : data){
                        if (bean.getName().contains(editText.getText().toString())){
                            editData.add(bean);
                        }
                    }
                }else {
                    editData.addAll(data);
                }

                spinnerAdapter.notifyDataSetChanged();
            }
        };
    }
}
