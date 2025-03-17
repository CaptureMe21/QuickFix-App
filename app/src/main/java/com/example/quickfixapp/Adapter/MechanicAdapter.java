package com.example.quickfixapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.quickfixapp.shopOwner.MechanicDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.MechanicViewHolder> {

    private final List<MechanicModel> mechanicList;
    private final Context context;
DatabaseReference databaseReference;

    public MechanicAdapter(Context context, List<MechanicModel> mechanicList) {
        this.context = context;
        this.mechanicList = mechanicList;
        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");
    }

    @NonNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use parent.getContext() to avoid passing incorrect context
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanic_view, parent, false);
        return new MechanicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MechanicViewHolder holder, int position) {
        MechanicModel mechanic = mechanicList.get(position);

        // Prevent NullPointerException by setting default values
        holder.Mname.setText(
                (mechanic.getmFirstName() != null ? mechanic.getmFirstName() : "") + " " +
                        (mechanic.getmMiddleName() != null && !mechanic.getmMiddleName().isEmpty() ? mechanic.getmMiddleName() + " " : "") +
                        (mechanic.getmLastName() != null ? mechanic.getmLastName() : "")
        );
        holder.Mspecialty.setText(mechanic.getmSpecialty() != null ? mechanic.getmSpecialty() : "No specialty listed");

        // Add a click listener (if needed)
        holder.mechanic_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MechanicDetails.class);
                intent.putExtra("mechanic", mechanic);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mechanicList != null) ? mechanicList.size() : 0;
    }

    public static class MechanicViewHolder extends RecyclerView.ViewHolder {

        TextView Mname, Mspecialty;
        CardView mechanic_view;
        ConstraintLayout constraintLayout;

        public MechanicViewHolder(View view) {
            super(view);
            Mname = view.findViewById(R.id.Mname);
            Mspecialty = view.findViewById(R.id.Mspecialty);
            mechanic_view = view.findViewById(R.id.mechanic_viewer);
            constraintLayout = view.findViewById(R.id.constraintLayout);
        }
    }
}
