package fajieyefu.com.luoxiang.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            public InitClueInfo initClueInfo;
            public List<ContractTypeBean> client_level;
            public List<ContractBean> sameOrders;
            public List<Option> optionItems;
            public PushBean pushBean;
            public List<U8HrCt007> u8HrCt007;
            public List<InvioceBean> invioces;
            public UserInfo userInfo;
            public List<ContractDetail> orderHistory;
            public List<AreaStan> areaStans;
            public List<ClueAnalysisBean> clue_analysis_data;


    }
}
