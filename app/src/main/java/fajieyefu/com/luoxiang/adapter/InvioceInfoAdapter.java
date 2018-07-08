package fajieyefu.com.luoxiang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.bean.InvioceBean;
import fajieyefu.com.luoxiang.bean.PictureBean;
import fajieyefu.com.luoxiang.data.CommonData;

/**
 * Created by Administrator on 2017-08-08.
 */

public class InvioceInfoAdapter extends BaseAdapter {
    private List<InvioceBean> data;
    private Context context;
    private OnButtonOnClick onButtonOnClick;
    private OnDeleteOnClick onDeleteClick;

    public InvioceInfoAdapter(Context context,List<InvioceBean> data,OnButtonOnClick onButtonOnClick,OnDeleteOnClick onDeleteClick) {
        this.data = data;
        this.context = context;
        this.onButtonOnClick=onButtonOnClick;
        this.onDeleteClick=onDeleteClick;
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
        InvioceBean invioceBean = data.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.invoice_lv_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.invoiceName.setText(invioceBean.getInvioceName());
        viewHolder.invoiceNumber.setText(invioceBean.getInvioceNumber());
        viewHolder.invoiceAddressPhone.setText(invioceBean.getInvioceAddressPhone());
        viewHolder.invoiceFlag.setText(invioceBean.getInvioceFlag());
        viewHolder.bankNumber.setText(invioceBean.getBankNumber());
        viewHolder.invoiceType.setText(invioceBean.getInvioceType());
        viewHolder.invoiceMoney.setText(invioceBean.getInvioceMoney());
        viewHolder.invoiceProject.setText(invioceBean.getInvioceProject());
        viewHolder.invoiceFlag.setText(invioceBean.getInvioceFlag());
        viewHolder.applyTime.setText(invioceBean.getApplyTime());
        viewHolder.edit.setOnClickListener(new MyOnClick(position));
        viewHolder.delete.setOnClickListener(new DeleteClick(position));
        ArrayList<String> list = new ArrayList<>();
        ArrayList<PictureBean> listPicture = new ArrayList<>();
        if (!TextUtils.isEmpty(invioceBean.getImgurl())){
            PictureBean pictureBean  = new PictureBean();
            list.add(CommonData.loadImageFile + "?fileName=" + invioceBean.getImgurl());
            pictureBean.setPic(list);
            listPicture.add(pictureBean);
            PicturePreviewAdapter pictureAdapter = new PicturePreviewAdapter(context, listPicture, "附件");
            viewHolder.lvPic.setAdapter(pictureAdapter);
        }

        if(invioceBean.getInvioceFlag()!=null){
            viewHolder.edit.setVisibility(View.INVISIBLE);
            viewHolder.delete.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.invoice_name)
        TextView invoiceName;
        @BindView(R.id.invoice_number)
        TextView invoiceNumber;
        @BindView(R.id.invoice_address_phone)
        TextView invoiceAddressPhone;
        @BindView(R.id.bank_number)
        TextView bankNumber;
        @BindView(R.id.invoice_type)
        TextView invoiceType;
        @BindView(R.id.invoice_money)
        TextView invoiceMoney;
        @BindView(R.id.invoice_project)
        TextView invoiceProject;
        @BindView(R.id.apply_time)
        TextView applyTime;
        @BindView(R.id.invoice_flag)
        TextView invoiceFlag;
        @BindView(R.id.edit)
        ImageView edit;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.lv_pic)
        ListView lvPic;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface OnButtonOnClick {

        public void onClick(int position);
    }
    public interface OnDeleteOnClick {

        public void onClick(int position);
    }
    private class MyOnClick implements View.OnClickListener {
        int position;
        public MyOnClick(int position) {
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            onButtonOnClick.onClick(position);
        }
    }
    private class DeleteClick implements View.OnClickListener {
        int position;
        public DeleteClick(int position) {
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("确定删除该信息?");
            builder.setTitle("警告");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onDeleteClick.onClick(position);
                        }
                    }
            );
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
    }
}
