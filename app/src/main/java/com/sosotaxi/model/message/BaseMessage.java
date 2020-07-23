/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

/**
 * 消息基类
 */
public class BaseMessage {
    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 消息主体
     */
    private BaseBody body;

    public BaseMessage(MessageType type,BaseBody body){
        this.type=type;
        this.body=body;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public BaseBody getBody() {
        return body;
    }

    public void setBody(BaseBody body) {
        this.body = body;
    }
}
