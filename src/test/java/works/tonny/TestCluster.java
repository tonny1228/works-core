package works.tonny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import works.tonny.apps.user.AuthService;
import works.tonny.apps.user.LoginedUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by tonny on 2015/8/5.
 */
@ContextConfiguration(locations = { "classpath*:/config/applicationContext-basic.xml",
		"classpath*:config/applicationContext-*mysql.xml", "classpath*:/config/applicationContext-mod-*.xml" })
public class TestCluster extends AbstractJUnit4SpringContextTests {
    @Autowired
    private AuthService authService;


    @org.junit.Test
    public void test() throws IOException {
//        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("g:\\temp\\1")));
//        LoginedUser user = authService.login("admin", "1");
//        System.out.println(user);
//        os.writeObject(user);
//        os.close();
    }
}
