package repositories;


import domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

}
