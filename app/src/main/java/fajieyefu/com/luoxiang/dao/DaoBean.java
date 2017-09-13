package fajieyefu.com.luoxiang.dao;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import fajieyefu.com.luoxiang.bean.AuditCount;
import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.bean.InventoryClass;
import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.db.DaoSession;
import fajieyefu.com.luoxiang.util.DaoManager;

/**
 * Created by Administrator on 2017-05-08.
 */

public class DaoBean {
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
                case "1514":case "1512":case "1502":case "1507":
                case "1503":case "1504":case "0411":case "0404":
                case "0405":case "1513":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "0308":
                    for (Inventory inventory : list) {
                        String[] temp = inventory.getCInvStd().split("\\*");
                        if (temp[temp.length - 1].equals(Integer.parseInt(width)-20+"")&&inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "1505":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd().equals(width)&&inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020105":
                    for (Inventory inventory : list) {
                        String[] temp = inventory.getCInvStd().split("\\*");
                        if (temp[temp.length - 1].equals(height) && inventory.getCInvStd().startsWith(ze)&& inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020106":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd().equals(height) && inventory.getCInvName().equals(text)) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020107":
                    for (Inventory inventory : list) {
                        if (inventory.getCInvStd().equals(height) ) {
                            result.add(inventory);
                        }
                    }
                    break;
                case "020104":
                    for (Inventory inventory : list) {
                        String[] temp = inventory.getCInvStd().split("\\-");
                        if (temp.length>1){
                            if (temp[1].equals(height)&&inventory.getCInvStd().startsWith(ze)) {
                                result.add(inventory);
                            }
                        }

                    }
                    break;
                case "1511":
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
                    break;
                case "1506":
                    for (Inventory inventory : list) {
                        String temp = inventory.getCInvName();
                        if (temp.contains(text)) {
                            result.add(inventory);
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
        Collections.sort(result);
        return result;
    }
}
