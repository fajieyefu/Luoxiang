package fajieyefu.com.luoxiang.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ReponseBean {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class T {
            public List<ObtainBean> standards;
            public List<ObtainBean> customers;
            public ArrayList<ContractBean> contracts;
            public List<InventoryClass> inventory;
    }
}