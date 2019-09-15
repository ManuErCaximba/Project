package controllers.section;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Section;
import domain.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.SectionService;
import services.TutorialService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("section")
public class SectionController extends AbstractController{

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TutorialService tutorialService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int sectionId){
        ModelAndView result;
        final Section section;
        try {
            section = this.sectionService.findOne(sectionId);
            Assert.notNull(section);
            result = new ModelAndView("section/show");
            result.addObject("section", section);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Collection<Tutorial> tutorials;
        try {
            Section section = this.sectionService.create();
            tutorials = this.tutorialService.getFutureTutorials();
            Assert.notNull(tutorials);
            result = new ModelAndView("section/administrator/create");
            result.addObject("section", section);
            result.addObject("tutorials", tutorials);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int sectionId) {
        ModelAndView result;
        Section section;
        Collection<Tutorial> tutorials;
        try {
            section = this.sectionService.findOne(sectionId);
            Assert.notNull(sectionId);
            tutorials = this.tutorialService.getFutureTutorials();
            Actor user = this.actorService.getActorLogged();
            Assert.isTrue(user instanceof Administrator);
            result = new ModelAndView("section/administrator/edit");
            result.addObject("section", section);
            result.addObject("tutorials", tutorials);
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Section section, BindingResult binding){
        ModelAndView result;
        Collection<Tutorial> tutorials = this.tutorialService.getFutureTutorials();
        try{
            Assert.notNull(section);
            section = this.sectionService.reconstruct(section, binding);
            section = this.sectionService.save(section);
            result = new ModelAndView("redirect:/");
        }catch (ValidationException e){
            result = this.createEditModelAndView(section);
            result.addObject("section", section);
            result.addObject("tutorials", tutorials);
        } catch (Throwable oops){
            result = this.createEditModelAndView(section, "section.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int sectionId){
        ModelAndView result;
        Section section;
        try{
            section = this.sectionService.findOne(sectionId);
            Assert.notNull(section);
            this.sectionService.delete(section);
            result = new ModelAndView("redirect:/");
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    private ModelAndView createEditModelAndView(final Section section) {
        ModelAndView result;
        result = this.createEditModelAndView(section, null);
        return result;
    }

    private ModelAndView createEditModelAndView(final Section section, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("section/administrator/edit");
        result.addObject("section", section);
        result.addObject("message", messageCode);
        return result;
    }

}
