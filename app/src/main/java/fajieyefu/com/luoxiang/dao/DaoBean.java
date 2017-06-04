package fajieyefu.com.luoxiang.dao;

import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
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
     * @param id
     * @return
     */
    public static UserInfo getUseInfoById(long id){

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        QueryBuilder<UserInfo> queryBuilder = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (UserInfo) query.unique();

    }

    /**
     * 批量查询
     * @param code
     * @return
     */
    public static List<Inventory> getInventoryLikeCCode(String code){
        DaoSession daoSession =DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder= inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.like(code+"%"));
        return queryBuilder.list();
    }

    /**
     *通過物料编号获取物料
     * @param cInvCode
     * @return
     */
    public static Inventory getInventoryByCode(String cInvCode){
        DaoSession daoSession =DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder= inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode.eq(cInvCode));
        Query query = queryBuilder.build();
        return (Inventory) query.unique();
    }

    /**
     * 批量插入存货分类
     * @param list
     */
    public static void insertInventoryClassList(final List<InventoryClass> list){
        if (list==null||list.isEmpty()){
            return;
        }
        DaoSession daoSession=DaoManager.getInstance().getDaoSession();
        final InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        inventoryClassDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <list.size() ; i++) {
                    InventoryClass inventoryClass = list.get(i);
                    inventoryClassDao.insertOrReplace(inventoryClass);
                }
            }
        });

    }

    /**
     * 删除所有存货分类
     */
    public static void deleteInventoryClassAll(){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        inventoryClassDao.deleteAll();

    }
    /**
     * 获取所有存货分类信息
     * @return
     */
    public static List<InventoryClass> loadAllInventoryClass(){
        DaoSession daoSession =DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        List<InventoryClass> list;
        try{
            list = inventoryClassDao.loadAll();


        }catch (SQLiteException e){
            list = new ArrayList<>();

        }
        return  list;

    }

    /**
     *通过id获取存货分类
     * @param id
     * @return
     */
    public static InventoryClass getInventoryClassById(long id){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryClassDao inventoryClassDao = daoSession.getInventoryClassDao();
        QueryBuilder<InventoryClass> queryBuilder = inventoryClassDao.queryBuilder().where(InventoryClassDao.Properties.Id.eq(id));
        Query query = queryBuilder.build();
        return (InventoryClass) query.unique();
    }

    /**
     * 批量插入存货档案
     * @param list
     */
    public static void insertInventoryList(final List<Inventory> list){

        if (list==null||list.isEmpty()){
            return;
        }
        DaoSession daoSession=DaoManager.getInstance().getDaoSession();
        final InventoryDao inventoryDao = daoSession.getInventoryDao();
        inventoryDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <list.size() ; i++) {
                    Inventory inventory = list.get(i);
                    inventoryDao.insertOrReplace(inventory);
                }
            }
        });

    }
    /**
     * 删除所有存货档案
     */
    public static void deleteInventoryAll(){
        DaoSession daoSession =DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao= daoSession.getInventoryDao();
        inventoryDao.deleteAll();
    }
    //根据物料分类编码查询是否有选定的物料编码的selected数值为true;

    public static void upNoSelected(String cInvCCode,String cInvCode){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao= daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder= inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCCode.eq(cInvCCode),InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list= queryBuilder.list();
        for (Inventory inventory :list){
            inventory.setIsCurrent(0);
            inventoryDao.update(inventory);
        }
        Inventory it = inventoryDao.queryBuilder().where(InventoryDao.Properties.CInvCode
                .eq(cInvCode)).build().unique();
        it.setIsCurrent(1);
        inventoryDao.update(it);


    }

    public static List<Inventory> loadInventoryByCurrent(){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list =queryBuilder.list();
        return list;
    }

    public static List<Inventory> loadInventoryByCurrentAndShiJia(){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        InventoryDao inventoryDao = daoSession.getInventoryDao();
        QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder().where(InventoryDao.Properties.IsCurrent.eq(1));
        List<Inventory> list =queryBuilder.list();
        List<Inventory> list2 = new ArrayList<>();
        for (Inventory inventory:list){
            if (!inventory.getRealMoney().equals("0")){
                list2.add(inventory);
            }
        }
        return list2;
    }

}
