//package com.gaswell.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.read.builder.ExcelReaderBuilder;
//import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterBuilder;
//import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
//import com.gaswell.common.log.LogAnnotation;
//import com.gaswell.config.listener.RtuDataAiListener;
//import com.gaswell.pojo.RtuDataAi;
//import com.gaswell.service.RtuDataAiService;
//import com.gaswell.vo.Result;
//import com.gaswell.vo.params.RtuDataAiParams;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.util.List;
//
//
//@Api(tags = "RTU实时生产数据+诊断结果")
//@RestController
//@RequestMapping("/rtudataai")
//public class RtuDataAiController {
//    @Autowired
//    private RtuDataAiService rtuDataAiService;
//
//    @Autowired
//    private RtuDataAiListener rtuDataAiListener;
//
//    @PostMapping("selectByProperties")
//    @ApiOperation(value = "按条件查询")
//    @LogAnnotation(module = "实时生产数据+诊断结果", operator = "按条件查询")
//    public Result selectList(String userName, int current, int size, @RequestBody RtuDataAiParams rtuDataAiParams) {
//        return rtuDataAiService.selectProperties(current, size, rtuDataAiParams);
//    }
//
//    @PostMapping("addByExcel")
//    @ApiOperation(value = "excel表导入到数据库RtuDataAi表")
//    @LogAnnotation(module = "实时生产数据+诊断结果", operator = "excel表导入到数据库RtuDataAi表")
//    public Result insertByExcel(String userName, @RequestParam("file") MultipartFile file) throws IOException {
//        InputStream inputStream = file.getInputStream();
//        ExcelReaderBuilder read = EasyExcel.read(inputStream, RtuDataAi.class, rtuDataAiListener);
//        ExcelReaderSheetBuilder sheet = read.sheet();
//        sheet.doRead();
//        return Result.success(null);
//    }
//
//    @GetMapping("outExcel")
//    @ApiOperation(value = "导出为excel")
//    @LogAnnotation(module = "实时生产数据+诊断结果", operator = "导出为excel")
//    public void outExcel(String userName, HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode("实时生产数据+诊断结果", "UTF-8");
//        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName + ".xlsx");
//        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), RtuDataAi.class);
//        ExcelWriterSheetBuilder sheet = workbook.sheet();
//        Result result = Result.success(rtuDataAiService.list());
//        sheet.doWrite((List) result.getData());
//    }
//
//    @PostMapping("delete")
//    @ApiOperation(value = "按条件删除")
//    @LogAnnotation(module = "实时生产数据+诊断结果", operator = "按条件删除")
//    public Result delete(String userName, @RequestBody RtuDataAiParams rtuDataAiParams) {
//        return rtuDataAiService.delete(rtuDataAiParams);
//    }
//
//    @PostMapping("update")
//    @ApiOperation(value = "按条件更新")
//    @LogAnnotation(module = "实时生产数据+诊断结果", operator = "按条件更新")
//    public Result update(String userName, @RequestBody RtuDataAi rtuDataAi) {
//        return rtuDataAiService.update(rtuDataAi);
//    }
//}
