package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 巡视历史管控表(InspectionHistoryControl)实体类
 *
 * @author makejava
 * @since 2023-07-07 10:08:07
 */
@Table(value = "INSPECTION_HISTORY_CONTROL")
@Data
public class InspectionHistoryControl implements Serializable {

    private static final long serialVersionUID = 806791708672104543L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 工单单号
     */
    private String workOrderNumber;
    /**
     * 工单时限
     */
    private String workOrderTime;
    /**
     * 工单单位
     */
    private String workOrderUnit;
    /**
     * 作业班组
     */
    private String operationTeam;
    /**
     * 人员
     */
    private String personnels;
    /**
     * 派单人
     */
    private String payman;
    /**
     * 线路名称
     */
    private String wiringName;
    /**
     * 线路属性
     */
    private String wiringAttribute;
    /**
     * 巡视类型
     */
    private String patrolType;
    /**
     * 缺陷数
     */
    private Integer defectsNumber;
    /**
     * 隐患数
     */
    private Integer hiddenDangersNumber;
    /**
     * 计划开始时间
     */
    private Date scheduledStartTime;
    /**
     * 计划结束时间
     */
    private Date scheduledEndTime;
    /**
     * 到达现场时间
     */
    private Date timeOfArrival;
    /**
     * 巡视完成时间
     */
    private Date inspectionCompletionTime;
    /**
     * 回执内容
     */
    private String receiptContent;
    /**
     * 任务来源
     */
    private String taskSource;

}

