package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.dto.PftdDetailDto;
import com.pg.screen.mapper.dto.PftdFaultDetailPageQuery;
import com.pg.screen.service.IPftdFaultDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-07-30
 */
@RestController
@RequestMapping("pftd-fault-detail")
public class PftdFaultDetailController  {

    @Resource
    private IPftdFaultDetailService iPftdFaultDetailService;

    @GetMapping("page")
    public HttpResult<List<PftdDetailDto>> pageForPftdFaultDetail(PftdFaultDetailPageQuery pageQuery) {

        return  new HttpResult<List<PftdDetailDto>>().success(iPftdFaultDetailService.pageForPftdFaultDetail(pageQuery));
    }


}
