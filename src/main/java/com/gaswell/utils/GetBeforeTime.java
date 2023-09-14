package com.gaswell.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GetBeforeTime {
    //返回n小时前的时间
    public static String getTime(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,hour);

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return time;
    }
    //获取平均值
    public static float getAverage(List<Float> lists){
        float total=0;
        int num=0;
        for (Float list : lists) {
            total+=list;
            num+=1;
        }
        return  total/num;

    }
}
