package com.postss.common.extend.com.alibaba.druid.pool;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceSupport extends DruidDataSource {

    private static final long serialVersionUID = 1036506833586408015L;

    //private final static Log LOG = LogFactory.getLog(DruidDataSource.class);

    /*public DruidPooledConnection getConnectionDirect(long maxWaitMillis) throws SQLException {
        DruidPooledConnection polledConnection = super.getConnectionDirect(maxWaitMillis);
        if (isRemoveAbandoned()) {
            synchronized (activeConnections) {
                activeConnections.remove(poolableConnection, PRESENT);
            }
        }
        synchronized (activeConnections) {
            activeConnections.remove(polledConnection);
        }
    
    }*/
}
