package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author c.chuang
 */
@Table(value = "FAULT_INDICATOR")
@Data
public class FaultIndicator implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 运行编号
     */
    private String runNumber;

    /**
     * 所属线路
     */
    private String owningLine;

    /**
     * 所属杆塔
     */
    private String owningTower;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 维护班组
     */
    private String maintenanceCrew;

    /**
     * 所属大馈线
     */
    private String belongsToTheBigFeeder;

    /**
     * 类型
     */
    private String type;

    /**
     * 设备编码
     */
    private String deviceCoding;

    /**
     * 投运日期
     */
    private Date dateOfCommissioning;

    /**
     * 型号
     */
    private String modelNumber;

    /**
     * 运维单位
     */
    private String operationAndMaintenanceUnit;
}