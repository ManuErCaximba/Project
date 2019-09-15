package controllers.presentation;

import controllers.AbstractController;
import domain.Conference;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ConferenceService;
import services.PresentationService;

import java.util.Collection;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("presentation")
public class PresentationController extends AbstractController {

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private SubmissionService submissionService;

    // List --------------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam final int conferenceId) {
        ModelAndView result;
        Collection<Presentation> presentations;
        Conference conference = this.conferenceService.findOne(conferenceId);
        Date now = new Date();

        try {
            presentations = this.presentationService.getPresentationsByConference(conferenceId);

            Assert.isTrue(now.after(conference.getCameraReadyDeadline()));
            Assert.isTrue(now.before(conference.getStartDate()));

            result = new ModelAndView("presentation/list");
            result.addObject("presentations", presentations);
            result.addObject("requestURI", "presentation/list.do");
            result.addObject("now", now);
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam final int presentationId) {
        ModelAndView result;
        final Presentation presentation;

        try {
            final Administrator administrator = (Administrator) this.actorService.getActorLogged();
            result = new ModelAndView("presentation/show");
            presentation = this.presentationService.findOne(presentationId);
            result.addObject("presentation", presentation);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Create & Edit ------------------------------------------------------------------
    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam final int conferenceId) {
        ModelAndView result;
        Presentation presentation;
        Conference conference = this.conferenceService.findOne(conferenceId);
        Collection<Submission> submissions = this.submissionService.getSubmissionsAcceptedAndCameraReadyByConference(conferenceId);
        Collection<Presentation> presentations = this.presentationService.findAll();
        for(Presentation p:presentations){
            if(submissions.contains(p.getSubmission()))
                submissions.remove(p.getSubmission());
        }
        Date now = new Date();

        if(submissions.isEmpty()){
            Collection<Conference> conferences;
            conferences = this.conferenceService.getForthcomingConferencesFinal();
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("conference/listForthcoming");
            result.addObject("conferences", conferences);
            result.addObject("lang", language);
            result.addObject("requestURI", "conference/listForthcoming.do");
            result.addObject("now", new Date());
            result.addObject("errorNumber", 1);
        } else {
            Assert.isTrue(now.after(conference.getCameraReadyDeadline()) && now.before(conference.getStartDate()));

            presentation = this.presentationService.create();
            presentation.setConference(conference);
            result = this.createEditModelAndView(presentation, submissions);
        }

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int presentationId) {
        ModelAndView result;
        Presentation presentation;
        Collection<Submission> submissions;
        Date now = new Date();

        try {
            presentation = this.presentationService.findOne(presentationId);
            Assert.isTrue(now.after(presentation.getConference().getCameraReadyDeadline()) &&
                    now.before(presentation.getConference().getStartDate()));
            submissions =
                    this.submissionService.getSubmissionsAcceptedAndCameraReadyByConference(presentation.getConference().getId());
            Collection<Presentation> presentations = this.presentationService.findAll();
            for(Presentation p:presentations){
                if(submissions.contains(p.getSubmission()))
                    submissions.remove(p.getSubmission());
            }
            submissions.add(presentation.getSubmission());
            result = this.createEditModelAndView(presentation, submissions);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid @ModelAttribute("presentation") Presentation presentationForm, final BindingResult binding) {
        ModelAndView result;
        Conference conference;
        Presentation presentation;
        Collection<Submission> submissions;
        Date schedule = new Date();

        try {
            conference = presentationForm.getConference();
            submissions =
                    this.submissionService.getSubmissionsAcceptedAndCameraReadyByConference(conference.getId());
            Assert.isTrue(submissions.contains(presentationForm.getSubmission()));
            if (binding.hasErrors()) {
                result = this.createEditModelAndView(presentationForm, submissions, null, null);
                for (final ObjectError e : binding.getAllErrors()) {
                    if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
                        result.addObject("errorNumber", 3);
                }
            } else {
                schedule.setTime(presentationForm.getStartMoment().getTime() + (presentationForm.getDuration() * 60000));
                if (presentationForm.getStartMoment().before(conference.getStartDate()) ||
                        presentationForm.getStartMoment().after(conference.getEndDate()))
                        result = this.createEditModelAndView(presentationForm, submissions, null, 1);
                else if (schedule.before(conference.getStartDate()) ||
                        schedule.after(conference.getEndDate()))
                        result = this.createEditModelAndView(presentationForm, submissions, null, 2);
                else {
                    presentation = this.presentationService.reconstruct(presentationForm, binding);
                    this.presentationService.save(presentation);
                    result = new ModelAndView("redirect:/");
                }
            }

        } catch (final Exception oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    // Delete --------------------------------------------------------------------------
    @RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int presentationId) {
        ModelAndView result;
        Presentation presentation;
        Date now = new Date();

        try {
            presentation = this.presentationService.findOne(presentationId);
            this.presentationService.delete(presentation);
            Assert.isTrue(now.after(presentation.getConference().getCameraReadyDeadline()) &&
                    now.before(presentation.getConference().getStartDate()));
            result = new ModelAndView("redirect:/");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        return result;
    }

    //ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Presentation presentation, Collection<Submission> submissions) {
        ModelAndView result;

        result = this.createEditModelAndView(presentation, submissions, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Presentation presentation,
                                                  final Collection<Submission> submissions,
                                                  final String message,
                                                  final Integer errorNumber) {
        ModelAndView result;

        if(presentation.getId() == 0)
            result = new ModelAndView("presentation/administrator/create");
        else
            result = new ModelAndView("presentation/administrator/edit");

        result.addObject("presentation", presentation);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);
        result.addObject("submissions", submissions);

        return result;
    }

}
