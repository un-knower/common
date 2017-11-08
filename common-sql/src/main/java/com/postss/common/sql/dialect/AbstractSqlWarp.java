package com.postss.common.sql.dialect;

import java.util.Collection;
import java.util.Date;

import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.SystemCodeException;
import com.postss.common.util.DateUtil;

public abstract class AbstractSqlWarp implements SqlWarp {

    public String warp(Object obj) {
        if (obj == null) {
            throw new SystemCodeException(SystemCode.NULL, "obj");
        }

        if (obj instanceof java.util.Collection) {
            return warpCollection(obj);
        } else if (obj.getClass().isArray()) {
            return warpArray(obj);
        } else if (obj instanceof Date) {
            return warpDate(obj);
        } else if (obj instanceof Integer) {
            return warpInteger(obj);
        } else if (obj instanceof Short) {
            return warpShort(obj);
        } else if (obj instanceof Byte) {
            return warpByte(obj);
        } else if (obj instanceof Double) {
            return warpDouble(obj);
        } else if (obj instanceof Float) {
            return warpFloat(obj);
        } else if (obj instanceof Long) {
            return warpLong(obj);
        } else if (!(obj instanceof String)) {
            return warpObject(obj);
        } else {
            return warpString(obj);
        }
    }

    //public abstract String warpInternal(Object val);

    @Override
    public String warpDate(Object val) {
        Date date = (Date) val;
        return "to_date(" + DateUtil.format(date) + ",yyyy-mm-dd hh24:mi:ss)";
    }

    @Override
    public String warpByte(Object val) {
        Byte b = (byte) val;
        return b.toString();
    }

    @Override
    public String warpShort(Object val) {
        Short shorts = (Short) val;
        return shorts.toString();
    }

    @Override
    public String warpInteger(Object val) {
        return val.toString();
    }

    @Override
    public String warpDouble(Object val) {
        return val.toString();
    }

    @Override
    public String warpFloat(Object val) {
        return val.toString();
    }

    @Override
    public String warpLong(Object val) {
        return val.toString();
    }

    @Override
    public String warpChar(Object val) {
        return val.toString();
    }

    @Override
    public String warpString(Object val) {
        return "'" + val + "'";
    }

    @Override
    public String warpObject(Object val) {
        return val.toString();
    }

    public String warpArray(Object val) {
        Object[] listObj = (Object[]) val;
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        for (int i = 0; i < listObj.length; i++) {
            sb.append(warp(listObj[i]));
            if (i != listObj.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String warpCollection(Object val) {
        Collection<?> collectionObj = (Collection<?>) val;
        Object[] objArray = collectionObj.toArray(new Object[collectionObj.size()]);
        return warpArray(objArray);
    }

}
