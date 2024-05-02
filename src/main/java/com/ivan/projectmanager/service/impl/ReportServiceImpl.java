package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.ReportRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ModelMapper modelMapper;
    private final ReportRepository reportRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ReportServiceImpl(ModelMapper modelMapper, ReportRepository reportRepository, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;
    }

    public List<ReportDTO> getAll(Long projectId, Long taskId) {
        return reportRepository.getAll(projectId, taskId).stream().map(this::mapReportToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReportDTO save(Long projectId, Long taskId, ReportDTO reportDTO) {
        Task task = taskRepository.getById(projectId, taskId)
                .orElseThrow(() -> new CustomNotFoundException(taskId, Task.class));

        if (!task.getProject().getId().equals(projectId)) {
            throw new CustomNotFoundException(projectId, Project.class);
        }
        reportDTO.setTaskId(taskId);
        return mapReportToDTO(reportRepository.save(mapDTOToReport(reportDTO)));
    }

    public Optional<ReportDTO> getById(Long projectId, Long taskId, Long id) {
        Optional<Report> reportOptional = reportRepository.getById(projectId, taskId, id);
        if (reportOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Report.class);
        }
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public Optional<ReportDTO> update(Long projectId, Long taskId, Long id, ReportDTO updatedReportDTO) {

        Optional<Report> reportOptional = reportRepository.update(projectId, taskId, id, mapDTOToReport(updatedReportDTO));
        if (reportOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Report.class);
        }
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long id) {
        reportRepository.delete(projectId, taskId, id);
    }

    private Report mapDTOToReport(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    private ReportDTO mapReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}

