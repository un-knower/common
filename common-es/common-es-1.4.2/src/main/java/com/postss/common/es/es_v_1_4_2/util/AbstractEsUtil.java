package com.postss.common.es.es_v_1_4_2.util;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.PageBuilder;

public abstract class AbstractEsUtil {

    static EsClient esClient = new EsClient();

    protected static TransportClient client = esClient.transPortClient;

    protected static BasePage countPage = PageBuilder.begin().setBeginNumber(0).setPageSize(1).build();

    protected static Logger log = LoggerFactory.getLogger(AbstractEsUtil.class);

    public static TransportClient getClient() {
        return client;
    }

    public static void main(String[] args) {

    }

}
