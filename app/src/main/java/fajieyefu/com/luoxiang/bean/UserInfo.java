package fajieyefu.com.luoxiang.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER_INFO.
 */
public class UserInfo {

    private Long id;
    private String username;
    private String password;
    private Boolean rembPsw;
    private Integer rId;
    private String cDepName;
    private String cDepCode;
    private Integer enable_flag;

    public UserInfo() {
    }

    public UserInfo(Long id) {
        this.id = id;
    }

    public UserInfo(Long id, String username, String password, Boolean rembPsw, Integer rId, String cDepName, String cDepCode, Integer enable_flag) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rembPsw = rembPsw;
        this.rId = rId;
        this.cDepName = cDepName;
        this.cDepCode = cDepCode;
        this.enable_flag = enable_flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRembPsw() {
        return rembPsw;
    }

    public void setRembPsw(Boolean rembPsw) {
        this.rembPsw = rembPsw;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public String getCDepName() {
        return cDepName;
    }

    public void setCDepName(String cDepName) {
        this.cDepName = cDepName;
    }

    public String getCDepCode() {
        return cDepCode;
    }

    public void setCDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
    }

    public Integer getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(Integer enable_flag) {
        this.enable_flag = enable_flag;
    }

}
