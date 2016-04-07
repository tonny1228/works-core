/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-25 下午8:13:19
 * @author tonny
 */
package works.tonny;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.auth.DataOwnerConfigService;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.dao.CatalogDAO;
import works.tonny.apps.support.SpringHibernateDAO;
import works.tonny.apps.user.AuthService;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.UserService;

/**
 * <p>
 * 需求原型： 政府机构：省、市、县区、乡镇、村，县以上都是管理单元，下面是部门
 * 乡镇以上可以维护本部门的数据，可以查看子部门的数据，村不能维护数据，只能在乡镇一级维护
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@ContextConfiguration(locations = { "classpath*:/config/applicationContext-basic.xml",
		"classpath*:config/applicationContext-*mysql.xml", "classpath*:/config/applicationContext-mod-*.xml" })
public class DataAuth extends AbstractJUnit4SpringContextTests {
	@Autowired
	private SpringHibernateDAO dao;

	@Autowired
	private CatalogDAO catalogDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;
	
	
	@Autowired
	private DataOwnerConfigService dataOwnerConfigService;

	@Test
	public void test() {
		Set<String> roles = new HashSet<String>();
		roles.add("bu_zxtx");
		roles.add("bu_yfzx");
		roles.add("dp_ydsyb");
		roles.add("ps_jgs");
		roles.add("rl_info");
		roles.add("rl_admin");

		// 文章所有者身份：bu=level3,rl=info

		// 设置文章所有者为:当前用户，当前岗位，当前部门，当前管理单元，父部门(level=n)

		// 情形1,设置为所有者为管理单元，乡管自己，县管自己，县管乡做不到，需要联合部门表查询。
		// 情形2,设置为所有者为岗位，用户管自己，换人后，无法读取，新人接管。部门领导想查看不行，更高级领导都不行。
		// 用户组织机构管理单独做，与用户管理分开，权限也要分开。
		/*
		 * 查询所有有所有者的数据 select * from cms_article a where id in(select data_id
		 * from cms_article_owner o where a.id=o.data_id)
		 */
		// 查询当前用户的数据：
		// select * from cms_article a where id in(select data_id from
		// cms_article_owner o where a.id=o.data_id and o.owner_id='liubo')
		//
		// 查询当前单位下所有子单位、子部门部门的数据：
		// select * from cms_article a where id in(select data_id from
		// cms_article_owner o where o.owner_id in (select zt.id from
		// u_department_tree pt,u_department_tree zt where pt.id='ydsyb' and
		// zt.id_layer like CONCAT(pt.id_layer,'%') ) )
		// 查询当前单位下所有岗位创建的数据。
		/*
		 * 
		 * 数据所有者为岗位 SELECT
		 * 
		 * FROM cms_article a WHERE id IN( SELECT data_id FROM cms_article_owner
		 * o WHERE o.owner_id IN( SELECT ps.id FROM u_department_tree pt,
		 * u_department_tree zt, u_position ps WHERE pt.id = 'lnzxtx' AND
		 * zt.id_layer LIKE CONCAT(pt.id_layer, '%') and ps.dept_id=zt.id ) )
		 */
		// 查询当前单位下所有用户的数据：
		/*
		 * 数据所有者为用户 SELECT
		 * 
		 * FROM cms_article a WHERE id IN( SELECT data_id FROM cms_article_owner
		 * o WHERE o.owner_id IN( SELECT u.user_id FROM u_department_tree pt,
		 * u_department_tree zt, u_position ps, u_user_position u WHERE pt.id =
		 * 'lnzxtx' AND zt.id_layer LIKE CONCAT(pt.id_layer, '%') and
		 * ps.dept_id=zt.id and u.position_id=ps.id ) )
		 */
		// 1每个部门能看到自己部门的数据，2公司宣传部管理员能看到本单位的数据，3集团管理员能看到所有
		/*
		 * 记录数据所有者为部门 1、查询部门数据 2、查询本单位所有部门数据 3、查询所有数据
		 */

		/**
		 * 日报：所有人都可编辑自己数据。部门领导可以查看部门下所有人的数据。 查询部门下自己的 查询当前部门所有的，并且用户id＝？
		 * 查询当前部门所有的，并且岗位id＝？
		 */
	}

	/**
	 * 
	 * 
	 * @author tonny
	 */
	@Test
	public void testUserCatalog() {
		System.out.println("查询所有的");
		List list = dao.find("from Catalog c");
		System.out.println(list);
		if (true) {
			return;
		}
		list = dao.find("from CatalogTreeNode c");
		System.out.println(list);
		System.out.println("查询某个人的");
		list = dao.find("select c from Catalog c join c.owner o where o.ownerId=? and o.ownerType='user'",
				new Object[] { "40283e8146d5b7ca0146d5cb47ab001e" });
		System.out.println(list);

		System.out.println("查询某个部门的");
		list = dao
				.find("select c from Catalog c join c.owner o,UserPosition p where  o.ownerType='user' and p.id.user.id=o.ownerId and p.id.position.department.id=?",
						new Object[] { "ydsyb" });
		System.out.println(list);

		System.out.println("查询某个部门及其所有子部门的");
		list = dao
				.find("select c from Catalog c join c.owner o,UserPosition p where  o.ownerType='user' and p.id.user.id=o.ownerId and p.id.position.department.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)",
						new Object[] { "lnzxtx%" });
		System.out.println(list);

	}

	/**
	 * 
	 * 
	 * @author tonny
	 */
	@Test
	public void testPositionCatalog() {
		System.out.println("查询某个的");
		List list = dao.find("select c from Catalog c join c.owner o where o.ownerId=? and o.ownerType='pos'",
				new Object[] { "cn" });
		System.out.println(list);

		System.out.println("查询某个部门的");
		list = dao
				.find("select c from Catalog c join c.owner o,Position p where  o.ownerType='pos' and p.id=o.ownerId and p.department.id=?",
						new Object[] { "cwb" });
		System.out.println(list);

		System.out.println("查询某个部门及其所有子部门的");
		list = dao
				.find("select c from Catalog c join c.owner o,Position p where  o.ownerType='pos' and p.id=o.ownerId and p.department.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)",
						new Object[] { "lnzxtx%" });
		System.out.println(list);

	}

	/**
	 * 
	 * 
	 * @author tonny
	 */
	@Test
	public void testBuCatalog() {
		System.out.println("查询某个的");
		List list = dao.find("select c from Catalog c join c.owner o where o.ownerId=? and o.ownerType='bu'",
				new Object[] { "ydsyb" });
		System.out.println(list);

		System.out.println("查询某个部门的");
		list = dao.find("select c from Catalog c join c.owner o where  o.ownerType='bu' and o.ownerId=?",
				new Object[] { "ydsyb" });
		System.out.println(list);

		System.out.println("查询某个部门及其所有子部门的");
		list = dao
				.find("select c from Catalog c where c.id in (select o.data.id from CatalogDataOwner o ,Department d where  o.ownerType='bu' and d.id=o.ownerId and d.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?))",
						new Object[] { "lnzxtx%" });
		System.out.println(list);

	}

	/**
	 * 
	 * 
	 * @author tonny
	 */
	@Test
	public void testDeptCatalog() {
		System.out.println("查询某个的");
		List list = dao.find("select c from Catalog c join c.owner o where o.ownerId=? and o.ownerType='dept'",
				new Object[] { "ydsyb" });
		System.out.println(list);

		System.out.println("查询某个部门的");
		list = dao.find("select c from Catalog c join c.owner o where  o.ownerType='dept' and o.ownerId=?",
				new Object[] { "ydsyb" });
		System.out.println(list);

		System.out.println("查询某个部门及其所有子部门的");
		list = dao
				.find("select c from Catalog c join c.owner o,Department d where  o.ownerType='dept' and d.id=o.ownerId and d.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)",
						new Object[] { "lnzxtx%" });
		System.out.println(list);

	}

	@Test
	public void testQueryFilter() {
		String sql = "select o.data.id from CatalogDataOwner o ,Department d where  o.ownerType='bu' and d.id=o.ownerId and d.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)";
		sql = "";
		List list = dao.find(sql);
		System.out.println(list);

	}

	@Test
	public void query() {
		String sql = "select c from Catalog c";
		buildSQL(sql);
		sql = "select c from Catalog c,CatalogAuth a where a.catalog.id=c.id";
		buildSQL(sql);
		sql = "select c from Catalog c where c.type=?";
		buildSQL(sql, 0);
	}

	private void buildSQL(String sql, Object... args) {
		String entity = "Catalog";
		if (sql.matches("(.*)(\\swhere\\s)(.*)")) {
			sql += " and (c.id in  (select o.data.id from CatalogDataOwner o ,Department d where  o.ownerType='bu' and d.id=o.ownerId and d.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)))";
		} else {
			sql += " where (c.id in  (select o.data.id from CatalogDataOwner o ,Department d where  o.ownerType='bu' and d.id=o.ownerId and d.id in(select d.id from DepartmentTreeNode d where d.idLayer like ?)))";
		}
		System.out.println(sql);
		Object[] n = null;
		if (args != null) {
			n = new Object[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				n[i] = args[i];
			}
		} else {
			n = new Object[1];
		}
		n[n.length - 1] = "lnzxtx%";
		List list = dao.find(sql, n);
		System.out.println(list);
	}

	private void buildSQL2(String sql, Object... args) {
		/*
		 * Pattern pattern = Pattern.compile("(.*)(\\swhere\\s)(.*)",
		 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); Matcher matcher =
		 * pattern.matcher(sql); System.out.println(matcher.find());
		 * System.out.println(matcher.groupCount());
		 * System.out.println(matcher.group(0));
		 * System.out.println(matcher.group(1));
		 * System.out.println(matcher.group(2));
		 * System.out.println(matcher.group(3)); sql = matcher.group(1) +
		 * " join c.owner o "; if (matcher.group(2) != null) { sql +=
		 * matcher.group(2) + matcher.group(3) +
		 * " and  o.ownerId=?  and o.ownerType='user'"; } else { sql +=
		 * " where  o.ownerId=?  and o.ownerType='user'"; }
		 * System.out.println(sql);
		 */
		String entity = "Catalog";
		sql = sql.replaceAll("\\s(" + entity + "\\s+\\w+)([^\\w])*", " $1  join c.owner o $2");
		if (sql.matches("(.*)(\\swhere\\s)(.*)")) {
			sql = sql
					.replaceAll("(.*)(\\swhere\\s)(.*)", "$1" + " $2 $3" + " and  o.ownerId=?  and o.ownerType='user'");
		} else {
			sql = sql.replaceAll("(.*)(\\swhere\\s)(.*)", "$1") + " where  o.ownerId=?  and o.ownerType='user'";
		}
		System.out.println(sql);
		Object[] n = null;
		if (args != null) {
			n = new Object[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				n[i] = args[i];
			}
		} else {
			n = new Object[1];
		}
		n[n.length - 1] = "40283e8146d5b7ca0146d5cb47ab001e";
		List list = dao.find(sql, n);
		System.out.println(list);
	}

	/**
	 * 
	 * @author tonny
	 */
	@Test
	public void goonTest() {
		LoginedUser user = authService.login("liubo", "1");
		ThreadLocalMap.getInstance().putObject(LoginedUser.class, user);
		ThreadLocalMap.getInstance().putObject(DataOwnerConfig.Type.class, DataOwnerConfig.Type.VIEW);
		System.out.println("########################查询################################");
		List<Catalog> list = catalogDAO.listSubs("cms");
		System.out.println(list);
	}
	
	
	@Test
	public void testSetQuery(){
		//String sql = "select c from Catalog c where exists elements(c.owner) ";
		String sql = "select c from Catalog c,CatalogDataOwner o where o.ownerType='user' and o in elements(c.owner) group by c";
//		PagedList list = dao.find(sql,1,2);
//		System.out.println(list);
//		System.out.println(list.getTotal());
	}

	@Test
	public void auto() {
		System.out.println(dataOwnerConfigService);
	}
}
