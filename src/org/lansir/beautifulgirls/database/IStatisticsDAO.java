
package org.lansir.beautifulgirls.database;

import java.util.List;

import org.lansir.beautifulgirls.model.Statistics;
/**
 * 统计
 * 
 * @author brucelan
 */
public interface IStatisticsDAO {
    /**
     * 根据id，将times+1
     * 
     * @param record
     * @return
     */
    int incRecord(int id);

    /**
     * 获取所有记录，主要用于上报统计时用
     * 
     * @return
     */
    public List<Statistics> getAllRecord();
    
    
    /**
     * 清0
     */
    public void clearAllRecord ();

}
