package com.pg.screen.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 大范围停电情况 vo
 *
 * @author c.chuang
 */
@Data
public class ExtensivePowerOutageSituationVo{

    /**
     * 年度累计停电
     */
    private BigDecimal annualAccumulation;

    /**
     * 本周发生
     */
    private BigDecimal happenThisWeek;

    /**
     * 本周发生百分比
     */
    private Integer happenThisWeekPercent;

    /**
     * X轴
     */
    private List<String> xAxisNameList;

    /**
     * 年度累计List
     */
    private List<Double> yAxisNameList;

    /**
     * 本周发生List
     */
    private List<Double> y2AxisNameList;

}
