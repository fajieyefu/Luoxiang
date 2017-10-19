package fajieyefu.com.luoxiang.bean;

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
            public ObtainBean customer;
            public ArrayList<ContractBean> contracts;
            public List<InventoryClass> inventory;
            public List<ContractDetail> order;
            public List<ContractDetail> contractList;
            public int counts;
            public UpdateBean version;
            public List<AnalysisAddressBean> orders;
            public List<ContractBean> stockList;
            public List<Area> area;
            public List<ContractChange> orderChange;
            public List<ProNumBean> proNum;
            public List<ClueBean> clueInfo;
            public ClueBean clueInfoDetail;
            public List<ClueFollowBean> clueFollow;
    }
}
