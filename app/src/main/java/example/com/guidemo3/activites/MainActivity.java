package example.com.guidemo3.activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.core.GeoHashQuery;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guidemo3.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import example.com.guidemo3.modal.DonateTabModel;
import example.com.guidemo3.modal.User;
import example.com.guidemo3.modal.Users;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ProgressDialog progressDialog;
    LocationManager locationManager;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference,userLoc,userInformation;
    Location location2;
    String provider;
    private GeoFire geofire;
    public  ArrayList<DonateTabModel> users = new ArrayList<>();
    private static GoogleApiClient mGoogleAPIClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    public FragmentRefreshListener fragmentRefreshListener;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        databaseReference= FirebaseDatabase.getInstance().getReference();
        userLoc=databaseReference.child("User Locations");
        mAuth=FirebaseAuth.getInstance();


        initGoogleAPIClient();

        progressDialog = new ProgressDialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                readLocation();

            } else {
                showSettingsDialog();
                Log.d("TAG", "SettingDialog");
                readLocation();
                Log.d("TAG", "readLocationMethod");
                //locationManager.requestLocationUpdates(provider, 0, 2, this);
            }
        } else {

            showSettingsDialog();
            readLocation();
        }



    }


    private void fetchUsers(Location location) {
        final Set<String> usersNearBy = new HashSet<String>();
        GeoLocation geoLocation = new GeoLocation(location.getLatitude(),location.getLongitude());

        // query around current user location with 1.6km radius
        GeoQuery geoQuery = geofire.queryAtLocation(geoLocation, 100f);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String username, GeoLocation location) {
                usersNearBy.add(username);


                // additional code, like displaying a pin on the map
                // and adding Firebase listeners for this user
            }

            @Override
            public void onKeyExited(String username) {
                usersNearBy.remove(username);
                // additional code, like removing a pin from the map
                // and removing any Firebase listener for this user
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

                Log.d("FetchDetails",usersNearBy.toString());
                fetchFirebaseUsers(usersNearBy);
                fragmentRefreshListener.passDataToFragment(users);

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }


        });



    }

    private void fetchFirebaseUsers(Set<String> usersNearBy) {
        Iterator<String> iterator = usersNearBy.iterator();
        while(iterator.hasNext()) {
            userInformation = databaseReference.child("User Information");
            //String name =userInformation.child(iterator.next()).child("name").getKey();
            userInformation.child(iterator.next()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users currentUser = dataSnapshot.getValue(Users.class);
                    if(currentUser==null)
                    {
                        Log.d("Tag","Current User has no details");

                    }
                    else
                    {
                        users.add(new DonateTabModel(currentUser.getName(),currentUser.getLocation(),currentUser.getBloodGroup()));
                        Log.d("Users",users.toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }



    }

    private void initGeoFire(Location location) {
        FirebaseUser user = mAuth.getCurrentUser();
        geofire = new GeoFire(databaseReference.child("geofire"));

        geofire.setLocation(user.getUid(), new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if(error!=null)
                {
                    Log.d("GeoFire","Success");
                }
            }
        });

    }

    private void initGoogleAPIClient() {
        mGoogleAPIClient = new GoogleApiClient.Builder(MainActivity.this).addApi(LocationServices.API).build();
        mGoogleAPIClient.connect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(this,ProfileActivity.class));
            return true;

        }
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.sign_out) {
            startActivity(new Intent(this, OtpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {
        readLocation();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {


    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private Bundle bundleAdapter;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            donateFragment donateFragment = new donateFragment();
            RecentActivityFragment histroyFragment=new RecentActivityFragment();
            EmergencyHelpFragment emergencyHelpFragment=new EmergencyHelpFragment();
            switch (position) {
                case 0:
                    return donateFragment;
                case 1:
                    return histroyFragment;
                case 2:
                    return emergencyHelpFragment;

            }
            return null;
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Donate ";
                case 1:
                    return "Recent ";
                case 2:
                    return "Emergency";
            }
            return null;
        }
    }

    private void readLocation() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (location2 == null) {
            locationManager.requestLocationUpdates(provider, 60000, 1500, this);
            if (locationManager != null) {
                location2 = locationManager.getLastKnownLocation(provider);
                if (location2 != null) {
                    String locationName=getLocation(location2.getLatitude(),location2.getLongitude());
                    Toast.makeText(MainActivity.this, String.valueOf(location2.getLatitude()) + location2.getLongitude(), Toast.LENGTH_LONG).show();
                    User user1=new User(FirstActivity.number,location2.getLongitude(),location2.getLatitude(),locationName);
                    FirebaseUser user=mAuth.getCurrentUser();
                    userLoc.child(user.getUid()).setValue(user1);
                    initGeoFire(location2);
                    fetchUsers(location2);

                }
            }
        }

    }

    private String getLocation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            return (city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not Found";

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 123:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED )
                {
                    Toast.makeText(this,"Permissions are granted for location",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void showSettingsDialog()
    {
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30*1000);
        locationRequest.setFastestInterval(15*1000);

        LocationSettingsRequest.Builder builder= new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleAPIClient,builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here
                        Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode){
                    case RESULT_OK:
                        readLocation();
                        Log.d("Settings","Result OK");
                        break;
                    case RESULT_CANCELED:
                        Log.d("Settings","Result Cancel");
                        break;
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingsDialog();
        }
    };

    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");

                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();

                    Log.e("About GPS", "GPS is Disabled in your device");
                }

            }

        }
    };

}

