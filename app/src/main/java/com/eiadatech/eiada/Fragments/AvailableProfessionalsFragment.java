package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter;
import com.eiadatech.eiada.Fragments.BottomSheets.SortOptionsSheet;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ProfessionalModel;
import com.eiadatech.eiada.Retrofit.Models.SymptonsModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableProfessionalsFragment extends Fragment {

    public static RecyclerView recyclerView;
    private ConstraintLayout constraintLayout, sortOptions;
    private EditText searchProfesional;
    public static List<ProfessionalModel> professionals = new ArrayList<>();
    // private TextView textViewSelectDate, textViewClear;
    private TextView selectedDisease;
    public static  TextView selectedPrice;
    public static ImageView closeFilter;

    public AvailableProfessionalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_available_professionals, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        navigationView.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.availableProfessions);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        searchProfesional = view.findViewById(R.id.editText18);
        closeFilter = view.findViewById(R.id.imageView176);
        selectedPrice = view.findViewById(R.id.textView206);
        //   textViewSelectDate = view.findViewById(R.id.textView112);
        selectedDisease = view.findViewById(R.id.textView111);
        sortOptions = view.findViewById(R.id.constraintLayout23);

        selectedDisease.setText(appointment.getSympton());



        selectedPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new AvailableProfessionalsAdapter(professionals, getActivity()));

                selectedPrice.setVisibility(View.INVISIBLE);
                closeFilter.setVisibility(View.INVISIBLE);
            }
        });

        //  textViewClear = view.findViewById(R.id.textView111);

//        textViewClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getAvailableProfessionals(appointment.getSymptonId());
//                textViewSelectDate.setText("Select Date");
//            }
//        });

        sortOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SortOptionsSheet bottomSheetDialog = new SortOptionsSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "sortOptionsBottomSheet");

            }
        });

        selectDate();
        getAvailableProfessionals(appointment.getSymptonId());

        searchProfesional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchValue = editable.toString();
                if (!searchValue.isEmpty()) {

                    List<ProfessionalModel> searchedProfessionals = new ArrayList<>();
                    for (int i = 0; i < professionals.size(); i++) {
                        if (professionals.get(i).getName().toLowerCase().contains(searchValue.toLowerCase())) {
                            searchedProfessionals.add(professionals.get(i));
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new AvailableProfessionalsAdapter(searchedProfessionals, getActivity()));

                } else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new AvailableProfessionalsAdapter(professionals, getActivity()));

                }

            }
        });


    }

    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);
//        textViewSelectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                int month = monthOfYear + 1;
//                                String day = String.valueOf(dayOfMonth);
//
//                                String stringMonth = String.valueOf(month);
//
//
//                                if (stringMonth.length() == 1) {
//                                    stringMonth = "0" + month;
//                                }
//
//                                if (day.length() == 1) {
//                                    day = "0" + day;
//                                }
//
//
//                                String date = year + "-" + stringMonth + "-" + day;
//                                textViewSelectDate.setText(date);
//                                getProfessionalsByDate(date, appointment.getSymptonId());
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });

    }

    private void getProfessionalsByDate(String date, String symptonId) {
        SymptonsModel s = new SymptonsModel();
        s.setSymptonId(symptonId);
        s.setDate(date);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ProfessionalModel>> apiCall = retrofitInterface.getAvailableProfessionalsByDate(s);

        apiCall.enqueue(new Callback<List<ProfessionalModel>>() {
            @Override
            public void onResponse(Call<List<ProfessionalModel>> call, Response<List<ProfessionalModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new AvailableProfessionalsAdapter(response.body(), getActivity()));
                }
            }

            @Override
            public void onFailure(Call<List<ProfessionalModel>> call, Throwable t) {
                Snackbar.make(constraintLayout, "Intenet Problem, Please try again later", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void getAvailableProfessionals(String symptonId) {

        SymptonsModel s = new SymptonsModel();
        s.setSymptonId(symptonId);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ProfessionalModel>> apiCall = retrofitInterface.getAvailableProfessionals(s);
        apiCall.enqueue(new Callback<List<ProfessionalModel>>() {
            @Override
            public void onResponse(Call<List<ProfessionalModel>> call, Response<List<ProfessionalModel>> response) {
                if (response.isSuccessful()) {
                    selectedPrice.setVisibility(View.INVISIBLE);
                    closeFilter.setVisibility(View.INVISIBLE);
                    professionals = response.body();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new AvailableProfessionalsAdapter(response.body(), getActivity()));

                }
            }

            @Override
            public void onFailure(Call<List<ProfessionalModel>> call, Throwable t) {
                Snackbar.make(constraintLayout, "Intenet Problem, Please try again later", Snackbar.LENGTH_SHORT).show();
            }
        });


    }


}
