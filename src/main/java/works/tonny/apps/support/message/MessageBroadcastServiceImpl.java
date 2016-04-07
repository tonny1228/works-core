/**
 * 
 */
package works.tonny.apps.support.message;

import java.util.List;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * @author 祥栋
 */
public class MessageBroadcastServiceImpl implements MessageBroadcastService {
	private Logger log = LogFactory.getLogger(getClass());

	private MessageServiceImpl messageServiceImpl;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.mmpf.message.MessageBroadcastService#notify(com.MessageEvent.mmpf.message.Message)
	 */
	public void notify(MessageEvent message) {
		log.info(message);
		List<MessageListener> list = messageServiceImpl.getListeners().get(
				message.getCategory() + "." + message.getStatus());
		List<MessageListener> c = messageServiceImpl.getListeners().get(message.getCategory());
		if (c != null)
			list.addAll(c);
		if (list == null) {
			return;
		}
		for (MessageListener messageListener : list) {
			try {
				messageListener.onRecieved(message);
			} catch (MessageHandleException e) {
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.mmpf.message.MessageBroadcastService#notify(java.lang.String,
	 *      MessageEvent.Status, java.lang.Object)
	 */
	public void notify(String category, MessageEvent.Status status, Object data) {
		notify(new MessageEvent(category, status, data));
	}

	/**
	 * @return the messageServiceImpl
	 */
	public MessageServiceImpl getMessageServiceImpl() {
		return messageServiceImpl;
	}

	/**
	 * @param messageServiceImpl the messageServiceImpl to set
	 */
	public void setMessageServiceImpl(MessageServiceImpl messageServiceImpl) {
		this.messageServiceImpl = messageServiceImpl;
	}
}
