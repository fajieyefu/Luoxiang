package fajieyefu.com.luoxiang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by Administrator on 2017-05-16.
 */

public class ContractDetail {
    private String cInvCCode;//物料分类编码
    private String cInvCName;//物料名称
    private String standardId;//标准的编码
    private String standardMoney;//标准的价格
    private List<Inventory> inventoryDetails;//配置信息
    private String standardName;//标准的名称
    private String cCusName;//客户姓名
    private String cCusCode;//客户编码
    private String orderName;//物料分类编码
    private double orderMoney;//订单价格
    private String leaveTime;//提车时间
    private String ordercreatetime;//创建时间
    private String createtime;

    private String carstyle;//提車方式
    private String payedmoney;//定金
    private String orderNumber;//订单编号
    private int orderId;//订单ID
    private String cCusAddress;//客户地址0
    private int statues;//订单状态 0/等待审核，1/审批通过，2/审批退回
    private String color;//颜色
    private String cCusHand;//客户联系方式
    private String cCusPerson;//客户联系人
    @SerializedName(value = "marks", alternate = {"remark"})
    private String marks;//备注
    private String bmustbook;//是否有定金
    private String fbookratio;//定金比例
    private String cDefine1;//是否包含运费
    private String singleprice;//单价
    private String booknum;//数量
    private String height;
    private int sqcopy;
    private int del_flag;
    private String sq_remark;
    private String car_long;
    private String car_width;
    private String car_height;
    private String cantrail;
    private String liang_color;
    private String qianyinzuo;
    private String lidigao;
    private String jinshengqi;
    private String fangshuicao;
    private String fangguan;
    private String xiaokuang;
    private String btzj;
    private String btsjq;
    private String ordermoney_dx;
    private String pcflag;
    private String sc_flag;
    private int nq_flag;
    private String qianyinche;
    private String up;
    private String down;
    private String mid;
    private String qianyinxiao;
    private String banhuang;
    private String bianliang;
    private String wcheng_type;
    private String wcheng_num;
    private String box_left;
    private String box_right;
    private String abs;
    private String absMark;
    private String gangquan;
    private String gangquan_num;
    private String luntai;
    private String luntai_num;
    private String chezhou;
    private String houxuan;
    private String qianxuan;
    private String zhongxinju;
    private String houmen_mark;
    private String jinshengqi_mark;
    private String wcheng_mark;
    private String celanban_mark;
    private String box_mark;
    private String model;
    private String powerType;
    private String celanban;
    private String houmen;
    private String diban;
    private String shuibaokong;
    private String liangbaojiao;
    private String pati;
    private String penggan;
    private String nq_remark;
    private String sp_person;
    private String endCustomerName;
    private String endCustomerPhone;
    private String order_type;
    private int isNew;
    private String butterMouthHeight;
    private String cDCName;
    private String spmark;
    private String outlinesize;
    private String oldPrice;
    private String skeletonType;
    private String axisCount;
    private String lockType;
    private String unloadingPlatform;
    private String suspensionType;
    private String fender;
    private String relayValveType;
    private String airCylinderType;
    private String legType;
    private String brakeChamberType;
    private String classCode;
    private String lock_mark;
    private String front_space;
    private String chezhouMark;
    private String banhuangMark;
    private String filePath;
    private String signaturePath;
    private int iStatus;
    private Float iAmt_f;
    private Float fbooksum;
    private String wordPath;
    private int stop_flag;
    private String pgbhyj;
    private String carriage;
    private String obtain_type;
    private Float discountFee;
    private String change_content;
    private String extra_change_content;
    private int zp_flag;
    private int pro_flag;
    private String zp_mark;
    private String pro_mark;
    private String nq_mark;

    private int final_flag;
    private String  areaStanName;
    private String standard_pro_text;
    private String gangquan_mark;
    private String luntai_mark;
    private int isQingZang;
    private int certificate_flag;
    private String certificate_approve_time;
    private String certificate_number;
    private String frame_number;

    private String  forkIsOn;
    private String  isLive;
    private String beam_type;
    private int beam_num;
    private String traction_force;
    private int isElect;

    public int getIsElect() {
        return isElect;
    }

    public void setIsElect(int isElect) {
        this.isElect = isElect;
    }

    public int getBeam_num() {
        return beam_num;
    }

