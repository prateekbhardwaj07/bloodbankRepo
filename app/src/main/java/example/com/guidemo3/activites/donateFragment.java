package example.com.guidemo3.activites;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guidemo3.R;

import java.util.ArrayList;
import java.util.List;

import example.com.guidemo3.modal.DonateTabModel;

/**
 * Created by Prateek on 8/28/2017.
 */

public class donateFragment extends Fragment implements FragmentRefreshListener{

    View view;
    public RecyclerView recyclerView;
    public ArrayList<DonateTabModel> list = new ArrayList<>();
    public static DonateTabAdapter tabAdapter;

    public donateFragment()
    {

    }


    public static donateFragment newInstance(List<DonateTabModel> list)
    {
        donateFragment fragment = new donateFragment();
        return fragment;

    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        context = getActivity();
        ((MainActivity)context).fragmentRefreshListener = this;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.donate_tab, container, false);
        setRetainInstance(true);
        return view;

    }

    public void onViewCreated(View view,Bundle savedInstanceBundle)
    {
        super.onViewCreated(view, savedInstanceBundle);
        this.view = view;
    }


    public void onActivityCreated(Bundle savedInstanceBundle)
    {
        super.onActivityCreated(savedInstanceBundle);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void passDataToFragment(ArrayList<DonateTabModel> list) {
        tabAdapter = new DonateTabAdapter(list);
        recyclerView.setAdapter(tabAdapter);
    }



}
