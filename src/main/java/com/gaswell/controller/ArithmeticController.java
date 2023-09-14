package com.gaswell.controller;

import com.gaswell.common.log.LogAnnotation;
import com.gaswell.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:03
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */

// 压裂项目算法模块，供参考
@Slf4j
@RestController
@RequestMapping("/algorithms")
@Api(tags = "算法模块")
public class ArithmeticController {

    @GetMapping("/trainCNYC")
    @ApiOperation("训练产能预测模型")
    @LogAnnotation(module = "算法模块", operator = "训练产能预测模型")
    public Result trainCNYC(@RequestParam("params") String params) throws IOException {

        Socket socket = new Socket("127.0.0.1", 16666);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.print(params);
//        log.info(params);

        String str = br.readLine();

        return Result.success(str);
    }

    @GetMapping("/predictCNYC")
    @ApiOperation("通过产能预测模型进行产能预测")
    @LogAnnotation(module = "算法模块", operator = "通过产能预测模型进行产能预测")
    public Result predictCNYC(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);

        String str= br.readLine();
        StringBuilder sb=new StringBuilder(str);
        while (str!=null){
            str= br.readLine();
            sb.append(str);
        }
        return Result.success(sb.toString());
    }


    @GetMapping("/trainGKZD")
    @ApiOperation("训练工况诊断的模型")
    @LogAnnotation(module = "算法模块", operator = "训练工况诊断的模型")
    public Result trainGKZD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.print(params);
        log.info(params);

        String str = br.readLine();

        return Result.success(str);
    }



    @GetMapping("/predictGKZD")
    @ApiOperation("通过工况诊断模型进行工况诊断")
    @LogAnnotation(module = "算法模块", operator = "通过工况诊断模型进行工况诊断")
    public Result predictGKZD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.print(params);
        log.info(params);

        String str = br.readLine();

        return Result.success(str);
    }

    @GetMapping("/chooseParamCNYC")
    @ApiOperation("产能预测模型参数选择")
    @LogAnnotation(module = "算法模块", operator = "产能预测模型参数选择")
    public Result chooseParamCNYC(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.print(params);
        log.info(params);

        String str = br.readLine();

        return Result.success(str);
    }

    @GetMapping("/chooseParamGKZD")
    @ApiOperation("工况诊断模型参数选择")
    @LogAnnotation(module = "算法模块", operator = "工况诊断模型参数选择")
    public Result chooseParamGKZD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.print(params);
        log.info(params);

        String str = br.readLine();

        return Result.success(str);
    }


    // 0419新增
    // ******************************************模型训练*********************************************
    @GetMapping("/trainSFJY")
    @ApiOperation("训练状态数据预测是否积液模型")
    @LogAnnotation(module = "算法模块", operator = "训练状态数据预测是否积液模型")
    public Result trainSFJY(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainSFDD")
    @ApiOperation("训练状态数据预测是否冻堵模型")
    @LogAnnotation(module = "算法模块", operator = "训练状态数据预测是否冻堵模型")
    public Result trainSFDD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainSFJK")
    @ApiOperation("训练状态数据预测间开模型")
    @LogAnnotation(module = "算法模块", operator = "训练状态数据预测间开模型")
    public Result trainSFJK(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainPPYC")
    @ApiOperation("训练泡排预测模型")
    @LogAnnotation(module = "算法模块", operator = "训练泡排预测模型")
    public Result trainPPYC(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainJCYC")
    @ApiOperation("训练甲醇预测模型")
    @LogAnnotation(module = "算法模块", operator = "训练甲醇预测模型")
    public Result trainJCYC(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainGLDFX")
    @ApiOperation("状态数据与XX关联度分析")
    @LogAnnotation(module = "算法模块", operator = "状态数据与XX关联度分析")
    public Result trainGLDFX(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainContinueOneMonthAnyNumDaysProduct")
    @ApiOperation("训练状态数据与连续一个月任意天数产量模型")
    @LogAnnotation(module = "算法模块", operator = "训练状态数据与连续一个月任意天数产量模型")
    public Result trainContinueOneMonthAnyNumDaysProduct(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainSameDataDifferentPPJJZLNextDayProduct")
    @ApiOperation("训练 相同生产状态数据时，不同泡排剂加注量，对应的明天产量")
    @LogAnnotation(module = "算法模块", operator = "训练 相同生产状态数据时，不同泡排剂加注量，对应的明天产量")
    public Result trainSameDataDifferentPPJJZLNextDayProduct(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/trainSameDataDifferentJCJZLNextDayProduct")
    @ApiOperation("训练 相同生产状态数据时，不同甲醇加注量，对应的明天产量")
    @LogAnnotation(module = "算法模块", operator = "训练 相同生产状态数据时，不同甲醇加注量，对应的明天产量")
    public Result trainSameDataDifferentJCJZLNextDayProduct(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    // ******************************************模型预测*********************************************
    @GetMapping("/predictSFJY")
    @ApiOperation("根据 状态数据 预测 是否积液")
    @LogAnnotation(module = "算法模块", operator = "根据状态数据预测是否积液")
    public Result predictSFJY(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictSFDD")
    @ApiOperation("根据 状态数据 预测 是否冻堵")
    @LogAnnotation(module = "算法模块", operator = "根据状态数据预测是否冻堵")
    public Result predictSFDD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictSFJK")
    @ApiOperation("根据 状态数据 预测 间开模型")
    @LogAnnotation(module = "算法模块", operator = "根据状态数据预测间开模型")
    public Result predictSFJK(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictPPJL")
    @ApiOperation("根据 状态数据 预测 泡排剂量")
    @LogAnnotation(module = "算法模块", operator = "根据状态数据预测泡排剂量")
    public Result predictPPJL(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictJCJL")
    @ApiOperation("根据 状态数据 预测 甲醇剂量")
    @LogAnnotation(module = "算法模块", operator = "根据状态数据预测甲醇剂量")
    public Result predictJCJL(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }


    @GetMapping("/predictFutureCQLAndCSL")
    @ApiOperation("预测未来一段时间的产气量、产水量")
    @LogAnnotation(module = "算法模块", operator = "预测未来一段时间的产气量、产水量")
    public Result predictFutureCQLAndCSL(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictFutureYYAndTY")
    @ApiOperation("预测未来一段时间的油压、套压")
    @LogAnnotation(module = "算法模块", operator = "预测未来一段时间的油压、套压")
    public Result predictFutureYYAndTY(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }


    @GetMapping("/predictFutureSFJYAndSFDD")
    @ApiOperation("预测未来一段时间的是否积液、是否冻堵")
    @LogAnnotation(module = "算法模块", operator = "预测未来一段时间的是否积液、是否冻堵")
    public Result predictFutureSFJYAndSFDD(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/returnAllPredictResult")
    @ApiOperation("返回整体预测结果")
    @LogAnnotation(module = "算法模块", operator = "返回整体预测结果")
    public Result returnAllPredictResult(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }


    @GetMapping("/predictSameDataDifferentPPJJZLNextDayProduct")
    @ApiOperation("根据 相同生产状态数据时，不同泡排剂加注量，对应的明天产量")
    @LogAnnotation(module = "算法模块", operator = "根据 相同生产状态数据时，不同泡排剂加注量，对应的明天产量")
    public Result predictSameDataDifferentPPJJZLNextDayProduct(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/predictSameDataDifferentJCJZLNextDayProduct")
    @ApiOperation("根据 相同生产状态数据时，不同甲醇加注量，对应的明天产量")
    @LogAnnotation(module = "算法模块", operator = "根据 相同生产状态数据时，不同甲醇加注量，对应的明天产量")
    public Result predictSameDataDifferentJCJZLNextDayProduct(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }


    @GetMapping("/showModelNameList")
    @ApiOperation("查看本地所有模型名称")
    @LogAnnotation(module = "算法模块", operator = "查看本地所有模型名称")
    public Result showModelNameList(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }

    @GetMapping("/selectAlgorithmBackupTable")
    @ApiOperation("查询算法备份表")
    @LogAnnotation(module = "算法模块", operator = "查询算法备份表")
    public Result selectAlgorithmBackupTable(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16666);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }
    @GetMapping("/predictFutureWorkingConditions")
    @ApiOperation("预测未来工况")
    @LogAnnotation(module = "算法模块", operator = "预测未来工况")
    public Result predictFutureWorkingConditions(@RequestParam("params") String params) throws IOException {
        Socket socket = new Socket("127.0.0.1", 16868);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.print(params);
        log.info(params);
        String str = br.readLine();
        return Result.success(str);
    }
}
