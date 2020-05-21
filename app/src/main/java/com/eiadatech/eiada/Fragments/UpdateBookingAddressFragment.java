package com.eiadatech.eiada.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.SaveChangesSuccessfully;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.BookingAddressModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.eiadatech.eiada.Adapters.BookingAddressAdapter.selectedBookingAddress;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateBookingAddressFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private Location location;
    GoogleApiClient googleApiClient;
    LocationManager locationManager;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionstoRequest;
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String mlattitude = "";
    Marker currentMarker;
    TextView name;
    public String result;
    private String selectedAddress = "", selectedType = "";
    private ProgressDialog progressDialog;
    int mapPosition = 0;

    private EditText editTextAddress, editTextName;
    Spinner spinnerAddressType;
    private Button btnSave;
    private ConstraintLayout constraintLayout;

    public UpdateBookingAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_booking_address, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {
        mapPosition = 0;
        editTextAddress = view.findViewById(R.id.editText5);
        editTextName = view.findViewById(R.id.editText4);
        spinnerAddressType = view.findViewById(R.id.editText6);

        btnSave = view.findViewById(R.id.button4);
        constraintLayout = view.findViewById(R.id.constraintLayout);

        progressDialog = new ProgressDialog(getActivity());
        editTextName.setText(selectedBookingAddress.getLocationName());
        TextView btnBack = view.findViewById(R.id.textView151);
        ImageView imageBack = view.findViewById(R.id.imageView73);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        latitude = Double.parseDouble(selectedBookingAddress.getLatitude());
        longitude = Double.parseDouble(selectedBookingAddress.getLongitude());
        selectedAddress = selectedBookingAddress.getAddress();
        spinnerAddressType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedType = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectedType = selectedBookingAddress.getType();

        if (selectedType.equalsIgnoreCase("Home")) {
            spinnerAddressType.setSelection(0);
        } else if (selectedType.equalsIgnoreCase("Office")) {
            spinnerAddressType.setSelection(1);
        } else if (selectedType.equalsIgnoreCase("Others")) {
            spinnerAddressType.setSelection(2);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionstoRequest = permissionstoRequest(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionstoRequest.size() > 0) {
                requestPermissions(permissionstoRequest.toArray(new String[permissionstoRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        initializeGoogleApiClient();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextName.getText().toString().trim().isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide location name", Snackbar.LENGTH_SHORT).show();
                } else if (selectedType.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please select addfress type", Snackbar.LENGTH_SHORT).show();
                } else {
                    updateBookingAddress(selectedBookingAddress.getBookingAddressId(), selectedBookingAddress.getUserId(), editTextName.getText().toString().trim(), selectedAddress, selectedBookingAddress.getMobile(), String.valueOf(latitude), String.valueOf(longitude));

                }


            }
        });


    }

    private void updateBookingAddress(String bookingId, String userId, String locationName, String address, String mobile, String mlattitude, String longitude) {
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<BookingAddressModel> apiCall = retrofitInterface.updateBookingAddress(new BookingAddressModel(bookingId, userId, locationName, address, selectedType,mobile, mlattitude, longitude));

        apiCall.enqueue(new Callback<BookingAddressModel>() {
            @Override
            public void onResponse(Call<BookingAddressModel> call, Response<BookingAddressModel> response) {
                if (response.isSuccessful()) {
                    Session.savePrimaryAddress(getActivity(),locationName);
                    final SaveChangesSuccessfully bottomSheetDialog = new SaveChangesSuccessfully();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "successfullySaved");
                }
            }

            @Override
            public void onFailure(Call<BookingAddressModel> call, Throwable t) {

                Snackbar.make(constraintLayout, "Internet problem please try later", Snackbar.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1010) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                name.setText(getAddress(latitude, longitude));
                mMap.clear();
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(place.getLatLng().latitude,
                                        place.getLatLng().longitude))
                        .draggable(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 17f));


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {


            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                getActivity().finish();
            }
            return false;
        }

        return true;
    }


    private void initializeGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    private ArrayList<String> permissionstoRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String permission : wantedPermissions) {
            if (!hasPermission(permission)) {
                result.add(permission);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;

    }


    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!checkPlayServices()) {
            Toast.makeText(getActivity(), "You need to install google play services to use the app properly", Toast.LENGTH_LONG).show();
        }


    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (!selectedAddress.equalsIgnoreCase("")) {
            LatLng sydney = new LatLng(latitude, longitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));


            mMap.addMarker(new MarkerOptions().position(sydney).title(getAddress(latitude, longitude)).draggable(true));

        } else if (location != null) {
            mlattitude = String.valueOf(location.getLatitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng sydney = new LatLng(latitude, longitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));


            mMap.addMarker(new MarkerOptions().position(sydney).title(getAddress(latitude, longitude)).draggable(true));


        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                double latitude, longitude;
                if (mapPosition == 0 || mapPosition == 1) {
                    latitude = Double.parseDouble(selectedBookingAddress.getLatitude());
                    longitude = Double.parseDouble(selectedBookingAddress.getLongitude());
                    mapPosition++;
                } else {
                    latitude = mMap.getCameraPosition().target.latitude;

                    longitude = mMap.getCameraPosition().target.longitude;
                }

                selectedAddress = getAddress(latitude, longitude);

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                editTextAddress.setText(selectedAddress);
                selectedAddress = editTextAddress.getText().toString().trim();

            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

    }


    public String getAddress(final double latitude, final double longitude) {

        final Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);

                        result = address.getAddressLine(0);
                    }
                } catch (IOException e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                }

            }
        };
        thread.start();


        return result;
    }

}
