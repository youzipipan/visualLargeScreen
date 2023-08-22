package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 供电可靠性总表(DpGdkkxZb)实体类
 *
 * @author makejava
 * @since 2023-07-07 18:57:16
 */
@Table("DP_GDKKX_ZB")
@Data
public class DpGdkkxZb implements Serializable {

    private static final long serialVersionUID = -87311510057562026L;
    
    private Long id;
    /**
     * 所属地市

     */
    private String ssds;
    /**
     * 所属区县
     */
    private String ssxq;
    /**
     * 所属区县 管理属性
     */
    private String glsx;
    /**
     * 统计期间小时

     */
    private String tjsj;
    /**
     * 统计期间天数
     */
    private String tjts;
    /**
     * 起始时间

     */
    private Date qssj;
    /**
     * 终止时间

     */
    private Date zzsj;
    /**
     * 电压等级

     */
    private String dydj;
    /**
     * 规划特征

     */
    private String ghtz;
    /**
     * 线路性质

     */
    private String xlxz;
    /**
     * 单位性质

     */
    private String dwxz;
    /**
     * 系统平均停电时间SAIDI-1

     */
    private String pjtdsj;
    /**
     * 平均供电可靠率ASAI-1

     */
    private String pjgdsj;
    /**
     * 平均供电可靠率ASAI-1
等效总用户数

     */
    private String dxzyhs;
    /**
     * 停电总时户数

     */
    private String tdzshs;
    /**
     * 地区特征

     */
    private String dqtz;
    /**
     * 系统平均预安排停电时间SAIDI-S

     */
    private String pjytdsj;
    /**
     * 系统平均故障停电时间SAIDI-F

     */
    private String pjgztdsj;

}

