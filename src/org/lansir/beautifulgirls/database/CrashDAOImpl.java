
package org.lansir.beautifulgirls.database;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.database.DbTable.CrashRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.Table;
import org.lansir.beautifulgirls.model.CrashRecord;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.ContentValues;
import android.database.Cursor;

public class CrashDAOImpl extends AbstractDAO implements ICrashRecordDAO {

    @Override
    public int saveCrashRecord(CrashRecord record) {
        ContentValues value = convertCrashReacord(record);
        long rowid = this.getWritableDatabase().insert(Table.CRASH, null, value);
        if (-1 == rowid) {
            LogUtil.e("insert CrashRecord Failed");
            return -1;
        }
        return 0;
    }

    private ContentValues convertCrashReacord(CrashRecord record) {
        ContentValues value = new ContentValues();
        value.put(CrashRecordColumns.OCCURE_TIMES, record.getOccurTime());
        value.put(CrashRecordColumns.CONTENT, record.getContent());
        value.put(CrashRecordColumns.STATUS, record.getStatus());
        if (record.getId() != 0)
            value.put(CrashRecordColumns.ID, record.getId());
        return value;
    }

    @Override
    public void deleteCrashRecord(int recordId, boolean deleteLogical) {
        if (deleteLogical) {
            ContentValues value = new ContentValues();
            value.put(CrashRecordColumns.STATUS, 0);
            this.getWritableDatabase().update(Table.CRASH, value, CrashRecordColumns.ID + "=?", new String[] {
                "" + recordId
            });
        } else
            this.getWritableDatabase().delete(Table.CRASH, CrashRecordColumns.ID + "=" + recordId, null);
    }

    @Override
    public void deleteAllHandledCrashRecord() {
        this.getWritableDatabase().delete(Table.CRASH, CrashRecordColumns.STATUS + "=0", null);
    }

    @Override
    public List<CrashRecord> getAllUnHandledCrashRecord() {
        Cursor cur = this.getReadableDatabase().query(Table.CRASH, null, CrashRecordColumns.STATUS + "=-1", null, null,
                null, null);
        List<CrashRecord> mList = new ArrayList<CrashRecord>();
        while (cur.moveToNext()) {
            CrashRecord crashCord = new CrashRecord();
            crashCord.setId(cur.getInt(cur.getColumnIndex(CrashRecordColumns.ID)));
            crashCord.setOccurTime(cur.getLong(cur.getColumnIndex(CrashRecordColumns.OCCURE_TIMES)));
            crashCord.setContent(cur.getString(cur.getColumnIndex(CrashRecordColumns.CONTENT)));
            crashCord.setStatus(cur.getInt(cur.getColumnIndex(CrashRecordColumns.STATUS)));
            mList.add(crashCord);
        }
        cur.close();
        return mList;
    }

    @Override
    public void updateAllUnHandledCrashRecord() {
        ContentValues value = new ContentValues();
        value.put(CrashRecordColumns.STATUS, 0);
        this.getWritableDatabase().update(Table.CRASH, value, CrashRecordColumns.STATUS + "=-1", null);
    }

    @Override
    public void deleteAllCrashRecord() {
        this.getWritableDatabase().delete(Table.CRASH, null, null);
    }

}
