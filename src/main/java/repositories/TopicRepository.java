package repositories;

import domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query("select c from Topic  c where c.nameEn = 'REGISTRATION' and c.nameEs = 'REGISTRO'")
    Topic getRegistrationtTopic();

    @Query("select c from Topic c where c.nameEn = 'OTHER' and c.nameEs = 'OTRO'")
    Topic getOtherTopic();
}
