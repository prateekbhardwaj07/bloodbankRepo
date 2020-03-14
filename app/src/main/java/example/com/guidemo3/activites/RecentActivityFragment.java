package example.com.guidemo3.activites;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guidemo3.R;

/**
 * Created by Prateek on 8/28/2017.
 */

public class RecentActivityFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recent_activity_tab,container,false);
    }
}
