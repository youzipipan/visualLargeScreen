package com.pg.screen.mapper.entity;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 故障——主动抢修
 * </p>
 *
 * @author 作者
 * @since 2023-07-16
 */

@Table(value = "GZ_ZDQX")
@Data
public class FaultRepair implements Serializable{

    private static final long serialVersionUID = 1L;

      /**
     * 任务名
     */
    private String name;

      /**
     * 负责人
     */
    private Long coverUser;

      /**
     * 状态: 1未接单、2进行中、3已完成、4已超期、 5待审核、6未派发
     */
    private String flag;

      /**
     * 台区编号
     */
    private String tgNo;

      /**
     * 地区编码
     */
    private String orgNo;

      /**
     * 修复前照片
     */
      
    private Long qqFileId;

      /**
     * 工单编号
     */
      
    private Long appNo;

      /**
     * 修复后照片
     */
      
    private Long hqFileId;

      /**
     * 故障发生时间
     */
      
    private LocalDateTime gzfssj;

      /**
     * 故障地址
     */
      
    private String gzdz;

      /**
     * 故障类型 1.欠费停电、2.计划停电、3.线路故障停电 4.台区停电、5.多户停电(是)
     */
      
    private String gzlx;

      /**
     * 联系人
     */
      
    private String lxr;

      /**
     * 联系电话
     */
      
    private Long lxdh;

      /**
     * 工单描述
     */
      
    private String gdms;

      /**
     * 抢修记录
     */
      
    private String qxjl;

      /**
     * 到达现场时间
     */
      
    private LocalDateTime ddxcsj;

      /**
     * 抢修完成时间
     */
      
    private LocalDateTime qxwcsj;

      /**
     * 接单时间（进行中）
     */
      
    private LocalDateTime jdsj;

      /**
     * 归档时间（审核通过）
     */
      
    private LocalDateTime gdsj;

      /**
     * 抢修时长
     */
      
    private String qxsc;

      /**
     * 审核人
     */
      
    private String shr;

      /**
     * 接单人
     */
      
    private String jdr;

      /**
     * 供电单位
     */
      
    private String gddw;


}
