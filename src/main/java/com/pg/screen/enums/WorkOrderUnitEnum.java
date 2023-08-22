package com.pg.screen.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * 工单单位枚举
 *
 * @author c.chuang
 */
public enum WorkOrderUnitEnum {

    ZS("中山供电分公司", "中山"),
    SHK("沙河口供电分公司", "沙河口"),
    GJZ("甘井子供电分公司", "甘井子"),
    GX("高新园区供电分公司", "高新"),
    JZ("国网大连市金州新区供电公司", "金州"),
    KD("国网大连市开发区东部供电公司", "开东"),
    KFQ("国网大连市开发区供电公司", "开发区"),
    LS("国网大连市旅顺口区供电公司", "旅顺"),
    WFD("国网瓦房店市供电公司", "瓦房店"),
    PLD("国网普兰店市供电公司", "普兰店"),
    ZH("国网庄河市供电公司", "庄河"),
    CH("国网长海县供电公司", "长海"),
    CXD("国网大连市长兴岛临港工业区供电公司", "长兴岛");


    private final String fullName;

    private final String forShort;

    WorkOrderUnitEnum(String fullName, String forShort) {
        this.fullName = fullName;
        this.forShort = forShort;
    }

    public static String getName(String shortName) {
        for (WorkOrderUnitEnum name : values()) {
            if (name.forShort.equals(shortName)) {
                return name.fullName;
            }
        }
        throw new RuntimeException("WorkOrderUnitEnum-参数错误");
    }

    /**
     * 查询地区全名
     *
     * @param forShort 地区简称
     * @return 地区全名
     */
    public static String getFullName(String forShort) {
        for (WorkOrderUnitEnum name : values()) {
            if (name.forShort.equals(forShort)) {
                return name.fullName;
            }
        }
        throw new RuntimeException("WorkOrderUnitEnum-参数错误");
    }

    public static List<String> getFullName() {
        List<String> resultList = new LinkedList<>();
        for (WorkOrderUnitEnum value : values()) {
            resultList.add(value.fullName);
        }
        return resultList;
    }

    public static List<String> getShortName() {
        List<String> resultList = new LinkedList<>();
        for (WorkOrderUnitEnum value : values()) {
            resultList.add(value.forShort);
        }
        return resultList;
    }

    public static String getShortName(String fullName) {
        for (WorkOrderUnitEnum name : values()) {
            if (name.fullName.equals(fullName)) {
                return name.forShort;
            }
        }
        return null;
    }

}
