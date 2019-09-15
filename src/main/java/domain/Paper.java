package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Paper extends DomainEntity {

    private String title;
    private String summary;
    private String documentURL;
    private Boolean isInSubmission;

    //Relationships
    private Collection<Author> authors;

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    @URL
    public String getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public Boolean getIsInSubmission() {
        return isInSubmission;
    }

    public void setIsInSubmission(Boolean isInSubmission) {
        this.isInSubmission = isInSubmission;
    }

    //Relationships

    @ManyToMany()
    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }
}
