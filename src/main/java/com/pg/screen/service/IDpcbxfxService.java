package com.pg.screen.service;

import com.pg.screen.mapper.dto.DpcbxfxDto;
import com.pg.screen.mapper.dto.DpcbxfxParam;

import java.util.List;

/**
 * <p>
 * 多频次保修分析 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-07-25
 */
public interface IDpcbxfxService  {

    /**
     * 查询列表-线路多频次报修
     * @param queryParams
     * @return
     */

    public List<DpcbxfxDto> getssxlpage(DpcbxfxParam queryParams);


    /**
     * 查询列表-台区多频次报修
     * @param queryParams
     * @return
     */
    public List<DpcbxfxDto> getsstqpage(DpcbxfxParam queryParams);


    /**
     * 查询列表-站所重复报修
     * @param queryParams
     * @return
     */
    public List<DpcbxfxDto> getsszspage(DpcbxfxParam queryParams);




}
