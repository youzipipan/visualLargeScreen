package com.pg.screen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.dto.BxgzfxbParam;
import com.pg.screen.mapper.dto.PftdDetailDto;
import com.pg.screen.service.IBxgzfxbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 保修故障维修
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/bxgzfxb")
public class BxgzfxbController  {

    @Autowired
    IBxgzfxbService iBxgzfxbService;


    /**
     * 导出统计
     * @return
     */
    @GetMapping("/printTotal")
    public HttpResult<String> printTotal(BxgzfxbParam queryParams, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        return  new HttpResult<String>().success(iBxgzfxbService.printTotal(queryParams,response,request));
    }


}
