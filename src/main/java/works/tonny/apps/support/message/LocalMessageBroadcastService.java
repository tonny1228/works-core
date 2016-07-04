/**
 *
 */
package works.tonny.apps.support.message;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * @author 祥栋
 */
public class LocalMessageBroadcastService implements MessageBroadcastService {
    private Logger log = LogFactory.getLogger(getClass());

    /**
     * 本地监听通知接口
     */
    private NotifyLocalListener notifyLocalListener;

    /**
     * {@inheritDoc}
     */
    public void notify(MessageEvent message) {
        log.debug(message);
        notifyLocalListener.notify(message);
    }

    /**
     * {@inheritDoc}
     * <p/>
     */
    public void notify(String category, MessageEvent.Status status, Object data) {
        notify(new MessageEvent(category, status, data));
    }

    public NotifyLocalListener getNotifyLocalListener() {
        return notifyLocalListener;
    }

    public void setNotifyLocalListener(NotifyLocalListener notifyLocalListener) {
        this.notifyLocalListener = notifyLocalListener;
    }
}
