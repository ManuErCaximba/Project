package repositories;

import domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    @Query("select s from Submission s where s.author.id = ?1")
    Collection<Submission> getSubmissionsByAuthor(int authorId);

    @Query("select s from Submission s where (s.conference.id = ?1 and s.isAssigned = false)")
    Collection<Submission> getSubmissionsByConferenceNotAssigned(int conferenceId);

    @Query("select s from Submission s where (s.status = 'ACCEPTED' and s.isCameraReady = true and s.conference.id = ?1)")
    Collection<Submission> getSubmissionsAcceptedAndCameraReadyByConference(int conferenceId);

    @Query("select s from Submission s where (s.status = 'ACCEPTED' and s.isCameraReady = true and s.author.id = ?1)")
    Collection<Submission> getSubmissionsAcceptedAndCameraReadyByAuthor(int authorId);


}
