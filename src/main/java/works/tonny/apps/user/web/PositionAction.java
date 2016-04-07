/**  
 * @Title: PositionAction.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2011-11-16 下午2:21:05
 */
package works.tonny.apps.user.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.support.EntityLazyProxy;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.JobLevel;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.service.DepartmentService;
import works.tonny.apps.user.service.JobService;
import works.tonny.apps.user.service.PositionEntityService;

/**
 * @ClassName: PositionAction
 * @Description:
 * @author Tonny
 * @date 2011-11-16 下午2:21:05
 * @version 1.0
 */
public class PositionAction extends AuthedAction {
	protected PositionEntityService positionService;

	protected DepartmentService departmentService;

	private JobService jobService;

	private Position position;

	private String deptId;

	private String id;

	public String list() {
		List<Position> list = positionService.getPositions(deptId);
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String add() {
		if (StringUtils.isNotBlank(deptId)) {
			Department department = departmentService.findById(deptId);
			List<Position> list = positionService.parentPositions(deptId);
			request.setAttribute("parent", department);
			request.setAttribute("jobs", jobService.list());
			request.setAttribute("positions", list);
		}
		return SUCCESS;
	}

	public String edit() {
		position = positionService.findById(id);
		List<Position> list = positionService.parentPositions(position.getDepartment().getId());
		list.remove(position);
		EntityLazyProxy proxy = ServiceManager.lookup(ServiceManager.LAZY_SERVICE);
		JobLevel jobLevel = position.getJobLevel();
		proxy.refresh(jobLevel);
		request.setAttribute("positions", list);
		request.setAttribute("parent", position.getDepartment());
		request.setAttribute("jobs", jobService.list());
		return SUCCESS;
	}

	public String save() {
		String parent = request.getParameter("parentId");
		position.setJobLevel(jobService.getJobLevel(getParameter("jobLevel")));
		positionService.create(position, parent);
		return SUCCESS;
	}

	public String update() {
		position.setJobLevel(jobService.getJobLevel(getParameter("jobLevel")));
		positionService.update(position);
		return SUCCESS;
	}

	public String delete() {
		positionService.remove(id);
		return SUCCESS;
	}

	public String move() {
		positionService.move(id, getParameter("departmentId"));
		position = positionService.findById(id);
		return SUCCESS;
	}

	public void setPositionService(PositionEntityService positionService) {
		this.positionService = positionService;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PositionEntityService getPositionService() {
		return positionService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getId() {
		return id;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

}
