package com.postss.common.kylin.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.kylin.entity.KylinAuth;
import com.postss.common.kylin.exception.KylinJdbcException;
import com.postss.common.kylin.exception.KylinParamsException;
import com.postss.common.kylin.exception.KylinUnKnowException;
import com.postss.common.kylin.search.ApiSqlSearch;
import com.postss.common.kylin.search.KylinBodySearch;
import com.postss.common.kylin.search.KylinSearch;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.StringUtil;

/**
 * sql查询使用kylin-jdbc操作 目前效率低
 * @className KylinJdbcClient
 * @author jwSun
 * @date 2017年8月22日 下午7:11:31
 * @version 1.0.0
 */
public class KylinJdbcClient implements KylinClient {

    private Logger log = LoggerUtil.getLogger(getClass());

    private final KylinAuth kylinAuth;
    /**http://url:port/kylin**/
    private final String kylinUrl;
    private final String jdbcUrl;
    private Properties info = new Properties();
    private final String driverClass = "org.apache.kylin.jdbc.Driver";
    private Driver driver;
    private KylinClient apiClient;

    public KylinJdbcClient(String kylinUrl, KylinAuth kylinAuth) {
        super();
        this.kylinUrl = kylinUrl;
        this.kylinAuth = kylinAuth;
        String url = StringUtil.getAllPatternMattcher(kylinUrl, "(http|https)://(.*?)/(.*?)", 2);
        if (!StringUtil.notEmpty(url)) {
            throw new KylinParamsException("kylinUrl: " + kylinUrl + " 验证失败:获取不到路径");
        }
        jdbcUrl = "jdbc:kylin://" + url + "/";
        info.put("user", kylinAuth.getName());
        info.put("password", kylinAuth.getPassword());
        try {
            driver = (Driver) Class.forName(driverClass).newInstance();
        } catch (Exception e) {
            throw new KylinUnKnowException(e.getLocalizedMessage());
        }
        apiClient = new SimpleKylinClient(kylinUrl, kylinAuth);
        doAuthentication();
    }

    public KylinAuth getKylinAuth() {
        return kylinAuth;
    }

    public String getKylinUrl() {
        return kylinUrl;
    }

    @Override
    public String executeQueryBody(KylinBodySearch kylinBodySearch) {
        if (kylinBodySearch instanceof ApiSqlSearch) {
            try {
                ApiSqlSearch sqlSearch = (ApiSqlSearch) kylinBodySearch;
                Connection connection = getConnection(sqlSearch.getProject());
                connection.createStatement();
                Statement state = connection.createStatement();
                ResultSet resultSet = state.executeQuery(sqlSearch.getSql());
                JSONObject jsonArray = new JSONObject();
                List<List<Object>> results = new ArrayList<>();
                jsonArray.put("results", results);
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<Object>();
                    int i = 1;
                    try {
                        while (true) {
                            row.add(resultSet.getObject(i));
                            i++;
                        }
                    } catch (Exception e) {

                    } finally {
                        results.add(row);
                    }
                }
                return jsonArray.toJSONString();
            } catch (Exception e) {
                log.error("executeQueryBody : ", e);
                throw new KylinJdbcException(e.getLocalizedMessage());
            }
        }
        return apiClient.executeQueryBody(kylinBodySearch);
    }

    public Connection getConnection(String projectName) {
        try {
            return driver.connect(jdbcUrl + projectName, info);
        } catch (Exception e) {
            log.error("getConnection : ", e);
            throw new KylinJdbcException(e.getLocalizedMessage());
        }
    }

    @Override
    public String executeQueryJson(KylinSearch kylinSearch) {
        return apiClient.executeQueryJson(kylinSearch);
    }

    @Override
    public synchronized boolean checkAuthentication() {
        return apiClient.checkAuthentication();
    }

    @Override
    public synchronized boolean doAuthentication() {
        return apiClient.doAuthentication();
    }

    @Override
    public void close() throws IOException {
        apiClient.close();
    }

}
