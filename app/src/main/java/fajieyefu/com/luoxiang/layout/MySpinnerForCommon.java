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

import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.SpinnerAdapterForInventory;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.util.ToolUtil;

/**
 * Created by Administrator on 2017-05-07.
 */

public class MySpinnerForCommon extends LinearLayout {
    TextView spinnerText;
    LinearLayout numLayout;
    EditText number;
    private EditText edit_text;
    private TextView noSelect;
    private ImageView downline;
    private TextView exit;

    private SpinnerAdapterForInventory adapter;
    private List<Inventory> data = new ArrayList<>();
    private Context context;
    private String selected_code;
    private String selected_name;
    private List<Inventory> editData = new ArrayList<>();
    private int[] screenWH;




    public MySpinnerForCommon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.spinner_layout, this);
        spinnerText = (TextView) findViewById(R.id.spinner_text);
        spinnerText.setOnClickListener(new MyOnClickListener());
        screenWH=new ToolUtil().getScreenWH(context);
    }
    public String getText(){
        return spinnerText.getText().toString()==null?"":spinnerText.getText().toString();
    }

    public void setData(List<Inventory> data) {
        this.data = data;
        spinnerText.setText("");
        if (data!=null&&data.size()>0){
                    spinnerText.setText(data.get(0).getCInvName());
                    selected_code = data.get(0).getCInvCode();
            }


    }

    public String getSelected_code() {
        return selected_code;
    }

    private class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            editData.clear();
            editData.addAll(data);
            final Dialog dialog = new Dialog(context);
            adapter = new SpinnerAdapterForInventory(editData, context);
            View view = LayoutInflater.from(context).inflate(R.layout.spinner_dialog_layout, null);
            exit = (TextView) view.findViewById(R.id.close);
            exit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                }
            });
            edit_text = (EditText) view.findViewById(R.id.edit_text);
            noSelect = (TextView) view.findViewById(R.id.noselect);
            downline = (ImageView) view.findViewById(R.id.downline);
            noSelect.setVisibility(VISIBLE);
            downline.setVisibility(VISIBLE);
            noSelect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinnerText.setText(context.getResources().getString(R.string.noSelected));
                    dialog.dismiss();
                }
            });
            edit_text.addTextChangedListener(watcher2);
            ListView lv = (ListView) view.findViewById(R.id.spinner_list);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Inventory inventory = editData.get(position);
                    selected_name = inventory.getCInvName();
                    selected_code = inventory.getCInvCode();
                    for (Inventory inventory1:data){
                        inventory1.setIsCurrent(0);
                    }
                    inventory.setIsCurrent(1);
                    spinnerText.setText(selected_name);
                    Log.i("点击选项", selected_name);
                    dialog.dismiss();

                }
            });
            lv.setAdapter(adapter);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWH[1]/3*2));
            dialog.show();

        }

        private TextWatcher watcher2 = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editData.clear();
                if (!TextUtils.isEmpty(edit_text.getText().toString())) {
                    for (Inventory bean : data) {
                        if (bean.getCInvName().contains(edit_text.getText().toString()) ) {
                            editData.add(bean);
                        }
                    }
                } else {
                    editData.addAll(data);
                }

                adapter.notifyDataSetChanged();


            }
        };
    }



}
