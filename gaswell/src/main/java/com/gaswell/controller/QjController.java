package com.gaswell.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.config.listener.QjListener;
import com.gaswell.pojo.Qj;
import com.gaswell.service.QjService;
import com.gaswell.service.UserService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/qj")
@Api(tags = "气井")
public class QjController {
    @Autowired
    private QjService qjService;
    @Autowired
    private QjListener qjListener;
    @Autowired
    private UserService userService;
    @PostMapping("addOne")
    @ApiOperation(value="添加一条数据")
    @LogAnnotation(module = "气井",operator = "添加一条数据")
    public Result insertOne(String userName , @RequestBody Qj qj){
        return qjService.insertOne(qj);
    }

    @PostMapping("delete")
    @ApiOperation(value="删除一条数据")
    @LogAnnotation(module = "气井",operator = "删除一条数据")
    public Result deleteOne(String userName,String ywbh){
        return qjService.deleteOne(ywbh);
    }
    @PostMapping("update")
    @ApiOperation(value = "修改气井信息")
    @LogAnnotation(module = "气井", operator = "修改气井信息")
    public Result updateInfo(String userName,@RequestBody Qj qj){
        return qjService.updateOne(qj);
    }
    @PostMapping("findAll")
    @ApiOperation("查询所有信息")
    @LogAnnotation(module = "气井",operator = "查询所有气井")
    public Result findAll(String userName,int current,int size){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return qjService.findAll(current,size,department);
    }
    @PostMapping("findByProperties")
    @ApiOperation(value = "按条件查询")
    @LogAnnotation(module = "气井",operator = "按条件查询气井")
    public Result selectList(String userName,int current,int size,@RequestBody Qj qj){
        return qjService.selectByProperties(current,size,qj);
    }
    @PostMapping("addByExcel")
    @ApiOperation(value = "excel表导入到数据库")
    @LogAnnotation(module = "气井",operator = "excel表导入到数据库")
    public Result insertByExcel(String userName, @RequestParam("excelFile")MultipartFile file) throws IOException {
        InputStream in=file.getInputStream();
        ExcelReaderBuilder read= EasyExcel.read(in,Qj.class,qjListener);
        ExcelReaderSheetBuilder sheet= read.sheet();
        sheet.doRead();
        return Result.success(null);
    }
    @GetMapping("outExcel")
    @ApiOperation(value = "导出为excel")

    public void outExcel(String userName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName= URLEncoder.encode("气井","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename*=UTF-8''"+fileName+".xlsx");
        ExcelWriterBuilder workbook = EasyExcel.write(response.getOutputStream(), Qj.class);
        ExcelWriterSheetBuilder sheet=workbook.sheet();
        sheet.doWrite(qjService.selectAll());

    }
    @PostMapping("readJhFromOracle")
    @ApiOperation("从oracle数据库读取井号信息")
    @LogAnnotation(module = "气井",operator = "从oracle数据库读取井号信息")
    public Result readJhFromOracle(String userName){
        return qjService.readJhFromOracle();
    }
    @PostMapping("findYm")
    @ApiOperation("查询所有域名")
    @LogAnnotation(module = "气井",operator = "查询所有域名")
    public Result findYm(String userName){
        return qjService.findYm();
    }
    @PostMapping("findJh")
    @ApiOperation("查询所有井号")
    @LogAnnotation(module = "气井",operator = "查询所有井号")
    public Result findJh(String userName){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return qjService.findJh(department);
    }
    @PostMapping("findZm")
    @ApiOperation("查询所有站名")
    @LogAnnotation(module = "气井",operator = "查询所有站名")
    public Result findZm(String userName){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return qjService.findZm(department);
    }
    @PostMapping("findScq")
    @ApiOperation("查询所有生产区")
    @LogAnnotation(module = "气井",operator = "查询所有生产区")
    public Result findScq(String userName){
        int department=userService.selectUserByuserName(userName).getUser_department_id();
        return qjService.findScq(department);
    }
    @PostMapping("findQmByYm")
    @ApiOperation("根据域名查询区名")
    @LogAnnotation(module = "气井",operator = "根据域名查询区名")
    public Result findQmByYm(String userName,String ym){
        return qjService.findQMByYM(ym);
    }
    @PostMapping("findZmByQm")
    @ApiOperation("根据区名查询站名")
    @LogAnnotation(module = "气井",operator = "根据区名查询站名")
    public Result findZmByQm(String userName,String qm){
        return qjService.findZmByQm(qm);
    }
    @PostMapping("findJhByzm")
    @ApiOperation("根据站名查询井号")
    @LogAnnotation(module = "气井",operator = "根据站名查询井号")
    public Result findJhByzm(String userName,String zm){
        return qjService.findJhByzm(zm);
    }
}
