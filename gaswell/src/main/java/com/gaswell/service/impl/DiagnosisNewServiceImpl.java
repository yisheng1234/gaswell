package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.mapper.AlarmRecordMapper;
import com.gaswell.mapper.DiagnosisNewMapper;
import com.gaswell.mapper.ParamsMapper;
import com.gaswell.pojo.AlarmRecord;
import com.gaswell.pojo.Diagnosis;
import com.gaswell.pojo.Params;
import com.gaswell.service.DiagnosisNewService;
import com.gaswell.utils.GetBeforeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("mysql")
public class DiagnosisNewServiceImpl implements DiagnosisNewService {
    @Autowired
    private DiagnosisNewMapper diagnosisNewMapper;

    @Autowired
    private AlarmRecordMapper alarmRecordMapper;
    @Autowired
    private ParamsMapper paramsMapper;
    private static String secondLastTime="2000-01-01 00:00:00";
    @Override
    public void dataUnusual() {
//        数据空
//       找出所有井号
        List<String> jhs = diagnosisNewMapper.selectAllJh();
//        找出数据库中最大时间
        String lastTime = diagnosisNewMapper.selectLastTime();
        for (String jh : jhs) {
//            找出每个井最新五分钟内添加的数据所有异常记录
            List<Diagnosis> latestDatas = diagnosisNewMapper.lastFiveNewData(jh,secondLastTime, lastTime);
            if(latestDatas.size()!=0) {
                for (Diagnosis latestData : latestDatas) {
                    AlarmRecord alarmRecord = new AlarmRecord();
                    if (latestData.getCategory().equals("null")) {
                        alarmRecord.setAlarm_category("数据丢失");
                        alarmRecord.setRecord(latestData.getRecord());
                        alarmRecord.setJh(jh);
                        alarmRecord.setTime(latestData.getCjsj());
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
//            失真
                int N = 0;
                Params params = paramsMapper.selectByNameaAndJh("失真-追溯数", jh);
                if (params == null) {
                    N = (int) paramsMapper.selectByNameaAndJh("失真-追溯数", "all_well").getParam_value();
                } else {
                    N = (int) params.getParam_value();
                }
                int M = 0;
                Params params1 = paramsMapper.selectByNameaAndJh("失真-报警数", jh);
                if (params == null) {
                    M = (int) paramsMapper.selectByNameaAndJh("失真-报警数", "all_well").getParam_value();
                } else {
                    M = (int) params1.getParam_value();
                }
                //先查询最近5分钟内新增的数据，也就是新增的时间点
                List<String> newAddData = diagnosisNewMapper.findNewDataList(jh, secondLastTime, lastTime);
//            对于每个新增的时间点都往前追溯N条数据
                for (String newAddDatum : newAddData) {
//           从当前时间往前查找 N条数据，也就是N个时间点
                    List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                    int distortionNum = 0;
                    for (String s : dateList) {
//                查询每个时间点的所有数据
                        List<Diagnosis> datas = diagnosisNewMapper.findDataByTime(jh, s);
                        for (Diagnosis data : datas) {
                            if (data.getCategory().equals("distortion"))
                                distortionNum++;
                        }
                    }
                    if (distortionNum > M) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("数据失真");
                        alarmRecord.setRecord("往前追溯" + String.valueOf(N) + "个点，数据失真达到" + String.valueOf(M) + "次");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
//趋势偏离
                params = paramsMapper.selectByNameaAndJh("趋势偏离-追溯数", jh);
                if (params == null) {
                    N = (int) paramsMapper.selectByNameaAndJh("趋势偏离-追溯数", "all_well").getParam_value();
                } else {
                    N = (int) params.getParam_value();
                }
                params1 = paramsMapper.selectByNameaAndJh("趋势偏离-报警数", jh);
                if (params == null) {
                    M = (int) paramsMapper.selectByNameaAndJh("趋势偏离-报警数", "all_well").getParam_value();
                } else {
                    M = (int) params1.getParam_value();
                }

                for (String newAddDatum : newAddData) {
                    List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                    int deviationNum = 0;
                    for (String s : dateList) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, s);
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("deviation"))
                                deviationNum++;
                        }
                    }

                    if (deviationNum > M) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("数据偏离趋势");
                        alarmRecord.setRecord("往前追溯" + String.valueOf(N) + "个点，数据偏离趋势达到" + String.valueOf(M) + "次");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
            }
        }

    }

