package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 变压器最大负载率计算表(TransformerMaximumLoadRatio)实体类
 *
 * @author makejava
 * @since 2023-07-06 18:51:47
 */
@Data
@Table(value = "TRANSFORMER_MAXIMUM_LOAD_RATIO")
public class TransformerMaximumLoadRatio implements Serializable {

    private static final long serialVersionUID = 693702426888292240L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * CONS_NO
     */
    private Integer consNo;
    /**
     * 采集相数
     */
    private Integer acquisitionPhaseNumber;
    /**
     * 0时
     */
    private BigDecimal zeroHour;
    /**
     * 1时
     */
    private BigDecimal oneHour;
    /**
     * 2时
     */
    private BigDecimal twoHour;
    /**
     * 3时
     */
    private BigDecimal threeHour;
    /**
     * 4时
     */
    private BigDecimal fourHour;
    /**
     * 5时
     */
    private BigDecimal fiveHour;
    /**
     * 6时
     */
    private BigDecimal sixHour;
    /**
     * 7时
     */
    private BigDecimal sevenHour;
    /**
     * 8时
     */
    private BigDecimal eightHour;
    /**
     * 9时
     */
    private BigDecimal nineHour;
    /**
     * 10时
     */
    private BigDecimal tenHour;
    /**
     * 11时
     */
    private BigDecimal elevenHour;
    /**
     * 12时
     */
    private BigDecimal twelveHour;
    /**
     * 13时
     */
    private BigDecimal thirteenHour;
    /**
     * 14时
     */
    private BigDecimal fourteenHour;
    /**
     * 15时
     */
    private BigDecimal fifteenHour;
    /**
     * 16时
     */
    private BigDecimal sixteenHour;
    /**
     * 17时
     */
    private BigDecimal seventeenHour;
    /**
     * 18时
     */
    private BigDecimal eighteenHour;
    /**
     * 19时
     */
    private BigDecimal nineteenHour;
    /**
     * 20时
     */
    private BigDecimal twentyHour;
    /**
     * 21时
     */
    private BigDecimal twentyOneHour;
    /**
     * 22时
     */
    private BigDecimal twentyTwoHour;
    /**
     * 23时
     */
    private BigDecimal twentyThreeHour;
    /**
     * 最大电流
     */
    private BigDecimal maximumCurrent;
    /**
     * 容量
     */
    private Integer capacitys;
    /**
     * 综合倍率
     */
    private Integer compositeRatio;
    /**
     * 最大负载率
     */
    private BigDecimal maximumLoadRate;
    /**
     * 配变名称
     */
    private String variantName;
    /**
     * 单位
     */
    private String units;
    /**
     * 现场实际容量
     */
    private Integer actualFieldCapacity;
    /**
     * 分公司掌握CT变比
     */
    private BigDecimal ctVariableRatio;
    /**
     * 创建时间
     */
    private Date createTime;

}

