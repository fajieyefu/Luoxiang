package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-09-20.
 */

public class ContractTypeBean  {
    @SerializedName(value = "typeName", alternate = {"client_level_name"})
    private String typeName;
    @SerializedName(value = "typeCode", alternate = {"client_level_code"})
    private int typeCode;
    private int select_flag;

    public ContractTypeBean(String typeName, int typeCode, int select_flag) {
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.select_flag = select_flag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(int select_flag) {
        this.select_flag = select_flag;
    }
}
