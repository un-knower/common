package com.postss.common.manager;

import java.util.Map;

public interface WildCardManager {

    public String replace(String text, Map<String, Object> replaceMap);

}
