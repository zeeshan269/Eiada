package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.AppointmentAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView recyclerViewAppointments;
    private Button btnProgress, btnCompleted;

    private CircleImageView patientImage;
    private TextView textViewName;

    private View rootView;

    private ImageView messages,notifications;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
          rootView = inflater.inflate(R.layout.fragment_history, container, false);
            initializeViews(rootView);
        }else{
            navigationView.setVisibility(View.VISIBLE);
        }
        return rootView;

    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }


    private void initializeViews(View view) {

        recyclerViewAppointments = view.findViewById(R.id.appointments);
        btnProgress = view.findViewById(R.id.button16);
        btnCompleted = view.findViewById(R.id.button18);
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


        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAppointments(Session.getUser_ID(getActivity()), "progress");
                btnProgress.setBackgroundResource(R.drawable.box_selected);
                btnProgress.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                btnCompleted.setBackgroundResource(R.drawable.box_unselected);
                btnCompleted.setTextColor(getActivity().getResources().getColor(R.color.themeColor));
            }
        });

        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAppointments(Session.getUser_ID(getActivity()), "completed");
                btnProgress.setBackgroundResource(R.drawable.box_unselected);
                btnProgress.setTextColor(getActivity().getResources().getColor(R.color.themeColor));
                btnCompleted.setBackgroundResource(R.drawable.box_selected);
                btnCompleted.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
            }
        });


        getAppointments(Session.getUser_ID(getActivity()), "progress");


    }

    private void getAppointments(String patientId, final String type) {
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<AppointmentModel>> apiCall = retrofitInterface.getAppointments(new PatientModel(patientId));

        apiCall.enqueue(new Callback<List<AppointmentModel>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {
                if (response.isSuccessful()) {

                    List<AppointmentModel> appointments = new ArrayList<>();


                    for (int i = 0; i < response.body().size(); i++) {

                        if (type.equalsIgnoreCase("completed")) {
                            if ((response.body().get(i).getStatus().equalsIgnoreCase("Completed") || response.body().get(i).getStatus().equalsIgnoreCase("Report Created"))) {
                                appointments.add(response.body().get(i));
                            }

                        } else {
                            if (response.body().get(i).getStatus().equalsIgnoreCase("pending") || response.body().get(i).getStatus().equalsIgnoreCase("Approved") || response.body().get(i).getStatus().equalsIgnoreCase("Reached") || response.body().get(i).getStatus().equalsIgnoreCase("on the way")) {
                                appointments.add(response.body().get(i));
                            }
                        }
                    }


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewAppointments.setLayoutManager(layoutManager);
                    recyclerViewAppointments.setItemAnimator(new DefaultItemAnimator());
                    if (type.equalsIgnoreCase("completed")) {
                        recyclerViewAppointments.setAdapter(new AppointmentAdapter(appointments, getActivity(), "completed"));

                    } else {
                        recyclerViewAppointments.setAdapter(new AppointmentAdapter(appointments, getActivity(), "pending"));

                    }

                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {

            }
        });


    }

}
