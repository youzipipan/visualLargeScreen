package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 缺陷库表(DefectLibrary)实体类
 *
 * @author makejava
 * @since 2023-07-07 14:14:41
 */
@Data
@Table("DEFECT_LIBRARY")
public class DefectLibrary implements Serializable {

    private static final long serialVersionUID = -71138771261605850L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 工单时限
     */
    private String workOrderTime;
    /**
     * 工单单号
     */
    private String workOrderNumber;
    /**
     * 工单状态
     */
    private String workOrderStatus;
    /**
     * 工单单位
     */
    private String workOrderUnit;
    /**
     * 作业班组
     */
    private String operationTeam;
    /**
     * 问题类型
     */
    private String problemType;
    /**
     * 缺陷等级
     */
    private String defectGrade;
    /**
     * 缺陷内容
     */
    private String defectiveContent;
    /**
     * 所属变电站
     */
    private String substation;
    /**
     * 所属线路
     */
    private String owningLine;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 实物ID编号
     */
    private String physicalIdNumber;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 是否家族缺陷
     */
    private String familyDefect;
    /**
     * 拟采取方案
     */
    private String proposedScheme;
    /**
     * 任务来源
     */
    private String taskSource;
    /**
     * 发现时间
     */
    private Date discoveryTime;
    /**
     * 处理时间
     */
    private Date processingTime;
    /**
     * 处理描述
     */
    private String processDescription;

}

