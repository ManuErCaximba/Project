package domain;

import datatype.Url;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity{
    private String title;
    private String summary;
    private Collection<Url> pictures;

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
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Valid
    @ElementCollection(fetch = FetchType.EAGER)
    public Collection<Url> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Url> pictures) {
        this.pictures = pictures;
    }

    //Relationships
    private Tutorial tutorial;

    @ManyToOne(optional = false)
    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }
}
