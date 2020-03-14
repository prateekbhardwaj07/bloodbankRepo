package example.com.guidemo3.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Prateek on 4/8/2018.
 */

public class DonateTabModel implements Serializable,Parcelable
{
    private String donateTabName,donateTabLocation,donateTabBloodGroup;

    public DonateTabModel(String donateTabName, String donateTabLocation, String donateTabBloodGroup) {
        this.donateTabName = donateTabName;
        this.donateTabLocation = donateTabLocation;
        this.donateTabBloodGroup = donateTabBloodGroup;
    }

    //Parcelling Part

    public DonateTabModel(Parcel parcel)
    {
        String[] data = new String[3];
        parcel.readStringArray(data);
        this.donateTabName = data[0];
        this.donateTabLocation= data[1];
        this.donateTabBloodGroup= data[2];
    }

    public String getDonateTabName() {
        return donateTabName;
    }

    public void setDonateTabName(String donateTabName) {
        this.donateTabName = donateTabName;
    }

    public String getDonateTabLocation() {
        return donateTabLocation;
    }

    public void setDonateTabLocation(String donateTabLocation) {
        this.donateTabLocation = donateTabLocation;
    }

    public String getDonateTabBloodGroup() {
        return donateTabBloodGroup;
    }

    public void setDonateTabBloodGroup(String donateTabBloodGroup) {
        this.donateTabBloodGroup = donateTabBloodGroup;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.donateTabName,this.donateTabLocation,this.donateTabBloodGroup});

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DonateTabModel createFromParcel(Parcel parcel) {
            return new DonateTabModel(parcel);
        }

        public DonateTabModel[] newArray(int size) {
            return new DonateTabModel[size];
        }
    };
}
