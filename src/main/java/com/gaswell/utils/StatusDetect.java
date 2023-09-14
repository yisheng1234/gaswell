package com.gaswell.utils;

import com.gaswell.pojo.RealTimeData;
import com.gaswell.pojo.ReciveCycleData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lei Wang
 * @Date: 2022/03/22/ 18:05
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 根据机理公式判断气井状态
public class StatusDetect {

    /**
     * jk_threshold为间开阈值
     */
    public static String isJianKai(ReciveCycleData reciveCycleData, double jk_threshold) {
        String jk = null;
        if (reciveCycleData.getYgyl() - reciveCycleData.getWsyl() < jk_threshold) {
            jk = "2";
        } else {
            jk = "1";
        }
        // 设置对象间开属性
        // reciveCycleData.setJK(jk);
        return jk;
    }

    /**
     * g为天然气相对密度
     * d为油管直径（单位为mm）
     * vg为流速里面的参数
     */
    public static String isJiYe(ReciveCycleData reciveCycleData, double g, double d, double vg) {
        String jy = null;
        double Ppc = 0;
        double Tpc = 0;
        if (g >= 0.7) {
            // 拟临界压力
            Ppc = 5.10 - 0.69 * g;
            // 拟临界温度
            Tpc = 132.2 + 116.7 * g;
        } else {
            // 拟临界压力
            Ppc = 4.78 - 0.25 * g;
            // 拟临界温度
            Tpc = 106.1 + 152.2 * g;
        }
        // 井口温度，单位开尔文K
        double T = reciveCycleData.getJkwd() + 273.15;
        // 井口压力 MPa
        // TODO 井口压力是否为Ygyl？
        double P = reciveCycleData.getYgyl();
        // 拟对比压力 无单位
        double Ppr = P / Ppc;
        // 拟对比温度 无单位
        double Tpr = T / Tpc;
        double a1 = 0.31506237;
        double a2 = -1.0467099;
        double a3 = -0.57832729;
        double a4 = 0.53530771;
        double a5 = -0.61232031;
        double a6 = -0.10488813;
        double a7 = 0.68157001;
        double a8 = 0.68446549;
        // 牛顿迭代法求气体拟对比密度ρ

        double pppr0 = 50; // 拟对比密度初始值，50kg/m³

        double F_pppr0 = pppr0 - 0.27 * pppr0 / Tpr + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * Math.pow(pppr0, 2) + (
                a4 + a5 / Tpr) * Math.pow(pppr0, 3) + (a5 * a6 * Math.pow(pppr0, 6)) / Tpr + (a7 * Math.pow(pppr0, 3) / Math.pow(Tpr, 3)) * (
                1 + a8 * Math.pow(pppr0, 2)) * Math.pow(Math.exp(1), (-a8 * Math.pow(pppr0, 2)));

        double F_pppr0_ = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * 2 * pppr0 + (a4 + a5 / Tpr) * 3 * Math.pow(pppr0, 2) + (
                a5 * a6 / Tpr) * 6 * Math.pow(pppr0, 5) + (a7 / Math.pow(Tpr, 3)) * (
                3 * Math.pow(pppr0, 2) + a8 * 3 * Math.pow(pppr0, 4) - Math.pow(a8, 2) * 2 * Math.pow(pppr0, 6)) * Math.pow(Math.exp(1), (
                -a8 * Math.pow(pppr0, 2)));

        double pppr1 = pppr0 - F_pppr0 / F_pppr0_;

        // 精度0.00001
        while (Math.abs(pppr1 - pppr0) > Math.pow(Math.exp(1), -5)) {
            pppr0 = pppr1;
            F_pppr0 = pppr0 - 0.27 * pppr0 / Tpr + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * Math.pow(pppr0, 2) + (
                    a4 + a5 / Tpr) * Math.pow(pppr0, 3) + (a5 * a6 * Math.pow(pppr0, 6)) / Tpr + (a7 * Math.pow(pppr0, 3) / Math.pow(Tpr, 3)) * (
                    1 + a8 * Math.pow(pppr0, 2)) * Math.pow(Math.exp(1), (-a8 * Math.pow(pppr0, 2)));
            F_pppr0_ = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * 2 * pppr0 + (a4 + a5 / Tpr) * 3 * Math.pow(pppr0, 2) + (
                    a5 * a6 / Tpr) * 6 * Math.pow(pppr0, 5) + (a7 / Math.pow(Tpr, 3)) * (
                    3 * Math.pow(pppr0, 2) + a8 * 3 * Math.pow(pppr0, 4) - Math.pow(a8, 2) * 2 * Math.pow(pppr0, 6)) * Math.pow(Math.exp(1), (
                    -a8 * Math.pow(pppr0, 2)));
            pppr1 = pppr0 - F_pppr0 / F_pppr0_;
        }

        // 最终结果，求出拟对比密度==pppr1
        // 气体偏差系数Z
        double Z = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * pppr1 + (a4 + a5 / Tpr) * Math.pow(pppr1, 2) + (
                a5 * a6 / Tpr) * Math.pow(pppr1, 5) + (a7 / Tpr * 3) * Math.pow(pppr1, 2) * (1 + a8 * Math.pow(pppr1, 2)) * Math.pow(Math.exp(1), (
                a8 * Math.pow(pppr1, 2)));
        double t = reciveCycleData.getJkwd(); // 井口温度，单位℃
        double pl = (1.083886 - 5.10546 * 0.0001 * t - 3.06254 * 0.000001 * Math.pow(t, 2)) * 1000;  // 地层水密度kg/m³
        double c_137 = 52.5 - 0.87018 * P;  // 137度时水的表面张力 mN/M
        double c_23 = 76 * Math.pow(Math.exp(1), (-0.0362575 * P));  // 23度时水的表面张力 mN/M
        double c_now = (1.8 * (137.78 - t) / 206) * (c_23 - c_137) + c_137;  // 当前水的表面张力 mN/M
        double pg = 3484.4 * g * P / (Z * T); // 天然气密度
        d = d / 1000;  // 油管直径，单位m 油管直径这个参数可调，暂定62mm
        double A = 3.14 * Math.pow(d, 2) / 4;
        double Vg = 0;
        if (pl == 0 || pg == 0) {
            Vg = 0;
        } else {
            Vg = vg * Math.pow(((0.001 * c_now * (pl - pg)) / Math.pow(pg, 2)), 0.25);  // 流速  这里面的2.5可调
        }
        double Qsc = 2.5 * Math.pow(10, 4) * P * A * Vg / (T * Z);  // 卸载流量
        // TODO 日产气量是否为Cqill？
        double Q = reciveCycleData.getCqill();  // 日产气量
        if (Qsc > Q) { // 卸载流量大于日产气量，此时为积液
            jy = "1";
        } else { // 此时为不积液
            jy = "2";
        }
        // 设置对象是否积液
        // reciveCycleData.setJY(jy);
        return jy;
    }
    public static List<Double> isJiYe_realtime(RealTimeData realTimeData, double g, double d, double vg) {
        double jy = 0;
        double Ppc = 0;
        double Tpc = 0;
        if (g >= 0.7) {
            // 拟临界压力
            Ppc = 5.10 - 0.69 * g;
            // 拟临界温度
            Tpc = 132.2 + 116.7 * g;
        } else {
            // 拟临界压力
            Ppc = 4.78 - 0.25 * g;
            // 拟临界温度
            Tpc = 106.1 + 152.2 * g;
        }
        // 井口温度，单位开尔文K
        double T = realTimeData.getJkwd() + 273.15;
        // 井口压力 MPa
        // TODO 井口压力是否为Ygyl？
        double P = realTimeData.getJkyy();
        // 拟对比压力 无单位
        double Ppr = P / Ppc;
        // 拟对比温度 无单位
        double Tpr = T / Tpc;
        double a1 = 0.31506237;
        double a2 = -1.0467099;
        double a3 = -0.57832729;
        double a4 = 0.53530771;
        double a5 = -0.61232031;
        double a6 = -0.10488813;
        double a7 = 0.68157001;
        double a8 = 0.68446549;
        // 牛顿迭代法求气体拟对比密度ρ

        double pppr0 = 50; // 拟对比密度初始值，50kg/m³

        double F_pppr0 = pppr0 - 0.27 * pppr0 / Tpr + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * Math.pow(pppr0, 2) + (
                a4 + a5 / Tpr) * Math.pow(pppr0, 3) + (a5 * a6 * Math.pow(pppr0, 6)) / Tpr + (a7 * Math.pow(pppr0, 3) / Math.pow(Tpr, 3)) * (
                1 + a8 * Math.pow(pppr0, 2)) * Math.pow(Math.exp(1), (-a8 * Math.pow(pppr0, 2)));

        double F_pppr0_ = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * 2 * pppr0 + (a4 + a5 / Tpr) * 3 * Math.pow(pppr0, 2) + (
                a5 * a6 / Tpr) * 6 * Math.pow(pppr0, 5) + (a7 / Math.pow(Tpr, 3)) * (
                3 * Math.pow(pppr0, 2) + a8 * 3 * Math.pow(pppr0, 4) - Math.pow(a8, 2) * 2 * Math.pow(pppr0, 6)) * Math.pow(Math.exp(1), (
                -a8 * Math.pow(pppr0, 2)));

        double pppr1 = pppr0 - F_pppr0 / F_pppr0_;

        // 精度0.00001
        while (Math.abs(pppr1 - pppr0) > Math.pow(Math.exp(1), -5)) {
            pppr0 = pppr1;
            F_pppr0 = pppr0 - 0.27 * pppr0 / Tpr + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * Math.pow(pppr0, 2) + (
                    a4 + a5 / Tpr) * Math.pow(pppr0, 3) + (a5 * a6 * Math.pow(pppr0, 6)) / Tpr + (a7 * Math.pow(pppr0, 3) / Math.pow(Tpr, 3)) * (
                    1 + a8 * Math.pow(pppr0, 2)) * Math.pow(Math.exp(1), (-a8 * Math.pow(pppr0, 2)));
            F_pppr0_ = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * 2 * pppr0 + (a4 + a5 / Tpr) * 3 * Math.pow(pppr0, 2) + (
                    a5 * a6 / Tpr) * 6 * Math.pow(pppr0, 5) + (a7 / Math.pow(Tpr, 3)) * (
                    3 * Math.pow(pppr0, 2) + a8 * 3 * Math.pow(pppr0, 4) - Math.pow(a8, 2) * 2 * Math.pow(pppr0, 6)) * Math.pow(Math.exp(1), (
                    -a8 * Math.pow(pppr0, 2)));
            pppr1 = pppr0 - F_pppr0 / F_pppr0_;
        }

        // 最终结果，求出拟对比密度==pppr1
        // 气体偏差系数Z
        double Z = 1 + (a1 + a2 / Tpr + a3 / Math.pow(Tpr, 3)) * pppr1 + (a4 + a5 / Tpr) * Math.pow(pppr1, 2) + (
                a5 * a6 / Tpr) * Math.pow(pppr1, 5) + (a7 / Tpr * 3) * Math.pow(pppr1, 2) * (1 + a8 * Math.pow(pppr1, 2)) * Math.pow(Math.exp(1), (
                a8 * Math.pow(pppr1, 2)));
        double t = realTimeData.getJkwd(); // 井口温度，单位℃
        double pl = (1.083886 - 5.10546 * 0.0001 * t - 3.06254 * 0.000001 * Math.pow(t, 2)) * 1000;  // 地层水密度kg/m³
        double c_137 = 52.5 - 0.87018 * P;  // 137度时水的表面张力 mN/M
        double c_23 = 76 * Math.pow(Math.exp(1), (-0.0362575 * P));  // 23度时水的表面张力 mN/M
        double c_now = (1.8 * (137.78 - t) / 206) * (c_23 - c_137) + c_137;  // 当前水的表面张力 mN/M
        double pg = 3484.4 * g * P / (Z * T); // 天然气密度
        d = d / 1000;  // 油管直径，单位m 油管直径这个参数可调，暂定62mm
        double A = 3.14 * Math.pow(d, 2) / 4;
        double Vg = 0;
        if (pl == 0 || pg == 0) {
            Vg = 0;
        } else {
            Vg = vg * Math.pow(((0.001 * c_now * (pl - pg)) / Math.pow(pg, 2)), 0.25);  // 流速  这里面的2.5可调
        }
        double Qsc = 2.5 * Math.pow(10, 4) * P * A * Vg / (T * Z);  // 卸载流量

        // TODO 日产气量是否为Cqill？
        double Q = realTimeData.getCkwd();  // 日产气量
        if (Qsc > Q) { // 卸载流量大于日产气量，此时为积液
            jy = 1;
        } else { // 此时为不积液
            jy = 0;
        }
        // 设置对象是否积液
        // reciveCycleData.setJY(jy);
        List<Double> list=new ArrayList<>();
        list.add(jy);
        list.add(Qsc);
        return list;
    }

