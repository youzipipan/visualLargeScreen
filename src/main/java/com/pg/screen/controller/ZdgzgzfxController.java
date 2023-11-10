package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.dto.ZdgzgzfxDto;
import com.pg.screen.mapper.dto.ZdgzgzfxParam;
import com.pg.screen.service.IZdgzgzfxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 重点关注故障分析
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/zdgzgzfx")
public class ZdgzgzfxController {

    @Autowired
    IZdgzgzfxService iZdgzgzfxService;

    /**
     * 查询列表
     * @param queryParams
     * @return
     */
    @GetMapping("/page")
    public HttpResult<List<ZdgzgzfxDto>> getpage(ZdgzgzfxParam queryParams) {
//        return PageResult.success();
        return  new HttpResult<List<ZdgzgzfxDto>>().success(iZdgzgzfxService.getpage(queryParams));
    }




}
