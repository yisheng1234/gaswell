package com.gaswell.utils;

import com.gaswell.pojo.AlgorithmRecords;
import com.gaswell.pojo.RealTimeData;
import com.gaswell.pojo.ReciveCycleData;

/**
 * @author Lei Wang
 * @Date: 2022/05/04/ 15:07
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 构造远程算法调用字符串
public class AlgorithmParamBuilder {

    public static String build(AlgorithmRecords records, ReciveCycleData reciveCycleData){
        String s = "";
        s += records.getAlgorithm_type();
        s += ";";
        s += records.getDatabase_condition_type();
        s += ";";
        s += records.getDatabase_condition();
        s += ";";
        s += records.getUser_id();
        s += ";";
        s += records.getAlgorithm_name();
        s += ";";

        String param = records.getAlgorithm_parameter();

        String p = "";
        if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfjy")){
            p += reciveCycleData.getJkwd(); //井口温度
            p += ",";
            p += reciveCycleData.getYgyl(); // 油管压力
            p += ",";
            p += reciveCycleData.getTgyl(); // 套管压力
            p += ",";
            p += (reciveCycleData.getYgyl() - reciveCycleData.getTgyl()); // 油管压力-套管压力
            p += ",";
            p += reciveCycleData.getWsyl(); // 外输压力
            p += ",";
            p += reciveCycleData.getCqill(); // 产气流量
        }
        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfdd")){
            p += reciveCycleData.getJkwd(); //井口温度
            p += ",";
            p += reciveCycleData.getYgyl(); // 油管压力
            p += ",";
            p += reciveCycleData.getTgyl(); // 套管压力
            p += ",";
            p += (reciveCycleData.getYgyl() - reciveCycleData.getTgyl()); // 油管压力-套管压力
            p += ",";
            p += reciveCycleData.getWsyl(); // 外输压力
            p += ",";
            p += reciveCycleData.getCqill(); // 产气流量
        }
        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_sfjk")){
            p += reciveCycleData.getJkwd(); //井口温度
            p += ",";
            p += reciveCycleData.getYgyl(); // 油管压力
            p += ",";
            p += reciveCycleData.getTgyl(); // 套管压力
            p += ",";
            p += (reciveCycleData.getYgyl() - reciveCycleData.getTgyl()); // 油管压力-套管压力
            p += ",";
            p += reciveCycleData.getWsyl(); // 外输压力
            p += ",";
            p += reciveCycleData.getCqill(); // 产气流量
        }
        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_ppjzl")){
            p += reciveCycleData.getJkwd(); //井口温度
            p += ",";
            p += reciveCycleData.getYgyl(); // 油管压力
            p += ",";
            p += reciveCycleData.getTgyl(); // 套管压力
            p += ",";
            p += (reciveCycleData.getYgyl() - reciveCycleData.getTgyl()); // 油管压力-套管压力
            p += ",";
            p += reciveCycleData.getWsyl(); // 外输压力
            p += ",";
            p += reciveCycleData.getCqill(); // 产气流量
        }
        else if(param.equals("r_jkwd,r_pjyy,r_pjty,r_ytyc,r_wsyl,r_ce_rcql,r_jcjzl")){
            p += reciveCycleData.getJkwd(); //井口温度
            p += ",";
            p += reciveCycleData.getYgyl(); // 油管压力
            p += ",";
            p += reciveCycleData.getTgyl(); // 套管压力
            p += ",";
            p += (reciveCycleData.getYgyl() - reciveCycleData.getTgyl()); // 油管压力-套管压力
            p += ",";
            p += reciveCycleData.getWsyl(); // 外输压力
            p += ",";
            p += reciveCycleData.getCqill(); // 产气流量
        }
        s += p;
        return s;
    }

    // TODO 从RealTimeData获取数据
    public static String build(AlgorithmRecords records, RealTimeData realTimeData){
        return "";
    }


}
