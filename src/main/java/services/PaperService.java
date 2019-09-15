package services;

import domain.Actor;
import domain.Author;
import domain.Paper;
import domain.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.PaperRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PaperService {

    //Managed Repositories
    @Autowired
    private PaperRepository paperRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Paper create(){
        Paper paper = new Paper();
        List<Author> authors = new ArrayList<>();
        paper.setAuthors(authors);
        paper.setIsInSubmission(false);

        return paper;
    }

    public Paper findOne(int id){
        return this.paperRepository.findOne(id);
    }

    public Collection<Paper> findAll(){
        return this.paperRepository.findAll();
    }

    public Paper save(Paper paper){
        Paper result;
        final Actor actor = this.actorService.getActorLogged();
        final Author author = this.authorService.findOne(actor.getId());

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
        Assert.notNull(paper);

        if(paper.getId() == 0) {
            Collection<Author> authors = paper.getAuthors();
            authors.add(author);
            paper.setAuthors(authors);
        }

        result = this.paperRepository.save(paper);

        return result;
    }

    public void delete(Paper paper){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
        Assert.isTrue(paper.getAuthors().contains(actor));
        Assert.notNull(paper);

        this.paperRepository.delete(paper);
    }

    //Other methods
    public Collection<Paper> findAllByAuthor(Author author){
        return this.paperRepository.findAllByAuthor(author);
    }

    public Paper reconstruct(Paper paper, BindingResult binding){
        Paper result;
        Collection<Author> authors = new ArrayList<>();
        Author author = (Author) this.actorService.getActorLogged();

        if(paper.getId()==0)
            result = this.create();
        else
            result = this.findOne(paper.getId());

        result.setId(paper.getId());
        result.setVersion(paper.getVersion());
        result.setDocumentURL(paper.getDocumentURL());
        result.setSummary(paper.getSummary());
        result.setTitle(paper.getTitle());

        if(paper.getAuthors() != null)
            authors = paper.getAuthors();

        authors.add(author);
        result.setAuthors(authors);

        result.setIsInSubmission(false);

        this.validator.validate(paper, binding);

        return result;
    }

    private Collection<Submission> getSubmissionPerPaper(Paper paper){
        return this.paperRepository.getSubmissionPerPaper(paper.getId());
    }

    public void update() {
        Collection<Paper> papers = this.paperRepository.findAll();
        for(Paper p: papers){
            if(!this.getSubmissionPerPaper(p).isEmpty()){
                p.setIsInSubmission(true);
                this.paperRepository.save(p);
            }
        }
    }
}
