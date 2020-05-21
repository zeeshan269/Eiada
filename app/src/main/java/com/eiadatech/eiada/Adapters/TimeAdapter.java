package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.MakeAppointment.SelectAppointmentDateTimeFragment;
import com.eiadatech.eiada.R;

import java.util.List;

import static com.eiadatech.eiada.Fragments.MakeAppointment.SelectAppointmentDateTimeFragment.selectedTimeType;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    private List<String> times;
    private List<String> slotIds;
    private Context context;
    private int index = -1;
    public static String selectedSlotTime;
    public static String selectedSlotId;

    public TimeAdapter(List<String> times,List<String> slotIds, Context context) {
        this.times = times;
        this.slotIds = slotIds;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_row, parent, false);
        return new TimeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeAdapter.ViewHolder holder, final int position) {

        holder.name.setText(times.get(position));


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.button_background));
                holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                notifyDataSetChanged();
            }
        });

        if (index == position) {

            getSlotTime(times.get(position));
            selectedSlotTime = times.get(position);
            selectedSlotId = slotIds.get(position);

            holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.button_background));
            holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.slot_background));
            holder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }


    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView255);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);


        }


    }

    private void getSlotTime(String slot) {


        if (slot.equals("1:00")) {

            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "01:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "02:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "13:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "14:00";
            }

        } else if (slot.equals("2:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "02:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "03:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "14:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "15:00";
            }

        } else if (slot.equals("3:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "03:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "04:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "15:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "16:00";
            }

        } else if (slot.equals("4:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "04:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "05:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "16:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "17:00";
            }

        } else if (slot.equals("5:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "05:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "06:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "17:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "18:00";
            }

        } else if (slot.equals("6:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "07:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "07:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "18:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "19:00";
            }

        } else if (slot.equals("7:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "07:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "08:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "19:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "20:00";
            }

        } else if (slot.equals("8:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "08:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "09:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "20:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "21:00";
            }

        } else if (slot.equals("9:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "09:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "10:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "21:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "22:00";
            }

        } else if (slot.equals("10:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "10:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "11:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "22:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "23:00";
            }

        } else if (slot.equals("11:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "11:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "12:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "23:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "00:00";
            }

        } else if (slot.equals("12:00")) {
            if (selectedTimeType.equals("am")) {
                SelectAppointmentDateTimeFragment.selectedStartTime = "12:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "13:00";
            } else {
                SelectAppointmentDateTimeFragment.selectedStartTime = "00:00";
                SelectAppointmentDateTimeFragment.selectedEndTime = "01:00";
            }

        }


    }


}
