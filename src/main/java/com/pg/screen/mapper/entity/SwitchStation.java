package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author c.chuang
 */
@Table(value = "SWITCH_STATION")
@Data
public class SwitchStation implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 运维单位
     */
    private String operationAndMaintenanceUnit;

    /**
     * 所属电站
     */
    private String owningPowerStation;

    /**
     * 站内开关名称
     */
    private String stationSwitchName;

    /**
     * 所属大馈线
     */
    private String belongsToTheBigFeeder;

    /**
     * 大馈线支线
     */
    private String largeFeederFeeder;

    /**
     * 维护班组
     */
    private String maintenanceCrew;

    /**
     * 投运日期
     */
    private Date dateOfCommissioning;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 设备主人
     */
    private String equipmentOwner;

    /**
     * 是否代维
     */
    private String replaceOrNot;

    /**
     * 专业分类
     */
    private String professionalClassification;

    /**
     * 设备编码
     */
    private String deviceCoding;
}