package com.eiadatech.eiada.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.SaveChangesSuccessfully;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;
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
public class AddFamilyMemberFragment extends Fragment {

    private Spinner spinnerGender;
    private Spinner spinnerRelation;
    private EditText editTextName;
    private TextView textViewDate;
    private Button btnAdd;
    private ConstraintLayout constraintLayout;
    private String birthDate = "";
    private String gender = "";
    private String relation = "";
    private RetrofitInterface retrofitInterface;



    public AddFamilyMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_family_member, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        spinnerGender = view.findViewById(R.id.editText16);
        spinnerRelation = view.findViewById(R.id.spinner3);
        editTextName = view.findViewById(R.id.editText13);
        textViewDate = view.findViewById(R.id.textView36);
        btnAdd = view.findViewById(R.id.button3);

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


        selectBirthDate();



        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relation = adapterView.getItemAtPosition(i).toString();
                if (relation.equalsIgnoreCase("Select Relation")) {
                    relation = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }

                String name = editTextName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide name", Snackbar.LENGTH_SHORT).show();
                } else if (birthDate.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide birth date", Snackbar.LENGTH_SHORT).show();
                } else if (gender.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please select gender", Snackbar.LENGTH_SHORT).show();
                } else if (relation.isEmpty()) {
                    Snackbar.make(constraintLayout, "Please select relation", Snackbar.LENGTH_SHORT).show();
                } else {
                    addFamilyMember(name, gender, birthDate, relation);
                }

            }
        });

    }

    private void addFamilyMember(String name, String gender, String birthday, final String relation) {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<FamilyMemberModel> apiCall = retrofitInterface.addFamilyMember(new FamilyMemberModel(name, relation, gender, birthday, Session.getUser_ID(getActivity())));
        apiCall.enqueue(new Callback<FamilyMemberModel>() {
            @Override
            public void onResponse(Call<FamilyMemberModel> call, Response<FamilyMemberModel> response) {
                if (response.isSuccessful()) {
                    final SaveChangesSuccessfully bottomSheetDialog = new SaveChangesSuccessfully();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "successfullySaved");

                }
            }

            @Override
            public void onFailure(Call<FamilyMemberModel> call, Throwable t) {
                Snackbar.make(constraintLayout, "Internet Problem Please try later", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void selectBirthDate() {
        final Calendar calendar = Calendar.getInstance();
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);


        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String day = String.valueOf(dayOfMonth);

                                String stringMonth = String.valueOf(month);


                                if (stringMonth.length() == 1) {
                                    stringMonth = "0" + month;
                                }

                                if (day.length() == 1) {
                                    day = "0" + day;
                                }


                                birthDate = year + "-" + (stringMonth) + "-" + day;
                                textViewDate.setText(birthDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }

}
