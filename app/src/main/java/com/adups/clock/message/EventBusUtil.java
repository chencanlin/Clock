package com.adups.clock.message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by ccl on 2019/4/17 10:23
 */

public class EventBusUtil {

    public static final int ALMIGHTY_HASH_CODE = -1;
    public static final int NULL_HASH_CODE = 0;


    public static void init() {
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .installDefaultEventBus();
    }

    public static EventBusBuilder builder() {
        return EventBus.builder();
    }

    public static EventBus getDefault() {
        return EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unRegister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    /**
     * 为了规范回调方法名（方法必须添加EventBus注解）
     *
     * @param onNotifyListener 回调监听
     * @author ccl
     * @date 2018 /1/22 13:49
     */
    public static void setOnMessageNotify(OnNotifyListener onNotifyListener) {

    }

    /**
     * 发送事件
     *
     * @param customMessageInfo 需要发送的事件
     * @author ccl
     * @date 2018/1/22 14:13
     */
    public static void sendEvent(CustomMessageInfo customMessageInfo) {
        EventBus.getDefault().post(customMessageInfo);
    }

    public static void sendEvent(int messageCode) {
        CustomMessageInfo<Object> customMessageInfo = new CustomMessageInfo<>();
        customMessageInfo.setMessageCode(messageCode);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    public static void sendEvent(int messageCode, Object data) {
        CustomMessageInfo<Object> customMessageInfo = new CustomMessageInfo<>();
        customMessageInfo.setMessageCode(messageCode);
        customMessageInfo.setData(data);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    public static void sendEvent(int messageCode, Object data, String message) {
        CustomMessageInfo<Object> customMessageInfo = new CustomMessageInfo<>();
        customMessageInfo.setMessageCode(messageCode);
        customMessageInfo.setData(data);
        customMessageInfo.setMessage(message);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    public static void sendEvent(int hashCode, int messageCode) {
        CustomMessageInfo customMessageInfo = new CustomMessageInfo();
        customMessageInfo.setSubscriberHashCode(hashCode);
        customMessageInfo.setMessageCode(messageCode);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    public static void sendEvent(int hashCode, int messageCode, Object data) {
        CustomMessageInfo<Object> customMessageInfo = new CustomMessageInfo<>();
        customMessageInfo.setSubscriberHashCode(hashCode);
        customMessageInfo.setMessageCode(messageCode);
        customMessageInfo.setData(data);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    public static void sendEvent(int hashCode, int messageCode, Object data, String message) {
        CustomMessageInfo<Object> customMessageInfo = new CustomMessageInfo<>();
        customMessageInfo.setSubscriberHashCode(hashCode);
        customMessageInfo.setMessageCode(messageCode);
        customMessageInfo.setData(data);
        customMessageInfo.setMessage(message);
        EventBusUtil.sendEvent(customMessageInfo);
    }

    /**
     * 发送粘性事件（系统会存储最近一次粘性事件，新注册能收到粘性事件的实体总会接收到这个事件，所以一般收到并且处理完之后需移除该事件）
     * #
     *
     * @param customMessageInfo 需要发送的粘性事件
     * @author ccl
     * @date 2018/1/22 14:13
     */
    public static void sendStickyEvent(CustomMessageInfo customMessageInfo) {
        EventBus.getDefault().postSticky(customMessageInfo);
    }

    /**
     * 移除所有的粘性事件
     *
     * @author ccl
     * @date 2018/1/22 14:20
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 移除某个具体的粘性事件
     *
     * @param customMessageInfo 需要移除的粘性事件
     * @author ccl
     * @date 2018/1/22 14:21
     */
    public static void removeStickyEvent(CustomMessageInfo customMessageInfo) {
        EventBus.getDefault().removeStickyEvent(customMessageInfo);
    }

    /**
     * 移除某类粘性事件
     *
     * @param clazz 需要移除的粘性事件的Class
     * @author ccl
     * @date 2018/1/22 14:22
     */
    public static void removeStickyEvent(Class<? extends CustomMessageInfo> clazz) {
        EventBus.getDefault().removeStickyEvent(clazz);
    }

    /**
     * 取消事件的传递（一般发生在高优先级的subscriber）
     *
     * @param customMessageInfo 需要取消传递的事件
     * @author ccl
     * @date 2018/1/22 14:23
     */
    public static void cancelEventDelivery(CustomMessageInfo customMessageInfo) {
        EventBus.getDefault().cancelEventDelivery(customMessageInfo);
    }

    /**
     * subscriber判断消息是否需要处理
     *
     * @author ccl
     * @date 2018/1/25 17:33
     */
    public static boolean needHandleMessage(int subscriberHashCode, CustomMessageInfo customMessageInfo) {
        return customMessageInfo != null && (customMessageInfo.getSubscriberHashCode() == ALMIGHTY_HASH_CODE || customMessageInfo.getSubscriberHashCode() == NULL_HASH_CODE || subscriberHashCode == customMessageInfo.getSubscriberHashCode());
    }

    public interface OnSendListener<T> {
        void onSendSucceed(T t);

        void onSendFailed(String message);
    }
}
