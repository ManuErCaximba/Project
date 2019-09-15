package controllers.conference;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import security.UserAccount;
import services.*;

import javax.validation.ValidationException;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("conference")
public class ConferenceController extends AbstractController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SponsorshipService sponsorshipService;

    //List forthcoming conferences

    @RequestMapping(value = "/listForthcoming", method = RequestMethod.GET)
    public ModelAndView listForthcoming(){
        ModelAndView result;
        try {
            Collection<Conference> conferences;
            conferences = this.conferenceService.getForthcomingConferencesFinal();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/listForthcoming");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/listForthcoming.do");
            result.addObject("now", new Date());
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    //List past conferences

    @RequestMapping(value = "/listPast", method = RequestMethod.GET)
    public ModelAndView listPast(){
        ModelAndView result;

        try {
            Collection<Conference> conferences;
            conferences = this.conferenceService.getPastConferencesFinal();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/listPast");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/listPast.do");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }

    //List running conferences

    @RequestMapping(value = "/listRunning", method = RequestMethod.GET)
    public ModelAndView listRunning(){
        ModelAndView result;
        try {
            Collection<Conference> conferences;
            conferences = this.conferenceService.getRunningConferencesFinal();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/listRunning");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/listRunning.do");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }

    //List conferences whose submis-sion deadline elapsed in the last five days

    @RequestMapping(value = "/administrator/listDeadline5Days", method = RequestMethod.GET)
    public ModelAndView listDeadline5Days(){
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            Collection<Conference> conferences;
            conferences = this.conferenceService.getConferencesSubmission5Days();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/administrator/listDeadline5Days");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/administrator/listDeadline5Days.do");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }

    //List conferences whose notification deadline elapses in less than five days

    @RequestMapping(value = "/administrator/listNotification4Days", method = RequestMethod.GET)
    public ModelAndView listNotification4Days(){
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            Collection<Conference> conferences;
            conferences = this.conferenceService.getConferencesNotificationnNext4Days();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/administrator/listNotification4Days");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/administrator/listNotification4Days.do");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }

    //List conferences whose camera-ready deadline elapses in less than five days

    @RequestMapping(value = "/administrator/listCamera4Days", method = RequestMethod.GET)
    public ModelAndView listCamera4Days(){
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            Collection<Conference> conferences;
            conferences = this.conferenceService.getConferencesCamera4Days();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/administrator/listCamera4Days");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/administrator/listCamera4Days.do");
        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }

    //List conferences that are going to be organised in less than five days

    @RequestMapping(value = "/administrator/listStartNext4Days", method = RequestMethod.GET)
    public ModelAndView listStartNext4Days(){
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            Collection<Conference> conferences;
            conferences = this.conferenceService.getConferenceStartNext4Days();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/administrator/listStartNext4Days");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/administrator/listStartNext4Days.do");
        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;

    }


    //List all conferences

    @RequestMapping(value = "/administrator/listAll", method = RequestMethod.GET)
    public ModelAndView listAll(){
        ModelAndView result;
        Collection<Conference> conferences;
        conferences = this.conferenceService.findAll();
        final String language = LocaleContextHolder.getLocale().getLanguage();

        result = new ModelAndView("conference/administrator/listAll");
        result.addObject("conferences", conferences);
        result.addObject("lang", language);
        result.addObject("requestURI", "conference/administrator/listAll.do");

        return result;

    }

    //Menu Admin of conferences list by criteria...

    @RequestMapping(value = "/administrator/listConferenceAdminMenu", method = RequestMethod.GET)
    public ModelAndView listConferenceAdminMenu(){
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result = new ModelAndView("conference/administrator/listConferenceAdminMenu");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    //Show not logged users

    @RequestMapping(value = "/showNotLogged", method = RequestMethod.GET)
    public ModelAndView showNotLogged(@RequestParam int conferenceId){
        ModelAndView result;
        final Conference conference;
        final String language = LocaleContextHolder.getLocale().getLanguage();
        Collection<Tutorial> tutorials;
        Collection<Presentation> presentations;
        Collection<Activity> panels;
        List<Sponsorship> sponsorships = new ArrayList<>(this.sponsorshipService.findAll());
        int random = (int) (Math.random()*sponsorships.size());
        try {
            Assert.notNull(sponsorships);
            Sponsorship sponsorship = sponsorships.get(random);
            conference = this.conferenceService.findOne(conferenceId);
            tutorials = this.tutorialService.getTutorialsByConference(conferenceId);
            presentations = this.presentationService.getPresentationsByConference(conferenceId);
            panels = this.activityService.getPanelsByConference(conferenceId);
            Assert.isTrue(conference.getIsFinal());
            Assert.notNull(conference);
            Assert.notNull(tutorials);
            Assert.notNull(presentations);
            result = new ModelAndView("conference/showNotLogged");
            result.addObject("conference", conference);
            result.addObject("lang", language);
            result.addObject("tutorials", tutorials);
            result.addObject("presentations", presentations);
            result.addObject("panels", panels);
            result.addObject("now", new Date());
            result.addObject("sponsorship", sponsorship);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    //Show logged users

    @RequestMapping(value = "/administrator/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int conferenceId){
        ModelAndView result;
        final Conference conference;
        final String language = LocaleContextHolder.getLocale().getLanguage();
        Collection<Tutorial> tutorials;
        Collection<Presentation> presentations;
        Collection<Activity> panels;
        List<Sponsorship> sponsorships = new ArrayList<>(this.sponsorshipService.findAll());
        int random = (int) (Math.random()*sponsorships.size());
        try {
            Assert.notNull(sponsorships);
            Sponsorship sponsorship = sponsorships.get(random);
            Assert.notNull(sponsorship);
            conference = this.conferenceService.findOne(conferenceId);
            tutorials = this.tutorialService.getTutorialsByConference(conferenceId);
            presentations = this.presentationService.getPresentationsByConference(conferenceId);
            panels = this.activityService.getPanelsByConference(conferenceId);
            Actor actor = this.actorService.getActorLogged();
            Assert.isTrue(actor instanceof Administrator);
            Assert.isTrue(conference.getIsFinal());
            Assert.notNull(conference);
            Assert.notNull(tutorials);
            Assert.notNull(presentations);
            result = new ModelAndView("conference/administrator/show");
            result.addObject("conference", conference);
            result.addObject("lang", language);
            result.addObject("tutorials", tutorials);
            result.addObject("presentations", presentations);
            result.addObject("panels", panels);
            result.addObject("now", new Date());
            result.addObject("sponsorship", sponsorship);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    //Create

    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        try {
            Conference conference = this.conferenceService.create();
            final String language = LocaleContextHolder.getLocale().getLanguage();
            Collection<Category> categories = this.categoryService.findAll();
            result = new ModelAndView("conference/administrator/create");
            result.addObject("conference", conference);
            result.addObject("categories", categories);
            result.addObject("lang", language);
        } catch (Throwable oops) {
            final String language = LocaleContextHolder.getLocale().getLanguage();
            Collection<Category> categories = this.categoryService.findAll();
            result = new ModelAndView("redirect:/");
            result.addObject("categories", categories);
            result.addObject("lang", language);
        }
        return result;
    }

    //Edit

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int conferenceId) {
        ModelAndView result;
        final String language = LocaleContextHolder.getLocale().getLanguage();
        Collection<Category> categories = this.categoryService.findAll();
        try {
            Conference conference;
            conference = this.conferenceService.findOne(conferenceId);
            Assert.notNull(conference);
            Assert.isTrue(conference.getIsFinal() == false);
            Actor user = this.actorService.getActorLogged();
            Assert.isTrue(user instanceof Administrator);
            Assert.notNull(categories);
            result = new ModelAndView("conference/administrator/edit");
            result.addObject("conference", conference);
            result.addObject("categories", categories);
            result.addObject("lang", language);
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
            result.addObject("categories", categories);
            result.addObject("lang", language);
        }
        return result;
    }

    //Save as draft

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "saveDraft")
    public ModelAndView saveDraft(Conference conference, BindingResult binding) {
        ModelAndView result;
        final String language = LocaleContextHolder.getLocale().getLanguage();
        Collection<Category> categories = this.categoryService.findAll();
        try {
            Assert.notNull(conference);
            Assert.notNull(categories);
            conference = this.conferenceService.reconstruct(conference, binding);
            conference = this.conferenceService.saveDraft(conference);
            result = new ModelAndView("redirect:listAll.do");
        } catch (ValidationException e) {
            result = this.createEditModelAndView(conference, null);
            result.addObject("categories", categories);
            result.addObject("lang", language);
        } catch (Throwable oops) {
            result = this.createEditModelAndView(conference, "conference.commit.error");
            result.addObject("categories", categories);
            result.addObject("lang", language);
        }
        return result;
    }

    //Save as final

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "saveFinal")
    public ModelAndView saveFinal(Conference conference, BindingResult binding) {
        ModelAndView result;
        final String language = LocaleContextHolder.getLocale().getLanguage();
        Collection<Category> categories = this.categoryService.findAll();
        try {
            Assert.notNull(conference);
            conference = this.conferenceService.reconstruct(conference, binding);
            conference = this.conferenceService.saveFinal(conference);
            result = new ModelAndView("redirect:listAll.do");
        } catch (ValidationException e) {
            result = this.createEditModelAndView(conference, null);
            result.addObject("categories", categories);
            result.addObject("lang", language);
        } catch (Throwable oops) {
            result = this.createEditModelAndView(conference, "conference.commit.error");
            result.addObject("categories", categories);
            result.addObject("lang", language);
        }
        return result;
    }

    //Delete

    @RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int conferenceId) {
        ModelAndView result;
        try {
            Conference conference = this.conferenceService.findOne(conferenceId);
            Assert.notNull(conference);
            this.conferenceService.delete(conference);
            result = new ModelAndView("redirect:listAll.do");
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    private ModelAndView createEditModelAndView(Conference conference, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("conference/administrator/edit");
        result.addObject("conference", conference);
        result.addObject("message", messageCode);
        return result;
    }
}
