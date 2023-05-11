package com.gaswell.handler;

import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * @author Lei Wang
 * @email ileiwang@live.com
 * @date 2020/10/20 10:32
 */

// 维持硬件和服务器连接
// 硬件定期向服务器发送手机号
// 服务器回复ok心跳给已连接的硬件
public class IdleHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        // 将硬件发送的数据解析为字符串
        String phoneNumber = new String(bytes, Charset.forName("UTF-8"));
        // 如果硬件已经和服务器建立连接,则服务器向硬件回复ok作为响应
        // 硬件连接到服务器后,每次通信的连接对象ctx.channel()相同
        if (bytes.length == 11 && ByteUtils.isPhoneNumber(bytes) && CacheLoader.channelMapCS.get(ctx.channel().id()) != null && CacheLoader.channelMapCS.get(ctx.channel().id()).equals(phoneNumber)) {
            // 硬件向服务器发送心跳信息（手机号码）
            // Utils.printHexString(bytes);
            // 服务器回复ok给硬件
            heapBuf.writeBytes("ok".getBytes());
            ctx.channel().writeAndFlush(heapBuf);
        } else {
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
        ReferenceCountUtil.release(msg);
    }
}
