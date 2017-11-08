package com.postss.common.kylin.util;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.kylin.api.search.KylinApiSearch;
import com.postss.common.kylin.core.DefaultKylinManager;
import com.postss.common.kylin.core.KylinManager;
import com.postss.common.kylin.search.KylinBodySearch;
import com.postss.common.util.StringUtil;

/**
 * kylin 查询
 * @className KylinQueryAction
 * @author jwSun
 * @date 2017年8月22日 下午7:23:59
 * @version 1.0.0
 */
public class KylinQueryAction {

    /*@Autowired
    @Qualifier("kylinManager")
    private static KylinManager kylinManager;*/

    private static KylinManager kylinManager = new DefaultKylinManager();

    public static String queryByBodySearch(KylinBodySearch kylinBodySearch) {
        return kylinManager.getKylinClient().executeQueryBody(kylinBodySearch);
    }

    public static <T> T queryByApiSearch(KylinApiSearch kylinApiSearch, Class<T> clazz) {
        String responseJson = kylinManager.getKylinClient().executeQueryJson(kylinApiSearch);
        if (StringUtil.notEmpty(responseJson)) {
            return JSONObject.parseObject(responseJson, clazz);
        } else {
            return null;
        }
    }

    public static String queryByApiSearch(KylinApiSearch kylinApiSearch) {
        return kylinManager.getKylinClient().executeQueryJson(kylinApiSearch);
    }

    public static KylinManager getKylinManager() {
        return kylinManager;
    }

}
