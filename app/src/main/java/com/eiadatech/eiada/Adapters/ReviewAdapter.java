package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.getMonthName;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewModel> reviews;
    private Context context;

    public ReviewAdapter(List<ReviewModel> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_row, parent, false);
        return new ReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        ReviewModel review = reviews.get(position);
        holder.name.setText(review.getPatientName());

        String date = review.getDate().substring(Math.max(review.getDate().length() - 5, 0));
        String month = date.substring(0, Math.min(date.length(), 2));
        String day = date.substring(Math.max(date.length() - 2, 0));
        holder.date.setText(getMonthName(month) + " " + day);

        if (!review.getComment().equalsIgnoreCase("")) {
            holder.comments.setText(review.getComment());
        } else {
            holder.comments.setText("No Comments");
        }
        holder.rating.setText(review.getReview() + ".0");

        if (reviews.get(position).getPatientImage() != null) {
            Picasso.get().load(reviews.get(position).getPatientImage()).into(holder.patientImage);
        }else {
            holder.patientImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultu));
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView date;
        public TextView comments;
        public TextView rating;

        public CircleImageView patientImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView133);
            comments = itemView.findViewById(R.id.textView135);
            date = itemView.findViewById(R.id.textView134);
            rating = itemView.findViewById(R.id.textView17);

            patientImage = itemView.findViewById(R.id.profileCircleImageView);

        }
    }
}
