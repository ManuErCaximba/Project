
package services;

import domain.Author;
import domain.Conference;
import domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.RegistrationRepository;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class RegistrationService {

    //Managed Repositories
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Validator validator;



    public Collection<Registration> findAll(){
        Collection<Registration> res;
        res = this.registrationRepository.findAll();
        return res;
    }

    public Registration findOne(final int registrationId) {
        Assert.isTrue(registrationId != 0);
        final Registration res = this.registrationRepository.findOne(registrationId);
        Assert.notNull(res);
        return res;
    }

    public Registration create(){
        UserAccount userAccount;
        userAccount = this.actorService.getActorLogged().getUserAccount();
        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));

        Registration result = new Registration();

        return result;
    }


    public Registration save(Registration registration, int conferenceId){
        UserAccount userAccount;
        userAccount = this.actorService.getActorLogged().getUserAccount();
        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));

        Assert.notNull(registration);
        Conference conference = this.conferenceService.findOne(conferenceId);
        Assert.notNull(conference);
        Assert.isTrue(conference.getIsFinal());
        Assert.isTrue(new Date().before(conference.getStartDate()));
        registration.setMoment(new Date());

        Author author = this.authorService.findOne(this.actorService.getActorLogged().getId());
        registration.setAuthor(author);

        Collection<Conference> conferences = this.conferenceService.getConferencesByAuthor(author.getId());
        Assert.isTrue(conferences.contains(conference) == false);

        Registration result = this.registrationRepository.save(registration);

        Collection<Registration> registrations = conference.getRegistrations();
        registrations.add(result);
        conference.setRegistrations(registrations);
        return result;

    }

    public Collection<Registration> getRegistrationsPerAuthor(int authorId){
        Collection<Registration> res;
        res = this.registrationRepository.getRegistrationsPerAuthor(authorId);
        Assert.notNull(res);
        return res;
    }

    public Registration reconstruct(Registration registration, BindingResult binding) {
        Registration result;
        result = this.create();
        Calendar now = Calendar.getInstance();
        result.setMoment(new Date());
        result.setCreditCard(registration.getCreditCard());

        validator.validate(result, binding);

        if (registration.getCreditCard().getExpirationYear() != null){
            if (registration.getCreditCard().getExpirationYear() < now.get(Calendar.YEAR)) {
                binding.rejectValue("creditCard.expirationYear", "error.expirationYear");
            }
        }

        if(registration.getCreditCard().getExpirationYear() != null){
            if (registration.getCreditCard().getExpirationYear() == now.get(Calendar.YEAR) &&
                    registration.getCreditCard().getExpirationMonth() < now.get(Calendar.MONTH)) {
                binding.rejectValue("creditCard.expirationMonth", "error.expirationMonth");
            }
        }
        if (binding.hasErrors()){
            throw new ValidationException();
        }
        return result;
    }

}