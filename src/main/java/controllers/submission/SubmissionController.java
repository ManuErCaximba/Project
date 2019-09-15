package controllers.submission;

import controllers.AbstractController;
import domain.*;
import forms.AssignForm;
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

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("submission")
public class SubmissionController extends AbstractController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthorService authorService;

    // List --------------------------------------------------------------------------
    @RequestMapping(value = "/author/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Submission> submissions;
        Actor actor = this.actorService.getActorLogged();
        Date now = new Date();

        submissions = this.submissionService.getSubmissionsByAuthor(actor.getId());
        this.submissionService.update();

        result = new ModelAndView("submission/author/list");
        result.addObject("submissions", submissions);
        result.addObject("requestURI", "submission/author/list.do");
        result.addObject("now", new Date());

        return result;
    }

    @RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
    public ModelAndView adminList(int conferenceId) {
        ModelAndView result;
        Collection<Submission> submissions;
        try {

            submissions = this.submissionService.getSubmissionsByConferenceNotAssigned(conferenceId);
            this.submissionService.update();

            result = new ModelAndView("submission/administrator/list");
            result.addObject("submissions", submissions);
            result.addObject("requestURI", "submission/administrator/list.do");
            result.addObject("now", new Date());
        } catch (Exception oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Assign ------------------------------------------------------------------------
    @RequestMapping(value = "/administrator/autoassign", method = RequestMethod.GET)
    public ModelAndView autoAssign(int submissionId) {
        ModelAndView result;
        Collection<Submission> submissions;
        try {
            Submission submission = this.submissionService.findOne(submissionId);
            Assert.isTrue(!submission.getIsAssigned());
            this.submissionService.assign(submission);

            submissions = this.submissionService.getSubmissionsByConferenceNotAssigned(submission.getConference().getId());
            this.submissionService.update();

            result = new ModelAndView("submission/administrator/list");
            result.addObject("submissions", submissions);
            result.addObject("requestURI", "submission/administrator/list.do");
            result.addObject("now", new Date());
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/assign", method = RequestMethod.GET)
    public ModelAndView assign(int submissionId) {
        ModelAndView result;
        Collection<Reviewer> reviewers;
        try {
            reviewers = this.reviewerService.findAll();
            Assert.isTrue(!this.submissionService.findOne(submissionId).getIsAssigned());
            this.submissionService.update();

            result = this.createEditModelAndView2(submissionId, reviewers);
            result.addObject("requestURI", "submission/administrator/assign.do");
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator/assign", method = RequestMethod.POST, params = "assign")
    public ModelAndView assign(@ModelAttribute("assignForm") @Valid AssignForm assignForm, final BindingResult binding) {
        ModelAndView result;
        Collection<Reviewer> reviewers = this.reviewerService.findAll();
        try {
            if (binding.hasErrors())
                result = this.createEditModelAndView2(assignForm.getSubmissionId(), reviewers, null, null);
            else {
                this.submissionService.reconstructAssign(assignForm, binding);
                result = new ModelAndView("redirect:/");
            }
        } catch (final Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    // Show --------------------------------------------------------------------------
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam final int submissionId) {
        ModelAndView result;
        final Submission submission;

        try {
            submission = this.submissionService.findOne(submissionId);
            this.submissionService.update();
            result = new ModelAndView("submission/show");
            result.addObject("submission", submission);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    // Create & Edit ------------------------------------------------------------------
    @RequestMapping(value = "/author/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam final int conferenceId) {
        ModelAndView result;
        Submission submission = this.submissionService.create();
        Conference conference = this.conferenceService.findOne(conferenceId);
        Date now = new Date();

        try {
            Assert.isTrue(now.before(conference.getSubmissionDeadline()));

            final Author author = (Author) this.actorService.getActorLogged();
            Collection<Paper> papers = this.paperService.findAllByAuthor(author);

            this.submissionService.update();

            if (papers.isEmpty()) {
                result = new ModelAndView("paper/author/create");
                result.addObject("paper", this.paperService.create());
                result.addObject("errorNumber", 1);
            } else {
                submission.setConference(conference);
                result = this.createEditModelAndView(submission, papers);
            }
        } catch (Exception oops) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    /*
    @RequestMapping(value = "/author/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int submissionId) {
        ModelAndView result;
        Submission submission = this.submissionService.findOne(submissionId);
        Conference conference = submission.getConference();
        Date now = new Date();

        try {
            Assert.isTrue(now.before(conference.getSubmissionDeadline()));

            final Author author = (Author) this.actorService.getActorLogged();
            Collection<Paper> papers = this.paperService.findAllByAuthor(author);

            this.submissionService.update();

            if(papers.isEmpty()){
                result = new ModelAndView("paper/author/create");
                result.addObject("paper", this.paperService.create());
                result.addObject("errorNumber", 1);
            } else {
                Assert.isTrue(submission.getAuthor().equals(author));
                Assert.isTrue(now.before(conference.getSubmissionDeadline()));
                result = this.createEditModelAndView(submission, papers);
            }

            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    */

    @RequestMapping(value = "/author/cameraReady", method = RequestMethod.GET)
    public ModelAndView cameraReady(@RequestParam final int submissionId) {
        ModelAndView result;
        Submission submission = this.submissionService.findOne(submissionId);
        Conference conference = submission.getConference();
        Date now = new Date();
        final Actor actor = this.actorService.getActorLogged();

        try {
            Assert.isTrue(now.after(conference.getSubmissionDeadline()));
            Assert.isTrue(now.before(conference.getCameraReadyDeadline()));
            Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));

            this.submissionService.update();

            Collection<Paper> papers = this.paperService.findAllByAuthor((Author) actor);

            if(papers.isEmpty()){
                result = new ModelAndView("paper/author/create");
                result.addObject("paper", this.paperService.create());
                result.addObject("errorNumber", 1);
            } else {
                Assert.isTrue(submission.getAuthor().equals(actor));
                result = new ModelAndView("submission/author/cameraReady");
                result.addObject("submission", submission);
                result.addObject("papers", papers);
                result.addObject("message", null);
                result.addObject("errorNumber", null);
            }

            return result;
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }
    }

    @RequestMapping(value = "/author/save", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("submission") @Valid Submission submission, final BindingResult binding) {
        ModelAndView result;
        final Actor actor = this.actorService.getActorLogged();
        Author author = this.authorService.findOne(actor.getId());
        try {
            Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
            Collection<Paper> papers = this.paperService.findAllByAuthor((Author) actor);
            if (binding.hasErrors())
                result = this.createEditModelAndView(submission, papers, null, null);
            else {
                submission = this.submissionService.reconstruct(submission, binding);
                Submission saved = this.submissionService.save(submission);
                //TODO:REVISAR
                this.messageService.notificationSubmissionConference(author, saved);
                result = new ModelAndView("redirect:list.do");
            }
        } catch (final Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/author/cameraReady", method = RequestMethod.POST, params = "cameraReady")
    public ModelAndView cameraReady(@ModelAttribute("submission") @Valid Submission submission, final BindingResult binding) {
        ModelAndView result;
        final Actor actor = this.actorService.getActorLogged();

        try {
            Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
            Collection<Paper> papers = this.paperService.findAllByAuthor((Author) actor);
            if (binding.hasErrors())
                result = this.createEditModelAndView(submission, papers, null, null);
            else {
                submission = this.submissionService.reconstructCR(submission, binding);
                this.submissionService.save(submission);
                result = new ModelAndView("redirect:list.do");
            }
        } catch (final Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    // Delete --------------------------------------------------------------------------
    /*
    @RequestMapping(value = "/author/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int submissionId) {
        ModelAndView result;
        Submission submission;

        try {
            final Author author = (Author) this.actorService.getActorLogged();
            submission = this.submissionService.findOne(submissionId);
            Assert.isTrue(submission.getAuthor().equals(author));

            Conference conference = submission.getConference();
            Date now = new Date();
            Assert.isTrue(now.before(conference.getSubmissionDeadline()));

            this.submissionService.delete(submission);
            result = new ModelAndView("redirect:list.do");
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
            return result;
        }

        return result;
    }

    */

    // ModelAndView Methods -------------------------------------------------------------
    protected ModelAndView createEditModelAndView(final Submission submission, final Collection<Paper> papers) {
        ModelAndView result;

        result = this.createEditModelAndView(submission, papers, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Submission submission, Collection<Paper> papers,
                                                  final String message, final Integer errorNumber) {
        ModelAndView result;

        if(submission.getId() == 0)
            result = new ModelAndView("submission/author/create");
        else
            result = new ModelAndView("submission/author/edit");

        result.addObject("submission", submission);
        result.addObject("papers", papers);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);

        return result;
    }

    protected ModelAndView createEditModelAndView2(final int submissionId, Collection<Reviewer> reviewers) {
        ModelAndView result;

        result = this.createEditModelAndView2(submissionId, reviewers, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView2(final int submissionId, Collection<Reviewer> reviewers,
                                                  final String message, final Integer errorNumber) {
        ModelAndView result;

        result = new ModelAndView("submission/administrator/assign");

        AssignForm assignForm = new AssignForm(submissionId, reviewers);

        result.addObject("assignForm", assignForm);
        result.addObject("message", message);
        result.addObject("errorNumber", errorNumber);

        return result;
    }
}
