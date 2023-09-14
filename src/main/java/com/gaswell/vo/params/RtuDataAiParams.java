package com.gaswell.vo.params;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RtuDataAiParams {
    private String jh;

    private String cjsj;
    private String cjsj_ge;
    private String cjsj_le;

    private float J_YGYL;
    private float J_YGYL_ge;
    private float J_YGYL_le;

    private float J_TGYL;
    private float J_TGYL_ge;
    private float J_TGYL_le;

    private float J_ZQYL;
    private float J_ZQYL_ge;
    private float J_ZQYL_le;

    private float J_YGWD;
    private float J_YGWD_ge;
    private float J_YGWD_le;

    private float J_WSWD;
    private float J_WSWD_ge;
    private float J_WSWD_le;

    private String J_FCS;

    private String J_FOS;

    private String J_VVC;

    private float Z_JZWD;
    private float Z_JZWD_ge;
    private float Z_JZWD_le;

    private float Z_SJHWD;
    private float Z_SJHWD_ge;
    private float Z_SJHWD_le;

    private float Z_JRHWD;
    private float Z_JRHWD_ge;
    private float Z_JRHWD_le;

    private float Z_JZYL;
    private float Z_JZYL_ge;
    private float Z_JZYL_le;

    private float Z_SJHYL;
    private float Z_SJHYL_ge;
    private float Z_SJHYL_le;

    // 基于机理公式判断

    private int sfjy_jl;

    private int sfdd_jl;

    private int jk_jl;

    private int ppjjzl_jl;

    private int jcjzl_jl;

    // 基于大数据分析

    private int sfjy_py;

    private int sfdd_py;

    private int jk_py;


    private float xzll_jl;

    private String bz;
}
