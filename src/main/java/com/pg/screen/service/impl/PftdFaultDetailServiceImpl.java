package com.pg.screen.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.PftdFaultDetailMapper;
import com.pg.screen.mapper.dto.PftdDetailDto;
import com.pg.screen.mapper.dto.PftdFaultDetailPageQuery;
import com.pg.screen.mapper.entity.FaultDetail;
import com.pg.screen.service.IPftdFaultDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-07-30
 */
@Service
public class PftdFaultDetailServiceImpl implements IPftdFaultDetailService {


    @Autowired
    private PftdFaultDetailMapper pftdFaultDetailMapper;


    @Override
    public List<PftdDetailDto> pageForPftdFaultDetail(PftdFaultDetailPageQuery pageQuery) {

        if(pageQuery.getEventTimeStart()!=null){
            Instant instant1 = pageQuery.getEventTimeStart().atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
            pageQuery.setEventTimeStartData(Date.from(instant1));
        }
        if(pageQuery.getEventTimeStart()!=null) {
            Instant instant2 = pageQuery.getEventTimeEnd().atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
            pageQuery.setEventTimeEndData(Date.from(instant2));
        }


        List<PftdDetailDto> list =new ArrayList<>();
        Page<PftdDetailDto> page =new Page<>();
        if(pageQuery.getPageNum()==null){
            list = pftdFaultDetailMapper.getpage(null, pageQuery);
        }
        else{
            page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
            list = pftdFaultDetailMapper.getpage(page, pageQuery);
        }
        for(PftdDetailDto item:list){
            pageQuery.setCountyCorporation(item.getCountryCorporation());
            pageQuery.setPowerfailurelinename(item.getPowerFailureLineName());
            List<FaultDetail> faultDetails=pftdFaultDetailMapper.getpageDetail(null,pageQuery);
            item.setFaultDetailList(faultDetails);
            if(faultDetails.size()>0){
                item.setEventTime(faultDetails.get(0).getEventTime());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                item.setTdscTimeStr(sdf .format(faultDetails.get(0).getEventTime()));
            }
        }
        return list;
    }



}
