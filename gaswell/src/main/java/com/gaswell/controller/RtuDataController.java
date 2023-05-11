package com.gaswell.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.config.listener.RtuDataListener;
import com.gaswell.mapper.RtuDataMapper;
import com.gaswell.pojo.RtuData;
import com.gaswell.service.RtuDataService;
import com.gaswell.service.UserService;
import com.gaswell.vo.Result;
import com.gaswell.vo.RtuDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "RTU实时生产数据")
@RestController
@RequestMapping("/rtudata")
public class RtuDataController {

    @Autowired
    private RtuDataService rtuDataService;

    @Autowired
    private RtuDataListener rtuDataListener;
    @Autowired
    private RtuDataMapper rtuDataMapper;
    @Autowired
    private UserService userService;

    //    @PostMapping("selectByProperties")
//    @ApiOperation(value = "按条件查询（分页）")
//    @LogAnnotation(module="RTU实时生产数据",operator="按条件查询")
//    public Result selectList(String userName, int current, int size, @RequestBody RtuDataParams rtuDataParams) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        return rtuDataService.selectProperties(current,size,rtuDataParams);
//    }
    @PostMapping("selectByOptions")
    @ApiOperation("按条件查询")
    @LogAnnotation(module = "RTU实时生产数据", operator = "按条件查询")
    public Result selectByOptions(String userName, @RequestBody RtuData rtuData) {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return rtuDataService.selectByOptions(rtuData,department);
    }
    @PostMapping("selectByMutiOptions")
    @ApiOperation("按条件查询(多选)")
    @LogAnnotation(module = "RTU实时生产数据", operator = "按条件查询（多选）")
    public Result selectByMutiOptions(String userName, @RequestBody RtuDataVo rtuDataVo,String date,int sort) {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return rtuDataService.selectByMutiOptions(rtuDataVo,date,sort,department);
    }
    @PostMapping("latestdata")
    @ApiOperation("读取所有井最新数据")
    @LogAnnotation(module = "RTU实时生产数据", operator = "读取所有井最新数据")
    public Result findLatestData(String userName) {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return rtuDataService.findLatestData(department);
    }

    @PostMapping("latestdata2")
    @ApiOperation("组态图读取所有井最新数据")
    @LogAnnotation(module = "RTU实时生产数据", operator = "读取所有井最新数据")
    public Result findLatestData2(String userName) {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return rtuDataService.findLatestData2(department);
    }

    @PostMapping("selectAll")
    @ApiOperation("查询全部数据")
    @LogAnnotation(module = "RTU实时生产数据", operator = "查询全部数据")
    public Result selectAll(String userName) {
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return rtuDataService.selectAll(department);
    }

    @PostMapping("selectData")
    @ApiOperation("查询某个时间范围的数据(油压，套压，井口温度)")
    @LogAnnotation(module = "RTU实时生产数据", operator = "查询某个时间范围数据(油压，套压，井口温度)")
    public Result selectData(String userName, String properties, String ywbh, String date) {
        return rtuDataService.selectData(ywbh, date, properties);
    }


    @PostMapping("addByExcel")
    @ApiOperation(value = "excel表导入到数据库")
    @LogAnnotation(module = "rtu实时数据", operator = "excel表导入到数据库")
    public Result insertByExcel(String userName, @RequestParam("excelFile") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReaderBuilder read = EasyExcel.read(inputStream, RtuData.class, rtuDataListener);
        ExcelReaderSheetBuilder sheet = read.sheet();
        sheet.doRead();
        return Result.success(null);
    }

    @GetMapping("outExcel")
    @ApiOperation(value = "导出为excel")
    @LogAnnotation(module = "实时数据", operator = "导出为excel")
    public void outExcel(String userName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("实时生产数据", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), RtuData.class);
        ExcelWriterSheetBuilder sheet = workbook.sheet();
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        List<RtuData> list = rtuDataService.selectList(department);
        sheet.doWrite(list);
    }
}
