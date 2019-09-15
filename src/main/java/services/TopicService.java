package services;

import domain.Actor;
import domain.Configuration;
import domain.Message;
import domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.TopicRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private MessageService messageService;

    public Topic create(){
        UserAccount userAccount;
        userAccount = LoginService.getPrincipal();

        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

        Topic result;
        result = new Topic();

        return result;
    }

    public Collection<Topic> findAll(){
        Collection<Topic> res;
        res = this.topicRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public Topic findOne(int topicId){
        Topic res;
        res = this.topicRepository.findOne(topicId);
        Assert.notNull(res);
        return res;
    }

    public Topic save(Topic topic){
        Actor a;
        a = this.actorService.getActorLogged();

        Assert.isTrue(a.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(topic);

        Topic result;
        result = this.topicRepository.save(topic);

        return result;
    }

    public void delete(Topic topic){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(topic);

        Collection<Configuration> configurations = this.configurationService.findAll();
        for(Configuration c : configurations){
            if(c.getTopics().contains(topic))
                c.getTopics().remove(topic);
        }

        Collection<Message> messages = this.messageService.findAllMessagesByTopic(topic.getId());
        for(Message m: messages){
            if(m.getTopic().equals(topic))
                m.setTopic(this.topicRepository.getOtherTopic());
        }
        this.topicRepository.delete(topic);
    }

    public Topic reconstruct(Topic topic, BindingResult binding){
        Topic result;

        Boolean existsES = false;
        Boolean existsEN = false;

        if (topic.getId() == 0){
            result = this.create();
        } else {
            result = this.topicRepository.findOne(topic.getId());
        }

        if (result.getId() == 0) {
            existsES = existsES(topic);
            existsEN = existsEN(topic);
        } else {
            if (!result.equalsES(topic))
                existsES = existsES(topic);
            if (!result.equalsEN(topic))
                existsEN = existsEN(topic);
        }

        result.setNameEn(topic.getNameEn());
        result.setNameEs(topic.getNameEs());

        validator.validate(result, binding);

        if (existsES)
            binding.rejectValue("nameEs", "topic.es.duplicated");

        if (existsEN)
            binding.rejectValue("nameEn", "topic.en.duplicated");

        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }

    public Boolean existsES(final Topic a) {
        Boolean exist = false;

        final Collection<Topic> topics = this.findAll();
        for (final Topic b : topics)
            if (a.equalsES(b)) {
                exist = true;
                break;
            }
        return exist;
    }

    public Boolean existsEN(final Topic a) {
        Boolean exist = false;

        final Collection<Topic> topics = this.findAll();
        for (final Topic b : topics)
            if (a.equalsEN(b)) {
                exist = true;
                break;
            }
        return exist;
    }

    public Topic getRegistrationtTopic(){
        Topic res;
        res = this.topicRepository.getRegistrationtTopic();
        Assert.notNull(res);
        return res;
    }

    public Topic getOtherTopic(){
        Topic res;
        res = this.topicRepository.getOtherTopic();
        Assert.notNull(res);
        return res;
    }

}
