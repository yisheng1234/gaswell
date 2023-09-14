package com.gaswell.handler.udp;

import com.gaswell.utils.CommandBuilder;
import com.gaswell.utils.QueryDataList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.HashMap;
import java.util.Map;

public class RealTimeDataHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("RealTimeData start");
        System.out.println(msg);
        System.out.println(msg.content());
        ByteBuf byteBuf = msg.content();
        int length = byteBuf.readableBytes();
        System.out.println(length);
        // 将数据读入bytes
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        // UDP上报数据报文
        if (bytes[0] == 0x7B && bytes[1] == 0x09) {

            // 设备ID
            byte[] b_deviceId = new byte[11];
            for (int i = 0; i <= 10 ; i++) {
                b_deviceId[i] = bytes[i+4];
            }

            // 数据体为UDP数据包16位之后
            int data_length = length-16;
            byte[] data = new byte[data_length];
            for (int i = 0; i < data_length; i++) {
                data[i] = bytes[i+16];
            }

            String deviceId = new String(b_deviceId).substring(0,5);
            System.out.println("deviceId:"+deviceId+",modbus:" + QueryDataList.queryLog.get(deviceId));

            // 收到指令返回的响应后，将返回值保存到queryValue里，同时将查询记录从queryLog中删除。
            if(QueryDataList.queryValue.get(deviceId)==null){
                Map<String,Integer> map = new HashMap<String,Integer>();
                map.put(QueryDataList.queryLog.get(deviceId),((int) bytes[bytes.length-1]));
                QueryDataList.queryValue.put(deviceId,map);
                QueryDataList.queryLog.remove(deviceId);
                System.out.println(((int) bytes[bytes.length-1]) + "");
            }
            else{
                QueryDataList.queryValue.get(deviceId).put(QueryDataList.queryLog.get(deviceId),((int) bytes[bytes.length-1]));
                QueryDataList.queryLog.remove(deviceId);
                System.out.println(((int) bytes[bytes.length-1]) + "");
            }

        } else {
            // ctx.fireChannelRead(msg.copy());
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(new DatagramPacket(heapBuf,
                    msg.recipient(), msg.sender()));
        }
        // ReferenceCountUtil.release(msg);

        System.out.println(ctx.channel().localAddress());
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg.sender().getAddress());
        System.out.println(msg.sender().getPort());

        System.out.println("RealTimeData end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
