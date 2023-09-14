package com.gaswell.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.Params;
import com.gaswell.service.ParamsService;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lei Wang
 * @Date: 2022/04/17/ 17:55
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@RestController
@RequestMapping("/params")
@Api(tags = "参数配置管理_1")
public class ParamsController {

    @Autowired
    private ParamsService paramsService;

    @GetMapping("selectByPage")
    @ApiOperation(value = "分页查询所有参数配置")
    @LogAnnotation(module = "参数配置管理", operator = "分页查询所有参数配置")
    public Result selectByPage(String userName, int current, int size) {
        Page<Params> page = new Page<>(current, size);
        paramsService.page(page);
        return new Result(true,200,"success",page.getRecords(),(int)page.getTotal(),(int)page.getPages());

    }

    @GetMapping("selectAll")
    @ApiOperation(value = "查询所有参数配置")
    @LogAnnotation(module = "参数配置管理", operator = "查询所有参数配置")
    public Result selectAll(String userName) {
        List<Params> list = paramsService.list();
        return Result.success(list);
    }

    @GetMapping("selectOne")
    @ApiOperation(value = "按条件查询一条参数配置")
    @LogAnnotation(module = "参数配置管理", operator = "按条件查询一条参数配置")
    public Result selectOne(String userName, @RequestBody Params params) {
        Params params1 = paramsService.selectOne(params);
        return Result.success(params1);
    }

    @GetMapping("selectList")
    @ApiOperation(value = "按条件查询多条参数配置")
    @LogAnnotation(module = "参数配置管理", operator = "按条件查询多条参数配置")
    public Result selectList(String userName, @RequestBody Params params) {
        List<Params> params1 = paramsService.selectList(params);
        return Result.success(params1);
    }

    // 添加
    @PostMapping("addOne")
    @ApiOperation(value = "增加参数")
    @LogAnnotation(module = "参数配置管理", operator = "增加参数")
    public Result addOne(String userName, @RequestBody Params params) {
        return Result.success(paramsService.insertOne(params));
    }

    // 删除
    @DeleteMapping("delete")
    @ApiOperation(value = "删除参数")
    @LogAnnotation(module = "参数配置管理", operator = "删除参数")
    public Result delete(String userName, @RequestBody Params params) {
        return Result.success(paramsService.delete(params));
    }

    // 修改
    @PostMapping("updata")
    @ApiOperation(value = "修改参数")
    @LogAnnotation(module = "参数配置管理", operator = "修改参数")
    public Result update(String userName, @RequestBody Params params) {
        return  paramsService.updateOne(params,userName);
    }
}
