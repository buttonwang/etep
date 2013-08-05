/**
 * 
 */
package com.ambow.studyflow.event;

import org.springframework.util.StringUtils;

import com.ambow.studyflow.dao.ProcessEventDao;
import com.ambow.studyflow.service.IProcessService;

/**
 * 
 * 事件处理器基类
 * 所有的事件处理器需实现此接口
 * 
 * @author suxiaoyong
 *
 */
public abstract class EventHandler {

	protected ProcessEventDao processEventDao;

	public abstract void doAction(long processInstanceId);

	/**
	 * 取得事件处理器名称
	 * @return
	 */
	public String getBeanName() {
		return StringUtils.uncapitalize(getClass().getSimpleName());
	}

	/**
	 * 结束此事件
	 * @param processInstanceId
	 */
	public void finishEvent(long processInstanceId) {
		processEventDao.removeEvent(processInstanceId, getBeanName());
	}

	public void setProcessEventDao(ProcessEventDao processEventDao) {
		this.processEventDao = processEventDao;
	}

}
