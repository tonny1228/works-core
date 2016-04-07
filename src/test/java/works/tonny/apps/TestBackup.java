package works.tonny.apps;

import org.springframework.beans.factory.annotation.Autowired;
import works.tonny.Test;
import works.tonny.apps.backup.DataBackupService;
import works.tonny.apps.core.Catalog;

/**
 * Created by tonny on 2015/11/28.
 */
public class TestBackup extends Test {
    @Autowired
    private DataBackupService dataBackupService;

    @org.junit.Test
    public void backup() {
        dataBackupService.backup(Catalog.class);
    }
}
