package example.com.guidemo3.activites;

/**
 * Created by Prateek on 3/19/2018.
 */

public class PlacesData {

    private String title;
    private String address;
    private String status;

    public PlacesData(String title, String address, String status) {
        this.title = title;
        this.address = address;
        this.status = status;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
