package repositories;

import domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    @Query("select c from Section c where c.tutorial.id = ?1")
    Collection<Section> getSectionsByTutorial(int tutorialId);
}
