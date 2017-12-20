package fajieyefu.com.luoxiang.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zzti.fengyongge.imagepicker.PhotoPreviewActivity;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;
import com.zzti.fengyongge.imagepicker.model.PhotoModel;
import com.zzti.fengyongge.imagepicker.util.CommonUtils;
import com.zzti.fengyongge.imagepicker.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.InvioceInfoAdapter;
import fajieyefu.com.luoxiang.bean.InvioceBean;
import fajieyefu.com.luoxiang.bean.ReponseBean;
import fajieyefu.com.luoxiang.bean.UploadGoodsBean;
import fajieyefu.com.luoxiang.dao.DaoBean;
import fajieyefu.com.luoxiang.data.CommonData;
import fajieyefu.com.luoxiang.layout.MyGridView;
import fajieyefu.com.luoxiang.layout.MySpinner;
import fajieyefu.com.luoxiang.layout.MySpinnerForFree;
import fajieyefu.com.luoxiang.layout.TitleLayout;
import fajieyefu.com.luoxiang.util.Config;
import fajieyefu.com.luoxiang.util.DbTOPxUtils;
import fajieyefu.com.luoxiang.util.ImageFactory;
import fajieyefu.com.luoxiang.util.MyCallback;
import fajieyefu.com.luoxiang.util.ToolUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017-12-04.
 */

