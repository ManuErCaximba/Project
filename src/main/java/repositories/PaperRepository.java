package repositories;

import domain.Author;
import domain.Paper;
import domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

    @Query("select s from Paper s where ?1 member of s.authors")
    Collection<Paper> findAllByAuthor(Author author);

    @Query("select s from Submission s where s.paper.id = ?1 or s.cameraReadyPaper.id = ?1")
    Collection<Submission> getSubmissionPerPaper(int paperId);

}
