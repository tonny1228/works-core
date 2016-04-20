package works.tonny;

import org.junit.*;
import org.llama.library.utils.ThreadLocalMap;
import org.llama.library.utils.WebAppPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogService;
import works.tonny.apps.user.AuthService;
import works.tonny.apps.user.LoginedUser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by tonny on 2015/9/9.
 */
@ContextConfiguration(locations = {"classpath:/applicationContext-basic-test.xml",
        "classpath*:config/applicationContext-*mysql.xml", "classpath*:/config/applicationContext-mod-*.xml"})
public class CatalogTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private AuthService authService;

    @org.junit.Test
    @Transactional(rollbackFor = Exception.class)
    public void testTreeNode() {
        System.out.println(catalogService);

        LoginedUser admin = authService.login("admin", "1");
        ThreadLocalMap.getInstance().putObject(LoginedUser.class, admin);
        System.out.println("*******************************************************");
        List<Catalog> list = catalogService.createQuery().depth(3).list();
        for (Catalog catalog : list) {
            System.out.println(catalog.getTreeNode().getIdLayer());
        }
//        List<Catalog> cms = catalogService.listSubs("cms");
//        for (Catalog cm : cms) {
////            System.out.println(cm.getName());
//        }
    }


    @org.junit.Test
    public void testPath() throws UnsupportedEncodingException {
        System.out.println(new File(URLDecoder.decode(Thread.class.getResource("/").getPath(), "utf-8")));

    }
}
