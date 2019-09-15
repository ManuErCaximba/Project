package services;

import domain.Actor;
import domain.Category;
import domain.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.CategoryRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private Validator validator;

    public Category create(){
        UserAccount userAccount;
        userAccount = LoginService.getPrincipal();

        Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

        Category result;
        result = new Category();

        return result;
    }


    public Collection<Category> findAll() {
        Collection<Category> res;
        res = this.categoryRepository.findAll();
        Assert.notNull(res);
        return res;
    }

    public Category findOne(int categoryId){
        Category res;
        res = this.categoryRepository.findOne(categoryId);
        Assert.notNull(res);
        return res;
    }

    public Category save(final Category c) {
        Actor a;
        a = this.actorService.getActorLogged();

        Assert.isTrue(a.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.isTrue(c.getNameEn() != "CONFERENCE");
        Assert.isTrue(c.getNameEs() != "CONFERENCIA");
        Assert.notNull(c);
        Category result;
        if (c.getId() == 0) {
            result = this.categoryRepository.save(c);
            c.getParents().getChilds().add(result);
        } else {
            final Category oldParent = this.categoryRepository.findOne(c.getId()).getParents();
            if (oldParent.equals(c.getParents())) {
                oldParent.getChilds().remove(c);
                result = this.categoryRepository.save(c);
                c.getParents().getChilds().add(result);
            } else
                result = this.categoryRepository.save(c);

        }
        return result;
    }

    public void delete(final Category c) {
        Actor a;
        a = this.actorService.getActorLogged();

        Assert.isTrue(a.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
        Assert.notNull(c);

        for (final Category child : c.getChilds()) {
            final Category parent = c.getParents();
            child.setParents(parent);
        }
        for (final Conference f : this.conferenceService.findAll())
            if (f.getCategory().equals(c))
                f.setCategory(c.getParents());

        this.categoryRepository.delete(c);
    }

    public Category getDefaultCategory(){
        Category res;
        res = this.categoryRepository.getDefaultCategory();
        Assert.notNull(res);
        return res;
    }

    public Category reconstruct(Category category, BindingResult binding) {
        Category result;

        Boolean existsES = false;
        Boolean existsEN = false;

        if (category.getId() == 0) {
            result = this.create();
        } else {
            result = this.categoryRepository.findOne(category.getId());

        }

        if (result.getId() == 0) {
            existsES = existsES(category);
            existsEN = existsEN(category);
        } else {
            if (!result.equalsES(category))
                existsES = existsES(category);
            if (!result.equalsEN(category))
                existsEN = existsEN(category);
        }

        result.setNameEn(category.getNameEn());
        result.setNameEs(category.getNameEs());
        result.setParents(category.getParents());
        result.setChilds(category.getChilds());

        validator.validate(result, binding);

        if (existsES)
            binding.rejectValue("nameEs", "category.es.duplicated");

        if (existsEN)
            binding.rejectValue("nameEn", "category.en.duplicated");

        if (binding.hasErrors()) {
            throw new ValidationException();
        }
        return result;
    }

    public Boolean existsES(final Category a) {
        Boolean exist = false;

        final Collection<Category> categories = this.findAll();
        for (final Category b : categories)
            if (a.equalsES(b)) {
                exist = true;
                break;
            }
        return exist;
    }

    public Boolean existsEN(final Category a) {
        Boolean exist = false;

        final Collection<Category> categories = this.findAll();
        for (final Category b : categories)
            if (a.equalsEN(b)) {
                exist = true;
                break;
            }
        return exist;
    }

    public Collection<String> getNamesEs() {
        return this.categoryRepository.getNamesEs();
    }

    public Collection<String> getNamesEn() {
        return this.categoryRepository.getNamesEn();
    }
}
