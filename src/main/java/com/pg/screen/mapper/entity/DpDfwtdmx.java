package com.pg.screen.mapper.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 大范围停电明细表(DpDfwtdmx)实体类
 *
 * @author makejava
 * @since 2023-07-08 15:57:32
 */
@Data
@Table("DP_DFWTDMX")
public class DpDfwtdmx implements Serializable {

    private static final long serialVersionUID = -29210830767657586L;
    
    private Integer id;
    /**
     * 供电单位
     */
    private String gddw;
    /**
     * 停电时间
     */
    private Date tdsj;
    /**
     * 复电时间
     */
    private Date fdsj;
    /**
     * 变电站
     */
    private String bdz;
    /**
     * 线路
     */
    private String xl;
    /**
     * 时户数
     */
    private String shs;
    /**
     * 停电性质
     */
    private String tdxz;

}

