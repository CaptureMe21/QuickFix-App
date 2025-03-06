package com.example.quickfixapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickfixapp.R;
import com.example.quickfixapp.model.MechanicModel;

import java.util.List;

public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.MechanicViewHolder> {
    private final List<MechanicModel> mechanicList;

    public MechanicAdapter(List<MechanicModel> mechanicList) {
        this.mechanicList = mechanicList;
    }


    @NonNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanic_view, parent, false);
        return new MechanicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MechanicAdapter.MechanicViewHolder holder, int position) {
        MechanicModel mechanic = mechanicList.get(position);
        holder.Mname.setText(mechanic.getmFirstName() + " " + mechanic.getmMiddleName() + " " + mechanic.getmLastName());
        holder.Mspecialty.setText("Specialty: " + mechanic.getmSpecialty());
    }

    @Override
    public int getItemCount() {
        return mechanicList.size();
    }

    public class MechanicViewHolder extends RecyclerView.ViewHolder {

        TextView Mname, Mspecialty;
        public MechanicViewHolder(View view) {
            super(view);
            Mname = view.findViewById(R.id.Mname);
            Mspecialty = view.findViewById(R.id.Mspecialty);
        }
    }
}
