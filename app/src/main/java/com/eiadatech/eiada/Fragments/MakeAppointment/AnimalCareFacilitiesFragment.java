package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.DiseaseSymptonsAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.SymptonsModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalCareFacilitiesFragment extends Fragment {


    private RecyclerView recyclerViewAnimalCare;
    private Button btnNext;
    private EditText searchDisease;
    private List<SymptonsModel> symptons = new ArrayList<>();

    public AnimalCareFacilitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_care_facilities, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        recyclerViewAnimalCare = view.findViewById(R.id.animalCareList);
        btnNext = view.findViewById(R.id.button9);
        searchDisease = view.findViewById(R.id.editText18);
        getProfessionSymptons("3");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment.getSymptonId() != null) {
                    final SymptonDescriptionFragment bottomSheetDialog = new SymptonDescriptionFragment();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "symptonDescriptionBottomSheet");

                }
            }
        });


        searchDisease.addTextChangedListener(new TextWatcher() {
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

                    List<SymptonsModel> searchedSymptons = new ArrayList<>();
                    for (int i = 0; i < symptons.size(); i++) {
                        if (symptons.get(i).getName().contains(searchValue)) {
                        searchedSymptons.add(symptons.get(i));
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewAnimalCare.setLayoutManager(layoutManager);
                    recyclerViewAnimalCare.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewAnimalCare.setAdapter(new DiseaseSymptonsAdapter(searchedSymptons, getActivity(), "31"));

                } else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewAnimalCare.setLayoutManager(layoutManager);
                    recyclerViewAnimalCare.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewAnimalCare.setAdapter(new DiseaseSymptonsAdapter(symptons, getActivity(), "31"));

                }

            }
        });


    }

    private void getProfessionSymptons(String professionId) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<SymptonsModel>> apiCall = retrofitInterface.getSymptonsByProfession(new SymptonsModel(professionId));

        apiCall.enqueue(new Callback<List<SymptonsModel>>() {
            @Override
            public void onResponse(Call<List<SymptonsModel>> call, Response<List<SymptonsModel>> response) {
                if (response.isSuccessful()) {

                    symptons = response.body();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewAnimalCare.setLayoutManager(layoutManager);
                    recyclerViewAnimalCare.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewAnimalCare.setAdapter(new DiseaseSymptonsAdapter(response.body(), getActivity(), "31"));

                }
            }

            @Override
            public void onFailure(Call<List<SymptonsModel>> call, Throwable t) {

            }
        });
    }

}