    public static String isShuiHeWu(ReciveCycleData reciveCycleData, double g) {

        // 构建不同天然气相当于密度对应的B和B1值
        Map<Double, Map<String, Double>> map = new HashMap<>();
        Map<String, Double> map1 = new HashMap<>();
        map1.put("B", 24.25);
        map1.put("B1", 77.4);
        map.put(0.56, map1);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("B", 20.00);
        map2.put("B1", 64.2);
        map.put(0.58, map2);

        Map<String, Double> map3 = new HashMap<>();
        map3.put("B", 17.67);
        map3.put("B1", 56.1);
        map.put(0.60, map3);

        Map<String, Double> map4 = new HashMap<>();
        map4.put("B", 16.45);
        map4.put("B1", 51.6);
        map.put(0.62, map4);

        Map<String, Double> map5 = new HashMap<>();
        map5.put("B", 15.47);
        map5.put("B1", 48.6);
        map.put(0.64, map5);

        Map<String, Double> map6 = new HashMap<>();
        map6.put("B", 14.76);
        map6.put("B1", 46.9);
        map.put(0.66, map6);

        Map<String, Double> map7 = new HashMap<>();
        map7.put("B", 14.34);
        map7.put("B1", 45.6);
        map.put(0.68, map7);

        Map<String, Double> map8 = new HashMap<>();
        map8.put("B", 14.00);
        map8.put("B1", 44.4);
        map.put(0.70, map8);

        Map<String, Double> map9 = new HashMap<>();
        map9.put("B", 13.72);
        map9.put("B1", 43.4);
        map.put(0.72, map9);

        Map<String, Double> map10 = new HashMap<>();
        map10.put("B", 13.32);
        map10.put("B1", 42.0);
        map.put(0.75, map10);

        Map<String, Double> map11 = new HashMap<>();
        map11.put("B", 12.74);
        map11.put("B1", 39.9);
        map.put(0.8, map11);

        Map<String, Double> map12 = new HashMap<>();
        map12.put("B", 12.18);
        map12.put("B1", 37.9);
        map.put(0.85, map12);

        Map<String, Double> map13 = new HashMap<>();
        map13.put("B", 11.66);
        map13.put("B1", 36.2);
        map.put(0.90, map13);

        Map<String, Double> map14 = new HashMap<>();
        map14.put("B", 11.17);
        map14.put("B1", 34.5);
        map.put(0.95, map14);

        Map<String, Double> map15 = new HashMap<>();
        map15.put("B", 10.77);
        map15.put("B1", 33.1);
        map.put(1.00, map15);

//        double B = 17.67;  // # 相对密度0.6时
//        double B1 = 56.1;  // # 相对密度0.6时
        double B = map.get(g).get("B");
        double B1 = map.get(g).get("B1");
        double T = reciveCycleData.getJkwd() + 273.15;
        double logp = 0;
        if (T > 273) {
            logp = -1.0056 + 0.0541 * (B + T - 273);
        } else {
            logp = -1.0055 + 0.017 * (B1 - T + 273);
        }

        double p_s = Math.pow(10, logp);  // # 温度T对应的水合物压力最小临界值。
        String dd = null;
        //# 如果压力（井口压力），大于临界压力，判定含水合物
        // TODO 井口压力是否为Ygyl
        double P = reciveCycleData.getYgyl();
        if (P > p_s) {
            dd = "1";
        } else {
            dd = "2";
        }
        // 设置对象是否含水合物
        return dd;
    }

