package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.Appointments.CompletedAppointmentDetailsFragment;
import com.eiadatech.eiada.Fragments.Appointments.ImplementingAppointmentDetailsFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<AppointmentModel> appointments;
    private Context context;
    public static AppointmentModel selectedAppointment;
    private String type;

    public AppointmentAdapter(List<AppointmentModel> appointments, Context context, String type) {
        this.appointments = appointments;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row, parent, false);
        return new AppointmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        AppointmentModel appointmentModel = appointments.get(position);
        holder.name.setText(appointmentModel.getProfessionalName());
        holder.specialist.setText(appointmentModel.getSpecialistName());
        holder.location.setText(appointmentModel.getBookingAddress());

        holder.status.setText(appointmentModel.getStatus());
        holder.fees.setText(appointmentModel.getFees());

        if(appointmentModel.getStatus().equalsIgnoreCase("pending") ){
            holder.processingLabel.setText("Awaiting for Acceptance");
            holder.status.setText("Pending");
        }


        if(appointmentModel.getStatus().equalsIgnoreCase("Reached")){
            holder.statusIcon.setImageDrawable(context.getDrawable(R.drawable.location));
            holder.statusIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
        }else if(appointmentModel.getStatus().equalsIgnoreCase("Approved")){
            holder.status.setText("Accepted");
            holder.statusIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
            holder.statusIcon.setImageDrawable(context.getDrawable(R.drawable.approval));
        }else if(appointmentModel.getStatus().equalsIgnoreCase("on the way")){
            holder.statusIcon.setImageDrawable(context.getDrawable(R.drawable.hourglass));

        }



        String date = appointmentModel.getBookingDate().substring(Math.max(appointmentModel.getBookingDate().length() - 5, 0));
        String month = date.substring(0, Math.min(date.length(), 2));
        String day = date.substring(Math.max(date.length() - 2, 0));
        holder.date.setText(getMonthName(month) + " " + day);

        String time = appointmentModel.getBookingSlotTimings().substring(0, Math.min(date.length(), 5));
        holder.time.setText(get12FormatTime(time));

        if (appointments.get(position).getProfessionalImage() != null) {
            Picasso.get().load(appointments.get(position).getProfessionalImage()).into(holder.professionalImage);
        } else {
            holder.professionalImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultu));
        }

        if(type.equalsIgnoreCase("completed")){
            holder.status.setVisibility(View.GONE);
            holder.processingLabel.setVisibility(View.GONE);
            holder.processingIcon.setVisibility(View.GONE);
            holder.statusIcon.setVisibility(View.GONE);
            holder.horizontalLine.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView specialist;
        public TextView date;
        public TextView time;
        public TextView location;
        public TextView status;
        public TextView fees;
        public TextView processingLabel;
        public TextView horizontalLine;
        public CircleImageView professionalImage;
        public ImageView processingIcon;
        public ImageView statusIcon;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView8);
            specialist = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.textView137);
            time = itemView.findViewById(R.id.textView136);
            location = itemView.findViewById(R.id.textView10);
            status = itemView.findViewById(R.id.textView107);
            fees = itemView.findViewById(R.id.textView141);
            professionalImage = itemView.findViewById(R.id.profileCircleImageView);
            processingIcon = itemView.findViewById(R.id.imageView40);
            statusIcon = itemView.findViewById(R.id.imageView41);
            processingLabel = itemView.findViewById(R.id.textView104);
            horizontalLine = itemView.findViewById(R.id.textView106);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAppointment = appointments.get(getAdapterPosition());
                    if(!type.equalsIgnoreCase("completed")) {
                        ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new ImplementingAppointmentDetailsFragment())
                                .commit();
                    }else{
                        ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.frame, new CompletedAppointmentDetailsFragment())
                                .commit();
                    }
                }
            });
        }
    }

    public static String get12FormatTime(String startTime) {
        int startHour = Integer.parseInt(startTime.substring(0, Math.min(startTime.length(), 2)));
        String startMinute = startTime.substring(Math.max(startTime.length() - 2, 0));

        int _startHour;
        if (startHour % 12 == 0) {
            _startHour = 12;
        } else {
            _startHour = startHour % 12;
        }

        return _startHour + ":" + startMinute + "" + ((startHour >= 12) ? " PM" : " AM");

    }

    public static String getMonthName(String month) {
        String name = "";
        if (month.equalsIgnoreCase("1") || month.equalsIgnoreCase("01")) {
            name = "January";
        } else if (month.equalsIgnoreCase("2") || month.equalsIgnoreCase("02")) {
            name = "February";
        } else if (month.equalsIgnoreCase("3") || month.equalsIgnoreCase("03")) {
            name = "March";
        } else if (month.equalsIgnoreCase("4") || month.equalsIgnoreCase("04")) {
            name = "April";
        } else if (month.equalsIgnoreCase("5") || month.equalsIgnoreCase("05")) {
            name = "May";
        } else if (month.equalsIgnoreCase("6") || month.equalsIgnoreCase("06")) {
            name = "June";
        } else if (month.equalsIgnoreCase("7") || month.equalsIgnoreCase("07")) {
            name = "July";
        } else if (month.equalsIgnoreCase("8") || month.equalsIgnoreCase("08")) {
            name = "August";
        } else if (month.equalsIgnoreCase("9") || month.equalsIgnoreCase("09")) {
            name = "September";
        } else if (month.equalsIgnoreCase("10") || month.equalsIgnoreCase("10")) {
            name = "October";
        } else if (month.equalsIgnoreCase("11") || month.equalsIgnoreCase("11")) {
            name = "November";
        } else if (month.equalsIgnoreCase("12") || month.equalsIgnoreCase("12")) {
            name = "December";
        }
        return name;
    }

}
