package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ReportDTO;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<ReportDTO> getAll(Long projectId, Long taskId);

    ReportDTO save(Long projectId, Long taskId, ReportDTO reportDTO);

    Optional<ReportDTO> getById(Long projectId, Long taskId, Long id);

    Optional<ReportDTO> update(Long projectId, Long taskId, Long id, ReportDTO updatedReportDTO);

    void delete(Long projectId, Long taskId, Long id);
}
