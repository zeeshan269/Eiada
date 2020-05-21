package com.eiadatech.eiada.Fragments.EditProfile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Session;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private ConstraintLayout nameTab, emailTab, dateOfBirthTab, genderTab, PhoneNumberTab, changePasswordTab;

    private TextView textViewName, textViewEmail, textViewBirthday, textViewGender, textViewPhoneNumber;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {

        navigationView.setVisibility(View.GONE);

        nameTab = view.findViewById(R.id.constraintLayout11);
        dateOfBirthTab = view.findViewById(R.id.constraintLayout14);
        emailTab = view.findViewById(R.id.constraintLayout12);
        genderTab = view.findViewById(R.id.constraintLayout13);
        PhoneNumberTab = view.findViewById(R.id.constraintLayout15);
        changePasswordTab = view.findViewById(R.id.constraintLayout16);

        textViewEmail = view.findViewById(R.id.textView169);
        textViewGender = view.findViewById(R.id.textView171);
        textViewPhoneNumber = view.findViewById(R.id.textView175);
        textViewName = view.findViewById(R.id.textView167);
        textViewBirthday = view.findViewById(R.id.textView173);

        textViewEmail.setText(Session.getUser_Email(getActivity()));
        textViewGender.setText(Session.getUser_Gender(getActivity()));
        textViewName.setText(Session.getUser_Name(getActivity()));
        textViewPhoneNumber.setText(Session.getUser_Mobile(getActivity()));
        textViewBirthday.setText(Session.getUser_Date_Of_Birth(getActivity()));

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

        nameTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UpdateNameFragment()).commit();
            }
        });

        emailTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UpdateEmailFragment()).commit();
            }
        });

        genderTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UpdateGenderFragment()).commit();
            }
        });

        changePasswordTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ChangePasswordFragment()).commit();
            }
        });

        dateOfBirthTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UpdateBirthdayFragment()).commit();

            }
        });


    }

}
