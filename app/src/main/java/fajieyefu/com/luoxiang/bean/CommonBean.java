package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-10-20.
 */

public class CommonBean {
    @SerializedName(value = "code", alternate = {"client_level_code","brand_code","car_type_code","cDCCode"})
    private int code;
    @SerializedName(value = "name", alternate = {"client_level_name","brand_name","car_type_name","cDCName"})
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
