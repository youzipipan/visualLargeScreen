package com.pg.screen.mapper.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ZdgzgzfxParam extends BasePageQuery {
    /**
     * 供电单位
     */
    private List<String> GDDW;
    /**
     * 故障类型
     */
    private List<String> GZLX;
    /**
     * 故障原因
     */
    private List<String> GZYYDL;
    /**
     * 是否通知
     */
    private Date trueFalseTZ;
    /**
     * 是否生成督办
     */
    private Date trueFalseDb;
    /**
     * 发生时间起
     */
    private Date startDate;
    /**
     * 发生时间结束
     */
    private Date endDate;
    /**
     * 统计时间
     */
    private Date tjDate;
}
