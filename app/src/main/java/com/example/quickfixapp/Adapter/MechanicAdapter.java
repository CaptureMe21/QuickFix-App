package com.example.quickfixapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickfixapp.R;
import com.example.quickfixapp.model.MechanicModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.MechanicViewHolder> {
    private final List<MechanicModel> mechanicList;
    private Context context;
    DatabaseReference databaseReference;

    public MechanicAdapter(Context context, List<MechanicModel> mechanicList) {
        this.mechanicList = mechanicList;
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");

    }


    @NonNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.mechanic_view, parent, false);
        return new MechanicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MechanicViewHolder holder, int position) {
        MechanicModel mechanic = mechanicList.get(position);

        // Handle potential null values
        String firstName = mechanic.getmFirstName() != null ? mechanic.getmFirstName() : "";
        String middleName = mechanic.getmMiddleName() != null ? mechanic.getmMiddleName() : "";
        String lastName = mechanic.getmLastName() != null ? mechanic.getmLastName() : "";

        holder.Mfname.setText(mechanic.getmFirstName());
        holder.Mlname.setText(mechanic.getmLastName());
        holder.Mmname.setText(mechanic.getmMiddleName());
        holder.Mspecialty.setText(mechanic.getmSpecialty() != null ? mechanic.getmSpecialty() : "No specialty listed");
    }

    @Override
    public int getItemCount() {
        return mechanicList != null ? mechanicList.size() : 0;
    }

    public static class MechanicViewHolder extends RecyclerView.ViewHolder {

        TextView Mfname,Mlname, Mmname,  Mspecialty;
        CardView mechanic_view;
        ConstraintLayout constraintLayout;
        public MechanicViewHolder(View view) {
            super(view);
            Mfname = view.findViewById(R.id.Mfname);
            Mlname = view.findViewById(R.id.Mlname);
            Mmname = view.findViewById(R.id.Mmname);
            Mspecialty = view.findViewById(R.id.Mspecialty);
            mechanic_view = view.findViewById(R.id.mechanic_viewer);
            constraintLayout = view.findViewById(R.id.constraintLayout);
        }
    }
}
