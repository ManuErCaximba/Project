

package controllers.sponsor;

import controllers.AbstractController;
import domain.Actor;
import domain.Sponsor;
import forms.SponsorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.SponsorService;

import javax.validation.Valid;

;

@Controller
@RequestMapping("sponsor")
public class SponsorController extends AbstractController {

    @Autowired
    private AdministratorService	administratorService;

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private ActorService			actorService;




    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView result;
        SponsorForm sponsorForm;
        sponsorForm = new SponsorForm();
        result = this.createEditModelAndView(sponsorForm);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid final SponsorForm sponsorForm, final BindingResult binding) {
        ModelAndView result;
        Sponsor s;

        if (this.actorService.existUsername(sponsorForm.getUsername()) == false) {
            binding.rejectValue("username", "error.username");
            result = this.createEditModelAndView(sponsorForm);
        } else if (!sponsorForm.getPassword().equals(sponsorForm.getConfirmPass())) {
            binding.rejectValue("password", "error.password");
            result = this.createEditModelAndView(sponsorForm);
        } else if (binding.hasErrors())
            result = this.createEditModelAndView(sponsorForm);
        else
            try {
                s = this.sponsorService.reconstruct(sponsorForm, binding);
                this.sponsorService.save(s);
                result = new ModelAndView("redirect:/");
            } catch (final Throwable oops) {
                if (binding.hasErrors())
                    result = this.createEditModelAndView(sponsorForm, "error.duplicated");
                result = this.createEditModelAndView(sponsorForm, "error.commit.error");
            }
        return result;
    }

    protected ModelAndView createEditModelAndView(final SponsorForm sponsorForm) {
        ModelAndView result;
        result = this.createEditModelAndView(sponsorForm, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final SponsorForm sponsorForm, final String messageCode) {

        final ModelAndView result;

        result = new ModelAndView("sponsor/create");
        result.addObject("sponsorForm", sponsorForm);
        result.addObject("message", messageCode);

        return result;
    }

    @RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;

        final Actor user = this.actorService.getActorLogged();
        final Sponsor a= this.sponsorService.findOne(user.getId());
        Assert.notNull(a);
        result = this.editModelAndView(a);

        return result;
    }

    @RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "update")
    public ModelAndView update(@Valid Sponsor a, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.editModelAndView(a);
        else
            try {
                a = this.sponsorService.reconstruct(a, binding);
                this.sponsorService.save(a);
                result = new ModelAndView("redirect:/profile/display.do");
            } catch (final Throwable oops) {
                result = this.editModelAndView(a, "actor.commit.error");
            }
        return result;
    }

    protected ModelAndView editModelAndView(final Sponsor a) {
        ModelAndView result;
        result = this.editModelAndView(a, null);
        return result;
    }

    protected ModelAndView editModelAndView(final Sponsor a, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("sponsor/sponsor/edit");
        result.addObject("sponsor", a);
        result.addObject("messageCode", messageCode);

        return result;
    }
}