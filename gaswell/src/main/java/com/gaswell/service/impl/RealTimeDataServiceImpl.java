package com.gaswell.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaswell.mapper.QjMapper;
import com.gaswell.mapper.RealTimeDataMapper;
import com.gaswell.pojo.*;
import com.gaswell.service.IQba01Service;
import com.gaswell.service.ParamService;
import com.gaswell.service.QjService;
import com.gaswell.service.RealTimeDataService;
import com.gaswell.utils.DynamicTableNameThreadLocal;
import com.gaswell.vo.PoliceVo;
import com.gaswell.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class RealTimeDataServiceImpl implements RealTimeDataService {
    @Autowired
    private QjService qjService;
    @Autowired
    private QjMapper qjMapper;
    @Autowired
    private RealTimeDataMapper realTimeDataMapper;
    @Autowired
    private ParamService paramService;
    @Autowired
    private IQba01Service qba01Service;

    @Override
    public void addData(RealTimeData realTimeData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        DynamicTableNameThreadLocal.put(realTimeData.getYwbh() + "_" + date);
        realTimeDataMapper.insert(realTimeData);
    }

    @Override
    public Result findLatestData() {
        List<Qj> qjs = qjMapper.selectList(null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        List<RealTimeData> list = new ArrayList<>();
        for (Qj qj : qjs) {
            String dateAndywjh = qj.getYwbh() + "_" + date;
            DynamicTableNameThreadLocal.put(dateAndywjh);
            RealTimeData realTimeData = realTimeDataMapper.findLatestData();
            list.add(realTimeData);
        }
        return Result.success(list);
    }

    @Override
    public Result showData(String ywbh, String properties, String date) {
        DynamicTableNameThreadLocal.put(ywbh + "_" + date + "_backup");
        if (properties.equals("ytyc")) {
            properties = "(jkyy-jkty)";
            return Result.success(realTimeDataMapper.showDataYtyc(properties));
        }
        return Result.success(realTimeDataMapper.showData(properties));

    }

    @Override
    public Result insertOne(RealTimeData realTimeData, String deviceId) {
        return null;
    }

    @Override
    public Result updateOne(RealTimeData realTimeData, String ywjh, String date) {
        DynamicTableNameThreadLocal.put(ywjh + "_" + date);
        realTimeDataMapper.updateById(realTimeData);
        return Result.success(null);
    }

    @Override
    public Result selectProperties(int current, int size, RealTimeData realTimeData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Result selectAll(int current, int size) {
        return null;
    }

    @Override
    public Result insertBatch(List<RealTimeData> list) {
        return null;
    }

    @Override
    public IPage<RealTimeData> getPage(int current, int size) {
        return null;
    }

    @Override
    public IPage<RealTimeData> selectByPropertiesPage(Integer current, Integer size, RealTimeData realTimeData) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Result deleteData(String ywbh, String date, Integer id) {
        return null;
    }

    @Override
    public ReciveCycleData selectLatestNormalDataByYwbh(String ywbh) {
        return null;
    }

    @Override
    public Result findOneDayData(String ywjh, String day) {
//        2022-04-11
        String[] dates = day.split("-");
        String date = dates[0] + dates[1];
        String down = day + " 00:00:00";
        String up = day + " 24:00:00";
        DynamicTableNameThreadLocal.put(ywjh + "_" + date);
        return Result.success(realTimeDataMapper.findOneDayData(down, up));
    }

    @Override
    public Result selectData(String ywbh, String date, String properties) {
        String[] str = date.split("-");
        String ny = str[0] + str[1];
        System.out.println(ny);
        String[] dates = date.split(",");
        String down = dates[0];
        String up = dates[1];
        DynamicTableNameThreadLocal.put(ywbh + "_" + ny + "_backup");
        if (properties.equals("ytyc")) {
            properties = "(jkyy-jkty)";
            return Result.success(realTimeDataMapper.selectDataYtyc(down, up, properties));
        }
        return Result.success(realTimeDataMapper.selectData(down, up, properties));
    }

    @Override
    public Result workingCondition(String properties, int number, String ywjh) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        String dateAndywjh = ywjh + "_" + date;
        DynamicTableNameThreadLocal.put(dateAndywjh);
        RealTimeData realTimeDate = realTimeDataMapper.findLatestData();
        int lastId = realTimeDate.getId();

        Method m = realTimeDate.getClass().getMethod("get" + properties);
        String value = (String) m.invoke(realTimeDate);

        int id = realTimeDataMapper.selectIdByProperties(properties, value);
        if (number > (lastId - id + 1))
            return Result.success(null);
        else
            return Result.success(realTimeDate);
    }

    @Override
    public Result JYPolice(String ywbh) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
        String date = df1.format(new Date());
        DynamicTableNameThreadLocal.put(ywbh + "_" + date);
        Param param=new Param();
        param.setParam_name("报警-N");
        double N=paramService.selectOneByEntity(param).getParam_value();

        List<RealTimeData> realTimeDataList=realTimeDataMapper.selectDataNum((int)N);

        PoliceVo policeVo=new PoliceVo();
        //        连续N个点的标记值等于1
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjy_java())) {
                if (realTimeData.getSfjy_java().equals("2")) {
                    policeVo.setRule_1_jl(false);
                    break;
                }
            }
        }
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjy_py())) {
                if (realTimeData.getSfjy_py().equals("2")) {
                    policeVo.setRule_1_ai(false);
                    break;
                }
            }
        }
