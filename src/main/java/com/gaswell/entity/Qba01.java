package com.gaswell.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 采气井日数据
 * </p>
 *
 * @author Lei Wang
 * @since 2022-05-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Qba01对象", description = "采气井日数据")
public class Qba01 implements Serializable, Comparable<Qba01> {

    private String jh;

    private String qm;

    private String dm;

    private String zm;

    private String rq;

    private String scsj;

    private String qzzj;

    private BigDecimal jkwd;

    private BigDecimal zgyy;

    private BigDecimal zdyy;

    private BigDecimal pjyy;

    private BigDecimal gjyy;

    private BigDecimal zgty;

    private BigDecimal zdty;

    private BigDecimal pjty;

    private BigDecimal gjty;

    private BigDecimal wsyl;

    private BigDecimal cqdb;

    private BigDecimal cqwd;

    private BigDecimal cqyl;

    private BigDecimal cqyc;

    private BigDecimal rcql;

    private BigDecimal rcsl;

    private BigDecimal rcol;

    private BigDecimal jgty;

    private BigDecimal jyty;

    private BigDecimal rzc;

    private String gjdm;

    private String csdm;

    private String csdm1;

    private String bz;

    private String qcdk;

    private BigDecimal zyql;

    private String jtyl;

    private String btyl;

    private String zyq;

    private String dzd;

    private String qcdka2;

    private String a2jh;

    private String qcdja2;

    private BigDecimal scsjj;


    @Override
    public int compareTo(Qba01 o) {
        Date d = null;
        Date d2 = null;
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        if (o.getRq() != null && this.getRq() != null) {
            try {
                if (this.getRq().contains("-"))
                    d = format1.parse(this.getRq());
                else
                    d = format0.parse(this.getRq());
                if (o.getRq().contains("-"))
                    d2 = format1.parse(o.getRq());
                else
                    d2 = format0.parse(o.getRq());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d.compareTo(d2);
        }
        return 1;
    }


}
