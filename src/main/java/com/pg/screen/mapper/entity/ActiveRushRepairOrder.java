package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主动抢修工单表(ActiveRushRepairOrder)实体类
 *
 * @author makejava
 * @since 2023-06-08 11:03:56
 */
@Data
@Table(value = "ACTIVE_RUSH_REPAIR_ORDER")
public class ActiveRushRepairOrder implements Serializable {

    private static final long serialVersionUID = -56776158063029390L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 工单编号
     */
    private String workOrderNumber;
    /**
     * 母单编号
     */
    private String masterOrderNumber;
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
     * 实物ID
     */
    private String objectId;
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
    /**
     * 预计送电时间
     */
    private Date estimatedPowerDeliveryTime;
    /**
     * 现场反馈内容
     */
    private String onSiteFeedbackContent;
    /**
     * 同源编号
     */
    private String homologyNumber;
    /**
     * 是否删除 0否 1是
     */
    private Integer deleted;
    /**
     * 删除原因
     */
    private String deletionReason;
    /**
     * 删除明细
     */
    private String deleteDetails;
    /**
     * 误报原因
     */
    private String falseAlarmCause;
    /**
     * 是否误报 0否 1是
     */
    private String falseAlarm;
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
    /**
     * 轨迹有效性
     */
    private String trajectoryEffectiveness;
    /**
     * 时户数
     */
    private Integer householdsNumber;
    /**
     * 是否自动派单
     */
    private String automaticDispatch;

}

