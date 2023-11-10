package com.pg.screen.mapper.dto;

import lombok.Data;


@Data
public class DpcbxfxDto extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 统计维度
     */
    private String gddw;

    private String name;
    /**
     * 统计指标1
     */
    private int num;


}