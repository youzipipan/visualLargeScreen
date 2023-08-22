package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中压线段(MediumVoltageLineSegment)实体类
 *
 * @author makejava
 * @since 2023-06-12 13:43:12
 */
@Data
@Table(value = "MEDIUM_VOLTAGE_LINE_SEGMENT")
public class MediumVoltageLineSegment implements Serializable {

    private static final long serialVersionUID = -66594514074401305L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 所属地市
     */
    private String city;
    /**
     * 所属区县
     */
    private String county;
    /**
     * 单位编码
     */
    private String companyCode;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 线段编码
     */
    private String lineSegmentCode;
    /**
     * 线段名称
     */
    private String lineSegmentName;
    /**
     * 线路编码
     */
    private String lineCode;
    /**
     * 线路名称
     */
    private String lineName;
    /**
     * 线路性质;1 公用 2 专用
     */
    private String lineNature;
    /**
     * 单位性质;1 国网 2 国网代管
     */
    private String companyNature;
    /**
     * 投运日期
     */
    private Date commissionDate;
    /**
     * 注册日期
     */
    private Date registrationDate;
    /**
     * 注销日期
     */
    private Date logoutDate;
    /**
     * 管理属性;1 城区 2 县公司
     */
    private String managementAttribute;
    /**
     * 地区特征;1城镇 2农村 3市区 4 市中心
     */
    private String regionalism;
    /**
     * 电压等级
     */
    private String voltageLevel;
    /**
     * 规划特征;1 A  2 A+ 3 B 4 C 5 D 6 E
     */
    private String identityPlan;

}

