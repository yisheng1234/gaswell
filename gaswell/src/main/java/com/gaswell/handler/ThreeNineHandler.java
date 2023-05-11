package com.gaswell.handler;

import com.gaswell.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Lei Wang
 * @email ileiwang@live.com
 * @date 2020/10/20 10:32
 */
// 处理硬件发来的39响应信息
public class ThreeNineHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将Netty接收到的Object msg转化为byte[]

        // ctx.fireChannelRead(msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteUtils.printHexString(bytes);

        // 如果收到39响应,则执行处理
        if (bytes.length == 16 && bytes[9] == (byte) 0x39) {
            System.out.println("收到39响应");
            ByteUtils.printHexString(bytes);

            // 开始
            // 从bytes中解析数据,并执行相关操作,如将数据保存到数据库
            // 结束

        } else { // 否则将消息向后传递,交由其他Handler进行处理
            ByteBuf heapBuf = Unpooled.buffer();
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
    }
}
