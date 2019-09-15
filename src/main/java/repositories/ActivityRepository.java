package repositories;

import domain.Activity;
import domain.Presentation;
import domain.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a where a.conference.id = ?1")
    Collection<Activity> getActivitiesByConference(int conferenceId);
}
