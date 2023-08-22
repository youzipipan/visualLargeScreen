package com.pg.screen.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 频繁故障线路-月和年-总数 vo
 *
 * @author c.chuang
 */
@Data
public class FrequentFailureVo {

    /**
     * 月度累计
     */
    private Integer monthTotal;

    private Integer monthNum;

    /**
     * 年度累计
     */
    private Integer yearTotal;

    private Integer yearNum;

    /**
     * 频繁故障线路列表
     */
    private List<FrequentFailure> list;

    @Data
    public static class FrequentFailure {

        /**
         * 供电单位
         */
        public String unit;

        /**
         * 线路名称
         */
        public String lineName;

        /**
         * 故障发生次数
         */
        public Long counts;
    }

}


