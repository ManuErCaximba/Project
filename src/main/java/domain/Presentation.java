package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {

    //Relationships
    private Submission submission;

    //Relationships
    @OneToOne(optional = false)
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
