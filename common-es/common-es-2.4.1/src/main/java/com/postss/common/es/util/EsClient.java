package com.postss.common.es.util;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.postss.common.es.GlobalForEsUtil;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * ES客户端-v1.4.2
 * @ClassName EsClient
 * @author jwSun
 * @Date 2017年6月24日 下午7:18:08
 * @version 1.0.0
 */
public class EsClient {
    private static Logger log = LoggerUtil.getLogger(EsClient.class);

    public TransportClient transPortClient = null;

    /**
     * ES TransPortClient 客户端连接-2.4.1版本<br>
     * 在elasticsearch平台中,可以执行创建索引,获取索引,删除索引,搜索索引等操作<br>
     * client.transport.sniff：为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器
     */
    public EsClient() {
        try {
            log.info("esClusterName:" + GlobalForEsUtil.esClusterName);
            log.info("esServerIps:" + GlobalForEsUtil.esServerIps);
            log.info("esServerPort:" + GlobalForEsUtil.esServerPortUse);
            if (transPortClient == null) {
                if (GlobalForEsUtil.esServerIps != null && !"".equals(GlobalForEsUtil.esServerIps.trim())) {
                    Settings settings = Settings.builder().put("cluster.name", GlobalForEsUtil.esClusterName)// 集群名
                            .put("client.transport.sniff", false).build();
                    transPortClient = TransportClient.builder().settings(settings).build();
                    String esIps[] = GlobalForEsUtil.esServerIps.split(",");
                    for (String esIp : esIps) {// 添加集群IP列表
                        transPortClient.addTransportAddresses(new InetSocketTransportAddress(
                                InetAddress.getByName(esIp), GlobalForEsUtil.esServerPortUse));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transPortClient != null) {
                transPortClient.close();
            }
        }
    }
}
