package org.example.application.repository;

import org.example.application.RepositoryInterfaces.ReportRepositoryInterface;
import org.example.application.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepository implements ReportRepositoryInterface {
    private static final Logger log = LoggerFactory.getLogger(ReportRepository.class);
    List<Report> reports = new ArrayList<>();

    public List<Report> getAll() {
        return reports;
    }

    public Report save(Report report) {
        reports.add(report);
        return report;
    }

    public Optional<Report> getById(int id) {
        Optional<Report> optionalReport = reports.stream()
                .filter(report -> report.getId() == id)
                .findFirst();
        if (optionalReport.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalReport;
    }

    public Optional<Report> update(int id, Report updatedreport) {
        Optional<Report> optionalReport = getById(id);
        optionalReport.ifPresent(report -> report.setText(updatedreport.getText()).setTitle(updatedreport.getTitle()));
        return optionalReport;
    }

    public void delete(int id) {
        reports.removeIf(report -> report.getId() == id);
    }
}
