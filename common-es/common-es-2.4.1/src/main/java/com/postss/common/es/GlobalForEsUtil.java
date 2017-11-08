package com.postss.common.es;

import org.springframework.util.Assert;

import com.postss.common.es.exception.EsSearchException;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.PropertiesUtil;
import com.postss.common.util.StringUtil;

/**
 * ES工具类全局常量
 * @className GlobalForEsUtil
 * @author jwSun
 * @date 2017年8月5日 上午11:11:02
 * @version 1.0.0
 */
public class GlobalForEsUtil {

    private static Logger log = LoggerUtil.getLogger(GlobalForEsUtil.class);

    public static final String ES_CLUSTER_NAME = PropertiesUtil.getProperty("es.server.clustername");
    public static final String ES_SERVER_IPS = PropertiesUtil.getProperty("es.server.ips");
    public static final String ES_SERVER_PORT = PropertiesUtil.getProperty("es.server.port");
    /**兼容旧版本**/
    public static final String ES_CLUSTER_NAME_OLD = PropertiesUtil.getProperty("ES.SERVER.CLUSTERNAME");
    public static final String ES_SERVER_IPS_OLD = PropertiesUtil.getProperty("ES.SERVER.IPS");
    public static final String ES_SERVER_PORT_OLD = PropertiesUtil.getProperty("ES.SERVER.PORT");
    /**集群名**/
    public static final String esClusterName = StringUtil.notEmpty(ES_CLUSTER_NAME) ? ES_CLUSTER_NAME
            : ES_CLUSTER_NAME_OLD;
    /**集群服务IP集合**/
    public static final String esServerIps = StringUtil.notEmpty(ES_SERVER_IPS) ? ES_SERVER_IPS : ES_SERVER_IPS_OLD;
    /**集群服务IP集合**/
    public static final String esServerPort = StringUtil.notEmpty(ES_SERVER_PORT) ? ES_SERVER_PORT : ES_SERVER_PORT_OLD;
    /**ES集群端口**/
    public static final Integer esServerPortUse;

    /**集群名**/
    //public static final String esClusterName = "mycluster";
    /**集群服务IP集合**/
    //public static final String esServerIps = "192.168.52.34";
    /**集群服务IP集合**/
    //public static final String esServerPort = "9300";

    static {
        try {
            Assert.notNull(esClusterName, "请检查es.server.clustername是否存在及是否被PropertiesUtil扫描");
            Assert.notNull(esServerIps, "请检查es.server.ips是否存在及是否被PropertiesUtil扫描");
            Assert.notNull(esServerPort, "请检查es.server.port是否存在及是否被PropertiesUtil扫描");
            if (!StringUtil.notEmpty(ES_CLUSTER_NAME)) {
                log.warn("ES_CLUSTER_NAME 配置项存在,但使用的是已过期配置项:{},请修改为{}", "ES.SERVER.CLUSTERNAME",
                        "es.server.clustername");
            }
            if (!StringUtil.notEmpty(ES_SERVER_IPS)) {
                log.warn("ES_SERVER_IPS 配置项存在,但使用的是已过期配置项:{},请修改为{}", "ES.SERVER.IPS", "es.server.ips");
            }
            if (!StringUtil.notEmpty(ES_SERVER_PORT)) {
                log.warn("ES_SERVER_PORT 配置项存在,但使用的是已过期配置项:{},请修改为{}", "ES.SERVER.PORT", "es.server.port");
            }
            int port = Integer.valueOf(esServerPort);
            esServerPortUse = port;
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

}
