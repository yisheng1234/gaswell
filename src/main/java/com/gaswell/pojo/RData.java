package com.gaswell.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("rtu_data")
public class RData {
    private String sbid;
    private String jh;
    private String cjsj;
    private String jzl;
    private String pl;
    private String dsms;
    private String ds1;
    private String ds1_s;
    private String ds1_f;
    private String dstc;
    private String sd_zd;
    private String tynbdy;
    private String xdcdy;
    private String xdcdydx;
    private String yw;
    private String ywdx;
    private String wd;
    private String krqtnd;
    private String krqtndgx;
    private String yy;
    private String ty;
    private String sjjy_year;
    private String sjjy_month;
    private String sjjy_day;
    private String sjjy_hour;
    private String sjjy_min;
}
