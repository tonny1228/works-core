/**
 * 
 */
package works.tonny.apps.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import works.tonny.apps.Entity;

/**
 * @author 祥栋
 */
public class EntityUtils {

	/**
	 * 合并集合
	 * 
	 * @param dest
	 * @param source
	 */
	public static void merge(Collection dest, Collection source) {
		if (dest == null) {
			throw new IllegalArgumentException("dest 不能为空");
		}
		if (dest.isEmpty()) {
			dest.addAll(source);
		}

		Map<String, Object> sourceMap = new HashMap<String, Object>();
		for (Object entity : source) {
			if (entity != null)
				sourceMap.put(((Entity) entity).getId(), (Entity) entity);
		}

		Map<String, Object> destMap = new HashMap<String, Object>();
		Set<Object> remove = new HashSet<Object>();
		for (Object entity : dest) {
			Entity en = (Entity) entity;
			destMap.put(en.getId(), en);
			if (sourceMap.containsKey(en.getId())) {
				try {
					BeanUtils.copyProperties(entity, sourceMap.get(en.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				sourceMap.remove(en.getId());
			} else {
				remove.add(entity);
			}
		}
		dest.removeAll(remove);
		dest.addAll(sourceMap.values());
	}

	/**
	 * 删除已删除的数据，返回待添加的id
	 * 
	 * @param dest
	 * @param ids
	 * @return
	 */
	public static String[] deleteAndGetToBeAdded(Collection<Entity> dest, String[] ids) {
		if (dest == null) {
			throw new IllegalArgumentException("dest 不能为空");
		}
		if (dest.isEmpty()) {
			return ids;
		}
		List<String> add = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			add.add(ids[i]);
		}
		Set<Entity> remove = new HashSet<Entity>();
		for (Entity entity : dest) {
			if (!add.contains(entity.getId())) {
				remove.add(entity);
			} else {
				add.remove(entity.getId());
			}
		}
		ids = new String[add.size()];
		return add.toArray(ids);
	}
}
