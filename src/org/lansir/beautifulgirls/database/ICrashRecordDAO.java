
package org.lansir.beautifulgirls.database;

import java.util.List;

import org.lansir.beautifulgirls.model.CrashRecord;

/**
 * Crash记录的持久层
 * 
 * @author brucedlan
 */
public interface ICrashRecordDAO {
    /**
     * 保存记录
     * 
     * @param record
     * @return
     */
    public int saveCrashRecord(CrashRecord record);

    /**
     * 删除指定id的记录
     * 
     * @param recordId crash记录的id
     */
    public void deleteCrashRecord(int recordId, boolean deleteLogical);

    /**
     * 删除所有已上传的记录
     */
    public void deleteAllHandledCrashRecord();

    /**
     * 删除所有记录
     */
    public void deleteAllCrashRecord();

    /**
     * 获取所有未上传的Crash记录
     * 
     * @return
     */
    public List<CrashRecord> getAllUnHandledCrashRecord();

    /**
     * 更新所有未上传的Crash记录为已上传状态
     */
    public void updateAllUnHandledCrashRecord();
}
