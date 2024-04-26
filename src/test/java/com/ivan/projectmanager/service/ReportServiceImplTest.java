package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.ReportRepository;
import com.ivan.projectmanager.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
@WebAppConfiguration
public class ReportServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void testGetAllReports() {
        Report report = new Report().setTitle("report1");
        Report report2 = new Report().setTitle("report2");
        ReportDTO reportDTO = new ReportDTO().setTitle("report1");
        ReportDTO reportDTO2 = new ReportDTO().setTitle("report2");
        when(modelMapper.map(report, ReportDTO.class)).thenReturn(reportDTO);
        when(modelMapper.map(report2, ReportDTO.class)).thenReturn(reportDTO2);
        when(reportRepository.getAll()).thenReturn(List.of(report, report2));
        List<ReportDTO> result = reportService.getAll();
        assertEquals(2, result.size());
        assertEquals(report.getTitle(), result.get(0).getTitle());
        assertEquals(report2.getTitle(), result.get(1).getTitle());
        verify(reportRepository).getAll();
    }

    @Test
    void testSaveReport() {
        User user = new User().setId(1L);
        Task task = new Task().setId(1L);
        Report report = new Report().setTitle("Test Report").setTask(task).setUser(user);
        ReportDTO reportDTO = new ReportDTO().setTitle("Test Report").setTaskId(1L).setUserId(1L);
        when(modelMapper.map(report, ReportDTO.class)).thenReturn(reportDTO);
        when(modelMapper.map(reportDTO, Report.class)).thenReturn(report);
        when(reportRepository.save(report)).thenReturn(report);
        ReportDTO savedReportDTO = reportService.save(reportDTO);
        assertNotNull(savedReportDTO);
        assertEquals(report.getTitle(), savedReportDTO.getTitle());
        verify(reportRepository).save(any());
    }

    @Test
    void testGetReportById() {
        long id = 1L;
        Report report = new Report().setTitle("title");
        ReportDTO reportDTO = new ReportDTO().setTitle("title");
        when(modelMapper.map(report, ReportDTO.class)).thenReturn(reportDTO);
        when(reportRepository.getById(id)).thenReturn(Optional.of(report));
        Optional<ReportDTO> result = reportService.getById(id);
        assertTrue(result.isPresent());
        assertEquals("title", result.get().getTitle());
        verify(reportRepository).getById(id);
    }

    @Test
    void testDeleteReport() {
        long id = 1L;
        reportService.delete(id);
        verify(reportRepository).delete(id);
    }
}
