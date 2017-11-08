package com.postss.common.es.util;

import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;

import com.postss.common.es.entity.EsIndex;
import com.postss.common.es.entity.EsPut;

/**
 * ES改变数据工具类
 * @ClassName EsChangeUtil
 * @author jwSun
 * @Date 2017年6月24日 下午7:19:09
 * @version 1.0.0
 */
public class EsChangeUtil extends AbstractEsUtil {

    /**
     * 创建索引
     *  <pre>example:
     *      System.out.println(createIndex(new EsIndex("test", "test", 5, 1).putField("ff1", IndexField.type, "string")
                .putField("ff2", IndexField.type, "string")));</pre>
     * @author jwSun
     * @date 2017年4月18日 上午10:29:05
     * @param index
     * @return
     */
    public static Boolean createIndex(EsIndex index) {
        try {
            Settings settings = Settings.settingsBuilder().put("number_of_shards", index.getNumber_of_shards())
                    .put("number_of_replicas", index.getNumber_of_replicas()).build();
            client.admin().indices().prepareCreate(index.getFirstIndex()).setSettings(settings).execute().actionGet();
            log.debug(index.getProperties().string());
            System.out.println(index.getProperties().string());
            PutMappingRequest mappingRequest = Requests.putMappingRequest(index.getFirstIndex())
                    .type(index.getFirstType()).source(index.getProperties());
            return client.admin().indices().putMapping(mappingRequest).actionGet().isAcknowledged();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * 新增数据
     * @author jwSun
     * @date 2017年4月18日 下午1:51:33
     * @param put
     * @return
     */
    public static Boolean putToIndex(EsPut put) {
        try {
            BulkRequestBuilder bulkRequest = getClient().prepareBulk();
            put.getPutMaps().forEach((Map<String, Object> putMap) -> {
                bulkRequest.add(getClient().prepareIndex(put.getFirstIndex(), put.getFirstType()).setSource(putMap));
            });
            bulkRequest.execute().actionGet();
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    /*public static void main(String[] args) {
        EsPut esPut = new EsPut("test_abc", "abc");
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < 1000; k++) {
            for (int i = 0; i < 100; i++) {
                sb.setLength(0);
                for (int j = 0; j < 6; j++) {
                    sb.append(RandomUtil.getRandomNum(10));
                }
                esPut.setPutMap(new QuickHashMap<String, Object>().quickPut("CAR_NUM", "浙" + sb.toString())
                        .quickPut("CREATE_TIME", sb.toString()));
            }
            EsChangeUtil.putToIndex(esPut);
        }
    }*/
}
