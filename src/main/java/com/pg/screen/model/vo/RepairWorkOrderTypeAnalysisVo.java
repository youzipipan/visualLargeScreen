package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报修工单类型分析 vo
 *
 * @author c.chuang
 */
@Data
public class RepairWorkOrderTypeAnalysisVo {

    /**
     * total
     */
    private Long total;

    /**
     * list
     */
    private List<RepairWorkOrderTypeAnalysis> list;

    @Data
    public static class RepairWorkOrderTypeAnalysis {

        /**
         * name
         */
        private String name;

        /**
         * value
         */
        private BigDecimal value;

    }

}
