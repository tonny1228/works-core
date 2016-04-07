package works.tonny.apps.core.action;

import org.apache.commons.lang3.StringUtils;
import works.tonny.apps.core.LogQuery;
import works.tonny.apps.core.LogService;
import works.tonny.apps.support.QueryCriteriaUtils;
import works.tonny.apps.user.AuthedAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonny on 2015/11/27.
 */
public class LogAction extends AuthedAction {
    private LogService logService;

    public String list() {
        LogQuery query = logService.createQuery();
        String order = getParameter("_order");
        String param = getParameter("_orderparam");
        final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.putAll(request.getParameterMap());
        if (StringUtils.isEmpty(order)) {
            parameterMap.put("_order", new String[]{"orderByTime"});
            parameterMap.put("_orderparam", new String[]{"desc"});
        }

        QueryCriteriaUtils.buildQuery(query, parameterMap);
        request.setAttribute("query", query);
        return "list";
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
}
