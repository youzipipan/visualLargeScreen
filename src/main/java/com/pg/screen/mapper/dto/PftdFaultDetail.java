package com.pg.screen.mapper.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2023-07-30
 */
@Getter
@Setter

public class PftdFaultDetail extends BaseEntity  {

    private static final long serialVersionUID = 1L;



      /**
     * 停电线路名称
     */

    private String powerFailureLineName;

      /**
     * 市公司
     */

    private String cityCompany;

      /**
     * 县公司
     */

    private String countyCorporation;

      /**
     * 生成时间
     */

    private LocalDateTime eventTime;

      /**
     * 停电次数
     */

    private BigDecimal eventCount;

      /**
     * 停电类型（0：全年5次，1：每月三次）
     */

    private BigDecimal eventType;

      /**
     * 是否通知(1是 0否)
     */

    private String isNotify;

      /**
     * 通知时间
     */

    private LocalDateTime notifyTiem;

      /**
     * 通知人
     */

    private String notifyPerson;

      /**
     * 是否生成督办单(1是 0否)
     */

    private String isForm;

      /**
     * 督办单生成时间
     */

    private LocalDateTime formTime;

      /**
     * 督办单处理人
     */

    private String formPerson;


    private LocalDateTime updateTime;


    private String updateUser;


    private LocalDateTime createTime;


    private String createUser;
}
