package fajieyefu.com.luoxiang.data;

import android.os.Environment;

import java.net.URL;

/**
 * Created by Administrator on 2017/4/30.
 */

public class CommonData {
//    public final static String URL2="http://192.168.31.111:9891/mobile";
    public final static String URL2="http://221.2.89.146:9891/mobile";
    public final static String modifyURL=URL2+"/modify";
    public final static String loginURL=URL2+"/login";
    public static final String HISTORYCONTRACTURL ="/getHistory" ;//历史订单接口
    public static final String DELETECONTRACT = URL2+"/deleteContract";//刪除合同
    public static String contractInputURL=URL2+"/getAllStandard";//标准、客户列表接口
    public static String OrderAplyListURL=URL2+"/getOrderAplyList";//审批订单列表
    public static String StandardDetails=URL2+"/getClassesByStandard";//标准详情接口
    public static String historyContract=URL2+"/getOrderHisAplyList";//历史订单接口地址
    public static String contractDetails = URL2+"/getOrderDetail";//合同详情接口
    public static String CommitAuditRuslt=URL2+"/aplyOrder";
    public static String UpdateApkURL=URL2+"/getAppVersion";//应用程序更新
    public static String getCountsByAddressURL = URL2+"/getCountsByAddress";//获取地区订单数量
    public static String CommitContract=URL2+"/saveOrder";
    public static String CommitStockContract=URL2+"/saveStockOrder";
    public static String getStockList=URL2+"/getStockCarInfo";
    public static String addCustomerUrl=URL2+"/addCustomerInfo";//维护客户信息
    public static String commitContractChange=URL2+"/contractChange";//增加合同变更
    public static String getEnableNumOfDays=URL2+"/getEnableNumOfDays";//增加合同变更
    public static String loadImageFile=URL2+"/downImageFile";//下载图片的地址
    public static String getLastCheckInfo=URL2+"/getLastCheckInfo";//获取最新的审批信息，用于提示
    public static final String myClueInfoURL=URL2+"/getMyClueInfo";//获取自己的线索
    public static final String areaClueInfoURL=URL2+"/getAreaClueInfo";//获取自己的线索
    public static final String clueInfoDetail=URL2+"/getClueInfoDetail";//获取线索详情



    public  static final  String PRO_FAIL="FAIL";//处理失败


    public  static final  String PRO_SUCCESS="SUCCESS";//成功


    public static final String LOAD_AREA=URL2+"/getAreaList";//加载区域

//    public static final String PIC_TEMP = Environment.getExternalStorageDirectory().getPath() + "/luoxiang/pic_temp/";
}
