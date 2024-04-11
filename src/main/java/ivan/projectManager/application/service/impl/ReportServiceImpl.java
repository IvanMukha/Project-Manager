package ivan.projectManager.application.service.impl;

import ivan.projectManager.application.dto.ReportDTO;
import ivan.projectManager.application.model.Report;
import ivan.projectManager.application.repository.ReportRepository;
import ivan.projectManager.application.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ModelMapper modelMapper;
    private final ReportRepository reportRepositoryImpl;

    @Autowired
    public ReportServiceImpl(ModelMapper modelMapper, ReportRepository reportRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.reportRepositoryImpl = reportRepositoryImpl;
    }

    public List<ReportDTO> getAll() {
        return reportRepositoryImpl.getAll().stream().map(this::mapReportToDTO).collect(Collectors.toList());
    }

    public ReportDTO save(ReportDTO reportDTO) {
        return mapReportToDTO(reportRepositoryImpl.save(mapDTOToReport(reportDTO)));
    }

    public Optional<ReportDTO> getById(int id) {
        Optional<Report> reportOptional = reportRepositoryImpl.getById(id);
        return reportOptional.map(this::mapReportToDTO);
    }


    public Optional<ReportDTO> update(int id, ReportDTO updatedReportDTO) {
        Optional<Report> reportOptional = reportRepositoryImpl.update(id, mapDTOToReport(updatedReportDTO));
        return reportOptional.map(this::mapReportToDTO);
    }

    public void delete(int id) {
        reportRepositoryImpl.delete(id);
    }

    private Report mapDTOToReport(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    private ReportDTO mapReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}

