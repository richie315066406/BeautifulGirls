
package org.lansir.beautifulgirls.database;

import android.content.Context;

public class DaoFactory {

    private PictureSqliteHelper dbHelper;

    public DaoFactory(Context context) {
        this.dbHelper = new PictureSqliteHelper(context);
    }

    public ICrashRecordDAO getCrashRecordDAO() {
        CrashDAOImpl dao = new CrashDAOImpl();
        dao.setDbHelper(dbHelper);
        return dao;
    }

    public IStatisticsDAO getStatisticsDAO() {
        StatisticsDAOImpl dao = new StatisticsDAOImpl();
        dao.setDbHelper(dbHelper);
        return dao;
    }

}
