package com.ivan.projectmanager.utils;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectionHolder {

    private final DataSource dataSource;
    private final ThreadLocal<Boolean> transactionStatus = ThreadLocal.withInitial(() -> false);
    private final Map<Thread, Connection> threadConnectionMap = new ConcurrentHashMap<>();
    private final Map<Thread, Boolean> threadTransactionMap = new ConcurrentHashMap<>();
    private final List<Connection> connectionPool = new ArrayList<>();

    public ConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        Thread currentThread = Thread.currentThread();

        if (threadConnectionMap.containsKey(currentThread) && !threadConnectionMap.get(currentThread).isClosed()) {
            for (Connection conn : threadConnectionMap.values()) {
                if (conn.isClosed() && (isTransactionActive() || !threadTransactionMap.get(Thread.currentThread()))) {
                    throw new RuntimeException("Thread with transaction was closed");
                } else {
                    return threadConnectionMap.get(currentThread);
                }
            }
        }
        return getConnectionFromPool();
    }

    public boolean isTransactionActive() {
        return transactionStatus.get();
    }

    public void setTransactionStatus(boolean status) {
        transactionStatus.set(status);
    }

    public void releaseConnection() {
        Thread currentThread = Thread.currentThread();
        Connection connection = threadConnectionMap.get(currentThread);
        if (connection != null) {
            try {
                if (threadTransactionMap.get(currentThread) && connection.isClosed()) {
                    throw new RuntimeException("Connection used in transaction was closed");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to release connection", e);
            }
            connectionPool.add(connection);
            threadConnectionMap.remove(currentThread);
            threadTransactionMap.remove(currentThread);
        }
    }

    @PreDestroy
    public void closeAllConnections() {
        for (Connection connection : threadConnectionMap.values()) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to close all connections", e);
                }
            }
        }
        threadConnectionMap.clear();
        threadTransactionMap.clear();
        connectionPool.clear();
    }

    private Connection getConnectionFromPool() throws SQLException {
        if (connectionPool.isEmpty()) {
            return createConnection();
        } else {
            return connectionPool.remove(connectionPool.size() - 1);
        }
    }

    private Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
