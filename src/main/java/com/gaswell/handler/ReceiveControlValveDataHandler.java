package com.gaswell.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.nettyDemo.Utils;
import com.gaswell.pojo.ControlValveLog;
import com.gaswell.service.ControlValveLogService;
import com.gaswell.service.DeviceInfoService;
import com.gaswell.service.ReciveCycleDataService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.ControlValveList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author Lei Wang
 * @Date: 2022/01/16/ 21:57
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// （3）实时控制数据指令返回给服务器
public class ReceiveControlValveDataHandler extends ChannelInboundHandlerAdapter {

    static Log log = LogFactory.get(ReceiveControlValveDataHandler.class);

    @Autowired
    private ReciveCycleDataService reciveCycleDataService;
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private ControlValveLogService controlValveLogService;

    private static ReceiveControlValveDataHandler receiveControlValveDataHandler;
    @PostConstruct
//    通过@PostConstruct实现初始化bean之前进行的操作
//    在初始化的时候初始化静态对象和它的静态成员变量healthDataService，原理是拿到service层bean对象，静态存储下来，防止被释放。
    public void init() {
        receiveControlValveDataHandler = this;
        receiveControlValveDataHandler.reciveCycleDataService = this.reciveCycleDataService;
        receiveControlValveDataHandler.deviceInfoService = this.deviceInfoService;
        receiveControlValveDataHandler.controlValveLogService = this.controlValveLogService;
        // 初使化时将已静态化的testService实例化
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 将Netty接收到的msg转化为byte[]
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        // 从bytes中解析数据,并执行相关操作,如将数据保存到数据库

        // 判断bytes[1]是否为0x06写功能码，同时检查校验码是否正确
        if (bytes[1] == 0x03 && Utils.isCrcRight(bytes) && bytes.length == 8) {
            log.info("收到RTU发来的实时控制指令响应数据：");
            ByteUtils.printHexString(bytes);
            log.info(ByteUtils.bytesToHexString(bytes));

            // 获得数据来自于哪个设备
            // 方法1：先从ctx中获取连接的IP地址，再从CacheLoader.deviceIdAndIp中获得IP地址对应的设备ID
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ipAddress = inetSocketAddress.getAddress().getHostAddress();
            String deviceId = null;
            for (String s : CacheLoader.deviceIdAndIp.keySet()) {
                if (CacheLoader.deviceIdAndIp.get(s).equals(ipAddress)) {
                    deviceId = s;
                    break;
                }
            }

            // 方法2：从CacheLoader.channelMapCS中获得ChannelId对应的设备ID
            ChannelId channelId = ctx.channel().id();
            String deviceId1 = CacheLoader.channelMapCS.get(channelId);

            // 从ControlValveList.controlCommandLogList中获得控制指令记录对象

            ControlValveLog controlValveLog = ControlValveList.controlCommandLogList.get(deviceId);

            // 判断设置是否成功

            // （问题1：如果设备未返回响应，如何在一段时间间隔之后，将结果设置为失败并保存到数据库中？）
            // （问题2：如果设备未返回响应，再次向同一个设备发送不同的实时控制数据指令应如何处理？）

            // 从ControlValveList.controlCommandList或controlValveLog.getCommand()中获得指令内容
            // eg：0206000A0001683B
            // String command = ControlValveList.controlCommandList.get(deviceId);
            String command = controlValveLog.getCommand();

            // 从设备中获得返回的指令内容
            String commandFromDevice = Utils.bytesToHexString(bytes);
            if (command.equals(commandFromDevice)) {
                controlValveLog.setResult("OK");
            } else {
                controlValveLog.setResult("Fail");
            }

            // 保存controlValveLog到数据库
            controlValveLogService.save(controlValveLog);

            // 将对象从controlCommandLogList中删除
            ControlValveList.controlCommandLogList.remove(deviceId);

        } else { // 否则将消息向后传递,交由其他Handler进行处理
            ByteBuf heapBuf = Unpooled.buffer();
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
    }
}
