package com.pg.screen.model.vo;

import lombok.Data;

@Data
public class FaultRepairCountVO {

    /**
     * 上报停电事件
     */
    private Long sbtdsjCount;

    /**
     * 智能研判
     */
    private Long znypCount;

    /**
     * 符合精准抢修条件
     */
    private Long fhjzqxtjCount;

    /**
     * 不符合精准抢修条件
     */
    private Long bfhjzqxtjCount;
}
