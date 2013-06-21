
package org.lansir.beautifulgirls.database;

public class DbTable {

    public class Table {
        public static final String PICTURE_JUDGE = "picture_judge";
        public static final String CRASH = "crash";
        public static final String STATISTICS = "statistics";
    }

    /**
     * 统计记录
     */

    public class StatisticsRecordColumns {
        public static final String ID = "id";
        public static final String TIMES = "times";
    }

    /**
     * Crash记录
     * 
     * @author brucedlan
     */
    public class CrashRecordColumns {
        // local id
        public final static String ID = "crash_id";
        public final static String OCCURE_TIMES = "occure_times";
        public final static String CONTENT = "content";
        public final static String STATUS = "status";
    }

    /**
     * 事件阅读记录
     * 
     * @author brucedlan
     */
    public static class PictureJudgeRecordColumns {
        // local id
        public static final String ID = "picture_id";
        public static final String STATE = "state";
    }


}
