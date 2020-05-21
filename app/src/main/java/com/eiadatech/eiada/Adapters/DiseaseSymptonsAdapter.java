package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.SymptonsModel;

import java.util.List;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

public class DiseaseSymptonsAdapter extends RecyclerView.Adapter<DiseaseSymptonsAdapter.ViewHolder> {

    private List<SymptonsModel> symptons;
    private Context context;
    private String type;
    private SymptonsModel selectedName;

    int index = -1;

    public DiseaseSymptonsAdapter(List<SymptonsModel> symptons, Context context, String type) {
        this.symptons = symptons;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public DiseaseSymptonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sympton_row, parent, false);
        return new DiseaseSymptonsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseSymptonsAdapter.ViewHolder holder, int position) {
        SymptonsModel sympton = symptons.get(position);
        holder.name.setText(sympton.getName());

        if (sympton.getCategory().equalsIgnoreCase("11")) {
            holder.category.setText("Feeling Sick/Infections/Injuries");
        } else if (sympton.getCategory().equalsIgnoreCase("12")) {
            holder.category.setText("Mental Health, Counselling and Wellness");
        } else if (sympton.getCategory().equalsIgnoreCase("13")) {
            holder.category.setText("Naturopathic Medicine");
        } else if (sympton.getCategory().equalsIgnoreCase("14")) {
            holder.category.setText("Ongoing (Chronic) Conditions");
        } else if (sympton.getCategory().equalsIgnoreCase("15")) {
            holder.category.setText("Skin Issues");
        } else if (sympton.getCategory().equalsIgnoreCase("16")) {
            holder.category.setText("Travel Health");
        }else if(sympton.getCategory().equalsIgnoreCase("21")){
            holder.category.setText("Nursing Care");
        }else if(sympton.getCategory().equalsIgnoreCase("31")){
            holder.category.setText("Therapists");
        }else if(sympton.getCategory().equalsIgnoreCase("41")){
            holder.category.setText("Veterinarian");
        }else if(sympton.getCategory().equalsIgnoreCase("51")){
            holder.category.setText("Laboratory");
        }

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });


        if (index == position) {
            appointment.setSympton(symptons.get(position).getName());
            appointment.setSymptonCategory(symptons.get(position).getCategory());
            appointment.setSymptonId(symptons.get(position).getSymptonId());
            holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.home_box_selected_background));

        } else {
            holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.disease_background));
        }


    }

    @Override
    public int getItemCount() {
        return symptons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, category;
        public ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView62);
            category = itemView.findViewById(R.id.textView66);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);


        }
    }

}