//        N个点中出现k个标记值等于1的情况
        param.setParam_name("报警-K");
        double doublek=paramService.selectOneByEntity(param).getParam_value();
        int k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjy_java())) {
                if (realTimeData.getSfjy_java().equals("1")) {
                    k--;
                }
            }
        }
        if(k>0)
            policeVo.setRule_2_jl(false);
        k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjy_py())) {
                if (realTimeData.getSfjy_py().equals("1")) {
                    k--;
                }
            }
        }
        if (k>0)
            policeVo.setRule_2_jl(false);
        return Result.success(policeVo);
    }

    @Override
    public Result DDPolice(String ywbh) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
        String date = df1.format(new Date());
        DynamicTableNameThreadLocal.put(ywbh + "_" + date);
        Param param=new Param();
        param.setParam_name("报警-N");
        double N=paramService.selectOneByEntity(param).getParam_value();

        List<RealTimeData> realTimeDataList=realTimeDataMapper.selectDataNum((int)N);

        PoliceVo policeVo=new PoliceVo();
        //        连续N个点的标记值等于1
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjk_java())) {
                if (realTimeData.getSfjk_java().equals("2")) {
                    policeVo.setRule_1_jl(false);
                    break;
                }
            }
        }
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjk_py())) {
                if (realTimeData.getSfjk_py().equals("2")) {
                    policeVo.setRule_1_ai(false);
                    break;
                }
            }
        }
//        N个点中出现k个标记值等于1的情况
        param.setParam_name("报警-K");
        double doublek=paramService.selectOneByEntity(param).getParam_value();
        int k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjk_java())) {
                if (realTimeData.getSfjk_java().equals("1")) {
                    k--;
                }
            }
        }
        if(k>0)
            policeVo.setRule_2_jl(false);
        k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfjk_py())) {
                if (realTimeData.getSfjk_py().equals("1")) {
                    k--;
                }
            }
        }
        if (k>0)
            policeVo.setRule_2_jl(false);
        return Result.success(policeVo);

    }
    @Override
    public Result SHWPolice(String ywbh) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
        String date = df1.format(new Date());
        DynamicTableNameThreadLocal.put(ywbh + "_" + date);
        Param param=new Param();
        param.setParam_name("报警-N");
        double N=paramService.selectOneByEntity(param).getParam_value();

        List<RealTimeData> realTimeDataList=realTimeDataMapper.selectDataNum((int)N);

        PoliceVo policeVo=new PoliceVo();
        //        连续N个点的标记值等于1
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfshw_java())) {
                if (realTimeData.getSfshw_java().equals("2")) {
                    policeVo.setRule_1_jl(false);
                    break;
                }
            }
        }
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfshw_py())) {
                if (realTimeData.getSfshw_py().equals("2")) {
                    policeVo.setRule_1_ai(false);
                    break;
                }
            }
        }
//        N个点中出现k个标记值等于1的情况
        param.setParam_name("报警-K");
        double doublek=paramService.selectOneByEntity(param).getParam_value();
        int k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfshw_java())) {
                if (realTimeData.getSfshw_java().equals("1")) {
                    k--;
                }
            }
        }
        if(k>0)
            policeVo.setRule_2_jl(false);
        k=(int)doublek;
        for (RealTimeData realTimeData : realTimeDataList) {
            if(StringUtils.isNotBlank(realTimeData.getSfshw_py())) {
                if (realTimeData.getSfshw_py().equals("1")) {
                    k--;
                }
            }
        }
        if (k>0)
            policeVo.setRule_2_jl(false);
        return Result.success(policeVo);
    }

    @Override
    public Result selectALLData(String ywbh,int current,int size) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
        String date = df1.format(new Date());
        DynamicTableNameThreadLocal.put(ywbh + "_" + date);
        int total=realTimeDataMapper.dataNum("realtimedata");
        int totalPage=total / size + (total % size != 0 ? 1 : 0);
        Page<RealTimeData> page=new Page<>(current,size);
        page.setSearchCount(false);
        page.setTotal(total);
        page.setPages(totalPage);
        IPage<RealTimeData> page1 = realTimeDataMapper.selectPage(page, null);
        return new Result(true,200,"success",page1.getRecords(),total,totalPage);

    }

    @Override
    public Result selectDataByDate(String ywbh, String date) {
        String[] str = date.split("-");
        String ny = str[0] + str[1];

        String[] dates = date.split(",");
        String down = dates[0];
        String up = dates[1];
        DynamicTableNameThreadLocal.put(ywbh + "_" + "202207" );
        return Result.success(realTimeDataMapper.selectDataByDate(down,up));

    }

}
