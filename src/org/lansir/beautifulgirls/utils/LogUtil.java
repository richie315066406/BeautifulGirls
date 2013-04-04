
package org.lansir.beautifulgirls.utils;

import android.util.Log;


/**
 * 基本的类，主要提供一些基础的方法，为了收拢一些比较乱的接口使用 目前只针对LogUtil，这里通过获取LogUtil的当前类的名字来使用
 * 
 * @author brooksfan
 */
public class LogUtil {

    private static final String format = "[%s:%d] %s";

    public enum LogUtilUtilState {
        INFO, ERROR, ALL, NONE;
    }

    public static LogUtilUtilState CURRENT_STATE = LogUtilUtilState.ALL;

    /**
     * 通过配置文件来设置LogUtil的等级，这样我们就可以拿一个网络上面的apk包 来修改等级然后来LogUtil
     * 
     * @param level
     */
    public static void setCurrentStatus(LogUtilUtilState state) {
        if (state == null) {
            CURRENT_STATE = LogUtilUtilState.NONE;
        } else {
            CURRENT_STATE = state;
        }
    }

    public static void e(String msg) {

        switch (CURRENT_STATE) {
            case ALL:
            case ERROR:

                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                String className = elements[1].getClassName();
                String callerClassName = className.substring(className.lastIndexOf('.') + 1);
                String callerMethodName = elements[1].getMethodName();
                int callerMethodLineNumber = elements[1].getLineNumber();

                Log.e(callerClassName, String.format(format, callerMethodName, callerMethodLineNumber, msg));
        }
    }

    public static void d(String msg) {
        switch (CURRENT_STATE) {
            case ALL:
            case INFO:
                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                String className = elements[1].getClassName();
                String callerClassName = className.substring(className.lastIndexOf('.') + 1);
                String callerMethodName = elements[1].getMethodName();
                int callerMethodLineNumber = elements[1].getLineNumber();

                Log.d(callerClassName, String.format(format, callerMethodName, callerMethodLineNumber, msg));
        }
    }

    public static void v(String msg) {
        switch (CURRENT_STATE) {
            case ALL:
            case INFO:
                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                String className = elements[1].getClassName();
                String callerClassName = className.substring(className.lastIndexOf('.') + 1);
                String callerMethodName = elements[1].getMethodName();
                int callerMethodLineNumber = elements[1].getLineNumber();

                Log.v(callerClassName, String.format(format, callerMethodName, callerMethodLineNumber, msg));
        }
    }

}
