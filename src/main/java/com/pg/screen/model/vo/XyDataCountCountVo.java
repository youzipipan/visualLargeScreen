package com.pg.screen.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 例：配变低电压vo
 *
 * @author c.chuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XyDataCountCountVo extends XyDataCountVo {

    public XyDataCountCountVo(List<String> xAxisNameList, List<Long> yAxisNameList, Long counts) {
        super(xAxisNameList, yAxisNameList);
        this.counts = counts;
    }

    /**
     * 总数
     */
    private Long counts;

}
