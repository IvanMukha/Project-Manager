package com.ivan.projectmanager.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T> {
    T map(ResultSet resultSet) throws SQLException;

    List<T> mapAll(ResultSet resultSet) throws SQLException;
}
