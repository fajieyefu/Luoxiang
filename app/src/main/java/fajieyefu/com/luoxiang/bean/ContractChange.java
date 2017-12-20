package fajieyefu.com.luoxiang.bean;

/**
 * Created by Administrator on 2017-08-08.
 */

public class ContractChange {
    private String orderNumber;
    private String change_content;
    private String create_person;
    private String create_time;
    private String nq_check_person;
    private String nq_check_time;
    private String pro_check_person;
    private String pro_check_time;
    private int nq_flag;
    private int pro_flag;
    private double amt;
    private String amt_dx;
    private int zp_flag;//装配车间标记
    private int final_flag;//最终标记
    private int remark;//备注
    private String obtain_type;//提车方式
    private String nq_mark;
    private String pro_mark;
    private String zp_mark;

    public String getNq_mark() {
        return nq_mark;
    }

    public void setNq_mark(String nq_mark) {
        this.nq_mark = nq_mark;
    }

    public String getPro_mark() {
        return pro_mark;
    }

    public void setPro_mark(String pro_mark) {
        this.pro_mark = pro_mark;
    }

    public String getZp_mark() {
        return zp_mark;
    }

    public void setZp_mark(String zp_mark) {
        this.zp_mark = zp_mark;
    }

    public String getObtain_type() {
        return obtain_type;
    }

    public void setObtain_type(String obtain_type) {
        this.obtain_type = obtain_type;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public int getZp_flag() {
        return zp_flag;
    }

    public void setZp_flag(int zp_flag) {
        this.zp_flag = zp_flag;
    }

    public int getFinal_flag() {
        return final_flag;
    }

    public void setFinal_flag(int final_flag) {
        this.final_flag = final_flag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getChange_content() {
        return change_content;
    }

    public void setChange_content(String change_content) {
        this.change_content = change_content;
    }

    public String getCreate_person() {
        return create_person;
    }

    public void setCreate_person(String create_person) {
        this.create_person = create_person;
    }

    public String getNq_check_person() {
        return nq_check_person;
    }

    public void setNq_check_person(String nq_check_person) {
        this.nq_check_person = nq_check_person;
    }

    public String getNq_check_time() {
        return nq_check_time;
    }

    public void setNq_check_time(String nq_check_time) {
        this.nq_check_time = nq_check_time;
    }

    public String getPro_check_person() {
        return pro_check_person;
    }

    public void setPro_check_person(String pro_check_person) {
        this.pro_check_person = pro_check_person;
    }

    public String getPro_check_time() {
        return pro_check_time;
    }

    public void setPro_check_time(String pro_check_time) {
        this.pro_check_time = pro_check_time;
    }

    public int getNq_flag() {
        return nq_flag;
    }

    public void setNq_flag(int nq_flag) {
        this.nq_flag = nq_flag;
    }

    public int getPro_flag() {
        return pro_flag;
    }

    public void setPro_flag(int pro_flag) {
        this.pro_flag = pro_flag;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getAmt_dx() {
        return amt_dx;
    }

    public void setAmt_dx(String amt_dx) {
        this.amt_dx = amt_dx;
    }
}
