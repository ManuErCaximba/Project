package controllers.finder;

import controllers.AbstractController;
import domain.Actor;
import domain.Conference;
import domain.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("finder")
public class FinderController extends AbstractController {

    @Autowired
    private FinderService finderService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private CategoryService categoryService;


    // List ---------------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Conference> conferences;

        try {
            result = new ModelAndView("finder/list");

            final Actor user = this.actorService.getActorLogged();

            Finder finder;
            finder = this.finderService.searchByActor(user.getId());
            Assert.isTrue(finder.getActor().equals(user));

            conferences = finder.getConferences();

            result.addObject("conferences", conferences);
            result.addObject("finder", finder);
            result.addObject("requestURI", "finder/list.do");
        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Edit --------------------------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;
        Finder finder;

        try {
            final Actor user = this.actorService.getActorLogged();

            finder = this.finderService.searchByActor(user.getId());
            Assert.notNull(finder);
            Assert.isTrue(finder.getActor().equals(user));
            result = this.createEditModelAndView(finder);
        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("finder") Finder finder, final BindingResult binding) {
        ModelAndView result;
        Collection<String> nameEs = this.categoryService.getNamesEs();
        Collection<String> nameEn = this.categoryService.getNamesEn();

        try {
            if (binding.hasErrors())
                result = this.createEditModelAndView(finder);
            else if (finder.getStartDate() == null ^ finder.getEndDate() == null)
                result = this.createEditModelAndView(finder, null, 1);
            else if (nameEs.contains(finder.getCategoryName()) || nameEn.contains(finder.getCategoryName()))
                result = new ModelAndView("redirect:/");
            else {
                finder = this.finderService.reconstruct(finder, binding);
                this.finderService.save(finder);
                result = new ModelAndView("redirect:/finder/list.do");
            }
        } catch (final Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    // Model & View ------------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Finder finder) {
        ModelAndView result;

        result = this.createEditModelAndView(finder, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode, Integer errorNumber) {
        ModelAndView result;
        String language = LocaleContextHolder.getLocale().getLanguage();

        result = new ModelAndView("finder/edit");
        result.addObject("finder", finder);
        result.addObject("errorNumber", errorNumber);
        result.addObject("messageCode", messageCode);

        if (language.equals("es")) {
            List<String> cNames = new ArrayList<>();
            cNames.add("");
            cNames.addAll(this.categoryService.getNamesEs());
            result.addObject("cNames", cNames);
        } else {
            List<String> cNames = new ArrayList<>();
            cNames.add("");
            cNames.addAll(this.categoryService.getNamesEn());
            result.addObject("cNames", cNames);
        }

        return result;
    }
}
