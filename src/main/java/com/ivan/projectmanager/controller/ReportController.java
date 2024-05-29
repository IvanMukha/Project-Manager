package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<ReportDTO>> getAll(@PathVariable("projectId") Long projectId,
                                                  @PathVariable("taskId") Long taskId,
                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ReportDTO> reportsPage = reportService.getAll(projectId, taskId, page, size);
        return ResponseEntity.ok().body(reportsPage);
    }


    @PostMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReportDTO> save(@PathVariable("projectId") Long projectId,
                                          @PathVariable("taskId") Long taskId,
                                          @RequestBody @Valid ReportDTO reportDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ReportDTO savedReport = reportService.save(projectId, taskId, reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReportDTO> getById(@PathVariable("projectId") Long projectId,
                                             @PathVariable("taskId") Long taskId,
                                             @PathVariable("id") Long id) {
        Optional<ReportDTO> reportDTOOptional = reportService.getById(projectId, taskId, id);
        return reportDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReportDTO> update(@PathVariable("projectId") Long projectId,
                                            @PathVariable("taskId") Long taskId,
                                            @PathVariable("id") Long id,
                                            @RequestBody @Valid ReportDTO reportDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<ReportDTO> updatedReport = reportService.update(projectId, taskId, id, reportDTO);
        return updatedReport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("id") Long id) {
        reportService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }
}

