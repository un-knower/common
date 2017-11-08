package com.postss.common.system.code;

import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.ExecutorsDaemon;
import com.postss.common.util.PropertiesUtil;
import com.postss.common.util.StringUtil;

/**
 * 返回码对应信息管理器
 * @className CodeMessageManager
 * @author jwSun
 * @date 2017年8月1日 下午4:35:52
 * @version 1.0.0
 */
public class CodeMessageManager {

    private static Logger log = LoggerUtil.getLogger(CodeMessageManager.class);

    private final static String systemMessagePath = "common-web-config/properties/system-message-code_zh_CN.properties";
    private final String messagePath;
    private final String fileName;
    private final String language;
    private String realMessagePath;
    //不需要加载说明
    public static final int NO_MESSAGE_CODE = 0;
    //默认重新读取配置文件时间(分钟)
    private static final long defaultLoadTime = 30;
    /**更新配置文件信息定时器**/
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1,
            ExecutorsDaemon.getDaemonThreadFactory());

    /**key:code,value:message**/
    private static ConcurrentHashMap<Integer, String> codeMessageMap = new ConcurrentHashMap<Integer, String>();

    static {
        log.debug("init system code and message...");
        for (SystemCode businessCode : SystemCode.values()) {
            String message = PropertiesUtil.getProperty(String.valueOf(businessCode.getCode()), systemMessagePath);
            codeMessageMap.put(businessCode.getCode(), message == null ? "" : message);
        }
        log.debug("init system code and message end");
    }

    public CodeMessageManager(String messagePath, String fileName) {
        this(messagePath, fileName, "zh_CN", defaultLoadTime);
    }

    public CodeMessageManager(String messagePath, String fileName, String language) {
        this(messagePath, fileName, language, defaultLoadTime);
    }

    public CodeMessageManager(String messagePath, String fileName, String language, long loadTime) {
        super();
        this.messagePath = messagePath;
        this.fileName = fileName;
        this.language = language;
        this.realMessagePath = messagePath + "/" + fileName + "_" + language + ".properties";
        //Executors.defaultThreadFactory();
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                loadMessage();
            }
        }, 0, loadTime, TimeUnit.MINUTES);
    }

    /**
     * 读取配置文件信息
     */
    private void loadMessage() {
        log.debug("init code and message...");
        Properties messageProperties = PropertiesUtil.loadProperties(this.realMessagePath);
        for (Entry<Object, Object> businessCode : messageProperties.entrySet()) {
            try {
                Integer code = Integer.valueOf((String) businessCode.getKey());
                String message = (String) businessCode.getValue();
                codeMessageMap.put(code, message == null ? "" : message);
            } catch (Exception e) {
                log.error("key : {},value: {} ,error is : {}", businessCode.getKey(), businessCode.getValue(),
                        e.getLocalizedMessage());
            }
        }
        log.debug("init code and message end...");
    }

    public static void loadMessageByPath(String path) {
        log.debug("loadMessageByPath... {}", path);
        Properties messageProperties = PropertiesUtil.loadProperties(path);
        for (Entry<Object, Object> businessCode : messageProperties.entrySet()) {
            try {
                Integer code = Integer.valueOf((String) businessCode.getKey());
                String message = (String) businessCode.getValue();
                codeMessageMap.put(code, message == null ? "" : message);
            } catch (Exception e) {
                log.error("key : {},value: {} ,error is : {}", businessCode.getKey(), businessCode.getValue(),
                        e.getLocalizedMessage());
            }
        }
        log.debug("loadMessageByPath end...");
    }

    public static String getMessageByCodeOrDefault(int code, String message, Object... params) {
        if (NO_MESSAGE_CODE == code) {
            return message;
        } else {
            return getMessageByCode(code, params);
        }
    }

    public static String getMessageByCode(int code, Object... params) {
        return replaceMessage(codeMessageMap.get(code), params);
    }

    /**
     * 根据返回码获得详细提示信息
     * @param code 代码
     * @param message 提示信息
     * @param params 提示信息,可在配置文件中以{index}保存
     * @return
     */
    public static String getDetailMessageByCodeOrDefault(int code, String message, Object... params) {
        String firstMessage = replaceMessage(message, params);
        if (NO_MESSAGE_CODE == code) {
            return firstMessage;
        } else {
            return getDetailMessageByCode(code, params) + ",at first message is " + firstMessage;
        }
    }

    public static String getDetailMessageByCode(int code, Object... params) {
        return "code : " + code + ",code message is : " + getMessageByCode(code, params);
    }

    // 匹配{}
    private static final String PATTERN_COMPILE = "\\{(.*?)\\}";

    private static String replaceMessage(String message, Object... params) {
        if (params == null || message == null)
            return message;
        try {
            Object[] replaceParams = params;
            List<String> replaceList = StringUtil.getPatternMattcherList(message, PATTERN_COMPILE, 1);
            for (String replaceIndex : replaceList) {
                int index = Integer.parseInt(replaceIndex);
                if (index < replaceParams.length && index >= 0) {
                    message = message.replaceAll("\\{" + index + "\\}", replaceParams[index].toString());
                }
            }
        } catch (Exception e) {
            log.error(" replace message error ,{}", e.getLocalizedMessage());
        }
        return message;

    }

    public String getSystemMessagePath() {
        return systemMessagePath;
    }

    public String getMessagePath() {
        return messagePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLanguage() {
        return language;
    }

    public String getRealMessagePath() {
        return realMessagePath;
    }

    /*public static String getMessageByBusinessCode(BusinessCode businessCode) {
        return codeMessageMap.get(businessCode.getCode());
    }*/

}
