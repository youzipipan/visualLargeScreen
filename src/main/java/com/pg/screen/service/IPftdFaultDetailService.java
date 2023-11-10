package com.pg.screen.service;

import com.pg.screen.mapper.dto.PftdDetailDto;
import com.pg.screen.mapper.dto.PftdFaultDetailPageQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2023-07-30
 */
public interface IPftdFaultDetailService {

    /**
     * 分页查询频率统计
     * @param pftdFaultDetailPageQuery 分页器
     * @return com.dlstategrid.shujufenxi.pojo.entity
     */
    List<PftdDetailDto> pageForPftdFaultDetail(PftdFaultDetailPageQuery pftdFaultDetailPageQuery);


}
