package com.gaswell.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.config.listener.RscsjListener;
import com.gaswell.pojo.Rscsj;
import com.gaswell.service.RscsjService;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags="日生产数据")
@RestController
@RequestMapping("/rscsj")
public class RscsjController {
    @Autowired
    private RscsjService rscsjService;
    @Autowired
    private RscsjListener rscsjListener;
    @PostMapping("addOne")
    @ApiOperation(value = "添加一条数据")
    @LogAnnotation(module="日生产数据",operator="添加一条数据")
    public Result insertOne(String userName, @RequestBody Rscsj rscsj,String ywjh){
        return rscsjService.insertOne(rscsj,ywjh);
    }
    @PostMapping("addByExcel")
    @ApiOperation(value = "excel表导入到数据库")
    @LogAnnotation(module="日生产数据",operator="excel表导入到数据库")
    public Result insertByExcel(String userName,@RequestParam("excelFile") MultipartFile file,String ywjh) throws IOException {
        InputStream inputStream = file.getInputStream();
        DynamicTableNameThreadLocal.put(ywjh);
        ExcelReaderBuilder read = EasyExcel.read(inputStream, Rscsj.class,rscsjListener);
        ExcelReaderSheetBuilder sheet = read.sheet();
        sheet.doRead();
        return Result.success(null);
    }

    @PostMapping("updataOne")
    @ApiOperation(value = "修改一条数据")
    @LogAnnotation(module="日生产数据",operator="修改一条数据")
    public Result updateOne(String userName,@RequestBody Rscsj rscsj,String ywjh){
        return rscsjService.updateOne(rscsj,ywjh);
    }

    @PostMapping("selectByProperties")
    @ApiOperation(value = "按条件查询（分页）")
    @LogAnnotation(module="日生产数据",operator="按条件查询")
    public Result selectList(String userName,int current,int size,@RequestBody Rscsj rscsj,String ywjh) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return rscsjService.selectProperties(current,size,rscsj,ywjh);
    }

    @PostMapping("selectAll")
    @ApiOperation(value = "查询所有数据（分页）")
    @LogAnnotation(module="日生产数据",operator="查询所有数据")
    public Result selectAll(String userName, int current,int size,String ywjh){
        return rscsjService.selectAll(current,size,ywjh);
    }

    @GetMapping("outExcel")
    @ApiOperation(value = "导出为excel")
    @LogAnnotation(module="日生产数据",operator="导出为excel")
    public void outExcel(String userName,HttpServletResponse response,String ywjh) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName= URLEncoder.encode("日生产数据","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename*=UTF-8''"+fileName+".xlsx");
        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), Rscsj.class);
        ExcelWriterSheetBuilder sheet=workbook.sheet();
        Result result =rscsjService.selectAll(-1,-1,ywjh);
        List list=(List)result.getData();
        sheet.doWrite(list);
    }




    @PostMapping("showdata")
    @ApiOperation(value="数据展示")
    @LogAnnotation(module = "日生产数据",operator = "数据展示")
    public Result showdata(String userName,String ywjh,String date,String properties){
        return rscsjService.showdata(ywjh,date,properties);
    }
    @PostMapping("getColumnNameAndComment")
    @ApiOperation("获取字段和备注")
    public Result getColumnNameAndComment(String ywjh){
        return rscsjService.getColumnNameAndComment(ywjh);
    }
}
