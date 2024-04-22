package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.ReportDTO;
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
        return mapReportToDTO(reportRepository.save(mapDTOToReport(reportDTO)));
    }

    public Optional<ReportDTO> getById(Long id) {
        Optional<Report> reportOptional = reportRepository.getById(id);
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public Optional<ReportDTO> update(Long id, ReportDTO updatedReportDTO) {
        Optional<Report> reportOptional = reportRepository.update(id, mapDTOToReport(updatedReportDTO));
        return reportOptional.map(this::mapReportToDTO);
    }

    @Transactional
    public void delete(Long id) {
        reportRepository.delete(id);
    }

    private Report mapDTOToReport(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    private ReportDTO mapReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}

