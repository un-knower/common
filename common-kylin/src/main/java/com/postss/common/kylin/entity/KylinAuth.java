package com.postss.common.kylin.entity;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.util.Assert;

import com.postss.common.enums.CharsetEnum;
import com.postss.common.util.EncryptUtil;

/**
 * 用户认证实体类
 * @className KylinAuth
 * @author jwSun
 * @date 2017年7月20日 下午4:38:55
 * @version 1.0.0
 */
public class KylinAuth {

    /**用户名**/
    private String name;
    /**密码**/
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public KylinAuth(String name, String password) {
        super();
        Assert.notNull(name, "name cant null");
        Assert.notNull(password, "password cant null");
        this.name = name;
        this.password = password;
    }

    /**
     * 获得认证信息
     * @return
     */
    public String getAuthentication() {
        return "Basic " + EncryptUtil.encodeBase64((name + ":" + password).getBytes(CharsetEnum.UTF_8.getCharset()));
    }

    /**
     * 获得认证信息请求头
     * @return Header
     */
    public Header getAuthenticationHeader() {
        return new BasicHeader("Authorization", getAuthentication());
    }

    @Override
    public String toString() {
        return "KylinAuth [name=" + name + ", password=" + password + "]";
    }

}
