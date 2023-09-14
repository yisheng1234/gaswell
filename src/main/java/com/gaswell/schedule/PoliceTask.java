package com.gaswell.schedule;

import com.gaswell.service.AlarmCensusService;
import com.gaswell.service.DiagnosisNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class PoliceTask {
    @Autowired
    private DiagnosisNewService diagnosisNewService;
    @Autowired
    private AlarmCensusService alarmCensusService;

    private static Integer dataNum=0;
//    每5分钟一次
//    @Async
    @Scheduled(cron = "0 */5 * * * ?")
    public void execute1(){
        Integer numNow=diagnosisNewService.getDataNum();
        synchronized(dataNum){

        }
        if(!numNow.equals(dataNum)) {
            diagnosisNewService.gj();
            dataNum=numNow;
        }
    }


    @Async
    @Scheduled(cron = "0 */5 * * * ?")
    public void execute2(){
        Integer numNow=diagnosisNewService.getDataNum();

        if(!numNow.equals(dataNum)) {
            diagnosisNewService.gj();
            diagnosisNewService.dataUnusual();
            diagnosisNewService.JYPolice();
            diagnosisNewService.DDPolice();
            diagnosisNewService.JYFXPolice();
            alarmCensusService.census();
            dataNum=numNow;
        }
    }


}
