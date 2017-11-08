package com.postss.common.es.search;

/**
 * Scroll模式查询
 * @author jwSun
 * @date 2017年6月21日 上午11:02:45
 */
public class EsScrollFieldSearch extends EsFieldSearch {

    public EsScrollFieldSearch(String index, String type) {
        super(index, type);
    }

    private long timeValue = 3000L;
    private String scrollId;

    public EsSearch setScroll(long timeValue) {
        this.timeValue = timeValue;
        return this;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

}
