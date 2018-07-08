package fajieyefu.com.luoxiang.dao;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fajieyefu.com.luoxiang.bean.AuditCount;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.LastCheckClueInfo;
import fajieyefu.com.luoxiang.bean.LastCheckInfo;
import fajieyefu.com.luoxiang.bean.PushNewsInfo;
import fajieyefu.com.luoxiang.bean.Region;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.bean.U8HrCt007;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.util.DaoManager;

/**
 * Created by Administrator on 2017-05-08.
 */

public class DaoBean {
    /**
     * 删除用户信息，密码
     */
    public static void deleteUserInfo() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        userInfoDao.deleteAll();

    }
    /**
     * 获取用户信息实体类
     *
     * @param id
     * @return
     */
    public static UserInfo getUseInfoById(long id) {

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        QueryBuilder<UserInfo> queryBuilder = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (UserInfo) query.unique();

    }

    /**
     * 批量查询
     *
     * @param code
     * @return
     */
    public static List<Inventory> getInventoryLikeCCode(String code) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        return queryBuilder.list();
    }

    //批量查询关联
    public static List<Inventory> getInventoryLikeCCode(String code, String text, String ze,String width,String height) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        List<Inventory> list = queryBuilder.list();
        List<Inventory> result = new ArrayList<>();
        if (list.size() != 0) {
            switch (code) {
                case "1508":case "1509":case "1510":case "0410":
                case "1512":case "1507":
                case "1503":case "1504":case "0411":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;

                case "1514": case "1513":case "0404":case "0405":
                    if (ze.equals("厂配")){
                        for (Inventory inventory : list) {
                            if (inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }
                    }

                    break;
                case "0308":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd()!=null){
                            String[] temp = inventory.getCInvStd().split("\\*");
                            if (temp.length>=1&&temp[temp.length - 1].equals(Integer.parseInt(width)-20+"")&&inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }

                    }
                    break;
                case "1505":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd()!=null&&inventory.getCInvStd().equals(width)&&inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020105":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd()!=null){
                            String[] temp = inventory.getCInvStd().split("\\*");
                            if (temp.length>=1&&temp[temp.length - 1].equals(height) && inventory.getCInvStd().startsWith(ze)&& inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }

                    }
                    break;
                case "020106":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd()!=null&&inventory.getCInvStd().equals(height) && inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020107":
                    Inventory inventoryCelanban = DaoBean.getSelectedInventoryLikeCCode("020105");
                    if (inventoryCelanban!=null&&inventoryCelanban.getCInvDefine1()!=null){
                        String celanBanCode = inventoryCelanban.getCInvDefine1();
                        if (celanBanCode!=null){
                            for (Inventory inventory : list) {
                                if (inventory.getCInvDefine1()!=null&&inventory.getCInvDefine1().equals(celanBanCode) ) {
                                    result.add(inventory);
                                }
                            }
                        }

                    }

                    break;
                case "020104":
                    for (Inventory inventory : list) {
                        String[] temp = inventory.getCInvStd().split("\\-");
                        if (temp.length>1){
                            if (inventory.getCInvStd()!=null&&temp[1].equals(height)&&inventory.getCInvStd().startsWith(ze)) {
                                result.add(inventory);
                            }
                        }

                    }
                    break;
                case "1511":
                    if (height.equals("无栏板")){
                        for (Inventory inventory : list){
                            if (inventory.getCInvName().contains(ze)&&inventory.getCInvName().contains("平板车")){
                                result.add(inventory);
                            }
                        }

                    }else{
                        for (Inventory inventory : list) {
                            try {
                                String temp = inventory.getCInvName();
                                if (text.contains("下打") && temp.contains("下打") && temp.contains(ze)) {
                                    result.add(inventory);
                                } else if (!text.contains("下打") && temp.contains("对开") && temp.contains(ze)) {
                                    result.add(inventory);
                                }
                            } catch (Exception e) {

                            }

                        }
                    }

                    break;
                case "1506":
                    for (Inventory inventory : list) {
                        String temp = inventory.getCInvName();
                        if (temp.contains(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "1502":
                    if (!text.equals("无")){
                        for (Inventory inventory : list) {
                            if (inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }
                    }

                    break;
                case "1524":
                    if (!text.equals("0")){
                        for (Inventory inventory : list) {
                            if (inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }
                    }

                    break;
            }

            for (Inventory inventory : list) {
                inventory.setIsCurrent(0);
                inventoryDao.update(inventory);
            }
        }


        if (result.size() != 0) {
            Inventory inventory = result.get(0);
            inventory.setIsCurrent(1);
            inventoryDao.update(inventory);
        }

        return result;
    }

    //批量查询关联
    public static List<Inventory> getInventoryLikeCCodeSkeleton(String code, String text, String ze,String width,String height) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        List<Inventory> list = queryBuilder.list();
        List<Inventory> result = new ArrayList<>();
        if (list.size() != 0) {
            switch (code) {
                case "0410":case "1512":case "1502":case "1507":
                case "1503":case "1504":case "0411":
                case "1513":case "1516":
                case "1518":case "1519":case "1520":
                case "1521":case "1522":case "1523":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;

               case "0404":case "0405":
                    if (ze.equals("厂配")){
                        for (Inventory inventory : list) {
                            if (inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }
                    }

                    break;
                case "1511":
                    for (Inventory inventory : list) {
                        try {
                            String temp = inventory.getCInvName();
                            if (temp.contains(text) && temp.contains(ze)) {
                                result.add(inventory);
                            }
                        } catch (Exception e) {

                        }

                    }
                    break;
                case "1506":
                    for (Inventory inventory : list) {
                        String temp = inventory.getCInvName();
                        String tempInvstd =inventory.getCInvStd();
                        if (tempInvstd!=null&&temp.contains(text)&&tempInvstd.equals(width)&&temp.contains(ze)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "1517":
                    for (Inventory inventory : list) {
                        String temp = inventory.getCInvName();
                        String tempInvstd =inventory.getCInvStd();
                        if ( tempInvstd!=null&&temp.equals(text)&&tempInvstd.contains(width)&&tempInvstd.contains(ze)) {
                            result.add(inventory);
                        }
                    }

                    break;
                case "1508":case "1509":case "1510":
                    for (Inventory inventory:list){
                        String tempInvstd =inventory.getCInvStd();
                        if (tempInvstd!=null&&inventory.getCInvName().equals(text)&&tempInvstd.contains(ze)&&tempInvstd.contains(width)){
                            result.add(inventory);
                        }
                    }
                    break;

                case "1514":
                    if (width.equals("48英尺（可抽拉至53英尺）")){
                        for (Inventory inventory : list) {
                            String tempString = inventory.getCInvStd();
                            if (tempString!=null&&tempString.contains("4轴")&&inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }

                    }
                    else if (width.contains("20英尺")&&height.equals("2")){
                        for (Inventory inventory : list) {
                            String tempString = inventory.getCInvStd();
                            if (tempString!=null&&inventory.getCInvName().equals(text)&&tempString.contains("2轴")&&tempString.contains("20英尺")) {
                                result.add(inventory);
                            }
                        }

                    }else{
                        for (Inventory inventory : list) {
                            String tempString = inventory.getCInvStd();
                            if (tempString!=null&&tempString.contains("3轴")&&inventory.getCInvName().equals(text)) {
                                result.add(inventory);
                            }
                        }
                    }

                    break;

                /*case "1516":
                    if (text.equals("有")){
                        for (Inventory inventory : list){
                            inventory.setIsCurrent(1);
                            inventoryDao.update(inventory);
                            result.add(inventory);
                        }
                        return result;
                    }else{
                        for (Inventory inventory : list){
                            inventory.setIsCurrent(0);
                            inventoryDao.update(inventory);
                            result.add(inventory);
                        }
                        return result;
                    }*/

            }

            for (Inventory inventory : list) {
                inventory.setIsCurrent(0);
                inventoryDao.update(inventory);
            }
        }


        if (result.size() != 0) {
            Inventory inventory = result.get(0);
            inventory.setIsCurrent(1);
            inventoryDao.update(inventory);
        }

        return result;
    }

    /**
     * 查询选中的某个值
     *
     * @param code
     * @return
     */
    public static Inventory getSelectedInventoryLikeCCode(String code) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        queryBuilder.where(InventoryDao.Properties.IsCurrent.eq(1));
        Query<Inventory> query = queryBuilder.build();
        List<Inventory> datas = query.list();
        if (datas.size() == 0) {
            return null;
        }
        return datas.get(0);
    }

    /**
     * 通過物料编号获取物料
     *
     * @param cInvCode
     * @return
     */
    public static Inventory getInventoryByCode(String cInvCode) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.eq(cInvCode));
        Query query = queryBuilder.build();
        return (Inventory) query.unique();
    }

    /**
     * 批量插入存货分类
     *
     * @param list
     */
    public static void insertInventoryClassList(final List<InventoryClass> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        inventoryClassDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    InventoryClass inventoryClass = list.get(i);
                    inventoryClassDao.insertOrReplace(inventoryClass);
                }
            }
        });

    }

    /**
     * 删除所有存货分类
     */
    public static void deleteInventoryClassAll() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        inventoryClassDao.deleteAll();

    }

    /**
     * 获取所有存货分类信息
     *
     * @return
     */
    public static List<InventoryClass> loadAllInventoryClass() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        List<InventoryClass> list;
        try {
            list = inventoryClassDao.loadAll();


        } catch (SQLiteException e) {
            list = new ArrayList<>();

        }
        return list;

    }

    /**
     * 通过id获取存货分类
     *
     * @param id
     * @return
     */
    public static InventoryClass getInventoryClassById(long id) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        QueryBuilder<InventoryClass> queryBuilder = inventoryClassDao.queryBuilder().where(InventoryClassDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (InventoryClass) query.unique();
    }

    /**
     * 批量插入存货档案
     *
     * @param list
     */
    public static void insertInventoryList(final List<Inventory> list) {

        if (list == null || list.isEmpty()) {
            return;
        }
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final InventoryDao inventoryDao = daoSession.getInventoryDao();
        inventoryDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Inventory inventory = list.get(i);
                    inventoryDao.insertOrReplace(inventory);
                }
            }
        });

    }

    /**
     * 删除所有存货档案
     */
    public static void deleteInventoryAll() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        inventoryDao.deleteAll();
    }
    //根据物料分类编码查询是否有选定的物料编码的selected数值为true;

    public static void upNoSelected(String cInvCCode, String cInvCode) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCCode.eq(cInvCCode), InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list = queryBuilder.list();
        for (Inventory inventory : list) {
            Log.i(inventory.getCInvCCode(), "數量:" + inventory.getCounts());
        }
        for (Inventory inventory : list) {
            inventory.setIsCurrent(0);
            inventoryDao.update(inventory);
        }
        Inventory it = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode
                .eq(cInvCode)).build().unique();
        it.setIsCurrent(1);
        inventoryDao.update(it);

    }

    public static List<Inventory> loadInventoryByCurrent() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list = queryBuilder.list();
        return list;
    }

    public static List<Inventory> loadAllInventory() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder();
        List<Inventory> list = queryBuilder.list();
        return list;
    }

    public static List<Inventory> loadInventoryByCurrentAndShiJia() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list = queryBuilder.list();
        List<Inventory> list2 = new ArrayList<>();
        for (Inventory inventory : list) {
            if (!inventory.getRealMoney().equals("0")) {
                list2.add(inventory);
            }
        }
        return list2;
    }

    public static AuditCount getAuditCountByUserName(String username) {

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        AuditCountDao auditCountDao = daoSession.getAuditCountDao();
        QueryBuilder<AuditCount> queryBuilder = auditCountDao.queryBuilder().where(UserInfoDao.Properties.Username.eq(username));
        Query query = queryBuilder.build();
        return (AuditCount) query.unique();

    }

    public static LastCheckInfo getLastCheckInfoByUserName(String username) {

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        LastCheckInfoDao lastCheckInfoDao = daoSession.getLastCheckInfoDao();
        QueryBuilder<LastCheckInfo> queryBuilder = lastCheckInfoDao.queryBuilder().where(LastCheckInfoDao.Properties.Username.eq(username));
        Query query = queryBuilder.build();
        return (LastCheckInfo) query.unique();

    }
