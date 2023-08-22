package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (ActiveRepairOrder)实体类
 *
 * @author makejava
 * @since 2023-06-08 16:29:51
 */
@Data
@Table(value = "ACTIVE_REPAIR_ORDER")
public class ActiveRepairOrder implements Serializable {

    private static final long serialVersionUID = 578185956954072569L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 工单编号
     */
    private Integer workOrderNumber;
    /**
     * 母单编号
     */
    private Integer masterOrderNumber;
    /**
     * 故障时间
     */
    private Date failureTime;
    /**
     * 工单类型
     */
    private String workOrderType;
    /**
     * 工单来源
     */
    private String workOrderSource;
    /**
     * 故障类型
     */
    private String faultType;
    /**
     * 所属地市
     */
    private String localCity;
    /**
     * 工单单位
     */
    private String workOrderUnit;
    /**
     * 变电站
     */
    private String substation;
    /**
     * 线路
     */
    private String electricLine;
    /**
     * 设备
     */
    private String equipment;
    /**
     * 支线名称
     */
    private String sideName;
    /**
     * 故障区域
     */
    private String faultArea;
    /**
     * 故障描述
     */
    private String faultDescription;
    /**
     * 额定容量
     */
    private Integer ratedCapacity;
    /**
     * 最大过载功率
     */
    private Integer maxiOverloadPower;

    private String recallResult;
    /**
     * 派单时间
     */
    private Date dispatchTime;
    /**
     * 接单时间
     */
    private Date receivingTime;
    /**
     * 抢修班组
     */
    private String repairTeam;
    /**
     * 抢修人员
     */
    private String repairPersonnel;

    private Date arrivalOnSiteTime;
    /**
     * 预计送电时间
     */
    private Date estimatedPowerDeliveryTime;
    /**
     * 现场反馈内容
     */
    private String onSiteFeedbackContent;
    /**
     * 是否删除 0否 1是
     */
    private String deleted;
    /**
     * 删除原因
     */
    private String deletionReason;
    /**
     * 删除明细
     */
    private String deleteDetails;
    /**
     * 是否误报 0否 1是
     */
    private String falseAlarm;
    /**
     * 误报原因
     */
    private String falseAlarmCause;
    /**
     * 工单状态
     */
    private String workOrderStatus;
    /**
     * app状态
     */
    private String appStatus;
    /**
     * 完成时间
     */
    private Date completionTime;
    /**
     * 处理时长
     */
    private String processingTime;
    /**
     * 原因类型
     */
    private String causeType;

    private String shareStateGrid;

    private String causeSubclass;
    /**
     * 时户数
     */
    private Integer householdsNumber;
    /**
     * 是否自动派单
     */
    private String automaticDispatch;

    private String evaluate;

    private String solutionMeasure;

    private String processingTerminal;

    private String processingResult;

    private Date filingTime;
}

