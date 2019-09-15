package controllers.administrator;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import forms.AdministratorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;

import javax.validation.Valid;

@Controller
@RequestMapping("administrator")
public class AdministratorController extends AbstractController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView result;
        UserAccount userAccount = LoginService.getPrincipal();

        try {
            Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
            result = new ModelAndView("administrator/dashboard");

            /*Q1*/
            result.addObject("Q11", this.administratorService.getAvgSubmissionPerConference());
            result.addObject("Q12", this.administratorService.getMinSubmissionPerConference());
            result.addObject("Q13", this.administratorService.getMaxSubmissionPerConference());
            result.addObject("Q14", this.administratorService.getStddevSubmissionPerConference());

            /*Q2*/
            result.addObject("Q21", this.administratorService.getAvgNumberRegistrationConference());
            result.addObject("Q22", this.administratorService.getMinNumberRegistrationConference());
            result.addObject("Q23", this.administratorService.getMaxNumberRegistrationConference());
            result.addObject("Q24", this.administratorService.getStddevNumberRegistrationConference());

            /*Q3*/
            result.addObject("Q31", this.administratorService.getAvgConferenceFee());
            result.addObject("Q32", this.administratorService.getMinConferenceFee());
            result.addObject("Q33", this.administratorService.getMaxConferenceFee());
            result.addObject("Q34", this.administratorService.getStddevConferenceFee());

            /*Q4*/
            result.addObject("Q41", this.administratorService.getAvgNumberOfDaysConference());
            result.addObject("Q42", this.administratorService.getMinNumberOfDaysConference());
            result.addObject("Q43", this.administratorService.getMaxNumberOfDaysConference());
            result.addObject("Q44", this.administratorService.getStddevNumberOfDaysConference());

            /*Q5*/
            result.addObject("Q51", this.administratorService.getAvgConferencesPerCategory());
            result.addObject("Q52", this.administratorService.getMinConferencesPerCategory());
            result.addObject("Q53", this.administratorService.getMaxConferencesPerCategory());
            result.addObject("Q54", this.administratorService.getStddevConferencesPerCategory());

            /*Q6*/
            result.addObject("Q61", this.administratorService.getAvgNumberCommentsConference());
            result.addObject("Q62", this.administratorService.getMinNumberCommentsConference());
            result.addObject("Q63", this.administratorService.getMaxNumberCommentsConference());
            result.addObject("Q64", this.administratorService.getStddevNumberCommentsConference());

            /*Q7*/
            result.addObject("Q71", this.administratorService.getAvgNumberCommentsActivity());
            result.addObject("Q72", this.administratorService.getMinNumberCommentsActivity());
            result.addObject("Q73", this.administratorService.getMaxNumberCommentsActivity());
            result.addObject("Q74", this.administratorService.getStddevNumberCommentsActivity());

        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        try {
            AdministratorForm administratorForm;
            administratorForm = new AdministratorForm();
            result = this.createEditModelAndView(administratorForm);
        } catch(Exception oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(@Valid final AdministratorForm administratorForm, final BindingResult binding) {
        ModelAndView result;
        Administrator admin;

        try {
            if (this.actorService.existUsername(administratorForm.getUsername()) == false) {
                binding.rejectValue("username", "error.username");
                result = this.createEditModelAndView(administratorForm);
            } else if (!administratorForm.getPassword().equals(administratorForm.getConfirmPass())) {
                binding.rejectValue("password", "error.password");
                result = this.createEditModelAndView(administratorForm);
            } else if (binding.hasErrors())
                result = this.createEditModelAndView(administratorForm);
            else {
                admin = this.administratorService.reconstruct(administratorForm, binding);
                this.administratorService.save(admin);
                result = new ModelAndView("redirect:/");
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(administratorForm);
        }
        return result;
    }

    protected ModelAndView createEditModelAndView(final AdministratorForm adminForm) {
        ModelAndView result;
        result = this.createEditModelAndView(adminForm, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final AdministratorForm adminForm, final String messageCode) {

        final ModelAndView result;

        result = new ModelAndView("administrator/register");
        result.addObject("administratorForm", adminForm);
        result.addObject("message", messageCode);

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;
        try {
            final Actor user = this.actorService.getActorLogged();
            final Administrator a = this.administratorService.findOne(user.getId());
            Assert.notNull(a);
            result = this.editModelAndView(a);
        } catch(Exception oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "update")
    public ModelAndView update(@Valid Administrator a, final BindingResult binding) {
        ModelAndView result;

        try {
            if (binding.hasErrors())
                result = this.editModelAndView(a);
            else {

                a = this.administratorService.reconstruct(a, binding);
                this.administratorService.save(a);
                result = new ModelAndView("redirect:/profile/display.do");
            }
        } catch (final Throwable oops) {
                result = this.editModelAndView(a, "actor.commit.error");
        }
        return result;
    }

    protected ModelAndView editModelAndView(final Administrator a) {
        ModelAndView result;
        result = this.editModelAndView(a, null);
        return result;
    }

    protected ModelAndView editModelAndView(final Administrator a, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("administrator/administrator/edit");
        result.addObject("administrator", a);
        result.addObject("messageCode", messageCode);

        return result;
    }

}
