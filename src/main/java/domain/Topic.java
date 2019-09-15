package domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity{
    private String nameEn;
    private String nameEs;

    @NotBlank
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @NotBlank
    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public boolean equalsES(final Topic obj) {
        boolean res = false;
        if (this.getNameEs().equals(obj.getNameEs()))
            res = true;
        return res;
    }

    public boolean equalsEN(final Topic obj) {
        boolean res = false;
        if (this.getNameEn().equals(obj.getNameEn()))
            res = true;
        return res;
    }
}
