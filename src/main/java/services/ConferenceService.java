package services;

import domain.Actor;
import domain.Category;
import domain.Conference;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ConferenceRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ConferenceService {

    //Managed Repositories
    @Autowired
    private ConferenceRepository conferenceRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;

    @Autowired
    private CategoryService categoryService;

    public Conference create(){
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

        Conference res = new Conference();
        return res;
    }

    public Conference saveFinal(Conference conference){
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(conference);
        Conference res;

        if(conference.getCategory() == null){
            Category category = this.categoryService.getDefaultCategory();
            conference.setCategory(category);
        }

        conference.setIsFinal(true);
        conference.setMoment(new Date());
        Assert.isTrue(conference.getSubmissionDeadline().before(conference.getNotificationDeadline()));
        Assert.isTrue(conference.getNotificationDeadline().before(conference.getCameraReadyDeadline()));
        Assert.isTrue(conference.getCameraReadyDeadline().before(conference.getStartDate()));
        Assert.isTrue(conference.getStartDate().before(conference.getEndDate()));
        res = this.conferenceRepository.save(conference);
        return res;
    }


    public Conference saveDraft(Conference conference){
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(conference);
        Assert.isTrue(conference.getIsFinal() == false);
        Conference res;

        if(conference.getCategory() == null){
            Category category = this.categoryService.getDefaultCategory();
            conference.setCategory(category);
        }

        conference.setIsFinal(false);
        Assert.isTrue(conference.getSubmissionDeadline().before(conference.getNotificationDeadline()));
        Assert.isTrue(conference.getNotificationDeadline().before(conference.getCameraReadyDeadline()));
        Assert.isTrue(conference.getCameraReadyDeadline().before(conference.getStartDate()));
        Assert.isTrue(conference.getStartDate().before(conference.getEndDate()));
        res = this.conferenceRepository.save(conference);
        return res;
    }

    public void delete (Conference conference){
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

        Assert.notNull(conference);
        Assert.isTrue(conference.getId() != 0);
        Assert.isTrue(conference.getIsFinal() == false);

        this.conferenceRepository.delete(conference);
    }

    public Collection<Conference> findAll(){
        Collection<Conference> res;
        res = this.conferenceRepository.findAll();
        return res;
    }

    public Conference findOne(final int conferenceId) {
        Assert.isTrue(conferenceId != 0);
        final Conference res = this.conferenceRepository.findOne(conferenceId);
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getForthcomingConferencesFinal(){
        Collection<Conference> res;
        res = this.conferenceRepository.getForthcomingConferencesFinal();
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getPastConferencesFinal(){
        Collection<Conference> res;
        res = this.conferenceRepository.getPastConferencesFinal();
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getRunningConferencesFinal(){
        Collection<Conference> res;
        res = this.conferenceRepository.getRunningConferencesFinal();
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getConferencesByKeyword(String keyword){
        Collection<Conference> res;
        Assert.notNull(keyword);
        res = this.conferenceRepository.getConferencesByKeyword(keyword);
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getConferencesSubmission5Days(){
        Collection<Conference> res;
        Date date = new DateTime(new Date()).minusDays(5).toDate();

        res = this.conferenceRepository.getConferencesSubmission5Days(date);
        Assert.notNull(res);

        return res;
    }

    public Collection<Conference> getConferencesNotificationnNext4Days(){
        Collection<Conference> res;
        Date date = new DateTime(new Date()).plusDays(4).toDate();

        res = this.conferenceRepository.getConferencesNotificationnNext4Days(date);
        Assert.notNull(res);

        return res;
    }

    public Collection<Conference> getConferencesCamera4Days(){
        Collection<Conference> res;
        Date date = new DateTime(new Date()).plusDays(4).toDate();

        res = this.conferenceRepository.getConferencesCamera4Days(date);
        Assert.notNull(res);

        return res;
    }

    public Collection<Conference> getConferenceStartNext4Days(){
        Collection<Conference> res;
        Date date = new DateTime(new Date()).plusDays(4).toDate();

        res = this.conferenceRepository.getConferenceStartNext4Days(date);
        Assert.notNull(res);

        return res;
    }

    public Collection<Conference> getAllFutureConferences(){
        Collection<Conference> res;
        res = this.conferenceRepository.getAllFutureConferences();
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getConferencesByAuthor(int authorId){
        Collection<Conference> res;
        res = this.conferenceRepository.getConferencesByAuthor(authorId);
        Assert.notNull(res);
        return res;
    }

    public Collection<Conference> getConferencesSince12Months(){
        Date date = new DateTime(new Date()).minusMonths(12).toDate();
        return this.conferenceRepository.getConferencesSince12Months(date);
    }

    public Collection<Conference> getConferencesCameraReadyLaterNow(){
        Collection<Conference> res;
        res = this.conferenceRepository.getConferencesCameraReadyLaterNow();
        Assert.notNull(res);
        return res;
    }

    public Conference reconstruct(Conference conference, BindingResult binding){
        Conference result;
        if (conference.getId() == 0){
            result = this.create();
        } else {
            result = this.conferenceRepository.findOne(conference.getId());
        }

        if(conference.getCategory() == null){
            Category category = this.categoryService.getDefaultCategory();
           result.setCategory(category);
        } else {
            result.setCategory(conference.getCategory());
        }

        result.setTitle(conference.getTitle());
        result.setAcronym(conference.getAcronym());
        result.setVenue(conference.getVenue());
        result.setSubmissionDeadline(conference.getSubmissionDeadline());
        result.setNotificationDeadline(conference.getNotificationDeadline());
        result.setCameraReadyDeadline(conference.getCameraReadyDeadline());
        result.setStartDate(conference.getStartDate());
        result.setEndDate(conference.getEndDate());
        result.setSummary(conference.getSummary());
        result.setFee(conference.getFee());
        result.setIsFinal(conference.getIsFinal());
        result.setRegistrations(conference.getRegistrations());
        result.setComments(conference.getComments());

        validator.validate(result, binding);

        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }
}
