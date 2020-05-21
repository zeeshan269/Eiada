package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.SlotsAdapter;
import com.eiadatech.eiada.Fragments.MakeAppointment.BookingConfirmationFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.SlotModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.SlotsAdapter.selectedSlot;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingDateFragment extends Fragment {
    private CalendarView calender;
    public static Button btnBook;
    private String selctedDate = "";
    private RecyclerView slotsList;
    public static String date = "";

    public BookingDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_date, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        calender = view.findViewById(R.id.calendarView);
        btnBook = view.findViewById(R.id.button13);
        slotsList = view.findViewById(R.id.recyclerView);

        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        selctedDate = mYear + "-" + (mMonth + 1) + "-" + mDay;

        String monthName = getMonthForInt(mMonth);
        btnBook.setText("Book " + monthName + " " + mDay + ", " + mYear);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String monthName = getMonthForInt(month);
                String mMonth = String.valueOf(month + 1);
                String day = String.valueOf(dayOfMonth);
                if (day.length() == 1) {
                    day = "0" + day;
                }

                if (mMonth.length() == 1) {
                    mMonth = "0" + mMonth;
                }

                selctedDate = year + "-" + (mMonth) + "-" + day;
                getSlotsByDate(selctedDate, appointment.getProfessionalId());
                date = "Book " + monthName + " " + dayOfMonth + ", " + year;

            }
        });


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedSlot == null) {
                    Toast.makeText(getActivity(), "Please select slot", Toast.LENGTH_SHORT).show();
                } else if (selectedSlot.getStatus().equalsIgnoreCase("available")) {
                    appointment.setBookingDate(selctedDate);
                    appointment.setBookingSlotTimings(selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime());
                    appointment.setBookingSlotId(selectedSlot.getSlotId());
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new BookingConfirmationFragment()).commit();
                }
            }
        });


    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }


    private void getSlotsByDate(final String date, String professionId) {
        SlotModel slotModel = new SlotModel();
        slotModel.setDate(date);
        slotModel.setProfessionalId(professionId);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<SlotModel>> api = retrofitInterface.getSlotsByDate(slotModel);

        api.enqueue(new Callback<List<SlotModel>>() {
            @Override
            public void onResponse(Call<List<SlotModel>> call, Response<List<SlotModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    slotsList.setLayoutManager(layoutManager);
                    slotsList.setItemAnimator(new DefaultItemAnimator());
                    slotsList.setAdapter(new SlotsAdapter(response.body(), getActivity()));


                }
            }

            @Override
            public void onFailure(Call<List<SlotModel>> call, Throwable t) {

            }
        });
    }


}
