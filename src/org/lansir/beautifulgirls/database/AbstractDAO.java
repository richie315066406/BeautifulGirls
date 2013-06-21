
package org.lansir.beautifulgirls.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractDAO {
    private SQLiteOpenHelper dbHelper;

    public SQLiteOpenHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.dbHelper.getWritableDatabase();
    }

}
