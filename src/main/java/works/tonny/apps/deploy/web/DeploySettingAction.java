package works.tonny.apps.deploy.web;

import works.tonny.apps.deploy.ModuleHelper;
import works.tonny.apps.deploy.SystemInfo;
import works.tonny.apps.support.CommonAction;

public class DeploySettingAction extends CommonAction {

	public String setting() {
		request.setAttribute("modules", ModuleHelper.getInstance().getModules());
		request.setAttribute("sys", SystemInfo.getInstance());
		return "setting";
	}
}