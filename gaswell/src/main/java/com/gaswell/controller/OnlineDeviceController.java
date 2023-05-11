package com.gaswell.controller;

import com.gaswell.utils.CacheLoader;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2022/01/15/ 12:23
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "在线设备管理")
public class OnlineDeviceController {
    // 获取在线设备列表
    @GetMapping("/device")
    public Object api() {
        Map<String, Object> map = new HashMap<>();
        map.put("channelGroup", CacheLoader.channelGroup);
        map.put("channelMapCS", CacheLoader.channelMapCS.values());
        map.put("channelMapSC", CacheLoader.channelMapSC.keySet());
        map.put("deviceIdAndIp", CacheLoader.deviceIdAndIp);
        map.put("channelGroup.size", CacheLoader.channelGroup.size());
        map.put("channelGroup.name", CacheLoader.channelGroup.name());
        map.put("channelGroup.isEmpty", CacheLoader.channelGroup.isEmpty());
        map.put("channelGroup.toString", CacheLoader.channelGroup.toString());
        return map;
    }
}
