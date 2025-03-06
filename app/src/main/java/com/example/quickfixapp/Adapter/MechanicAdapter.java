package com.example.quickfixapp.Adapter;

import android.content.Context;
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
    private Context context;

    public MechanicAdapter(List<MechanicModel> mechanicList) {
        this.mechanicList = mechanicList;
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

        holder.Mname.setText(String.format("%s %s %s", firstName, middleName, lastName));
        holder.Mspecialty.setText(mechanic.getmSpecialty() != null ? mechanic.getmSpecialty() : "No specialty listed");
    }

    @Override
    public int getItemCount() {
        return mechanicList != null ? mechanicList.size() : 0;
    }

    public static class MechanicViewHolder extends RecyclerView.ViewHolder {

        TextView Mname, Mspecialty;
        public MechanicViewHolder(View view) {
            super(view);
            Mname = view.findViewById(R.id.Mname);
            Mspecialty = view.findViewById(R.id.Mspecialty);
        }
    }
}
