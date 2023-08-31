package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.pg.screen.mapper.entity.table.DpDfwtdmxTableDef.DP_DFWTDMX;
import static com.pg.screen.mapper.entity.table.DpGdkkxZbTableDef.DP_GDKKX_ZB;

/**
 * @author c.chuang
 */
@Repository
public class DpGdkkxZbDao {

    /**
     * 查询累计消耗
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 累计消耗
     */
    public List<Row> selectTdzshs(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(DP_GDKKX_ZB.SSXQ,
                        DP_GDKKX_ZB.TDZSHS,
                        DP_GDKKX_ZB.QSSJ,
                        DP_GDKKX_ZB.ZZSJ)
                .from(DP_GDKKX_ZB)
                .where(DP_GDKKX_ZB.DQTZ
                        .eq("市中心+市区+城镇+农村")
                        .and(DP_GDKKX_ZB.SSXQ.isNotNull()));
        if (beginDate != null) {
            queryWrapper
                    .and(DP_GDKKX_ZB.QSSJ.ge(beginDate))
                    .and(DP_GDKKX_ZB.ZZSJ.le(endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

    /**
     * 查询大范围停电
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectWidespreadPowerOutage(LocalDate beginDate, LocalDate endDate) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(DP_DFWTDMX.GDDW,
                        DP_DFWTDMX.TDSJ,
                        DP_DFWTDMX.FDSJ,
                        DP_DFWTDMX.BDZ,
                        DP_DFWTDMX.XL,
                        DP_DFWTDMX.SHS,
                        DP_DFWTDMX.TDXZ)
                .from(DP_DFWTDMX);
        if (beginDate != null && endDate != null) {
            queryWrapper.where(DP_DFWTDMX.TDSJ.between(beginDate, endDate));
        }
        return Db.selectListByQuery(queryWrapper);
    }

    /**
     * 查询年度累计停电
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return sum
     */
    public Row selectAnnualCumulativeOutage(LocalDate beginDate, LocalDate endDate, Boolean thisWeek) {
        String orderByStr;
        if (thisWeek) {
            orderByStr = ", QSSJ DESC ";
        } else {
            orderByStr = ", QSSJ ";
        }
        String sql = "SELECT * FROM (" +
                "       SELECT DFWTDSHS, QSSJ, ZZSJ " +
                "       FROM DP_DFWTD " +
                "       WHERE QSSJ >= TO_DATE('" + beginDate + "', 'yyyy-MM-dd') " +
                "             AND ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-MM-dd') " +
                "             AND DQTZ = '市中心,市区,城镇,农村' " +
                "             AND DWMC = '大连' " +
                "      ORDER BY ZZSJ DESC " + orderByStr + ") " +
                "WHERE ROWNUM = 1";
        return Db.selectOneBySql(sql);
    }

    /**
     * 查询年度累计停电根据单位
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectAnnualCumulativeOutageForUnit(LocalDate beginDate, LocalDate endDate, Boolean thisWeek) {
        String orderByStr;
        if (thisWeek) {
            orderByStr = ", QSSJ DESC ";
        } else {
            orderByStr = ", QSSJ ";
        }
        String sql = "select * from (" +
                "       select DWMC,DFWTDSHS " +
                "       from DP_DFWTD " +
                "       where QSSJ >= to_date('" + beginDate + "','yyyy-mm-dd') " +
                "             and ZZSJ <= to_date('" + endDate + "','yyyy-mm-dd') " +
                "             and DQTZ = '市中心,市区,城镇,农村' and DWMC <> '大连' " +
                "       order by ZZSJ desc " + orderByStr + " ) " +
                "WHERE ROWNUM <= 13";

        return Db.selectListBySql(sql);
    }

    /**
     * 查询停电次数Total
     *
     * @param endDate 结束日期
     * @return total
     */
    public Long selectTableTotal(LocalDate endDate, String table, String columns) {
        String year = String.valueOf(LocalDate.now().getYear());
        String sql =
                "select * " +
                        "from (SELECT DWMC, " + columns + ", QSSJ, ZZSJ " +
                        "      FROM " + table +
                        "      WHERE QSSJ >= TO_DATE('" + year + "-01-01', 'yyyy-mm-dd') " +
                        "        and ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-mm-dd') " +
                        "        and DWMC = '国网大连供电公司' " +
                        "      order by QSSJ desc, ZZSJ desc) " +
                        "where ROWNUM = 1";
        Row row = Db.selectOneBySql(sql);
        if (row == null) {
            return 0L;
        }
        return row.getLong(columns, 0L);
    }

    /**
     * 查询停电次数根据单位
     *
     * @param endDate 结束日期
     * @return list
     */
    public List<Row> selectTdcsListForUnit(LocalDate endDate) {
        String year = String.valueOf(LocalDate.now().getYear());
        String sql =
                "select * " +
                        "from (SELECT DWMC, CCTDYHS " +
                        "      FROM DP_CCTD " +
                        "      WHERE QSSJ >= TO_DATE('" + year + "-01-01', 'yyyy-mm-dd') " +
                        "        and ZZSJ <= to_date('" + endDate + "', 'yyyy-mm-dd') " +
                        "        and DWMC <> '国网大连供电公司' " +
                        "      order by QSSJ desc, ZZSJ desc) " +
                        "where ROWNUM <= 13";
        return Db.selectListBySql(sql);
    }

    /**
     * 查询本期重停用户数根据单位
     *
     * @param endDate 结束日期
     * @return list
     */
    public List<Row> selectBqctyhsListForUnit(LocalDate endDate) {
        String year = String.valueOf(LocalDate.now().getYear());
        String sql =
                "select * " +
                        "from (SELECT DWMC, BQCTYHS " +
                        "      FROM DP_CFTDCS " +
                        "      WHERE QSSJ >= TO_DATE('" + year + "-01-01', 'yyyy-mm-dd') " +
                        "        and ZZSJ <= to_date('" + endDate + "', 'yyyy-mm-dd') " +
                        "        and DWMC <> '国网大连供电公司' " +
                        "      order by QSSJ desc, ZZSJ desc) " +
                        "where ROWNUM <= 13";
        return Db.selectListBySql(sql);
    }

    /**
     * 查询用户平均停电时间的数据
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectMeanOutageTime(LocalDate beginDate, LocalDate endDate) {
        String sql;
        String sqlFrom = "select PJTDSJ, PJYTDSJ, PJGZTDSJ, QSSJ, ZZSJ " +
                "      from DP_GDKKX_ZB " +
                "      where (SSXQ is null or SSXQ='大连') " +
                "        and DQTZ = '市中心+市区+城镇+农村' " +
                "        and QSSJ >= TO_DATE('" + beginDate + "', 'yyyy-mm-dd') " +
                "        and ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-mm-dd') " +
                "      order by DP_GDKKX_ZB.QSSJ , ZZSJ desc";
        if (beginDate.isEqual(LocalDate.of(
                LocalDate.now().getYear(), 1, 1))) {
            sql = "select * from (" + sqlFrom + ") where ROWNUM = 1";
        } else {
            sql = sqlFrom;
        }
        return Db.selectListBySql(sql);
    }

    /**
     * 查询用户平均停电时间的数据根据单位
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectMeanOutageTimeForUnit(
            LocalDate beginDate, LocalDate endDate, Boolean isOneMonthDay, String columns) {
        String sql;
        if (isOneMonthDay) {
            sql = "select * " +
                    "from (select SSXQ, " + columns + " " +
                    "      from DP_GDKKX_ZB " +
                    "      where SSXQ is not null  " +
                    "        and DQTZ = '市中心+市区+城镇+农村' " +
                    "        and QSSJ >= TO_DATE('" + beginDate + "', 'yyyy-mm-dd') " +
                    "        and ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-mm-dd') " +
                    "      order by DP_GDKKX_ZB.QSSJ desc, ZZSJ desc) " +
                    "      order by DP_GDKKX_ZB.QSSJ , ZZSJ desc) " +
                    "where ROWNUM <= 13";
        } else {
            sql = "select SSXQ, " + columns + " " +
                    "from DP_GDKKX_ZB " +
                    "where SSXQ is not null  " +
                    "  and DQTZ = '市中心+市区+城镇+农村' " +
                    "  and QSSJ >= TO_DATE('" + beginDate + "', 'yyyy-mm-dd') " +
                    "  and ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-mm-dd')";
        }
        return Db.selectListBySql(sql);
    }

    /**
     * 查询供电可靠性管理
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      0全口径 1城网 2农网
     * @return row
     */
    public Row selectReliability(LocalDate beginDate, LocalDate endDate, Integer type) {
        String sql = "select PJGDSJ,PJTDSJ,TDZSHS from DP_GDKKX_ZB " +
                "where QSSJ >= TO_DATE('" + beginDate + "', 'yyyy-mm-dd') " +
                " and  ZZSJ <= TO_DATE('" + endDate + "', 'yyyy-mm-dd') ";
        String whereAnd = "";
        if (type == 0) {
            whereAnd = " and (SSXQ is null or SSXQ='大连') and DQTZ='市中心+市区+城镇+农村' ";
        } else if (type == 1) {
            whereAnd = " and (SSXQ is null or SSXQ='大连') and DQTZ = '市中心+市区' ";
        } else if (type == 2) {
            whereAnd = " and (SSXQ is null or SSXQ='大连') and DQTZ= '城镇+农村' ";
        }
        sql = sql + whereAnd + " order by QSSJ  ,ZZSJ DESC  ";
        String resultSql = "select * from (" + sql + ") where ROWNUM =1";
        return Db.selectOneBySql(resultSql);
    }

}
