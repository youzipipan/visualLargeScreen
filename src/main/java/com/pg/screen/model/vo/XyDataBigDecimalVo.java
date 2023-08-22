package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * X-Y(BigDecimal) Data vo
 *
 * @author c.chuang
 */
@Data
public class XyDataBigDecimalVo {

    public XyDataBigDecimalVo(List<String> xAxisNameList, List<BigDecimal> yAxisNameList) {
        this.xAxisNameList = xAxisNameList;
        this.yAxisNameList = yAxisNameList;
    }

    /**
     * X轴-状态
     */
    private List<String> xAxisNameList;

    /**
     * Y轴-值
     */
    private List<BigDecimal> yAxisNameList;

    /**
     * total
     */
    private BigDecimal total;

}
