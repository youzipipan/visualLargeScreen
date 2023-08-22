package com.pg.screen.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单位抢修时长分析 vo
 *
 * @author c.chuang
 */
@Data
public class RushRepairAvgForUnitVo {

    /**
     * 故障平均修复时长
     */
    private RushRepairXyData faultRepair;

    /**
     * 到达现场平均时长
     */
    private RushRepairXyData arriveAtScene;

    @Data
    public static class RushRepairXyData {

        public RushRepairXyData(List<String> xAxisNameList, List<BigDecimal> yAxisNameList) {
            this.xAxisNameList = xAxisNameList;
            this.yAxisNameList = yAxisNameList;
        }

        /**
         * X轴
         */
        private List<String> xAxisNameList;

        /**
         * Y轴
         */
        private List<BigDecimal> yAxisNameList;

    }

}
