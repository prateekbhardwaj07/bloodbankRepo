package example.com.guidemo3.modal;

/**
 * Created by Prateek on 4/8/2018.
 */

public class RecentTabModel {
    public String getRecentTabName() {
        return recentTabName;
    }

    public void setRecentTabName(String recentTabName) {
        this.recentTabName = recentTabName;
    }

    public String getRecentTabLocation() {
        return recentTabLocation;
    }

    public void setRecentTabLocation(String recentTabLocation) {
        this.recentTabLocation = recentTabLocation;
    }

    public String getRecentTabMobileNo() {
        return recentTabMobileNo;
    }

    public void setRecentTabMobileNo(String recentTabMobileNo) {
        this.recentTabMobileNo = recentTabMobileNo;
    }

    public RecentTabModel(String recentTabName, String recentTabLocation, String recentTabMobileNo) {
        this.recentTabName = recentTabName;
        this.recentTabLocation = recentTabLocation;
        this.recentTabMobileNo = recentTabMobileNo;
    }

    private String recentTabName,recentTabLocation,recentTabMobileNo;

}
