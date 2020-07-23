package com.sosotaxi.service.net;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.sosotaxi.common.Constant;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/19
 * @UpdateTime 2020/7/19
 */
public class OrderService extends Service {

    /**
     * 连接URI
     */
    private URI mUri;

    /**
     * 连接器
     */
    private OrderClient mOrderClient;

    /**
     * 绑定
     */
    private DriverOrderBinder mDriverOrderBinder;

    public OrderService(){
        mDriverOrderBinder=new DriverOrderBinder();
    }

    /**
     * 获取连接器
     * @return 连接器
     */
    public OrderClient getClient(){
        return mOrderClient;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDriverOrderBinder;
    }

    public class DriverOrderBinder extends Binder {
        public OrderService getService(String token){
            // 构造URI
            mUri=URI.create(Constant.WEB_SOCKET_URI+token);

            // 可以重写方法以满足新的需求
            mOrderClient = new OrderClient(mUri){
                @Override
                public void onMessage(String message) {
                    Intent intent=new Intent();
                    intent.setAction(Constant.FILTER_CONTENT);
                    intent.putExtra(Constant.EXTRA_RESPONSE_MESSAGE,message);

                    sendBroadcast(intent);
                }

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    super.onOpen(handshakedata);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    super.onClose(code, reason, remote);
                }

                @Override
                public void onError(Exception ex) {
                    super.onError(ex);
                }
            };

            // 连接
            connect();
            return OrderService.this;
        }
    }

    /**
     * 连接
     */
    private void connect(){
        new Thread(){
            @Override
            public void run() {
                try{
                    mOrderClient.connectBlocking();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
