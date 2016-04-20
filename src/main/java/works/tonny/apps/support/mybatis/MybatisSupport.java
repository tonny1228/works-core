package works.tonny.apps.support.mybatis;

import java.lang.annotation.*;

/**
 * 在DAO配置方法支持mybatis
 * Created by Tonny on 2016/4/16.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MybatisSupport {
//    String statement() default "";
}
