package com.gaswell.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.gaswell.nettyDemo.Utils;
import com.gaswell.pojo.ReciveRealTimeData;
import com.gaswell.service.ReciveRealTimeDataService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.QueryDataList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 13:15
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// （2）RTU实时上传数据到服务器
    @Component
public class ReciveRealTimeDataHandler extends ChannelInboundHandlerAdapter {
    static Log log = LogFactory.get(ReciveCycleDataHandler.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ReciveRealTimeDataService reciveRealTimeDataService;
    private static ReciveRealTimeDataHandler reciveRealTimeHandler;
    @PostConstruct
    public void init() {
        reciveRealTimeHandler = this;
        reciveRealTimeHandler.reciveRealTimeDataService = this.reciveRealTimeDataService;
        reciveRealTimeHandler.redisTemplate=this.redisTemplate;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("实时读取数据---handle");
        // 将Netty接收到的msg转化为byte[]
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        int length = bytes.length;

        // 判断bytes[1]是否为0x03读功能码,同时检查校验码是否正确
        if (bytes[1] == 3 && Utils.isCrcRight(bytes)) {
            log.info("收到RTU发来的实时数据：");
            ByteUtils.printHexString(bytes);
            log.info("--------------------");


            // 获得数据来自于哪个设备
            // 方法1：先从ctx中获取连接的IP地址，再从CacheLoader.deviceIdAndIp中获得IP地址对应的设备ID
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ipAddress = inetSocketAddress.getAddress().getHostAddress();
            String deviceId = null;
//            for (String s : CacheLoader.deviceIdAndIp.keySet()) {
//                System.out.println("-----"+CacheLoader.deviceIdAndIp.get(s));
//                if (CacheLoader.deviceIdAndIp.get(s).equals(ipAddress)) {
//                    deviceId = s;
//                    break;
//                }
//            }
            System.out.println(deviceId);
            Double datas[] = new Double[13];

            // {ModBus站内地址0-9：数据放大倍数}
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 100);
            map.put(1, 1000);
            map.put(2, 1000);
            map.put(3, 1000);
            map.put(4, 100);
            map.put(5, 1000);
            map.put(6, 100);
            map.put(7, 100);
            map.put(8, 10);
            map.put(9, 100);

            // {PLC存储位置:ModBus站内地址}
            Map<String, Integer> map1 = new HashMap<>();
            map1.put("VW4000", 0);
            map1.put("VW4002", 1);
            map1.put("VW4004", 2);
            map1.put("VW4006", 3);
            map1.put("VW4008", 4);
            map1.put("VW4010", 5);
            map1.put("VW4012", 6);
            map1.put("VW4014", 7);
            map1.put("VW4016", 8);
            map1.put("VW4018", 9);
            map1.put("VW4020", 10);
            map1.put("VW4022", 11);
            map1.put("VW4024", 12);

            // 开始解析数据
            // 从bytes中解析数据,并执行相关操作,如将数据保存到数据库

//            // byte[]转为字符串
//            String ascii = new String(bytes, "UTF-8");
//            System.out.println(ascii); // 乱码，0x00-1x1F非可打印字符

            // byte转为int，获取返回的字节数
            byte b_num = bytes[2];
            int i_num = Integer.valueOf(Byte.toString(b_num));
            System.out.println("获取字节数"+i_num);



            // 从QueryDataList.queryPositionList中获取数据的起始地址
            // eg：VW4000
            String startPosition = QueryDataList.queryPositionList.get(deviceId);

            int startPositionNum=Integer.valueOf(map1.get(startPosition));
            System.out.println("起始位置"+startPositionNum);


            // 复制数据
            byte[] b_data = new byte[length - 5];
            for (int i = 0; i < i_num; i++) {
                b_data[i] = bytes[i + 3];
            }
            double propertiesValue[]=new double[i_num / 2];
            // 将各字段数据赋值给reciveCycleData对象，并保存到数据库中
            for (int i = 0; i < i_num / 2; i++) {
                byte[] b2i_2 = new byte[2];
                b2i_2[0] = b_data[2 * i];
                b2i_2[1] = b_data[2 * i + 1];
                int res_2 = Utils.byteArrayToInt2(b2i_2);
                System.out.println(res_2);
//                System.out.println(map.get(startPositionNum+i));
                if((startPositionNum+i)<10) {
                    propertiesValue[i] = Double.valueOf(res_2) / map.get(startPositionNum + i);
                }else{
                    propertiesValue[i]=Double.valueOf(res_2);
                }
            }
            for (double v : propertiesValue) {
                System.out.println(v);
            }
            ReciveRealTimeData reciveRealTimeData=new ReciveRealTimeData();
            Field[] fields=reciveRealTimeData.getClass().getDeclaredFields();
            for (int i = 0; i < i_num/2 ; i++) {
                String pname = fields[i + 1 + startPositionNum].getName();
                String name = pname.substring(0, 1).toUpperCase() + pname.substring(1);
                System.out.println(name);
                Method method = reciveRealTimeData.getClass().getMethod("set" + name, Double.class);
                method.invoke(reciveRealTimeData, propertiesValue[i]);
            }
            System.out.println(reciveRealTimeData);
            //将读取到的数据存到数据库
//            reciveRealTimeHandler.reciveCycleDataService.addData(reciveRealTimeData);
            reciveRealTimeHandler.reciveRealTimeDataService.addData(reciveRealTimeData);
            //将对象转为json类型
            String jsonData=JSONObject.toJSONString(reciveRealTimeData);
            reciveRealTimeHandler.redisTemplate.opsForValue().set("data",jsonData);
            reciveRealTimeHandler.redisTemplate.expire("data",2, TimeUnit.MINUTES);
            // 结束解析数据

        } else { // 否则将消息向后传递,交由其他Handler进行处理
            ByteBuf heapBuf = Unpooled.buffer();
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
    }
}
