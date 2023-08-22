package com.pg.screen.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户平均停电时间 vo
 *
 * @author c.chuang
 */
@Data
public class MeanOutageTimeVo {

    /**
     * 全口径
     */
    private double fullAperture;

    /**
     * 预安排停电
     */
    private double prearrangedOutage;

    /**
     * 故障停电
     */
    private double faultOutage;

    /**
     * 同比
     */
    private double yearOnYearBasis;

    /**
     * X轴
     */
    private List<String> xAxisNameList;

    /**
     * Y轴
     */
    private List<Double> yAxisNameList;

    /**
     * Y轴2
     */
    private List<Double> y2AxisNameList;

}
