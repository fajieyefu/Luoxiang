package fajieyefu.com.luoxiang.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-05-08.
 */

public class ContractBean implements Serializable {
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
    private int nq_flag;
    private String cCusName;
    private long ordercreatetime;
    private int isSkeleton;
    private int select_flag;
    private String endCustomerName;
    private String endCustomerPhone;
    private long sp_time;
    private String sp_person;
    private int stop_flag;
    private int isNew;

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getStop_flag() {
        return stop_flag;
    }

    public void setStop_flag(int stop_flag) {
        this.stop_flag = stop_flag;
    }

    public long getSp_time() {
        return sp_time;
    }

    public void setSp_time(long sp_time) {
        this.sp_time = sp_time;
    }

    public String getSp_person() {
        return sp_person;
    }

    public void setSp_person(String sp_person) {
        this.sp_person = sp_person;
    }

    public String getEndCustomerPhone() {
        return endCustomerPhone;
    }

    public void setEndCustomerPhone(String endCustomerPhone) {
        this.endCustomerPhone = endCustomerPhone;
    }

    public String getEndCustomerName() {
        return endCustomerName;
    }

    public void setEndCustomerName(String endCustomerName) {
        this.endCustomerName = endCustomerName;
    }

    public int getSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(int select_flag) {
        this.select_flag = select_flag;
    }

    public int getIsSkeleton() {
        return isSkeleton;
    }

    public void setIsSkeleton(int isSkeleton) {
        this.isSkeleton = isSkeleton;
    }

    public long getOrdercreatetime() {
        return ordercreatetime;
    }

    public void setOrdercreatetime(long ordercreatetime) {
        this.ordercreatetime = ordercreatetime;
    }

    public String getcCusName() {
        return cCusName;
    }

    public void setcCusName(String cCusName) {
        this.cCusName = cCusName;
    }

    public int getNq_flag() {
        return nq_flag;
    }

    public void setNq_flag(int nq_flag) {
        this.nq_flag = nq_flag;
    }

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
