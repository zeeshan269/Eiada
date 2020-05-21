package com.eiadatech.eiada.Fragments.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.eiadatech.eiada.Fragments.Appointments.CompletedAppointmentDetailsFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.selectedAppointment;

public class EvaluationBottomSheet extends BottomSheetDialogFragment {

    private String rating = "";
    private TextView rate1, rate2, rate3, rate4, rate5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.evaluation_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        rate1 = view.findViewById(R.id.textView56);
        rate2 = view.findViewById(R.id.textView57);
        rate3 = view.findViewById(R.id.textView60);
        rate4 = view.findViewById(R.id.textView61);
        rate5 = view.findViewById(R.id.textView63);
        EditText comments = view.findViewById(R.id.editText17);
        ImageView cancel = view.findViewById(R.id.imageView148);
        Button btnConfirm = view.findViewById(R.id.button20);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if (rating.isEmpty()) {
                    Toast.makeText(((FragmentActivity) CompletedAppointmentDetailsFragment.completedAppointContext), "Please rate the service", Toast.LENGTH_SHORT).show();
                } else {
                    giveReview(comments.getText().toString(), rating);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "1";
                setRatings(view, 1);
            }
        });

        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "2";
                setRatings(view, 2);
            }
        });

        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "3";
                setRatings(view, 3);
            }
        });

        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "4";
                setRatings(view, 4);
            }
        });

        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "5";
                setRatings(view, 5);
            }
        });


    }


    private void setRatings(View view, int rating) {

        ImageView unselected5Star1 = view.findViewById(R.id.imageView138);
        ImageView unselected5Star2 = view.findViewById(R.id.imageView139);
        ImageView unselected5Star3 = view.findViewById(R.id.imageView140);
        ImageView unselected5Star4 = view.findViewById(R.id.imageView141);
        ImageView unselected5Star5 = view.findViewById(R.id.imageView142);

        ImageView unselected4Star1 = view.findViewById(R.id.imageView130);
        ImageView unselected4Star2 = view.findViewById(R.id.imageView131);
        ImageView unselected4Star3 = view.findViewById(R.id.imageView132);
        ImageView unselected4Star4 = view.findViewById(R.id.imageView133);

        ImageView unselected3Star1 = view.findViewById(R.id.imageView124);
        ImageView unselected3Star2 = view.findViewById(R.id.imageView125);
        ImageView unselected3Star3 = view.findViewById(R.id.imageView126);

        ImageView unselected2Star1 = view.findViewById(R.id.imageView122);
        ImageView unselected2Star2 = view.findViewById(R.id.imageView123);

        ImageView unselected1Star = view.findViewById(R.id.imageView119);


        if (rating == 1) {

            unselected1Star.setVisibility(View.VISIBLE);

            unselected2Star1.setVisibility(View.INVISIBLE);
            unselected2Star2.setVisibility(View.INVISIBLE);

            unselected3Star1.setVisibility(View.INVISIBLE);
            unselected3Star2.setVisibility(View.INVISIBLE);
            unselected3Star3.setVisibility(View.INVISIBLE);

            unselected4Star1.setVisibility(View.INVISIBLE);
            unselected4Star2.setVisibility(View.INVISIBLE);
            unselected4Star3.setVisibility(View.INVISIBLE);
            unselected4Star4.setVisibility(View.INVISIBLE);

            unselected5Star1.setVisibility(View.INVISIBLE);
            unselected5Star2.setVisibility(View.INVISIBLE);
            unselected5Star3.setVisibility(View.INVISIBLE);
            unselected5Star4.setVisibility(View.INVISIBLE);
            unselected5Star5.setVisibility(View.INVISIBLE);

        } else if (rating == 2) {
            unselected2Star1.setVisibility(View.VISIBLE);
            unselected2Star2.setVisibility(View.VISIBLE);

            unselected3Star1.setVisibility(View.INVISIBLE);
            unselected3Star2.setVisibility(View.INVISIBLE);
            unselected3Star3.setVisibility(View.INVISIBLE);

            unselected4Star1.setVisibility(View.INVISIBLE);
            unselected4Star2.setVisibility(View.INVISIBLE);
            unselected4Star3.setVisibility(View.INVISIBLE);
            unselected4Star4.setVisibility(View.INVISIBLE);

            unselected5Star1.setVisibility(View.INVISIBLE);
            unselected5Star2.setVisibility(View.INVISIBLE);
            unselected5Star3.setVisibility(View.INVISIBLE);
            unselected5Star4.setVisibility(View.INVISIBLE);
            unselected5Star5.setVisibility(View.INVISIBLE);

            unselected1Star.setVisibility(View.INVISIBLE);

        } else if (rating == 3) {
            unselected3Star1.setVisibility(View.VISIBLE);
            unselected3Star2.setVisibility(View.VISIBLE);
            unselected3Star3.setVisibility(View.VISIBLE);

            unselected4Star1.setVisibility(View.INVISIBLE);
            unselected4Star2.setVisibility(View.INVISIBLE);
            unselected4Star3.setVisibility(View.INVISIBLE);
            unselected4Star4.setVisibility(View.INVISIBLE);

            unselected5Star1.setVisibility(View.INVISIBLE);
            unselected5Star2.setVisibility(View.INVISIBLE);
            unselected5Star3.setVisibility(View.INVISIBLE);
            unselected5Star4.setVisibility(View.INVISIBLE);
            unselected5Star5.setVisibility(View.INVISIBLE);

            unselected2Star1.setVisibility(View.INVISIBLE);
            unselected2Star2.setVisibility(View.INVISIBLE);


            unselected1Star.setVisibility(View.INVISIBLE);

        } else if (rating == 4) {
            unselected4Star1.setVisibility(View.VISIBLE);
            unselected4Star2.setVisibility(View.VISIBLE);
            unselected4Star3.setVisibility(View.VISIBLE);
            unselected4Star4.setVisibility(View.VISIBLE);

            unselected5Star1.setVisibility(View.INVISIBLE);
            unselected5Star2.setVisibility(View.INVISIBLE);
            unselected5Star3.setVisibility(View.INVISIBLE);
            unselected5Star4.setVisibility(View.INVISIBLE);
            unselected5Star5.setVisibility(View.INVISIBLE);

            unselected3Star1.setVisibility(View.INVISIBLE);
            unselected3Star2.setVisibility(View.INVISIBLE);
            unselected3Star3.setVisibility(View.INVISIBLE);

            unselected2Star1.setVisibility(View.INVISIBLE);
            unselected2Star2.setVisibility(View.INVISIBLE);


            unselected1Star.setVisibility(View.INVISIBLE);


        } else if (rating == 5) {
            unselected5Star1.setVisibility(View.VISIBLE);
            unselected5Star2.setVisibility(View.VISIBLE);
            unselected5Star3.setVisibility(View.VISIBLE);
            unselected5Star4.setVisibility(View.VISIBLE);
            unselected5Star5.setVisibility(View.VISIBLE);

            unselected4Star1.setVisibility(View.INVISIBLE);
            unselected4Star2.setVisibility(View.INVISIBLE);
            unselected4Star3.setVisibility(View.INVISIBLE);
            unselected4Star4.setVisibility(View.INVISIBLE);

            unselected3Star1.setVisibility(View.INVISIBLE);
            unselected3Star2.setVisibility(View.INVISIBLE);
            unselected3Star3.setVisibility(View.INVISIBLE);

            unselected2Star1.setVisibility(View.INVISIBLE);
            unselected2Star2.setVisibility(View.INVISIBLE);


            unselected1Star.setVisibility(View.INVISIBLE);
        }

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
                    dismiss();


                    final ConfirmTermination bottomSheetDialog = new ConfirmTermination();
                    bottomSheetDialog.show(((FragmentActivity) CompletedAppointmentDetailsFragment.completedAppointContext).getSupportFragmentManager(), "terminationBottomSheet");

                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {

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
