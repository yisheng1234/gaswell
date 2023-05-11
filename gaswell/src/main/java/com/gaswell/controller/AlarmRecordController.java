package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.AlarmRecord;
import com.gaswell.service.AlarmRecordService;
import com.gaswell.service.UserService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarmRecord")
@Api(tags = "报警记录")
public class AlarmRecordController {
    @Autowired
    private AlarmRecordService alarmRecordService;
    @Autowired
    private UserService userService;
    @PostMapping("selectAll")
    @ApiOperation("查询所有数据")
    @LogAnnotation(module = "报警记录",operator = "查询所有数据")
    public Result selectAll(String userName){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return alarmRecordService.selectAll(department);
    }
    @PostMapping("updateOne")
    @ApiOperation("更新数据")
    @LogAnnotation(module = "报警记录",operator = "查询所有数据")
    public Result updateOne(String userName, @RequestBody AlarmRecord alarmRecord){
        return alarmRecordService.updateOne(alarmRecord);
    }
}
