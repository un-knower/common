package com.postss.common.sql.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.hibernate.sql.Alias;

public abstract class CaseFragment implements Fragment {

    protected String returnColumnName;

    protected Map cases = new LinkedHashMap();

    public CaseFragment setReturnColumnName(String returnColumnName) {
        this.returnColumnName = returnColumnName;
        return this;
    }

    public CaseFragment setReturnColumnName(String returnColumnName, String suffix) {
        return setReturnColumnName(new Alias(suffix).toAliasString(returnColumnName));
    }

    public CaseFragment addWhenColumnNotNull(String alias, String columnName, String value) {
        cases.put(StringHelper.qualify(alias, columnName), value);
        return this;
    }
}