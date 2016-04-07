package works.tonny.apps.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import works.tonny.apps.support.BeanInitListener;

/**
 * Created by tonny on 2015/9/10.
 */
public class DataOwnerConfigPosPrcessor implements ApplicationListener<ContextRefreshedEvent> {

    private BeanInitListener dataOwnerConfigService;

    public DataOwnerConfigPosPrcessor(){
        System.out.println("#################################");
        System.out.println("#################################");
        System.out.println("#################################");
        System.out.println("#################################");
        System.out.println("#################################");
        System.out.println("#################################");
    }


    public BeanInitListener getDataOwnerConfigService() {
        return dataOwnerConfigService;
    }

    public void setDataOwnerConfigService(BeanInitListener dataOwnerConfigService) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        this.dataOwnerConfigService = dataOwnerConfigService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("*********************************");
        dataOwnerConfigService.initialize();
    }
}