    public void setBeam_num(int beam_num) {
        this.beam_num = beam_num;
    }

    public String getForkIsOn() {
        return forkIsOn;
    }

    public void setForkIsOn(String forkIsOn) {
        this.forkIsOn = forkIsOn;
    }

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }

    public String getBeam_type() {
        return beam_type;
    }

    public void setBeam_type(String beam_type) {
        this.beam_type = beam_type;
    }

    public String getTraction_force() {
        return traction_force;
    }

    public void setTraction_force(String traction_force) {
        this.traction_force = traction_force;
    }

    public String getCertificate_approve_time() {
        return certificate_approve_time;
    }

    public void setCertificate_approve_time(String certificate_approve_time) {
        this.certificate_approve_time = certificate_approve_time;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public String getFrame_number() {
        return frame_number;
    }

    public void setFrame_number(String frame_number) {
        this.frame_number = frame_number;
    }

    public int getCertificate_flag() {
        return certificate_flag;
    }

    public void setCertificate_flag(int certificate_flag) {
        this.certificate_flag = certificate_flag;
    }

    public int getIsQingZang() {
        return isQingZang;
    }

    public void setIsQingZang(int isQingZang) {
        this.isQingZang = isQingZang;
    }
    public String getGangquan_mark() {
        return gangquan_mark;
    }

    public void setGangquan_mark(String gangquan_mark) {
        this.gangquan_mark = gangquan_mark;
    }

    public String getLuntai_mark() {
        return luntai_mark;
    }

    public void setLuntai_mark(String luntai_mark) {
        this.luntai_mark = luntai_mark;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAreaStanName() {
        return areaStanName;
    }

    public void setAreaStanName(String areaStanName) {
        this.areaStanName = areaStanName;
    }

    public String getStandard_pro_text() {
        return standard_pro_text;
    }

    public void setStandard_pro_text(String standard_pro_text) {
        this.standard_pro_text = standard_pro_text;
    }

    public int getZp_flag() {
        return zp_flag;
    }

    public void setZp_flag(int zp_flag) {
        this.zp_flag = zp_flag;
    }

    public int getPro_flag() {
        return pro_flag;
    }

    public void setPro_flag(int pro_flag) {
        this.pro_flag = pro_flag;
    }

    public String getZp_mark() {
        return zp_mark;
    }

    public void setZp_mark(String zp_mark) {
        this.zp_mark = zp_mark;
    }

    public String getPro_mark() {
        return pro_mark;
    }

    public void setPro_mark(String pro_mark) {
        this.pro_mark = pro_mark;
    }

    public String getNq_mark() {
        return nq_mark;
    }

    public void setNq_mark(String nq_mark) {
        this.nq_mark = nq_mark;
    }

    public int getFinal_flag() {
        return final_flag;
    }

    public void setFinal_flag(int final_flag) {
        this.final_flag = final_flag;
    }

    public String getExtra_change_content() {
        return extra_change_content;
    }

    public void setExtra_change_content(String extra_change_content) {
        this.extra_change_content = extra_change_content;
    }

    public String getChange_content() {
        return change_content;
    }

    public void setChange_content(String change_content) {
        this.change_content = change_content;
    }

    public Float getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Float discountFee) {
        this.discountFee = discountFee;
    }

    public String getObtain_type() {
        return obtain_type;
    }

    public void setObtain_type(String obtain_type) {
        this.obtain_type = obtain_type;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getCantrail() {
        return cantrail;
    }

    public void setCantrail(String cantrail) {
        this.cantrail = cantrail;
    }

    public String getAbsMark() {
        return absMark;
    }

    public void setAbsMark(String absMark) {
        this.absMark = absMark;
    }

    public int getStop_flag() {
        return stop_flag;
    }

    public void setStop_flag(int stop_flag) {
        this.stop_flag = stop_flag;
    }

    public String getPgbhyj() {
        return pgbhyj;
    }

    public void setPgbhyj(String pgbhyj) {
        this.pgbhyj = pgbhyj;
    }

    public String getWordPath() {
        return wordPath;
    }

    public void setWordPath(String wordPath) {
        this.wordPath = wordPath;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public Float getiAmt_f() {
        return iAmt_f;
    }

    public void setiAmt_f(Float iAmt_f) {
        this.iAmt_f = iAmt_f;
    }

    public Float getFbooksum() {
        return fbooksum;
    }

    public void setFbooksum(Float fbooksum) {
        this.fbooksum = fbooksum;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getChezhouMark() {
        return chezhouMark;
    }

    public void setChezhouMark(String chezhouMark) {
        this.chezhouMark = chezhouMark;
    }

    public String getBanhuangMark() {
        return banhuangMark;
    }

    public void setBanhuangMark(String banhuangMark) {
        this.banhuangMark = banhuangMark;
    }

    public String getRelayValveType() {
        return relayValveType;
    }

    public void setRelayValveType(String relayValveType) {
        this.relayValveType = relayValveType;
    }

    public String getBrakeChamberType() {
        return brakeChamberType;
    }

    public void setBrakeChamberType(String brakeChamberType) {
        this.brakeChamberType = brakeChamberType;
    }

    public String getFender() {
        return fender;
    }

    public void setFender(String fender) {
        this.fender = fender;
    }

    public String getSkeletonType() {
        return skeletonType;
    }

    public void setSkeletonType(String skeletonType) {
        this.skeletonType = skeletonType;
    }

    public String getAxisCount() {
        return axisCount;
    }

    public void setAxisCount(String axisCount) {
        this.axisCount = axisCount;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public String getUnloadingPlatform() {
        return unloadingPlatform;
    }

    public void setUnloadingPlatform(String unloadingPlatform) {
        this.unloadingPlatform = unloadingPlatform;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }



    public String getAirCylinderType() {
        return airCylinderType;
    }

    public void setAirCylinderType(String airCylinderType) {
        this.airCylinderType = airCylinderType;
    }

    public String getLegType() {
        return legType;
    }

    public void setLegType(String legType) {
        this.legType = legType;
    }


    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getLock_mark() {
        return lock_mark;
    }

    public void setLock_mark(String lock_mark) {
        this.lock_mark = lock_mark;
    }

    public String getFront_space() {
        return front_space;
    }

    public void setFront_space(String front_space) {
        this.front_space = front_space;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getOutlinesize() {
        return outlinesize;
    }

    public void setOutlinesize(String outlinesize) {
        this.outlinesize = outlinesize;
    }

    public String getSpmark() {
        return spmark;
    }

    public void setSpmark(String spmark) {
        this.spmark = spmark;
    }

    public String getcDCName() {
        return cDCName;
    }

    public void setcDCName(String cDCName) {
        this.cDCName = cDCName;
    }

    public String getButterMouthHeight() {
        return butterMouthHeight;
    }

    public void setButterMouthHeight(String butterMouthHeight) {
        this.butterMouthHeight = butterMouthHeight;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getEndCustomerName() {
        return endCustomerName;
    }

    public void setEndCustomerName(String endCustomerName) {
        this.endCustomerName = endCustomerName;
    }

    public String getEndCustomerPhone() {
        return endCustomerPhone;
    }

    public void setEndCustomerPhone(String endCustomerPhone) {
        this.endCustomerPhone = endCustomerPhone;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrdercreatetime() {
        return ordercreatetime;
    }

    public void setOrdercreatetime(String ordercreatetime) {
        this.ordercreatetime = ordercreatetime;
    }

    public int getUrgent_flag() {
        return urgent_flag;
    }

    public void setUrgent_flag(int urgent_flag) {
        this.urgent_flag = urgent_flag;
    }

    private int urgent_flag;

    public String getNq_remark() {
        return nq_remark;
    }

    public void setNq_remark(String nq_remark) {
        this.nq_remark = nq_remark;
    }

    public String getSp_person() {
        return sp_person;
    }

    public void setSp_person(String sp_person) {
        this.sp_person = sp_person;
    }




    public String getCar_long() {
        return car_long;
    }

    public void setCar_long(String car_long) {
        this.car_long = car_long;
    }

    public String getCar_width() {
        return car_width;
    }

    public void setCar_width(String car_width) {
        this.car_width = car_width;
    }

    public String getCar_height() {
        return car_height;
    }

    public void setCar_height(String car_height) {
        this.car_height = car_height;
    }

    public String getLiang_color() {
        return liang_color;
    }

    public void setLiang_color(String liang_color) {
        this.liang_color = liang_color;
    }

    public String getQianyinzuo() {
        return qianyinzuo;
    }

    public void setQianyinzuo(String qianyinzuo) {
        this.qianyinzuo = qianyinzuo;
    }

    public String getLidigao() {
        return lidigao;
    }

    public void setLidigao(String lidigao) {
        this.lidigao = lidigao;
    }

    public String getJinshengqi() {
        return jinshengqi;
    }

    public void setJinshengqi(String jinshengqi) {
        this.jinshengqi = jinshengqi;
    }

    public String getFangshuicao() {
        return fangshuicao;
    }

    public void setFangshuicao(String fangshuicao) {
        this.fangshuicao = fangshuicao;
    }

    public String getFangguan() {
        return fangguan;
    }

    public void setFangguan(String fangguan) {
        this.fangguan = fangguan;
    }

    public String getXiaokuang() {
        return xiaokuang;
    }

    public void setXiaokuang(String xiaokuang) {
        this.xiaokuang = xiaokuang;
    }

    public String getBtzj() {
        return btzj;
    }

    public void setBtzj(String btzj) {
        this.btzj = btzj;
    }

    public String getBtsjq() {
        return btsjq;
    }

    public void setBtsjq(String btsjq) {
        this.btsjq = btsjq;
    }

    public String getOrdermoney_dx() {
        return ordermoney_dx;
    }

    public void setOrdermoney_dx(String ordermoney_dx) {
        this.ordermoney_dx = ordermoney_dx;
    }

    public String getPcflag() {
        return pcflag;
    }

    public void setPcflag(String pcflag) {
        this.pcflag = pcflag;
    }

    public String getSc_flag() {
        return sc_flag;
    }

    public void setSc_flag(String sc_flag) {
        this.sc_flag = sc_flag;
    }

    public String getBmustbook() {
        return bmustbook;
    }

    public void setBmustbook(String bmustbook) {
        this.bmustbook = bmustbook;
    }

    public String getcDefine1() {
        return cDefine1;
    }

    public void setcDefine1(String cDefine1) {
        this.cDefine1 = cDefine1;
    }

    public int getNq_flag() {
        return nq_flag;
    }

    public void setNq_flag(int nq_flag) {
        this.nq_flag = nq_flag;
    }

    public String getQianyinche() {
        return qianyinche;
    }

    public void setQianyinche(String qianyinche) {
        this.qianyinche = qianyinche;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getQianyinxiao() {
        return qianyinxiao;
    }

    public void setQianyinxiao(String qianyinxiao) {
        this.qianyinxiao = qianyinxiao;
    }

    public String getBanhuang() {
        return banhuang;
    }

    public void setBanhuang(String banhuang) {
        this.banhuang = banhuang;
    }

    public String getBianliang() {
        return bianliang;
    }

    public void setBianliang(String bianliang) {
        this.bianliang = bianliang;
    }

    public String getWcheng_type() {
        return wcheng_type;
    }

    public void setWcheng_type(String wcheng_type) {
        this.wcheng_type = wcheng_type;
    }

    public String getWcheng_num() {
        return wcheng_num;
    }

    public void setWcheng_num(String wcheng_num) {
        this.wcheng_num = wcheng_num;
    }

    public String getBox_left() {
        return box_left;
    }

    public void setBox_left(String box_left) {
        this.box_left = box_left;
    }

    public String getBox_right() {
        return box_right;
    }

    public void setBox_right(String box_right) {
        this.box_right = box_right;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getGangquan() {
        return gangquan;
    }

    public void setGangquan(String gangquan) {
        this.gangquan = gangquan;
    }

    public String getGangquan_num() {
        return gangquan_num;
    }

    public void setGangquan_num(String gangquan_num) {
        this.gangquan_num = gangquan_num;
    }

    public String getLuntai() {
        return luntai;
    }

    public void setLuntai(String luntai) {
        this.luntai = luntai;
    }

    public String getLuntai_num() {
        return luntai_num;
    }

    public void setLuntai_num(String luntai_num) {
        this.luntai_num = luntai_num;
    }

    public String getChezhou() {
        return chezhou;
    }

    public void setChezhou(String chezhou) {
        this.chezhou = chezhou;
    }

    public String getHouxuan() {
        return houxuan;
    }

    public void setHouxuan(String houxuan) {
        this.houxuan = houxuan;
    }

    public String getQianxuan() {
        return qianxuan;
    }

    public void setQianxuan(String qianxuan) {
        this.qianxuan = qianxuan;
    }

    public String getZhongxinju() {
        return zhongxinju;
    }

    public void setZhongxinju(String zhongxinju) {
        this.zhongxinju = zhongxinju;
    }

    public String getHoumen_mark() {
        return houmen_mark;
    }

    public void setHoumen_mark(String houmen_mark) {
        this.houmen_mark = houmen_mark;
    }

    public String getJinshengqi_mark() {
        return jinshengqi_mark;
    }

    public void setJinshengqi_mark(String jinshengqi_mark) {
        this.jinshengqi_mark = jinshengqi_mark;
    }

    public String getWcheng_mark() {
        return wcheng_mark;
    }

    public void setWcheng_mark(String wcheng_mark) {
        this.wcheng_mark = wcheng_mark;
    }

    public String getCelanban_mark() {
        return celanban_mark;
    }

    public void setCelanban_mark(String celanban_mark) {
        this.celanban_mark = celanban_mark;
    }

    public String getBox_mark() {
        return box_mark;
    }

    public void setBox_mark(String box_mark) {
        this.box_mark = box_mark;
    }

    public String getPati() {
        return pati;
    }

    public void setPati(String pati) {
        this.pati = pati;
    }

    public String getPenggan() {
        return penggan;
    }

    public void setPenggan(String penggan) {
        this.penggan = penggan;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getCelanban() {
        return celanban;
    }

    public void setCelanban(String celanban) {
        this.celanban = celanban;
    }

    public String getDiban() {
        return diban;
    }

    public void setDiban(String diban) {
        this.diban = diban;
    }

    public String getHoumen() {
        return houmen;
    }

    public void setHoumen(String houmen) {
        this.houmen = houmen;
    }

    public String getShuibaokong() {
        return shuibaokong;
    }

    public void setShuibaokong(String shuibaokong) {
        this.shuibaokong = shuibaokong;
    }

    public int getSqcopy() {
        return sqcopy;
    }

    public void setSqcopy(int sqcopy) {
        this.sqcopy = sqcopy;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getSq_remark() {
        return sq_remark;
    }

    public void setSq_remark(String sq_remark) {
        this.sq_remark = sq_remark;
    }

    public String getLiangbaojiao() {
        return liangbaojiao;
    }

    public void setLiangbaojiao(String liangbaojiao) {
        this.liangbaojiao = liangbaojiao;
    }

    public String getBooknum() {
        return booknum;
    }


    public void setBooknum(String booknum) {
        this.booknum = booknum;
    }


    public String getFbookratio() {
        return fbookratio;
    }

    public void setFbookratio(String fbookratio) {
        this.fbookratio = fbookratio;
    }


    public String getSingleprice() {
        return singleprice;
    }

    public void setSingleprice(String singleprice) {
        this.singleprice = singleprice;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getcCusCode() {
        return cCusCode;
    }

    public void setcCusCode(String cCusCode) {
        this.cCusCode = cCusCode;
    }



    public String getcInvCCode() {
        return cInvCCode;
    }

    public void setcInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
    }

    public String getcInvCName() {
        return cInvCName;
    }

    public void setcInvCName(String cInvCName) {
        this.cInvCName = cInvCName;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardMoney() {
        return standardMoney;
    }

    public void setStandardMoney(String standardMoney) {
        this.standardMoney = standardMoney;
    }

    public List<Inventory> getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(List<Inventory> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getcCusName() {
        return cCusName;
    }

    public void setcCusName(String cCusName) {
        this.cCusName = cCusName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getCarstyle() {
        return carstyle;
    }

    public void setCarstyle(String carstyle) {
        this.carstyle = carstyle;
    }

    public String getPayedmoney() {
        return payedmoney;
    }

    public void setPayedmoney(String payedmoney) {
        this.payedmoney = payedmoney;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getcCusAddress() {
        return cCusAddress;
    }

    public void setcCusAddress(String cCusAddress) {
        this.cCusAddress = cCusAddress;
    }

    public int getStatues() {
        return statues;
    }

    public void setStatues(int statues) {
        this.statues = statues;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getcCusHand() {
        return cCusHand;
    }

    public void setcCusHand(String cCusHand) {
        this.cCusHand = cCusHand;
    }

    public String getcCusPerson() {
        return cCusPerson;
    }

    public void setcCusPerson(String cCusPerson) {

        this.cCusPerson = cCusPerson;
    }

}
