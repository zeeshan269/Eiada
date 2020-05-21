package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.ReviewAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter.selectedProfessional;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    private RecyclerView recyclerViewReviews;
    private ConstraintLayout constraintLayout;
    private TextView textViewUsers, textViewRating;
    private RatingBar ratingBar;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        recyclerViewReviews = view.findViewById(R.id.reviewsList);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        textViewUsers = view.findViewById(R.id.textView115);
        textViewRating = view.findViewById(R.id.textView114);
        ratingBar = view.findViewById(R.id.ratingBar);


        getProfessionalReviews(selectedProfessional.getProfessionalId());
    }


    private void getProfessionalReviews(String profesionalId) {

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setProfessionalId(profesionalId);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ReviewModel>> apiCall = retrofitInterface.getProfessionalReviews(reviewModel);

        apiCall.enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewReviews.setLayoutManager(layoutManager);
                recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
                recyclerViewReviews.setAdapter(new ReviewAdapter(response.body(), getActivity()));
                textViewUsers.setText(response.body().size() + "");
                if (response.body().size() == 0) {
                    ratingBar.setRating(5f);
                    textViewRating.setText("5.0");
                } else {
                    Float ratingValue = 0f;
                    for (int i = 0; i < response.body().size(); i++) {
                        ratingValue = ratingValue + Float.valueOf(response.body().get(i).getReview());
                    }

                    ratingValue = (ratingValue / (response.body().size()));


                    ratingBar.setRating(ratingValue);
                    textViewRating.setText(ratingValue + "");

                }
            }

            @Override
            public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
                Snackbar.make(constraintLayout, "Error while fetching the reviews", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
