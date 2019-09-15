package repositories;

import domain.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

    @Query("select t from Tutorial t where t.conference.id = ?1")
    Collection<Tutorial> getTutorialsByConference(int conferenceId);

    @Query("select t from Tutorial t where CURRENT_DATE < t.startMoment")
    Collection<Tutorial> getFutureTutorials();
}
