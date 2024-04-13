package com.ivan.projectManager.repository.repositoryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface RowMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
    List<T> mapAll(ResultSet resultSet) throws SQLException;
}
