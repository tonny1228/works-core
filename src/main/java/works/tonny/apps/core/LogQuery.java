package works.tonny.apps.core;

import works.tonny.apps.Query;

import java.util.Date;

/**
 * Created by tonny on 2015/11/26.
 */
public interface LogQuery extends Query<LogQuery, Log> {

    LogQuery user(String user);

    LogQuery catalog(String catalog);

    LogQuery action(String... action);

    LogQuery objectId(String objectId);

    LogQuery infoLike(String info);

    LogQuery timeRange(Date from, Date to);

    LogQuery orderByTime(Direction direction);
}
