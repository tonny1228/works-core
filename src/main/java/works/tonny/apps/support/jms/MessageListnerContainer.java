package works.tonny.apps.support.jms;

import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;

/**
 * Created by Tonny on 2016/6/16.
 */
public class MessageListnerContainer extends DefaultMessageListenerContainer {
    public boolean initialized;

    @Override
    public void initialize() {
        super.initialize();

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    @Override
    protected void doInitialize() throws JMSException {
        super.doInitialize();
        System.out.println("doInitialize()");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("start()");
        initialized = true;
    }

    @Override
    public void stop(Runnable callback) {
        super.stop(callback);
        System.out.println("stop()");
    }

    @Override
    protected TaskExecutor createDefaultTaskExecutor() {
        System.out.println("createDefaultTaskExecutor()");
        return super.createDefaultTaskExecutor();

    }

    @Override
    protected void doRescheduleTask(Object task) {
        super.doRescheduleTask(task);
        System.out.println("doRescheduleTask()");
    }

    @Override
    protected void establishSharedConnection() {
        super.establishSharedConnection();
        System.out.println("establishSharedConnection()");
    }

    @Override
    protected void startSharedConnection() {
        super.startSharedConnection();
        System.out.println("startSharedConnection()");
    }

    @Override
    protected void refreshConnectionUntilSuccessful() {
        super.refreshConnectionUntilSuccessful();
        System.out.println("refreshConnectionUntilSuccessful()");
    }

    @Override
    protected void refreshDestination() {
        super.refreshDestination();
        System.out.println("refreshDestination()");
    }

    @Override
    protected void doStart() throws JMSException {
        super.doStart();
        System.out.println("doStart()");
    }
}