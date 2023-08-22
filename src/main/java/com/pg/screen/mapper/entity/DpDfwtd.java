package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 中压大范围停电分析(DpDfwtd)实体类
 *
 * @author makejava
 * @since 2023-07-08 16:14:03
 */
@Data
@Table("DP_DFWTD")
public class DpDfwtd implements Serializable {

    private static final long serialVersionUID = 808571959544966938L;
    
    private Long id;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 单位编码
     */
    private String dwbh;
    /**
     * 起始时间
     */
    private Date qssj;
    /**
     * 终止时间
     */
    private Date zzsj;
    /**
     * 地区特征
     */
    private String dqtz;
    /**
     * 地区特征
     */
    private String ghtz;
    /**
     * 线路性质
     */
    private String xlxz;
    /**
     * 实际用户数
     */
    private String sjyhs;
    /**
     * 大范围停电次数
     */
    private String dfwtdcs;
    /**
     * 大范围停电时户数
     */
    private String dfwtdshs;
    /**
     * 等效总用户数
     */
    private String dxzyhs;
    /**
     * 停电总时户数
     */
    private String tdzshs;
    /**
     * 占停电总时户数比例
     */
    private String shsbl;

}

