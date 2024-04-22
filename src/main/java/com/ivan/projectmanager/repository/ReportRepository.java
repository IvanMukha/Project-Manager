package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.User;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {
    List<Report> getReportsByUserJpql(User user);

    List<Report> getReportsByUserCriteria(User user);

    List<Report> getReportsByUserJpqlFetch(User user);

    List<Report> getReportsByUserCriteriaFetch(User user);

    List<Report> getReportsByUserEntityGraph(User user);
}
