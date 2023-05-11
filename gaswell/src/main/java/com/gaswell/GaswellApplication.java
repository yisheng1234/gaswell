package com.gaswell;

import com.gaswell.initializer.NettyServer;
import com.gaswell.initializer.UDPNettyServer;
import com.gaswell.service.StorageService;
import com.gaswell.storage.StorageProperties;
import com.github.pagehelper.PageHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class GaswellApplication {
	// 打包成war格式
	// https://blog.csdn.net/led1114/article/details/122650051
	// https://www.cnblogs.com/zddsl/p/14838618.html
	// https://blog.csdn.net/sheng_xinjun/article/details/90448616
// public class GaswellApplication extends SpringBootServletInitializer { //1.继承SpringBootServletInitializer
//	//2.重写该方法
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(GaswellApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(GaswellApplication.class, args);

		// new NettyServer().start();
		new UDPNettyServer().start();
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			// storageService.deleteAll();
			storageService.init();
		};
	}

	//配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties props = new Properties();
		props.setProperty("offsetAsPageNum","true");
		props.setProperty("rowBoundsWithCount","true");
		props.setProperty("reasonable","true");
		props.setProperty("dialect", "mysql"); //配置mysql数据库的方言
		props.setProperty("supportMethodsArguments", "true");// 表示支持从接口中读取pageNum和pageSize
		pageHelper.setProperties(props);
		return pageHelper;
	}

}
