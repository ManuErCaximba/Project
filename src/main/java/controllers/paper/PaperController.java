package controllers.paper;

import controllers.AbstractController;
import domain.Author;
import domain.Paper;
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
import services.AuthorService;
import services.PaperService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("paper/author")
public class PaperController extends AbstractController {

    @Autowired
    PaperService paperService;

    @Autowired
    ActorService actorService;

    @Autowired
    AuthorService authorService;

    // List --------------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Paper> papers;

        try {
            final Author author = (Author) this.actorService.getActorLogged();

            this.paperService.update();

            papers = this.paperService.findAllByAuthor(author);

            result = new ModelAndView("paper/author/list");
            result.addObject("papers", papers);
            result.addObject("requestURI", "paper/author/list.do");
        } catch(Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam final int paperId) {
        ModelAndView result;
        final Paper paper;

        try {
            final Author author = (Author) this.actorService.getActorLogged();
            paper = this.paperService.findOne(paperId);
            this.paperService.update();
            Assert.isTrue(paper.getAuthors().contains(author));
            result = new ModelAndView("paper/author/show");
            result.addObject("paper", paper);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Create & Edit ------------------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Paper paper;

        try {
            Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
            paper = this.paperService.create();
            result = this.createEditModelAndView(paper);
        } catch(Exception oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int paperId) {
        ModelAndView result;
        Paper paper;

        try {
            final Author author = (Author) this.actorService.getActorLogged();
            paper = this.paperService.findOne(paperId);
            Assert.isTrue(paper.getAuthors().contains(author));
            Assert.isTrue(!paper.getIsInSubmission());
            result = this.createEditModelAndView(paper);
            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("paper") @Valid Paper paperForm, final BindingResult binding) {
        ModelAndView result;
        Paper paper;

        try {
            if (binding.hasErrors())
                result = this.createEditModelAndView(paperForm, null, null);
            else {
                paper = this.paperService.reconstruct(paperForm, binding);
                this.paperService.save(paper);
                result = new ModelAndView("redirect:list.do");
            }
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(paperForm, "paper.commit.error", null);
        }
        return result;
    }

    // Delete --------------------------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int paperId) {
        ModelAndView result;
        Paper paper;

        try {
            final Author author = (Author) this.actorService.getActorLogged();
            paper = this.paperService.findOne(paperId);
            Assert.isTrue(paper.getAuthors().contains(author));
            Assert.isTrue(!paper.getIsInSubmission());
            this.paperService.delete(paper);
            result = new ModelAndView("redirect:list.do");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        return result;
    }

    //ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Paper paper) {
        ModelAndView result;

        result = this.createEditModelAndView(paper, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Paper paper, final String message,
                                                  final Integer errorNumber) {
        ModelAndView result;
        Collection<Author> authors = this.authorService.findAll();
        Author user = (Author) this.actorService.getActorLogged();
        authors.remove(user);

        if(paper.getId() == 0)
            result = new ModelAndView("paper/author/create");
        else
            result = new ModelAndView("paper/author/edit");

        result.addObject("paper", paper);
        result.addObject("authors", authors);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);

        return result;
    }
}
