package com.gaswell.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.service.DeviceInfoService;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Lei Wang
 * @Date: 2022/05/20/ 15:47
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 处理下线报文
public class OfflineHandler extends ChannelInboundHandlerAdapter {

    static Log log = LogFactory.get(OfflineHandler.class);

    @Autowired
    private DeviceInfoService deviceInfoService;

    private static OfflineHandler offlineHandler;

    @PostConstruct
//    通过@PostConstruct实现初始化bean之前进行的操作
//    在初始化的时候初始化静态对象和它的静态成员变量healthDataService，原理是拿到service层bean对象，静态存储下来，防止被释放。
    public void init() {
        offlineHandler = this;
        offlineHandler.deviceInfoService = this.deviceInfoService;
        // 初使化时将已静态化的testService实例化
    }


    // 若硬件10min钟内未向服务器发送心跳报文，触发IdleStateEvent事件，由IdleStateTrigger.userEventTriggered方法断开和硬件的连接
    // 连接断开后，执行handlerRemoved方法，从缓存和数据库中删除连接信息
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws IOException {
        // 将设备ID、设备IP、Channel信息从缓存中删除
        String devicdId = CacheLoader.channelMapCS.get(ctx.channel().id());
        CacheLoader.deleteFromCache(ctx.channel());
        // 将设备ID、设备IP从数据库删除
        deviceInfoService.deleteByDeviceId(devicdId);
        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 与服务器连接已断开");
        log.info("硬件：{} 与服务器连接已断开", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        if (bytes.length == 22 && bytes[0] == 0x7B && bytes[1] == 0x82 && CacheLoader.channelMapCS.get(ctx.channel().id()) != null) {
            // 硬件向服务器发送下线报文
            // 服务器回复下线响应报文
            heapBuf.writeBytes(CommandBuilder.offlineResponseCommand(bytes));
            ctx.channel().writeAndFlush(heapBuf);
            // 从缓存中移除网络连接
            CacheLoader.deleteFromCache(ctx.channel());
        } else {
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
        ReferenceCountUtil.release(msg);
    }

}
