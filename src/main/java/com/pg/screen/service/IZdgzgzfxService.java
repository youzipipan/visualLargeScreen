package com.pg.screen.service;

import com.pg.screen.mapper.dto.ZdgzgzfxDto;
import com.pg.screen.mapper.dto.ZdgzgzfxParam;

import java.util.List;

/**
 * <p>
 * 重点关注故障分析 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-07-25
 */
public interface IZdgzgzfxService  {

    /**
     * 查询列表
     * @param queryParams
     */
    List<ZdgzgzfxDto> getpage(ZdgzgzfxParam queryParams);



}
