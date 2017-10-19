package fajieyefu.com.luoxiang.bean;

/**
 * Created by Administrator on 2017-09-26.
 */
public class ProNumBean {
    private int normal_num=0;
    private int urgent_num=0;
    private int first_num=0;
    private int normal_flag=0;
    private int urgent_flag=0;
    private int first_flag=0;
    private String pro_date;
    private int enable_flag;

    public int getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(int enable_flag) {
        this.enable_flag = enable_flag;
    }

    public int getNormal_num() {
        return normal_num;
    }

    public void setNormal_num(int normal_num) {
        this.normal_num = normal_num;
    }

    public int getUrgent_num() {
        return urgent_num;
    }

    public void setUrgent_num(int urgent_num) {
        this.urgent_num = urgent_num;
    }

    public int getFirst_num() {
        return first_num;
    }

    public void setFirst_num(int first_num) {
        this.first_num = first_num;
    }

    public int getNormal_flag() {
        return normal_flag;
    }

    public void setNormal_flag(int normal_flag) {
        this.normal_flag = normal_flag;
    }

    public int getUrgent_flag() {
        return urgent_flag;
    }

    public void setUrgent_flag(int urgent_flag) {
        this.urgent_flag = urgent_flag;
    }

    public int getFirst_flag() {
        return first_flag;
    }

    public void setFirst_flag(int first_flag) {
        this.first_flag = first_flag;
    }

    public String getPro_date() {
        return pro_date;
    }

    public void setPro_date(String pro_date) {
        this.pro_date = pro_date;
    }
}
