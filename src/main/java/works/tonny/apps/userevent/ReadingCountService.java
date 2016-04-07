package works.tonny.apps.userevent;

// Generated 2015-6-15 9:26:22 by Hibernate Tools 3.4.0.CR1

/**
 * Service interface for domain model class ReadingCount.
 *
 * @author Tonny Liu
 * @see .ReadingCount
 */
public interface ReadingCountService {

    /**
     * 读取信息，记录数据被读取并返回访问量
     *
     * @param mainFunction 主功能项
     * @param subFunction  子功能项
     * @param dataId       读取的数据id
     * @return
     */
    int read(String mainFunction, String subFunction, String dataId);

    int unread(String mainFunction, String subFunction, String dataId);

    /**
     * 获取访问条数
     *
     * @param dataId
     * @return
     */
    int getCount(String mainFunction, String subFunction, String dataId);

    /**
     * 创建复合查询
     */
    ReadingCountQuery createQuery();

}
