package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

    private String nameEn;
    private String nameEs;


    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    //Relationships
    private Collection<Category> childs;
    private Category parents;


    @Valid
    @OneToMany(mappedBy = "parents")
    public Collection<Category> getChilds() {
        return this.childs;
    }

    @Valid
    @ManyToOne(optional = true)
    public Category getParents() {
        return this.parents;
    }

    public void setChilds(final Collection<Category> childs) {
        this.childs = childs;
    }

    public void setParents(final Category parents) {
        this.parents = parents;
    }

    public boolean equalsES(final Category obj) {
        boolean res = false;
        if (this.getNameEs().equals(obj.getNameEs()))
            res = true;
        return res;
    }

    public boolean equalsEN(final Category obj) {
        boolean res = false;
        if (this.getNameEn().equals(obj.getNameEn()))
            res = true;
        return res;
    }
}
