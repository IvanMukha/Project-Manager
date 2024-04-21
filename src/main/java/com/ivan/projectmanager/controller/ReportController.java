package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.service.ReportService;
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

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.getAll());
    }

    public String save(ReportDTO reportDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.save(reportDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<ReportDTO> reportDTOOptional = reportService.getById(id);
        return objectMapper.writeValueAsString(reportDTOOptional.orElse(null));
    }

    public String update(Long id, ReportDTO reportDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.update(id, reportDTO));
    }

    public void delete(Long id) {
        reportService.delete(id);
    }
}