    public static String isNullValue(ReciveCycleData newData) {
        System.out.println("236");
        System.out.println("isnullValue-----------"+newData);
        String jkwd = newData.getJkwd().toString();
        String tgyl = newData.getTgyl().toString();
        String ygyl = newData.getYgyl().toString();
        String wsyl = newData.getWsyl().toString();
        String cyell = newData.getCyell().toString();
        String cyoull = newData.getCyoull().toString();
        String cshuill = newData.getCshuill().toString();
        String cqill = newData.getCqill().toString();
        String jyyl = newData.getJyyl().toString();
        String jyll = newData.getJyll().toString();
        String qjkgzjl = newData.getQjkgzjl().toString();
        String ppkgzjl = newData.getPpkgzjl().toString();
        String zckgzjl = newData.getZckgzjl().toString();
        String deviceId = newData.getDeviceId();
        String rtuVoltage = newData.getRtuVoltage();
        String rtuAi = newData.getRtuAi();
        if (StringUtils.isNotBlank(jkwd) &&
                StringUtils.isNotBlank(tgyl) &&
                StringUtils.isNotBlank(ygyl) &&
                StringUtils.isNotBlank(wsyl) &&
                StringUtils.isNotBlank(cyell) &&
                StringUtils.isNotBlank(cyoull) &&
                StringUtils.isNotBlank(cshuill) &&
                StringUtils.isNotBlank(cqill) &&
                StringUtils.isNotBlank(jyyl) &&
                StringUtils.isNotBlank(jyll) &&
                StringUtils.isNotBlank(qjkgzjl) &&
                StringUtils.isNotBlank(ppkgzjl) &&
                StringUtils.isNotBlank(zckgzjl) &&
                StringUtils.isNotBlank(deviceId) &&
                StringUtils.isNotBlank(rtuVoltage) &&
                StringUtils.isNotBlank(rtuAi) &&
                StringUtils.isNotBlank(ygyl)
        ) {
            return "0";
        } else {
            return "1";
        }
    }

