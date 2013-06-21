
package org.lansir.beautifulgirls.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Crash记录
 * 
 * @author brucedlan
 */
public class CrashRecord {
    private int iId;

    /**
     * crash发生的时间
     */
    private long lOccurTime = 0;

    /**
     * crash的日志内容
     */
    private String sContent = null;

    /**
     * crash日志的状态，插入到数据库是默认是-1，当程序上报时更新状态为0(已上传)，上传完后自动删除
     */
    private int iStatus;

    public CrashRecord() {
        iStatus = -1;
        iId = 0;
    }

    public CrashRecord(long lOccurTime, String sContent) {
        super();
        this.lOccurTime = lOccurTime;
        this.sContent = sContent;
        iStatus = -1;
    }

    public int getId() {
        return iId;
    }

    public void setId(int iId) {
        this.iId = iId;
    }

    public long getOccurTime() {
        return lOccurTime;
    }

    public void setOccurTime(long lOccurTime) {
        this.lOccurTime = lOccurTime;
    }

    public String getContent() {
        return sContent;
    }

    public void setContent(String sContent) {
        this.sContent = sContent;
    }

    public int getStatus() {
        return iStatus;
    }

    public void setStatus(int iStatus) {
        this.iStatus = iStatus;
    }

}
