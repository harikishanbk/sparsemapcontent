/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.lite.jdbc.mysql;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import org.sakaiproject.nakamura.api.lite.Configuration;
import org.sakaiproject.nakamura.lite.DummyStorageCacheManager;
import org.sakaiproject.nakamura.lite.storage.jdbc.JDBCStorageClientPool;
import org.sakaiproject.nakamura.lite.storage.spi.monitor.StatsServiceFactroyImpl;
import org.sakaiproject.nakamura.lite.storage.spi.monitor.StatsServiceImpl;

public class MysqlSetup {

    private static JDBCStorageClientPool clientPool = null;

    public synchronized static JDBCStorageClientPool createClientPool(Configuration configuration) {
        try {
            JDBCStorageClientPool connectionPool = new JDBCStorageClientPool();
            connectionPool.statsServiceFactroy = new StatsServiceFactroyImpl();
            connectionPool.storageManagerCache = new DummyStorageCacheManager();
            Builder<String, Object> b = ImmutableMap.builder();
            b.put(JDBCStorageClientPool.CONNECTION_URL,"jdbc:mysql://127.0.0.1:3306/sakai22?useUnicode=true&amp;characterEncoding=UTF-8");
            b.put(JDBCStorageClientPool.JDBC_DRIVER, "com.mysql.jdbc.Driver");
            b.put("username", "sakai22");
            b.put("password", "sakai22");
            b.put("store-base-dir", "target/store");
            b.put(Configuration.class.getName(), configuration);
            connectionPool
                    .activate(b.build());
            return connectionPool;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public  synchronized static JDBCStorageClientPool getClientPool(Configuration configuration) {
        if ( clientPool == null) {
            clientPool = createClientPool(configuration);
        }
        return clientPool;
    }

}
