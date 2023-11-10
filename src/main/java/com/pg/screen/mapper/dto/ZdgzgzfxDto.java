package com.pg.screen.mapper.dto;

import com.pg.screen.mapper.entity.FaultDetail;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ZdgzgzfxDto extends FaultDetail {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private String rownum;
    /**
     * 线路名称
     */
    private String lingName;
    /**
     * 巡视完成时间
     */
    private LocalDateTime inspectionCompletionTime;
    /**
     * 巡视结果
     */
    private String receiptContent;

    /**
     * 故障发生时间
     */
    private String eventTimeStr;

    /**
     * 近期巡视时间
     */
    private String inspectionCompletionTimeStr;
    /**
     * 生成时间
     */
    private String scTime;

}