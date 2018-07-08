package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-06-04.
 */

public class AnalysisAddressBean {
    @SerializedName(value = "address", alternate = {"cDCName","code"})
    private String address;
    @SerializedName(value = "addressCode", alternate = {"cDCCode","name"})
    private String addressCode;
    private int counts;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
