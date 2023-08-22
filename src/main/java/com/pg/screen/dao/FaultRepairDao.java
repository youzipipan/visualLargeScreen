package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.FaultRepairMapper;
import com.pg.screen.mapper.entity.FaultRepair;
import com.pg.screen.model.vo.FaultRepairCountVO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FaultRepairDao {

    private final String currentMonthSql = "TO_CHAR(GZFSSJ,'YYYY-MM') = TO_CHAR(SYSDATE,'YYYY-MM')";

    @Resource
    private FaultRepairMapper faultRepairMapper;

    public QueryWrapper createQueryWrapper() {
        return QueryWrapper
                .create()
                .from("GZ_ZDQX");
    }

    public FaultRepairCountVO findRepairCount() {
        final FaultRepairCountVO faultRepairVO = new FaultRepairCountVO();
        faultRepairVO.setSbtdsjCount(Db.selectCountByQuery(createQueryWrapper().where(currentMonthSql)));
        faultRepairVO.setZnypCount(faultRepairVO.getSbtdsjCount());
        faultRepairVO.setFhjzqxtjCount(Db.selectCountByQuery(createQueryWrapper().where(currentMonthSql + " AND GZLX = 5")));
        final StringBuilder builder = new StringBuilder(currentMonthSql);
        for (int index = 1; index <= 4; index++) {
            if (index == 1) {
                builder.append(" AND (GZLX =  " + index);
            } else {
                builder.append(" OR GZLX = " + index);
            }
        }
        builder.append(")");
        faultRepairVO.setBfhjzqxtjCount(Db.selectCountByQuery(createQueryWrapper().where(builder.toString())));
        return faultRepairVO;
    }

    public List<Row> findFaultRepairFlagCount(String type) {
        final StringBuilder sqlBuilder = new StringBuilder("SElECT FLAG, COUNT(FLAG) AS COUNT FROM GZ_ZDQX WHERE ")
                .append(currentMonthSql);
        if ("3".equals(type)) {
            sqlBuilder.append(" AND GZLX = 5");
        } else if ("4".equals(type)) {
            sqlBuilder.append("AND (");
            for (int index = 1; index <= 4; index++) {
                sqlBuilder.append("GZLX = " + index);
                if (index != 4) {
                    sqlBuilder.append(" OR ");
                }
            }
            sqlBuilder.append(")");
        }
        sqlBuilder.append(" GROUP BY FLAG");
        return Db.selectListBySql(sqlBuilder.toString());
    }

    public List<Row> selectList(String flag, String type) {
        final StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM GZ_ZDQX WHERE ")
                .append(currentMonthSql)
                .append(" AND FLAG = ? ");
        if ("3".equals(type)) {
            sqlBuilder.append("AND GZLX = 5");
        } else if ("4".equals(type)) {
            sqlBuilder.append("AND (");
            for (int index = 1; index <= 4; index++) {
                sqlBuilder.append("GZLX = " + index);
                if (index != 4) {
                    sqlBuilder.append(" OR ");
                }
            }
            sqlBuilder.append(")");
        }
        return Db.selectListBySql(sqlBuilder.toString(), flag);
    }
}
