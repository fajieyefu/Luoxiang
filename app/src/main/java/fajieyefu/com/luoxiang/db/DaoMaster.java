package fajieyefu.com.luoxiang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import fajieyefu.com.luoxiang.dao.UserInfoDao;
import fajieyefu.com.luoxiang.dao.InventoryClassDao;
import fajieyefu.com.luoxiang.dao.InventoryDao;
import fajieyefu.com.luoxiang.dao.AuditCountDao;
import fajieyefu.com.luoxiang.dao.LastCheckInfoDao;
import fajieyefu.com.luoxiang.dao.RegionDao;
import fajieyefu.com.luoxiang.dao.LastCheckClueInfoDao;
import fajieyefu.com.luoxiang.dao.U8HrCt007Dao;
import fajieyefu.com.luoxiang.dao.PushNewsInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        UserInfoDao.createTable(db, ifNotExists);
        InventoryClassDao.createTable(db, ifNotExists);
        InventoryDao.createTable(db, ifNotExists);
        AuditCountDao.createTable(db, ifNotExists);
        LastCheckInfoDao.createTable(db, ifNotExists);
        RegionDao.createTable(db, ifNotExists);
        LastCheckClueInfoDao.createTable(db, ifNotExists);
        U8HrCt007Dao.createTable(db, ifNotExists);
        PushNewsInfoDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        UserInfoDao.dropTable(db, ifExists);
        InventoryClassDao.dropTable(db, ifExists);
        InventoryDao.dropTable(db, ifExists);
        AuditCountDao.dropTable(db, ifExists);
        LastCheckInfoDao.dropTable(db, ifExists);
        RegionDao.dropTable(db, ifExists);
        LastCheckClueInfoDao.dropTable(db, ifExists);
        U8HrCt007Dao.dropTable(db, ifExists);
        PushNewsInfoDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserInfoDao.class);
        registerDaoClass(InventoryClassDao.class);
        registerDaoClass(InventoryDao.class);
        registerDaoClass(AuditCountDao.class);
        registerDaoClass(LastCheckInfoDao.class);
        registerDaoClass(RegionDao.class);
        registerDaoClass(LastCheckClueInfoDao.class);
        registerDaoClass(U8HrCt007Dao.class);
        registerDaoClass(PushNewsInfoDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
