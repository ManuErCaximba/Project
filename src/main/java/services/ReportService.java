package services;

import domain.Actor;
import domain.Report;
import domain.Reviewer;
import domain.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ReportRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ReportService {

    //Managed Repositories
    @Autowired
    private ReportRepository reportRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Report create(int submissionId){
        Report report = new Report();
        report.setSubmission(this.submissionService.findOne(submissionId));

        return report;
    }

    public Report findOne(int id){
        return this.reportRepository.findOne(id);
    }

    public Collection<Report> findAll(){
        return this.reportRepository.findAll();
    }

    public Report save(Report report){
        Report result;
        final Actor actor = this.actorService.getActorLogged();
        final Reviewer reviewer = this.reviewerService.findOne(actor.getId());

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("REVIEWER"));
        Assert.notNull(reviewer);

        if(report.getId() == 0)
            report.setReviewer(reviewer);

        result = this.reportRepository.save(report);

        Collection<Submission> submissions = reviewer.getSubmissions();
        submissions.remove(result.getSubmission());
        reviewer.setSubmissions(submissions);

        this.reviewerService.save(reviewer);

        return result;
    }

    public void delete(Report report){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("REVIEWER"));
        Assert.notNull(report);
        this.reportRepository.delete(report);
    }

    //Other Methods
    public Report reconstruct(final Report report, final BindingResult binding) {
        Report result;

        result = report;
        this.validator.validate(report, binding);

        return result;
    }

    public Collection<Report> getReportsMadeByReviewer(int reviewerId){
        return this.reportRepository.getReportsMadeByReviewer(reviewerId);
    }

    public Collection<Report> getReportsOfSubmission(int submissionId) {
        return this.reportRepository.getReportsOfSubmission(submissionId);
    }
}
