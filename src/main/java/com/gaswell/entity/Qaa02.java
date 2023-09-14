package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 钻井地质信息
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qaa02对象", description = "钻井地质信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qaa02 implements Serializable {



    private String jh;

    private BigDecimal zzbx;

    private BigDecimal hzby;

    private BigDecimal mdczzbx;

    private BigDecimal mdchzby;

    private BigDecimal zxdjs;

    private String jx;

    private BigDecimal jj;

    private String kzrq;

    private String wzrq;

    private String wjrq;

    private BigDecimal wzjs;

    private BigDecimal ybj;

    private BigDecimal tbj;

    private BigDecimal bxhb;

    private BigDecimal rgjd;

    private BigDecimal jdwy;

    private BigDecimal wyfw;

    private String snsfsd;

    private String gjzl;

    private String wjff;

    private BigDecimal qcyzsd;

    private BigDecimal fsxjzz;

    private BigDecimal yjymd;

    private BigDecimal gjymd;

    private BigDecimal ycjpsj;

    private String zjfs;

    private BigDecimal dmhb;

    private String bz;

    private String bzjh;


}
