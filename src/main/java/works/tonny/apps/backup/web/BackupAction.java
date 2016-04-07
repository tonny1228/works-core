package works.tonny.apps.backup.web;

import works.tonny.apps.backup.Backup;
import works.tonny.apps.backup.DataBackupService;
import works.tonny.apps.user.AuthedAction;

import java.io.File;
import java.util.List;

/**
 * Created by tonny on 2015/11/30.
 */
public class BackupAction extends AuthedAction {

    private DataBackupService dataBackupService;

    private String file;

    public String list() {
        List<Backup> files = dataBackupService.files();
        request.setAttribute("files", files);
        return "list";
    }

    public String recover() {
        dataBackupService.recorver(new File[]{new File(dataBackupService.backupDir(), file)});
        return "recover";
    }

    public DataBackupService getDataBackupService() {
        return dataBackupService;
    }

    public void setDataBackupService(DataBackupService dataBackupService) {
        this.dataBackupService = dataBackupService;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
