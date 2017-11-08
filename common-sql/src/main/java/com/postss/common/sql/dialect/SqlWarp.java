package com.postss.common.sql.dialect;

import com.postss.common.system.interfaces.Warp;

public interface SqlWarp extends Warp<String> {

    public String[] warp(Object[] val);

    //public T warp(Object val, T t);

    public static SqlWarp defaultSqlWarp = new DefaultSqlWarp();

    public String getParamterPattern();

    public String warpDate(Object val);

    public String warpByte(Object val);

    public String warpShort(Object val);

    public String warpInteger(Object val);

    public String warpDouble(Object val);

    public String warpFloat(Object val);

    public String warpLong(Object val);

    public String warpChar(Object val);

    public String warpString(Object val);

    public String warpObject(Object val);

    public String warpArray(Object val);

    public String warpCollection(Object val);

}
