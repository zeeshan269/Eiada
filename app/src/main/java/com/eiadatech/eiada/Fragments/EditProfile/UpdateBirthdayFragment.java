package com.eiadatech.eiada.Fragments.EditProfile;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateBirthdayFragment extends Fragment {


    private TextView textViewBirthday;
    private Button btnSaveCHanges;
    private ConstraintLayout constraintLayout;

    private String selectedBirthday;


    public UpdateBirthdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_birthday, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        textViewBirthday = view.findViewById(R.id.editText14);
        btnSaveCHanges = view.findViewById(R.id.button23);
        constraintLayout = view.findViewById(R.id.constraintLayout);

        navigationView.setVisibility(View.GONE);

        selectBirthday();
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

        selectedBirthday = Session.getUser_Date_Of_Birth(getActivity());
        textViewBirthday.setText(selectedBirthday);

        btnSaveCHanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                }


                if (selectedBirthday.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide birthday", Snackbar.LENGTH_SHORT).show();
                } else {
                    updateBirthday(selectedBirthday);
                }

            }
        });


    }

    private void selectBirthday() {

        final Calendar calendar = Calendar.getInstance();
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        textViewBirthday.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        int month = monthOfYear + 1;
                        String day = String.valueOf(dayOfMonth);

                        String stringMonth = String.valueOf(month);

                        if (stringMonth.length() == 1) {
                            stringMonth = "0" + month;
                        }

                        if (day.length() == 1) {
                            day = "0" + day;
                        }

                        selectedBirthday = day + "-" + (stringMonth) + "-" + year;
                        textViewBirthday.setText(selectedBirthday);

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

    }


    private void updateBirthday(final String birthday) {

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(Session.getUser_ID(getActivity()));
        patientModel.setName("");
        patientModel.setEmail("");
        patientModel.setMobile("");
        patientModel.setGender("");
        patientModel.setDateOfBirth(birthday);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> api = retrofitInterface.updatePatientProfile(patientModel);
        api.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    Session.saveDateOfBirth(getActivity(), birthday);
                    textViewBirthday.setText(Session.getUser_Date_Of_Birth(getActivity()));
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
