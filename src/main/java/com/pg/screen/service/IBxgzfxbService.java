package com.pg.screen.service;

import com.pg.screen.mapper.dto.BxgzfxbParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 保修故障维修分析表 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-07-25
 */
public interface IBxgzfxbService {



    /**
     * 导出统计
     * @return
     */
    String printTotal(BxgzfxbParam queryParams, HttpServletResponse response, HttpServletRequest request);
}
