package fajieyefu.com.luoxiang.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import fajieyefu.com.luoxiang.bean.Inventory;
import fajieyefu.com.luoxiang.db.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table INVENTORY.
*/
public class InventoryDao extends AbstractDao<Inventory, Long> {

    public static final String TABLENAME = "INVENTORY";

    /**
     * Properties of entity Inventory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CInvCode = new Property(1, String.class, "cInvCode", false, "C_INV_CODE");
        public final static Property CInvName = new Property(2, String.class, "cInvName", false, "C_INV_NAME");
        public final static Property DeMoney = new Property(3, String.class, "deMoney", false, "DE_MONEY");
        public final static Property RealMoney = new Property(4, String.class, "realMoney", false, "REAL_MONEY");
        public final static Property Counts = new Property(5, String.class, "counts", false, "COUNTS");
        public final static Property Weight = new Property(6, String.class, "weight", false, "WEIGHT");
    };


    public InventoryDao(DaoConfig config) {
        super(config);
    }
    
    public InventoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'INVENTORY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'C_INV_CODE' TEXT," + // 1: cInvCode
                "'C_INV_NAME' TEXT," + // 2: cInvName
                "'DE_MONEY' TEXT," + // 3: deMoney
                "'REAL_MONEY' TEXT," + // 4: realMoney
                "'COUNTS' TEXT," + // 5: counts
                "'WEIGHT' TEXT);"); // 6: weight
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'INVENTORY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Inventory entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String cInvCode = entity.getCInvCode();
        if (cInvCode != null) {
            stmt.bindString(2, cInvCode);
        }
 
        String cInvName = entity.getCInvName();
        if (cInvName != null) {
            stmt.bindString(3, cInvName);
        }
 
        String deMoney = entity.getDeMoney();
        if (deMoney != null) {
            stmt.bindString(4, deMoney);
        }
 
        String realMoney = entity.getRealMoney();
        if (realMoney != null) {
            stmt.bindString(5, realMoney);
        }
 
        String counts = entity.getCounts();
        if (counts != null) {
            stmt.bindString(6, counts);
        }
 
        String weight = entity.getWeight();
        if (weight != null) {
            stmt.bindString(7, weight);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Inventory readEntity(Cursor cursor, int offset) {
        Inventory entity = new Inventory( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // cInvCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // cInvName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // deMoney
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // realMoney
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // counts
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // weight
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Inventory entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCInvCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCInvName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDeMoney(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRealMoney(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCounts(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWeight(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Inventory entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Inventory entity) {
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