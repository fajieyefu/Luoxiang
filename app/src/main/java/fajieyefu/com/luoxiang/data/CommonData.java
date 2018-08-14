package fajieyefu.com.luoxiang.data;


import java.net.URL;

/**
 * Created by Administrator on 2017/4/30.
 */

public class CommonData {
//    public final static String URL2 = "http://192.168.1.3:9891/mobile";
    public final static String URL2 = "http://dd.luoxiangcheliang.com:9891/mobile";
    public final static String modifyURL = URL2 + "/modify";
    public final static String loginURL = URL2 + "/login";
    public static final String HISTORYCONTRACTURL = "/getHistory";//历史订单接口
    public static final String DELETECONTRACT = URL2 + "/deleteContract";//刪除合同
    public static final String FOLLOWCLUE = URL2 + "/startfollowClue";
    public static final int SUCCESS = 0;
    public static String contractInputURL = URL2 + "/getAllStandard";//标准、客户列表接口
    public static String OrderAplyListURL = URL2 + "/getOrderAplyList";//审批订单列表
    public static String StandardDetails = URL2 + "/getClassesByStandard";//标准详情接口
    public static String historyContract = URL2 + "/getOrderHisAplyList";//历史订单接口地址
    public static String contractDetails = URL2 + "/getOrderDetail";//合同详情接口
    public static String CommitAuditRuslt = URL2 + "/aplyOrder";
    public static String UpdateApkURL = URL2 + "/getAppVersion";//应用程序更新
    public static String getCountsByAddressURL = URL2 + "/getCountsByAddress";//获取地区订单数量
    public static String CommitContract = URL2 + "/saveOrder";
    public static String modifyContract=URL2+"/modifyOrder";
    public static String CommitStockContract = URL2 + "/saveStockOrder";
    public static String getStockList = URL2 + "/getStockCarInfo";
    public static String addCustomerUrl = URL2 + "/addCustomerInfo";//维护客户信息
    public static String commitContractChange = URL2 + "/contractChange";//增加合同变更（老版）
    public static String getEnableNumOfDays = URL2 + "/getEnableNumOfDays";//增加合同变更
    public static String loadImageFile = URL2 + "/downImageFile";//下载图片的地址
    public static String getLastCheckInfo = URL2 + "/getLastCheckInfo";//获取最新的审批信息，用于提示
    public static final String myClueInfoURL = URL2 + "/getMyClueInfo";//获取自己的线索
    public static final String areaClueInfoURL = URL2 + "/getAreaClueInfo";//获取自己的线索
    public static final String clueInfoDetail = URL2 + "/getClueInfoDetailAndFollowList";//获取线索详情
    public static final String addClueFollow = URL2 + "/addClueFollow";
    public static final String editClueInfo = URL2 + "/editClueInfo";
    public static final String startFollowClue = URL2 + "/startFollowClue";
    public static final String loadInitClueInfo = URL2 + "/loadInitClueInfo";
    public static final String initFreeSpinnerUrl=URL2+"/getInitFreeSpinnerData";//初始化录入合同自由选项
    public static final String newClueInfo = URL2 + "/newClueInfo";
    public static final String initRegid=URL2+"/initRegid";//初始化regid
    public static final String initHWToken = URL2+"/initHWToken";//初始化华为Token
    public static final String invioceInfoURL= URL2+"/getInvioceInfoByOrderId";
    public static final String addInvioceInfo = URL2+"/addInvioceInfo";
    public static final String deleteInvioceUrl =URL2+"/deleteInvioceInfo";
    public static final String applyChangeContract =URL2+"/applyContractChange";//合同变更接口（新版）


    public static final String searchOrderUrl=URL2+"/searchOrder";
    public static final String updateInvioceInfo = URL2+"/updateInvioceInfo";
    public static final String updateRemindFollowFlag = URL2 + "/updateRemindFollowFlag";
    public static final String PRO_FAIL = "FAIL";//处理失败
    public static final String PRO_SUCCESS = "SUCCESS";//成功
    public static final String LOAD_AREA = URL2 + "/getAreaList";//加载区域
    public static String cancelFollow=URL2+"/cancelFollow";
    public static Object PACKAGE_NAME="fajieyefu.com.luoxiang";
    public static boolean DEBUG=true;
    public static final String APP_ID = "2882303761517669999";
    public static final String APP_KEY = "5311766995999";

    public static final String TAG = "fajieyefu.com.luoxiang";

    //申请权限常量
    public static final int PERMISSION_CODE = 1204;


    public  static  final int  modifyFlag = 1;
    public static final int changeFlag =2;

    public static  String getClueDataByArea = URL2+"/getClueDataByArea";
    public static  String getClueDataByPerson = URL2+"/getClueDataByPerson";
}
