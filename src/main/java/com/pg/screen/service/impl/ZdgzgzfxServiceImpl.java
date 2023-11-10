package com.pg.screen.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.pg.screen.mapper.ZdgzgzfxMapper;
import com.pg.screen.mapper.dto.ZdgzgzfxDto;
import com.pg.screen.mapper.dto.ZdgzgzfxParam;
import com.pg.screen.service.IZdgzgzfxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 重点关注故障分析 服务实现类
 * </p>
 *
 * @author 作者 lxz
 * @since 2023-07-31
 */
@Service
@Slf4j
public class ZdgzgzfxServiceImpl implements IZdgzgzfxService {


    @Autowired
    private ZdgzgzfxMapper zdgzgzfxMapper;

    /**
     * 查询列表
     * @param queryParams
     */
    @Override
    public List<ZdgzgzfxDto> getpage(ZdgzgzfxParam queryParams){
        Page<ZdgzgzfxDto> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<ZdgzgzfxDto> list = zdgzgzfxMapper.getpage(page, queryParams);
        return list;
    }



}
