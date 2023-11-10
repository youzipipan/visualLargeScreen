package com.pg.screen.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.dto.BxgzfxbParam;
import com.pg.screen.mapper.dto.BxgzfxbPrintTotalDto;
import com.pg.screen.mapper.dto.Gzgd95598;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 保修故障维修分析表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-07-25
 */
@Mapper
public interface BxgzfxbMapper extends BaseMapper<Gzgd95598> {



    /**
     * 导出统计
     * @param queryParams
     * @return
     */
    List<BxgzfxbPrintTotalDto> getPrintTotal(@Param("page") Page<Gzgd95598> page, @Param("queryParams") BxgzfxbParam queryParams);


}
