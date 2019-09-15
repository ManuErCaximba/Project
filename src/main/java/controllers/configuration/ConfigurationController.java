package controllers.configuration;

import controllers.AbstractController;
import domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;

import javax.validation.Valid;

@Controller
@RequestMapping("configuration/administrator")
public class ConfigurationController extends AbstractController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService administratorService;

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show() {
        ModelAndView result;
        final Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            result = new ModelAndView("configuration/administrator/show");
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result.addObject("configuration", configuration);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Scoring ------------------------------------------------------------------------
    @RequestMapping(value = "/scoring", method = RequestMethod.GET)
    public ModelAndView scoring() {
        ModelAndView result;
        final Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            this.administratorService.getScoreAllAuthor();
            result = new ModelAndView("configuration/administrator/show");
            result.addObject("configuration", configuration);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/showVoidWordsEs", method = RequestMethod.GET)
    public ModelAndView showVoidWordsEs() {
        ModelAndView result;
        final Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result = new ModelAndView("configuration/administrator/showVoidWordsEs");
            result.addObject("voidWordsEs", configuration.getVoidWordsEs());
            result.addObject("requestURI", "configuration/administrator/showVoidWordsEs.do");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/showVoidWordsEn", method = RequestMethod.GET)
    public ModelAndView showVoidWordsEn() {
        ModelAndView result;
        final Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result = new ModelAndView("configuration/administrator/showVoidWordsEn");
            result.addObject("voidWordsEn", configuration.getVoidWordsEn());
            result.addObject("requestURI", "configuration/administrator/showVoidWordsEn.do");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Edit ---------------------------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;
        Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result = this.createEditModelAndView(configuration);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("configuration") @Valid Configuration config, final BindingResult binding) {
        ModelAndView result;
        Configuration configuration;

        try {
            if (binding.hasErrors())
                result = this.createEditModelAndView(config, null);
            else {
                configuration = this.configurationService.reconstruct(config, binding);
                this.configurationService.save(configuration);
                result = new ModelAndView("redirect:show.do");
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(config, "sponsorship.commit.error");
        }
        return result;
    }

    //ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Configuration configuration) {
        ModelAndView result;

        result = this.createEditModelAndView(configuration, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
        ModelAndView result;

        result = new ModelAndView("configuration/administrator/edit");

        result.addObject("configuration", configuration);
        result.addObject("message", message);

        return result;
    }
}
