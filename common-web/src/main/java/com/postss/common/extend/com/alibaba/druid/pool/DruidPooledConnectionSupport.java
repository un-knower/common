package com.postss.common.extend.com.alibaba.druid.pool;

import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidPooledConnectionSupport extends DruidPooledConnection {

    public DruidPooledConnectionSupport(DruidPooledConnection druidPooledConnection) {
        super(druidPooledConnection.getConnectionHolder());
    }

}
