package works.tonny.apps.user.service;
// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1


import java.util.List;

import works.tonny.apps.EntityService;
import works.tonny.apps.user.model.Title;
/**
 * Service interface for domain model class Title.
 * @see entity.Title
 * @author Tonny Liu
 */
public interface TitleService extends EntityService<Title> {
	/**
	 * 查询Title列表
	 * 
	 * @return 分页列表
	 */
	List<Title> list();
}

