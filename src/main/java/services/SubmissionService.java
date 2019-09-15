package services;

import domain.*;
import forms.AssignForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SubmissionRepository;

import javax.transaction.Transactional;
import java.text.CollationElementIterator;
import java.util.*;

@Service
@Transactional
public class SubmissionService {

    //Managed Repositories
    @Autowired
    private SubmissionRepository submissionRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReviewerService reviewerService;
    @Autowired
    private Validator validator;

    @Autowired
    private MessageService messageService;

    //CRUD Methods
    public Submission create(){
        Submission submission = new Submission();

        return submission;
    }

    public Submission findOne(int id){
        return this.submissionRepository.findOne(id);
    }

    public Collection<Submission> findAll(){
        return this.submissionRepository.findAll();
    }

    public Submission save(Submission submission){
        Submission result;
        final Actor actor = this.actorService.getActorLogged();

        Assert.notNull(submission);

        result = this.submissionRepository.save(submission);

        return result;
    }

    public void delete(Submission submission){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
        Assert.notNull(submission);

        this.submissionRepository.delete(submission);
    }

    //Other Methods
    public Submission reconstruct(final Submission submission, final BindingResult binding) {
        Submission result;
        final Author author = (Author) this.actorService.getActorLogged();

        if(submission.getId() == 0){
            result = this.create();

            result.setTicker(this.tickerGenerator(author));
            result.setMoment(new Date());
            result.setStatus("UNDER-REVIEW");
            result.setIsCameraReady(false);
            result.setAuthor(author);

            result.setConference(submission.getConference());
            result.setPaper(submission.getPaper());
        } else {
            result = this.submissionRepository.findOne(submission.getId());

            result.setPaper(submission.getPaper());
        }

        this.validator.validate(submission, binding);

        return result;
    }

    public Submission reconstructCR(final Submission submission, final BindingResult binding) {
        Submission result;

        result = this.submissionRepository.findOne(submission.getId());
        result.setCameraReadyPaper(submission.getCameraReadyPaper());
        result.setIsCameraReady(true);

        this.validator.validate(submission, binding);

        return result;
    }

    public void update(){
        Collection<Submission> allSubmissions = this.findAll();
        Date now = new Date();
        for(Submission s: allSubmissions){
            Conference conference = s.getConference();
            if(now.after(conference.getSubmissionDeadline()) && s.getStatus().equals("UNDER-REVIEW")
                && s.getIsAssigned() == true && s.getIsCameraReady() == false) {

                this.finalReview(s);
            }
            if(now.after(conference.getSubmissionDeadline()) && now.before(conference.getCameraReadyDeadline())
                    && s.getIsAssigned() == false){
                s.setStatus("REJECTED");
                s.setIsAssigned(true);
                this.save(s);
            }
            if(s.getIsCameraReady() == false && now.after(conference.getCameraReadyDeadline())
                && now.before(conference.getStartDate()) && s.getStatus().equals("ACCEPTED")){
                s.setIsCameraReady(true);
                this.save(s);
            }
        }
    }

    public void reconstructAssign(AssignForm assignForm, BindingResult bindingResult) {
        Submission submission = this.submissionRepository.findOne(assignForm.getSubmissionId());
        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.addAll(assignForm.getReviewers());

        this.validator.validate(assignForm, bindingResult);

        this.assignToReviewers(reviewers, submission);
        submission.setIsAssigned(true);
    }

    public void assign(Submission submission){
        final Actor actor = this.actorService.getActorLogged();
        Date now = new Date();

        List<Reviewer> reviewers = this.selectReviewersWhoMatchKeywords(submission);

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.isTrue(now.before(submission.getConference().getSubmissionDeadline()));

        if(reviewers.size() < 3){
            this.assignToReviewers(reviewers, submission);
        } else {
            List<Reviewer> threeReviewers = new ArrayList<>();
            for(int i=0; i<3; i++){
                Random random = new Random();
                int n = random.nextInt(reviewers.size() - 1);
                threeReviewers.add(reviewers.get(n));
            }
            this.assignToReviewers(threeReviewers, submission);
        }

        submission.setIsAssigned(true);
        this.submissionRepository.save(submission);
    }
    private List<Reviewer> selectReviewersWhoMatchKeywords(Submission submission){
        String summary = submission.getConference().getSummary();
        summary += " " + submission.getConference().getTitle();
        Collection<Reviewer> allReviewers = this.reviewerService.findAll();
        List<Reviewer> res = new ArrayList<>();

        Collection<String> words = this.administratorService.splitThisText(summary);

        for(Reviewer r : allReviewers) {
            for(String keyword : r.getKeywords()) {
                if(words.contains(keyword)) {
                    res.add(r);
                    break;
                }
            }
        }

        return res;
    }

    private void assignToReviewers(Collection<Reviewer> reviewers, Submission submission){
        for(Reviewer r: reviewers) {
            List<Submission> submissions = (List<Submission>) r.getSubmissions();
            submissions.add(submission);
            r.setSubmissions(submissions);
        }
    }

    public String tickerGenerator(Actor actor){
        String result;
        String nPart;
        String lPart;

        lPart = Character.toString(actor.getName().charAt(0)).toUpperCase();

        if (actor.getMiddleName() != null && actor.getMiddleName().equals(""))
            lPart += Character.toString(actor.getMiddleName().charAt(0)).toUpperCase();
        else
            lPart += "X";

        lPart += Character.toString(actor.getSurname().charAt(0)).toUpperCase();

        Random random = new Random();
        int n = random.nextInt(10);
        nPart = String.valueOf(n);

        for(int i=0; i<3; i++){
            random = new Random();
            n = random.nextInt(10);
            nPart += String.valueOf(n);
        }

        result = lPart + "-" + nPart;

        return result;
    }

    public Collection<Submission> getSubmissionsByAuthor(int authorId){
        return this.submissionRepository.getSubmissionsByAuthor(authorId);
    }

    public Collection<Submission> getSubmissionsByConferenceNotAssigned(int conferenceId){
        return this.submissionRepository.getSubmissionsByConferenceNotAssigned(conferenceId);
    }

    public Collection<Submission> getSubmissionsAcceptedAndCameraReadyByConference(int conferenceId){
        return this.submissionRepository.getSubmissionsAcceptedAndCameraReadyByConference(conferenceId);
    }

    public Collection<Submission> getSubmissionsAcceptedAndCameraReadyByAuthor(int authorId){
        return this.submissionRepository.getSubmissionsAcceptedAndCameraReadyByAuthor(authorId);
    }

    public void finalReview(Submission submission){
        Collection<Report> reports = this.reportService.getReportsOfSubmission(submission.getId());
        int accepted = 0;
        int refused = 0;
        int borderline = 0;
        for (Report r : reports){
            if (r.getDecision().equals("ACCEPT"))
                accepted++;
            else if (r.getDecision().equals("REJECT"))
                refused++;
            else
                borderline++;
        }

        if(accepted > refused)
            submission.setStatus("ACCEPTED");
        else {
            if (accepted == refused) {
                if(accepted + borderline >= refused)
                    submission.setStatus("ACCEPTED");
            } else {
                submission.setStatus("REJECTED");
            }
        }

        submission = this.save(submission);
        this.messageService.notificationStatusSubmmission(submission);

        Collection<Reviewer> reviewers = this.reviewerService.findAll();
        for(Reviewer r: reviewers){
            if(r.getSubmissions().contains(submission)){
                Collection<Submission> submissions = r.getSubmissions();
                submissions.remove(submission);
                r.setSubmissions(submissions);
            }
        }
    }

}
