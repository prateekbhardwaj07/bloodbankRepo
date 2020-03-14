package example.com.guidemo3.modal;

/**
 * Created by Prateek on 5/10/2017.
 */

public class User {

    String location;
    public User(){}
    public User(String mobileNumber, double langitude, double longitude, String location) {
        this.mobileNumber = mobileNumber;
        Langitude = langitude;
        Longitude = longitude;
        this.location=location;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getLangitude() {
        return Langitude;
    }

    public void setLangitude(double langitude) {
        Langitude = langitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    private String mobileNumber;
    private double Langitude;
    private double Longitude;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
