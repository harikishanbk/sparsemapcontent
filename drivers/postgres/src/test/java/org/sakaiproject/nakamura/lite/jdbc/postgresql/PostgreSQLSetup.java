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
package org.sakaiproject.nakamura.lite.jdbc.postgresql;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import org.sakaiproject.nakamura.api.lite.Configuration;
import org.sakaiproject.nakamura.lite.DummyStorageCacheManager;
import org.sakaiproject.nakamura.lite.storage.jdbc.JDBCStorageClientPool;
import org.sakaiproject.nakamura.lite.storage.spi.monitor.StatsServiceFactroyImpl;

public class PostgreSQLSetup {

    private static JDBCStorageClientPool clientPool = null;

    public synchronized static JDBCStorageClientPool createClientPool(Configuration configuration) {
        try {
            JDBCStorageClientPool connectionPool = new JDBCStorageClientPool();
            connectionPool.storageManagerCache = new DummyStorageCacheManager();
            connectionPool.statsServiceFactroy = new StatsServiceFactroyImpl();
            Builder<String, Object> b = ImmutableMap.builder();
            b.put(JDBCStorageClientPool.CONNECTION_URL,"jdbc:postgresql://localhost/nak");
            b.put(JDBCStorageClientPool.JDBC_DRIVER, "org.postgresql.Driver");
            b.put("username", "nakamura");
            b.put("password", "nakamura");
            b.put("store-base-dir", "target/store");
            b.put(Configuration.class.getName(), configuration);
            connectionPool
                    .activate(b.build());
            return connectionPool;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public synchronized static JDBCStorageClientPool getClientPool(Configuration configuration) {
        if ( clientPool == null) {
            clientPool = createClientPool(configuration);
        }
        return clientPool;
    }   

}
