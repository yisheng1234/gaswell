package com.gaswell.initializer;

import com.gaswell.utils.CacheLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


// https://blog.csdn.net/GrayBar/article/details/119830176
// https://blog.csdn.net/hhl18730252820/article/details/123379931
// https://blog.csdn.net/qq_21033663/article/details/113773141

@Slf4j
public class UDPNettyServer {

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new UDPServerInitializer())
                .localAddress(new InetSocketAddress(9000));
        //      .bind(9000)
        try {
            Channel channel = bootstrap.bind().syncUninterruptibly().channel();
            // 保存连接对象，用于发送UDP指令
            CacheLoader.udpChannel = channel;
            log.info("Netty服务器启动，开始监听端口: {}", 9000);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
