package com.pg.screen.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum BxcsEnum {
    SSXL_1("3-5次", 1, "SSXL",3,5),
    SSXL_2("6-10次", 2, "SSXL",6,10),
    SSXL_3("11-15次", 3, "SSXL",11,15),
    SSXL_4("16次及以上", 4, "SSXL",16,-1),
    SSTQ_1("3-5次", 5, "SSTQ",3,5),
    SSTQ_2("6-10次", 5, "SSTQ",6,10),
    SSTQ_3("11-15次", 5, "SSTQ",11,15),
    SSTQ_4("16次及以上", 5, "SSTQ",16,-1),
    QXBZ_1("3-5次", 5, "QXBZ",3,5),
    QXBZ_2("6-10次", 5, "QXBZ",6,10),
    QXBZ_3("11-20次", 5, "QXBZ",11,20),
    QXBZ_4("21次及以上", 5, "QXBZ",21,-1),
    YHXX_1("2次", 5, "YHXX",2,2),
    YHXX_2("3-5次", 5, "YHXX",3,5),
    YHXX_3("6-10次", 5, "YHXX",6,10),
    YHXX_4("11次及以上", 5, "YHXX",11,-1);
    // 成员变量
    public String name;
    public int index;
    public String type;
    public int starNum;
    public int endNum;
    // 构造方法 ,赋值给成员变量
    private BxcsEnum(String name, int index, String type, int starNum, int endNum) {
        this.name = name;
        this.index = index;
        this.type = type;
        this.starNum=starNum;
        this.endNum=endNum;
    }
    //覆盖方法  :只能使用toString方法来输出枚举变量值
    @Override
    public String toString() {
        return this.index+"_"+this.name+"_"+this.type+"_"+this.starNum+"_"+this.endNum;
    }

    public static List<HashMap<String,Object>> getEnumList(String type) {
        List<HashMap<String,Object>> bxcsEnums=new ArrayList<>();
        for (BxcsEnum item : values()) {
            if (item.type.equals(type)) {
                HashMap<String,Object> addItem=new HashMap<>();
                addItem.put("key",item.name());
                addItem.put("name",item.name);
                bxcsEnums.add(addItem);
            }
        }
        return bxcsEnums;
    }


    public static int getStartNum(BxcsEnum bxcsEnum) {
        return bxcsEnum.starNum;
    }

    public static int getEndNum(BxcsEnum bxcsEnum) {
        return bxcsEnum.endNum;
    }

}
