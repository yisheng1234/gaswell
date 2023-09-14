package com.gaswell.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.gaswell.pojo.*;
import com.gaswell.service.*;
import com.gaswell.utils.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 12:40
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
//hex16进制数
// （1）RTU以固定时间(20秒)上传数据到服务器
@Component
public class ReciveCycleDataHandler extends ChannelInboundHandlerAdapter {

    static Log log = LogFactory.get(ReciveCycleDataHandler.class);
    @Autowired
    private ReciveCycleDataService reciveCycleDataService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private QjService qjService;
    @Autowired
    private ParamService paramService;

    @Autowired
    private AlgorithmRecordsService algorithmRecordsService;

    private static ReciveCycleDataHandler reciveCycleDataHandler;

    @PostConstruct
//    通过@PostConstruct实现初始化bean之前进行的操作
//    在初始化的时候初始化静态对象和它的静态成员变量healthDataService，原理是拿到service层bean对象，静态存储下来，防止被释放。
    public void init() {
        reciveCycleDataHandler = this;
        reciveCycleDataHandler.reciveCycleDataService = this.reciveCycleDataService;
        reciveCycleDataHandler.deviceInfoService = this.deviceInfoService;
        reciveCycleDataHandler.qjService = this.qjService;
        reciveCycleDataHandler.paramService=this.paramService;
        reciveCycleDataHandler.algorithmRecordsService = this.algorithmRecordsService;
        // 初使化时将已静态化的testService实例化
    }

    // 若硬件10min钟内未向服务器发送数据，触发IdleStateEvent事件，由IdleStateTrigger.userEventTriggered方法断开和硬件的连接
    // 连接断开后，执行handlerRemoved方法，从缓存和数据库中删除连接信息
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws IOException {
//        // 将设备ID、设备IP、Channel信息从缓存中删除
//        CacheLoader.deleteFromCache(ctx.channel());
//
//        // 将设备ID、设备IP从数据库删除
//        String devicdId = CacheLoader.channelMapCS.get(ctx.channel().id());
//        deviceInfoService.deleteByDeviceId(devicdId);
//        System.out.println("硬件：" + ctx.channel().remoteAddress() + " 与服务器连接已断开");
//        log.info("硬件：{} 与服务器连接已断开", ctx.channel().remoteAddress());
//    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将Netty接收到的msg转化为byte[]
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ReciveCycleData reciveCycleData = new ReciveCycleData();
        // 判断是否以回车换行(ASCII为0D 0A)结束
        if (ByteUtils.isEndWithEnter(bytes)) {
            log.info("收到RTU发来的定时数据：");
            ByteUtils.printHexString(bytes);
            // 开始解析数据
            // 从bytes中解析数据,并执行相关操作,如将数据保存到数据库
            String ascii = new String(bytes);
            log.info(ascii);                    //这个地方是   ，+空格
            String[] strings = ascii.split(", ");
            int length1 = strings.length;
            System.out.println(length1);
            String deviceId = strings[0];
            //不使用传过来的指令中的时间，使用系统中的时间。
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String datetime = df.format(new Date());

            String rtuVoltage = strings[2].substring(2);
            String rtuAI = strings[3].substring(2);


            String m0 = null;
            String m1 = null;
            String m2 = null;
            String m3 = null;
            String m4 = null;

            if (length1 == 5) {
                m0 = strings[4];
            } else if (length1 == 6) {
                m0 = strings[4];
                m1 = strings[5];
            } else if (length1 == 7) {
                m0 = strings[4];
                m1 = strings[5];
                m2 = strings[6];
            } else if (length1 == 8) {
                m0 = strings[4];
                m1 = strings[5];
                m2 = strings[6];
                m3 = strings[7];
            } else if (length1 == 9) {
                m0 = strings[4];
                m1 = strings[5];
                m2 = strings[6];
                m3 = strings[7];
                m4 = strings[8];
            }
            Double m0_Datas[] = new Double[8];
            Double m1_Datas[] = new Double[8];
            Double m2_Datas[] = new Double[8];
            Double m3_Datas[] = new Double[8];
            Double m4_Datas[] = new Double[8];

            if (m0 != null) {
                System.out.println("M0:---------------------------");
                String m0_data = m0.substring(3);
                int m0_data_len = m0_data.length();
                System.out.println(m0_data_len);
                for (int i = 0; i < (m0_data_len) / 4; i++) {
                    String s1 = m0_data.substring(4 * i, 4 * i + 4);
                    BigInteger bigInteger = new BigInteger(s1, 16);
                    m0_Datas[i] = bigInteger.doubleValue();
                }
            }
            for (int i = 0; i < m0_Datas.length; i++) {
                //获取所有属性
                if (m0_Datas[i] != null) {
                    Field[] fields = reciveCycleData.getClass().getDeclaredFields();
                    //获取属性名,要排除id，所以+1
                    String fname = fields[i + 1].getName();
                    // 将属性的首字符大写，方便构造get，set方法
                    String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);

                    Method method = reciveCycleData.getClass().getMethod("set" + name, Double.class);
                    method.invoke(reciveCycleData, m0_Datas[i]);
                }
            }

            if (m1 != null) {
                System.out.println("M1:---------------------------");
                String m1_data = m1.substring(3);
                int m1_data_len = m1_data.length();
                System.out.println(m1_data_len);
                for (int i = 0; i < m1_data_len / 4; i++) {
                    String s1 = m1_data.substring(4 * i, 4 * i + 4);

                    BigInteger bigInteger = new BigInteger(s1, 16);
                    m1_Datas[i] = bigInteger.doubleValue();

                }
            }

            for (int i = 0; i < m1_Datas.length; i++) {
                //获取所有属性
                if (m1_Datas[i] != null) {
                    Field[] fields = reciveCycleData.getClass().getDeclaredFields();
                    //获取属性名
                    String fname = fields[i + 8 + 1].getName();
                    // 将属性的首字符大写，方便构造get，set方法
                    String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);

                    Method method = reciveCycleData.getClass().getMethod("set" + name, Double.class);
                    method.invoke(reciveCycleData, m1_Datas[i]);
                }
            }

