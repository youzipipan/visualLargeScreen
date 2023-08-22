package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中压线路(MediumVoltageLine)实体类
 *
 * @author makejava
 * @since 2023-06-12 13:43:12
 */
@Data
@Table(value = "MEDIUM_VOLTAGE_LINE")
public class MediumVoltageLine implements Serializable {

    private static final long serialVersionUID = -98212109378511079L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 所属地市名称
     */
    private String city;
    /**
     * 所属区县名称
     */
    private String county;
    /**
     * 运维班组名称
     */
    private String operation;
    /**
     * 线路编码
     */
    private String lineCode;
    /**
     * 线路名称
     */
    private String lineName;
    /**
     * 投运日期
     */
    private Date commissionDate;
    /**
     * 注销日期
     */
    private Date logoutDate;
    /**
     * 注册日期
     */
    private Date registrationDate;
    /**
     * 退役日期
     */
    private Date retirementDate;
    /**
     * 配网线路名称
     */
    private String distributionNetworkLine;
    /**
     * 电压等级
     */
    private String voltageLevel;
    /**
     * 短时载流量
     */
    private String shortTermAmpacity;
    /**
     * 所属变电站
     */
    private String transformerSubstation;
    /**
     * 专业分类
     */
    private String professionalClassification;
    /**
     * 地区特征
     */
    private String regionalism;
    /**
     * 公用户次
     */
    private String publicAccount;
    /**
     * 专用户次
     */
    private String dedicatedAccount;
    /**
     * 城市用户数
     */
    private String cityUserNumber;
    /**
     * 农村用户数
     */
    private String villageUserNumber;
    /**
     * 架空线路
     */
    private String overheadLines;
    /**
     * 电缆线路
     */
    private String cableLines;
    /**
     * 线路合计
     */
    private String lineTotal;
    /**
     * 架设方式
     */
    private String erectionMode;
    /**
     * 供电区域
     */
    private String powerSupplyArea;
    /**
     * 设备密度
     */
    private String equipmentDensity;
    /**
     * 绝缘化率
     */
    private String insulateRate;

}

