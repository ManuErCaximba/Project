package datatype;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

    private String holderName;
    private String brandName;
    private String number;
    private Integer expirationMonth;
    private Integer expirationYear;
    private Integer CVV;

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @CreditCardNumber
    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotNull
    @Range(min = 1, max = 12)
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @NotNull
    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @NotNull
    @Range(min = 0, max = 999)
    public Integer getCVV() {
        return CVV;
    }

    public void setCVV(Integer CVV) {
        this.CVV = CVV;
    }
}
