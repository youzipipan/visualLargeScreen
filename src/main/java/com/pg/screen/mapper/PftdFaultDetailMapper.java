package com.pg.screen.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.dto.PftdDetailDto;
import com.pg.screen.mapper.dto.PftdFaultDetail;
import com.pg.screen.mapper.dto.PftdFaultDetailPageQuery;
import com.pg.screen.mapper.entity.FaultDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-07-30
 */
@Mapper
public interface PftdFaultDetailMapper extends BaseMapper<PftdFaultDetail> {

    List<PftdDetailDto> getExportRelationList();

    List<PftdDetailDto> getpage(@Param("page") Page<PftdDetailDto> page, @Param("queryParams") PftdFaultDetailPageQuery queryParams);

    List<FaultDetail> getpageDetail(@Param("page") Page<FaultDetail> page, @Param("queryParams") PftdFaultDetailPageQuery queryParams);
}
