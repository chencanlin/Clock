package com.adups.clock.message;

/**
 * Created by ccl on 2019/4/17 10:23
 */

public class CustomMessageInfo<T> {
    private int subscriberHashCode;
    private int messageCode;
    private T data;
    private String message;
    /*public CustomMessageInfo() {
    }

    public CustomMessageInfo(int messageCode) {
        this.messageCode = messageCode;
    }

    public CustomMessageInfo(int messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public CustomMessageInfo(int hashCode, int messageCode) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
    }

    public CustomMessageInfo(int hashCode, int messageCode, String message) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.message = message;
    }

    public CustomMessageInfo(int messageCode, T data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public CustomMessageInfo(int messageCode, T data, String message) {
        this.messageCode = messageCode;
        this.data = data;
        this.message = message;
    }

    public CustomMessageInfo(int hashCode, int messageCode, T data) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.data = data;
    }

    public CustomMessageInfo(int hashCode, int messageCode, T data, String message) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.data = data;
        this.message = message;
    }*/

public int getSubscriberHashCode() {
        return subscriberHashCode;
    }

public void setSubscriberHashCode(int subscriberHashCode) {
        this.subscriberHashCode = subscriberHashCode;
    }

public int getMessageCode() {
        return messageCode;
    }

public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder<T> {

        private final CustomMessageInfo<T> mCustomMessage;

        public Builder() {
            mCustomMessage = new CustomMessageInfo<>();
        }

        public Builder setHashCode(int hashCode) {
            mCustomMessage.subscriberHashCode = hashCode;
            return this;
        }

        public Builder setMessageCode(int messageCode) {
            mCustomMessage.messageCode = messageCode;
            return this;
        }

        public Builder setData(T data) {
            mCustomMessage.data = data;
            return this;
        }

        public Builder setMessage(String message) {
            mCustomMessage.message = message;
            return this;
        }

        public CustomMessageInfo build() {
            return mCustomMessage;
        }
    }
}
