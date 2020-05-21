package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.selectedAppointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentDetailsFragment extends Fragment implements RatingDialogListener {

    private VerticalStepperFormLayout verticalStepperForm;
    private CircleImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView;
    private ImageView imageViewReview;
    private TextView textView1, textView2, textView3, textView4, textView5, textView, textViewReview;
    private ConstraintLayout constraintLayout;
    private boolean reviewSubmitted = true;

    public AppointmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_details, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {


        imageView1 = view.findViewById(R.id.profileCircleImageView);
        imageView2 = view.findViewById(R.id.profileCircleImageView1);
        imageView3 = view.findViewById(R.id.profileCircleImageView2);
        imageView4 = view.findViewById(R.id.profileCircleImageView3);
        imageView5 = view.findViewById(R.id.profileCircleImageView4);
        imageView = view.findViewById(R.id.profileCircleImageView5);
        imageViewReview = view.findViewById(R.id.imageView37);
        constraintLayout = view.findViewById(R.id.constraintLayout);


        textView1 = view.findViewById(R.id.textView122);
        textView2 = view.findViewById(R.id.textView123);
        textView3 = view.findViewById(R.id.textView124);
        textView4 = view.findViewById(R.id.textView125);
        textView5 = view.findViewById(R.id.textView126);
        textView = view.findViewById(R.id.textView129);
        textViewReview = view.findViewById(R.id.textView131);


        textViewReview.setOnClickListener(view1 -> {
            if (reviewSubmitted) {
                Snackbar.make(constraintLayout, "Review already submitted!", Snackbar.LENGTH_SHORT).show();
            } else {
                showDialog();
            }
        });
        imageViewReview.setOnClickListener(view12 -> {
            if (reviewSubmitted) {
                Snackbar.make(constraintLayout, "Review already submitted!", Snackbar.LENGTH_SHORT).show();
            } else {
                showDialog();
            }
        });

        getReview();

        if (selectedAppointment.getStatus().equalsIgnoreCase("pending")) {

            imageView1.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView2.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView3.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView4.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView5.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            textView1.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView2.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView3.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView4.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView5.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textViewReview.setVisibility(View.GONE);
            imageViewReview.setVisibility(View.GONE);

        } else if (selectedAppointment.getStatus().equalsIgnoreCase("rejected")) {
            imageView2.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView3.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView4.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView5.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            textView2.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView3.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView4.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView5.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textViewReview.setVisibility(View.GONE);
            imageViewReview.setVisibility(View.GONE);
        } else if (selectedAppointment.getStatus().equalsIgnoreCase("approved")) {
            imageView3.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView4.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView5.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            textView3.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView4.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView5.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textViewReview.setVisibility(View.GONE);
            imageViewReview.setVisibility(View.GONE);

        } else if (selectedAppointment.getStatus().equalsIgnoreCase("checked")) {
            imageView4.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            imageView5.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorGray));
            textView4.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textView5.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textViewReview.setVisibility(View.GONE);
            imageViewReview.setVisibility(View.GONE);
        } else if (selectedAppointment.getStatus().equalsIgnoreCase("Report In Progress")) {
            imageView5.setCircleBackgroundColor(getActivity().getResources().getColor(R.color.colorGray));
            textView5.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
            textViewReview.setVisibility(View.GONE);
            imageViewReview.setVisibility(View.GONE);
        } else if (selectedAppointment.getStatus().equalsIgnoreCase("Report Created")) {
            textViewReview.setVisibility(View.VISIBLE);
            imageViewReview.setVisibility(View.VISIBLE);
        }


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });


    }


    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not Good", "Quite Ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setTitle("Rate this Professional")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setStarColor(R.color.themeColor)
                .setNoteDescriptionTextColor(R.color.colorDarkGray)
                .setTitleTextColor(R.color.themeColor)
                .setDescriptionTextColor(R.color.contentTextColor)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.colorGray)
                .setCommentTextColor(R.color.colorDarkGray)
                .setCommentBackgroundColor(R.color.colorSilver)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(getActivity())
                .setTargetFragment(this, 11) // only if listener is implemented by fragment
                .show();
    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        giveReview(s, String.valueOf(i));
    }


    private void giveReview(String comment, String review) {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setReview(review);
        reviewModel.setComment(comment);
        reviewModel.setAppointmentId(selectedAppointment.getAppointmentId());
        reviewModel.setProfessionalId(selectedAppointment.getProfessionalId());
        reviewModel.setPatientId(selectedAppointment.getPatientId());
        reviewModel.setDate(getCurrentDate());

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<ReviewModel> api = retrofitInterface.giveReview(reviewModel);

        api.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(constraintLayout, "Review submitted Successfully!", Snackbar.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HistoryFragment()).commit();
                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                Snackbar.make(constraintLayout, "Error while submitting the review!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getReview() {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setAppointmentId(selectedAppointment.getAppointmentId());
        reviewModel.setProfessionalId(selectedAppointment.getProfessionalId());
        reviewModel.setPatientId(selectedAppointment.getPatientId());


        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<ReviewModel> api = retrofitInterface.getReview(reviewModel);

        api.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getAppointmentId().equalsIgnoreCase("")) {
                        reviewSubmitted = false;
                    } else if (response.body().getAppointmentId().equalsIgnoreCase(selectedAppointment.getAppointmentId())) {
                        reviewSubmitted = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                Snackbar.make(constraintLayout, "Error while getting the review!", Snackbar.LENGTH_SHORT).show();
                Log.d("Hello", t.getMessage());
            }
        });
    }

    private String getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        return mYear + "-" + (mMonth + 1) + "-" + mDay;
    }
}
