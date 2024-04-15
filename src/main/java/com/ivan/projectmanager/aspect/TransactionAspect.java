package com.ivan.projectmanager.aspect;

import com.ivan.projectmanager.utils.ConnectionHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Aspect
public class TransactionAspect {

    private final ConnectionHolder connectionHolder;

    @Autowired
    public TransactionAspect(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Around("@annotation(com.ivan.projectmanager.annotations.Transaction)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection connection = connectionHolder.getConnection();
        connectionHolder.setTransactionStatus(true);
        boolean previousAutoCommit = false;
        try {
            previousAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Object result = joinPoint.proceed();
            connection.commit();
            return result;
        } catch (RuntimeException runtimeException) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("An error occurred while rolling back the transaction", e);
            }
            throw runtimeException;
        } finally {
            try {
                connection.setAutoCommit(previousAutoCommit);
                connectionHolder.setTransactionStatus(false);
                connectionHolder.releaseConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit state", e);
            }
        }
    }
}