package com.postss.common.sql.dialect;

import java.util.Date;

import com.postss.common.util.DateUtil;

public class KylinSqlWarp extends AbstractSqlWarp {

    @Override
    public String warp(Object val) {
        return "({?" + super.warp(val) + "})";
    }

    public String warpDate(Object val) {
        Date date = (Date) val;
        return "to_date(" + DateUtil.format(date) + ",yyyy-mm-dd hh24:mi:ss)";
    }

    @Override
    public String[] warp(Object[] val) {
        String[] obj = new String[val.length];
        for (int i = 0; i < obj.length; i++) {
            obj[i] = warp(val[i]);
        }
        return obj;
    }

    @Override
    public String getParamterPattern() {
        return "\\(\\{\\?(.*?)\\}\\)";
    }

}
