package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/reports")
public class ReportController {
    private final ReportService reportService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(ReportService reportService, ObjectMapper objectMapper) {
        this.reportService = reportService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.getAll());
    }

    @PostMapping("/new")
    public String save(ReportDTO reportDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.save(reportDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<ReportDTO> reportDTOOptional = reportService.getById(id);
        return objectMapper.writeValueAsString(reportDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, ReportDTO reportDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reportService.update(id, reportDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        reportService.delete(id);
    }
}

