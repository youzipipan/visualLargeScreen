package com.pg.screen.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 异常台区处理饼图vo
 *
 * @author c.chuang
 */
@Data
public class ExceptionHandlingPieVo {

    /**
     * 数量
     */
    private Long counts;

    /**
     * 饼图
     */
    private List<EhpPieChart> pieChartList;

    @Data
    public static class EhpPieChart {

        /**
         * 值
         */
        private Long value;

        /**
         * 已处理/未处理
         */
        private String name;

    }

}
