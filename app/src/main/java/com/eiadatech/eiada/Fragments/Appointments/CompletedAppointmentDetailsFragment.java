package com.eiadatech.eiada.Fragments.Appointments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.ConfirmationBottomSheet;
import com.eiadatech.eiada.Fragments.ViewReportFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.ReportModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Adapters.AppointmentAdapter.getMonthName;
import static com.eiadatech.eiada.Adapters.AppointmentAdapter.selectedAppointment;
import static com.eiadatech.eiada.Adapters.ReportAdapter.selectedReport;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedAppointmentDetailsFragment extends Fragment {

    private TextView name, specialist, notes, rating, location, address, amount, patientName, date, comments;
    private FrameLayout frameLayout;
    private Button btnAwaitingImplementation;
    private ImageView btnBack;
    private ConstraintLayout constraintLayout, ratingBlock, dateBlock, medicalReport;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static Context completedAppointContext;


    public CompletedAppointmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_appointment_details, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        name = view.findViewById(R.id.textView216);
        specialist = view.findViewById(R.id.textView42);
        rating = view.findViewById(R.id.textView48);
        address = view.findViewById(R.id.textView47);
        amount = view.findViewById(R.id.textView234);
        patientName = view.findViewById(R.id.textView224);
        date = view.findViewById(R.id.textView50);
        location = view.findViewById(R.id.textView226);
        notes = view.findViewById(R.id.textView231);
        comments = view.findViewById(R.id.textView53);
        frameLayout = view.findViewById(R.id.frameLayout2);
        btnAwaitingImplementation = view.findViewById(R.id.button26);
        btnBack = view.findViewById(R.id.imageView95);
        constraintLayout = view.findViewById(R.id.constraintLayout26);
        ratingBlock = view.findViewById(R.id.constraintLayout17);
        dateBlock = view.findViewById(R.id.constraintLayout5);
        medicalReport = view.findViewById(R.id.constraintLayout21);

        completedAppointContext = getActivity();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        if (selectedAppointment.getAppointmentReview().equalsIgnoreCase("")) {
            constraintLayout.setVisibility(View.VISIBLE);
            ratingBlock.setVisibility(View.INVISIBLE);
            dateBlock.setVisibility(View.VISIBLE);
        } else {
            constraintLayout.setVisibility(View.GONE);
            ratingBlock.setVisibility(View.VISIBLE);
            dateBlock.setVisibility(View.GONE);

            comments.setText(selectedAppointment.getAppointmentComment());
            int patientReview = (int) Math.floor(Double.parseDouble(selectedAppointment.getAppointmentReview()));
            setAppointmentRatingStars(view, patientReview);
        }

        int professionalRating = (int) Math.floor(Double.parseDouble(selectedAppointment.getRating()));
        setRatingStars(view, professionalRating);

        medicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReports(Session.getUser_ID(getActivity()));
            }
        });


        try {
            if (selectedAppointment.getRating().length() != 1) {

                String ratingV = selectedAppointment.getRating().substring(0, Math.min(selectedAppointment.getRating().length(), 3));
                rating.setText(ratingV);
            } else {
                rating.setText(selectedAppointment.getRating());
            }
        } catch (NumberFormatException e) {
            rating.setText(selectedAppointment.getRating());
        }


        name.setText(selectedAppointment.getProfessionalName());

        specialist.setText(selectedAppointment.getSpecialistName());
        patientName.setText(selectedAppointment.getPatientName());
        address.setText(selectedAppointment.getBookingAddress());
        location.setText(selectedAppointment.getLocationName());
        amount.setText(selectedAppointment.getFees());

        notes.setText(selectedAppointment.getReason());

        String bookingDate = selectedAppointment.getBookingDate().substring(Math.max(selectedAppointment.getBookingDate().length() - 5, 0));
        String month = bookingDate.substring(0, Math.min(bookingDate.length(), 2));
        String day = bookingDate.substring(Math.max(bookingDate.length() - 2, 0));

        date.setText(getMonthName(month) + ", " + day);


        if (selectedAppointment.getProfessionalImage() != null) {
            Picasso.get().load(selectedAppointment.getProfessionalImage()).into(new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    frameLayout.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }


                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });
        }
        btnAwaitingImplementation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConfirmationBottomSheet bottomSheetDialog = new ConfirmationBottomSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "confirmationBottomSheet");

            }
        });

    }

    private void setAppointmentRatingStars(View view, int patientReview) {

        ImageView unselectedStar1 = view.findViewById(R.id.imageView113);
        ImageView unselectedStar2 = view.findViewById(R.id.imageView114);
        ImageView unselectedStar3 = view.findViewById(R.id.imageView115);
        ImageView unselectedStar4 = view.findViewById(R.id.imageView116);
        ImageView unselectedStar5 = view.findViewById(R.id.imageView117);

        if (patientReview == 1) {

            unselectedStar1.setVisibility(View.INVISIBLE);
        } else if (patientReview == 2) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
        } else if (patientReview == 3) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
        } else if (patientReview == 4) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
        } else if (patientReview == 5) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
            unselectedStar5.setVisibility(View.INVISIBLE);
        }
    }


    private void setRatingStars(View view, int rating) {

        ImageView unselectedStar1 = view.findViewById(R.id.imageView101);
        ImageView unselectedStar2 = view.findViewById(R.id.imageView99);
        ImageView unselectedStar3 = view.findViewById(R.id.imageView102);
        ImageView unselectedStar4 = view.findViewById(R.id.imageView100);
        ImageView unselectedStar5 = view.findViewById(R.id.imageView104);

        if (rating == 1) {

            unselectedStar1.setVisibility(View.INVISIBLE);
        } else if (rating == 2) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
        } else if (rating == 3) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
        } else if (rating == 4) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
        } else if (rating == 5) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
            unselectedStar5.setVisibility(View.INVISIBLE);
        }


    }

    private void getReports(String patientId) {
        PatientModel professionalModel = new PatientModel();
        professionalModel.setPatientId(patientId);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ReportModel>> apiCall = retrofitInterface.getReports(professionalModel);

        apiCall.enqueue(new Callback<List<ReportModel>>() {
            @Override
            public void onResponse(Call<List<ReportModel>> call, Response<List<ReportModel>> response) {

                ReportModel reportModel = null;
                for (int i = 0; i < response.body().size(); i++) {
                    if (selectedAppointment.getAppointmentId().equalsIgnoreCase(response.body().get(i).getAppointmentId())) {
                        reportModel = response.body().get(i);
                        break;
                    }
                }

                if (reportModel == null) {
                    Toast.makeText(getActivity(), "Report is not created yet!", Toast.LENGTH_LONG).show();
                } else {
                    selectedReport = reportModel;
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frame, new ViewReportFragment())
                            .commit();
                }


            }

            @Override
            public void onFailure(Call<List<ReportModel>> call, Throwable t) {

            }
        });
    }


}
