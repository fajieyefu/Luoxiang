package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;
/**
 * Created by meng on 2018/4/9.
 */

public class ClueAnalysisBean {
    @SerializedName(value = "name", alternate = {"cDCName","cPersonName"})
    private String name;
    private int total;
    private int success;
    private int success_per;
    private int fail;
    private int fail_per;
    @SerializedName(value = "yes", alternate = {"follow"})
    private int yes;
    private int follow_per;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess_per() {
        return success_per;
    }

    public void setSuccess_per(int success_per) {
        this.success_per = success_per;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getFail_per() {
        return fail_per;
    }

    public void setFail_per(int fail_per) {
        this.fail_per = fail_per;
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getFollow_per() {
        return follow_per;
    }

    public void setFollow_per(int follow_per) {
        this.follow_per = follow_per;
    }
}