    @Override
    public void JYPolice() {

        //       找出所有井号
        List<String> jhs = diagnosisNewMapper.selectAllJh();
        String lastTime = diagnosisNewMapper.selectLastTime();
        for (String jh : jhs) {
            int N = 0;
            Params params = paramsMapper.selectByNameaAndJh("积液-报警1-N", jh);
            if (params == null) {
                N = (int) paramsMapper.selectByNameaAndJh("积液-报警1-N", "all_well").getParam_value();
            } else {
                N = (int) params.getParam_value();
            }
//            5分钟内新加的时间点
            List<String> newAddData=diagnosisNewMapper.findNewDataList(jh,secondLastTime, lastTime);
            if(newAddData.size()!=0) {
                for (String newAddDatum : newAddData) {
//            从当前点往前追溯N条数据
                    List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                    boolean isPoliceJy1 = true;
                    boolean isPoliceJy2 = true;
                    boolean isPoliceJy3 = true;
                    if (dateList.size() < N) {
                        isPoliceJy1 = false;
                        isPoliceJy2 = false;
                        isPoliceJy3 = false;
                    }
                    for (String s : dateList) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, s);
                        boolean isJy1 = false;
                        boolean isJy2 = false;
                        boolean isJy3 = false;
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("jy1"))
                                isJy1 = true;
                            if (diagnosis.getCategory().equals("jy2"))
                                isJy2 = true;
                            if (diagnosis.getCategory().equals("jy3"))
                                isJy3 = true;
                        }
                        if (!isJy1)
                            isPoliceJy1 = false;
                        if (!isJy2)
                            isPoliceJy2 = false;
                        if (!isJy3)
                            isPoliceJy3 = false;

                    }
                    if (isPoliceJy1) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点的标记值为积液");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPoliceJy2) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点的标记值为积液");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPoliceJy3) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点的标记值为积液");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }

                //           报警规则2对规则1标签进行判断
                Params params1 = paramsMapper.selectByNameaAndJh("积液-报警2-N", jh);
                if (params == null) {
                    N = (int) paramsMapper.selectByNameaAndJh("积液-报警2-N", "all_well").getParam_value();
                } else {
                    N = (int) params1.getParam_value();
                }
                int K = 0;
                Params params3 = paramsMapper.selectByNameaAndJh("积液-报警2-K", jh);
                if (params == null) {
                    K = (int) paramsMapper.selectByNameaAndJh("积液-报警2-K", "all_well").getParam_value();
                } else {
                    K = (int) params3.getParam_value();
                }
                for (String newAddDatum : newAddData) {
                    List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                    int num1 = 0;
                    int num2 = 0;
                    int num3 = 0;
                    for (String s : dateList) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, s);
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("jy1"))
                                num1++;
                            if (diagnosis.getCategory().equals("jy2"))
                                num2++;
                            if (diagnosis.getCategory().equals("jy3"))
                                num3++;
                        }
                    }
                    if (num1 >= K) {
                        AlarmRecord alarmRecord = new AlarmRecord();

                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点中出现" + String.valueOf(K) + "个标记值为积液的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (num2 >= K) {
                        AlarmRecord alarmRecord = new AlarmRecord();

                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点中出现" + String.valueOf(K) + "个标记值为积液的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (num3 >= K) {
                        AlarmRecord alarmRecord = new AlarmRecord();

                        alarmRecord.setTime(dateList.get(dateList.size()-1));
                        alarmRecord.setAlarm_category("积液(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N) + "个点中出现" + String.valueOf(K) + "个标记值为积液的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
            }
        }

    }

    @Override
    public void DDPolice() {
        //       找出所有井号
        List<String> jhs = diagnosisNewMapper.selectAllJh();
        String lastTime = diagnosisNewMapper.selectLastTime();
        for (String jh : jhs) {
            int N_JT=0,N_GX = 0;
            Params params_JT = paramsMapper.selectByNameaAndJh("井筒冻堵-报警1-N", jh);
            Params params_GX = paramsMapper.selectByNameaAndJh("管线冻堵-报警1-N", jh);
            if (params_JT == null) {
                N_JT = (int) paramsMapper.selectByNameaAndJh("井筒冻堵-报警1-N", "all_well").getParam_value();
            } else {
                N_JT = (int) params_JT.getParam_value();
            }
            if (params_GX == null) {
                N_GX = (int) paramsMapper.selectByNameaAndJh("井筒冻堵-报警1-N", "all_well").getParam_value();
            } else {
                N_GX = (int) params_GX.getParam_value();
            }
            List<String> newAddData=diagnosisNewMapper.findNewDataList(jh,secondLastTime, lastTime);
            if(newAddData.size()!=0) {
                for (String newAddDatum : newAddData) {
                    List<String> dateList_GX = diagnosisNewMapper.findDateList(N_GX, jh, newAddDatum);
                    List<String> dateList_JT = diagnosisNewMapper.findDateList(N_JT, jh, newAddDatum);
                    boolean isPolice_dd1 = true;
                    boolean isPolice_dd2 = true;
                    boolean isPolice_dd3_GX = true;
                    boolean isPolice_dd3_JT = true;
                    boolean isPolice_dd4_JT = true;
                    boolean isPolice_dd4_GX = true;
                    if (dateList_GX.size() < N_GX) {
                        isPolice_dd1 = false;
                        isPolice_dd3_GX = false;
                        isPolice_dd4_GX = false;
                    }
                    if (dateList_JT.size() < N_JT) {
                        isPolice_dd2 = false;
                        isPolice_dd3_JT = false;
                        isPolice_dd4_JT = false;
                    }
                    for (String dateList_gx : dateList_GX) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, dateList_gx);
                        boolean isGx_dd1 = false;
                        boolean isGx_dd3 = false;
                        boolean isGx_dd4 = false;
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("dd1"))
                                isGx_dd1 = true;
                            if (diagnosis.getCategory().equals("dd3"))
                                isGx_dd3 = true;
                            if (diagnosis.getCategory().equals("dd4"))
                                isGx_dd4 = true;
                        }
                        if (!isGx_dd1)
                            isPolice_dd1 = false;
                        if (!isGx_dd3)
                            isPolice_dd3_GX = false;
                        if (!isGx_dd4)
                            isPolice_dd4_GX = false;
                    }
                    for (String dateList_jt : dateList_JT) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, dateList_jt);
                        boolean isJt_dd2 = false;
                        boolean isJt_dd3 = false;
                        boolean isJt_dd4 = false;
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("dd2"))
                                isJt_dd2 = true;
                            if (diagnosis.getCategory().equals("dd3"))
                                isJt_dd3 = true;
                            if (diagnosis.getCategory().equals("dd4"))
                                isJt_dd4 = true;
                        }
                        if (!isJt_dd2)
                            isPolice_dd2 = false;
                        if (!isJt_dd3)
                            isPolice_dd3_JT = false;
                        if (!isJt_dd4)
                            isPolice_dd4_JT = false;
                    }
                    if (isPolice_dd1) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点的标记值为地面冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPolice_dd2) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点的标记值为井筒内冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPolice_dd3_GX) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点的标记值为地面管线冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPolice_dd3_JT) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点的标记值为井筒内冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPolice_dd4_JT) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点的标记值为井筒内冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (isPolice_dd4_GX) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点的标记值为地面管线冻堵");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }

                }

