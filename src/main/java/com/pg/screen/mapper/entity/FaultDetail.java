package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 故障明细表(FaultDetail)实体类
 *
 * @author makejava
 * @since 2023-06-12 15:20:06
 */
@Data
@Table(value = "FAULT_DETAIL")
public class FaultDetail implements Serializable {

    private static final long serialVersionUID = 244454562953595497L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 市公司
     */
    private String cityCompany;
    /**
     * 县公司
     */
    private String countyCorporation;
    /**
     * 超时情况
     */
    private String timeoutCase;
    /**
     * 事件序号
     */
    private Integer eventSequenceNumber;
    /**
     * 事件时间
     */
    private Date eventTime;
    /**
     * 复电时间
     */
    private Date recoverElectricityTime;
    /**
     * 专用停电数
     */
    private Integer specificPowerFailureNumber;
    /**
     * 公用停电数
     */
    private Integer publicPowerFailureNumber;
    /**
     * 总停电数
     */
    private Integer totalPowerFailureNumber;
    /**
     * 总用户数
     */
    private Integer totalUserNumber;
    /**
     * 停电占比(%)
     */
    private Double powerFailurePercentage;
    /**
     * 修复时长
     */
    private Double repairTime;
    /**
     * 报告状态
     */
    private String reportStatus;
    /**
     * 停电线路名称
     */
    private String powerFailureLineName;
    /**
     * 故障类型
     */
    private String faultType;
    /**
     * 故障原因
     */
    private String faultCause;
    /**
     * 故障原因二级类目
     */
    private String faultCause2Category;
    /**
     * 线路管理属性
     */
    private String lineManagementAttribute;
    /**
     * 线路投运时间
     */
    private Date lineOperationTime;
    /**
     * 生产厂家
     */
    private String manufacturer;
    /**
     * 更换厂家
     */
    private String changeManufacturer;
    /**
     * 停电部位
     */
    private String powerFailureSite;
    /**
     * 故障情况
     */
    private String faultSituation;
    /**
     * 故障处理经过及原因
     */
    private String troubleshootingProcessAndCause;
    /**
     * 下一步措施
     */
    private String nextStep;
    /**
     * 填报人
     */
    private String createUser;
    /**
     * 填报时间
     */
    private Date createTime;
    /**
     * 上报时间
     */
    private Date reportingTime;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 标记状态
     */
    private String markerState;

}

