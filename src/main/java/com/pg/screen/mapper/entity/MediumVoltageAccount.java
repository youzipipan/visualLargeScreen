package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中压用户(MediumVoltageAccount)实体类
 *
 * @author makejava
 * @since 2023-06-12 13:43:09
 */
@Data
@Table(value = "MEDIUM_VOLTAGE_ACCOUNT")
public class MediumVoltageAccount implements Serializable {

    private static final long serialVersionUID = -59308158948051066L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 变压器类型;1 公变 2 专变
     */
    private String transformerType;
    /**
     * 数据异常说明
     */
    private String dataExceptionDescription;
    /**
     * 数据状态;1.正常 2.修改审核中 3.变更审核中 4,退役审核中 5. 注销审核中
     */
    private String dataStatus;
    /**
     * 所属地市名称
     */
    private String city;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 所属线路编码
     */
    private String lineCode;
    /**
     * 所属线路名称
     */
    private String lineName;
    /**
     * 所属线段编码
     */
    private String lineSegmentCode;
    /**
     * 所属线段名称
     */
    private String lineSegmentName;
    /**
     * 单位编码
     */
    private String companyCode;
    /**
     * 单位性质;1 国网 2 国网代管
     */
    private String companyNature;
    /**
     * 线路性质;1 公用 2 专用
     */
    private String lineNature;
    /**
     * 用户性质;1 公用 2 专用
     */
    private String userNature;
    /**
     * 电压等级
     */
    private String voltageLevel;
    /**
     * 管理属性;1 城区 2 县公司
     */
    private String managementAttribute;
    /**
     * 变压器容量
     */
    private String transformerCapacity;
    /**
     * 变压器台数
     */
    private String transformerNumber;
    /**
     * 地区特征;1城镇 2农村 3市区 4 市中心
     */
    private String regionalism;
    /**
     * 规划特征;1 A  2 A+ 3 B 4 C 5 D 6 E
     */
    private String identityPlan;
    /**
     * 低压用户数
     */
    private String lowNumberOfUsers;
    /**
     * 是否双电源;0否 1是
     */
    private String dualPower;
    /**
     * 双电源容量
     */
    private String dualPowerCapacity;
    /**
     * 总容量
     */
    private String totalCapacity;
    /**
     * 专用设备容量
     */
    private String dedicatedEquipmentCapacity;
    /**
     * 专用设备台数
     */
    private String numberOfDedicatedEquipment;
    /**
     * 载容比
     */
    private String loadCapacityRatio;
    /**
     * 统计用区划代码
     */
    private String statisticalZoningCode;
    /**
     * 统计用区划名称
     */
    private String statisticalZoningName;
    /**
     * 城乡分类代码
     */
    private String urbanAndRuralClassificationCode;
    /**
     * 城乡分类名称
     */
    private String urbanAndRuralClassification;
    /**
     * 注册日期
     */
    private Date registrationDate;
    /**
     * 投运日期
     */
    private Date commissionDate;
    /**
     * 注销日期
     */
    private Date logoutDate;
    /**
     * 退役日期
     */
    private Date retirementDate;


}

