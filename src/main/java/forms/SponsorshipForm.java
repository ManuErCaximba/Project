package forms;

import domain.Sponsorship;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public class SponsorshipForm {

    private int id;
    private int version;

    private String banner;
    private String targetURL;

    private String holderName;
    private String brandName;
    private String number;
    private int expirationMonth;
    private int expirationYear;
    private int CVV;

    public SponsorshipForm(Sponsorship sponsorship){
        this.id = sponsorship.getId();
        this.version = sponsorship.getVersion();

        this.banner = sponsorship.getBanner();
        this.targetURL = sponsorship.getTargetURL();

        this.holderName = sponsorship.getCreditCard().getHolderName();
        this.brandName = sponsorship.getCreditCard().getBrandName();
        this.number = sponsorship.getCreditCard().getNumber();
        this.expirationMonth = sponsorship.getCreditCard().getExpirationMonth();
        this.expirationYear = sponsorship.getCreditCard().getExpirationYear();
        this.CVV = sponsorship.getCreditCard().getCVV();
    }

    public SponsorshipForm(){
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
