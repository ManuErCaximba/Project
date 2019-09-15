package domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

    private Collection<String> keywords;

    //Relationship
    private Collection<Submission> submissions;

    @NotEmpty
    @ElementCollection
    public Collection<String> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    //Relationship

    @ManyToMany
    public Collection<Submission> getSubmissions() {
        return this.submissions;
    }

    public void setSubmissions(Collection<Submission> submissions) {
        this.submissions = submissions;
    }
}
