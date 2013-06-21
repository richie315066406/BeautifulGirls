
package org.lansir.beautifulgirls.database;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.database.DbTable.StatisticsRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.Table;
import org.lansir.beautifulgirls.model.Statistics;

import android.database.Cursor;

/**
 * 
 * @author brucelan
 *
 */
public class StatisticsDAOImpl extends AbstractDAO implements IStatisticsDAO {
	public final String INC_TABLE_SQL = "UPDATE " + Table.STATISTICS + " SET times = times + 1 where id=%d";
    public final String CLEAR_TABLE_SQL = "UPDATE " + Table.STATISTICS + " SET times = 0";;


    @Override
    public List<Statistics> getAllRecord() {
        ArrayList<Statistics> array = new ArrayList<Statistics>();
        Cursor cur = this.getReadableDatabase().query(Table.STATISTICS, null,
                String.format("%s>%d", StatisticsRecordColumns.TIMES, 0), null, null, null, null);

        while (cur.moveToNext()) {
            Statistics stat = new Statistics();
            stat.setId(cur.getInt(cur.getColumnIndex(StatisticsRecordColumns.ID)));
            stat.setTimes(cur.getInt(cur.getColumnIndex(StatisticsRecordColumns.TIMES)));
            array.add(stat);
        }
        cur.close();
        return array;
    }

    @Override
    public void clearAllRecord() {
        this.getReadableDatabase().execSQL(CLEAR_TABLE_SQL);
        
    }

	@Override
	public int incRecord(int id) {
		return 0;
	}

}
