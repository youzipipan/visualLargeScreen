package com.pg.screen.model.vo;

import lombok.Data;

import java.util.List;

/**
 * X-Y Data vo
 *
 * @author c.chuang
 */
@Data
public class XyDataCountVo {

    public XyDataCountVo(List<String> xAxisNameList, List<Long> yAxisNameList) {
        this.xAxisNameList = xAxisNameList;
        this.yAxisNameList = yAxisNameList;
    }

    @SuppressWarnings("unused")
    public XyDataCountVo() {
    }

    /**
     * X轴
     */
    private List<String> xAxisNameList;

    /**
     * Y轴
     */
    private List<Long> yAxisNameList;

}
