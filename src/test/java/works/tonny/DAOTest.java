/**
 *
 */
package works.tonny;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.HibernateDAO;

import java.util.List;

/**
 * @author 祥栋
 */
@ContextConfiguration(locations = {"classpath:/applicationContext-basic-test.xml",
        "classpath*:config/applicationContext-mysql-*.xml", "classpath*:/config/applicationContext-mod-*.xml"})
public class DAOTest extends AbstractJUnit4SpringContextTests {
    //    @Autowired
    BaseDAOSupport<Catalog> support;
    @Autowired
    HibernateDAO hibernateDAO;

    @Test
    public void testListSupport() {
        BaseDAOSupport<Catalog> support = new BaseDAOSupport<Catalog>();
        // support.find(new String[] { "name", "type", "treeNode.depth", "a",
        // "b", "c", "d", "e", "f", "g", "h", "i", "j",
        // "k" }, new Object[] { "1", "2", 3, new RangeParameter("a", 1, 2), new
        // RangeParameter("a", 1, 2),
        // new RangeParameter("a", 1, 2), new RangeParameter("a", 1, 2), 3, 5,
        // new Integer[] { 1, 2 }, 10, "abc",
        // "def", "df" }, new String[] {}, new String[] { ListSupport.EQUALS,
        // ListSupport.NOT_EQUALS,
        // ListSupport.LESS, ListSupport.BETWEEN, ListSupport.BETWEEN_N_L,
        // ListSupport.BETWEEN_N_L_R,
        // ListSupport.BETWEEN_N_R, ListSupport.GREATER,
        // ListSupport.GREATER_EQUALS, ListSupport.IN,
        // ListSupport.LESS_EQUALS, ListSupport.LIKE, ListSupport.LLIKE,
        // ListSupport.RLIKE },
        // new String[] { "treeNode.orderNo" }, new String[] {
        // "treeNode.depth>:treeNode_depth" });
        System.out.println("**************************************");
        List<Catalog> list = hibernateDAO.find("select  new Catalog(d,n) from CatalogTreeNode n inner join n.data d");
        System.out.println("#####################################");
        for (Catalog catalog : list) {
            System.out.println(catalog.getTreeNode().getIdLayer() + ": " + catalog.getName());
        }

        //        support.list("from Catalog", null, null, null);
    }
}
