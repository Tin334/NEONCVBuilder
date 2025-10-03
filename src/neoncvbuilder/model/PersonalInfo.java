package neoncvbuilder.model;

import java.io.Serializable;

public class PersonalInfo implements Serializable {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String summary;

    public PersonalInfo() {}

    public PersonalInfo(String fullName, String email, String phone, String address, String summary) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.summary = summary;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}