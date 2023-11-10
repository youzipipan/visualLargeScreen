package com.pg.screen.mapper.dto;

import com.pg.screen.mapper.entity.FaultDetail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class PftdDetailDto extends FaultDetail {

    private String rownum;

//    private String id;

    private String countryCorporation;

    private String powerFailureLineName;

    private String eventCount;

    private Date eventTime;

    private String tdscTimeStr;

    private List<FaultDetail> faultDetailList;

    private String eventTimeStr;

    private String recoverElectricityTimeStr;
}
