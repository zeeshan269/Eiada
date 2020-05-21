package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.FamilyMemberAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyMembersFragment extends Fragment {

    private Button btnAddFamilyMember;
    private RecyclerView familyMembersList;
    private RetrofitInterface retrofitInterface;
    private ConstraintLayout constraintLayout;

    public FamilyMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_members, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        btnAddFamilyMember = view.findViewById(R.id.button6);
        familyMembersList = view.findViewById(R.id.familyMembersList);
        constraintLayout = view.findViewById(R.id.constraintLayout);


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

        getFamilyMembers(Session.getUser_ID(getActivity()));

        btnAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new AddFamilyMemberFragment()).commit();
            }
        });
    }

    private void getFamilyMembers(String userId) {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<FamilyMemberModel>> apiCall = retrofitInterface.getFamilyMembers(new PatientModel(userId));

        apiCall.enqueue(new Callback<List<FamilyMemberModel>>() {
            @Override
            public void onResponse(Call<List<FamilyMemberModel>> call, Response<List<FamilyMemberModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    familyMembersList.setLayoutManager(layoutManager);
                    familyMembersList.setItemAnimator(new DefaultItemAnimator());
                    familyMembersList.setAdapter(new FamilyMemberAdapter(getActivity(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<FamilyMemberModel>> call, Throwable t) {
                Snackbar.make(constraintLayout, "Internet problem please try later!", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

}
