package com.ivan.projectManager.service.impl;

import com.ivan.projectManager.dto.ReportDTO;
import com.ivan.projectManager.model.Report;
import com.ivan.projectManager.repository.ReportRepository;
import com.ivan.projectManager.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ReportDTO save(ReportDTO reportDTO) {
        return mapReportToDTO(reportRepository.save(mapDTOToReport(reportDTO)));
    }

    public Optional<ReportDTO> getById(int id) {
        Optional<Report> reportOptional = reportRepository.getById(id);
        return reportOptional.map(this::mapReportToDTO);
    }


    public Optional<ReportDTO> update(int id, ReportDTO updatedReportDTO) {
        Optional<Report> reportOptional = reportRepository.update(id, mapDTOToReport(updatedReportDTO));
        return reportOptional.map(this::mapReportToDTO);
    }

    public void delete(int id) {
        reportRepository.delete(id);
    }

    private Report mapDTOToReport(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    private ReportDTO mapReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}

