package fajieyefu.com.luoxiang.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table LAST_CHECK_INFO.
 */
public class LastCheckInfo {

    private Long id;
    private Long lastCheckTime;
    private String username;

    public LastCheckInfo() {
    }

    public LastCheckInfo(Long id) {
        this.id = id;
    }

    public LastCheckInfo(Long id, Long lastCheckTime, String username) {
        this.id = id;
        this.lastCheckTime = lastCheckTime;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Long lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
