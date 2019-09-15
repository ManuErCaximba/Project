package controllers.comment;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("comment")
public class CommentController extends AbstractController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int conferenceId){
        ModelAndView result;
        try{
            Conference conference = this.conferenceService.findOne(conferenceId);
            Assert.notNull(conference);
            Collection<Comment> comments = conference.getComments();
            result = new ModelAndView("comment/list");
            result.addObject("comments", comments);
            result.addObject("conferenceId", conferenceId);
            result.addObject("requestURI", "comment/list.do");
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/listCommentsTutorial", method = RequestMethod.GET)
    public ModelAndView listCommentsTutorial(@RequestParam int tutorialId){
        ModelAndView result;
        try{
            Tutorial tutorial = this.tutorialService.findOne(tutorialId);
            Assert.notNull(tutorial);
            Collection<Comment> comments = tutorial.getComments();
            result = new ModelAndView("comment/listCommentsTutorial");
            result.addObject("comments", comments);
            result.addObject("tutorialId", tutorialId);
            result.addObject("requestURI", "comment/listCommentsTutorial.do");
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/listCommentsPresentation", method = RequestMethod.GET)
    public ModelAndView listCommentsPresentation(@RequestParam int presentationId){
        ModelAndView result;
        try{
            Presentation presentation = this.presentationService.findOne(presentationId);
            Assert.notNull(presentation);
            Collection<Comment> comments = presentation.getComments();
            result = new ModelAndView("comment/listCommentsPresentation");
            result.addObject("comments", comments);
            result.addObject("presentationId", presentationId);
            result.addObject("requestURI", "comment/listCommentsPresentation.do");
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/createConference", method = RequestMethod.GET)
    public ModelAndView createConference(@RequestParam int conferenceId){
        ModelAndView result;
        Comment comment;
        Conference conference;
        Actor actor = this.actorService.getActorLogged();
        try{
            conference = this.conferenceService.findOne(conferenceId);
            Assert.notNull(conference);
            comment = this.commentService.create();
            result = new ModelAndView("comment/createConference");
            result.addObject("comment", comment);
            result.addObject("conference", conference);
            result.addObject("conferenceId", conferenceId);
        } catch (Throwable oops){
            if(actor instanceof  Administrator) {
                result = new ModelAndView("redirect:conference/administrator/show.do?conferenceId="+conferenceId);
            } else {
                result = new ModelAndView("redirect:conference/showNotLogged.do?conferenceId="+conferenceId);
            }
        }
        return result;
    }

    @RequestMapping(value = "/createTutorial", method = RequestMethod.GET)
    public ModelAndView createTutorial(@RequestParam int tutorialId){
        ModelAndView result;
        Tutorial tutorial;
        Comment comment;
        try{
            tutorial = this.tutorialService.findOne(tutorialId);
            Assert.notNull(tutorial);
            comment = this.commentService.create();
            result = new ModelAndView("comment/createTutorial");
            result.addObject("comment", comment);
            result.addObject("tutorial", tutorial);
            result.addObject("tutorialId", tutorialId);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:tutorial/show.do?tutorialId="+tutorialId);

        }
        return result;
    }

    @RequestMapping(value = "/createPresentation", method = RequestMethod.GET)
    public ModelAndView createPresentation(@RequestParam int presentationId){
        ModelAndView result;
        Presentation presentation;
        Comment comment;
        try{
            presentation = this.presentationService.findOne(presentationId);
            Assert.notNull(presentation);
            comment = this.commentService.create();
            result = new ModelAndView("comment/createPresentation");
            result.addObject("comment", comment);
            result.addObject("presentation", presentation);
            result.addObject("presentationId", presentationId);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:presentation/show.do?presentationId="+presentationId);

        }
        return result;
    }

    @RequestMapping(value="/createConference", method = RequestMethod.POST, params = "saveConference")
    public ModelAndView saveConference(@ModelAttribute("comment") Comment comment, @RequestParam int conferenceId, BindingResult binding){
        ModelAndView result;

        try{
            comment = this.commentService.reconstruct(comment, binding);
            this.commentService.saveConference(comment, conferenceId);
            result = new ModelAndView("redirect:/comment/list.do?conferenceId="+conferenceId);
        }catch (ValidationException v){
            result = new ModelAndView("comment/createConference");
            result.addObject("comment", comment);
            result.addObject("conferenceId", conferenceId);
        }catch (Throwable oops){
            result = new ModelAndView("comment/createConference");
            result.addObject("comment", comment);
            result.addObject("conferenceId", conferenceId);
            result.addObject("message", "comment.commit.error");
        }
        return result;
    }

    @RequestMapping(value="/createTutorial", method = RequestMethod.POST, params = "saveTutorial")
    public ModelAndView saveTutorial(@ModelAttribute("comment") Comment comment, @RequestParam int tutorialId, BindingResult binding){
        ModelAndView result;

        try{
            comment = this.commentService.reconstruct(comment, binding);
            this.commentService.saveTutorial(comment, tutorialId);
            result = new ModelAndView("redirect:/comment/listCommentsTutorial.do?tutorialId="+tutorialId);
        }catch (ValidationException v){
            result = new ModelAndView("comment/createTutorial");
            result.addObject("comment", comment);
            result.addObject("tutorialId", tutorialId);
        }catch (Throwable oops){
            result = new ModelAndView("comment/createTutorial");
            result.addObject("comment", comment);
            result.addObject("tutorialId", tutorialId);
            result.addObject("message", "comment.commit.error");
        }
        return result;
    }

    @RequestMapping(value="/createPresentation", method = RequestMethod.POST, params = "savePresentation")
    public ModelAndView savePresentation(@ModelAttribute("comment") Comment comment, @RequestParam int presentationId, BindingResult binding){
        ModelAndView result;

        try{
            comment = this.commentService.reconstruct(comment, binding);
            this.commentService.savePresentation(comment, presentationId);
            result = new ModelAndView("redirect:/comment/listCommentsPresentation.do?presentationId="+presentationId);
        }catch (ValidationException v){
            result = new ModelAndView("comment/createPresentation");
            result.addObject("comment", comment);
            result.addObject("presentationId", presentationId);
        }catch (Throwable oops){
            result = new ModelAndView("comment/createPresentation");
            result.addObject("comment", comment);
            result.addObject("presentationId", presentationId);
            result.addObject("message", "comment.commit.error");
        }
        return result;
    }
}

