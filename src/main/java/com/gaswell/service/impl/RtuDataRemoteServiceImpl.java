package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.RtuDataMapper;
import com.gaswell.pojo.RtuData;
import com.gaswell.service.RtuDataRemoteService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RtuDataParams;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("rtuDataRemote")// 远程数据库数据源
public class RtuDataRemoteServiceImpl extends ServiceImpl<RtuDataMapper, RtuData> implements RtuDataRemoteService {

    @Autowired
    private RtuDataMapper rtuDataMapper;

    @Override
    public Result selectPropertiesByPage(int current, int size, RtuDataParams params) {
        IPage<RtuData> page=new Page<>(current,size);
        LambdaQueryWrapper<RtuData> lqw = new LambdaQueryWrapper();
        // 井号
        lqw.eq(params.getJh()!=null && Strings.isNotEmpty(params.getJh()), RtuData::getJh,params.getJh());

        // 采集时间（精确查询，区间查询）
        lqw.eq(params.getCjsj()!=null && Strings.isNotEmpty(params.getCjsj()), RtuData::getCjsj,params.getCjsj());
        lqw.ge(params.getCjsj_ge()!=null && Strings.isNotEmpty(params.getCjsj_ge()), RtuData::getCjsj,params.getCjsj_ge());
        lqw.le(params.getCjsj_le()!=null && Strings.isNotEmpty(params.getCjsj_le()), RtuData::getCjsj,params.getCjsj_le());

        // 油管压力
        lqw.eq(params.getJ_YGYL()!=0, RtuData::getYGYL,params.getJ_YGYL());
        lqw.ge(params.getJ_YGYL_ge()!=0, RtuData::getYGYL,params.getJ_YGYL_ge());
        lqw.le(params.getJ_YGYL_le()!=0, RtuData::getYGYL,params.getJ_YGYL_le());

        // 套管压力
        lqw.eq(params.getJ_TGYL()!=0, RtuData::getTGYL,params.getJ_TGYL());
        lqw.ge(params.getJ_TGYL_ge()!=0, RtuData::getTGYL,params.getJ_TGYL_ge());
        lqw.le(params.getJ_TGYL_le()!=0, RtuData::getTGYL,params.getJ_TGYL_le());

        // 注气压力
        lqw.eq(params.getJ_ZQYL()!=0, RtuData::getZQYL,params.getJ_ZQYL());
        lqw.ge(params.getJ_ZQYL_ge()!=0, RtuData::getZQYL,params.getJ_ZQYL_ge());
        lqw.le(params.getJ_ZQYL_le()!=0, RtuData::getZQYL,params.getJ_ZQYL_le());

        // 油管温度
        lqw.eq(params.getJ_YGWD()!=0, RtuData::getYGWD,params.getJ_YGWD());
        lqw.ge(params.getJ_YGWD_ge()!=0, RtuData::getYGWD,params.getJ_YGWD_ge());
        lqw.le(params.getJ_YGWD_le()!=0, RtuData::getYGWD,params.getJ_YGWD_le());

        // 外输温度
        lqw.eq(params.getJ_WSWD()!=0, RtuData::getWSWD,params.getJ_WSWD());
        lqw.ge(params.getJ_WSWD_ge()!=0, RtuData::getWSWD,params.getJ_WSWD_ge());
        lqw.le(params.getJ_WSWD_le()!=0, RtuData::getWSWD,params.getJ_WSWD_le());

        // 安全阀开
        lqw.eq(params.getJ_FCS()!=null && Strings.isNotEmpty(params.getJ_FCS()), RtuData::getFCS,params.getJ_FCS());

        // 安全阀关
        lqw.eq(params.getJ_FOS()!=null && Strings.isNotEmpty(params.getJ_FOS()), RtuData::getFOS,params.getJ_FOS());

        // 安全阀停止
        lqw.eq(params.getJ_VVC()!=null && Strings.isNotEmpty(params.getJ_VVC()), RtuData::getVVC,params.getJ_VVC());

        // 进站温度
        lqw.eq(params.getZ_JZWD()!=0, RtuData::getJZWD,params.getZ_JZWD());
        lqw.ge(params.getZ_JZWD_ge()!=0, RtuData::getJZWD,params.getZ_JZWD_ge());
        lqw.le(params.getZ_JZWD_le()!=0, RtuData::getJZWD,params.getZ_JZWD_le());

        // 三节后温度
        lqw.eq(params.getZ_SJHWD()!=0, RtuData::getSJHWD,params.getZ_SJHWD());
        lqw.ge(params.getZ_SJHWD_ge()!=0, RtuData::getSJHWD,params.getZ_SJHWD_ge());
        lqw.le(params.getZ_SJHWD_le()!=0, RtuData::getSJHWD,params.getZ_SJHWD_le());

        // 加热后温度
        lqw.eq(params.getZ_JRHWD()!=0, RtuData::getJRHWD,params.getZ_JRHWD());
        lqw.ge(params.getZ_JRHWD_ge()!=0, RtuData::getJRHWD,params.getZ_JRHWD_ge());
        lqw.le(params.getZ_JRHWD_le()!=0, RtuData::getJRHWD,params.getZ_JRHWD_le());

        // 进站压力
        lqw.eq(params.getZ_JZYL()!=0, RtuData::getJZYL,params.getZ_JZYL());
        lqw.ge(params.getZ_JZYL_ge()!=0, RtuData::getJZYL,params.getZ_JZYL_ge());
        lqw.le(params.getZ_JZYL_le()!=0, RtuData::getJZYL,params.getZ_JZYL_le());

        // 三节后压力
        lqw.eq(params.getZ_SJHYL()!=0, RtuData::getSJHYL,params.getZ_SJHYL());
        lqw.ge(params.getZ_SJHYL_ge()!=0, RtuData::getSJHYL,params.getZ_SJHYL_ge());
        lqw.le(params.getZ_SJHYL_le()!=0, RtuData::getSJHYL,params.getZ_SJHYL_le());

        IPage<RtuData> page1 = rtuDataMapper.selectPage(page, lqw);
        return new Result(true,200,"success",page1.getRecords(),(int)page1.getTotal(),(int)page1.getPages());
    }

