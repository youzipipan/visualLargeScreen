package com.pg.screen.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * X-Y2 Data vo
 *
 * @author c.chuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Xy2DataCountVo extends XyDataCountVo {

    public Xy2DataCountVo(List<String> xAxisNameList, List<Long> yAxisNameList, List<Long> y2AxisNameList) {
        super(xAxisNameList, yAxisNameList);
        this.y2AxisNameList = y2AxisNameList;
    }

    public Xy2DataCountVo() {
    }

    private List<Long> y2AxisNameList;

}
