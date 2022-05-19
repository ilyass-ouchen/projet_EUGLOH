package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Evenements> eventArrayList;

    public MyAdapter(Context context, ArrayList<Evenements> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);



        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Evenements evenements = eventArrayList.get(position);

        holder.titre.setText(evenements.titre);
        holder.date.setText(evenements.date);
        holder.localisation.setText(evenements.localisation);
        holder.groupeCible.setText(evenements.groupeCible);
        holder.host.setText(evenements.host);
        holder.dateLimite.setText(evenements.dateLimite);
        holder.description.setText(evenements.description);
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre, date, localisation, groupeCible, host, dateLimite, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.tvTitreEvent);
            date = itemView.findViewById(R.id.tvDateEvent);
            localisation = itemView.findViewById(R.id.tvLocalisationEvent);
            groupeCible = itemView.findViewById(R.id.tvGroupeCibleEvent);
            host = itemView.findViewById(R.id.tvHostEvent);
            dateLimite = itemView.findViewById(R.id.tvDateLimiteEvent);
            description = itemView.findViewById(R.id.tvDesctiptionEvent);
        }
    }
}