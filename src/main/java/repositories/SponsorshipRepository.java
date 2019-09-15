package repositories;

import domain.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

    @Query("select s from Sponsorship s where s.sponsor.id = ?1")
    Collection<Sponsorship> findAllBySponsor(int sponsorId);

}
