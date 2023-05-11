package com.gaswell.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.SystemLog;
import com.gaswell.service.SystemLogService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2021/12/09/ 17:07
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Api(tags = "日志")
@RestController
@RequestMapping("/log")
public class SystemLogController {
    @Autowired
    private SystemLogService systemLogService;

    public void insertOne(SystemLog systemLog){
        systemLogService.insertOne(systemLog);
    }

    @PostMapping("deleteOne")
    @ApiOperation("删除一条日志")
    @LogAnnotation(module = "日志",operator = "删除一条日志")
    public Result deleteOne(String userName,@RequestBody SystemLog systemLog){
        return systemLogService.deleteOne(systemLog);
    }

    @PostMapping("selectAll")
    @ApiOperation("查询全部日志（分页）")
    @LogAnnotation(module = "日志",operator = "查询全部日志(分页)")
    public Result selectAll(String userName,int current,int size){
        return systemLogService.selectAll(current,size);
    }

    @PostMapping("selectByProperties")
    @ApiOperation("按条件查询日志")
    @LogAnnotation(module = "日志",operator = "按条件查询日志")
    public Result selectByProperties(String userName, @RequestBody SystemLog systemLog,int current,int size) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return Result.success(systemLogService.selectByProperties(systemLog,current,size));
    }


    @PutMapping("updataOne")
    @ApiOperation("修改一条日志")
    @LogAnnotation(module = "日志",operator = "修改一条日志")
    public Result updataOne(String userName, @RequestBody SystemLog systemLog){
        return Result.success(systemLogService.updateById(systemLog));
    }

    @GetMapping("outExcel")
    @ApiOperation(value = "导出为excel")
    @LogAnnotation(module="日志",operator="导出为excel")
    public void outExcel(String userName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName= URLEncoder.encode("日志","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename*=UTF-8''"+fileName+".xlsx");
        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), SystemLog.class);
        ExcelWriterSheetBuilder sheet=workbook.sheet();
        Result result = systemLogService.selectAll(-1,-1);
        sheet.doWrite((List)result.getData());
    }

    @PostMapping("deleteBatch")
    @ApiOperation("删除批量日志")
    @LogAnnotation(module = "日志",operator = "删除批量日志")
    public Result deleteOne(String userName,@RequestBody List<Integer> systemLogs){
        return systemLogService.deleteBatchById(systemLogs);
    }

}
