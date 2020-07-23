/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.utils;

import com.google.gson.Gson;
import com.sosotaxi.model.message.BaseBody;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.service.net.OrderClient;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息帮手类
 */
public class MessageHelper {
    /** 消息帮手实例 */
    private static MessageHelper sInstance;
    /** 待发送消息列表 */
    private ConcurrentLinkedQueue<BaseMessage> mSendMessageQueue;
    /** 已发送消息列表 */
    private ConcurrentHashMap<Integer,BaseMessage> mSentMessageMap;
    /** 序列生成器 */
    private AtomicInteger atomicInteger;
    /** 连接器 */
    private OrderClient mClient;
    /** Gson对象 */
    private Gson gson;

    /**
     * 获取实例
     * @return 消息帮手实例
     */
    public static MessageHelper getInstance(){
        if(sInstance==null){
            sInstance=new MessageHelper();
        }
        return  sInstance;
    }

    public MessageHelper(){
        mSendMessageQueue=new ConcurrentLinkedQueue<>();
        mSentMessageMap=new ConcurrentHashMap<>();
        atomicInteger=new AtomicInteger();
        gson=new Gson();
    }

    /**
     * 构建消息
     * @param type 消息类型
     * @param body 消息体
     * @return 消息
     */
    public BaseMessage build(MessageType type, BaseBody body){
        BaseMessage message=new BaseMessage(type, body);
        return message;
    }

    /**
     * 解析消息
     * @param json JSON
     * @return 消息
     */
    public BaseMessage parse(String json){
        BaseMessage message=gson.fromJson(json,BaseMessage.class);
        return message;
    }

    /**
     * 发送消息
     * @param type 消息类型
     * @param body 消息体
     */
    public void send(MessageType type, BaseBody body){
        BaseMessage message=new BaseMessage(type, body);
        send(message);
    }

    /**
     * 发送消息
     * @param message 消息
     */
    public void send(BaseMessage message){
        //mSendMessageQueue.add(message);
        String json=gson.toJson(message);
        if(mClient!=null){
            mClient.send(json);
        }
    }

    /**
     * 设置连接器
     * @param client 连接器
     */
    public void setClient(OrderClient client){
        mClient=client;
    }

    /**
     * 获取消息序号
     * @return 序号
     */
    public int getMessageId(){
        return atomicInteger.getAndIncrement();
    }
}
