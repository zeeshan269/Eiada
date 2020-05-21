package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.BottomSheets.DeleteFamilyMemberConfirmationSheet;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;

import java.util.Calendar;
import java.util.List;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.ViewHolder> {

    private Context context;
    private List<FamilyMemberModel> familyMembers;
    public static FamilyMemberModel selectedFamily;

    public FamilyMemberAdapter(Context context, List<FamilyMemberModel> familyMembers) {
        this.context = context;
        this.familyMembers = familyMembers;
    }

    @NonNull
    @Override
    public FamilyMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.family_member_row, parent, false);
        return new FamilyMemberAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberAdapter.ViewHolder holder, int position) {
        FamilyMemberModel familyMember = familyMembers.get(position);
        holder.name.setText(familyMember.getName());
        holder.relation.setText(familyMember.getRelation());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFamily = familyMembers.get(position);

                final DeleteFamilyMemberConfirmationSheet bottomSheetDialog = new DeleteFamilyMemberConfirmationSheet();
                bottomSheetDialog.show(((FragmentActivity) context).getSupportFragmentManager(),"evaluationBottomSheet");
            }
        });

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        if (familyMember.getAge().equalsIgnoreCase(year)) {
            holder.age.setText("0 years");
        } else {
            holder.age.setText(familyMember.getAge() + " years");
        }


    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView relation;
        public ImageView gender;
        public ImageView delete;
        public TextView age;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView26);
            relation = itemView.findViewById(R.id.textView27);
            gender = itemView.findViewById(R.id.imageView15);
            age = itemView.findViewById(R.id.textView46);
            delete = itemView.findViewById(R.id.imageView172);


        }
    }
}
