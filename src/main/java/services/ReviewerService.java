package services;

import domain.Actor;
import domain.Administrator;
import domain.Reviewer;
import domain.Submission;
import forms.ReviewerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ReviewerService {

    //Managed Repositories
    @Autowired
    private ReviewerRepository reviewerRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Collection<Reviewer> findAll() {
        Collection<Reviewer> result;

        result = this.reviewerRepository.findAll();

        return result;
    }

    public Reviewer findOne(final int reviewerId) {
        Assert.isTrue(reviewerId != 0);

        Reviewer result;

        result = this.reviewerRepository.findOne(reviewerId);

        return result;
    }

    public Reviewer create(){

        final Reviewer reviewer = new Reviewer();
        final Collection<String> keywords;
        final Collection<Submission> submissions;
        final Authority auth;
        final UserAccount userAccount;
        final Collection<Authority> authorities;
        userAccount = new UserAccount();
        auth = new Authority();
        authorities = new ArrayList<>();
        keywords = new ArrayList<>();
        submissions = new ArrayList<>();

        auth.setAuthority(Authority.REVIEWER);
        authorities.add(auth);
        userAccount.setAuthorities(authorities);
        reviewer.setUserAccount(userAccount);
        reviewer.setKeywords(keywords);
        reviewer.setSubmissions(submissions);

        return reviewer;
    }

    public Reviewer save(Reviewer reviewer){
        Assert.notNull(reviewer);
        Reviewer result;
        final char[] c = reviewer.getPhoneNumber().toCharArray();
        /*
        if ((!administrator.getPhoneNumber().equals(null) && !administrator.getPhoneNumber().equals("")))
            if (c[0] != '+') {
                final String i = this.configurationService.findAll().get(0).getCountryCode();
                administrator.setPhoneNumber("+" + i + " " + administrator.getPhoneNumber());
            }
        */
        if (reviewer.getId() == 0) {
            final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            final String res = encoder.encodePassword(reviewer.getUserAccount().getPassword(), null);
            reviewer.getUserAccount().setPassword(res);
        }
        result = this.reviewerRepository.save(reviewer);
        return result;
    }

    public void delete(Reviewer reviewer){
        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("REVIEWER"));
        Assert.notNull(reviewer);
        Assert.isTrue(actor.getId() != 0);

        this.reviewerRepository.delete(reviewer);
    }

    public Reviewer reconstruct(final Reviewer s, final BindingResult binding) {

        Reviewer result;
        if (s.getId() == 0) {
            this.validator.validate(s, binding);
            result = s;
        } else {
            result = this.reviewerRepository.findOne(s.getId());

            result.setName(s.getName());
            result.setPhoto(s.getPhoto());
            result.setPhoneNumber(s.getPhoneNumber());
            result.setEmail(s.getEmail());
            result.setAddress(s.getAddress());
            result.setSurname(s.getSurname());
            result.setKeywords(s.getKeywords());

            this.validator.validate(s, binding);
        }
        return result;
    }




    //Objeto formulario
    public Reviewer reconstruct(final ReviewerForm reviewerForm, final BindingResult binding) {

        final Reviewer result = this.create();

        result.setId(reviewerForm.getId());
        result.setVersion(reviewerForm.getVersion());

        result.getUserAccount().setPassword(reviewerForm.getPassword());
        result.getUserAccount().setUsername(reviewerForm.getUsername());

        result.setEmail(reviewerForm.getEmail());
        result.setPhoto(reviewerForm.getPhoto());
        result.setPhoneNumber(reviewerForm.getPhoneNumber());
        result.setAddress(reviewerForm.getAddress());
        result.setName(reviewerForm.getName());
        result.setMiddleName(reviewerForm.getMiddleName());
        result.setSurname(reviewerForm.getSurname());

        String keywordString = reviewerForm.getKeywordsString();

        String[] keywords = keywordString.split(",");
        List<String> keywordsTrimpeadas = new ArrayList<>();
        for(String k: keywords){
            k.trim();
            keywordsTrimpeadas.add(k);
        }

        result.setKeywords(keywordsTrimpeadas);

        this.validator.validate(result, binding);
        return result;
    }
}
