package fajieyefu.com.luoxiang.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table INVENTORY.
 */
public class Inventory {

    private Long id;
    private String cInvCCode;
    private String cInvCode;
    private String cInvName;
    private String deMoney;
    private String realMoney;
    private String counts;
    private Float weight;
    private String classId;
    private String standardId;
    private Integer isCurrent;
    private String cInvStd;
    private String u8code;
    private Integer styleflag;
    private Integer iSupplyType;

    public Inventory() {
    }

    public Inventory(Long id) {
        this.id = id;
    }

    public Inventory(Long id, String cInvCCode, String cInvCode, String cInvName, String deMoney, String realMoney, String counts, Float weight, String classId, String standardId, Integer isCurrent, String cInvStd, String u8code, Integer styleflag, Integer iSupplyType) {
        this.id = id;
        this.cInvCCode = cInvCCode;
        this.cInvCode = cInvCode;
        this.cInvName = cInvName;
        this.deMoney = deMoney;
        this.realMoney = realMoney;
        this.counts = counts;
        this.weight = weight;
        this.classId = classId;
        this.standardId = standardId;
        this.isCurrent = isCurrent;
        this.cInvStd = cInvStd;
        this.u8code = u8code;
        this.styleflag = styleflag;
        this.iSupplyType = iSupplyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCInvCCode() {
        return cInvCCode;
    }

    public void setCInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
    }

    public String getCInvCode() {
        return cInvCode;
    }

    public void setCInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getCInvName() {
        return cInvName;
    }

    public void setCInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getDeMoney() {
        return deMoney;
    }

    public void setDeMoney(String deMoney) {
        this.deMoney = deMoney;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getCInvStd() {
        return cInvStd;
    }

    public void setCInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    public String getU8code() {
        return u8code;
    }

    public void setU8code(String u8code) {
        this.u8code = u8code;
    }

    public Integer getStyleflag() {
        return styleflag;
    }

    public void setStyleflag(Integer styleflag) {
        this.styleflag = styleflag;
    }

    public Integer getISupplyType() {
        return iSupplyType;
    }

    public void setISupplyType(Integer iSupplyType) {
        this.iSupplyType = iSupplyType;
    }

}
