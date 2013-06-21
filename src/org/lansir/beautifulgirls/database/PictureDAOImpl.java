package org.lansir.beautifulgirls.database;

import org.lansir.beautifulgirls.database.DbTable.PictureJudgeRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.Table;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.ContentValues;

public class PictureDAOImpl extends AbstractDAO implements IPictureDAO {

	@Override
	public boolean clickGood(Integer pid) {
		ContentValues value = new ContentValues();
		value.put(PictureJudgeRecordColumns.ID, pid);
        long rowid = this.getWritableDatabase().insert(Table.PICTURE_JUDGE, null, value);
        if (-1 == rowid) {
            LogUtil.e("insert CrashRecord Failed");
            return false;
        }
		return true;
	}

	@Override
	public boolean clickBad(Integer pid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getJudge(Integer pid) {
		// TODO Auto-generated method stub
		return null;
	}

}
