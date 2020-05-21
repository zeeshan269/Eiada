package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.SelectPatientAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;

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
public class SelectPatientFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView previous;
    public static TextView next;

    public SelectPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_patient, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerview);
        previous = view.findViewById(R.id.textView64);
        next = view.findViewById(R.id.textView145);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment.getRelation() != null) {
                    if (appointment.getProfessionId().equalsIgnoreCase("1")) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new DoctorsFacilitiesFragment())
                                .commit();
                    } else if (appointment.getProfessionId().equalsIgnoreCase("2")) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new NurseFacilitiesFragment())
                                .commit();
                    } else if (appointment.getProfessionId().equalsIgnoreCase("3")) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new TherapistsFacilitiesFragment())
                                .commit();
                    } else if (appointment.getProfessionId().equalsIgnoreCase("4")) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new AnimalCareFacilitiesFragment())
                                .commit();
                    } else if (appointment.getProfessionId().equalsIgnoreCase("5")) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new LaboratoryFacilitiesFragment())
                                .commit();
                    }


                }
            }
        });

        getFamilyMembers(Session.getUser_ID(getActivity()));

    }


    private void getFamilyMembers(String userId) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<FamilyMemberModel>> apiCall = retrofitInterface.getFamilyMembers(new PatientModel(userId));

        apiCall.enqueue(new Callback<List<FamilyMemberModel>>() {
            @Override
            public void onResponse(Call<List<FamilyMemberModel>> call, Response<List<FamilyMemberModel>> response) {
                if (response.isSuccessful()) {

                    List<FamilyMemberModel> familyMembers = new ArrayList<>();

                    FamilyMemberModel familyMember = new FamilyMemberModel();
                    familyMember.setAge("");
                    familyMember.setName(Session.getUser_Name(getActivity()));
                    familyMember.setGender(Session.getUser_Gender(getActivity()));
                    familyMember.setRelation("MySelf");
                    familyMembers.add(familyMember);
                    for (int i = 0; i < response.body().size(); i++) {
                        familyMembers.add(response.body().get(i));
                    }

                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new SelectPatientAdapter(getActivity(), familyMembers));
                }
            }

            @Override
            public void onFailure(Call<List<FamilyMemberModel>> call, Throwable t) {


            }
        });
    }

}