    public static String isYT(ReciveCycleData newData) {
        if (newData.getYgyl() > newData.getTgyl()) {
            return "1";
        }
        return "0";
    }

    public static String isFiveFold(ReciveCycleData lastData, ReciveCycleData newData) {
        Double jkwd = newData.getJkwd();
        Double tgyl = newData.getTgyl();
        Double ygyl = newData.getYgyl();
        Double wsyl = newData.getWsyl();
        Double cyell = newData.getCyell();
        Double cyoull = newData.getCyoull();
        Double cshuill = newData.getCshuill();
        Double cqill = newData.getCqill();
        Double jyyl = newData.getJyyl();
        Double jyll = newData.getJyll();

        Double jkwd_old = lastData.getJkwd();
        Double tgyl_old = lastData.getTgyl();
        Double ygyl_old = lastData.getYgyl();
        Double wsyl_old = lastData.getWsyl();
        Double cyell_old = lastData.getCyell();
        Double cyoull_old = lastData.getCyoull();
        Double cshuill_old = lastData.getCshuill();
        Double cqill_old = lastData.getCqill();
        Double jyyl_old = lastData.getJyyl();
        Double jyll_old = lastData.getJyll();

        if (jkwd > 5 * jkwd_old || tgyl > 5 * tgyl_old || ygyl > 5 * ygyl_old || wsyl > 5 * wsyl_old
                || cyell > 5 * cyell_old || cyoull > 5 * cyoull_old || cshuill > 5 * cshuill_old
                || cqill > 5 * cqill_old || jyyl > 5 * jyyl_old || jyll > 5 * jyll_old) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * lastData：上一条正常数据
     * newData：当前接收到的数据
     */
    public static String isDeviceError(ReciveCycleData lastData, ReciveCycleData newData) {


        //        第一条数据
        System.out.println("isdeviceReeor----------"+newData);
        if (lastData == null) {

            if (isNullValue(newData).equals("1")) {
                return "3";
            }
            if (isYT(newData).equals("1")) {
                return "4";
            }
        } else {
            if (isFiveFold(lastData, newData).equals("1")) {
                return "2";
            }
            if (isNullValue(newData).equals("1")) {
                return "3";
            }
            if (isYT(newData).equals("1")) {
                return "4";
            }

        }
        return "1";
    }

    // 泡排挤加注量
    /**
     * Ql：产液量
     * a：浓度：5/1000
     * b：稀释倍数：5
     */
    public static double ppjjzl(double Ql, double a, double b){
        return Ql*a*b*1000;
    }

    // 甲醇加注量
    /**
     * c：每小时加注量：20L/h
     */
    public static double jcjzl(double c){
        return 24*c;
    }



}
