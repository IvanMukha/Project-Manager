package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.User;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    List<Report> getAll(Long projectId, Long taskId);

    Optional<Report> getById(Long projectId, Long taskId, Long id);

    Report save(Report report);

    Optional<Report> update(Long projectId, Long taskId, Long id, Report updatedEntity);

    void delete(Long projectId, Long taskId, Long id);

    List<Report> getReportsByUserJpql(User user);

    List<Report> getReportsByUserCriteria(User user);

    List<Report> getReportsByUserJpqlFetch(User user);

    List<Report> getReportsByUserCriteriaFetch(User user);

    List<Report> getReportsByUserEntityGraph(User user);
}
