package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.MakeAppointment.SelectPatientFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;

import java.util.List;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

public class SelectPatientAdapter extends RecyclerView.Adapter<SelectPatientAdapter.ViewHolder> {

    private Context context;
    private List<FamilyMemberModel> familyMembers;

    int index = -1;

    public SelectPatientAdapter(Context context, List<FamilyMemberModel> familyMembers) {
        this.context = context;
        this.familyMembers = familyMembers;
    }

    @NonNull
    @Override
    public SelectPatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_patient_item, parent, false);
        return new SelectPatientAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull SelectPatientAdapter.ViewHolder holder, int position) {
        holder.name.setText(familyMembers.get(position).getRelation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }

        });

        if (index == position) {
            SelectPatientFragment.next.setBackgroundResource(R.drawable.theme_circle);
            appointment.setRelation(familyMembers.get(position).getName());
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.home_box_selected_background));
            holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));

        } else {
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.home_box_background_style));
            holder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }


        if (familyMembers.get(position).getRelation().equals("MySelf")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.myself));
        } else if (familyMembers.get(position).getRelation().equals("Spouse")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.housewife));
        } else if (familyMembers.get(position).getRelation().equals("Son")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.baby));
        } else if (familyMembers.get(position).getRelation().equals("Daughter")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.baby));
        } else if (familyMembers.get(position).getRelation().equals("Mother")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.grandmother));
        } else if (familyMembers.get(position).getRelation().equals("Father")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.grandfather));
        } else if (familyMembers.get(position).getRelation().equals("Brother")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.grandfather));
        } else if (familyMembers.get(position).getRelation().equals("Sister")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.housewife));
        } else if (familyMembers.get(position).getRelation().equals("Grandfather")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.grandfather));
        } else if (familyMembers.get(position).getRelation().equals("Grandmother")) {
            holder.gender.setBackground(context.getDrawable(R.drawable.grandmother));
        }else{
            holder.gender.setBackground(context.getDrawable(R.drawable.others));
        }

    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView gender;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView65);
            gender = itemView.findViewById(R.id.imageView150);


        }
    }

}
