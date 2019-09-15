package repositories;

import domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    @Query("select r from Registration r where r.author.id = ?1")
    Collection<Registration> getRegistrationsPerAuthor(int authorId);
}
