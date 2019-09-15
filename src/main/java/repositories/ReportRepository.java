package repositories;

import domain.Report;
import domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("select r from Report r where r.reviewer.id = ?1")
    Collection<Report> getReportsMadeByReviewer(int reviewerId);

    @Query("select count(r) from Reviewer r where ?1 member of r.submissions")
    int countSubmissionOnReviewers(Submission submission);

    @Query("select r from Report r where r.submission.id = ?1")
    Collection<Report> getReportsOfSubmission(int submissionId);

}
