package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.Privilege;

/**
 * 
 * <p>
 * Title: 权限类dao接口
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 辽宁众信同行软件开发有限公司
 * </p>
 * 
 * @author tonny
 * @version 1.0
 * @Date 2009-10-21
 */
public interface AuthDAO extends EntityDAO<Privilege> {

	/**
	 * 列出所有的权限
	 * 
	 * @return
	 */
	@ListSupport(order = "name")
	List<Privilege> list();

}
