package fajieyefu.com.luoxiang.bean;

/**
 * Created by Administrator on 2017-05-08.
 */

public class ContractBean {
    private int  standardId;
    private long createtime;
    private int statues;
    private String userId;
    private String orderName;
    private double standardMoney;
    private String standardName;
    private String cDCCode;
    private int orderId;
    private String orderNumber;
    private int dpc;
    private int ddtc;
    private int wc;

    public int getDpc() {
        return dpc;
    }

    public void setDpc(int dpc) {
        this.dpc = dpc;
    }

    public int getDdtc() {
        return ddtc;
    }

    public void setDdtc(int ddtc) {
        this.ddtc = ddtc;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getStatues() {
        return statues;
    }

    public void setStatues(int statues) {
        this.statues = statues;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getStandardMoney() {
        return standardMoney;
    }

    public void setStandardMoney(double standardMoney) {
        this.standardMoney = standardMoney;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getcDCCode() {
        return cDCCode;
    }

    public void setcDCCode(String cDCCode) {
        this.cDCCode = cDCCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
