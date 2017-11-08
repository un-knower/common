package com.postss.common.kylin.core;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.postss.common.kylin.GlobalForKylinUtil;
import com.postss.common.kylin.client.KylinClient;
import com.postss.common.kylin.client.SimpleKylinClient;
import com.postss.common.kylin.entity.KylinAuth;

/**
 * 默认kylin操作管理器
 * @className DefaultKylinManager
 * @author jwSun
 * @date 2017年7月27日 下午7:11:14
 * @version 1.0.0
 */
@Component("kylinManager")
public class DefaultKylinManager implements KylinManager {

    {
        Assert.notNull(GlobalForKylinUtil.kylinUrl, "未获取到配置项：" + GlobalForKylinUtil.KYLIN_SERVER_URL);
        Assert.notNull(GlobalForKylinUtil.kylinUserName, "未获取到配置项：" + GlobalForKylinUtil.KYLIN_SERVER_NAME);
        Assert.notNull(GlobalForKylinUtil.kylinPassword, "未获取到配置项：" + GlobalForKylinUtil.KYLIN_SERVER_PASSWORD);
    }

    private KylinClient kylinClient = new SimpleKylinClient(GlobalForKylinUtil.kylinUrl,
            new KylinAuth(GlobalForKylinUtil.kylinUserName, GlobalForKylinUtil.kylinPassword));

    /*private KylinClient kylinClient = new KylinJdbcClient(GlobalForKylinUtil.kylinUrl,
            new KylinAuth(GlobalForKylinUtil.kylinUserName, GlobalForKylinUtil.kylinPassword));*/

    public KylinClient getKylinClient() {
        return kylinClient;
    }

    public void setKylinClient(KylinClient kylinClient) {
        this.kylinClient = kylinClient;
    }

}