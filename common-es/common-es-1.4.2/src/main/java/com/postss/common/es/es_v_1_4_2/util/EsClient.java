package com.postss.common.es.es_v_1_4_2.util;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.postss.common.util.PropertiesUtil;

/**
 * ES客户端-v1.4.2
 * @author jwSun
 * @date 2017年5月2日 下午4:03:47
 */
public class EsClient {
    static Log log = LogFactory.getLog(EsClient.class);

    public TransportClient transPortClient = null;
    private String esClusterName = PropertiesUtil.getProperty("ES.SERVER.CLUSTERNAME", "properties/es.properties");// 集群名
    private String esServerIps = PropertiesUtil.getProperty("ES.SERVER.IPS", "properties/es.properties");// 集群服务IP集合
    private Integer esServerPort = Integer
            .valueOf(PropertiesUtil.getProperty("ES.SERVER.PORT", "properties/es.properties"));// ES集群端口

    /**
     * client.transport.sniff：为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器
     */
    public EsClient() {
        try {
            if (transPortClient == null) {
                if (esServerIps != null && !"".equals(esServerIps.trim())) {
                    Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", esClusterName)// 集群名
                            .put("client.transport.sniff", false).build();
                    transPortClient = new TransportClient(settings);
                    String esIps[] = esServerIps.split(",");
                    for (String esIp : esIps) {// 添加集群IP列表
                        transPortClient.addTransportAddresses(
                                new InetSocketTransportAddress(InetAddress.getByName(esIp), esServerPort));
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
