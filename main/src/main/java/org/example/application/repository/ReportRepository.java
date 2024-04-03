package org.example.application.repository;

import org.example.application.model.Report;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepository {
    List<Report> reports = new ArrayList<>();

    public List<Report> getAll() {
        return new ArrayList<>(reports);
    }

    public void save(Report report) {
        reports.add(report);
    }

    public Optional<Report> getById(int id) {
        return reports.stream().filter(report -> report.getId() == id).findFirst();
    }

    public void update(int id, Report updatedreport) {
        Optional<Report> optionalReport = getById(id);
        optionalReport.ifPresent(report -> report.setText(updatedreport.getText()).setTitle(updatedreport.getTitle()));
    }

    public void delete(int id) {
        reports.removeIf(report -> report.getId() == id);
    }
}
