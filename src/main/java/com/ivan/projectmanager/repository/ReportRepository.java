package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends CrudRepository<Report, Long> {

    Page<Report> getAll(Long projectId, Long taskId, Pageable pageable);

    Optional<Report> getById(Long projectId, Long taskId, Long id);

    Report save(Report report);

    Optional<Report> update(Long projectId, Long taskId, Long id, Report updatedEntity);

    void delete(Long projectId, Long taskId, Long id);

    List<Report> getReportsByUser(User user);

}
