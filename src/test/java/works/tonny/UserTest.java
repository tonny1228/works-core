package works.tonny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import works.tonny.apps.user.dao.UserInfoEntityDAO;

/**
 * Created by tonny on 2015/9/10.
 */
@ContextConfiguration(locations = {"classpath:/applicationContext-basic-test.xml",
        "classpath*:config/applicationContext-mysql-*.xml", "classpath*:/config/applicationContext-mod-*.xml"})

public class UserTest extends AbstractJUnit4SpringContextTests {


    @Autowired
    private UserInfoEntityDAO userInfoEntityDAO;

    @org.junit.Test
    @Rollback(true)
    public void test() {
        //userInfoEntityDAO.delete("40283e8146d5b7ca0146d5cb47ab001e");
    }
}
