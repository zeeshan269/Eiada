package com.eiadatech.eiada.Fragments.EditProfile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.HomeFragment;
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
public class ChangePasswordFragment extends Fragment {

    private RetrofitInterface retrofitInterface;
    private EditText editTextPassword, editTextReTypePassword, editTextOldPassword;
    private Button btnUpdate;
    private ConstraintLayout constraintLayout;
    private ProgressDialog progressDialog;

    private CheckedTextView curreShowPassword, newShowPassword, confirmShowPassword;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        editTextPassword = view.findViewById(R.id.editText);
        editTextReTypePassword = view.findViewById(R.id.editText2);
        editTextOldPassword = view.findViewById(R.id.editText15);
        curreShowPassword = view.findViewById(R.id.imageView69);
        newShowPassword = view.findViewById(R.id.imageView64);
        confirmShowPassword = view.findViewById(R.id.imageView61);
        navigationView.setVisibility(View.GONE);

        btnUpdate = view.findViewById(R.id.button2);

        constraintLayout = view.findViewById(R.id.constraintLayout);

        progressDialog = new ProgressDialog(getActivity());

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


        curreShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curreShowPassword.isChecked()) {
                    curreShowPassword.setChecked(false);
                    editTextOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    curreShowPassword.setChecked(true);
                    editTextOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

        newShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newShowPassword.isChecked()) {
                    newShowPassword.setChecked(false);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    newShowPassword.setChecked(true);
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        confirmShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (confirmShowPassword.isChecked()) {
                    confirmShowPassword.setChecked(false);

                    editTextReTypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    confirmShowPassword.setChecked(true);
                    editTextReTypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }

                String password = editTextPassword.getText().toString().trim();
                String retypePassword = editTextReTypePassword.getText().toString().trim();
                String oldPassword = editTextOldPassword.getText().toString().trim();

                if (oldPassword.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide current password", Snackbar.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide new password", Snackbar.LENGTH_SHORT).show();
                } else if (retypePassword.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please retype password again", Snackbar.LENGTH_SHORT).show();
                } else if (!oldPassword.equalsIgnoreCase(Session.getUser_Password(getActivity()))) {
                    Snackbar.make(constraintLayout, "Invalid current password!", Snackbar.LENGTH_SHORT).show();
                } else if (!password.equalsIgnoreCase(retypePassword)) {
                    Snackbar.make(constraintLayout, "Passwords do not match!", Snackbar.LENGTH_SHORT).show();
                } else {
                    updatePassword(Session.getUser_ID(getActivity()), password);
                }

            }
        });

    }


    private void updatePassword(final String userId, final String password) {

        progressDialog.setMessage("Updating");
        progressDialog.show();

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(userId);
        patientModel.setPassword(password);

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> apiCall = retrofitInterface.updatePatientPassword(patientModel);

        apiCall.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getName().equalsIgnoreCase("")) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
                        Session.savePassword(getActivity(), password);
                        Snackbar.make(constraintLayout, "Password updated successfully!", Snackbar.LENGTH_SHORT).show();
                    } else if (response.body().getName().equalsIgnoreCase("Failed")) {
                        Snackbar.make(constraintLayout, "Server problem! Password could not be updated", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(constraintLayout, "Internet problem please try later", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
