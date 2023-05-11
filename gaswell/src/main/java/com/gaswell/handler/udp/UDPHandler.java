package com.gaswell.handler.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UDPHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("UDPHandler start");

        System.out.println(ctx.channel().localAddress());
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg.sender().getAddress());
        System.out.println(msg.sender().getPort());

        ctx.fireChannelRead(msg.retain());


        System.out.println(ctx.channel().localAddress());
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg.sender().getAddress());
        System.out.println(msg.sender().getPort());
        System.out.println("UDPHandler end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
