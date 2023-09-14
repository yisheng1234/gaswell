package com.gaswell.handler;

import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.QueryDataList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.HashMap;
import java.util.Map;


public class TestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ByteBuf heapBuf = Unpooled.buffer();
        if (bytes.length == 7 && bytes[0] == (byte)0xAA) {
            QueryDataList.map.put(QueryDataList.current, ((int) bytes[bytes.length-1]) + "");
            System.out.println(QueryDataList.current);
            System.out.println(((int) bytes[bytes.length-1]) + "");
            System.out.println(QueryDataList.map);
        }
        else if(bytes.length == 7 && bytes[0] == (byte)0xBB){
            // 实际设备ID需解析得到
            String deviceId = "1111";
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
        }
        // 假设硬件发送0x00 0x00 0x00 0x00即为注册信息
        // 1111为设备编号
        else if(bytes.length == 4){
            System.out.println("模拟设备注册");
            CacheLoader.addChannel(ctx.channel(), "1111");
        }
        else {
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
        ReferenceCountUtil.release(msg);
    }
}
