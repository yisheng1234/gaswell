package com.gaswell.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaswell.mapper.RtuDataAiMapper;
import com.gaswell.pojo.RtuDataAi;
import com.gaswell.service.RtuDataAiService;
import com.gaswell.vo.Result;
import com.gaswell.vo.params.RtuDataAiParams;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("mysql")
public class RtuDataAiServiceImpl extends ServiceImpl<RtuDataAiMapper, RtuDataAi> implements RtuDataAiService {
    @Autowired
    private RtuDataAiMapper rtuDataAiMapper;

    @Override
    public Result selectProperties(int current, int size, RtuDataAiParams params) {
        IPage<RtuDataAi> page = new Page<>(current, size);
        LambdaQueryWrapper<RtuDataAi> lqw = new LambdaQueryWrapper();
        // 井号
        lqw.eq(params.getJh() != null && Strings.isNotEmpty(params.getJh()), RtuDataAi::getJh, params.getJh());

        // 采集时间（精确查询，区间查询）
        lqw.eq(params.getCjsj() != null && Strings.isNotEmpty(params.getCjsj()), RtuDataAi::getCjsj, params.getCjsj());
        lqw.ge(params.getCjsj_ge() != null && Strings.isNotEmpty(params.getCjsj_ge()), RtuDataAi::getCjsj, params.getCjsj_ge());
        lqw.le(params.getCjsj_le() != null && Strings.isNotEmpty(params.getCjsj_le()), RtuDataAi::getCjsj, params.getCjsj_le());

        // 油管压力
        lqw.eq(params.getJ_YGYL() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL());
        lqw.ge(params.getJ_YGYL_ge() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL_ge());
        lqw.le(params.getJ_YGYL_le() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL_le());

        // 套管压力
        lqw.eq(params.getJ_TGYL() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL());
        lqw.ge(params.getJ_TGYL_ge() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL_ge());
        lqw.le(params.getJ_TGYL_le() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL_le());

        // 注气压力
        lqw.eq(params.getJ_ZQYL() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL());
        lqw.ge(params.getJ_ZQYL_ge() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL_ge());
        lqw.le(params.getJ_ZQYL_le() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL_le());

        // 油管温度
        lqw.eq(params.getJ_YGWD() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD());
        lqw.ge(params.getJ_YGWD_ge() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD_ge());
        lqw.le(params.getJ_YGWD_le() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD_le());

        // 外输温度
        lqw.eq(params.getJ_WSWD() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD());
        lqw.ge(params.getJ_WSWD_ge() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD_ge());
        lqw.le(params.getJ_WSWD_le() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD_le());

        // 安全阀开
        lqw.eq(params.getJ_FCS() != null && Strings.isNotEmpty(params.getJ_FCS()), RtuDataAi::getJ_FCS, params.getJ_FCS());

        // 安全阀关
        lqw.eq(params.getJ_FOS() != null && Strings.isNotEmpty(params.getJ_FOS()), RtuDataAi::getJ_FOS, params.getJ_FOS());

        // 安全阀停止
        lqw.eq(params.getJ_VVC() != null && Strings.isNotEmpty(params.getJ_VVC()), RtuDataAi::getJ_VVC, params.getJ_VVC());

        // 进站温度
        lqw.eq(params.getZ_JZWD() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD());
        lqw.ge(params.getZ_JZWD_ge() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD_ge());
        lqw.le(params.getZ_JZWD_le() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD_le());

        // 三节后温度
        lqw.eq(params.getZ_SJHWD() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD());
        lqw.ge(params.getZ_SJHWD_ge() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD_ge());
        lqw.le(params.getZ_SJHWD_le() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD_le());

        // 加热后温度
        lqw.eq(params.getZ_JRHWD() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD());
        lqw.ge(params.getZ_JRHWD_ge() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD_ge());
        lqw.le(params.getZ_JRHWD_le() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD_le());

        // 进站压力
        lqw.eq(params.getZ_JZYL() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL());
        lqw.ge(params.getZ_JZYL_ge() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL_ge());
        lqw.le(params.getZ_JZYL_le() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL_le());

        // 三节后压力
        lqw.eq(params.getZ_SJHYL() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL());
        lqw.ge(params.getZ_SJHYL_ge() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL_ge());
        lqw.le(params.getZ_SJHYL_le() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL_le());

        // 基于机理公式判断
        lqw.eq(params.getSfjy_jl() != 0, RtuDataAi::getSfjy_jl, params.getSfjy_jl());
        lqw.eq(params.getSfdd_jl() != 0, RtuDataAi::getSfdd_jl, params.getSfdd_jl());
        lqw.eq(params.getJk_jl() != 0, RtuDataAi::getJk_jl, params.getJk_jl());
        lqw.eq(params.getPpjjzl_jl() != 0, RtuDataAi::getPpjjzl_jl, params.getPpjjzl_jl());
        lqw.eq(params.getJcjzl_jl() != 0, RtuDataAi::getJcjzl_jl, params.getJcjzl_jl());

        // 基于大数据分析
        lqw.eq(params.getSfjy_py() != 0, RtuDataAi::getSfjy_py, params.getSfjy_py());
        lqw.eq(params.getSfdd_py() != 0, RtuDataAi::getSfdd_py, params.getSfdd_py());
        lqw.eq(params.getJk_py() != 0, RtuDataAi::getJk_py, params.getJk_py());

        lqw.eq(params.getXzll_jl() != 0, RtuDataAi::getXzll_jl, params.getXzll_jl());
        lqw.like(params.getBz() != null && Strings.isNotEmpty(params.getBz()), RtuDataAi::getBz, params.getBz());

        IPage<RtuDataAi> page1 = rtuDataAiMapper.selectPage(page, lqw);
        return new Result(true, 200, "success", page1.getRecords(), (int) page1.getTotal(), (int) page1.getPages());
    }

