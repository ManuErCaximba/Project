package forms;

import domain.Reviewer;
import domain.Submission;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class AssignForm {

    private int submissionId;
    private Collection<Reviewer> reviewers;

    public AssignForm(int submissionId, Collection<Reviewer> reviewers){
        this.submissionId = submissionId;
        this.reviewers = reviewers;
    }

    public AssignForm(){

    }

    @NotNull
    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    @ElementCollection
    @NotEmpty
    public Collection<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Collection<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }
}
