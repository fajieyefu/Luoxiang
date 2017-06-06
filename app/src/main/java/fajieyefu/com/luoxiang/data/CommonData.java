package fajieyefu.com.luoxiang.data;

/**
 * Created by Administrator on 2017/4/30.
 */

public class CommonData {
//    public final static String URL2="http://192.168.1.105:8080/clzxszpt/mobile";
    public final static String URL2="http://192.168.1.111:8083/mobile";

    public final static String modifyURL=URL2+"/modify";
    public final static String loginURL=URL2+"/login";
    public static final String HISTORYCONTRACTURL ="/getHistory" ;//历史订单接口
    public static String contractInputURL=URL2+"/getAllStandard";//标准、客户列表接口
    
    public static String OrderAplyListURL=URL2+"/getOrderAplyList";//审批订单列表
    public static String StandardDetails=URL2+"/getClassesByStandard";//标准详情接口
    public static String inProcessContract="";//待处理订单接口地址
    public static String historyContract=URL2+"/getOrdersList";//历史订单接口地址
    public static String contractDetails = URL2+"/getOrderDetail";//合同详情接口
    public static String CommitAuditRuslt=URL2+"/saveOrder";
    public static String UpdateApkURL=URL2+"/getAppVersion";//应用程序更新
}
