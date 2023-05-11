package com.gaswell.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.pojo.DeviceInfo;
import com.gaswell.service.DeviceInfoService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lei Wang
 * @Date: 2022/05/20/ 16:10
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {

    static Log log = LogFactory.get(LoginHandler.class);

    @Autowired
    private DeviceInfoService deviceInfoService;

    private static LoginHandler loginHandler;

    @PostConstruct
//    通过@PostConstruct实现初始化bean之前进行的操作
//    在初始化的时候初始化静态对象和它的静态成员变量healthDataService，原理是拿到service层bean对象，静态存储下来，防止被释放。
    public void init() {
        loginHandler = this;
        loginHandler.deviceInfoService = this.deviceInfoService;
        // 初使化时将已静态化的testService实例化
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        if (bytes.length == 22 && bytes[0] == 0x7B && bytes[1] == 0x03 && CacheLoader.channelMapCS.get(ctx.channel().id()) == null) {
            // 硬件向服务器发送登录报文


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

            // 保存连接信息到缓存、添加设备信息到数据库
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setOnlineTime(onlineTime);
            Channel channel = ctx.channel();
            // 从消息中解析出的ip应该与从channel中获取的ip一致
            deviceInfo.setDeviceIp(((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress());
            // 从消息中解析出的port应该与从channel中获取的port一致
            deviceInfo.setDevicePort(((InetSocketAddress) channel.remoteAddress()).getPort() + "");
            //保存缓存
            CacheLoader.addToCache(channel, deviceId);
            //保存deciceInfo到数据库
            log.info("添加新设备：{}", deviceInfo.toString());
            loginHandler.deviceInfoService.addData(deviceInfo);

            // 服务器回复登录响应报文
            heapBuf.writeBytes(CommandBuilder.loginResponseCommand(bytes));
            ctx.channel().writeAndFlush(heapBuf);
            // 从缓存中移除网络连接
            CacheLoader.addToCache(ctx.channel(), deviceId);

        } else {
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
        ReferenceCountUtil.release(msg);
    }

}
