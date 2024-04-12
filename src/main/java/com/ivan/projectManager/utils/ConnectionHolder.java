package com.ivan.projectManager.utils;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionHolder {

    private final DataSource dataSource;
    private final ThreadLocal<Boolean> transactionStatus = ThreadLocal.withInitial(() -> false);
    private final Map<Thread, Connection> threadConnectionMap = new HashMap<>();
    private final Map<Thread, Boolean> threadTransactionMap = new HashMap<>();

    public ConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        Thread currentThread = Thread.currentThread();
        Connection connection = threadConnectionMap.get(currentThread);

        if (connection == null || connection.isClosed()) {
            if (isTransactionActive()) {
                for (Connection conn : threadConnectionMap.values()) {
                    if (!conn.isClosed() && threadTransactionMap.get(Thread.currentThread())) {
                        connection = conn;
                        break;
                    }
                }
            } else {
                for (Connection conn : threadConnectionMap.values()) {
                    if (!conn.isClosed()) {
                        connection = conn;
                        break;
                    }
                }
            }

            if (connection == null) {
                connection = dataSource.getConnection();
                threadConnectionMap.put(currentThread, connection);
                threadTransactionMap.put(currentThread, isTransactionActive());
            }
        }

        return connection;
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
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to release connection", e);
            }
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
    }
}
