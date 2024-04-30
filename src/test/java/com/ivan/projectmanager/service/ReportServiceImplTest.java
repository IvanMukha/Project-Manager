package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ReportDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.ReportRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

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
public class ReportServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void testGetAllReports() {
        Project project = new Project().setId(1L).setTitle("project");
        Task task = new Task().setId(1L).setProject(project);
        Report report = new Report().setTitle("report1").setTask(task);
        Report report2 = new Report().setTitle("report2").setTask(task);
        ReportDTO reportDTO = new ReportDTO().setTitle("report1").setTaskId(1L);
        ReportDTO reportDTO2 = new ReportDTO().setTitle("report2").setTaskId(1L);
        when(modelMapper.map(report, ReportDTO.class)).thenReturn(reportDTO);
        when(modelMapper.map(report2, ReportDTO.class)).thenReturn(reportDTO2);
        when(reportRepository.getAll(1L, 1L)).thenReturn(List.of(report, report2));
        List<ReportDTO> result = reportService.getAll(1L, 1L);
        assertEquals(2, result.size());
        assertEquals(report.getTitle(), result.get(0).getTitle());
        assertEquals(report2.getTitle(), result.get(1).getTitle());
        verify(reportRepository).getAll(1L, 1L);
    }

    @Test
    void testSaveReport() {
        Project project = new Project().setId(1L).setTitle("project");
        User user = new User().setId(1L);
        Task task = new Task().setId(1L).setProject(project);
        Report report = new Report().setTitle("Test Report").setTask(task).setUser(user);
        ReportDTO reportDTO = new ReportDTO().setTitle("Test Report").setTaskId(1L).setUserId(1L);
        when(modelMapper.map(report, ReportDTO.class)).thenReturn(reportDTO);
        when(modelMapper.map(reportDTO, Report.class)).thenReturn(report);
        when(taskRepository.getById(1L, 1L)).thenReturn(Optional.ofNullable(task));
        when(reportRepository.save(report)).thenReturn(report);
        ReportDTO savedReportDTO = reportService.save(1L, 1L, reportDTO);
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
        when(reportRepository.getById(1L, 1L, id)).thenReturn(Optional.of(report));
        Optional<ReportDTO> result = reportService.getById(1L, 1L, id);
        assertTrue(result.isPresent());
        assertEquals("title", result.get().getTitle());
        verify(reportRepository).getById(1L, 1L, id);
    }

    @Test
    void testDeleteReport() {
        reportService.delete(1L, 1L, 1L);
        verify(reportRepository).delete(1L, 1L, 1L);
    }
}
