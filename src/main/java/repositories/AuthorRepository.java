package repositories;

import domain.Author;
import domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
