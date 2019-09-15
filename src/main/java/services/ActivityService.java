package services;

import domain.Activity;
import domain.Actor;
import domain.Presentation;
import domain.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ActivityRepository;
import repositories.PresentationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ActivityService {

    //Managed Repositories
    @Autowired
    private ActorService actorService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private Validator validator;

    //Supporting services

    //CRUD Methods
    public Activity create(){
        Activity activity = new Activity();

        return activity;
    }

    public Activity findOne(int id){
        return this.activityRepository.findOne(id);
    }

    public Collection<Activity> findAll(){
        return this.activityRepository.findAll();
    }

    public Activity save(Activity activity){
        Activity result;
        Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(activity);

        result = this.activityRepository.save(activity);

        return result;
    }

    public void delete(Activity activity){
        Assert.notNull(activity);
        Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        this.activityRepository.delete(activity);
    }

    public Collection<Activity> getPanelsByConference(int conferenceId){
        Collection<Activity> res;
        res = this.activityRepository.getActivitiesByConference(conferenceId);
        Collection<Presentation> presentations = this.presentationService.getPresentationsByConference(conferenceId);
        Collection<Tutorial> tutorials = this.tutorialService.getTutorialsByConference(conferenceId);
        res.removeAll(presentations);
        res.removeAll(tutorials);
        Assert.notNull(res);
        return res;
    }

    public Activity reconstruct(final Activity activity, final BindingResult binding) {
        Activity result;

        if(activity.getId() == 0)
            result = this.create();
        else
            result = this.findOne(activity.getId());

        result.setConference(activity.getConference());
        result.setSpeakerName(activity.getSpeakerName());
        result.setAttachments(activity.getAttachments());
        result.setComments(activity.getComments());
        result.setTitle(activity.getTitle());
        result.setDuration(activity.getDuration());
        result.setStartMoment(activity.getStartMoment());
        result.setRoom(activity.getRoom());
        result.setSummary(activity.getSummary());

        this.validator.validate(activity, binding);

        return result;
    }
}
