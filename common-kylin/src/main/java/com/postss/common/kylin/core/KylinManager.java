package com.postss.common.kylin.core;

import com.postss.common.kylin.client.KylinClient;

/**
 * kylin 全局操作管理器
 * @className KylinManager
 * @author jwSun
 * @date 2017年8月22日 下午7:13:16
 * @version 1.0.0
 */
public interface KylinManager {

    public KylinClient getKylinClient();

}
