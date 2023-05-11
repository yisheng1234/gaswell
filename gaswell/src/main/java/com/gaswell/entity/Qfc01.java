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
@ApiModel(value = "Qfc01对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qfc01 implements Serializable {



    private String jh;

    private BigDecimal xh;

    private String qyri;

    private BigDecimal jw;

    private BigDecimal yw;

    private BigDecimal bw;

    private BigDecimal ydw;

    private BigDecimal zdw;

    private BigDecimal yww;

    private BigDecimal zww;

    private BigDecimal wwys;

    private BigDecimal hq;

    private BigDecimal qq;

    private BigDecimal dq;

    private BigDecimal ryht;

    private BigDecimal yyht;

    private BigDecimal lhq;

    private BigDecimal yq;

    private BigDecimal trqxdmd;

    private BigDecimal ljwd;

    private BigDecimal ljyl;

    private BigDecimal qbz;

    private BigDecimal zqzl;

    private String qydd;

    private String fxrq;

    private String bz;

    private BigDecimal sh;

}
