package controllers.sponsorship;

import controllers.AbstractController;
import domain.Configuration;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ConfigurationService;
import services.SponsorshipService;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Collection;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorshipController extends AbstractController {

    @Autowired
    private SponsorshipService sponsorshipService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConfigurationService configurationService;

    // List --------------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Sponsorship> sponsorships;

        final Sponsor sponsor = (Sponsor) this.actorService.getActorLogged();

        sponsorships = this.sponsorshipService.findAllBySponsor(sponsor.getId());

        result = new ModelAndView("sponsorship/sponsor/list");
        result.addObject("sponsorships", sponsorships);
        result.addObject("requestURI", "sponsorship/sponsor/list.do");

        return result;
    }

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        final Sponsorship sponsorship;

        try {
            final Sponsor sponsor = (Sponsor) this.actorService.getActorLogged();
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
            result = new ModelAndView("sponsorship/sponsor/show");
            result.addObject("sponsorship", sponsorship);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Create & Edit ------------------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Sponsorship sponsorship;

        sponsorship = this.sponsorshipService.create();
        result = this.createEditModelAndView(sponsorship);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        Sponsorship sponsorship;

        try {
            final Sponsor sponsor = (Sponsor) this.actorService.getActorLogged();
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
            result = this.createEditModelAndView(sponsorship);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("sponsorshipForm") @Valid SponsorshipForm sponsorshipForm, final BindingResult binding) {
        ModelAndView result;
        Sponsorship sponsorship;
        Calendar now = Calendar.getInstance();
        Configuration configuration = this.configurationService.findAll().iterator().next();

        try {
            if (binding.hasErrors())
                result = this.createEditModelAndView(sponsorshipForm, null, null);
            else if (sponsorshipForm.getExpirationYear() < now.get(Calendar.YEAR))
                result = this.createEditModelAndView(sponsorshipForm, null, 1);
            else if (sponsorshipForm.getExpirationYear() == now.get(Calendar.YEAR) &&
                    sponsorshipForm.getExpirationMonth() < now.get(Calendar.MONTH))
                result = this.createEditModelAndView(sponsorshipForm, null, 2);
            else if (!configuration.getCreditCardMakes().contains(sponsorshipForm.getBrandName()))
                result = this.createEditModelAndView(sponsorshipForm, null, null);
            else {
                sponsorship = this.sponsorshipService.reconstruct(sponsorshipForm, binding);
                this.sponsorshipService.save(sponsorship);
                result = new ModelAndView("redirect:list.do");
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(sponsorshipForm, "sponsorship.commit.error", null);
        }
        return result;
    }

    // Delete --------------------------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int sponsorshipId) {
        ModelAndView result;
        Sponsorship sponsorship;

        try {
            final Sponsor sponsor = (Sponsor) this.actorService.getActorLogged();
            sponsorship = this.sponsorshipService.findOne(sponsorshipId);
            Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
            this.sponsorshipService.delete(sponsorship);
            result = new ModelAndView("redirect:list.do");
        } catch (final Exception e) {
                result = new ModelAndView("redirect:/");
                return result;
        }

        return result;
    }

    //ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
        ModelAndView result;

        result = this.createEditModelAndView(new SponsorshipForm(sponsorship), null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm, final String message,
                                                  final Integer errorNumber) {
        ModelAndView result;
        Configuration configuration = this.configurationService.findAll().iterator().next();

        if(sponsorshipForm.getId() == 0)
            result = new ModelAndView("sponsorship/sponsor/create");
        else
            result = new ModelAndView("sponsorship/sponsor/edit");

        result.addObject("sponsorshipForm", sponsorshipForm);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);
        result.addObject("configuration", configuration);

        return result;
    }
}
