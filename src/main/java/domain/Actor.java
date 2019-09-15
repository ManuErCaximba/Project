package domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import security.UserAccount;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

    private String name;
    private String middleName;
    private String surname;
    private String photo;
    private String email;
    private String phoneNumber;
    private String address;

    @NotBlank
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotBlank
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @URL
    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Email
    @NotBlank
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //Relationships

    private UserAccount userAccount;

    @Valid
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
