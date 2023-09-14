package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.EquipMent;
import com.gaswell.service.EquitMentService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "设备")
@RequestMapping("/equipment")
public class EquipMentController {
    @Autowired
    private EquitMentService equitMentService;
    @PostMapping("/insert")
    @ApiOperation("添加数据")
    @LogAnnotation(module = "设备",operator = "添加数据")
    public Result insertData(String userName,  @RequestBody EquipMent equipMent){
        return equitMentService.insert(equipMent);
    }
    @PostMapping("/delete")
    @ApiOperation("删除数据")
    @LogAnnotation(module = "设备",operator = "删除数据")
    public Result insertData(String userName, int id){
        return equitMentService.delete(id);
    }
    @PostMapping("/update")
    @ApiOperation("修改数据")
    @LogAnnotation(module = "设备",operator = "修改数据")
    public Result updateData(String userName,  @RequestBody EquipMent equipMent){
        return equitMentService.update(equipMent);
    }
    @PostMapping("/select")
    @ApiOperation("查询数据")
    @LogAnnotation(module = "设备",operator = "查询数据")
    public Result updateData(String userName,int size,int current, @RequestBody EquipMent equipMent){
        return equitMentService.select(size,current,equipMent);
    }
}
