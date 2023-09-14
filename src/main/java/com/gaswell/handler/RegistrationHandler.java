package com.gaswell.handler;

import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lei Wang
 * @email ileiwang@live.com
 * @date 2020/10/20 10:32
 */

// 处理硬件注册信息
// 硬件发送A+equipmentId(SIM卡号)即表示将硬件连接到服务器
// 服务器将equipmentId及对应的Channel.ChannelId保存到CatchLoader中
@Slf4j
@RestController
@ChannelHandler.Sharable
public class RegistrationHandler extends ChannelInboundHandlerAdapter {

    // 当Channel 已经注册到它的EventLoop 并且能够处理I/O 时被调用
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Registered!");
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 已注册到EventLoop");
        ctx.fireChannelRegistered();
    }

    // 当Channel 从它的EventLoop 注销并且无法处理任何I/O 时被调用
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Unregistered!");
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 已从EventLoop取消注册");
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Active!");
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 已连接到服务器");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel Inactive!");
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 已断开服务器");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws IOException {
        log.info("Handler Removed!");
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 与服务器连接已被移除");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        // 如果A开头，长度为12，且后11位为equipmentId(SIM卡号)，认为是注册消息
        if (bytes[0] == (byte) 0x41 && ByteUtils.isRegistration(bytes)) {
            System.out.println("收到硬件注册信息:");
            ByteUtils.printHexString(bytes);
            String equipmentId = new String(bytes).substring(1, 12);
            System.out.println("equipmentId（SIM卡号）： " + equipmentId);

            // 上线时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String onlineTime = formatter.format(date).toString();

//            // 将硬件equipmentId及连接时间插入到数据库在线设备表中
//            String url = "http://127.0.0.1:10001/api/addOnlineDevice?equipmentId=" + equipmentId + "&onlineTime=" + onlineTime;
//            HttpClient.sendGetRequest(url);

            // 将equipmentId及Channel.ChannleId保存到channelGroup/channelMapSC/channelMapCS中
            CacheLoader.addChannel(ctx.channel(), equipmentId);

        } else {
            ByteBuf heapBuf = Unpooled.buffer();
            heapBuf.writeBytes(bytes);
            // 调用ChannelPipeline 中下一个ChannelInboundHandler 的 channelRead(ChannelHandlerContext, Object msg)方法
            ctx.fireChannelRead(heapBuf);
        }
        // 释放消息资源
        ReferenceCountUtil.release(msg);
    }
}


