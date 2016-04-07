package backup;

import org.springframework.beans.factory.annotation.Autowired;
import works.tonny.Test;
import works.tonny.apps.backup.Backup;
import works.tonny.apps.backup.DataBackupService;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogDataOwner;
import works.tonny.apps.core.CatalogTreeNode;

import java.io.File;
import java.util.List;

/**
 * Created by tonny on 2015/12/17.
 */
public class BackupAndRestore extends Test {

    @Autowired
    private DataBackupService backupService;

    @org.junit.Test
    public void testBackup() {
        backupService.backup(Catalog.class, CatalogTreeNode.class, CatalogDataOwner.class);
    }

    @org.junit.Test
    public void list() {
        List<Backup> files = backupService.files();
        for (Backup file : files) {

            System.out.println(file.getFile() + " " + file.getBackupTime() + " " + file.getSizes());
        }


    }

    @org.junit.Test
    public void restore() {
        List<Backup> files = backupService.files();
        backupService.recorver(new File[]{new File(backupService.backupDir(), "Catalog_CatalogTreeNode_CatalogDataOwner_20151221165026.backup")});
    }
}
