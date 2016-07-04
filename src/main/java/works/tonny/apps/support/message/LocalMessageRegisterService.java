/**
 *
 */
package works.tonny.apps.support.message;

import works.tonny.apps.support.message.MessageEvent.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 祥栋
 */
public class LocalMessageRegisterService implements MessageRegisterService {
    /**
     * 已注册的本地的监听
     */
    private Map<String, List<MessageListener>> listeners;

    /**
     * 初始化监听
     */
    public LocalMessageRegisterService() {
        listeners = new HashMap<String, List<MessageListener>>();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.zxtx.mmpf.message.MessageService#register(java.lang.String,
     * com.zxtx.mmpf.message.MessageListener)
     */
    public void register(String category, MessageListener listener, Status... status) {
        if (status != null) {
            for (Status st : status) {
                register(category + "." + st, listener);
            }
        } else {
            register(category, listener);
        }
    }

    /**
     * @param category
     * @param listener
     */
    public void register(String category, MessageListener listener) {
        List<MessageListener> list = null;
        synchronized (this) {
            list = listeners.get(category);
            if (list == null) {
                list = new ArrayList<MessageListener>();
                listeners.put(category, list);
            }
        }
        list.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void unRegister(String category, MessageListener listener, Status... status) {
        if (status != null) {
            for (Status st : status) {
                unRegister(category + "." + st, listener);
            }
        } else {
            unRegister(category, listener);
        }
    }

    /**
     * @param category
     * @param listener
     */
    public void unRegister(String category, MessageListener listener) {
        List<MessageListener> list = null;
        synchronized (this) {
            list = listeners.get(listener);
            if (list == null) {
                list = new ArrayList<MessageListener>();
                listeners.put(category, list);
            }
        }
        list.remove(listener);
    }

    /**
     * @return the listeners
     */
    @Override
    public Map<String, List<MessageListener>> getListeners() {
        return listeners;
    }

}
