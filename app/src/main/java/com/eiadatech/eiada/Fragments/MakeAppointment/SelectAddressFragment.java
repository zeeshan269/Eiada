package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.BookingAddressAdapter;
import com.eiadatech.eiada.Fragments.AddBookingAddressFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.BookingAddressModel;
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
public class SelectAddressFragment extends Fragment {
    private ConstraintLayout btnAddBookingAddress;
    private RecyclerView bookingAddressList;
    private ConstraintLayout constraintLayout;

    public SelectAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_address, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        btnAddBookingAddress = view.findViewById(R.id.addBookingAddress);
        bookingAddressList = view.findViewById(R.id.bookingAddressList);
        constraintLayout = view.findViewById(R.id.constraintLayout);



        btnAddBookingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new AddBookingAddressFragment()).commit();
            }
        });



        getBookingAddresses(Session.getUser_ID(getActivity()));
    }

    private void getBookingAddresses(String userId) {
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<BookingAddressModel>> apiCall = retrofitInterface.getBookingAddresses(new PatientModel(userId));

        apiCall.enqueue(new Callback<List<BookingAddressModel>>() {
            @Override
            public void onResponse(Call<List<BookingAddressModel>> call, Response<List<BookingAddressModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    bookingAddressList.setLayoutManager(layoutManager);
                    bookingAddressList.setItemAnimator(new DefaultItemAnimator());
                    bookingAddressList.setAdapter(new BookingAddressAdapter(getActivity(), response.body(), "select"));

                }
            }

            @Override
            public void onFailure(Call<List<BookingAddressModel>> call, Throwable t) {
                Snackbar.make(constraintLayout, "Internet Problem Please try later", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

}
