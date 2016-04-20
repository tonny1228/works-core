package works.tonny.apps.support.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

/**
 * Mybatis advisor 拦截mybatis支持dao中的方法，使用mybatis进行处理。
 * Created by Tonny on 2016/4/16.
 */
public class MybatisAdvisor extends DefaultIntroductionAdvisor {
    public MybatisAdvisor(SqlSessionFactory sqlSessionFactory) {
        super(new MybatisInterceptor(sqlSessionFactory));
    }


}
