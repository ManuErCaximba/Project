package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

    private int originalityScore;
    private int qualityScore;
    private int readabilityScore;
    private String decision;
    private String comment;

    //Relationships
    private Reviewer reviewer;
    private Submission submission;

    @NotNull
    @Range(min = 0, max = 10)
    public int getOriginalityScore() {
        return originalityScore;
    }

    public void setOriginalityScore(int originalityScore) {
        this.originalityScore = originalityScore;
    }

    @NotNull
    @Range(min = 0, max = 10)
    public int getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(int qualityScore) {
        this.qualityScore = qualityScore;
    }

    @NotNull
    @Range(min = 0, max = 10)
    public int getReadabilityScore() {
        return readabilityScore;
    }

    public void setReadabilityScore(int readabilityScore) {
        this.readabilityScore = readabilityScore;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    @Pattern(regexp = "^REJECT|BORDER-LINE|ACCEPT$")
    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //Relationships

    @ManyToOne(optional = false)
    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    @ManyToOne(optional = false)
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
