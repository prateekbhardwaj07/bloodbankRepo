package example.com.guidemo3.modal;

/**
 * Created by Prateek on 8/21/2017.
 */

public class Users {


    private String MobileNumber;
    private String Name;
    private String Age;
    private String BloodGroup;
    private String Location;
    private String EmailId;
    public Users(){};
    public Users(String mobileNumber, String name, String age, String bloodGroup,String EmailId) {
        MobileNumber = mobileNumber;
        Name = name;
        Age = age;
        BloodGroup = bloodGroup;
        this.EmailId = EmailId;
    }
    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }


}
