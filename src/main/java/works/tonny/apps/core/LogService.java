/**
 *
 */
package works.tonny.apps.core;

/**
 * 用户操作日志
 *
 * @author tonny
 * @version 1.0.0
 * @date 2012-10-9
 */
public interface LogService {

    public static final String TYPE_ADD = "add";

    public static final String TYPE_UPDATE = "update";

    public static final String TYPE_DETETE = "delete";

    /**
     * 记录用户操作日志
     *
     * @param catalog  目录
     * @param username 用户
     * @param type     类型
     * @param message  日志
     */
    void log(String catalog, String username, String type, String objectId, String message);

    LogQuery createQuery();

}
