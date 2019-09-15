package domain;

import datatype.CreditCard;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

    private String banner;
    private String targetURL;
    private CreditCard creditCard;

    //Relationships
    private Sponsor sponsor;

    @NotBlank
    @URL
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @NotBlank
    @URL
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    //Relationships

    @ManyToOne(optional = false)
    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }
}
