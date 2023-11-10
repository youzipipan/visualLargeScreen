package com.pg.screen.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.dto.ZdgzgzfxDto;
import com.pg.screen.mapper.dto.ZdgzgzfxParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 重点关注故障分析 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-07-25
 */
@Mapper
public interface ZdgzgzfxMapper extends BaseMapper<ZdgzgzfxDto> {

    /**
     * 查询列表
     * @param queryParams
     * @return
     */
    List<ZdgzgzfxDto> getpage(@Param("page") Page<ZdgzgzfxDto> page, @Param("queryParams") ZdgzgzfxParam queryParams);

    Integer createData(@Param("queryParams") ZdgzgzfxParam queryParams);

}
