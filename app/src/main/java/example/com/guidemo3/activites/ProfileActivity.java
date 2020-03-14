package example.com.guidemo3.activites;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ProfileActivity extends AppCompatActivity {

    ImageView profile;
    TextView email,editLocation,mobileNumber,userName,userAge,userBlood;
    Button editEmail;
    private DatabaseReference reference,reference2;
    private FirebaseAuth mAuth;
    private String emailString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView profile=(ImageView)findViewById(R.id.circleprofile);
        final TextView userName=(TextView)findViewById(R.id.userName);
        final TextView userAge=(TextView)findViewById(R.id.userAge);
        final TextView userBlood=(TextView)findViewById(R.id.userBloodGroup);
        final TextView email=(TextView)findViewById(R.id.emailField);
        final TextView editLocation=(TextView)findViewById(R.id.editLocation);
        final TextView mobileNumber=(TextView) findViewById(R.id.mobileNo);
        editEmail = (Button)findViewById(R.id.editEmailBtn);


        reference= FirebaseDatabase.getInstance().getReference("User Information");
        mAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=mAuth.getCurrentUser();
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users currentUser = dataSnapshot.getValue(Users.class);
                if(currentUser==null)
                {
                    Log.d("Tag","Current User has no details");

                }
                else
                {
                    Log.d("Details","Details Set");
                    String number=currentUser.getMobileNumber();
                    mobileNumber.setText(number);
                    userName.setText(currentUser.getName().toUpperCase());
                    userAge.setText("Age :"+currentUser.getAge());
                    userBlood.setText("Blood Group :"+currentUser.getBloodGroup());
                    email.setText(currentUser.getEmailId());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference2= FirebaseDatabase.getInstance().getReference("User Locations");
        reference2.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User currentUser = dataSnapshot.getValue(User.class);
                if(currentUser==null)
                {
                    Log.d("Tag","Current User has no details");

                }
                else
                {
                    Log.d("Location","Location Set");
                    editLocation.setText(currentUser.getLocation());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ProfileActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_dialog_input, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ProfileActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                userInputDialogEditText.setText(email.getText());
                alertDialogBuilderUserInput
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                emailString = userInputDialogEditText.getText().toString();
                                reference.child(user.getUid()).child("emailId").setValue(emailString);
                                Log.d("Email",emailString);

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {

                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                alertDialogAndroid.getButton(alertDialogAndroid.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alertDialogAndroid.getButton(alertDialogAndroid.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

            }
        });




    }
}
