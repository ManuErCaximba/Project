package services;

import domain.Actor;
import domain.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SectionRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class SectionService {

    //Managed Repositories
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private Validator validator;

    public Section create(){
        UserAccount userAccount;
        userAccount = LoginService.getPrincipal();

        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

        Section result;
        result = new Section();

        return result;
    }

    public Section save(Section section){
        Actor a;
        a = this.actorService.getActorLogged();

        Assert.isTrue(a.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(section);

        Section result;
        result = this.sectionRepository.save(section);

        return result;
    }

    public void delete(Section section){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(section);
        Assert.isTrue(section.getId() != 0);
        this.sectionRepository.delete(section);
    }

    public Collection<Section> findAll(){
        Collection<Section> res;
        res = this.sectionRepository.findAll();
        return res;
    }

    public Section findOne(final int sectionId) {
        Assert.isTrue(sectionId != 0);
        final Section res = this.sectionRepository.findOne(sectionId);
        Assert.notNull(res);
        return res;
    }

    public Collection<Section> getSectionsByTutorial(int tutorialId){
        Collection<Section> res;
        res = this.sectionRepository.getSectionsByTutorial(tutorialId);
        Assert.notNull(res);
        return res;
    }

    public Section reconstruct(Section section, BindingResult binding){
        Section result;
        if (section.getId() == 0){
            result = this.create();
        } else {
            result = this.sectionRepository.findOne(section.getId());
        }

        result.setTitle(section.getTitle());
        result.setSummary(section.getSummary());
        result.setPictures(section.getPictures());
        result.setTutorial(section.getTutorial());
        validator.validate(result, binding);

        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }
}
