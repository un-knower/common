package com.postss.common.sql.dialect;

import com.postss.common.base.annotation.AutoRootApplicationConfig;

@AutoRootApplicationConfig("defaultSqlWarp")
public class DefaultSqlWarp extends AbstractSqlWarp {

    @Override
    public String[] warp(Object[] val) {
        String[] retArray = new String[val.length];
        for (int i = 0; i < val.length; i++) {
            Object valOne = val[i];
            retArray[i] = warp(valOne);
        }
        return retArray;
    }

    public String getParamterPattern() {
        return "\\$\\{-\\{(.*?)\\}-\\}";
    }

}
