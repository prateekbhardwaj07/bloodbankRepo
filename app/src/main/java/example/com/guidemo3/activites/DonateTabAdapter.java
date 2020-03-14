package example.com.guidemo3.activites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guidemo3.R;

import java.util.ArrayList;

import example.com.guidemo3.modal.DonateTabModel;

/**
 * Created by Prateek on 4/8/2018.
 */

public class DonateTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DonateTabModel> list;

    public DonateTabAdapter(ArrayList<DonateTabModel> list)
    {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        final TextView textName,textLocation,textBloodGroup,textMapView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            textName = (TextView)itemView.findViewById(R.id.donateTabName);
            textLocation = (TextView)itemView.findViewById(R.id.donateTabLocation);
            textBloodGroup = (TextView)itemView.findViewById(R.id.donateTabBloodGroup);
            textMapView = (TextView)itemView.findViewById(R.id.donateTabMapsText);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_list_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DonateTabModel model = list.get(position);
        ((MyViewHolder)holder).textName.setText(model.getDonateTabName());
        ((MyViewHolder)holder).textLocation.setText(model.getDonateTabLocation());
        ((MyViewHolder)holder).textBloodGroup.setText(model.getDonateTabBloodGroup());
        ((MyViewHolder)holder).textMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
