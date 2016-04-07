/**
 * 
 */
package works.tonny.apps.task;

import java.util.Date;

/**
 * 
 * @author tonny
 * @date 2012-10-9
 * @version 1.0.0
 */
public interface TaskService {

	/**
	 * 执行定时任务
	 * 
	 * @param task 定时任务
	 */
	void newTask(Task task);

	/**
	 * 间隔一段时间后执行定时任务
	 * 
	 * @param task 定时任务
	 */
	void newTask(Task task, long delay);

	void newTask(Task task, long delay, long period);

	void newTask(Task task, long delay, long period, int times);

	void newTask(Task task, Date executeTime, long period);

	void newTask(Task task, Date executeTime, long period, int times);

}
