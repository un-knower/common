package com.postss.common.es.util;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.PageBuilder;

/**
 * Es基本工具抽象类
 * @ClassName AbstractEsUtil
 * @author jwSun
 * @Date 2017年6月24日 下午7:19:33
 * @version 1.0.0
 */
public abstract class AbstractEsUtil {

    /**Es连接客户端**/
    static EsClient esClient = new EsClient();
    /**Es连接客户端实例**/
    protected static TransportClient client = esClient.transPortClient;
    /**基本分页实体类,查询第一条数据**/
    protected static BasePage countPage = PageBuilder.begin().setBeginNumber(0).setPageSize(1).build();

    protected static Logger log = LoggerFactory.getLogger(AbstractEsUtil.class);

    /**
     * 获得Es连接客户端
     * @return TransportClient
     */
    public static TransportClient getClient() {
        return client;
    }
}
