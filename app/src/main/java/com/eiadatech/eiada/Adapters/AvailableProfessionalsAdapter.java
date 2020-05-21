package com.eiadatech.eiada.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.BookingDateFragment;
import com.eiadatech.eiada.Fragments.MakeAppointment.SelectAppointmentDateTimeFragment;
import com.eiadatech.eiada.Fragments.MakeAppointment.ViewAvailableProfessionalProfileFragment;
import com.eiadatech.eiada.Fragments.ReviewsFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ProfessionalModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;


public class AvailableProfessionalsAdapter extends RecyclerView.Adapter<AvailableProfessionalsAdapter.ViewHolder> {

    private List<ProfessionalModel> professionals;
    private Context context;
    public static ProfessionalModel selectedProfessional;


    public AvailableProfessionalsAdapter(List<ProfessionalModel> professionals, Context context) {
        this.professionals = professionals;
        this.context = context;
    }

    @NonNull
    @Override
    public AvailableProfessionalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_row, parent, false);
        return new AvailableProfessionalsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableProfessionalsAdapter.ViewHolder holder, int position) {
        ProfessionalModel professionalModel = professionals.get(position);
        holder.name.setText(professionalModel.getName());
        if (professionalModel.getAddress().equalsIgnoreCase("")) {
            holder.location.setText("Not Available");
        }else{
            holder.location.setText(professionalModel.getAddress());
        }
        //        holder.specialist.setText(professionalModel.getSpecialistName());
//        holder.languages.setText(professionalModel.getLanguage());
//        holder.ratingBar.setRating(Float.parseFloat(professionalModel.getRating()));

        if (professionalModel.getPrice() == null) {
            holder.price.setText("0 AED");
        } else {
            holder.price.setText(professionalModel.getPrice());
        }

        if (professionals.get(position).getProfileImage() != null) {
            Picasso.get().load(professionals.get(position).getProfileImage()).into(holder.doctorImage);
        }else {
            holder.doctorImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultu));
        }


        String ratingFirstDigit = professionalModel.getRating().substring(0, Math.min(professionalModel.getRating().length(), 1));


        if (ratingFirstDigit.equalsIgnoreCase("1")) {
            holder.rating1.setVisibility(View.INVISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("2")) {
            holder.rating1.setVisibility(View.INVISIBLE);
            holder.rating2.setVisibility(View.INVISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("3")) {
            holder.rating1.setVisibility(View.INVISIBLE);
            holder.rating2.setVisibility(View.INVISIBLE);
            holder.rating3.setVisibility(View.INVISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("4")) {
            holder.rating1.setVisibility(View.INVISIBLE);
            holder.rating2.setVisibility(View.INVISIBLE);
            holder.rating3.setVisibility(View.INVISIBLE);
            holder.rating4.setVisibility(View.INVISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("5")) {
            holder.rating1.setVisibility(View.INVISIBLE);
            holder.rating2.setVisibility(View.INVISIBLE);
            holder.rating3.setVisibility(View.INVISIBLE);
            holder.rating4.setVisibility(View.INVISIBLE);
            holder.rating5.setVisibility(View.INVISIBLE);
        }

        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfessional = professionals.get(position);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame, new ViewAvailableProfessionalProfileFragment())
                        .commit();
            }
        });

        holder.bookApppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfessional = professionals.get(position);
                appointment.setProfessionalId(professionals.get(position).getProfessionalId());
                appointment.setSpecialistName(professionals.get(position).getSpecialistName());
                appointment.setProfessionalName(professionals.get(position).getName());
                appointment.setProfessionalImage(professionals.get(position).getProfileImage());
                appointment.setFees(professionals.get(position).getFees());
                appointment.setProfessionalMobile(professionals.get(position).getMobile());
                appointment.setRating(professionals.get(position).getRating());

                SelectAppointmentDateTimeFragment bottomSheetDialog = new SelectAppointmentDateTimeFragment();
                bottomSheetDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "timingsBottomSheet");
            }
        });


//        if (professionalModel.getLanguage() == null) {
//            holder.languages.setText("Not available");
//        } else {
//            holder.languages.setText(professionalModel.getLanguage());
//        }

    }

    @Override
    public int getItemCount() {
        return professionals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView location;
        public CircleImageView doctorImage;
        public ImageView rating1;
        public ImageView rating2;
        public ImageView rating3;
        public ImageView rating4;
        public ImageView rating5;
        public Button viewProfile;
        public Button bookApppointment;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView8);
            viewProfile = itemView.findViewById(R.id.button14);
            price = itemView.findViewById(R.id.textView141);
            location = itemView.findViewById(R.id.textView10);
            doctorImage = itemView.findViewById(R.id.profileCircleImageView);
            rating1 = itemView.findViewById(R.id.imageView156);
            rating2 = itemView.findViewById(R.id.imageView157);
            rating3 = itemView.findViewById(R.id.imageView158);
            rating4 = itemView.findViewById(R.id.imageView159);
            rating5 = itemView.findViewById(R.id.imageView160);
            bookApppointment = itemView.findViewById(R.id.button15);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedProfessional = professionals.get(getLayoutPosition());
                    ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frame, new ViewAvailableProfessionalProfileFragment())
                            .commit();

                }
            });

        }
    }

    private void showDialog(final View itemView) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.professional_details_dialog);

        final TextView name = dialog.findViewById(R.id.textView92);
        final TextView specialist = dialog.findViewById(R.id.textView94);
        final TextView about = dialog.findViewById(R.id.textView96);
        final TextView fees = dialog.findViewById(R.id.textView98);
        final TextView experience = dialog.findViewById(R.id.textView100);
        final TextView university = dialog.findViewById(R.id.textView102);
        final Button cancel = dialog.findViewById(R.id.button15);
        final Button select = dialog.findViewById(R.id.button16);
        final TextView reviews = dialog.findViewById(R.id.textView132);
        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

        ratingBar.setRating(Float.parseFloat(selectedProfessional.getRating()));


        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame, new ReviewsFragment())
                        .commit();
            }
        });


        name.setText(selectedProfessional.getName());
        specialist.setText(selectedProfessional.getSpecialistName());
        if (selectedProfessional.getAbout() != null) {
            about.setText(selectedProfessional.getAbout());
        } else {
            about.setText("Not Available");
        }
        if (selectedProfessional.getFees() != null) {
            fees.setText(selectedProfessional.getFees());
        } else {
            fees.setText("0.00 AED");
        }
        if (selectedProfessional.getExperience() != null) {
            experience.setText(selectedProfessional.getExperience());
        } else {
            experience.setText("Not Available");
        }

        if (selectedProfessional.getUniversity() != null) {
            university.setText(selectedProfessional.getUniversity());
        } else {
            university.setText("Not Available");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                appointment.setProfessionalId(selectedProfessional.getProfessionalId());
                appointment.setSpecialistName(selectedProfessional.getSpecialistName());
                appointment.setSpecialistName(selectedProfessional.getSpecialistName());
                appointment.setFees(selectedProfessional.getFees());
                appointment.setProfessionalMobile(selectedProfessional.getMobile());

                ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame, new BookingDateFragment())
                        .commit();

            }
        });

        dialog.show();

    }


}
