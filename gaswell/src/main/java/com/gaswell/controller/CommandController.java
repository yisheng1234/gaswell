package com.gaswell.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.mapper.ModbusLogMapper;
import com.gaswell.mapper.ModbusSiteMapper;
import com.gaswell.pojo.ModbusLog;
import com.gaswell.pojo.ModbusSite;
import com.gaswell.service.ModbusSiteService;
import com.gaswell.utils.ByteUtils;
import com.gaswell.utils.CacheLoader;
import com.gaswell.utils.CommandBuilder;
import com.gaswell.utils.QueryDataList;
import com.gaswell.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 13:42
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@RestController
@RequestMapping("/command")
@Api(tags = "发送命令")
public class CommandController {
    @Autowired
    private CommandBuilder commandBuilder;
    @Autowired
    private ModbusLogMapper modbusLogMapper;
    @Autowired
    private ModbusSiteMapper modbusSiteMapper;
    @Autowired
    private ModbusSiteService modbusSiteService;
    static Log log = LogFactory.get(CommandController.class);

    // http://localhost:8888/command/queryData?userName=wang&plcPosition=VM4020&dataNum=2&deviceId=602042504521
    @GetMapping("/queryData")
    @ApiOperation("发送实时读取数据指令")
    @LogAnnotation(module = "发送命令", operator = "发送实时读取数据指令")
    public Result queryData(@RequestParam("userName") String userName, @RequestParam("plcPosition") String plcPosition, @RequestParam("dataNum") int dataNum, @RequestParam("deviceId") String deviceId) {
        // 判断设备ID是否在线
        if (!CacheLoader.channelMapSC.containsKey(deviceId)) { // 不在线
            log.info("设备不在线");
            return Result.fail(444, "设备不在线");
        } else { // 在线
            ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
            ByteBuf byteBuf = Unpooled.buffer();
            // Utils.printHexString(CommandBuilder.queryDataCommand(plcPosition, dataNum));
            byteBuf.writeBytes(CommandBuilder.queryDataCommand(plcPosition, dataNum));
            // 将指令发送到设备
            CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
            String hexString = ByteUtils.bytesToHexString(CommandBuilder.queryDataCommand(plcPosition, dataNum));
            // 保存设备ID和数据起始地址
            QueryDataList.queryPositionList.put(deviceId, plcPosition);
//            System.out.println(deviceId);
            log.info("实时读取数据指令已发送给设备：{}", deviceId);
            log.info("指令内容：{}", hexString);
            return Result.success("实时读取数据指令已发送给设备：" + deviceId);
        }
    }

    // http://localhost:8888/command/controlValve?userName=wang&plcPosition=VW4022&status=ON&deviceId=602042504521
//    @GetMapping("/controlValve")
//    @ApiOperation("发送实时控制数据指令")
//    @LogAnnotation(module = "发送命令", operator = "发送实时控制数据指令")
//    public Result controlValve(@RequestParam("userName") String userName, @RequestParam("plcPosition") String plcPosition, @RequestParam("status") String status, @RequestParam("deviceId") String deviceId) {
//        // 判断设备ID是否在线
//        if (!CacheLoader.channelMapSC.containsKey(deviceId)) { // 不在线
//            log.info("设备不在线");
//            return Result.fail(444, "设备不在线");
//        }
//        else { // 在线
//            if(ControlValveList.controlCommandLogList.get(deviceId) != null){
//                log.info("上次指令未返回响应，请稍后再试");
//                return Result.fail(444, "上次指令未返回响应，请稍后再试");
//            }
//            ChannelId channelId = CacheLoader.channelMapSC.get(deviceId);
//            ByteBuf byteBuf = Unpooled.buffer();
//            // Utils.printHexString(CommandBuilder.controlValveCommand(plcPosition, status));
//            byteBuf.writeBytes(CommandBuilder.controlValveCommand(plcPosition, status));
//
//            // 将指令发送到设备
//            CacheLoader.channelGroup.find(channelId).writeAndFlush(byteBuf);
//
//            // 记录设备ID和控制指令的Hex字符串
//            String hexString = ByteUtils.bytesToHexString(CommandBuilder.controlValveCommand(plcPosition, status));
//            ControlValveList.controlCommandList.put(deviceId, hexString);
//
//            // 记录设备ID和控制指令记录对象
//            ControlValveLog controlValveLog = new ControlValveLog();
//            // 设置设备ID
//            controlValveLog.setDeviceId(deviceId);
//            // 设置控制时间
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//            Date date = new Date(System.currentTimeMillis());
//            controlValveLog.setControlTime(formatter.format(date).toString());
//            // 设置开关量
//            controlValveLog.setPlcPosition(plcPosition);
//            // 设置开关类型
//            controlValveLog.setStatus(status);
//            // 设置指令内容
//            controlValveLog.setCommand(hexString);
//            ControlValveList.controlCommandLogList.put(deviceId, controlValveLog);
//            log.info("实时控制数据指令已发送给设备：", deviceId);
//            log.info("指令内容：", hexString);
//            return Result.success("实时控制数据指令已发送给设备：" + deviceId);
//
//        }
//    }
    @GetMapping("/controlValve")
    @ApiOperation("发送实时控制数据指令")
    @LogAnnotation(module = "发送命令", operator = "发送实时控制数据指令")
    public Result controlValve(String userName,String param,String data, String jh){
        byte[] bytes = commandBuilder.rtuCommand(param, data);
        ModbusLog modbusLog=new ModbusLog();
        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        modbusLog.setTime(sdf.format(new Date()));

        LambdaQueryWrapper<ModbusSite> lqw1=new LambdaQueryWrapper<>();
        lqw1.eq(ModbusSite::getParamName,param);

        ModbusSite modbusSite = modbusSiteMapper.selectOne(lqw1);
        modbusLog.setDatatype(modbusSite.getDatatype());
        modbusLog.setModbus(modbusSite.getRtu());
        modbusLog.setOperate("修改"+modbusSite.getBz()+"为"+data);


        LambdaUpdateWrapper<ModbusSite> uw=new LambdaUpdateWrapper<>();
        uw.eq(ModbusSite::getParamName,param);
        ModbusSite modbusSite1=new ModbusSite();
        modbusSite1.setValue(data);
        modbusSiteMapper.update(modbusSite1,uw);


//        将字节数组转换为string
        StringBuilder sb=new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase() + " ");
//            System.out.print(hex.toUpperCase() + " ");
        }
        modbusLog.setUpdateValue(data);
        modbusLog.setUserName(userName);
        modbusLog.setJh(jh);
        modbusLogMapper.insert(modbusLog);
        return null;
    }
}
