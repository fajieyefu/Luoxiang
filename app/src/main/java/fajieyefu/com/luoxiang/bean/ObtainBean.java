package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017-05-07.
 */
public class ObtainBean {
    @SerializedName(value = "code", alternate = {"standardId","cCusCode"})
    private String code ;
    @SerializedName(value = "name", alternate = {"standardName","cCusName"})
    private String name;
    private String cCusHand;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcCusHand() {
        return cCusHand;
    }

    public void setcCusHand(String cCusHand) {
        this.cCusHand = cCusHand;
    }
}
