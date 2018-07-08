package fajieyefu.com.luoxiang.bean;

/**
 * Created by Administrator on 2017-11-22.
 */
public class PushBean {
    private int flag;
    private String orderNumber;
    private String clientName;
    private String clientPhone;
    private String pushId;
    private int pushType;//1.小米推送 2.华为推送 3.个推
    private String contractMsg;

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getContractMsg() {
        return contractMsg;
    }

    public void setContractMsg(String contractMsg) {
        this.contractMsg = contractMsg;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
