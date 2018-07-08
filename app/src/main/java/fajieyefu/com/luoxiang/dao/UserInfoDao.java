package fajieyefu.com.luoxiang.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import fajieyefu.com.luoxiang.bean.UserInfo;
import fajieyefu.com.luoxiang.db.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_INFO.
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property RembPsw = new Property(3, Boolean.class, "rembPsw", false, "REMB_PSW");
        public final static Property RId = new Property(4, Integer.class, "rId", false, "R_ID");
        public final static Property CDepName = new Property(5, String.class, "cDepName", false, "C_DEP_NAME");
        public final static Property CDepCode = new Property(6, String.class, "cDepCode", false, "C_DEP_CODE");
        public final static Property Enable_flag = new Property(7, Integer.class, "enable_flag", false, "ENABLE_FLAG");
    };


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_INFO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'USERNAME' TEXT," + // 1: username
                "'PASSWORD' TEXT," + // 2: password
                "'REMB_PSW' INTEGER," + // 3: rembPsw
                "'R_ID' INTEGER," + // 4: rId
                "'C_DEP_NAME' TEXT," + // 5: cDepName
                "'C_DEP_CODE' TEXT," + // 6: cDepCode
                "'ENABLE_FLAG' INTEGER);"); // 7: enable_flag
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        Boolean rembPsw = entity.getRembPsw();
        if (rembPsw != null) {
            stmt.bindLong(4, rembPsw ? 1l: 0l);
        }
 
        Integer rId = entity.getRId();
        if (rId != null) {
            stmt.bindLong(5, rId);
        }
 
        String cDepName = entity.getCDepName();
        if (cDepName != null) {
            stmt.bindString(6, cDepName);
        }
 
        String cDepCode = entity.getCDepCode();
        if (cDepCode != null) {
            stmt.bindString(7, cDepCode);
        }
 
        Integer enable_flag = entity.getEnable_flag();
        if (enable_flag != null) {
            stmt.bindLong(8, enable_flag);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // rembPsw
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // rId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // cDepName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // cDepCode
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // enable_flag
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRembPsw(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setRId(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setCDepName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCDepCode(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEnable_flag(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
