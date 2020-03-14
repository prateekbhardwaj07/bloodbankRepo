package example.com.guidemo3.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthProvider;
import com.guidemo3.R;

import java.util.concurrent.TimeUnit;

import static android.R.attr.phoneNumber;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OtpActivity";
    private FirebaseAuth mAuth;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Button mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        TextView fireBaseId = (TextView) findViewById(R.id.detail);
        preferences = getSharedPreferences("firstPreference",
                Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth!=null){
            fireBaseId.setText(mAuth.getCurrentUser().getPhoneNumber());
        }
        mSignOutButton.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_out_button:
                mAuth.signOut();
                preferences.edit().clear().commit();
                startActivity(new Intent(OtpActivity.this, PhoneAuthActivity.class));
                finish();
                break;
        }
    }

}
