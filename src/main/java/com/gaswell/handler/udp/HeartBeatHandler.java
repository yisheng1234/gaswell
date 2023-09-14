package com.gaswell.handler.udp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.pojo.DeviceInfo;
import com.gaswell.service.DeviceInfoService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartBeatHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    static Log log = LogFactory.get(com.gaswell.handler.LoginHandler.class);

    @Autowired
    private DeviceInfoService deviceInfoService;

    private static HeartBeatHandler heartBeatHandler;

    @PostConstruct
    public void init() {
        heartBeatHandler = this;
        heartBeatHandler.deviceInfoService = this.deviceInfoService;
        // 初使化时将已静态化的testService实例化
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("HeartBeat start");
        System.out.println(msg);
        System.out.println(msg.content());
        ByteBuf byteBuf = msg.content();
        int length = byteBuf.readableBytes();
        System.out.println(length);
        // 将数据读入bytes
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        // 可指定长度
        // ByteBuf heapBuf = Unpooled.buffer(2048);
        ByteBuf heapBuf = Unpooled.buffer();
        if (bytes.length == 22 && bytes[0] == 0x7B && bytes[1] == 0x01) {
            // 硬件向服务器发送心跳报文

            // 设备ID
            byte[] b_deviceId = new byte[11];
            for (int i = 0; i <= 10 ; i++) {
                b_deviceId[i] = bytes[i+4];
            }
            String deviceId = new String(b_deviceId);

            // IP
            byte b_ip1 = bytes[15];
            int ip1 = b_ip1 & 0xff;

            byte b_ip2 = bytes[16];
            int ip2 = b_ip2 & 0xff;

            byte b_ip3 = bytes[17];
            int ip3 = b_ip3 & 0xff;

            byte b_ip4 = bytes[18];
            int ip4 = b_ip4 & 0xff;
            String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

            // 端口
            byte[] b_port = new byte[2];
            for (int i = 0; i <= 1 ; i++) {
                b_port[i] = bytes[i+19];
            }
            String port = ByteUtils.byteArrayToInt2(b_port)+"";

            // 上线时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String onlineTime = formatter.format(date).toString();

            // 添加设备信息到数据库
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setOnlineTime(onlineTime);
            // 从消息中解析出的ip应该与从msg中获取的ip一致
            deviceInfo.setDeviceIp(msg.sender().getAddress().getHostAddress());
            // 从消息中解析出的port应该与从msg中获取的port一致
            deviceInfo.setDevicePort(msg.sender().getPort()+"");
            heartBeatHandler.deviceInfoService.updateByDeviceId(deviceInfo);

            // 服务器回复心跳响应报文
            heapBuf.writeBytes(CommandBuilder.heartBeatResponseCommand(bytes));
            //ctx.channel().writeAndFlush(heapBuf);
            ctx.writeAndFlush(new DatagramPacket(heapBuf, msg.sender()));
        } else {
            // ctx.fireChannelRead(msg.retain());
//            heapBuf.writeBytes(bytes);
//            ctx.fireChannelRead(new DatagramPacket(heapBuf,
//                    msg.recipient(), msg.sender()));

            // method 2
            msg.content().resetReaderIndex();
            ctx.fireChannelRead(msg.retain());
        }
        // ReferenceCountUtil.release(msg);

        System.out.println(ctx.channel().localAddress());
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg.sender().getAddress());
        System.out.println(msg.sender().getPort());

        System.out.println("HeartBeat end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
