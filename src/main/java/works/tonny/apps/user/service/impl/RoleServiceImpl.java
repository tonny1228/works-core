/**  
 * @Title: RoleServiceImpl.java
 * @Package works.tonny.user.service.impl
 * @author Tonny
 * @date 2011-10-14 下午3:36:02
 */
package works.tonny.apps.user.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.dao.RoleDAO;
import works.tonny.apps.user.service.RoleEntityService;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色管理类
 * @author Tonny
 * @date 2011-10-14 下午3:36:02
 * @version 1.0
 */
public class RoleServiceImpl extends AuthedAbstractService implements RoleEntityService {

	private RoleDAO roleDAO;

	/**
	 * 角色信息监听器
	 */
	// private Map<String, RoleEntityListener> listeners;

	/*
	 * @see works.tonny.user.service.iface.RoleService#list(java.lang.String, int, int)
	 */
	public List<Role> list(String name) {
		return roleDAO.list(name);
	}

	/*
	 * @see works.tonny.user.service.iface.RoleService#create(works.tonny.user.model .Role)
	 */
	@Transactional(rollbackFor = Exception.class)
	public String create(Role role) {
		MessageManager.notify(Role.class, MessageEvent.BEFORE_CREATE, role);
		final String id = roleDAO.save(role);
		MessageManager.notify(Role.class, MessageEvent.CREATED, role);
		// if (listeners != null) {
		// for (RoleEntityListener listener : listeners.values()) {
		// listener.roleSaved(role);
		// }
		// }
		return (String) id;
	}

	/*
	 * @see works.tonny.user.service.iface.RoleService#update(works.tonny.user.model .Role)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Role role) {
		Role db = roleDAO.get(role.getId());
		db.setDescription(role.getDescription());
		db.setName(role.getName());
		MessageManager.notify(Role.class, MessageEvent.BEFORE_UPDATE, db);
		roleDAO.update(db);
		MessageManager.notify(Role.class, MessageEvent.BEFORE_CREATE, db, role);
		// if (listeners != null) {
		// for (RoleEntityListener listener : listeners.values()) {
		// listener.roleUpdated(db);
		// }
		// }
	}

	/*
	 * @see works.tonny.user.service.iface.RoleService#delete(java.lang.String[])
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] id) {
		for (int i = 0; i < id.length; i++) {
			final Role role = get(id[i]);
			MessageManager.notify(Role.class, MessageEvent.BEFORE_DELETE, role);
			roleDAO.delete(role);
			MessageManager.notify(Role.class, MessageEvent.DELETED, role);
			// if (listeners != null) {
			// for (RoleEntityListener listener : listeners.values()) {
			// listener.roleDeleted(role);
			// }
			// }
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.RoleEntityService#registerListener(java.lang.String,
	 *      works.tonny.apps.user.service.RoleEntityListener)
	 */
//	public synchronized void registerListener(String name, RoleEntityListener listener) {
//		if (listeners == null) {
//			listeners = new LinkedHashMap<String, RoleEntityListener>();
//		}
//		listeners.put(name, listener);
//	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.RoleEntityService#removeListener(java.lang.String)
	 */
//	public void removeListener(String name) {
//		if (listeners != null) {
//			listeners.remove(name);
//		}
//	}

	/*
	 * @see works.tonny.user.service.iface.RoleService#get(java.lang.String)
	 */
	public Role get(String id) {
		return roleDAO.get(id);
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * @see works.tonny.apps.user.RoleService#list()
	 */
	public List<Role> list() {
		return roleDAO.list(null);
	}

	/**
	 * @return the listeners
	 */
//	public Map<String, RoleEntityListener> getListeners() {
//		return listeners;
//	}

	/**
	 * @param listeners the listeners to set
	 */
//	public void setListeners(Map<String, RoleEntityListener> listeners) {
//		Set<String> keySet = listeners.keySet();
//		for (String key : keySet) {
//			registerListener(key, listeners.get(key));
//		}
//	}

}
