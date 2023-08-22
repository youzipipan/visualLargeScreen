package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 线路故障成因分析 vo
 *
 * @author c.chuang
 */
@Data
public class LineFailureCauseAnalysisVo {

    private Long total;

    private List<LineFailureCauseAnalysis> list;

    @Data
    public static class LineFailureCauseAnalysis {

        /**
         * 故障原因
         */
        private String faultCause;

        /**
         * 百分比值
         */
        private BigDecimal percentValue;
    }

}
