package com.gaswell.handler;

import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author Lei Wang
 * @Date: 2022/05/20/ 15:28
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 处理心跳报文
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        if (bytes.length == 22 && bytes[0] == 0x7B && bytes[1] == 0x01 && CacheLoader.channelMapCS.get(ctx.channel().id()) != null) {
            // 硬件向服务器发送心跳报文
            // 服务器恢复心跳响应报文
            heapBuf.writeBytes(CommandBuilder.heartBeatResponseCommand(bytes));
            ctx.channel().writeAndFlush(heapBuf);
        } else {
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
        ReferenceCountUtil.release(msg);
    }
}