public class InvoiceDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.invoiceLv)
    ListView invoiceLv;
    private List<InvioceBean> data = new ArrayList<>();
    private InvioceInfoAdapter invioceInfoAdapter = null;
    private int orderId;
    private String orderNumber;
    private Dialog dialog ;
    private EditText invioceNameEdit ;
    private EditText invioceNumberEdit;
    private EditText invioceAddressPhoneEdit ;
    private EditText bankNumberEdit ;
    private MySpinnerForFree invioceTypeSpinner;
    private EditText invioceMoneyEdit  ;
    private EditText invioceProjectEdit ;
    private TextView close;
    private MyGridView myGridView;
    private GridImgAdapter gridImgsAdapter;
    private LinearLayout fileLayout;
    private Button commit;
    private ImageView add;
    private int do_flag = 0 ;
    private int currentPosition=0;
    private int screen_widthOffset;
    private JSONObject jsonObject = new JSONObject();
    private ToolUtil toolUtil = new ToolUtil();
    private List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
    private static String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();
    private int[] screenWH;

    private InvioceInfoAdapter.OnButtonOnClick onButtonOnClick = new InvioceInfoAdapter.OnButtonOnClick() {
        @Override
        public void onClick(int position) {
            img_uri.clear();
            InvioceBean invioceBean = data.get(position);
            setEditText(invioceNameEdit,invioceBean.getInvioceName());
            setEditText(invioceNumberEdit,invioceBean.getInvioceNumber());
            setEditText(invioceAddressPhoneEdit,invioceBean.getInvioceName());
            setEditText(bankNumberEdit,invioceBean.getInvioceName());
            setEditText(invioceMoneyEdit,invioceBean.getInvioceName());
            setEditText(invioceProjectEdit,invioceBean.getInvioceName());
            invioceTypeSpinner.setText(invioceBean.getInvioceType());
            if (!TextUtils.isEmpty(invioceBean.getImgurl())){
                String[] arr = invioceBean.getImgurl().split("//");
                if (arr.length>0){
                    String fileName = arr[arr.length - 1];
                    download(invioceBean.getImgurl(),null,fileName,0);
                }
            }
            img_uri.add(null);
            gridImgsAdapter.notifyDataSetChanged();
            do_flag = 1;
            currentPosition=position;
            dialog.show();
        }
    };
    private InvioceInfoAdapter.OnDeleteOnClick onDeleteOnClick = new InvioceInfoAdapter.OnDeleteOnClick(){

        @Override
        public void onClick(int position) {
            try {
                jsonObject.put("id",data.get(position).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.postString()
                    .url(CommonData.deleteInvioceUrl)
                    .content(jsonObject.toString())
                    .mediaType(MediaType.parse("application/json;charset=utf-8"))
                    .build()
                    .execute(new DeleteCallBack());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_layout);
        ButterKnife.bind(this);
        initView();
        initData();


    }

    private void initData() {
        try {
            jsonObject.put("username", DaoBean.getUseInfoById(1).getUsername());
            jsonObject.put("password", DaoBean.getUseInfoById(1).getPassword());
            jsonObject.put("orderNumber",orderNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString()
                .url(CommonData.invioceInfoURL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(new MyCallBack());
    }

    private void initView() {
        screenWH=new ToolUtil().getScreenWH(this);
        title.setTitleText("开票信息");
        add = (ImageView) title.findViewById(R.id.add);
        add.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        orderId= intent.getIntExtra("orderId",-1);
        orderNumber=intent.getStringExtra("orderNumber");
        invioceInfoAdapter = new InvioceInfoAdapter(this,data,onButtonOnClick,onDeleteOnClick);
        invoiceLv.setAdapter(invioceInfoAdapter);
        List<String> list_invioce_type = new ArrayList<>();
        list_invioce_type.add("机动车发票");
        list_invioce_type.add("增值税专票");
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_invoice_layout,null);
        invioceNameEdit = (EditText) view.findViewById(R.id.invioce_name_edit);
        invioceNumberEdit = (EditText) view.findViewById(R.id.invioce_number_edit);
        invioceAddressPhoneEdit = (EditText) view.findViewById(R.id.invioce_address_phone_edit);
        bankNumberEdit = (EditText) view.findViewById(R.id.bank_number_edit);
        invioceTypeSpinner = (MySpinnerForFree) view.findViewById(R.id.invioce_type_edit);
        invioceTypeSpinner.setData(list_invioce_type);
        invioceMoneyEdit = (EditText) view.findViewById(R.id.invioce_money_edit);
        invioceProjectEdit = (EditText) view.findViewById(R.id.invioce_project_edit);
        close = (TextView) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        myGridView = (MyGridView) view.findViewById(R.id.my_goods_GV);
        fileLayout= (LinearLayout) view.findViewById(R.id.file_layout);
        ViewTreeObserver vto = fileLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fileLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                screen_widthOffset=(fileLayout.getWidth() - (4 * DbTOPxUtils.dip2px(InvoiceDetailActivity.this, 2))) / 4;
            }
        });
        initFiles();
        commit = (Button) view.findViewById(R.id.commit);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWH[1]/3*2));
        add.setOnClickListener(this);
        commit.setOnClickListener(this);
        applyPermission();
    }

    private void applyPermission() {
        AlertDialog.Builder builder= applyPermission(this,"读取文件权限不可用","需要获取读取权限\n" +
                "否则，您将无法正常在应用中查看文件",permissions,"android.permission.WRITE_EXTERNAL_STORAGE"
        );
        if (builder!=null){
            builder.show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                add();
                break;
            case R.id.commit:
                commitData();
                break;
        }
    }

    private void add() {
        do_flag = 0;
        setEditText(invioceNameEdit,"");
        setEditText(invioceNumberEdit,"");
        setEditText(invioceAddressPhoneEdit,"");
        setEditText(bankNumberEdit,"");
        setEditText(invioceMoneyEdit,"");
        setEditText(invioceProjectEdit,"");
        img_uri.clear();
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();

        dialog.show();
    }

    private void commitData() {
        toolUtil.showProgressDialog(this);
        String judgeText = judgeMustEdit();
        if (!TextUtils.isEmpty(judgeText)){
            Toast.makeText(this, judgeText, Toast.LENGTH_SHORT).show();
            toolUtil.dismissProgressDialog();
            return;
        }
        String url ="";
        if (do_flag==0){
            url = CommonData.addInvioceInfo;
        }else{

            url =CommonData.updateInvioceInfo;
        }
        Map<String, File> map = new HashMap<>();
        if (img_uri.size()-1>=0){
            File file = new File(img_uri.get(0).getUrl());
            map.put("1.jpg",file);
        }

        OkHttpUtils.post()
                .url(url)
                .files("file", map)
                .addParams("content", jsonObject.toString())
                .build()
                .execute(new EditMyCallBack());
    }

    private String  judgeMustEdit() {
        String toastString = "";
        /*if (TextUtils.isEmpty(invioceNameEdit.getText().toString())){
            toastString="请填写开票名称！";
            return toastString;
        }
        if (TextUtils.isEmpty(invioceNumberEdit.getText().toString())){
            toastString="请填写税号！";
            return toastString;
        }
        if (TextUtils.isEmpty(invioceAddressPhoneEdit.getText().toString())){
            toastString="请填写地址电话！";
            return toastString;
        }
        if (TextUtils.isEmpty(bankNumberEdit.getText().toString())){
            toastString="请填写银行账号！";
            return toastString;
        }
        if (TextUtils.isEmpty(invioceTypeSpinner.getText())){
            toastString="请选择开票类型！";
            return toastString;
        }
        if (TextUtils.isEmpty(invioceProjectEdit.getText().toString())){
            toastString="请填写开票项目！";
            return toastString;
        }
        */
        if (TextUtils.isEmpty(invioceMoneyEdit.getText().toString())){
            toastString="请填写开票金额！";
            return toastString;
        }

        try {
            jsonObject.put("invoice_name",invioceNameEdit.getText().toString());
            jsonObject.put("invoice_number",invioceNumberEdit.getText().toString());
            jsonObject.put("invoice_address_phone",invioceAddressPhoneEdit.getText().toString());
            jsonObject.put("bank_number",bankNumberEdit.getText().toString());
            jsonObject.put("invoice_type",invioceTypeSpinner.getText());
            jsonObject.put("invoice_money",invioceMoneyEdit.getText().toString());
            jsonObject.put("invoice_project",invioceProjectEdit.getText().toString());
            if (do_flag==1){
                jsonObject.put("id",data.get(currentPosition).getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toastString;

    }
    private void initFiles() {
        //方法一
//        Config.ScreenMap = Config.getScreenSize(this, this);
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        screen_widthOffset = (display.getWidth() - (4 * DbTOPxUtils.dip2px(this, 2))) / 4;

        gridImgsAdapter = new GridImgAdapter();
        myGridView.setAdapter(gridImgsAdapter);
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();


    }

    private class MyCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(InvoiceDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode()==CommonData.SUCCESS){
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                data.clear();
                data.addAll(response.getData().invioces);
                invioceInfoAdapter.notifyDataSetChanged();
            }
        }
    }
    private void setEditText(EditText editText,String text){
        editText.setText(text==null?"":text);
    }

    private class EditMyCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(InvoiceDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode()==0){
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                data.clear();
                data.addAll(response.getData().invioces);
                invioceInfoAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(InvoiceDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeleteCallBack extends MyCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            toolUtil.dismissProgressDialog();
            Toast.makeText(InvoiceDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ReponseBean response, int id) {
            toolUtil.dismissProgressDialog();
            if (response.getCode()==0){
                data.clear();
                data.addAll(response.getData().invioces);
                invioceInfoAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(InvoiceDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case  CommonData.PERMISSION_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSetting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
                break;
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                    if (img_uri.size() > 0) {
                        img_uri.remove(img_uri.size() - 1);
                    }

                    for (int i = 0; i < paths.size(); i++) {
                        if (img_uri.size() >= 9) {
                            Toast.makeText(this, "最多可以上传9张附件!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        img_uri.add(new UploadGoodsBean(paths.get(i), false));
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setOriginalPath(paths.get(i));
                        photoModel.setChecked(true);
                        single_photos.add(photoModel);
                        //上传参数
                    }

                    if (img_uri.size() < 1) {
                        img_uri.add(null);
                    }
                    gridImgsAdapter.notifyDataSetChanged();
                }
                break;
        }

    }
    class GridImgAdapter extends BaseAdapter implements ListAdapter {
        @Override
        public int getCount() {
            return img_uri.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(InvoiceDetailActivity.this).inflate(R.layout.activity_addstory_img_item, null);
           ViewHolder holder;

            if (convertView != null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(InvoiceDetailActivity.this).inflate(R.layout.activity_addstory_img_item, null);
                convertView.setTag(holder);
                holder.add_IB = (ImageView) convertView.findViewById(R.id.add_IB);
                holder.delete_IV = (ImageView) convertView.findViewById(R.id.delete_IV);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            AbsListView.LayoutParams param = new AbsListView.LayoutParams(screen_widthOffset, screen_widthOffset);
            convertView.setLayoutParams(param);
            if (img_uri.get(position) == null) {
                holder.delete_IV.setVisibility(View.GONE);

                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.iv_add_the_pic, holder.add_IB);

                holder.add_IB.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(InvoiceDetailActivity.this, PhotoSelectorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("limit", 1 - (img_uri.size() - 1));
                        startActivityForResult(intent, 0);
                    }
                });

            } else {
                ImageLoader.getInstance().displayImage("file://" + img_uri.get(position).getUrl(), holder.add_IB);

                holder.delete_IV.setOnClickListener(new View.OnClickListener() {
                    private boolean is_addNull;

                    @Override
                    public void onClick(View arg0) {
                        is_addNull = true;
                        String img_url = img_uri.remove(position).getUrl();
                        single_photos.remove(position);
                        for (int i = 0; i < img_uri.size(); i++) {
                            if (img_uri.get(i) == null) {
                                is_addNull = false;
                                continue;
                            }
                        }
                        if (is_addNull) {
                            img_uri.add(null);
                        }

                        FileUtils.DeleteFolder(img_url);//删除在emulate/0文件夹生成的图片

                        gridImgsAdapter.notifyDataSetChanged();
                    }
                });

                holder.add_IB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos", (Serializable) single_photos);
                        bundle.putInt("position", position);
                        bundle.putBoolean("isSave", false);
                        CommonUtils.launchActivity(InvoiceDetailActivity.this, PhotoPreviewActivity.class, bundle);
                    }
                });

            }
            return convertView;
        }

        class ViewHolder {
            ImageView add_IB;
            ImageView delete_IV;
        }
    }
    private void download(String fileUrl, String filePath, String imageName, int type) {


        //Target
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File dcimFile = ImageFactory.getDCIMFile(InvoiceDetailActivity.this, imageName);
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Toast.makeText(ContractInputActivity.this, "图片下载至:" + dcimFile, Toast.LENGTH_SHORT).show();
//                FilesInfo fileInfo = new FilesInfo();
//                fileInfo.setAdd_file_path(dcimFile.getPath());
//                list.add(fileInfo);
                if (type == 0) {
                    img_uri.add(new UploadGoodsBean(dcimFile.getPath(), false));
                    single_photos.add(new PhotoModel(dcimFile.getPath(), true));
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("image_info",
                            MODE_PRIVATE).edit();
                    editor.putString("image_path", dcimFile.getPath());
                    editor.apply();
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (type == 0) {
                    img_uri.add(new UploadGoodsBean("drawable://" + R.drawable.more_item_unpress, false));
                    single_photos.add(new PhotoModel("drawable://" + R.drawable.more_item_unpress, true));
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {


            }
        };

        //Picasso下载
        fileUrl = CommonData.loadImageFile + "?fileName=" + fileUrl;
        Picasso.with(this).load(fileUrl).into(target);

    }
}
