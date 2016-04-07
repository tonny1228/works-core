package works.tonny.apps.backup;

import java.io.File;
import java.util.List;

/**
 * Created by tonny on 2015/11/28.
 */
public interface DataBackupService {

    void backup(Class... clazz);

    List<Backup> files();

    void recorver(File[] file);

    File backupDir();
}