package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.ReportAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.ReportModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment {

    private RecyclerView recyclerViewReports;

    private CircleImageView patientImage;
    private TextView textViewName;

    private ImageView messages,notifications;
    public ReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view){
        recyclerViewReports = view.findViewById(R.id.reportsList);

        navigationView.setVisibility(View.VISIBLE);

        patientImage = view.findViewById(R.id.profileCircleImageView);
        textViewName = view.findViewById(R.id.textView5);
        messages = view.findViewById(R.id.imageView28);
        notifications = view.findViewById(R.id.imageView35);

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

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ChatsFragment()).commit();

            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SettingsFragment()).commit();

            }
        });

        getReports(Session.getUser_ID(getActivity()));
    }


    private void getReports(String patientId) {
        PatientModel professionalModel = new PatientModel();
        professionalModel.setPatientId(patientId);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ReportModel>> apiCall = retrofitInterface.getReports(professionalModel);

        apiCall.enqueue(new Callback<List<ReportModel>>() {
            @Override
            public void onResponse(Call<List<ReportModel>> call, Response<List<ReportModel>> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewReports.setLayoutManager(layoutManager);
                recyclerViewReports.setItemAnimator(new DefaultItemAnimator());
                recyclerViewReports.setAdapter(new ReportAdapter(response.body(), getActivity()));

            }

            @Override
            public void onFailure(Call<List<ReportModel>> call, Throwable t) {

            }
        });
    }


}
