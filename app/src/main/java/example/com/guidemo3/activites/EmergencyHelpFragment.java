package example.com.guidemo3.activites;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guidemo3.R;

import example.com.guidemo3.modal.User;
import example.com.guidemo3.modal.Users;

/**
 * Created by Prateek on 8/28/2017.
 */

public class EmergencyHelpFragment extends Fragment{
    private CardView cardView1,cardView2;
    private DatabaseReference reference,userLoc;
    private FirebaseAuth mAuth;
    Double Longitude,Latitude;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.emergency_help_tab,container,false);
        return view;
    }
    public void onViewCreated(View view,Bundle savedInstanceBundle)
    {
        reference= FirebaseDatabase.getInstance().getReference();
        userLoc=reference.child("User Locations");
        mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if(currentUser!=null)
                {
                    Longitude = currentUser.getLongitude();
                    Latitude = currentUser.getLangitude();
                    Log.d("LOCATION",Longitude.toString());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cardView1 = (CardView)view.findViewById(R.id.hospitalCard);
        cardView2 = (CardView)view.findViewById(R.id.bloodBankCard);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Hospital Card Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MapsMarkerActivity.class);
                intent.putExtra("Longitude",Longitude);
                intent.putExtra("Latitude",Latitude);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Blood Bank Card Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MapsMarkerActivity.class);
                intent.putExtra("Longitude",Longitude);
                intent.putExtra("Latitude",Latitude);
                startActivity(intent);
            }
        });
    }
}
