package fajieyefu.com.luoxiang.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-28.
 */

public class Area implements Serializable {
    private String pubufts;
    private String bDCEnd;
    private String cDCName;
    private String iDCGrade;
    private String cDCCode;

    public String getPubufts() {
        return pubufts;
    }

    public void setPubufts(String pubufts) {
        this.pubufts = pubufts;
    }

    public String getbDCEnd() {
        return bDCEnd;
    }

    public void setbDCEnd(String bDCEnd) {
        this.bDCEnd = bDCEnd;
    }

    public String getcDCName() {
        return cDCName;
    }

    public void setcDCName(String cDCName) {
        this.cDCName = cDCName;
    }

    public String getiDCGrade() {
        return iDCGrade;
    }

    public void setiDCGrade(String iDCGrade) {
        this.iDCGrade = iDCGrade;
    }

    public String getcDCCode() {
        return cDCCode;
    }

    public void setcDCCode(String cDCCode) {
        this.cDCCode = cDCCode;
    }
}
