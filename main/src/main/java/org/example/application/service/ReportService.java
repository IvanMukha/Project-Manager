package org.example.application.service;

import org.example.application.dto.ReportDTO;
import org.example.application.model.Report;
import org.example.application.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ModelMapper modelMapper;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ModelMapper modelMapper, ReportRepository reportRepository) {
        this.modelMapper = modelMapper;
        this.reportRepository = reportRepository;
    }

    public List<ReportDTO> getAll() {
        return reportRepository.getAll().stream().map(this::mapReportToDTO).collect(Collectors.toList());
    }

    public void save(ReportDTO reportDTO) {
        reportRepository.save(mapDTOToReport(reportDTO));
    }

    public Optional<ReportDTO> getById(int id) {
        Optional<Report> reportOptional = reportRepository.getById(id);
        return reportOptional.map(this::mapReportToDTO);
    }

    public void update(int id, ReportDTO updatedReportDTO) {
        reportRepository.update(id, mapDTOToReport(updatedReportDTO));
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

