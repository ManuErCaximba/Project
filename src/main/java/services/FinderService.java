package services;

import domain.Actor;
import domain.Conference;
import domain.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.FinderRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class FinderService {

    //Managed Repositories
    @Autowired
    private FinderRepository finderRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Finder create(){
        Finder finder = new Finder();

        return finder;
    }

    public Finder findOne(int id){
        return this.finderRepository.findOne(id);
    }

    public Collection<Finder> findAll(){
        return this.finderRepository.findAll();
    }

    public Finder save(Finder finder){
        Collection<Conference> conferences;
        final Actor actor = this.actorService.getActorLogged();

        Assert.notNull(actor);
        Assert.notNull(finder);

        conferences = this.search(finder);

        if (conferences.isEmpty() && (finder.getKeyword() == null || finder.getKeyword().equals(""))
                && (finder.getCategoryName() == null || finder.getCategoryName().equals(""))
                && (finder.getEndDate() == null || finder.getStartDate() == null)
                && (finder.getMaximumFee() == null || finder.getMaximumFee().equals("")))
            conferences = this.finderRepository.findAllNotFinal();

        finder.setConferences(conferences);

        if(finder.getId() == 0) {
            finder.setActor(actor);
        }

        finder = this.finderRepository.save(finder);

        return finder;
    }

    // Other Methods
    public Finder reconstruct(final Finder finder, final BindingResult binding) {
        Finder result;

        result = this.finderRepository.findOne(finder.getId());

        finder.setVersion(result.getVersion());
        finder.setActor(result.getActor());
        finder.setConferences(result.getConferences());
        finder.setId(result.getId());

        result = finder;
        this.validator.validate(finder, binding);

        return result;
    }

    public Collection<Conference> search(Finder finder) {
        Collection<Conference> result = Collections.emptyList();
        List<Conference> pro1 = null;
        List<Conference> pro2 = null;
        List<Conference> pro3 = null;
        List<Conference> pro4 = null;
        List<Conference> proAux1;
        List<Conference> proAux2;
        List<Conference> proAux3;
        List<Conference> proAux4;

        if (!(finder.getKeyword() == null || finder.getKeyword().equals(""))) {
            proAux1 = (List<Conference>) this.finderRepository.getConferenceByTitle(finder.getKeyword());
            proAux2 = (List<Conference>) this.finderRepository.getConferenceByAcronym(finder.getKeyword());
            proAux3 = (List<Conference>) this.finderRepository.getConferenceByVenue(finder.getKeyword());
            proAux4 = (List<Conference>) this.finderRepository.getConferenceBySummary(finder.getKeyword());

            Set<Conference> set = new LinkedHashSet<>(proAux1);
            set.addAll(proAux2);
            set.addAll(proAux3);
            set.addAll(proAux4);
            pro1 = new ArrayList<>(set);
        }
        if (!(finder.getCategoryName() == null || finder.getCategoryName().equals("")))
            pro2 = (List<Conference>) this.finderRepository.getConferenceByCategory(finder.getCategoryName());
        if (!(finder.getStartDate() == null || finder.getEndDate() == null))
            pro3 = (List<Conference>) this.finderRepository.getConferenceByDate(finder.getStartDate(), finder.getEndDate());
        if (!(finder.getMaximumFee() == null || finder.getMaximumFee().equals("")))
            pro4 = (List<Conference>) this.finderRepository.getConferenceByFee(finder.getMaximumFee());

        if (!(pro1 == null && pro2 == null && pro3 == null && pro4 == null)) {
            if (pro1 == null)
                pro1 = (List<Conference>) this.finderRepository.findAllNotFinal();
            if (pro2 == null)
                pro2 = (List<Conference>) this.finderRepository.findAllNotFinal();
            if (pro3 == null)
                pro3 = (List<Conference>) this.finderRepository.findAllNotFinal();
            if (pro4 == null)
                pro4 = (List<Conference>) this.finderRepository.findAllNotFinal();
            pro1.retainAll(pro2);
            pro1.retainAll(pro3);
            pro1.retainAll(pro4);

            result = pro1;
        }

        return result;
    }

    public Finder searchByActor(int actorId){
        return this.finderRepository.searchByActor(actorId);
    }
}
