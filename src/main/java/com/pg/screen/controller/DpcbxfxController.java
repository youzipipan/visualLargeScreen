package com.pg.screen.controller;

import com.pg.screen.common.HttpResult;
import com.pg.screen.mapper.dto.DpcbxfxDto;
import com.pg.screen.mapper.dto.DpcbxfxParam;
import com.pg.screen.service.IDpcbxfxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 多频次保修分析
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/dpcbxfx")
public class DpcbxfxController  {

    @Autowired
    IDpcbxfxService iDpcbxfxService;



    /**
     * 查询列表-线路多频次报修
     * @param queryParams
     * @return
     */
    @GetMapping("/ssxlpage")
    public HttpResult<List<DpcbxfxDto>> getssxlpage(DpcbxfxParam queryParams) {
//        return PageResult.success(iDpcbxfxService.getssxlpage(queryParams));
        return  new HttpResult<List<DpcbxfxDto>>().success(iDpcbxfxService.getssxlpage(queryParams));
    }


    /**
     * 查询列表-台区多频次报修
     * @param queryParams
     * @return
     */
    @GetMapping("/sstqpage")
    public HttpResult<List<DpcbxfxDto>> getsstqpage(DpcbxfxParam queryParams) {
//        return PageResult.success(iDpcbxfxService.getsstqpage(queryParams));
        return  new HttpResult<List<DpcbxfxDto>>().success(iDpcbxfxService.getsstqpage(queryParams));
    }


    /**
     * 查询列表-站所重复报修
     * @param queryParams
     * @return
     */
    @GetMapping("/sszspage")
    public HttpResult<List<DpcbxfxDto>> getsszspage(DpcbxfxParam queryParams) {
//        return PageResult.success(iDpcbxfxService.getsszspage(queryParams));
        return  new HttpResult<List<DpcbxfxDto>>().success(iDpcbxfxService.getsszspage(queryParams));
    }


}
