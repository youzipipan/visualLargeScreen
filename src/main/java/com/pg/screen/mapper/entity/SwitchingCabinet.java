package com.pg.screen.mapper.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author c.chuang
 */
@Table(value = "SWITCHING_CABINET")
@Data
public class SwitchingCabinet implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 变电站名称
     */
    private String substationName;

    /**
     * 所属地市
     */
    private String localMunicipality;

    /**
     * 运维单位
     */
    private String operationAndMaintenanceUnit;

    /**
     * 维护班组
     */
    private String maintenanceCrew;

    /**
     * 资产性质
     */
    private String natureOfAssets;

    /**
     * 资产单位
     */
    private String assetUnit;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 设备状态
     */
    private String deviceStatus;

    /**
     * 投运日期
     */
    private Date dateOfCommissioning;

    /**
     * 电站类型
     */
    private String typeOfPowerStation;

    /**
     * 供电区域
     */
    private String powerSupplyArea;

    /**
     * 是否智能变电站
     */
    private String smartSubstationOrNot;

    /**
     * 是否枢纽站
     */
    private String hubOrNot;

    /**
     * 变电站重要级别
     */
    private String substationImportanceLevel;

    /**
     * 是否代维
     */
    private String replaceOrNot;

    /**
     * 是否农网
     */
    private String whetherRuralNetwork;

    /**
     * 值班方式
     */
    private String dutyMode;

    /**
     * 布置方式
     */
    private String arrangementMode;

    /**
     * 污秽等级
     */
    private String pollutionGrade;

    /**
     * 站址
     */
    private String siteLocation;

    /**
     * 占地面积(M2)
     */
    private String floorArea;

    /**
     * 建筑面积(M2)
     */
    private String buildingArea;

    /**
     * 海拔(M)
     */
    private String altitude;

    /**
     * 地区特征
     */
    private String regionalCharacteristics;

    /**
     * 退运日期
     */
    private Date returnDate;

    /**
     * 联系电话
     */
    private Long contactNumber;

    /**
     * WBS编码
     */
    private String wbsCoding;

    /**
     * 变电站简称
     */
    private String shortForSubstation;

    /**
     * 最高调度管辖权
     */
    private String supremeDispatchingJurisdiction;

    /**
     * 是否满足N-1
     */
    private String whetherNOneIsSatisfied;

    /**
     * 是否接入AVC
     */
    private String whetherToAccessAvc;

    /**
     * 是否接入故障信息远传系统
     */
    private String whetherToConnectTheRemoteFaultInformationTransmissionSystem;

    /**
     * 工程名称
     */
    private String projectName;

    /**
     * 是否集中监控
     */
    private String centralizedMonitoringOrNot;

    /**
     * 消防验收情况
     */
    private String fireControlAcceptance;

    /**
     * 设备编码
     */
    private String deviceCoding;

    /**
     * 消防类型
     */
    private String fireProtectionType;

    /**
     * 登记时间
     */
    private Date registrationTime;

    /**
     * 设备主人
     */
    private String equipmentOwner;

    /**
     * 备注
     */
    private String remark;

    /**
     * 配变总容量(KVA)
     */
    private String totalCapacity;

    /**
     * 换流站类型
     */
    private String converterStationType;

    /**
     * 所属直流系统
     */
    private String owningDcSystem;

    /**
     * 防误方式
     */
    private String errorProofSystem;

    /**
     * 是否独立建筑物
     */
    private String independentBuildingOrNot;

    /**
     * 直流输送容量(MVA)
     */
    private String dcTransferCapacity;

    /**
     * 无功补偿容量(KVAR)
     */
    private String reactivePowerCompensationCapacity;

    /**
     * 箱变类型
     */
    private String boxVariantType;

    /**
     * 是否具有环网
     */
    private String whetherTheRingNetworkIsConfigured;

    /**
     * 站房ID
     */
    private String stationId;

    /**
     * 专业分类
     */
    private String professionalClassification;

    /**
     * PM编码
     */
    private String pmCoding;

    /**
     * 资产编号
     */
    private String assetNumber;

    /**
     * 配变台数
     */
    private BigDecimal theNumberOfDifferentStations;

    /**
     * 重要程度
     */
    private String degreeOfImportance;

    /**
     * 是否地下站
     */
    private String undergroundStationOrNot;

    /**
     * 接地电阻(Ω)
     */
    private BigDecimal groundResistance;
}