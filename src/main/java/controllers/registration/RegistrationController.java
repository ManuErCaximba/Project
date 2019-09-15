package controllers.registration;


import controllers.AbstractController;
import domain.*;
import forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Calendar;
import java.util.Collection;

@Controller
@RequestMapping("registration")
public class RegistrationController extends AbstractController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/administrator/listAdmin", method = RequestMethod.GET)
    public ModelAndView listAdmin(@RequestParam int conferenceId) {
        ModelAndView result;
        try {
            Actor actor = this.actorService.getActorLogged();
            Assert.notNull(actor);
            Assert.isTrue(actor instanceof Administrator);
            Conference conference = this.conferenceService.findOne(conferenceId);
            Assert.notNull(conference);
            Collection<Registration> registrations = conference.getRegistrations();
            Assert.notNull(registrations);
            result = new ModelAndView("registration/administrator/listAdmin");
            result.addObject("registrations", registrations);
            result.addObject("requestURI", "registration/administrator/listAdmin.do");
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }


    @RequestMapping(value = "/author/listAuthor", method = RequestMethod.GET)
    public ModelAndView listAuthor() {
        ModelAndView result;

        Author author = this.authorService.findOne(this.actorService.getActorLogged().getId());
        Assert.notNull(author);
        Collection<Conference> conferences = this.conferenceService.getConferencesByAuthor(author.getId());
        Assert.notNull(conferences);
        result = new ModelAndView("registration/author/listAuthor");
        result.addObject("conferences", conferences);
        result.addObject("author", author);
        result.addObject("requestURI", "registration/author/listAuthor.do");


        return result;
    }

    @RequestMapping(value = "/author/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int conferenceId) {
        ModelAndView result;
        try {
            Collection<String> brandList = this.configurationService.findAll().iterator().next().getCreditCardMakes();
            Assert.notNull(brandList);
            Registration registration = this.registrationService.create();
            result = new ModelAndView("registration/author/create");
            result.addObject("registration", registration);
            result.addObject("brandList", brandList);
            result.addObject("conferenceId", conferenceId);
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/author/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Registration registration, @RequestParam int conferenceId, BindingResult binding){
        ModelAndView result;

        try{
            Author author = this.authorService.findOne(this.actorService.getActorLogged().getId());
            Assert.notNull(registration);
            registration = this.registrationService.reconstruct(registration, binding);
            registration = this.registrationService.save(registration, conferenceId);
            messageService.notificationRegisterConference(author, conferenceId);
            result = new ModelAndView("redirect:listAuthor.do");
        }catch (ValidationException e){
            Collection<String> brandList = this.configurationService.findAll().iterator().next().getCreditCardMakes();
            result = this.createEditModelAndView(registration);
            result.addObject("registration", registration);
            result.addObject("brandList", brandList);
            result.addObject("conferenceId", conferenceId);
            result.addObject("brandList", brandList);
        } catch (Throwable oops){
            Collection<String> brandList = this.configurationService.findAll().iterator().next().getCreditCardMakes();
            result = this.createEditModelAndView(registration, "registration.commit.error");
            result.addObject("conferenceId", conferenceId);
            result.addObject("brandList", brandList);
        }
        return result;
    }

    protected ModelAndView createEditModelAndView(final Registration registration) {
        ModelAndView result;
        result = this.createEditModelAndView(registration, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Registration registration, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("registration/author/create");
        result.addObject("registration", registration);
        result.addObject("message", messageCode);
        return result;
    }

}
