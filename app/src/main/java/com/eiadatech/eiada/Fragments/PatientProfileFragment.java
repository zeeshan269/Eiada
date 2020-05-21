package com.eiadatech.eiada.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.LogoutConfirmationBottomSheet;
import com.eiadatech.eiada.Fragments.Documents.UploadDocumentsFragment;
import com.eiadatech.eiada.Fragments.EditProfile.EditProfileFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Fragments.Documents.UploadPassportFragment.scaleDown;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientProfileFragment extends Fragment {


    private TextView textViewName, textViewPrimaryAddress;
    private CircleImageView patientImage;
    private ConstraintLayout editProfile, address, familyMembers, uploadDocuments, chatWithAdmin, cards, logOut;

    private TextView uploadProfessionalImage;
    private String encodedPatientImage = "";

    private ImageView btnBack;

    public PatientProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        patientImage = view.findViewById(R.id.profileCircleImageView1);
        textViewName = view.findViewById(R.id.textView145);
        textViewPrimaryAddress = view.findViewById(R.id.textView146);
        editProfile = view.findViewById(R.id.cardView);
        address = view.findViewById(R.id.cardView2);
        familyMembers = view.findViewById(R.id.cardView3);
        cards = view.findViewById(R.id.cardView7);
        logOut = view.findViewById(R.id.cardView10);
        uploadDocuments = view.findViewById(R.id.cardView4);
        chatWithAdmin = view.findViewById(R.id.cardView12);
        uploadProfessionalImage = view.findViewById(R.id.textView143);
        btnBack = view.findViewById(R.id.imageView74);


        if (Session.getPfrimaryAddress(getActivity()).equals("")) {
            textViewPrimaryAddress.setText("Not Available");
        } else {
            textViewPrimaryAddress.setText(Session.getPfrimaryAddress(getActivity()));
        }
        textViewName.setText(Session.getUser_Name(getActivity()));

        if (!Session.getImage(getActivity()).equalsIgnoreCase("")) {
            Picasso.get().load(Session.getImage(getActivity())).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(patientImage);
        } else {
            patientImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.defaultu));
        }

        uploadProfessionalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new EditProfileFragment()).commit();

            }
        });

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ReviewsFragment()).commit();

            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new BookingAddressFragment()).commit();
            }
        });

        familyMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new FamilyMembersFragment()).commit();
            }
        });

        uploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UploadDocumentsFragment()).commit();
            }
        });

        chatWithAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ChatsFragment()).commit();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final LogoutConfirmationBottomSheet bottomSheetDialog = new LogoutConfirmationBottomSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "logoutConfirmationBottomSheet");


            }
        });


    }

    public void uploadImage() {


        if (!checkIfAlreadyhavePermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), 301);

        }

    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == 301) {

                Uri productImageURI = data.getData();

                Bitmap bitmap1 = null;
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), productImageURI);
                    Bitmap scale = scaleDown(bitmap1, 400, true);
                    patientImage.setImageBitmap(scale);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    scale.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    encodedPatientImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    changeProfileImage(Session.getUser_ID(getActivity()), encodedPatientImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void changeProfileImage(String patientId, final String image) {

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(patientId);
        patientModel.setImage(image);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> apiCall = retrofitInterface.changeProfileImage(patientModel);

        apiCall.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getImage().equalsIgnoreCase("Failed")) {
                        Toast.makeText(getActivity(), "Server problem!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        Session.saveImage(getActivity(), response.body().getImage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getActivity(), "Internet Problem please try later", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
