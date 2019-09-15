package controllers.tutorial;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ConferenceService;
import services.SectionService;
import services.TutorialService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("tutorial")
public class TutorialController extends AbstractController {

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Collection<Conference> conferences = this.conferenceService.getAllFutureConferences();
        try {
            Tutorial tutorial = this.tutorialService.create();
            Assert.notNull(conferences);
            result = new ModelAndView("tutorial/administrator/create");
            result.addObject("tutorial", tutorial);
            result.addObject("conferences", conferences);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int tutorialId) {
        ModelAndView result;
        Tutorial tutorial;
        Collection<Conference> conferences = this.conferenceService.getAllFutureConferences();
        try {
            tutorial = this.tutorialService.findOne(tutorialId);
            Assert.notNull(tutorial);
            Assert.notNull(conferences);
            Actor user = this.actorService.getActorLogged();
            Assert.isTrue(user instanceof Administrator);
            result = new ModelAndView("tutorial/administrator/edit");
            result.addObject("tutorial", tutorial);
            result.addObject("conferences", conferences);
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
            result.addObject("conferences", conferences);
        }
        return result;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int tutorialId){
        ModelAndView result;
        final Tutorial tutorial;
        Collection<Section> sections;
        try {
            tutorial = this.tutorialService.findOne(tutorialId);
            Assert.notNull(tutorial);
            sections = this.sectionService.getSectionsByTutorial(tutorialId);
            Assert.notNull(sections);
            result = new ModelAndView("tutorial/show");
            result.addObject("tutorial", tutorial);
            result.addObject("sections", sections);
            } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Tutorial tutorial, BindingResult binding){
        ModelAndView result;
        Collection<Conference> conferences = this.conferenceService.getAllFutureConferences();
        try{
            Assert.notNull(conferences);
            tutorial = this.tutorialService.reconstruct(tutorial, binding);
            tutorial = this.tutorialService.save(tutorial);
            result = new ModelAndView("redirect:/");
        }catch (ValidationException e){
            result = this.createEditModelAndView(tutorial);
            result.addObject("tutorial", tutorial);
            result.addObject("conferences", conferences);
        } catch (Throwable oops){
            result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int tutorialId){
        ModelAndView result;
        Tutorial tutorial;
        try{
            tutorial = this.tutorialService.findOne(tutorialId);
            Assert.notNull(tutorialId);
            this.tutorialService.delete(tutorial);
            result = new ModelAndView("redirect:/");
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    private ModelAndView createEditModelAndView(final Tutorial tutorial) {
        ModelAndView result;
        result = this.createEditModelAndView(tutorial, null);
        return result;
    }

    private ModelAndView createEditModelAndView(final Tutorial tutorial, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("tutorial/administrator/edit");
        result.addObject("tutorial", tutorial);
        result.addObject("message", messageCode);
        return result;
    }

}
