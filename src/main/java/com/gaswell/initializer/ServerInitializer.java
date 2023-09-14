package com.gaswell.initializer;

import com.gaswell.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Lei Wang
 * @Date: 2021/12/08/ 23:44
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 若10分钟内未收到硬件发来的数据，则触发IdleStateEvent事件
        // 由IdleStateTrigger.userEventTriggered()方法中断硬件和服务器连接
        socketChannel.pipeline().addLast("IdleStateHandler", new IdleStateHandler(60 * 10, 0, 0, TimeUnit.SECONDS));

        // 中断硬件和服务器连接
        // 若服务器长时间未收到硬件发来的数据，则中断和硬件的连接
        socketChannel.pipeline().addLast("IdleStateTrigger", new IdleStateTrigger());

        // 处理硬件注册到服务器
        socketChannel.pipeline().addLast("RegistrationHandler", new RegistrationHandler());

        // 维持硬件和服务器连接
        socketChannel.pipeline().addLast("IdleHandler", new IdleHandler());

        // 处理39响应(示例Handler)
        // socketChannel.pipeline().addLast(new ThreeNineHandler());

        // 处理49响应((示例Handler))
        // socketChannel.pipeline().addLast(new FourNineHandler());

        // 处理RTU发送到服务器的数据（定时读取数据）
        // 实验室设备
        socketChannel.pipeline().addLast(new ReciveCycleDataHandler());

        // 处理RTU发送到服务器的数据（实时读取数据）
        socketChannel.pipeline().addLast(new ReciveRealTimeDataHandler());

        // 实时控制数据指令返回给服务器
        socketChannel.pipeline().addLast(new ReceiveControlValveDataHandler());

        // 处理RTU发送到服务器的数据（定时读取数据）
        // 生产设备
        // socketChannel.pipeline().addLast(new RealTimeDataHandler());

        // 处理心跳报文
        socketChannel.pipeline().addLast(new HeartBeatHandler());

        // 处理下线报文
        socketChannel.pipeline().addLast(new OfflineHandler());

        // 处理登录报文
        socketChannel.pipeline().addLast(new LoginHandler());

        // 测试
        socketChannel.pipeline().addLast(new TestHandler());
    }
}
