package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity{
    private Date moment;
    private String subject;
    private String body;
    private boolean deletedBySender;
    private boolean deletedByRecipient;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean getDeletedBySender() {
        return deletedBySender;
    }

    public void setDeletedBySender(boolean deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    public boolean getDeletedByRecipient() {
        return deletedByRecipient;
    }

    public void setDeletedByRecipient(boolean deletedByRecipient) {
        this.deletedByRecipient = deletedByRecipient;
    }

    // Relationships ----------------------------------------------------------
    private Actor				sender;
    private Actor recipient;
    private Topic topic;


    @Valid
    @ManyToOne(optional = false)
    public Actor getSender() {
        return this.sender;
    }

    public void setSender(final Actor sender) {
        this.sender = sender;
    }


    @ManyToOne(optional = false)
    public Actor getRecipient() {
        return recipient;
    }

    public void setRecipient(Actor recipient) {
        this.recipient = recipient;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
