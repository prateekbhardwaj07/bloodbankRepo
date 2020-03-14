package example.com.guidemo3.activites;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.guidemo3.R;

import example.com.guidemo3.modal.User;


public class RegisterActivity extends AppCompatActivity {
    private Spinner country,bloodGroup;
    private EditText state,city,mobileNumber,email,password;
    private TextInputEditText Fname,Lname;
    private Button registerbtn;
    public int position;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initValidations();


    }

    private void initValidations() {

    }

    private void initViews() {
        Fname=(TextInputEditText)findViewById(R.id.Fname);
        Lname=(TextInputEditText)findViewById(R.id.Lname);
        state=(EditText)findViewById(R.id.stateName);
        city=(EditText)findViewById(R.id.cityName);
        mobileNumber=(EditText)findViewById(R.id.mobileNo);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        country=(Spinner)findViewById(R.id.spinner);
        bloodGroup=(Spinner)findViewById(R.id.spinnerBlood);
        country.setPrompt("Select Country");
        bloodGroup.setPrompt("Please Tell us blood group");
        String[] items=getResources().getStringArray(R.array.spinnerBlood);
        String[] items1=getResources().getStringArray(R.array.spinnerCountry);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        bloodGroup.setAdapter(adapter);
        bloodGroup.setSelection(0);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.spinner_item,items1);
        country.setAdapter(adapter1);
        country.setSelection(0);
        registerbtn=(Button)findViewById(R.id.registerbtn);
    }
}