    @Override
    public Result insertBatch(List<RtuDataAi> list) {
        rtuDataAiMapper.insertBatchSomeColumn(list);
        return Result.success(null);
    }

    @Override
    public Result selectCount() {
        int count = rtuDataAiMapper.selectCount(null);
        return Result.success(count);
    }

    @Override
    public Result delete(RtuDataAiParams params) {
        LambdaQueryWrapper<RtuDataAi> lqw = new LambdaQueryWrapper();
        // 井号
        lqw.eq(params.getJh() != null && Strings.isNotEmpty(params.getJh()), RtuDataAi::getJh, params.getJh());

        // 采集时间（精确查询，区间查询）
        lqw.eq(params.getCjsj() != null && Strings.isNotEmpty(params.getCjsj()), RtuDataAi::getCjsj, params.getCjsj());
        lqw.ge(params.getCjsj_ge() != null && Strings.isNotEmpty(params.getCjsj_ge()), RtuDataAi::getCjsj, params.getCjsj_ge());
        lqw.le(params.getCjsj_le() != null && Strings.isNotEmpty(params.getCjsj_le()), RtuDataAi::getCjsj, params.getCjsj_le());

        // 油管压力
        lqw.eq(params.getJ_YGYL() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL());
        lqw.ge(params.getJ_YGYL_ge() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL_ge());
        lqw.le(params.getJ_YGYL_le() != 0, RtuDataAi::getJ_YGYL, params.getJ_YGYL_le());

        // 套管压力
        lqw.eq(params.getJ_TGYL() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL());
        lqw.ge(params.getJ_TGYL_ge() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL_ge());
        lqw.le(params.getJ_TGYL_le() != 0, RtuDataAi::getJ_TGYL, params.getJ_TGYL_le());

        // 注气压力
        lqw.eq(params.getJ_ZQYL() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL());
        lqw.ge(params.getJ_ZQYL_ge() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL_ge());
        lqw.le(params.getJ_ZQYL_le() != 0, RtuDataAi::getJ_ZQYL, params.getJ_ZQYL_le());

        // 油管温度
        lqw.eq(params.getJ_YGWD() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD());
        lqw.ge(params.getJ_YGWD_ge() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD_ge());
        lqw.le(params.getJ_YGWD_le() != 0, RtuDataAi::getJ_YGWD, params.getJ_YGWD_le());

        // 外输温度
        lqw.eq(params.getJ_WSWD() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD());
        lqw.ge(params.getJ_WSWD_ge() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD_ge());
        lqw.le(params.getJ_WSWD_le() != 0, RtuDataAi::getJ_WSWD, params.getJ_WSWD_le());

        // 安全阀开
        lqw.eq(params.getJ_FCS() != null && Strings.isNotEmpty(params.getJ_FCS()), RtuDataAi::getJ_FCS, params.getJ_FCS());

        // 安全阀关
        lqw.eq(params.getJ_FOS() != null && Strings.isNotEmpty(params.getJ_FOS()), RtuDataAi::getJ_FOS, params.getJ_FOS());

        // 安全阀停止
        lqw.eq(params.getJ_VVC() != null && Strings.isNotEmpty(params.getJ_VVC()), RtuDataAi::getJ_VVC, params.getJ_VVC());

        // 进站温度
        lqw.eq(params.getZ_JZWD() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD());
        lqw.ge(params.getZ_JZWD_ge() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD_ge());
        lqw.le(params.getZ_JZWD_le() != 0, RtuDataAi::getZ_JZWD, params.getZ_JZWD_le());

        // 三节后温度
        lqw.eq(params.getZ_SJHWD() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD());
        lqw.ge(params.getZ_SJHWD_ge() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD_ge());
        lqw.le(params.getZ_SJHWD_le() != 0, RtuDataAi::getZ_SJHWD, params.getZ_SJHWD_le());

        // 加热后温度
        lqw.eq(params.getZ_JRHWD() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD());
        lqw.ge(params.getZ_JRHWD_ge() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD_ge());
        lqw.le(params.getZ_JRHWD_le() != 0, RtuDataAi::getZ_JRHWD, params.getZ_JRHWD_le());

        // 进站压力
        lqw.eq(params.getZ_JZYL() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL());
        lqw.ge(params.getZ_JZYL_ge() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL_ge());
        lqw.le(params.getZ_JZYL_le() != 0, RtuDataAi::getZ_JZYL, params.getZ_JZYL_le());

        // 三节后压力
        lqw.eq(params.getZ_SJHYL() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL());
        lqw.ge(params.getZ_SJHYL_ge() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL_ge());
        lqw.le(params.getZ_SJHYL_le() != 0, RtuDataAi::getZ_SJHYL, params.getZ_SJHYL_le());

        // 基于机理公式判断
        lqw.eq(params.getSfjy_jl() != 0, RtuDataAi::getSfjy_jl, params.getSfjy_jl());
        lqw.eq(params.getSfdd_jl() != 0, RtuDataAi::getSfdd_jl, params.getSfdd_jl());
        lqw.eq(params.getJk_jl() != 0, RtuDataAi::getJk_jl, params.getJk_jl());
        lqw.eq(params.getPpjjzl_jl() != 0, RtuDataAi::getPpjjzl_jl, params.getPpjjzl_jl());
        lqw.eq(params.getJcjzl_jl() != 0, RtuDataAi::getJcjzl_jl, params.getJcjzl_jl());

        // 基于大数据分析
        lqw.eq(params.getSfjy_py() != 0, RtuDataAi::getSfjy_py, params.getSfjy_py());
        lqw.eq(params.getSfdd_py() != 0, RtuDataAi::getSfdd_py, params.getSfdd_py());
        lqw.eq(params.getJk_py() != 0, RtuDataAi::getJk_py, params.getJk_py());

        lqw.eq(params.getXzll_jl() != 0, RtuDataAi::getXzll_jl, params.getXzll_jl());
        lqw.like(params.getBz() != null && Strings.isNotEmpty(params.getBz()), RtuDataAi::getBz, params.getBz());

        int delete = rtuDataAiMapper.delete(lqw);
        return Result.success(delete, "删除成功");
    }

    @Override
    public Result update(RtuDataAi params) {
        LambdaQueryWrapper<RtuDataAi> lqw = new LambdaQueryWrapper();
        // 井号
        lqw.eq(params.getJh()!=null && Strings.isNotEmpty(params.getJh()), RtuDataAi::getJh,params.getJh());
        // 采集时间
        lqw.eq(params.getCjsj()!=null && Strings.isNotEmpty(params.getCjsj()), RtuDataAi::getCjsj,params.getCjsj());

        int update = rtuDataAiMapper.update(params,lqw);
        return Result.success(update,"修改成功");
    }
}
