package example.com.guidemo3.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guidemo3.R;

import example.com.guidemo3.modal.Users;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FirstActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference,userInfo;
    private SharedPreferences preferences;

    public static String number;
    private TextInputEditText textName,textAge;
    private Spinner bloodspinner;
    private Button AddDetail;

    public static String name,age,bloodgroup,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_actvity);
        Bundle bundle=getIntent().getExtras();
        number=bundle.getString("mobileNumber",null);
        textName=(TextInputEditText)findViewById(R.id.textInputEditTextName);
        textAge=(TextInputEditText)findViewById(R.id.textInputEditTextAge);
        AddDetail=(Button)findViewById(R.id.AddDetail);
        AddDetail.setOnClickListener(this);
        bloodspinner=(Spinner)findViewById(R.id.spinner1);
        String[] items=getResources().getStringArray(R.array.spinnerBlood);
        preferences = getSharedPreferences("firstPreference",
                Context.MODE_PRIVATE);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        bloodspinner.setAdapter(adapter);
        bloodspinner.setSelection(0);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        userInfo=databaseReference.child("User Information");
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,PhoneAuthActivity.class));
        }

    }

    @Override
    public void onClick(View view) {
        if(view==AddDetail) {
            saveInformation();

        }
    }

    public void saveInformation() {

        SharedPreferences.Editor editor = preferences.edit();
        name=textName.getText().toString();
        age=textAge.getText().toString();
        bloodgroup=bloodspinner.getSelectedItem().toString();
        number=mAuth.getCurrentUser().getPhoneNumber();
        Users users=new Users(number,name,age,bloodgroup,"Email");
        FirebaseUser user=mAuth.getCurrentUser();
        userInfo.child(user.getUid()).setValue(users);
        editor.putString("Name",name);
        editor.putString("Age",age);
        editor.putString("BloodGroup",bloodgroup);
        editor.putString("Number",number);
        editor.commit();
        Log.d("Preferences",preferences.getString("Number",null));

        //displaying a success toast
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));
    }


}
//SharedPreference Use