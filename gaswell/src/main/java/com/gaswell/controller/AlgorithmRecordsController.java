package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.pojo.AlgorithmRecords;
import com.gaswell.service.AlgorithmRecordsService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.AlgorithmRecordsParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/algorithmrecords")
@Api(tags = "算法执行记录")
public class AlgorithmRecordsController {
    @Autowired
    private AlgorithmRecordsService algorithmRecordsService;
    @PostMapping("selectByProperties")
    @ApiOperation("按条件查询")
    @LogAnnotation(module = "算法执行记录", operator = "按条件查询")
    public Result selectByProperties(String userName,@RequestBody AlgorithmRecords algorithmRecords){
        return algorithmRecordsService.selectByProperties(algorithmRecords);
    }

    @PostMapping("deleteByProperties")
    @ApiOperation("按条件删除")
    @LogAnnotation(module = "算法执行记录", operator = "按条件删除")
    public Result deleteByProperties(String userName,@RequestBody AlgorithmRecordsParams algorithmRecordsParams){
        return algorithmRecordsService.deleteByProperties(algorithmRecordsParams);
    }

    @PostMapping("add")
    @ApiOperation("添加")
    @LogAnnotation(module = "算法执行记录", operator = "添加")
    public Result add(String userName,@RequestBody AlgorithmRecords algorithmRecords){
        boolean res = algorithmRecordsService.save(algorithmRecords);
        if (res){
            return Result.success("添加成功");
        }
        else{
            return Result.fail(400, "添加失败");
        }
    }

    @PostMapping("update")
    @ApiOperation("修改")
    @LogAnnotation(module = "算法执行记录", operator = "修改")
    public Result update(String userName,@RequestBody AlgorithmRecords algorithmRecords){
        boolean res = algorithmRecordsService.updateById(algorithmRecords);
        if (res){
            return Result.success("修改成功");
        }
        else{
            return Result.fail(400, "修改失败");
        }
    }

    @GetMapping("selectByType")
    @ApiOperation("按类型查询测试集最高得分算法记录")
    @LogAnnotation(module = "算法执行记录", operator = "按类型查询测试集最高得分算法记录")
    public Result selectByType(String userName,@RequestBody AlgorithmRecords algorithmRecords){
        return algorithmRecordsService.selectByTypeHightest(algorithmRecords);
    }
    @PostMapping("updateUsedAlgorithm")
    @ApiOperation("修改使用的算法")
    @LogAnnotation(module = "周期读取数据",operator = "修改使用的算法")
    public Result updateUsedAlgorithm(String algorithm_type,String algorithm_name){
        return algorithmRecordsService.updateUsedAlgorithm(algorithm_type,algorithm_name);
    }
}
