package fajieyefu.com.luoxiang.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table LAST_CHECK_CLUE_INFO.
 */
public class LastCheckClueInfo {

    private Long id;
    private Long lastCreateTime;
    private String username;

    public LastCheckClueInfo() {
    }

    public LastCheckClueInfo(Long id) {
        this.id = id;
    }

    public LastCheckClueInfo(Long id, Long lastCreateTime, String username) {
        this.id = id;
        this.lastCreateTime = lastCreateTime;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastCreateTime() {
        return lastCreateTime;
    }

    public void setLastCreateTime(Long lastCreateTime) {
        this.lastCreateTime = lastCreateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
