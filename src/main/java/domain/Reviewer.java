package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

    private String email;
    private Collection<String> keywords;

    @Pattern(regexp = "^[a-zA-Z0-9 ]*[<]?\\w+[@][a-zA-Z0-9.]+[>]?$")
    @NotBlank
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
