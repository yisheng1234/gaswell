package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 试气记录
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qaa10对象", description = "试气记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qaa10 implements Serializable {



    private String jh;

    private String xh;

    private String cw;

    private String jsxh;

    private BigDecimal jdds1;

    private BigDecimal jdds2;

    private BigDecimal cs;

    private BigDecimal hd;

    private String sgdw;

    private String sqmd;

    private String qsrq;

    private String zzrq;

    private String cslb;

    private String qcfs;

    private String gzzd;

    private BigDecimal qcsj;

    private BigDecimal qcsd;

    private BigDecimal jy;

    private BigDecimal jw;

    private BigDecimal ly;

    private BigDecimal lw;

    private BigDecimal zdyy;

    private BigDecimal zxyy;

    private BigDecimal pjyy;

    private BigDecimal zdty;

    private BigDecimal zxty;

    private BigDecimal pjty;

    private BigDecimal jkwd;

    private BigDecimal rcql;

    private BigDecimal rcyl;

    private BigDecimal rcsl;

    private BigDecimal lcql;

    private BigDecimal lcyl;

    private BigDecimal lcsl;

    private String sqjg;

    private String sqjl;

    private String bz;


}
