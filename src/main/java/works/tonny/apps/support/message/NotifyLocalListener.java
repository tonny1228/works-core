package works.tonny.apps.support.message;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

import java.util.List;

/**
 * 本地监听服务通知，只通知本地监听接口
 * Created by Tonny on 2016/6/13.
 */
public class NotifyLocalListener {
    private Logger log = LogFactory.getLogger(getClass());
    /**
     * 消息服务
     */
    private MessageRegisterService messageService;


    /**
     * 调用本地注册的监听接口进行消息的处理
     */
    public void notify(MessageEvent message) {
        log.debug(message);
        List<MessageListener> list = messageService.getListeners().get(
                message.getCategory() + "." + message.getStatus());
        List<MessageListener> c = messageService.getListeners().get(message.getCategory());
        if (c != null)
            list.addAll(c);
        if (list == null) {
            return;
        }
        for (MessageListener messageListener : list) {
            try {
                messageListener.onRecieved(message);
            } catch (MessageHandleException e) {
                log.error(message, e);
            }
        }
    }

    public MessageRegisterService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageRegisterService messageService) {
        this.messageService = messageService;
    }
}
