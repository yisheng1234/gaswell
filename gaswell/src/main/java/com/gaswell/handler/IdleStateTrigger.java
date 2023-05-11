package com.gaswell.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Lei Wang
 * @email ileiwang@live.com
 * @date 2020/10/20 10:32
 */
// 心跳触发断开
// 若服务器长时间未收到硬件发来的数据，则中断和硬件的连接
@ChannelHandler.Sharable
public class IdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }

    // 若10分钟内未收到硬件发来的数据，则触发IdleStateEvent事件，由userEventTriggered()方法处理，断开和硬件的连接。
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            // 若服务器长时间未收到硬件发送消息，先向硬件发送A字符，然后主动断开连接。
            if (state == IdleState.READER_IDLE) {
                // ctx.channel().writeAndFlush(Unpooled.buffer().writeByte(0x41));
                // 不延迟会导致有时候硬件接收不到,因为缓冲区还没来得及发送,channel就宕掉了
                // Thread.sleep(3000);
                // 断开连接
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
