package fajieyefu.com.luoxiang.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-15.
 */

public class ResponseBean2 {
    private int code;
    private String msg;
    private List<ContractBean> data;
    private T data2;

    public T getData2() {
        return data2;
    }

    public void setData2(T data2) {
        this.data2 = data2;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ContractBean> getData() {
        return data;
    }

    public void setData(List<ContractBean> data) {
        this.data = data;
    }

    public static class T {
        public List<Map<String,Object>> remindClueInfo;
        public List<Map<String,Object>> newClueInfo;

    }

}