            if (m2 != null) {
                String m2_data = m2.substring(3);
                int m2_data_len = m2_data.length();
                for (int i = 0; i < m2_data_len / 4; i++) {
                    String s1 = m2_data.substring(4 * i, 4 * i + 4);
                    BigInteger bigInteger = new BigInteger(s1, 16);
                    m2_Datas[i] = bigInteger.doubleValue();
                }
            }
            for (int i = 0; i < m2_Datas.length; i++) {
                //获取所有属性
                if (m2_Datas[i] != null) {
                    Field[] fields = reciveCycleData.getClass().getDeclaredFields();
                    //获取属性名
                    String fname = fields[i + 16 + 1].getName();
                    // 将属性的首字符大写，方便构造get，set方法
                    String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);

                    Method method = reciveCycleData.getClass().getMethod("set" + name, Double.class);
                    method.invoke(reciveCycleData, m2_Datas[i]);
                }
            }
            if (m3 != null) {
                System.out.println("M3:---------------------------");
                String m3_data = m3.substring(3);
                int m3_data_len = m3_data.length();
                for (int i = 0; i < m3_data_len / 4; i++) {
                    String s1 = m3_data.substring(4 * i, 4 * i + 4);

                    BigInteger bigInteger = new BigInteger(s1, 16);
                    m3_Datas[i] = bigInteger.doubleValue();
                }
            }
            for (int i = 3; i < m0_Datas.length; i++) {
                //获取所有属性
                if (m3_Datas[i] != null) {
                    Field[] fields = reciveCycleData.getClass().getDeclaredFields();
//                System.out.println(m0_Datas[i]);
                    //获取属性名
                    String fname = fields[i + 24 + 1].getName();
                    // 将属性的首字符大写，方便构造get，set方法
                    String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);

                    Method method = reciveCycleData.getClass().getMethod("set" + name, Double.class);
                    method.invoke(reciveCycleData, m3_Datas[i]);
                }
            }
            if (m4 != null) {
                System.out.println("M4:---------------------------");
                String m4_data = m4.substring(3);
                int m4_data_len = m4_data.length();
                for (int i = 0; i < m4_data_len / 4; i++) {
                    String s1 = m4_data.substring(4 * i, 4 * i + 4);
//                    System.out.println(s1);
                    BigInteger bigInteger = new BigInteger(s1, 16);
//                    System.out.println(bigInteger.intValue());
                    m4_Datas[i] = bigInteger.doubleValue();
                }
            }
            for (int i = 4; i < m0_Datas.length; i++) {
                //获取所有属性
                if (m4_Datas[i] != null) {
                    Field[] fields = reciveCycleData.getClass().getDeclaredFields();
                    //获取属性名
                    String fname = fields[i + 32 + 1].getName();
                    // 将属性的首字符大写，方便构造get，set方法
                    String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);

                    Method method = reciveCycleData.getClass().getMethod("set" + name, Double.class);
                    method.invoke(reciveCycleData, m4_Datas[i]);
                }
            }

            //数据保存  recieveCycleData
            if (reciveCycleData.getJkwd() != null) {
                reciveCycleData.setJkwd(reciveCycleData.getJkwd() / 100);
            }
            if (reciveCycleData.getTgyl() != null) {
                reciveCycleData.setTgyl(reciveCycleData.getTgyl() / 1000);
            }
            if (reciveCycleData.getYgyl() != null) {
                reciveCycleData.setYgyl(reciveCycleData.getYgyl() / 1000);
            }
            if (reciveCycleData.getWsyl() != null) {
                reciveCycleData.setWsyl(reciveCycleData.getWsyl() / 1000);
            }
            if (reciveCycleData.getCyell() != null) {
                reciveCycleData.setCyell(reciveCycleData.getCyell() / 100);
            }
            if (reciveCycleData.getCyoull() != null) {
                reciveCycleData.setCyoull(reciveCycleData.getCyoull() / 1000);
            }
            if (reciveCycleData.getCshuill() != null) {
                reciveCycleData.setCshuill(reciveCycleData.getCshuill() / 100);
            }
            if (reciveCycleData.getCqill() != null) {
                reciveCycleData.setCqill(reciveCycleData.getCqill() / 100);
            }
            if (reciveCycleData.getJyyl() != null) {
                reciveCycleData.setJyyl(reciveCycleData.getJyyl() / 10);
            }
            if (reciveCycleData.getJyll() != null) {
                reciveCycleData.setJyll(reciveCycleData.getJyll() / 100);
            }
            reciveCycleData.setDeviceId(deviceId);
            reciveCycleData.setDatetime(datetime);
            reciveCycleData.setRtuVoltage(rtuVoltage);
            reciveCycleData.setRtuAi(rtuAI);
            // 给reciveCycleData设置气井编号（YWBH、ZWBH）
            Qj qj = reciveCycleDataHandler.qjService.findQjByTxbh(deviceId);
            reciveCycleData.setQjmc(qj.getQjmc());
            reciveCycleData.setYwbh(qj.getYwbh());
            reciveCycleData.setZwbh(qj.getZwbh());
            reciveCycleData.setScq(qj.getScq());


            // 4、判断是否数据异常（机理公式）
            // 从数据库查询该气井最新一条正常的数据（即各异常标志位都为1的记录）
            ReciveCycleData oldData = reciveCycleDataHandler.reciveCycleDataService.selectLatestNormalDataByYwbh(qj.getYwbh());

            System.out.println("olddata------------"+oldData);
            System.out.println("handle-------------"+reciveCycleData);
            String isSbyc = StatusDetect.isDeviceError(oldData, reciveCycleData);
            reciveCycleData.setSfsbyc_java(isSbyc);
            if(isSbyc.equals("1")){
                reciveCycleData.setIserror("0");
            }
            else{
                reciveCycleData.setIserror("1");
            }

            // 保存到实时生产数据备份表（记录原始数据）
            String ywbh = qj.getYwbh();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String date = sdf.format(new Date());

            String ywbhAndDateBackup = ywbh + "_" + date + "_backup";
            DynamicTableNameThreadLocal.put(ywbhAndDateBackup);
            reciveCycleDataHandler.reciveCycleDataService.addData(reciveCycleData);

            // 只有有效数据才进行工况诊断
            if(reciveCycleData.getIserror().equals("0")){
                // 1、判断是否间开（机理公式）
                Param param = new Param();
                param.setParam_name("间开阈值");
                Double jk_threshold = reciveCycleDataHandler.paramService.selectOneByEntity(param).getParam_value();
                String isJk = StatusDetect.isJianKai(reciveCycleData, jk_threshold);
                reciveCycleData.setSfjk_java(isJk);

                // 2、判断是否积液（机理公式）
                Param param2 = new Param();
                param2.setParam_name("天然气相对密度");
                double g = reciveCycleDataHandler.paramService.selectOneByEntity(param2).getParam_value();
                // double g = 0.6;
                Param param3 = new Param();
                param3.setParam_name("油管直径");
                double d = reciveCycleDataHandler.paramService.selectOneByEntity(param3).getParam_value();
                // double d = 62;
                Param param4 = new Param();
                param4.setParam_name("流速参数");
                double vg = reciveCycleDataHandler.paramService.selectOneByEntity(param4).getParam_value();
                // double vg = 2.5;
                String isJy = StatusDetect.isJiYe(reciveCycleData, g, d, vg);
                reciveCycleData.setSfjy_java(isJy);

                // 3、判断是否存在水合物（机理公式）
                String isShw = StatusDetect.isShuiHeWu(reciveCycleData, g);
                reciveCycleData.setSfshw_java(isShw);


                // 5、泡排剂加注量（机理公式）
                Param param1 = new Param();
                param1.setParam_name("泡排剂浓度");
                double param_a = reciveCycleDataHandler.paramService.selectOneByEntity(param1).getParam_value();
                param1.setParam_name("泡排剂稀释倍数");
                double param_b = reciveCycleDataHandler.paramService.selectOneByEntity(param1).getParam_value();
                double res = StatusDetect.ppjjzl(reciveCycleData.getCyell(), param_a, param_b);
                reciveCycleData.setPpjjzl_java(res + "");


                // 6、甲醇加注量（机理公式）
                param1.setParam_name("加注量");
                double param_c = reciveCycleDataHandler.paramService.selectOneByEntity(param1).getParam_value();
                double res1 = StatusDetect.jcjzl(param_c);
                reciveCycleData.setJcjzl_java(res1 + "");

                // 7、由算法端进行判断（大数据分析）
                Socket socket = new Socket("127.0.0.1", 16666);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream ps = new PrintStream(socket.getOutputStream());

                AlgorithmRecords algorithmRecords = new AlgorithmRecords();
                // 7.1 判断是否积液（大数据分析）
                algorithmRecords.setAlgorithm_type("1");
                AlgorithmRecords r1 = (AlgorithmRecords) reciveCycleDataHandler.algorithmRecordsService.selectByTypeHightest(algorithmRecords).getData();
                String p1 = AlgorithmParamBuilder.build(r1, reciveCycleData);
                ps.print(p1);
                log.info(p1);
                String str1 = br.readLine();
                log.info(str1);
                String[] split1 = str1.split("\\|");
                reciveCycleData.setSfjy_py(split1[1].substring(1, split1[1].length()-2));

                // 7.2 判断是否存在水合物（大数据分析）
                socket = new Socket("127.0.0.1", 16666);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ps = new PrintStream(socket.getOutputStream());
                algorithmRecords.setAlgorithm_type("2");
                AlgorithmRecords r2 = (AlgorithmRecords) reciveCycleDataHandler.algorithmRecordsService.selectByTypeHightest(algorithmRecords).getData();
                String p2 = AlgorithmParamBuilder.build(r2, reciveCycleData);
                ps.print(p2);
                log.info(p2);
                String str2 = br.readLine();
                log.info(str2);
                String[] split2 = str2.split("\\|");
                reciveCycleData.setSfshw_py(split2[1].substring(1, split2[1].length()-2));

                // 7.3 判断是否间开（大数据分析）
                socket = new Socket("127.0.0.1", 16666);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ps = new PrintStream(socket.getOutputStream());
                algorithmRecords.setAlgorithm_type("3");
                AlgorithmRecords r3 = (AlgorithmRecords) reciveCycleDataHandler.algorithmRecordsService.selectByTypeHightest(algorithmRecords).getData();
                String p3 = AlgorithmParamBuilder.build(r3, reciveCycleData);
                ps.print(p3);
                log.info(p3);
                String str3 = br.readLine();
                log.info(str3);
                String[] split3 = str3.split("\\|");
                reciveCycleData.setSfjk_py(split3[1].substring(1, split3[1].length()-2));

                // 7.4 泡排剂加注量（大数据分析）
                socket = new Socket("127.0.0.1", 16666);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ps = new PrintStream(socket.getOutputStream());
                algorithmRecords.setAlgorithm_type("4");
                AlgorithmRecords r4 = (AlgorithmRecords) reciveCycleDataHandler.algorithmRecordsService.selectByTypeHightest(algorithmRecords).getData();
                String p4 = AlgorithmParamBuilder.build(r4, reciveCycleData);
                ps.print(p4);
                log.info(p4);
                String str4 = br.readLine();
                log.info(str4);
                String[] split4 = str4.split("\\|");
                reciveCycleData.setPpjjzl_py(split4[1].substring(1, split4[1].length()-1));

                // 7.4 甲醇加注量（大数据分析）
                socket = new Socket("127.0.0.1", 16666);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ps = new PrintStream(socket.getOutputStream());
                algorithmRecords.setAlgorithm_type("5");
                AlgorithmRecords r5 = (AlgorithmRecords) reciveCycleDataHandler.algorithmRecordsService.selectByTypeHightest(algorithmRecords).getData();
                String p5 = AlgorithmParamBuilder.build(r5, reciveCycleData);
                ps.print(p5);
                log.info(p5);
                String str5 = br.readLine();
                log.info(str5);
                String[] split5 = str5.split("\\|");
                reciveCycleData.setJcjzl_py(split5[1].substring(1, split4[1].length()-1));
            }

            //根据设备id获取qj 英文编号，然后插入表中
//            String ywbh = reciveCycleDataHandler.qjService.findByTxbh(deviceId);

            // 保存到实时数据表
            String ywbhAndDate = ywbh + "_" + date;
            DynamicTableNameThreadLocal.put(ywbhAndDate);
            reciveCycleDataHandler.reciveCycleDataService.addData(reciveCycleData);

            log.info(reciveCycleData.toString());

            //device info
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setOnlineTime(datetime);
            Channel channel = ctx.channel();
            deviceInfo.setDeviceIp(((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress());
            //保存缓存
            CacheLoader.addToCache(channel, deviceId);
            //保存deciceInfo到数据库
            log.info(deviceInfo.toString());
            reciveCycleDataHandler.deviceInfoService.addData(deviceInfo);
        } else { // 否则将消息向后传递,交由其他Handler进行处理
            ByteBuf heapBuf = Unpooled.buffer();
            heapBuf.writeBytes(bytes);
            ctx.fireChannelRead(heapBuf);
        }
    }
}
