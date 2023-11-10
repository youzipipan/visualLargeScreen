package com.pg.screen.mapper.dto;



import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class PftdFaultDetailPageQuery extends BasePageQuery {

    /**
     * 供电单位
     */
    private String countyCorporation;

    /**
     * 供电单位查询集合
     */
    private List<String> countyCorporationList;

    /**
     * 事件 发起
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventTimeStart;

    private Date eventTimeStartData;

    /**
     * 事件时间 截止
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventTimeEnd;

    private Date eventTimeEndData;

    /**
     * 是否通知
     */
    private String isNotify;

    /**
     * 是否生成督办单
     */
    private String isForm;

    /**
     * 两月三次(1)或全年五次(2)
     */
    private String eventType;

    public void setCountyCorporation(String countyCorporation) {
        this.countyCorporation = countyCorporation;
        this.countyCorporationList = StrUtil.split(StrUtil.blankToDefault(countyCorporation, null), StrUtil.COMMA);
    }

    /**
     * 故障类型
     */
    private List<String> faultType;

    /**
     * 线路名称
     */
    private String powerfailurelinename;

}
