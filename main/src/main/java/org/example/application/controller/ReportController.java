package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.ReportDTO;
import org.example.application.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.Optional;

@Controller
public class ReportController {
    private final ReportService reportService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(ReportService reportService, ObjectMapper objectMapper) {
        this.reportService = reportService;
        this.objectMapper = objectMapper;
    }

    public void save(ReportDTO reportDTO) {
        reportService.save(reportDTO);
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<ReportDTO> reportDTOOptional = reportService.getById(id);
        return objectMapper.writeValueAsString(reportDTOOptional.orElse(null));
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.getAll());

    }

    public void update(int id, ReportDTO reportDTO) {
        reportService.update(id, reportDTO);
    }

    public void delete(int id) {
        reportService.delete(id);
    }
}

