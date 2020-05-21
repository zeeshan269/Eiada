package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.LogoutConfirmationBottomSheet;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private ConstraintLayout logOut;

    private CircleImageView patientImage;
    private TextView textViewName;
    private Switch messageNotifications;
    private Switch notifications;

    private ImageView messagesScreen,notificationsScreen;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        HomeFragment.type = "";
        logOut = view.findViewById(R.id.logout);
        navigationView.setVisibility(View.VISIBLE);
      //  wallet = view.findViewById(R.id.wallet);
        messageNotifications = view.findViewById(R.id.switch2);
        notifications = view.findViewById(R.id.switch1);

        patientImage = view.findViewById(R.id.profileCircleImageView);
        textViewName = view.findViewById(R.id.textView5);
        messagesScreen = view.findViewById(R.id.imageView28);
        notificationsScreen = view.findViewById(R.id.imageView35);


        textViewName.setText(Session.getUser_Name(getActivity()));

        if (!Session.getImage(getActivity()).equalsIgnoreCase("")) {
            Picasso.get().load(Session.getImage(getActivity())).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(patientImage);
        } else {
            patientImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.defaultu));
        }


        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new PatientProfileFragment()).commit();

            }
        });

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new PatientProfileFragment()).commit();
            }
        });



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LogoutConfirmationBottomSheet bottomSheetDialog = new LogoutConfirmationBottomSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "logoutConfirmationBottomSheet");
            }
        });

        messagesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ChatsFragment()).commit();

            }
        });

        notificationsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SettingsFragment()).commit();

            }
        });



        messageNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageNotificationStatus;
                if (messageNotifications.isChecked()) {
                    messageNotificationStatus = "yes";
                } else {
                    messageNotificationStatus = "no";
                }
                updateNotificationSettings(Session.getUser_ID(getActivity()),messageNotificationStatus);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value;
                if (notifications.isChecked()) {
                    value = "yes";
                } else {
                    value = "no";

                }

            }
        });
    }


    private void updateNotificationSettings(String patient, String status) {
        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(patient);
        patientModel.setMessageNotifications(status);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> api = retrofitInterface.updateNotificationSettings(patientModel);

        api.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {

            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {

            }
        });

    }




}
