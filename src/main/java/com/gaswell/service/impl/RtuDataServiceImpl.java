package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaswell.entity.Qba01;
import com.gaswell.mapper.RtuDataMapper;
import com.gaswell.pojo.Diagnosis;
import com.gaswell.pojo.Qj;
import com.gaswell.pojo.RtuData;
import com.gaswell.service.DiagnosisNewService;
import com.gaswell.service.IQba01Service;
import com.gaswell.service.QjService;
import com.gaswell.service.RtuDataService;
import com.gaswell.vo.FindLastDataVo;
import com.gaswell.vo.Result;
import com.gaswell.vo.RtuDataVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@DS("rtu")
public class RtuDataServiceImpl  implements RtuDataService {

    @Autowired
    private RtuDataMapper rtuDataMapper;
    @Autowired
    private IQba01Service qba01Service;

    @Autowired
    private DiagnosisNewService diagnosisNewService;

    @Autowired
    private QjService qjService;



    @Override
    public Result selectByOptions( RtuData rtuData,int department) {
            LambdaQueryWrapper<RtuData> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(rtuData.getJh() != null, RtuData::getJh, rtuData.getJh());
            String date = rtuData.getCjsj();

            if (StringUtils.isNotBlank(date)) {
                String[] dates = date.split(",");
                String down = dates[0];
                String up = dates[1];
                lambdaQueryWrapper.ge(RtuData::getCjsj, down);
                lambdaQueryWrapper.le(RtuData::getCjsj, up);
            }
            if(department==2){
                List<String> lists = qjService.findJhByYm("川渝");
                lambdaQueryWrapper.in(RtuData::getJh,lists);
            }
            if(department==3)
            {
                List<String> lists = qjService.findJhByYm("大庆");
                lambdaQueryWrapper.in(RtuData::getJh,lists);
            }
            lambdaQueryWrapper.eq(rtuData.getYGYL() != 0, RtuData::getYGYL, rtuData.getYGYL());
            lambdaQueryWrapper.eq(rtuData.getTGYL() != 0, RtuData::getTGYL, rtuData.getTGYL());
            lambdaQueryWrapper.eq(rtuData.getZQYL() != 0, RtuData::getZQYL, rtuData.getZQYL());
            lambdaQueryWrapper.eq(rtuData.getYGWD() != 0, RtuData::getYGWD, rtuData.getYGWD());
            lambdaQueryWrapper.eq(rtuData.getWSWD() != 0, RtuData::getWSWD, rtuData.getWSWD());
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(rtuData.getFCS()), RtuData::getFCS, rtuData.getFCS());
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(rtuData.getFOS()), RtuData::getFOS, rtuData.getFOS());
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(rtuData.getVVC()), RtuData::getVVC, rtuData.getVVC());
            lambdaQueryWrapper.eq(rtuData.getSJHYL() != 0, RtuData::getSJHYL, rtuData.getSJHYL());
            lambdaQueryWrapper.eq(rtuData.getJZYL() != 0, RtuData::getJZYL, rtuData.getJZYL());
            lambdaQueryWrapper.eq(rtuData.getJRHWD() != 0, RtuData::getJRHWD, rtuData.getJRHWD());
            lambdaQueryWrapper.eq(rtuData.getSJHWD() != 0, RtuData::getSJHWD, rtuData.getSJHWD());
            lambdaQueryWrapper.eq(rtuData.getJZWD() != 0, RtuData::getJZWD, rtuData.getJZWD());
//        IPage<RtuData> iPage=new Page<>(current,size);
//        rtuDataMapper.selectPage(iPage,lambdaQueryWrapper);
            List<RtuData> list = rtuDataMapper.selectList(lambdaQueryWrapper);
            return Result.success(list);

