package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.TimeAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.SlotModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.get12FormatTime;
import static com.eiadatech.eiada.Adapters.AppointmentAdapter.getMonthName;
import static com.eiadatech.eiada.Adapters.TimeAdapter.selectedSlotId;
import static com.eiadatech.eiada.Adapters.TimeAdapter.selectedSlotTime;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectAppointmentDateTimeFragment extends BottomSheetDialogFragment {

    private TextView textViewMonth;
    private Button btnConfirm;
    private ImageView btnCancel;
    private String selectedDate = "";
    private RecyclerView recyclerViewTimes;
    public static String selectedTimeType = "pm";
    public static String selectedStartTime = "", selectedEndTime = "";

    private List<SlotModel> allSlots = new ArrayList<>();

    HorizontalCalendar horizontalCalendar;
    int dateSelection = 0;

    public SelectAppointmentDateTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_appointment_date_time, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        textViewMonth = view.findViewById(R.id.textView16);
        btnConfirm = view.findViewById(R.id.button32);
        btnCancel = view.findViewById(R.id.imageView102);
        recyclerViewTimes = view.findViewById(R.id.recyclerView3);

        String date = getCurrentDate().substring(Math.max(getCurrentDate().length() - 5, 0));
        String month = date.substring(0, Math.min(date.length(), 2));
        String year = getCurrentDate().substring(0, Math.min(getCurrentDate().length(), 4));
        String day = date.substring(Math.max(date.length() - 2, 0));
//
//        String monthName = getMonthName(month);
//        String dayName = getDayName(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year));
//        textViewDate.setText(dayName + ", " + monthName + " " + day + ", " + year);


        final Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 96);
        final Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .startDate(startDate.getTime())

                .endDate(endDate.getTime())
                .datesNumberOnScreen(7)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .selectedDateBackground(getActivity().getResources().getDrawable(R.drawable.button_background))
                .showMonthName(false)
                .defaultSelectedDate(new Date())


                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                String pattern = "yyyy-MM";
                String pattern1 = "dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
                Integer selectedDateOnly;
                String selectedMonthYear;
                if (dateSelection == 0) {
                    selectedDateOnly = Integer.valueOf(simpleDateFormat1.format(date));
                    selectedMonthYear = simpleDateFormat.format(date);
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 1); //minus number would decrement the days
                    selectedDateOnly = Integer.valueOf(simpleDateFormat1.format(cal.getTime()));


                    selectedMonthYear = simpleDateFormat.format(cal.getTime());
                }
                dateSelection++;


                String dateOnly = String.valueOf(selectedDateOnly);

                if (dateOnly.length() == 1) {
                    dateOnly = "0" + dateOnly;
                }


                String month = selectedMonthYear.substring(Math.max(selectedMonthYear.length() - 2, 0));
                String year = selectedMonthYear.substring(0, Math.min(selectedMonthYear.length(), 4));

                textViewMonth.setText(getMonthName(month) + ", " + year);

                selectedDate = selectedMonthYear + "-" + dateOnly;

                getSlots(selectedDate, appointment.getProfessionalId());
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedSlotTime == null) {

                        } else {
                            appointment.setBookingSlotTimings(selectedSlotTime);
                            appointment.setBookingSlotId(selectedSlotId);
                            appointment.setBookingDate(selectedDate);
                            dismiss();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new BookingConfirmationFragment()).commit();
                        }
                    }
                });

            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }


    private void getSlots(String date, String professionalId) {
        SlotModel slotModel = new SlotModel();
        slotModel.setDate(date);
        slotModel.setProfessionalId(professionalId);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<SlotModel>> api = retrofitInterface.getSlotsByDate(slotModel);


        api.enqueue(new Callback<List<SlotModel>>() {
            @Override
            public void onResponse(Call<List<SlotModel>> call, Response<List<SlotModel>> response) {
                if (response.isSuccessful()) {
                    allSlots = response.body();


                    List<String> availableSlots = new ArrayList<>();
                    List<String> slotIds = new ArrayList<>();
                    for (int i = 0; i < allSlots.size(); i++) {

                        String startTime = allSlots.get(i).getStartTime().substring(0, Math.min(allSlots.get(i).getStartTime().length(), 5));
                        String currentTime = getCurrentTime();


                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        Date inTime = null;
                        try {
                            inTime = sdf.parse(startTime);
                            Date outTime = sdf.parse(currentTime);

                            if (outTime.compareTo(inTime) < 0) {
                                String time = get12FormatTime(startTime);
                                String availableTime = time.substring(0, Math.min(time.length(), 5));

                                String format = time.substring(Math.max(time.length() - 2, 0));


                                availableSlots.add(availableTime + " " + format);
                                slotIds.add(allSlots.get(i).getSlotId());
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }





                    }

                    recyclerViewTimes.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                    recyclerViewTimes.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewTimes.setAdapter(new TimeAdapter(availableSlots, slotIds, getActivity()));

                }
            }

            @Override
            public void onFailure(Call<List<SlotModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error while fetching slots", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String getDayName(int day, int month, int year) {
        Calendar myCalendar;
        myCalendar = new GregorianCalendar(year, month, day);
        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
        String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        String dayName = strDays[dayOfWeek - 1];
        return dayName;
    }

    private String getCurrentDate() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String format = s.format(new Date());
        return format;
    }

    private String getCurrentTime() {
        final Calendar calendar = Calendar.getInstance();

        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinutes = calendar.get(Calendar.MINUTE);

        String hours = String.valueOf(mHour);
        String minutes = String.valueOf(mMinutes);

        if (hours.length() == 1) {
            hours = "0" + hours;
        }

        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        return hours + ":" + minutes;
    }

}
