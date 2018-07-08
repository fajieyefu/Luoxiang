package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-12-04.
 */

public class InvioceBean {
    @SerializedName(value = "id", alternate = {"list_id"})
    private int id;
    @SerializedName(value = "orderNumber", alternate = {"hth"})
    private String orderNumber;
    @SerializedName(value = "invioceName", alternate = {"kpmc"})
    private String invioceName;
    @SerializedName(value = "invioceNumber", alternate = {"shuihao"})
    private String invioceNumber;
    @SerializedName(value = "invioceAddressPhone", alternate = {"dizhidh"})
    private String invioceAddressPhone;
    @SerializedName(value = "bankNumber", alternate = {"yinhzh"})
    private String bankNumber;
    @SerializedName(value = "invioceType", alternate = {"fplb"})
    private String invioceType;
    @SerializedName(value = "invioceMoney", alternate = {"je"})
    private String invioceMoney;
    @SerializedName(value = "invioceProject", alternate = {"kpxm"})
    private String invioceProject;
    @SerializedName(value = "applyTime", alternate = {"cdef1"})
    private String applyTime;
    @SerializedName(value = "invioceFlag", alternate = {"kpbj"})
    private String invioceFlag;
    private String imgurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvioceName() {
        return invioceName;
    }

    public void setInvioceName(String invioceName) {
        this.invioceName = invioceName;
    }

    public String getInvioceNumber() {
        return invioceNumber;
    }

    public void setInvioceNumber(String invioceNumber) {
        this.invioceNumber = invioceNumber;
    }

    public String getInvioceAddressPhone() {
        return invioceAddressPhone;
    }

    public void setInvioceAddressPhone(String invioceAddressPhone) {
        this.invioceAddressPhone = invioceAddressPhone;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getInvioceType() {
        return invioceType;
    }

    public void setInvioceType(String invioceType) {
        this.invioceType = invioceType;
    }

    public String getInvioceMoney() {
        return invioceMoney;
    }

    public void setInvioceMoney(String invioceMoney) {
        this.invioceMoney = invioceMoney;
    }

    public String getInvioceProject() {
        return invioceProject;
    }

    public void setInvioceProject(String invioceProject) {
        this.invioceProject = invioceProject;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getInvioceFlag() {
        return invioceFlag;
    }

    public void setInvioceFlag(String invioceFlag) {
        this.invioceFlag = invioceFlag;
    }
}
