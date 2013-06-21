package org.lansir.beautifulgirls.database;

/**
 * 图片评价接口
 * @author brucelan
 *
 */
public interface IPictureDAO {
	/**
	 * 对图片进行好评
	 * @param pid
	 * @return
	 */
	public boolean clickGood(Integer pid);
	
	/**
	 * 对图片进行差评
	 * @param pid
	 * @return
	 */
	public boolean clickBad(Integer pid);
	
	/**
	 * 获取图片评价
	 * @param pid
	 * @return
	 */
	public Integer getJudge(Integer pid);
}
