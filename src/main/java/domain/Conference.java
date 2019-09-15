package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends DomainEntity {
    private String title;
    private String acronym;
    private String venue;
    private Date moment;
    private Date submissionDeadline;
    private Date notificationDeadline;
    private Date cameraReadyDeadline;
    private Date startDate;
    private Date endDate;
    private String summary;
    private double fee;
    private boolean isFinal;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(Date submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getNotificationDeadline() {
        return notificationDeadline;
    }

    public void setNotificationDeadline(Date notificationDeadline) {
        this.notificationDeadline = notificationDeadline;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getCameraReadyDeadline() {
        return cameraReadyDeadline;
    }

    public void setCameraReadyDeadline(Date cameraReadyDeadline) {
        this.cameraReadyDeadline = cameraReadyDeadline;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    //Relationships
    private Category category;
    private Collection<Registration> registrations;
    private Collection<Comment> comments;

    @ManyToOne(optional = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany
    public Collection<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Collection<Registration> registrations) {
        this.registrations = registrations;
    }

    @OneToMany
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
