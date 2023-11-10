package com.pg.screen.mapper.dto;

import com.pg.screen.mapper.entity.FaultDetail;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class GddwXlcd  {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private String gddw;

    private String xlcd;


}