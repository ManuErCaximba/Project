package repositories;

import domain.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    /*Q1*/
    @Query("select avg(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
    Double getAvgSubmissionPerConference();

    @Query("select min(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
    Double getMinSubmissionPerConference();

    @Query("select max(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
    Double getMaxSubmissionPerConference();

    @Query("select stddev(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
    Double getStddevSubmissionPerConference();

    /*Q2*/
    @Query("select avg(c.registrations.size)*1.0 from Conference c")
    Double getAvgNumberRegistrationConference();

    @Query("select min(c.registrations.size)*1.0 from Conference c")
    Double getMinNumberRegistrationConference();

    @Query("select max(c.registrations.size)*1.0 from Conference c")
    Double getMaxNumberRegistrationConference();

    @Query("select stddev(c.registrations.size)*1.0 from Conference c")
    Double getStddevNumberRegistrationConference();

    /*Q3*/
    @Query("select avg(c.fee) from Conference c")
    Double getAvgConferenceFee();

    @Query("select min(c.fee) from Conference c")
    Double getMinConferenceFee();

    @Query("select max(c.fee) from Conference c")
    Double getMaxConferenceFee();

    @Query("select stddev(c.fee) from Conference c")
    Double getStddevConferenceFee();

    /*Q4*/
    @Query("select c.startDate from Conference c")
    Collection<Date> getStartDates();

    @Query("select c.endDate from Conference c")
    Collection<Date> getEndDates();

    @Query("select max(c.startDate) from Conference c")
    Date getMaxStartDate();

    @Query("select max(c.endDate) from Conference c")
    Date getMaxEndDate();

    @Query("select min(c.startDate) from Conference c")
    Date getMinStartDate();

    @Query("select min(c.endDate) from Conference c")
    Date getMinEndDate();

    /*Q5*/
    @Query("select avg(1.0*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
    Double getAvgConferencesPerCategory();

    @Query("select min(1.0*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
    Double getMinConferencesPerCategory();

    @Query("select max(1.0*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
    Double getMaxConferencesPerCategory();

    @Query("select stddev(1.0*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
    Double getStddevConferencesPerCategory();

    /*Q6*/
    @Query("select avg(c.comments.size)*1.0 from Conference c")
    Double getAvgNumberCommentsConference();

    @Query("select min(c.comments.size)*1.0 from Conference c")
    Double getMinNumberCommentsConference();

    @Query("select max(c.comments.size)*1.0 from Conference c")
    Double getMaxNumberCommentsConference();

    @Query("select stddev(c.comments.size)*1.0 from Conference c")
    Double getStddevNumberCommentsConference();

    /*Q7*/
    @Query("select avg(a.comments.size)*1.0 from Activity a")
    Double getAvgNumberCommentsActivity();

    @Query("select min(a.comments.size)*1.0 from Activity a")
    Double getMinNumberCommentsActivity();

    @Query("select max(a.comments.size)*1.0 from Activity a")
    Double getMaxNumberCommentsActivity();

    @Query("select stddev(a.comments.size)*1.0 from Activity a")
    Double getStddevNumberCommentsActivity();

}
