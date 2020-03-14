package example.com.guidemo3.activites;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prateekb on 2/22/2018.
 */
public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    String googlePlacesData;
    GoogleMap googleMap;
    String url;
    Context context;
    public List<HashMap<String, String>> nearbyPlacesList = null;

    public GetNearbyPlaces(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackGround Entered");
            googleMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");

        } catch (IOException exception) {
            Log.d("GooglePlacesReadTask", exception.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Log.d("Marker","Inside Marker Click Listener");
//                return true;
//            }
//        });

        Log.d("NearByPlacesList", nearbyPlacesList.toString());
    }


    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            final MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            final String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            final LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            googleMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            PlacesData placesData = new PlacesData(placeName,vicinity,"Active");
            MapsMarkerActivity.placesDataList.add(placesData);
            MapsMarkerActivity.placesAdapter.notifyDataSetChanged();


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(context,ActivitySearch.class);
                    //intent.putExtra("Source",MapsMarkerActivity.origin.toString());
                    Log.d("Destination",marker.getPosition().toString());
                    intent.putExtra("Destination",marker.getPosition());
                    context.startActivity(intent);
                    return false;
                }
            });
        }
    }

}
