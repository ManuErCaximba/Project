package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageService {

    //Managed Repositories
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private Validator validator;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private SubmissionService submissionService;

    public Message create() {

        final Message result = new Message();
        UserAccount userAccount = this.actorService.getActorLogged().getUserAccount();
        Actor sender = this.actorService.findByUserAccount(userAccount);
        result.setSender(sender);
        result.setDeletedByRecipient(false);
        result.setDeletedBySender(false);

        return result;
    }

    public Collection<Message> findAll(){
        Collection<Message> res;
        res = this.messageRepository.findAll();
        return res;
    }

    public Message findOne(final int messageId) {
        Assert.isTrue(messageId != 0);
        final Message res = this.messageRepository.findOne(messageId);
        Assert.notNull(res);
        return res;
    }

   public Message save(Message message){
        Message result;
        Assert.notNull(message);
        Date now = new Date();

        message.setMoment(now);
        result = this.messageRepository.save(message);

        return result;
    }

    public void delete(Message message){
        Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(message.getSender().getId() == actor.getId() || message.getRecipient().getId() == actor.getId());

        if(message.getSender().getId() == actor.getId()){
            if (message.getDeletedByRecipient() == true){
                this.messageRepository.delete(message);
            } else{
                message.setDeletedBySender(true);
                this.messageRepository.save(message);
            }
        } else if(message.getRecipient().getId() == actor.getId()){
            if(message.getDeletedBySender() == true){
                this.messageRepository.delete(message);
            } else {
                message.setDeletedByRecipient(true);
                this.messageRepository.save(message);
            }
        }
    }

    public Collection<Message> findAllByActor(int actorId){
        Collection<Message> res;
        res = this.messageRepository.findAllSendByActor(actorId);
        res.addAll(this.messageRepository.findAllReceiveByActor(actorId));
        Assert.notNull(res);
        return res;
    }

    public void broadcast(Message message) {
        final Actor principal = this.actorService.getActorLogged();
        Assert.isTrue(principal instanceof Administrator);

        final Collection<Actor> actors = this.actorService.findAll();
        actors.remove(principal);

        for (Actor a : actors) {
            Message msg = this.create();

            msg = message;

            msg.setSender(principal);
            msg.setRecipient(a);

            msg = this.messageRepository.save(msg);

        }

    }

    public void broadcastAuthors(Message message) {
        final Actor principal = this.actorService.getActorLogged();
        Assert.isTrue(principal instanceof Administrator);

        final Collection<Author> authors = this.authorService.findAll();

        for (Author a : authors) {
            Message msg = this.create();

            msg = message;

            msg.setSender(principal);
            msg.setRecipient(a);

            msg = this.messageRepository.save(msg);

        }

    }

    public void notificationRegisterConference(Author author, int conferenceId){
        Message message = this.create();

        List<Administrator> admins = new ArrayList<>(this.administratorService.findAll());
        int random = (int) (Math.random()*admins.size());

        Conference conference = this.conferenceService.findOne(conferenceId);
        Assert.notNull(conference);

        message.setSender(admins.get(random));
        message.setRecipient(author);
        message.setSubject("Conference registration \\n Registro en conferencia");
        message.setBody("You have successfully registered in the "+conference.getTitle()+" conference \\n Se ha registrado correctamente en la conferencia "+conference.getTitle()+".");

        Topic registrationTopic = this.topicService.getRegistrationtTopic();
        Assert.notNull(registrationTopic);
        message.setTopic(registrationTopic);

        message.setMoment(new Date());
        message = this.messageRepository.save(message);
    }

    public void notificationSubmissionConference(Author author, Submission submission2){
        Message message = this.create();

        List<Administrator> admins = new ArrayList<>(this.administratorService.findAll());
        int random = (int) (Math.random()*admins.size());

        Submission submission = this.submissionService.findOne(submission2.getId());
        message.setSender(admins.get(random));
        message.setRecipient(author);
        message.setSubject("Conference submission \n Presentación conferencia");
        message.setBody("Your submission "+submission.getTicker()+ " was registered correctly.\n Su solicitud "+submission.getTicker()+ " se ha registrado correctamente");

        Topic registrationTopic = this.topicService.getRegistrationtTopic();
        Assert.notNull(registrationTopic);
        message.setTopic(registrationTopic);

        message.setMoment(new Date());
        message = this.messageRepository.save(message);
    }

    public void notificationStatusSubmmission(Submission submission2){
        Message message = this.create();

        List<Administrator> admins = new ArrayList<>(this.administratorService.findAll());
        int random = (int) (Math.random()*admins.size());

        Submission submission = this.submissionService.findOne(submission2.getId());
        if(submission.getStatus().equals("ACCEPTED")){
            message.setSender(admins.get(random));
            message.setRecipient(submission.getAuthor());
            message.setSubject("Submission decision\n Resultado de solicitud");
            message.setBody("Your submission "+submission.getTicker()+ " was accepted.\n Su solicitud "+submission.getTicker()+ " fue aceptada.");

            Topic registrationTopic = this.topicService.getRegistrationtTopic();
            Assert.notNull(registrationTopic);
            message.setTopic(registrationTopic);

            message.setMoment(new Date());
            message = this.messageRepository.save(message);
        }
        if(submission.getStatus().equals("REJECTED")){
            message.setSender(admins.get(random));
            message.setRecipient(submission.getAuthor());
            message.setSubject("Submission decision\n Resultado de solicitud");
            message.setBody("Your submission "+submission.getTicker()+ " was rejected.\n Su solicitud "+submission.getTicker()+ " fue rechazada.");

            Topic registrationTopic = this.topicService.getRegistrationtTopic();
            Assert.notNull(registrationTopic);
            message.setTopic(registrationTopic);

            message.setMoment(new Date());
            message = this.messageRepository.save(message);
        }

    }

    public Collection<Message> findAllMessagesByTopic(int topicId){
        Collection<Message> res;
        res = this.messageRepository.findAllMessagesByTopic(topicId);
        Assert.notNull(res);
        return res;
    }

    public Message reconstruct(Message message, BindingResult binding){
        Message result;
        if (message.getId() == 0){
            result = this.create();
            Date now = new Date();
            result.setMoment(now);
        } else {
            result = this.messageRepository.findOne(message.getId());
        }

        result.setSender(message.getSender());
        result.setRecipient(message.getRecipient());
        result.setSubject(message.getSubject());
        result.setTopic(message.getTopic());
        result.setBody(message.getBody());
        result.setDeletedBySender(message.getDeletedBySender());
        result.setDeletedByRecipient(message.getDeletedByRecipient());
        validator.validate(result, binding);

        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }
}
