package com.postss.common.kylin.client;

import java.io.Closeable;

import com.postss.common.kylin.search.KylinBodySearch;
import com.postss.common.kylin.search.KylinSearch;

/**
 * kylin 客户端
 * @className KylinClient
 * @author jwSun
 * @date 2017年8月22日 下午7:09:48
 * @version 1.0.0
 */
public interface KylinClient extends Closeable {

    /**
     * 执行有请求体的操作
     * @param kylinBodySearch 请求体查询类
     * @return
     */
    public String executeQueryBody(KylinBodySearch kylinBodySearch);

    /**
     * 执行无请求体操作
     * @param kylinSearch 基本查询类
     * @return
     */
    public String executeQueryJson(KylinSearch kylinSearch);

    /**
     * 验证权限操作
     * @return
     */
    public boolean checkAuthentication();

    /**
     * 执行权限验证操作
     * @return
     */
    public boolean doAuthentication();

}