    @Override
    public Result selectProperties(RtuDataParams params) {
        LambdaQueryWrapper<RtuData> lqw = new LambdaQueryWrapper();
        // 井号
        lqw.eq(params.getJh() != null && Strings.isNotEmpty(params.getJh()), RtuData::getJh, params.getJh());

        // 采集时间（精确查询，区间查询）
        lqw.eq(params.getCjsj() != null && Strings.isNotEmpty(params.getCjsj()), RtuData::getCjsj, params.getCjsj());
        lqw.ge(params.getCjsj_ge() != null && Strings.isNotEmpty(params.getCjsj_ge()), RtuData::getCjsj, params.getCjsj_ge());
        lqw.le(params.getCjsj_le() != null && Strings.isNotEmpty(params.getCjsj_le()), RtuData::getCjsj, params.getCjsj_le());

        // 油管压力
        lqw.eq(params.getJ_YGYL() != 0, RtuData::getYGYL, params.getJ_YGYL());
        lqw.ge(params.getJ_YGYL_ge() != 0, RtuData::getYGYL, params.getJ_YGYL_ge());
        lqw.le(params.getJ_YGYL_le() != 0, RtuData::getYGYL, params.getJ_YGYL_le());

        // 套管压力
        lqw.eq(params.getJ_TGYL() != 0, RtuData::getTGYL, params.getJ_TGYL());
        lqw.ge(params.getJ_TGYL_ge() != 0, RtuData::getTGYL, params.getJ_TGYL_ge());
        lqw.le(params.getJ_TGYL_le() != 0, RtuData::getTGYL, params.getJ_TGYL_le());

        // 注气压力
        lqw.eq(params.getJ_ZQYL() != 0, RtuData::getZQYL, params.getJ_ZQYL());
        lqw.ge(params.getJ_ZQYL_ge() != 0, RtuData::getZQYL, params.getJ_ZQYL_ge());
        lqw.le(params.getJ_ZQYL_le() != 0, RtuData::getZQYL, params.getJ_ZQYL_le());

        // 油管温度
        lqw.eq(params.getJ_YGWD() != 0, RtuData::getYGWD, params.getJ_YGWD());
        lqw.ge(params.getJ_YGWD_ge() != 0, RtuData::getYGWD, params.getJ_YGWD_ge());
        lqw.le(params.getJ_YGWD_le() != 0, RtuData::getYGWD, params.getJ_YGWD_le());

        // 外输温度
        lqw.eq(params.getJ_WSWD() != 0, RtuData::getWSWD, params.getJ_WSWD());
        lqw.ge(params.getJ_WSWD_ge() != 0, RtuData::getWSWD, params.getJ_WSWD_ge());
        lqw.le(params.getJ_WSWD_le() != 0, RtuData::getWSWD, params.getJ_WSWD_le());

        // 安全阀开
        lqw.eq(params.getJ_FCS() != null && Strings.isNotEmpty(params.getJ_FCS()), RtuData::getFCS, params.getJ_FCS());

        // 安全阀关
        lqw.eq(params.getJ_FOS() != null && Strings.isNotEmpty(params.getJ_FOS()), RtuData::getFOS, params.getJ_FOS());

        // 安全阀停止
        lqw.eq(params.getJ_VVC() != null && Strings.isNotEmpty(params.getJ_VVC()), RtuData::getVVC, params.getJ_VVC());

        // 进站温度
        lqw.eq(params.getZ_JZWD() != 0, RtuData::getJZWD, params.getZ_JZWD());
        lqw.ge(params.getZ_JZWD_ge() != 0, RtuData::getJZWD, params.getZ_JZWD_ge());
        lqw.le(params.getZ_JZWD_le() != 0, RtuData::getJZWD, params.getZ_JZWD_le());

        // 三节后温度
        lqw.eq(params.getZ_SJHWD() != 0, RtuData::getSJHWD, params.getZ_SJHWD());
        lqw.ge(params.getZ_SJHWD_ge() != 0, RtuData::getSJHWD, params.getZ_SJHWD_ge());
        lqw.le(params.getZ_SJHWD_le() != 0, RtuData::getSJHWD, params.getZ_SJHWD_le());

        // 加热后温度
        lqw.eq(params.getZ_JRHWD() != 0, RtuData::getJRHWD, params.getZ_JRHWD());
        lqw.ge(params.getZ_JRHWD_ge() != 0, RtuData::getJRHWD, params.getZ_JRHWD_ge());
        lqw.le(params.getZ_JRHWD_le() != 0, RtuData::getJRHWD, params.getZ_JRHWD_le());

        // 进站压力
        lqw.eq(params.getZ_JZYL() != 0, RtuData::getJZYL, params.getZ_JZYL());
        lqw.ge(params.getZ_JZYL_ge() != 0, RtuData::getJZYL, params.getZ_JZYL_ge());
        lqw.le(params.getZ_JZYL_le() != 0, RtuData::getJZYL, params.getZ_JZYL_le());

        // 三节后压力
        lqw.eq(params.getZ_SJHYL() != 0, RtuData::getSJHYL, params.getZ_SJHYL());
        lqw.ge(params.getZ_SJHYL_ge() != 0, RtuData::getSJHYL, params.getZ_SJHYL_ge());
        lqw.le(params.getZ_SJHYL_le() != 0, RtuData::getSJHYL, params.getZ_SJHYL_le());

        List<RtuData> list = rtuDataMapper.selectList(lqw);
        return Result.success(list);
    }

    @Override
    public Result selectCount() {
        int count = rtuDataMapper.selectCount(null);
        return Result.success(count);
    }

    @Override
    public Result selectAll() {
        List<RtuData> list = rtuDataMapper.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result selectByRange(int countLocal, int countRemote) {
        LambdaQueryWrapper<RtuData> lqw = new LambdaQueryWrapper();
        lqw.last("limit "+ countLocal + "," + countRemote);

        List<RtuData> list = rtuDataMapper.selectList(lqw);
        return Result.success(list);
    }

}
