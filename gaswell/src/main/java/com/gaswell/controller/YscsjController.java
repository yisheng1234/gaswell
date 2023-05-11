//package com.gaswell.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.read.builder.ExcelReaderBuilder;
//import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.config.listener.YscsjListener;
//import com.gaswell.mapper.YscsjMapper;
//import com.gaswell.pojo.Yscsj;
//import com.gaswell.service.YscsjService;
//import com.gaswell.utils.DynamicTableNameThreadLocal;
//import com.gaswell.vo.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.net.URLEncoder;
//import java.util.List;
//
///**
// * @author Lei Wang
// * @Date: 2021/12/08/ 23:44
// * @Blog leiwang.xyz
// * @Email ileiwang@live.com
// */
//@Api(tags="月生产数据")
//@RestController
//@RequestMapping("/yscsj")
//@Slf4j
//public class YscsjController {
//
//    @Autowired
//    private YscsjService yscsjService;
//    @Autowired
//    private YscsjListener yscsjListener;
//    @Autowired
//    private YscsjMapper  yscsjMapper;
//
//    @PostMapping("addOne")
//    @ApiOperation(value = "添加一条数据")
//    @LogAnnotation(module="月生产数据",operator="添加一条数据")
//    public Result insertOne(String userName, @RequestBody Yscsj yscsj,String ywjh){
//        return yscsjService.insertOne(yscsj,ywjh);
//    }
//
//    @PostMapping("addByExcel")
//    @ApiOperation(value = "excel表导入到数据库")
//    @LogAnnotation(module="月生产数据",operator="excel表导入到数据库")
//    public Result insertByExcel(String userName,@RequestParam("excelFile") MultipartFile file,String ywjh) throws IOException {
//        InputStream inputStream = file.getInputStream();
//        DynamicTableNameThreadLocal.put(ywjh);
//        ExcelReaderBuilder read = EasyExcel.read(inputStream, Yscsj.class,yscsjListener);
//        ExcelReaderSheetBuilder sheet = read.sheet();
//        sheet.doRead();
//        return Result.success(null);
//    }
//
//    @PostMapping("updataOne")
//    @ApiOperation(value = "修改一条数据")
//    @LogAnnotation(module="月生产数据",operator="修改一条数据")
//    public Result updateOne(String userName,@RequestBody Yscsj yscsj,String ywjh){
//        return yscsjService.updateOne(yscsj,ywjh);
//    }
//
//    @PostMapping("selectByProperties")
//    @ApiOperation(value = "按条件查询(分页)")
//    @LogAnnotation(module="月生产数据",operator="按条件查询")
//    public Result selectList(String userName,int current,int size ,@RequestBody Yscsj yscsj,String ywjh) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        return yscsjService.selectProperties(current,size,yscsj,ywjh);
//    }
//
//    @PostMapping("selectAll")
//    @ApiOperation(value = "查询所有数据(分页)")
//    @LogAnnotation(module="月生产数据",operator="查询所有数据")
//    public Result selectAll(String userName,int current,int size,String ywjh){
//        return yscsjService.selectAll(current,size,ywjh);
//    }
//
//    @GetMapping("outExcel")
//    @ApiOperation(value = "导出为excel")
//    @LogAnnotation(module="月生产数据",operator="导出为excel")
//    public void outExcel(String userName,HttpServletResponse response,String ywjh) throws IOException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode("月生产数据", "UTF-8");
//        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
//        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), Yscsj.class);
//        ExcelWriterSheetBuilder sheet=workbook.sheet();
//        Result result =yscsjService.selectAll(-1,-1,ywjh);
//        sheet.doWrite((List)result.getData());
//    }
//    @PostMapping("showdata")
//    @ApiOperation(value="数据展示")
//    @LogAnnotation(module = "月生产数据",operator = "数据展示")
//    public Result showdata(String userName,String ywjh,String date,String properties){
//        return yscsjService.showdata(ywjh,date,properties);
//    }
//    @PostMapping("getColumnNameAndComment")
//    @ApiOperation("获取字段和备注")
//    public Result getColumnNameAndComment(String ywjh){
//        return yscsjService.getColumnNameAndComment(ywjh);
//    }
//}
