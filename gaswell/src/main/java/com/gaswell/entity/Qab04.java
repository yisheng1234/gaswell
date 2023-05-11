package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 气藏流体性质数据
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qab04对象", description = "气藏流体性质数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qab04 implements Serializable {



    private String qt;

    private String qkdy;

    private String cw;

    private String nd;

    private String cllb;

    private BigDecimal yshqbhd;

    private String dmtrqmd;

    private BigDecimal dmtrqnd;

    private BigDecimal dctrqmd;

    private BigDecimal dctrqnd;

    private BigDecimal trqtjxs;

    private BigDecimal trqysxs;

    private BigDecimal trqysyz;

    private BigDecimal ljyl;

    private BigDecimal pjljyl;

    private BigDecimal ljwd;

    private BigDecimal pjljwd;

    private BigDecimal ldyl;

    private BigDecimal lhqhl;

    private BigDecimal nxyhl;

    private BigDecimal nxyxdmd;

    private BigDecimal sfsbhd;

    private BigDecimal dcsxdmd;

    private BigDecimal dcsnd;

    private BigDecimal dcsysxs;

    private BigDecimal srzxs;

    private String dcsllzhl;

    private String dcssx;

    private String dcskhd;

    private BigDecimal dcsdzl;


}
