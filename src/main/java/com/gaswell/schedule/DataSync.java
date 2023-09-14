//package com.gaswell.schedule;
//
//import com.gaswell.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataSync {
//    @Autowired
//    private IQaa01Service qaa01Service;
//    @Autowired
//    private IQaa02Service qaa02Service;
//    @Autowired
//    private IQaa04Service qaa04Service;
//    @Autowired
//    private IQaa10Service qaa10Service;
//    @Autowired
//    private IQaa091Service qaa091Service;
//    @Autowired
//    private IQab04Service qab04Service;
//    @Autowired
//    private IQba01Service qba01Service;
//    @Autowired
//    private IQba02Service qba02Service;
//    @Autowired
//    private IQbb01Service qbb01Service;
//    @Autowired
//    private IQca02Service qca02Service;
//    @Autowired
//    private IQca03Service qca03Service;
//    @Autowired
//    private IQca05Service qca05Service;
//    @Autowired
//    private IQdb02Service qdb02Service;
//    @Autowired
//    private IQfc01Service qfc01Service;
//    @Autowired
//    private IQfc02Service qfc02Service;
//
//    @Scheduled(cron = "1 0 0 * * ?")  //每天 00:00:01执行一次
//    public void execute(){
//
//        int qaa01Count=qaa01Service.dataCount();
//
//
//    }
//}
