package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.CommentRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CommentService {

    //Managed Repositories
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private PresentationService presentationService;


    public Collection<Comment> findAll(){
        Collection<Comment> res;
        res = this.commentRepository.findAll();
        return res;
    }

    public Comment findOne(final int commentId) {
        Assert.isTrue(commentId != 0);
        final Comment res = this.commentRepository.findOne(commentId);
        Assert.notNull(res);
        return res;
    }

    public Comment create(){
        Comment res;
        res = new Comment();
        return res;
    }

    public Comment saveConference(Comment comment, int conferenceId){
        Actor actor = this.actorService.getActorLogged();
        comment.setActor(actor);

        Conference conference = this.conferenceService.findOne(conferenceId);
        Assert.notNull(conference);

        Comment result = this.commentRepository.save(comment);
        conference.getComments().add(result);

        return result;
    }

    public Comment saveTutorial(Comment comment, int tutorialId){
        Actor actor = this.actorService.getActorLogged();
        comment.setActor(actor);

        Tutorial tutorial = this.tutorialService.findOne(tutorialId);
        Assert.notNull(tutorial);

        Comment result = this.commentRepository.save(comment);
        tutorial.getComments().add(result);

        return result;
    }

    public Comment savePresentation(Comment comment, int presentationId){
        Actor actor = this.actorService.getActorLogged();
        comment.setActor(actor);

        Presentation presentation = this.presentationService.findOne(presentationId);
        Assert.notNull(presentation);

        Comment result = this.commentRepository.save(comment);
        presentation.getComments().add(result);

        return result;
    }


    public Comment reconstruct(Comment comment, BindingResult binding){
        Comment result;
        if (comment.getId() == 0){
            result = this.create();
            Date now = new Date();
            result.setMoment(now);
        } else {
            result = this.commentRepository.findOne(comment.getId());
        }

        result.setTitle(comment.getTitle());
        result.setText(comment.getText());
        result.setActor(comment.getActor());

        validator.validate(result, binding);

        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }
}
