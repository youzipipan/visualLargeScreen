package com.pg.screen.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.util.StringUtil;
import com.pg.screen.enums.BxcsEnum;
import com.pg.screen.mapper.DpcbxfxMapper;
import com.pg.screen.mapper.dto.DpcbxfxDto;
import com.pg.screen.mapper.dto.DpcbxfxParam;
import com.pg.screen.service.IDpcbxfxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 多频次保修分析 服务实现类
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@Service
@Slf4j
public class DpcbxfxServiceImpl  implements IDpcbxfxService {

    @Autowired
    private DpcbxfxMapper dpcbxfxMapper;



    /**
     * 查询列表-线路多频次报修
     * @param queryParams
     * @return
     */

    @Override
    public List<DpcbxfxDto> getssxlpage(DpcbxfxParam queryParams) {
        //参数处理
//        initParam(queryParams);
        Page<DpcbxfxDto> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<DpcbxfxDto> list = dpcbxfxMapper.getssxlpage(page, queryParams);
        page.setRecords(list);
        return list;
    }

    /**
     * 查询列表-台区多频次报修
     * @param queryParams
     * @return
     */
    @Override
    public List<DpcbxfxDto> getsstqpage(DpcbxfxParam queryParams) {
        //参数处理
//        initParam(queryParams);
        Page<DpcbxfxDto> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<DpcbxfxDto> list = dpcbxfxMapper.getsstqpage(page, queryParams);
        page.setRecords(list);
        return list;
    }

    /**
     * 查询列表-站所重复报修
     * @param queryParams
     * @return
     */
    @Override
    public List<DpcbxfxDto> getsszspage(DpcbxfxParam queryParams) {
        //参数处理
//        initParam(queryParams);
        Page<DpcbxfxDto> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<DpcbxfxDto> list = dpcbxfxMapper.getsszspage(page, queryParams);
        page.setRecords(list);
        return list;
    }



    private void initParam(DpcbxfxParam queryParams){
        queryParams.setStartNum(-1);
        queryParams.setEndNum(-1);
        if(StringUtil.isNotBlank(queryParams.getBXCS())){
            queryParams.setStartNum(BxcsEnum.valueOf(queryParams.getBXCS()).starNum);
            queryParams.setEndNum(BxcsEnum.valueOf(queryParams.getBXCS()).endNum);
        }
    }



}
