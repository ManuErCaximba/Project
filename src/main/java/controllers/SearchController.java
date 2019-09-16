package controllers;

import domain.Conference;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.FinderService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class SearchController extends AbstractController {

    @Autowired
    private FinderService finderService;

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/search/result", method = RequestMethod.GET)
    public ModelAndView result(@RequestParam String keyword) {
        ModelAndView result;

        Collection<Conference> conferences = this.finderService.getConferenceByKeyword(keyword);
        result = new ModelAndView("search/result");
        result.addObject("conferences", conferences);

        return result;
    }

    // Search ------------------------------------------------------------------
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search() {
        ModelAndView result;
        SearchForm search = new SearchForm();

        result = new ModelAndView("search");
        result.addObject("searchForm", search);

        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
    public ModelAndView save(@Valid SearchForm searchForm, final BindingResult binding) {
        ModelAndView result;

        try {
            if (binding.hasErrors()) {
                result = new ModelAndView("search");
                result.addObject("searchForm", searchForm);
            } else {
                Collection<Conference> conferences = this.finderService.getConferenceByKeyword(searchForm.getKeyword());
                result = new ModelAndView("search/result");
                result.addObject("conferences", conferences);
            }
        } catch (final Exception oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

}
