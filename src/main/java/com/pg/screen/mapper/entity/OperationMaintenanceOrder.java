package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运维工单表(OperationMaintenanceOrder)实体类
 *
 * @author makejava
 * @since 2023-06-08 19:00:25
 */
@Data
@Table(value = "OPERATION_MAINTENANCE_ORDER")
public class OperationMaintenanceOrder implements Serializable {

    private static final long serialVersionUID = 259752583583700014L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 工单单号
     */
    private String workOrderNumber;
    /**
     * 工单单位
     */
    private String workOrderUnit;
    /**
     * 作业班组
     */
    private String operationTeam;
    /**
     * 处理人员
     */
    private String processingPersonnel;
    /**
     * 派单人
     */
    private String dispatchOrderPersonnel;
    /**
     * 线路名称
     */
    private String wiringName;
    /**
     * 线路属性
     */
    private String wiringAttribute;
    /**
     * 巡视范围
     */
    private String patrolScope;
    /**
     * APP状态
     */
    private String appStatus;
    /**
     * 任务状态
     */
    private String taskStatus;
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
    private Date arrivalSceneTime;
    /**
     * 巡视完成时间
     */
    private Date inspectionCompletionTime;
    /**
     * 工单时限
     */
    private String workOrderTimeLimit;
    /**
     * 任务来源
     */
    private String taskSource;
    /**
     * 巡视原因
     */
    private String inspectionReason;
    /**
     * 巡视类型
     */
    private String patrolType;

}