/*
清除某个配置的默认选项 ,即此配置不选择
 */

    public static void onSelectInventory(List<Inventory> data) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        for (Inventory inventory : data) {
            inventory.setIsCurrent(0);
            inventoryDao.update(inventory);
        }


    }

    public static void updateCounts(String cInvCCode, int counts) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCCode.eq(cInvCCode), InventoryDao.Properties.IsCurrent.eq(1));
        //避免因为后台设置默认值时多选造成数值不唯一
        List<Inventory> list = queryBuilder.list();
        if (list.size() != 0) {
            list.get(0).setCounts(counts + "");
            inventoryDao.update(list.get(0));
        }

    }

    public static List<String> getInventoryNameByCCode(String code) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        List<Inventory> list = queryBuilder.list();
        List<String> result = new ArrayList<>();
        for (Inventory inventory : list) {
            if (!result.contains(inventory.getCInvName())) {
                result.add(inventory.getCInvName());
            }
        }
        return result;
    }

    public static List<Region> getRegionByParentCode(String parentCode) {

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        RegionDao regionDao = daoSession.getRegionDao();
        QueryBuilder<Region> queryBuilder = regionDao.queryBuilder().where(RegionDao.Properties.ParentID.eq(parentCode));
        return queryBuilder.list();

    }

    /**
     * 批量插入地区表数据
     *
     * @param list
     */
    public static void insertRegionList(final List<Region> list) {

        if (list == null || list.isEmpty()) {
            return;
        }
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final RegionDao regionDao = daoSession.getRegionDao();
        regionDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Region region = list.get(i);
                    regionDao.insertOrReplace(region);
                }
            }
        });

    }
    /**
     * 删除地区表所有数据
     */
    public static void deleteRegionAll() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        RegionDao regionDao = daoSession.getRegionDao();
        regionDao.deleteAll();
    }
    public static LastCheckClueInfo getLastCheckClueInfoByUserName(String username) {

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        LastCheckClueInfoDao lastCheckClueInfoDao = daoSession.getLastCheckClueInfoDao();
        QueryBuilder<LastCheckClueInfo> queryBuilder = lastCheckClueInfoDao.queryBuilder().where(LastCheckClueInfoDao.Properties.Username.eq(username));
        Query query = queryBuilder.build();
        return (LastCheckClueInfo) query.unique();

    }

    /**
     * 批量插入U8的行政区域表
     * @param list
     */
    public static void insertU8HrCt007List(final List<U8HrCt007> list) {

        if (list == null || list.isEmpty()) {
            return;
        }
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final U8HrCt007Dao dao = daoSession.getU8HrCt007Dao();
        dao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    U8HrCt007 bean = list.get(i);
                    dao.insertOrReplace(bean);
                }
            }
        });

    }

    /**
     * 删除U8行政区域表所有数据
     */
    public static void deleteU8HrCt007All() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        U8HrCt007Dao dao = daoSession.getU8HrCt007Dao();
        dao.deleteAll();
    }

    public static List<U8HrCt007> getU8HrCt007ListByIlevels(int iLevels) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        U8HrCt007Dao dao = daoSession.getU8HrCt007Dao();
        QueryBuilder<U8HrCt007> queryBuilder = dao.queryBuilder().where(U8HrCt007Dao.Properties.Ilevels.eq(iLevels));
        return queryBuilder.list();
    }

    public static List<U8HrCt007> getU8HrCt007ListByParentCode(String province_code) {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        U8HrCt007Dao dao = daoSession.getU8HrCt007Dao();
        QueryBuilder<U8HrCt007> queryBuilder = dao.queryBuilder().where(U8HrCt007Dao.Properties.CpCodeID.eq(province_code));
        return queryBuilder.list();
    }

    public static List<Inventory>  getTargetInventory(String code ,String name){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code + "%"));
        List<Inventory> list = queryBuilder.list();
        List<Inventory> result = new ArrayList<>();
        if (list.size() != 0) {

                for (Inventory inventory : list) {
                    if (inventory.getCInvName().equals(name)) {
                        result.add(inventory);
                    }
                }

        }
        return result;
    }
    /**
     * 插入推送消息
     * @param pushNewsInfo
     */
    public static void insertPushNewsInfo(final PushNewsInfo pushNewsInfo) {


        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final PushNewsInfoDao dao = daoSession.getPushNewsInfoDao();
        dao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {

                    dao.insertOrReplace(pushNewsInfo);

            }
        });

    }
    public static List<PushNewsInfo>  getTargetPushNewsInfo(String pushId){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        PushNewsInfoDao dao = daoSession.getPushNewsInfoDao();
        QueryBuilder<PushNewsInfo> queryBuilder = dao.queryBuilder().where(PushNewsInfoDao.Properties.PushId.eq(pushId));
        List<PushNewsInfo> list = queryBuilder.list();

        return list;
    }
}
