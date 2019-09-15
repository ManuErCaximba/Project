package forms;

import domain.Registration;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class RegistrationForm {

    private int id;
    private int version;

    private String holderName;
    private String brandName;
    private String number;
    private int expirationMonth;
    private int expirationYear;
    private int CVV;

    public RegistrationForm(Registration registration){
        this.id = registration.getId();
        this.version = registration.getVersion();

        this.holderName = registration.getCreditCard().getHolderName();
        this.brandName = registration.getCreditCard().getBrandName();
        this.number = registration.getCreditCard().getNumber();
        this.expirationMonth = registration.getCreditCard().getExpirationMonth();
        this.expirationYear = registration.getCreditCard().getExpirationYear();
        this.CVV = registration.getCreditCard().getCVV();
    }

    public RegistrationForm(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Range(min = 1, max = 12)
    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    @Range(min = 0, max = 999)
    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }
}