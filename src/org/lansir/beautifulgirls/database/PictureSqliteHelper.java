package org.lansir.beautifulgirls.database;

import org.lansir.beautifulgirls.database.DbTable.CrashRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.PictureJudgeRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.StatisticsRecordColumns;
import org.lansir.beautifulgirls.database.DbTable.Table;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PictureSqliteHelper extends SQLiteOpenHelper {
	
	public static final int DB_VERSION = 1;

    public static String DB_NAME = "beautifulgirls.db";
    
    private final String CREATE_PICTURE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + Table.PICTURE_JUDGE + "("
            + PictureJudgeRecordColumns.ID + " INTEGER PRIMARY KEY autoincrement," + PictureJudgeRecordColumns.STATE
            + " INTEGER NOT NULL DEFAULT 0)";

    private final String CREATE_CTASH_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + Table.CRASH + "("
            + CrashRecordColumns.ID + " INTEGER PRIMARY KEY autoincrement," + CrashRecordColumns.OCCURE_TIMES
            + " BIGINT NOT NULL DEFAULT 0," + CrashRecordColumns.CONTENT + " TEXT ," + CrashRecordColumns.STATUS
            + " INTEGER NOT NULL DEFAULT -1" + ")";
    
    private final String CREATE_STATISTICS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + Table.STATISTICS + "("
            + StatisticsRecordColumns.ID + " INTEGER PRIMARY KEY," + StatisticsRecordColumns.TIMES
            + " INTEGER NOT NULL DEFAULT 0" + ")";
    
    public PictureSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public PictureSqliteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.createEventTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createEventTable(SQLiteDatabase db) {
        db.execSQL(CREATE_CTASH_TABLE_SQL);
        db.execSQL(CREATE_PICTURE_TABLE_SQL);
        db.execSQL(CREATE_STATISTICS_TABLE_SQL);
        LogUtil.d("create event db");
    }
}
