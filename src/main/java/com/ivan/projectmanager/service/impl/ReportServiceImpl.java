package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.repository.ReportRepository;
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

    @Autowired
    public ReportServiceImpl(ModelMapper modelMapper, ReportRepository reportRepository) {
        this.modelMapper = modelMapper;
        this.reportRepository = reportRepository;
    }

    public List<ReportDTO> getAll() {
        return reportRepository.getAll().stream().map(this::mapReportToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReportDTO save(ReportDTO reportDTO) {
        checkReportDTO(reportDTO);
        return mapReportToDTO(reportRepository.save(mapDTOToReport(reportDTO)));
    }

    public Optional<ReportDTO> getById(Long id) {
        checkId(id);
        Optional<Report> reportOptional = reportRepository.getById(id);
        if (reportOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Report with id " + id + " not found");
        }
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public Optional<ReportDTO> update(Long id, ReportDTO updatedReportDTO) {
        checkId(id);
        if (updatedReportDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Report title cannot be null");
        }
        if (updatedReportDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Report title cannot be empty");
        }
        Optional<Report> reportOptional = reportRepository.update(id, mapDTOToReport(updatedReportDTO));
        if (reportOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Report with id " + id + " not found");
        }
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        reportRepository.delete(id);
    }

    private void checkReportDTO(ReportDTO reportDTO) {
        if (reportDTO == null) {
            throw new HandleCustomNullPointerException("ReportDTO is null");
        }
        if (reportDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Report title cannot be null");
        }
        if (reportDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Report title cannot be empty");
        }
        if (reportDTO.getTaskId() == null) {
            throw new HandleCustomNullPointerException("Task id cannot be null");
        }
        if (reportDTO.getUserId() == null) {
            throw new HandleCustomNullPointerException("User id cannot be null");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Report id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Report id must be greater than 0");
        }
    }


    private Report mapDTOToReport(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    private ReportDTO mapReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}

