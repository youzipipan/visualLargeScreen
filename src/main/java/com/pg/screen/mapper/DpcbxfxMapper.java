package com.pg.screen.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.dto.DpcbxfxDto;
import com.pg.screen.mapper.dto.DpcbxfxParam;
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
public interface DpcbxfxMapper extends BaseMapper<DpcbxfxDto> {


    /**
     * 查询列表-线路多频次报修
     * @param queryParams
     * @return
     */

    List<DpcbxfxDto> getssxlpage(@Param("page") Page<DpcbxfxDto> page, @Param("queryParams") DpcbxfxParam queryParams);

    /**
     * 查询列表-台区多频次报修
     * @param queryParams
     * @return
     */
    List<DpcbxfxDto> getsstqpage(@Param("page") Page<DpcbxfxDto> page, @Param("queryParams") DpcbxfxParam queryParams);

    /**
     * 查询列表-站所重复报修
     * @param queryParams
     * @return
     */
    List<DpcbxfxDto> getsszspage(@Param("page") Page<DpcbxfxDto> page, @Param("queryParams") DpcbxfxParam queryParams);


}
