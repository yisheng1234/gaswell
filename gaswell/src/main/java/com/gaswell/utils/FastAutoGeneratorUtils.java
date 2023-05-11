package com.gaswell.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author Lei Wang
 * @Date: 2022/05/23/ 20:54
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class FastAutoGeneratorUtils {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://101.35.119.91:3306/gaswell_oracle?characterEncoding=utf-8&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true", "root", "upc616518database")
                .globalConfig(builder -> {
                    builder.author("Lei Wang") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://fag"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.gaswell") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://fag")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("qaa01","qaa02","qaa04","qaa091","qaa10","qab04","qba01",
                            "qba02","qbb01","qca02","qca03","qca05","qdb02","qfc01","qfc02"); // 设置需要生成的表名
                            //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
