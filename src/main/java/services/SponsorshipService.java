package services;

import datatype.CreditCard;
import domain.Actor;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SponsorshipRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class SponsorshipService {

    //Managed Repositories
    @Autowired
    private SponsorshipRepository sponsorshipRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private SponsorService sponsorService;

    //Validator
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Sponsorship create(){
        Sponsorship sponsorship = new Sponsorship();
        sponsorship.setCreditCard(new CreditCard());

        return sponsorship;
    }

    public Sponsorship findOne(int id){
        return this.sponsorshipRepository.findOne(id);
    }

    public Collection<Sponsorship> findAll(){
        return this.sponsorshipRepository.findAll();
    }

    public Sponsorship save(Sponsorship sponsorship){
        Sponsorship result;
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("SPONSOR"));
        Assert.notNull(sponsorship);

        result = this.sponsorshipRepository.save(sponsorship);

        return result;
    }

    public void delete(Sponsorship sponsorship){
        final Actor actor = this.actorService.getActorLogged();

        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("SPONSOR"));
        Assert.notNull(sponsorship);

        this.sponsorshipRepository.delete(sponsorship);
    }

    //Reconstructs
    public Sponsorship reconstruct(SponsorshipForm sponsorshipForm, BindingResult binding){
        Sponsorship result = new Sponsorship();
        CreditCard creditCard = new CreditCard();
        Sponsor sponsor = this.sponsorService.findOne(this.actorService.getActorLogged().getId());

        if(sponsorshipForm.getId() == 0){
            result.setId(sponsorshipForm.getId());
            result.setVersion(sponsorshipForm.getVersion());
            result.setSponsor(sponsor);
        } else {
            result = this.findOne(sponsorshipForm.getId());
            Assert.isTrue(result.getSponsor().equals(sponsor));
        }

        result.setBanner(sponsorshipForm.getBanner());
        result.setTargetURL(sponsorshipForm.getTargetURL());

        creditCard.setHolderName(sponsorshipForm.getHolderName());
        creditCard.setBrandName(sponsorshipForm.getBrandName());
        creditCard.setNumber(sponsorshipForm.getNumber());
        creditCard.setExpirationMonth(sponsorshipForm.getExpirationMonth());
        creditCard.setExpirationYear(sponsorshipForm.getExpirationYear());
        creditCard.setCVV(sponsorshipForm.getCVV());

        result.setCreditCard(creditCard);

        this.validator.validate(result, binding);

        return result;
    }

    //Other Methods
    public Collection<Sponsorship> findAllBySponsor(int sponsorId){
        return this.sponsorshipRepository.findAllBySponsor(sponsorId);
    }
}
