package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 气井基础信息
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@ApiModel(value = "Qaa01对象", description = "气井基础信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qaa01 implements Serializable {


    private String hzjh;

    private String jh;

    private String qtmc;

    private String qcdk;

    private String gzdymc;

    private String ktxm;

    private String ktxmlb;

    private String dlwz;

    private BigDecimal qcds1;

    private BigDecimal qcds2;

    private BigDecimal ycdbs1;

    private BigDecimal ycdbs2;

    private BigDecimal scds;

    private String cw;

    private String skrq;

    private BigDecimal skjdds1;

    private BigDecimal skjdds2;

    private BigDecimal cs;

    private BigDecimal skyxhd;

    private BigDecimal sksyhd;

    private BigDecimal ylsyhd;

    private BigDecimal elsyhd;

    private String mqjb;

    private String wzjb;

    private String tcrq;

    private String cm;

    private String qm;

    private String dm;

    private String zm;

    private String jsrq;

    private String jscw;

    private BigDecimal qczbsd;

    private BigDecimal ysdcyl;

    private BigDecimal ysbhyl;

    private BigDecimal ysdcwd;

    private BigDecimal plyl;

    private String bfrq;

    private String bfyy;

    private String bffs;

    private String bz;

    private String pd;

    private String ip;

    private String cjrq;

    private String cjsj;

    private String zyq;

    private String dzd;

    private String a2jh;

}
