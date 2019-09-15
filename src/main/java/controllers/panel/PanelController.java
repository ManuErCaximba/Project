package controllers.panel;

import controllers.AbstractController;
import domain.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ConferenceService;

import java.util.Collection;
import domain.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("panel")
public class PanelController extends AbstractController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ActorService actorService;

    // List --------------------------------------------------------------------------
    /*
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam final int conferenceId) {
        ModelAndView result;
        Collection<Activity> panels;
        Conference conference = this.conferenceService.findOne(conferenceId);
        Date now = new Date();

        try {
            panels = this.activityService.getPanelsByConference(conferenceId);

            Assert.isTrue(now.after(conference.getCameraReadyDeadline()));
            Assert.isTrue(now.before(conference.getStartDate()));

            result = new ModelAndView("panel/list");
            result.addObject("panels", panels);
            result.addObject("requestURI", "panel/list.do");
            result.addObject("now", now);
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }
    */

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam final int panelId) {
        ModelAndView result;
        final Activity panel;

        try {
            final Administrator administrator = (Administrator) this.actorService.getActorLogged();
            result = new ModelAndView("panel/show");
            panel = this.activityService.findOne(panelId);
            result.addObject("panel", panel);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Create & Edit ------------------------------------------------------------------
    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam final int conferenceId) {
        ModelAndView result;
        Activity panel;
        Conference conference = this.conferenceService.findOne(conferenceId);
        Date now = new Date();

        Assert.isTrue(now.after(conference.getCameraReadyDeadline()) && now.before(conference.getStartDate()));

        panel = this.activityService.create();
        panel.setConference(conference);
        result = this.createEditModelAndView(panel);

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int panelId) {
        ModelAndView result;
        Activity panel;
        Date now = new Date();

        try {
            panel = this.activityService.findOne(panelId);
            Assert.isTrue(now.after(panel.getConference().getCameraReadyDeadline()) &&
                    now.before(panel.getConference().getStartDate()));
            result = this.createEditModelAndView(panel);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid @ModelAttribute("panel") Activity panelForm, final BindingResult binding) {
        ModelAndView result;
        Conference conference;
        Activity panel;
        Date schedule = new Date();

        try {
            conference = panelForm.getConference();
            if (binding.hasErrors()) {
                result = this.createEditModelAndView(panelForm, null, null);
                for (final ObjectError e : binding.getAllErrors()) {
                    if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
                        result.addObject("errorNumber", 3);
                }
            } else {
                schedule.setTime(panelForm.getStartMoment().getTime() + (panelForm.getDuration() * 60000));
                if (panelForm.getStartMoment().before(conference.getStartDate()) ||
                        panelForm.getStartMoment().after(conference.getEndDate()))
                    result = this.createEditModelAndView(panelForm, null, 1);
                else if (schedule.before(conference.getStartDate()) ||
                        schedule.after(conference.getEndDate()))
                    result = this.createEditModelAndView(panelForm, null, 2);
                else {
                    panel = this.activityService.reconstruct(panelForm, binding);
                    this.activityService.save(panel);
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
    public ModelAndView delete(@RequestParam final int panelId) {
        ModelAndView result;
        Activity panel;
        Date now = new Date();

        try {
            panel = this.activityService.findOne(panelId);
            this.activityService.delete(panel);
            Assert.isTrue(now.after(panel.getConference().getCameraReadyDeadline()) &&
                    now.before(panel.getConference().getStartDate()));
            result = new ModelAndView("redirect:/");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        return result;
    }

    //ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Activity panel) {
        ModelAndView result;

        result = this.createEditModelAndView(panel, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Activity panel,
                                                  final String message,
                                                  final Integer errorNumber) {
        ModelAndView result;

        if(panel.getId() == 0)
            result = new ModelAndView("panel/administrator/create");
        else
            result = new ModelAndView("panel/administrator/edit");

        result.addObject("panel", panel);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);

        return result;
    }

}