//报警规则2对规则1标签进行判断
                Params params1_GX = paramsMapper.selectByNameaAndJh("管线冻堵-报警2-N", jh);
                Params params1_JT = paramsMapper.selectByNameaAndJh("井筒冻堵-报警2-N", jh);
                if (params1_GX == null) {
                    N_GX = (int) paramsMapper.selectByNameaAndJh("管线冻堵-报警2-N", "all_well").getParam_value();
                } else {
                    N_GX = (int) params1_GX.getParam_value();
                }
                if (params1_JT == null) {
                    N_JT = (int) paramsMapper.selectByNameaAndJh("管线冻堵-报警2-N", "all_well").getParam_value();
                } else {
                    N_JT = (int) params1_JT.getParam_value();
                }
                int K_GX, K_JT = 0;
                Params params3_GX = paramsMapper.selectByNameaAndJh("管线冻堵-报警2-K", jh);
                Params params3_JT = paramsMapper.selectByNameaAndJh("井筒冻堵-报警2-K", jh);
                if (params3_JT == null) {
                    K_JT = (int) paramsMapper.selectByNameaAndJh("井筒冻堵-报警2-K", "all_well").getParam_value();
                } else {
                    K_JT = (int) params3_JT.getParam_value();
                }
                if (params3_GX == null) {
                    K_GX = (int) paramsMapper.selectByNameaAndJh("井筒冻堵-报警2-K", "all_well").getParam_value();
                } else {
                    K_GX = (int) params3_GX.getParam_value();
                }
                for (String newAddDatum : newAddData) {
                    List<String> dateList_GX = diagnosisNewMapper.findDateList(N_GX, jh, newAddDatum);
                    List<String> dateList_JT = diagnosisNewMapper.findDateList(N_JT, jh, newAddDatum);
                    int dd1_num = 0;
                    int dd2_num = 0;
                    int dd3_JT_num = 0;
                    int dd3_GX_num = 0;
                    int dd4_JT_num = 0;
                    int dd4_GX_num = 0;
                    for (String dateList_gx : dateList_GX) {
                        List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, dateList_gx);
                        for (Diagnosis diagnosis : dataByTime) {
                            if (diagnosis.getCategory().equals("dd1"))
                                dd1_num++;
                            if (diagnosis.getCategory().equals("dd2"))
                                dd2_num++;
                            if (diagnosis.getCategory().equals("dd3")) {
                                dd3_GX_num++;
                                dd3_JT_num++;
                            }
                            if (diagnosis.getCategory().equals("dd4")) {
                                dd4_GX_num++;
                                dd4_JT_num++;
                            }
                        }
                    }
                    if (dd1_num >= K_GX) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点中出现" + String.valueOf(K_GX) + "个标记值为地面冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (dd2_num >= K_JT) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(规则1)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点中出现" + String.valueOf(K_JT) + "个标记值为井筒内冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (dd3_GX_num >= K_GX) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点中出现" + String.valueOf(K_GX) + "个标记值为地面管线冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (dd3_JT_num >= K_JT) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(机理公式)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点中出现" + String.valueOf(K_JT) + "个标记值为井筒内冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (dd4_GX_num >= K_GX) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_GX.get(dateList_GX.size()-1));
                        alarmRecord.setAlarm_category("地面管线冻堵(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_GX) + "个点中出现" + String.valueOf(K_GX) + "个标记值为地面管线冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                    if (dd4_JT_num >= K_JT) {
                        AlarmRecord alarmRecord = new AlarmRecord();
                        alarmRecord.setTime(dateList_JT.get(dateList_JT.size()-1));
                        alarmRecord.setAlarm_category("井筒内冻堵(算法)");
                        alarmRecord.setRecord("连续" + String.valueOf(N_JT) + "个点中出现" + String.valueOf(K_JT) + "个标记值为井筒内冻堵的点");
                        alarmRecord.setJh(jh);
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
            }
        }
    }
        @Override
        public void JYFXPolice () {
            //       找出所有井号
            List<String> jhs = diagnosisNewMapper.selectAllJh();
            String lastTime = diagnosisNewMapper.selectLastTime();
            for (String jh : jhs) {
                int N = 0;
                Params params = paramsMapper.selectByNameaAndJh("积液风险-报警1-N", jh);
                if (params == null) {
                    N = (int) paramsMapper.selectByNameaAndJh("积液风险-报警1-N", "all_well").getParam_value();
                } else {
                    N = (int) params.getParam_value();
                }
//              找出5分钟内新增加的点
                List<String> newAddData=diagnosisNewMapper.findNewDataList(jh,secondLastTime, lastTime);
                if(newAddData.size()!=0) {
                    for (String newAddDatum : newAddData) {
                        //            从当前点往前追溯N条数据
                        List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                        boolean isPoliceJyfx1 = true;
                        boolean isPoliceJyfx2 = true;
                        if (dateList.size() < N) {
                            isPoliceJyfx1 = false;
                            isPoliceJyfx2 = false;
                        }
                        for (String s : dateList) {
                            List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, s);
                            boolean isJyfx1 = false;
                            boolean isJyfx2 = false;
                            for (Diagnosis diagnosis : dataByTime) {
                                if (diagnosis.getCategory().equals("jyfx1"))
                                    isJyfx1 = true;
                                if (diagnosis.getCategory().equals("jyfx2"))
                                    isJyfx2 = true;
                            }
                            if (!isJyfx1)
                                isPoliceJyfx1 = false;
                            if (!isJyfx2)
                                isPoliceJyfx2 = false;
                        }
                        if (isPoliceJyfx1) {
                            AlarmRecord alarmRecord = new AlarmRecord();
                            alarmRecord.setTime(dateList.get(dateList.size()-1));
                            alarmRecord.setAlarm_category("积液风险(规则1)");
                            alarmRecord.setRecord("连续" + String.valueOf(N) + "个点的标记值为积液风险");
                            alarmRecord.setJh(jh);
                            alarmRecordMapper.insert(alarmRecord);
                        }
                        if (isPoliceJyfx2) {
                            AlarmRecord alarmRecord = new AlarmRecord();
                            alarmRecord.setTime(dateList.get(dateList.size()-1));
                            alarmRecord.setAlarm_category("积液风险(规则2)");
                            alarmRecord.setRecord("连续" + String.valueOf(N) + "个点的标记值为积液风险");
                            alarmRecord.setJh(jh);
                            alarmRecordMapper.insert(alarmRecord);
                        }

                    }


//           报警规则1对规则1标签进行判断
//         ]

                    ////           报警规则2对规则1标签进行判断
                    Params params1 = paramsMapper.selectByNameaAndJh("积液风险-报警2-N", jh);
                    if (params == null) {
                        N = (int) paramsMapper.selectByNameaAndJh("积液风险-报警2-N", "all_well").getParam_value();
                    } else {
                        N = (int) params1.getParam_value();
                    }
                    int K = 0;
                    Params params3 = paramsMapper.selectByNameaAndJh("积液风险-报警2-K", jh);
                    if (params == null) {
                        K = (int) paramsMapper.selectByNameaAndJh("积液风险-报警2-K", "all_well").getParam_value();
                    } else {
                        K = (int) params3.getParam_value();
                    }
                    for (String newAddDatum : newAddData) {
                        List<String> dateList = diagnosisNewMapper.findDateList(N, jh, newAddDatum);
                        int num1 = 0;
                        int num2 = 0;
                        for (String s : dateList) {
                            List<Diagnosis> dataByTime = diagnosisNewMapper.findDataByTime(jh, s);
                            for (Diagnosis diagnosis : dataByTime) {
                                if (diagnosis.getCategory().equals("jyfx1"))
                                    num1++;
                                if (diagnosis.getCategory().equals("jyfx2"))
                                    num2++;
                            }
                        }
                        if (num1 >= K) {
                            AlarmRecord alarmRecord = new AlarmRecord();

                            alarmRecord.setTime(dateList.get(dateList.size()-1));
                            alarmRecord.setAlarm_category("积液风险(规则1)");
                            alarmRecord.setRecord("连续" + String.valueOf(N) + "个点中出现" + String.valueOf(K) + "个标记值为积液风险的点");
                            alarmRecord.setJh(jh);
                            alarmRecordMapper.insert(alarmRecord);
                        }
                        if (num2 >= K) {
                            AlarmRecord alarmRecord = new AlarmRecord();
                            alarmRecord.setTime(dateList.get(dateList.size()-1));
                            alarmRecord.setAlarm_category("积液风险(规则2)");
                            alarmRecord.setRecord("连续" + String.valueOf(N) + "个点中出现" + String.valueOf(K) + "个标记值为积液风险的点");
                            alarmRecord.setJh(jh);
                            alarmRecordMapper.insert(alarmRecord);
                        }
                    }
                }
            }
            secondLastTime=lastTime;
        }

    @Override
    public void clear() {
        String time = GetBeforeTime.getTime(-720);
        LambdaQueryWrapper<AlarmRecord> lqw=new LambdaQueryWrapper<>();
        lqw.le(AlarmRecord::getTime,time);
        alarmRecordMapper.delete(lqw);
    }

    @Override
    public Integer getDataNum() {
       return diagnosisNewMapper.selectCount(null);
    }

    @Override
    public void gj() {
        List<String> jhs = diagnosisNewMapper.selectAllJh();
//        找出数据库中最大时间
        String lastTime = diagnosisNewMapper.selectLastTime();
        for (String jh : jhs) {
//            找出每个井最新五分钟内添加的数据所有异常记录
            List<Diagnosis> latestDatas = diagnosisNewMapper.lastFiveNewData(jh, secondLastTime, lastTime);
            if (latestDatas.size() != 0) {
                for (Diagnosis latestData : latestDatas) {
                    AlarmRecord alarmRecord = new AlarmRecord();
                    if (latestData.getCategory().equals("gj")) {
                        alarmRecord.setAlarm_category("关井");
                        alarmRecord.setRecord(latestData.getRecord());
                        alarmRecord.setJh(jh);
                        alarmRecord.setTime(latestData.getCjsj());
                        alarmRecordMapper.insert(alarmRecord);
                    }
                }
            }
        }
    }

    @Override
    public List<Diagnosis> findDataByTime(String jh, String cjsj) {
            return  diagnosisNewMapper.findDataByTime(jh, cjsj);
        }
}