package works.tonny.apps.backup;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by tonny on 2015/12/21.
 */
public class Backup {
    private Date backupTime;

    private Map<Class, Long> sizes;

    private File file;

    public Date getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<Class, Long> getSizes() {
        return sizes;
    }

    public void setSizes(Map<Class, Long> sizes) {
        this.sizes = sizes;
    }
}
