package services;

import domain.Actor;
import domain.Presentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.PresentationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class PresentationService {

    //Managed Repositories
    @Autowired
    private ActorService actorService;

    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private Validator validator;

    //Supporting services

    //CRUD Methods
    public Presentation create(){
        Presentation presentation = new Presentation();

        return presentation;
    }

    public Presentation findOne(int id){
        return this.presentationRepository.findOne(id);
    }

    public Collection<Presentation> findAll(){
        return this.presentationRepository.findAll();
    }

    public Presentation save(Presentation presentation){
        Presentation result;
        Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(presentation);

        result = this.presentationRepository.save(presentation);

        return result;
    }

    public void delete(Presentation presentation){
        Assert.notNull(presentation);
        Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        this.presentationRepository.delete(presentation);
    }

    public Collection<Presentation> getPresentationsByConference(int conferenceId){
        Collection<Presentation> res;
        res = this.presentationRepository.getPresentationsByConference(conferenceId);
        Assert.notNull(res);
        return res;
    }

    public Presentation reconstruct(final Presentation presentation, final BindingResult binding) {
        Presentation result;

        if(presentation.getId() == 0)
            result = this.create();
        else
            result = this.findOne(presentation.getId());

        result.setConference(presentation.getConference());
        result.setSubmission(presentation.getSubmission());
        result.setSpeakerName(presentation.getSpeakerName());
        result.setAttachments(presentation.getAttachments());
        result.setComments(presentation.getComments());
        result.setTitle(presentation.getTitle());
        result.setDuration(presentation.getDuration());
        result.setStartMoment(presentation.getStartMoment());
        result.setRoom(presentation.getRoom());
        result.setSummary(presentation.getSummary());

        this.validator.validate(presentation, binding);

        return result;
    }
}
