package com.ivan.projectmanager.exeptions;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(Long id, Class<?> entityType) {
        super(formatErrorMessage(id, entityType));
    }

    private static String formatErrorMessage(Long id, Class<?> entityType) {
        String typeName = entityType.getSimpleName();
        return typeName + " with id: " + id + " not found";
    }
}