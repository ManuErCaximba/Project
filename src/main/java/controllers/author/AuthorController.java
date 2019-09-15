package controllers.author;

import controllers.AbstractController;
import domain.Actor;
import domain.Author;
import forms.AuthorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AdministratorService;
import services.AuthorService;

import javax.validation.Valid;


@Controller
@RequestMapping("author")
public class AuthorController extends AbstractController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ActorService actorService;




    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView result;
        AuthorForm authorForm;
        authorForm = new AuthorForm();
        result = this.createEditModelAndView(authorForm);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid final AuthorForm authorForm, final BindingResult binding) {
        ModelAndView result;
        Author s;

        if (this.actorService.existUsername(authorForm.getUsername()) == false) {
            binding.rejectValue("username", "error.username");
            result = this.createEditModelAndView(authorForm);
        } else if (!authorForm.getPassword().equals(authorForm.getConfirmPass())) {
            binding.rejectValue("password", "error.password");
            result = this.createEditModelAndView(authorForm);
        } else if (binding.hasErrors())
            result = this.createEditModelAndView(authorForm);
        else
            try {
                s = this.authorService.reconstruct(authorForm, binding);
                this.authorService.save(s);
                result = new ModelAndView("redirect:/");
            } catch (final Throwable oops) {
                if (binding.hasErrors())
                    result = this.createEditModelAndView(authorForm, "error.duplicated");
                result = this.createEditModelAndView(authorForm, "error.commit.error");
            }
        return result;
    }

    protected ModelAndView createEditModelAndView(final AuthorForm authorForm) {
        ModelAndView result;
        result = this.createEditModelAndView(authorForm, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final AuthorForm authorForm, final String messageCode) {

        final ModelAndView result;

        result = new ModelAndView("author/create");
        result.addObject("authorForm", authorForm);
        result.addObject("message", messageCode);

        return result;
    }

    @RequestMapping(value = "/author/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result;
        try {
            final Actor user = this.actorService.getActorLogged();
            final Author a = this.authorService.findOne(user.getId());
            Assert.notNull(a);
            Assert.isTrue(a.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
            result = this.editModelAndView(a);
        } catch (Exception oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/author/edit", method = RequestMethod.POST, params = "update")
    public ModelAndView update(@Valid Author a, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.editModelAndView(a);
        else
            try {
                a = this.authorService.reconstruct(a, binding);
                this.authorService.save(a);
                result = new ModelAndView("redirect:/profile/display.do");
            } catch (final Throwable oops) {
                result = this.editModelAndView(a, "actor.commit.error");
            }
        return result;
    }

    protected ModelAndView editModelAndView(final Author a) {
        ModelAndView result;
        result = this.editModelAndView(a, null);
        return result;
    }

    protected ModelAndView editModelAndView(final Author a, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("author/author/edit");
        result.addObject("author", a);
        result.addObject("messageCode", messageCode);

        return result;
    }
}
