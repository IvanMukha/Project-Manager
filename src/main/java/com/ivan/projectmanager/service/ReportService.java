package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ReportDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ReportService {
    Page<ReportDTO> getAll(Long projectId, Long taskId, Integer page, Integer size);

    ReportDTO save(Long projectId, Long taskId, ReportDTO reportDTO);

    Optional<ReportDTO> getById(Long projectId, Long taskId, Long id);

    Optional<ReportDTO> update(Long projectId, Long taskId, Long id, ReportDTO updatedReportDTO);

    void delete(Long projectId, Long taskId, Long id);
}
