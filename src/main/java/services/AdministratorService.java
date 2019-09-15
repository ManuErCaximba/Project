package services;

import domain.*;
import forms.AdministratorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class AdministratorService {

    //Managed Repositories
    @Autowired
    private AdministratorRepository administratorRepository;

    //Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Validator validator;
    @Autowired
    private ConfigurationService configurationService;

    //CRUD Methods
    public Administrator create() {

        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        final Administrator administrator = new Administrator();
        final Authority auth;
        final UserAccount userAccount;
        final Collection<Authority> authorities;
        userAccount = new UserAccount();
        auth = new Authority();
        authorities = new ArrayList<>();

        auth.setAuthority(Authority.ADMIN);
        authorities.add(auth);
        userAccount.setAuthorities(authorities);
        administrator.setUserAccount(userAccount);

        return administrator;
    }

    public Collection<Administrator> findAll() {
        Collection<Administrator> result;

        result = this.administratorRepository.findAll();

        return result;
    }

    public Administrator findOne(final int administratorId) {
        Assert.isTrue(administratorId != 0);

        Administrator result;

        result = this.administratorRepository.findOne(administratorId);

        return result;
    }

    public Administrator save(final Administrator administrator) {
        UserAccount userAccount;
        userAccount = LoginService.getPrincipal();
        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(administrator);
        Administrator result;
        final char[] c = administrator.getPhoneNumber().toCharArray();
        /*
        if ((!administrator.getPhoneNumber().equals(null) && !administrator.getPhoneNumber().equals("")))
            if (c[0] != '+') {
                final String i = this.configurationService.findAll().get(0).getCountryCode();
                administrator.setPhoneNumber("+" + i + " " + administrator.getPhoneNumber());
            }
        */
        if (administrator.getId() == 0) {
            final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            final String res = encoder.encodePassword(administrator.getUserAccount().getPassword(), null);
            administrator.getUserAccount().setPassword(res);
        }
        result = this.administratorRepository.save(administrator);
        return result;
    }

    public void delete(final Administrator administrator) {

        final Actor actor = this.actorService.getActorLogged();
        Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(administrator);
        Assert.isTrue(actor.getId() != 0);

        this.administratorRepository.delete(administrator);
    }


    public Administrator reconstruct(final Administrator admin, final BindingResult binding) {

        Administrator result;
        if (admin.getId() == 0) {
            this.validator.validate(admin, binding);
            result = admin;
        } else {
            result = this.administratorRepository.findOne(admin.getId());

            result.setName(admin.getName());
            result.setPhoto(admin.getPhoto());
            result.setPhoneNumber(admin.getPhoneNumber());
            result.setEmail(admin.getEmail());
            result.setAddress(admin.getAddress());
            result.setSurname(admin.getSurname());
            result.setMiddleName(admin.getMiddleName());

            this.validator.validate(admin, binding);
        }
        return result;
    }



    //Objeto formulario
    public Administrator reconstruct(final AdministratorForm admin, final BindingResult binding) {

        final Administrator result = this.create();

        result.setId(admin.getId());
        result.setVersion(admin.getVersion());

        result.setName(admin.getName());
        result.setMiddleName(admin.getMiddleName());
        result.setSurname(admin.getSurname());
        result.setEmail(admin.getEmail());
        result.setPhoto(admin.getPhoto());
        result.setPhoneNumber(admin.getPhoneNumber());
        result.setAddress(admin.getAddress());

        result.getUserAccount().setPassword(admin.getPassword());
        result.getUserAccount().setUsername(admin.getUsername());

        this.validator.validate(result, binding);
        return result;
    }

    //Queries
    /*Q1*/
    public Double getAvgSubmissionPerConference(){
        return this.administratorRepository.getAvgSubmissionPerConference();
    }

    public Double getMinSubmissionPerConference(){
        return this.administratorRepository.getMinSubmissionPerConference();
    }

    public Double getMaxSubmissionPerConference(){
        return this.administratorRepository.getMaxSubmissionPerConference();
    }

    public Double getStddevSubmissionPerConference(){
        return this.administratorRepository.getStddevSubmissionPerConference();
    }

    /*Q2*/
    public Double getAvgNumberRegistrationConference(){
        return this.administratorRepository.getAvgNumberRegistrationConference();
    }

    public Double getMinNumberRegistrationConference(){
        return this.administratorRepository.getMinNumberRegistrationConference();
    }

    public Double getMaxNumberRegistrationConference(){
        return this.administratorRepository.getMaxNumberRegistrationConference();
    }

    public Double getStddevNumberRegistrationConference(){
        return this.administratorRepository.getStddevNumberRegistrationConference();
    }

    /*Q3*/
    public Double getAvgConferenceFee(){
        return this.administratorRepository.getAvgConferenceFee();
    }

    public Double getMinConferenceFee(){
        return this.administratorRepository.getMinConferenceFee();
    }

    public Double getMaxConferenceFee(){
        return this.administratorRepository.getMaxConferenceFee();
    }

    public Double getStddevConferenceFee(){
        return this.administratorRepository.getStddevConferenceFee();
    }

    /*Q4*/
    public Double getAvgNumberOfDaysConference(){
        List<Date> startDateList = (List<Date>) this.administratorRepository.getStartDates();
        List<Date> endDateList = (List<Date>) this.administratorRepository.getEndDates();
        List<Double> times = new ArrayList<>();

        for(int i=0; i < startDateList.size(); i++){
            long j = endDateList.get(i).getTime() - startDateList.get(i).getTime();
            times.add((double) (j/86400000));
        }

        return this.findMean(times);
    }

    public Double getMinNumberOfDaysConference(){
        List<Date> startDateList = (List<Date>) this.administratorRepository.getStartDates();
        List<Date> endDateList = (List<Date>) this.administratorRepository.getEndDates();
        Double res = null;

        for(int i=0; i < startDateList.size(); i++){
            long j = endDateList.get(i).getTime() - startDateList.get(i).getTime();
            Double x = (double) j/86400000;
            if(i==0){
                res = x;
            } else {
                if (x < res)
                    res = x;
            }
        }

        return res;
    }

    public Double getMaxNumberOfDaysConference(){
        List<Date> startDateList = (List<Date>) this.administratorRepository.getStartDates();
        List<Date> endDateList = (List<Date>) this.administratorRepository.getEndDates();
        Double res = null;

        for(int i=0; i < startDateList.size(); i++){
            long j = endDateList.get(i).getTime() - startDateList.get(i).getTime();
            Double x = (double) j/86400000;
            if(i==0){
                res = x;
            } else {
                if (x > res)
                    res = x;
            }
        }

        return res;
    }

    public Double getStddevNumberOfDaysConference(){
        List<Date> endDateList = (List<Date>) this.administratorRepository.getEndDates();
        List<Double> times = new ArrayList<>();
        List<Date> startDateList = (List<Date>) this.administratorRepository.getStartDates();

        for(int i=0; i < startDateList.size(); i++){
            long j = endDateList.get(i).getTime() - startDateList.get(i).getTime();
            times.add((double) (j/86400000));
        }

        return this.findStandardDesviation(times);
    }

    /*Q5*/
    public Double getAvgConferencesPerCategory(){
        return this.administratorRepository.getAvgConferencesPerCategory();
    }

    public Double getMinConferencesPerCategory(){
        return this.administratorRepository.getMinConferencesPerCategory();
    }

    public Double getMaxConferencesPerCategory(){
        return this.administratorRepository.getMaxConferencesPerCategory();
    }

    public Double getStddevConferencesPerCategory(){
        return this.administratorRepository.getStddevConferencesPerCategory();
    }

    /*Q6*/
    public Double getAvgNumberCommentsConference(){
        return this.administratorRepository.getAvgNumberCommentsConference();
    }

    public Double getMinNumberCommentsConference(){
        return this.administratorRepository.getMinNumberCommentsConference();
    }

    public Double getMaxNumberCommentsConference(){
        return this.administratorRepository.getMaxNumberCommentsConference();
    }

    public Double getStddevNumberCommentsConference(){
        return this.administratorRepository.getStddevNumberCommentsConference();
    }

    /*Q7*/
    public Double getAvgNumberCommentsActivity(){
        return this.administratorRepository.getAvgNumberCommentsActivity();
    }

    public Double getMinNumberCommentsActivity(){
        return this.administratorRepository.getMinNumberCommentsActivity();
    }

    public Double getMaxNumberCommentsActivity(){
        return this.administratorRepository.getMaxNumberCommentsActivity();
    }

    public Double getStddevNumberCommentsActivity(){
        return this.administratorRepository.getStddevNumberCommentsActivity();
    }

    //Other Methods
    private Double findMean(List<Double> list){
        int total = 0;

        for ( int i= 0;i < list.size(); i++)
        {
            Double currentNum = list.get(i);
            total+= currentNum;
        }
        return (double) total / (double) list.size();
    }

    private Double findStandardDesviation(List<Double> list) {
        double mean = this.findMean(list);
        double temp = 0;

        for (int i = 0; i < list.size(); i++)
        {
            Double val = list.get(i);

            double squrDiffToMean = Math.pow(val - mean, 2);

            temp += squrDiffToMean;
        }

        double meanOfDiffs = temp / (double) (list.size());

        return Math.sqrt(meanOfDiffs);
    }

    public void getScoreAllAuthor(){

        //Calculamos buzz words
        Collection<Conference> conferences = this.conferenceService.getConferencesSince12Months();
        Collection<String> voidWords = this.configurationService.findAll().iterator().next().getVoidWordsEn();
        voidWords.addAll(this.configurationService.findAll().iterator().next().getVoidWordsEs());
        Collection<String> summaryWords = new ArrayList<>();
        Map<String, Integer> wordCount = new HashMap<>();
        Collection<String> buzzWords = new ArrayList<>();
        int frequency = 0;

        for(Conference c : conferences){
            String summary = c.getSummary();

            Collection<String> words = this.splitThisText(summary);

            for (String word : words){
                if(voidWords.contains(word))
                    words.remove(word);
            }

            summaryWords.addAll(words);
        }

        for(String word: summaryWords){
            if (wordCount.containsKey(word)){
                wordCount.put(word,wordCount.get(word) + 1);
            } else {
                    wordCount.put(word, 1);
            }

            if (wordCount.get(word) > frequency)
                frequency = wordCount.get(word);
        }

        double f = frequency - 0.2 * frequency;

        for(String word : wordCount.keySet()){
            if (wordCount.get(word) >= f)
                buzzWords.add(word);
        }

        //Ahora sacamos puntuacion de cada author
        Collection<Author> authors = this.authorService.findAll();

        for(Author a: authors){
            int score = 0;
            Collection<Paper> cameraReadyPapers = this.authorService.getCameraReadyPapersFromAuthor(a);
            for(Paper p : cameraReadyPapers){
                Collection<String> summary = this.splitThisText(p.getSummary());
                for(String word : summary){
                    if(buzzWords.contains(word) && !voidWords.contains(word))
                        score++;
                }
            }

            a.setScore(score);
            this.authorService.save(a);
        }
    }

    public Collection<String> splitThisText(String text){
        Collection<String> res = new ArrayList<>();

        text = text.toLowerCase();

        text = text.replace('.',' ');
        text = text.replace(',',' ');
        text = text.replace('\'',' ');
        text = text.replace(':',' ');
        text = text.replace(';',' ');
        text = text.replace('?',' ');
        text = text.replace('¿',' ');
        text = text.replace('!',' ');
        text = text.replace('¡',' ');
        text = text.replace('(',' ');
        text = text.replace(')',' ');
        text = text.replace('{',' ');
        text = text.replace('}',' ');
        text = text.replace('[',' ');
        text = text.replace(']',' ');
        text = text.replace('-',' ');
        text = text.replace('_',' ');
        text = text.replace('`',' ');
        text = text.replace('´',' ');
        text = text.replace('¨',' ');
        text = text.replace('^',' ');
        text = text.replace('*',' ');
        text = text.replace('@',' ');
        text = text.replace('"',' ');
        text = text.replace('|',' ');

        Collection<String> resWithoutTrim = Arrays.asList(text.split(" "));

        for(String s: resWithoutTrim){
            if(!s.equals("")) {
                String trim = s.trim();
                res.add(trim);
            }
        }


        return res;
    }

}
