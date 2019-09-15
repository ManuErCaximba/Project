package repositories;

import domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    @Query("select p from Presentation p where p.conference.id = ?1")
    Collection<Presentation> getPresentationsByConference(int conferenceId);
}
