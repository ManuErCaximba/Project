package repositories;

import domain.Category;
import domain.Conference;
import domain.Finder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

    @Query("select f from Finder f where f.actor.id = ?1")
    Finder searchByActor(int actorId);

    @Query("select c from Conference c where c.title like %?1% and c.isFinal = TRUE")
    Collection<Conference> getConferenceByTitle(String keyword);

    @Query("select c from Conference c where c.acronym like %?1% and c.isFinal = TRUE")
    Collection<Conference> getConferenceByAcronym(String keyword);

    @Query("select c from Conference c where c.summary like %?1% and c.isFinal = TRUE")
    Collection<Conference> getConferenceBySummary(String keyword);

    @Query("select c from Conference c where c.venue like %?1% and c.isFinal = TRUE")
    Collection<Conference> getConferenceByVenue(String keyword);

    @Query("select c from Conference c where (c.category.nameEs like ?1 or c.category.nameEn like ?1)" +
            " and c.isFinal = TRUE")
    Collection<Conference> getConferenceByCategory(String categoryName);

    @Query("select c from Conference c where (c.startDate between ?1 and ?2) and c.isFinal = TRUE")
    Collection<Conference> getConferenceByDate(Date startDate, Date endDate);

    @Query("select c from Conference c where c.fee <= ?1 and c.isFinal = TRUE")
    Collection<Conference> getConferenceByFee(Double maximumFee);

    @Query("select c from Conference c where c.isFinal = TRUE")
    Collection<Conference> findAllNotFinal();
}
