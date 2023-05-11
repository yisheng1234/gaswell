package com.gaswell.handler.udp;

import com.gaswell.utils.QueryDataList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.HashMap;
import java.util.Map;

// 用于处理发送设备参数查询指令后，硬件返回的参数数据
public class DeviceSettingHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf byteBuf = msg.content();
        int length = byteBuf.readableBytes();
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

            String deviceId = new String(b_deviceId).substring(0,5);

            // 数据体为UDP数据包16位之后
            int data_length = length-16;
            byte[] data = new byte[data_length];
            for (int i = 0; i < data_length; i++) {
                data[i] = bytes[i+16];
            }



            // 收到参数查询指令返回的响应后，将参数保存到paramQueryValue里，同时将查询记录从paramQueryLog中删除。
            if(QueryDataList.paramQueryValue.get(deviceId) == null){
                Map<String,Integer> map = new HashMap<String,Integer>();
                // TODO 从data中解析数据，并保存到paramQueryValue中
                map.put(QueryDataList.paramQueryLog.get(deviceId),((int) bytes[bytes.length-1]));
                QueryDataList.paramQueryValue.put(deviceId,map);
                QueryDataList.paramQueryLog.remove(deviceId);
            }
            else{
                QueryDataList.paramQueryValue.get(deviceId).put(QueryDataList.paramQueryLog.get(deviceId),((int) bytes[bytes.length-1]));
                QueryDataList.paramQueryLog.remove(deviceId);
            }

        } else {
            // ctx.fireChannelRead(msg.copy());
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(new DatagramPacket(heapBuf,
                    msg.recipient(), msg.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
