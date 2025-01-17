package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-05-07.
 */
public class ObtainBean implements Serializable{
    @SerializedName(value = "code", alternate = {"standardId","cCusCode","client_level_code","brand_code","car_type_code","areaCode"})
    private String code ;
    @SerializedName(value = "name", alternate = {"standardName","cCusName","client_level_name","brand_name","car_type_name","areaName"})
    private String name;
    private String cCusHand;
    private String cCusAddress;
    private String cCusLPerson;
    private String cCusPerson;
    private String cDCName;

    public String getcDCName() {
        return cDCName;
    }

    public void setcDCName(String cDCName) {
        this.cDCName = cDCName;
    }

    public String getcCusPerson() {
        return cCusPerson;
    }

    public void setcCusPerson(String cCusPerson) {
        this.cCusPerson = cCusPerson;
    }

    public String getcCusAddress() {
        return cCusAddress;
    }

    public void setcCusAddress(String cCusAddress) {
        this.cCusAddress = cCusAddress;
    }

    public String getcCusLPerson() {
        return cCusLPerson;
    }

    public void setcCusLPerson(String cCusLPerson) {
        this.cCusLPerson = cCusLPerson;
    }

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
