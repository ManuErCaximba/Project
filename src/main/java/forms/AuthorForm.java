package forms;

import domain.Author;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class AuthorForm {

    private int id;
    private int version;

    private String name;
    private String middleName;
    private String surname;
    private String photo;
    private String email;
    private String phoneNumber;
    private String address;

    private String username;
    private String password;
    private String confirmPass;

    public AuthorForm(Author author){
        this.id = author.getId();
        this.version = author.getVersion();
        this.name = author.getName();
        this.middleName = author.getMiddleName();
        this.surname = author.getSurname();
        this.photo = author.getPhoto();
        this.email = author.getEmail();
        this.phoneNumber = author.getPhoneNumber();
        this.address = author.getAddress();
    }

    public AuthorForm(){
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Size(min = 5, max = 32)
    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 5, max = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
