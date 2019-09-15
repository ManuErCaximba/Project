package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

    private String ticker;
    private Date moment;
    private String status;
    private boolean isCameraReady;
    private boolean isAssigned;

    //Relationships
    private Author author;
    private Paper paper;
    private Paper cameraReadyPaper;
    private Conference conference;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @Pattern(regexp = "^ACCEPTED|REJECTED|UNDER-REVIEW$")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsCameraReady() {
        return isCameraReady;
    }

    public void setIsCameraReady(boolean cameraReady) {
        isCameraReady = cameraReady;
    }

    public boolean getIsAssigned() {
        return this.isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    //Relationships

    @ManyToOne(optional = false)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToOne(optional = false)
    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @ManyToOne
    public Paper getCameraReadyPaper() {
        return cameraReadyPaper;
    }

    public void setCameraReadyPaper(Paper cameraReadyPaper) {
        this.cameraReadyPaper = cameraReadyPaper;
    }

    @ManyToOne(optional = false)
    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
