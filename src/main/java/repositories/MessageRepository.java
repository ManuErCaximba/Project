package repositories;

import domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("select m from Message m where m.sender.id = ?1 and m.deletedBySender = false")
    Collection<Message> findAllSendByActor(int actorId);

    @Query("select m from Message m where m.recipient.id = ?1 and m.deletedByRecipient = false ")
    Collection<Message> findAllReceiveByActor(int actorId);

    @Query("select m from Message m where m.topic.id = ?1")
    Collection<Message> findAllMessagesByTopic(int topicId);
}
