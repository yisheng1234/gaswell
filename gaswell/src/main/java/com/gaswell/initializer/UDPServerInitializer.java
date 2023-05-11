package com.gaswell.initializer;

import com.gaswell.handler.udp.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class UDPServerInitializer extends ChannelInitializer<Channel> {

    protected void initChannel(Channel channel) throws Exception{
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new UDPHandler());
        pipeline.addLast(new LoginHandler());
        pipeline.addLast(new HeartBeatHandler());
        pipeline.addLast(new RealTimeDataHandler());
        pipeline.addLast(new OfflineHandler());

    }
}
