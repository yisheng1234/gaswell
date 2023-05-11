//package com.gaswell.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.read.builder.ExcelReaderBuilder;
//import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.config.listener.SsscsjListener;
//import com.gaswell.pojo.Ssscsj;
//import com.gaswell.service.SsscsjService;
//import com.gaswell.vo.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
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
// * @Date: 2021/12/09/ 17:30
// * @Blog leiwang.xyz
// * @Email ileiwang@live.com
// */
//@Api(tags="实时生产数据")
//@RestController
//@RequestMapping("/ssscsj")
//public class SsscsjController {
//    @Autowired
//    private SsscsjService ssscsjService;
//    @Autowired
//    private SsscsjListener ssscsjListener;
//
//    @PostMapping("addOne")
//    @ApiOperation(value = "添加一条数据")
//    @LogAnnotation(module="实时生产数据",operator="添加一条数据")
//    public Result insertOne(String userName,@RequestBody Ssscsj ssscsj){
//        return ssscsjService.insertOne(ssscsj);
//    }
//
//    @PostMapping("addByExcel")
//    @ApiOperation(value = "excel表导入到数据库")
//    @LogAnnotation(module="实时生产数据",operator="excel表导入到数据库")
//    public Result insertByExcel(String userName,@RequestParam("excelFile") MultipartFile file) throws IOException {
//        InputStream inputStream = file.getInputStream();
//        ExcelReaderBuilder read = EasyExcel.read(inputStream, Ssscsj.class,ssscsjListener);
//        ExcelReaderSheetBuilder sheet = read.sheet();
//        sheet.doRead();
//        return Result.success(null);
//    }
//    @PostMapping("updataOne")
//    @ApiOperation(value = "修改一条数据")
//    @LogAnnotation(module="实时生产数据",operator="修改一条数据")
//    public Result updateOne(String userName,@RequestBody Ssscsj ssscsj){
//        return ssscsjService.updateOne(ssscsj);
//    }
//
//    @PostMapping("selectByProperties")
//    @ApiOperation(value = "按条件查询（分页）")
//    @LogAnnotation(module="实时生产数据",operator="按条件进行查询")
//    public Result selectList(String userName,int current,int size,@RequestBody Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        return ssscsjService.selectProperties(current,size,ssscsj);
//    }
////    @PostMapping("selectByPropertiesPage/{current}/{size}")
////    @ApiOperation(value = "按条件分页查询")
////    @LogAnnotation(module="实时生产数据",operator="按条分页查询")
////    public Result selectByPropertiesPage(Integer uid,String userName,@PathVariable Integer current, @PathVariable Integer size,@RequestBody Ssscsj ssscsj) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
////        IPage<Ssscsj> page = ssscsjService.selectByPropertiesPage(current,size,ssscsj);
////        if(page.getPages() < current)
////            page = ssscsjService.selectByPropertiesPage((int)page.getPages(),size,ssscsj);
////        return Result.success(page);
////    }
//
//    @PostMapping("selectAll")
//    @ApiOperation(value = "查询所有数据（分页）")
//    @LogAnnotation(module="实时生产数据",operator="查询所有数据")
//    public Result selectAll(String userName,int current,int size){
//        return ssscsjService.selectAll(current,size);
//    }
//
//    @GetMapping("outExcel")
//    @ApiOperation(value = "导出为excel")
//    @LogAnnotation(module="实时生产数据",operator="导出为excel")
//    public void outExcel(String userName,HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName= URLEncoder.encode("实时生产数据","UTF-8");
//        response.setHeader("Content-Disposition","attachment;filename*=UTF-8''"+fileName+".xlsx");
//        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), Ssscsj.class);
//        ExcelWriterSheetBuilder sheet=workbook.sheet();
//        Result result = ssscsjService.selectAll(-1,-1);
//        sheet.doWrite((List)result.getData());
//
//
//
//    }
//
//}
