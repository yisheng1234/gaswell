package com.gaswell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindLastDataVo {
    private String jh;
    private String zyq;
    private String cjsj;
    private String sjzt;
    private String gkzt;
    private double yy;
    private double ty;
    private double jkwd;
    private String rbrq;
    private BigDecimal rcql;
    private BigDecimal rcsl;
    private String zm;
    private String ym;
}