//        return new Result(true,200,"success",iPage.getRecords(),(int)iPage.getTotal(),(int)iPage.getPages());
    }
    @Override
    public Result selectAll(int department) {
        List<RtuData> list=new ArrayList<>();
        LambdaQueryWrapper<RtuData> queryWrapper=new LambdaQueryWrapper<>();
        if(department==1)
            list= rtuDataMapper.selectList(null);
        else if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            queryWrapper.in(RtuData::getJh,lists);
            list=rtuDataMapper.selectList(queryWrapper);
        }else {
            List<String> lists = qjService.findJhByYm("大庆");
            queryWrapper.in(RtuData::getJh,lists);
            list=rtuDataMapper.selectList(queryWrapper);
        }
        return Result.success(list);
    }
    @Override
    public Result insertBatch(List<RtuData> list) {
        rtuDataMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public Result selectCount() {
        int count = rtuDataMapper.selectCount(null);
        return Result.success(count);
    }



    @Override
    public Object list() {
        return null;
    }

    @Override
    public Result selectData(String ywbh, String date, String properties) {
        String[] dates = date.split(",");
        String down = dates[0];
        String up = dates[1];
        if (properties.equals("ytyc")) {
            properties = "(jkyy-jkty)";
            return Result.success(rtuDataMapper.selectDataYtyc(down, up, properties,ywbh));
        }
        return Result.success(rtuDataMapper.selectData(down, up, properties,ywbh));
    }

    @Override
    public Result findLatestData(int department) {
        List<FindLastDataVo> list=new ArrayList<>();
//        List<String> jhList=rtuDataMapper.selectAllJh();
        List<String> jhList=new ArrayList<>();
        if(department==1)
            jhList= qjService.findAllJh();
        else if(department==2 )
            jhList=qjService.findJhByYm("川渝");
        else
            jhList=qjService.findJhByYm("大庆");
        for (String jh : jhList) {
            FindLastDataVo findLastDataVo = new FindLastDataVo();
            RtuData rtuData = rtuDataMapper.findLatestData(jh);
            if (rtuData != null) {
                BeanUtils.copyProperties(rtuData, findLastDataVo);
                String cjsj = findLastDataVo.getCjsj();
                String[] s = StringUtils.split(cjsj, " ");
                Qba01 qba01 = qba01Service.setByDate(s[0], jh);
                Qj qj=qjService.selectOne(jh);
                if(qj!=null) {
                    findLastDataVo.setZyq(qj.getScq());
                    findLastDataVo.setYm(qj.getYm());
                    findLastDataVo.setZm(qj.getZm());
                }
                if (qba01 != null) {
                    findLastDataVo.setRbrq(s[0]);
                    findLastDataVo.setRcql(qba01.getRcql());
                    findLastDataVo.setRcsl(qba01.getRcsl());
                }

                List<Diagnosis> dataByTime = diagnosisNewService.findDataByTime(jh,cjsj);
                StringBuilder work = new StringBuilder();
                StringBuilder data = new StringBuilder();
                boolean dataNull = false;
                boolean distortion = false;
                boolean deviation = false;
                boolean jy = false;
                boolean dd = false;
                boolean jyfx = false;
                for (Diagnosis diagnosis : dataByTime) {
                    if (diagnosis.getCategory().equals("null")) {
                        dataNull = true;
                    }
                    if (diagnosis.getCategory().equals("distortion")) {
                        distortion = true;
                    }
                    if (diagnosis.getCategory().equals("deviation")) {
                        deviation = true;
                    }
                    if (diagnosis.getCategory().equals("jy1") || diagnosis.getCategory().equals("jy2") || diagnosis.getCategory().equals("jy3")) {
                        jy = true;
                    }
                    if (diagnosis.getCategory().equals("dd1") || diagnosis.getCategory().equals("dd2") || diagnosis.getCategory().equals("dd3") || diagnosis.getCategory().equals("dd4")) {
                        dd = true;
                    }
                    if (diagnosis.getCategory().equals("jyfx1") || diagnosis.getCategory().equals("jyfx2")) {
                        jyfx = true;
                    }
                }
                if (jy)
                    work.append("积液,");
                if (dd)
                    work.append("冻堵,");
                if (jyfx)
                    work.append("积液风险,");
                if (!jy && !dd && !jyfx){
                    if(dataNull || distortion || deviation)
                        work.append("无,");
                    else
                        work.append("正常,");
                }

                if (dataNull)
                    data.append("数据丢失,");
                if (distortion)
                    data.append("数据失真,");
                if (deviation)
                    data.append("数据趋势偏离,");
                if (!dataNull && !distortion && !deviation)
                    data.append("正常,");

                work.deleteCharAt(work.toString().length() - 1);
                data.deleteCharAt(data.toString().length() - 1);
                findLastDataVo.setSjzt(data.toString());
                findLastDataVo.setGkzt(work.toString());
                findLastDataVo.setYy(rtuData.getYGYL());
                findLastDataVo.setTy(rtuData.getTGYL());
                findLastDataVo.setJkwd(rtuData.getYGWD());
                list.add(findLastDataVo);
            }
        }
        return  Result.success(list);
    }
  @Override
    public Result findLatestData2(int department) {
        List<FindLastDataVo> list=new ArrayList<>();
      List<String> jhList=new ArrayList<>();
        if(department==1)
            jhList= qjService.findAllJh();
        else if(department==2 )
            jhList=qjService.findJhByYm("川渝");
        else
            jhList=qjService.findJhByYm("大庆");
//        List<String> jhList=rtuDataMapper.selectAllJh();
        for (String jh : jhList) {
            FindLastDataVo findLastDataVo = new FindLastDataVo();
            RtuData rtuData = rtuDataMapper.findLatestData(jh);
            if (rtuData != null) {
                BeanUtils.copyProperties(rtuData, findLastDataVo);
                String cjsj = findLastDataVo.getCjsj();
                String[] s = StringUtils.split(cjsj, " ");
                Qba01 qba01 = qba01Service.setByDateLast(jh);
                Qj qj=qjService.selectOne(jh);
                if(qj!=null) {
                    findLastDataVo.setZyq(qj.getScq());
                    findLastDataVo.setYm(qj.getYm());
                    findLastDataVo.setZm(qj.getZm());
                }
                if (qba01 != null) {
                    findLastDataVo.setRbrq(s[0]);
                    findLastDataVo.setRcql(qba01.getRcql());
                    findLastDataVo.setRcsl(qba01.getRcsl());
                }
                List<Diagnosis> dataByTime = diagnosisNewService.findDataByTime(jh, cjsj);
                StringBuilder work = new StringBuilder();
                StringBuilder data = new StringBuilder();
                boolean dataNull = false;
                boolean distortion = false;
                boolean deviation = false;
                boolean jy = false;
                boolean dd = false;
                boolean jyfx = false;
                for (Diagnosis diagnosis : dataByTime) {
                    if (diagnosis.getCategory().equals("null")) {
                        dataNull = true;
                    }
                    if (diagnosis.getCategory().equals("distortion")) {
                        distortion = true;
                    }
                    if (diagnosis.getCategory().equals("deviation")) {
                        deviation = true;
                    }
                    if (diagnosis.getCategory().equals("jy1") || diagnosis.getCategory().equals("jy2") || diagnosis.getCategory().equals("jy3")) {
                        jy = true;
                    }
                    if (diagnosis.getCategory().equals("dd1") || diagnosis.getCategory().equals("dd2") || diagnosis.getCategory().equals("dd3") || diagnosis.getCategory().equals("dd4")) {
                        dd = true;
                    }
                    if (diagnosis.getCategory().equals("jyfx1") || diagnosis.getCategory().equals("jyfx2")) {
                        jyfx = true;
                    }
                }
                if (jy)
                    work.append("积液,");
                if (dd)
                    work.append("冻堵,");
                if (jyfx)
                    work.append("积液风险,");
                if (!jy && !dd && !jyfx){
                    if(dataNull || distortion || deviation)
                        work.append("无,");
                    else
                        work.append("正常,");
                }
                if (dataNull)
                    data.append("数据丢失,");
                if (distortion)
                    data.append("数据失真,");
                if (deviation)
                    data.append("数据趋势偏离,");
                if (!dataNull && !distortion && !deviation)
                    data.append("正常,");
                work.deleteCharAt(work.toString().length() - 1);
                data.deleteCharAt(data.toString().length() - 1);
                findLastDataVo.setSjzt(data.toString());
                findLastDataVo.setGkzt(work.toString());
                findLastDataVo.setYy(rtuData.getYGYL());
                findLastDataVo.setTy(rtuData.getTGYL());
                findLastDataVo.setJkwd(rtuData.getYGWD());
                list.add(findLastDataVo);
            }
        }
        return  Result.success(list);
    }


    @Override
    public List<RtuData> selectList(int department) {
        LambdaQueryWrapper<RtuData>  lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(RtuData::getJh,lists);
        }
        if(department==3)
        {
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(RtuData::getJh,lists);
        }
        return rtuDataMapper.selectList(null);
    }

    @Override
    public Result selectByMutiOptions(RtuDataVo rtuDataVo,String date,int sort,int department) {
        LambdaQueryWrapper<RtuData> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(department==2){
            List<String> lists = qjService.findJhByYm("川渝");
            lambdaQueryWrapper.in(RtuData::getJh,lists);
        }
        if(department==3)
        {
            List<String> lists = qjService.findJhByYm("大庆");
            lambdaQueryWrapper.in(RtuData::getJh,lists);
        }

        int len=rtuDataVo.getOptions().size();
        if (StringUtils.isNotBlank(date)) {
            String[] dates = date.split(",");
            String down = dates[0];
            String up = dates[1];
            lambdaQueryWrapper.ge(RtuData::getCjsj, down);
            lambdaQueryWrapper.le(RtuData::getCjsj, up);
        }
        if(sort==0) {
            lambdaQueryWrapper.last("order by cjsj asc");
        }
        else
            lambdaQueryWrapper.last("order by cjsj desc");
        if(len==0){
            return Result.success(rtuDataMapper.selectList(lambdaQueryWrapper));
        }else
        if(rtuDataVo.getProperty().equals("jh")){
            lambdaQueryWrapper.in(RtuData::getJh,rtuDataVo.getOptions());
        }else
        if(rtuDataVo.getProperty().equals("scq")){
            List<String> jhs=qjService.selectJhByScq(rtuDataVo.getOptions());
            lambdaQueryWrapper.in(RtuData::getJh,jhs);
        }else
        if(rtuDataVo.getProperty().equals("ym")){
            List<String> jhs=qjService.selectJhByYm(rtuDataVo.getOptions());
            lambdaQueryWrapper.in(RtuData::getJh,jhs);
        }else
        if(rtuDataVo.getProperty().equals("zm")){
            List<String> jhs=qjService.selectJhByZm(rtuDataVo.getOptions());
            lambdaQueryWrapper.in(RtuData::getJh,jhs);
        }

        return Result.success(rtuDataMapper.selectList(lambdaQueryWrapper));
    }


}
