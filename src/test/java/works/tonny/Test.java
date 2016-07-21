/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 *
 * @date 2015-3-26 下午3:28:04
 * @author tonny
 */
package works.tonny;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
@ContextConfiguration(locations = {
        "classpath*:/applicationContext-basic-test.xml",
        "classpath*:/backup/applicationContext-backup.xml",
        "classpath*:config/applicationContext-mysql-*.xml",
        "classpath*:/config/applicationContext-mod-*.xml"})
public abstract class Test extends AbstractJUnit4SpringContextTests {
}
