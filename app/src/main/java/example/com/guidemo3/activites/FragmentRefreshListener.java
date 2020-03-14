package example.com.guidemo3.activites;

import java.util.ArrayList;


import example.com.guidemo3.modal.DonateTabModel;

/**
 * Created by Prateek on 4/17/2018.
 */

public interface FragmentRefreshListener{
    public void passDataToFragment(ArrayList<DonateTabModel> list);
}

