package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
@WebAppConfiguration
public class ReportRepositoryImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    public void testGetAll() {
        List<Report> reports = reportRepository.getAll();
        assertThat(reports).isNotEmpty();
    }

    @Test
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    public void testGetById() {
        Optional<Report> report = reportRepository.getById(1L);
        assertTrue(report.isPresent());
        assertEquals("Report 1", report.get().getTitle());
    }

    @Test
    @DirtiesContext
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteReport() {
        Optional<Report> report = reportRepository.getById(1L);
        assertTrue(report.isPresent());

        reportRepository.delete(1L);
        assertFalse(reportRepository.getById(1L).isPresent());
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdate() {
        Optional<Report> report = reportRepository.getById(1L);
        assertTrue(report.isPresent());

        Report updatedReport = report.get();
        updatedReport.setTitle("Updated Report");
        reportRepository.update(1L, updatedReport);

        Optional<Report> updatedReportOptional = reportRepository.getById(1L);
        assertTrue(updatedReportOptional.isPresent());
        assertEquals("Updated Report", updatedReportOptional.get().getTitle());
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetReportsByUserJpql() {
        User user = new User();
        user.setId(1L);
        List<Report> foundReports = reportRepository.getReportsByUserJpql(user);
        assertThat(foundReports).isNotEmpty();
        assertThat(foundReports).hasSize(1);
        assertThat(foundReports.getFirst().getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetReportsByUserCriteria() {
        User user = new User();
        user.setId(1L);
        List<Report> foundReports = reportRepository.getReportsByUserCriteria(user);
        assertThat(foundReports).isNotEmpty();
        assertThat(foundReports).hasSize(1);
        assertThat(foundReports.getFirst().getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetReportsByUserJpqlFetch() {
        User user = new User();
        user.setId(1L);
        List<Report> foundReports = reportRepository.getReportsByUserJpqlFetch(user);
        assertThat(foundReports).isNotEmpty();
        assertThat(foundReports).hasSize(1);
        assertThat(foundReports.getFirst().getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetReportsByUserCriteriaFetch() {
        User user = new User();
        user.setId(1L);
        List<Report> foundReports = reportRepository.getReportsByUserCriteriaFetch(user);
        assertThat(foundReports).isNotEmpty();
        assertThat(foundReports).hasSize(1);
        assertThat(foundReports.getFirst().getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetReportsByUserEntityGraph() {
        User user = new User();
        user.setId(1L);
        List<Report> foundReports = reportRepository.getReportsByUserEntityGraph(user);
        assertThat(foundReports).isNotEmpty();
        assertThat(foundReports).hasSize(1);
        assertThat(foundReports.getFirst().getUser().getId()).isEqualTo(1L);
    }
}
