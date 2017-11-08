package com.postss.common.sql.enums;

import com.postss.common.sql.fragment.SqlFunctionFragment;
import com.postss.common.util.StringUtil;

/**
 * Sql函数
 * @className SqlFunction
 * @author jwSun
 * @date 2017年8月9日 下午3:24:13
 * @version 1.0.0
 */
public enum SqlFunction {

    /**AVG 函数返回数值列的平均值。NULL 值不包括在计算中。**/
    AVG(null),
    /**COUNT() 函数返回匹配指定条件的行数。**/
    COUNT((String alias, Object... value) -> {
        if (value == null || value.length == 0) {
            return "COUNT(1) " + alias;
        }
        String appendAlias;
        if (StringUtil.notEmpty(alias)) {
            appendAlias = " AS " + alias;
        } else {
            appendAlias = "";
        }
        if (value.length == 1) {
            return "COUNT(" + value[0] + ") " + appendAlias;
        } else {
            return "COUNT(" + value[0] + "." + value[1] + ") " + appendAlias;
        }
    }),
    /**FIRST() 函数返回指定的字段中第一个记录的值。**/
    FIRST(null),
    /**LAST() 函数返回指定的字段中最后一个记录的值**/
    LAST(null),
    /**MAX 函数返回一列中的最大值。NULL 值不包括在计算中。**/
    MAX(null),
    /**MIN 函数返回一列中的最小值。NULL 值不包括在计算中。**/
    MIN(null),
    /**SUM 函数返回数值列的总数（总额）。**/
    SUM(null),
    /**UCASE 函数把字段的值转换为大写。**/
    UCASE(null),
    /**LCASE 函数把字段的值转换为小写。**/
    LCASE(null),
    /**MID 函数用于从文本字段中提取字符。**/
    MID(null),
    /**LEN 函数返回文本字段中值的长度。**/
    LEN(null),
    /**ROUND 函数用于把数值字段舍入为指定的小数位数。**/
    ROUND(null),
    /**NOW 函数返回当前的日期和时间。**/
    NOW((String alias, Object... value) -> {
        return "NOW() AS " + alias;
    }),
    /**FORMAT 函数用于对字段的显示进行格式化。**/
    FORMAT(null);

    private SqlFunctionFragment sqlFunctionFragment;

    private SqlFunction(SqlFunctionFragment sqlFunctionFragment) {
        this.sqlFunctionFragment = sqlFunctionFragment;
    }

    public SqlFunctionFragment getSqlFunctionFragment() {
        if (this.sqlFunctionFragment == null) {
            return defaultSqlFunctionFragment;
        }
        return sqlFunctionFragment;
    }

    private SqlFunctionFragment defaultSqlFunctionFragment = (String alias, Object... value) -> {
        if (value == null || value.length == 0) {
            throw new RuntimeException();
        }
        String appendAlias;
        if (StringUtil.notEmpty(alias)) {
            appendAlias = " AS " + alias;
        } else {
            appendAlias = "";
        }
        if (value.length == 1) {
            return this.toString() + "(" + value[0] + ")" + appendAlias;
        } else {
            return this.toString() + "(" + value[0] + "." + value[1] + ")" + appendAlias;
        }
    };
}
