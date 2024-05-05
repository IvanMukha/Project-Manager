package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/reports")
@Validated
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ReportDTO>> getAll(@PathVariable("projectId") Long projectId,
                                                  @PathVariable("taskId") Long taskId) {
        List<ReportDTO> reports = reportService.getAll(projectId, taskId);
        return ResponseEntity.ok().body(reports);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<ReportDTO> save(@PathVariable("projectId") Long projectId,
                                          @PathVariable("taskId") Long taskId,
                                          @RequestBody ReportDTO reportDTO) {
        ReportDTO savedReport = reportService.save(projectId, taskId, reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getById(@PathVariable("projectId") Long projectId,
                                             @PathVariable("taskId") Long taskId,
                                             @PathVariable("id") Long id) {
        Optional<ReportDTO> reportDTOOptional = reportService.getById(projectId, taskId, id);
        return reportDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> update(@PathVariable("projectId") Long projectId,
                                            @PathVariable("taskId") Long taskId,
                                            @PathVariable("id") Long id,
                                            @RequestBody ReportDTO reportDTO) {
        Optional<ReportDTO> updatedReport = reportService.update(projectId, taskId, id, reportDTO);
        return updatedReport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("id") Long id) {
        reportService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }
}

