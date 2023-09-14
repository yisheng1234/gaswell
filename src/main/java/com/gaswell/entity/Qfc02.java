package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qfc02对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qfc02 implements Serializable {



    private String jh;

    private BigDecimal xh;

    private String qyri;

    private BigDecimal ph;

    private BigDecimal dcsxdmd;

    private BigDecimal yd;

    private BigDecimal co3;

    private BigDecimal hco3;

    private BigDecimal cl1;

    private BigDecimal so4;

    private BigDecimal ca;

    private BigDecimal mg;

    private BigDecimal kna;

    private BigDecimal b;

    private BigDecimal i;

    private BigDecimal oh;

    private BigDecimal zkhd;

    private String sx;

    private BigDecimal ejt;

    private BigDecimal sjt;

    private BigDecimal wlys;

    private BigDecimal djsd1;

    private BigDecimal djsd2;

    private String yccw;

    private String qydd;

    private String fxrq;

    private String bz;

    private BigDecimal sh;

}
