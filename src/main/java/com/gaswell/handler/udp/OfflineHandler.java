package com.gaswell.handler.udp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.pojo.DeviceInfo;
import com.gaswell.service.DeviceInfoService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CommandBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OfflineHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    static Log log = LogFactory.get(com.gaswell.handler.LoginHandler.class);

    @Autowired
    private DeviceInfoService deviceInfoService;

    private static OfflineHandler offlineHandler;

    @PostConstruct
    public void init() {
        offlineHandler = this;
        offlineHandler.deviceInfoService = this.deviceInfoService;
        // 初使化时将已静态化的testService实例化
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("Offline start");
        ByteBuf byteBuf = msg.content();
        int length = byteBuf.readableBytes();
        // 将数据读入bytes
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        // 硬件向服务器发送下线报文
        if (bytes.length == 22 && bytes[0] == 0x7B && bytes[1] == 0x82) {

            // 设备ID
            byte[] b_deviceId = new byte[11];
            for (int i = 0; i <= 10 ; i++) {
                b_deviceId[i] = bytes[i+4];
            }
            String deviceId = new String(b_deviceId);

            // 从数据库删除
            offlineHandler.deviceInfoService.deleteByDeviceId(deviceId);

            // 服务器回复下线响应报文
            heapBuf.writeBytes(CommandBuilder.offlineResponseCommand(bytes));
            //ctx.channel().writeAndFlush(heapBuf);
            ctx.writeAndFlush(new DatagramPacket(heapBuf, msg.sender()));
        } else {
            // ctx.fireChannelRead(msg.retain());
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(new DatagramPacket(heapBuf,
                    msg.recipient(), msg.sender()));
        }
        // ReferenceCountUtil.release(msg);

        System.out.println(ctx.channel().localAddress());
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg.sender().getAddress());
        System.out.println(msg.sender().getPort());

        System.out.println("Offline end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
