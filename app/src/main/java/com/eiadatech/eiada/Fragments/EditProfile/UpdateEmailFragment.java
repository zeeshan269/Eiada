package com.eiadatech.eiada.Fragments.EditProfile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.SaveChangesSuccessfully;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateEmailFragment extends Fragment {

    private EditText editTextEmail;
    private Button btnSaveCHanges;
    private ConstraintLayout constraintLayout;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    public UpdateEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_email, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        editTextEmail = view.findViewById(R.id.editText14);
        btnSaveCHanges = view.findViewById(R.id.button23);
        constraintLayout = view.findViewById(R.id.constraintLayout);

        navigationView.setVisibility(View.GONE);

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


        editTextEmail.setText(Session.getUser_Email(getActivity()));


        btnSaveCHanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                }

                String email = editTextEmail.getText().toString();

                if (email.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide email", Snackbar.LENGTH_SHORT).show();
                } else if (!(email.matches(emailPattern))) {
                    Snackbar.make(constraintLayout, "Please provide valid email", Snackbar.LENGTH_SHORT).show();
                } else {
                    updateEmail(email);
                }

            }
        });

    }

    private void updateEmail(final String email) {

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(Session.getUser_ID(getActivity()));
        patientModel.setName("");
        patientModel.setEmail(email);
        patientModel.setMobile("");
        patientModel.setGender("");
        patientModel.setDateOfBirth("");

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> api = retrofitInterface.updatePatientProfile(patientModel);
        api.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    Session.saveEmail(getActivity(), email);
                    editTextEmail.setText(Session.getUser_Email(getActivity()));
                    final SaveChangesSuccessfully bottomSheetDialog = new SaveChangesSuccessfully();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "successfullySaved");
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                Snackbar.make(constraintLayout, "Server Error Please try later", Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}
