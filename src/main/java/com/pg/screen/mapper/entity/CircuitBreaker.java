package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author c.chuang
 */
@Table(value = "CIRCUIT_BREAKER")
@Data
public class CircuitBreaker implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编码
     */
    private String deviceCoding;

    /**
     * 运行编号
     */
    private Long runNumber;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 所属分段线路
     */
    private String owningSegmentLine;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    /**
     * 所属杆塔
     */
    private String owningTower;

    /**
     * 所属区域
     */
    private String owningRegion;

    /**
     * 所属大馈线
     */
    private String belongsToTheBigFeeder;

    /**
     * 资产性质
     */
    private String natureOfAssets;

    /**
     * 资产单位
     */
    private String assetUnit;

    /**
     * 所属地市
     */
    private String localMunicipality;

    /**
     * 大馈线支线
     */
    private String largeFeederFeeder;

    /**
     * 出厂编号
     */
    private Long factoryNumber;

    /**
     * 出厂日期
     */
    private Date factoryDate;

    /**
     * 运维单位
     */
    private String operationAndMaintenanceUnit;

    /**
     * 维护班组
     */
    private String maintenanceCrew;

    /**
     * 主线名称
     */
    private String mainLineName;

    /**
     * 设备状态
     */
    private String deviceStatus;

    /**
     * 制造国家
     */
    private String manufacturingCountry;

    /**
     * 是否代维
     */
    private String replaceOrNot;

    /**
     * 型号
     */
    private Long modelNumber;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 投运日期
     */
    private Date dateOfCommissioning;

    /**
     * 资产编号
     */
    private String assetNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 设备主人
     */
    private String equipmentOwner;

    /**
     * 专业分类
     */
    private String professionalClassification;

    /**
     * 电压等级名称
     */
    private String voltageClassName;

    /**
     * 登记时间
     */
    private Date registrationTime;

    /**
     * 所属大馈线ID
     */
    private String idOfALargeFeeder;

}