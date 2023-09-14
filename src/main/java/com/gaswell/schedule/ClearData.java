package com.gaswell.schedule;

import com.gaswell.service.DiagnosisNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearData {
    @Autowired
    private DiagnosisNewService diagnosisNewService;
    @Scheduled(cron = "0 0 6 1 * ?")
    public void execute(){
        diagnosisNewService.clear();
    }
}
