package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 射孔井段数据
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qaa091对象", description = "射孔井段数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qaa091 implements Serializable {



    private String jh;

    private String rq;

    private BigDecimal skmd;

    private BigDecimal jdds1;

    private BigDecimal jdds2;

    private String xh;

    private String yczmc;

    private String xch;

    private String xfch;

    private BigDecimal fsl;

    private String jsxh;

    private BigDecimal skhd;

    private String bz;

    private BigDecimal sxh;


}
