package domain;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

    private String keyword;
    private Date startDate;
    private Date endDate;
    private Double maximumFee;
    private String categoryName;

    //Relationships
    private Actor actor;
    private Collection<Conference> conferences;

    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yy HH:mm")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yy HH:mm")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getMaximumFee() {
        return maximumFee;
    }

    public void setMaximumFee(Double maximumFee) {
        this.maximumFee = maximumFee;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //Relationships
    @OneToOne(optional = false)
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Valid
    @ManyToMany
    public Collection<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Collection<Conference> conferences) {
        this.conferences = conferences;
    }
}
