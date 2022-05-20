package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapterEvents extends RecyclerView.Adapter<MyAdapterEvents.MyViewHolder> {
    Context context;
    ArrayList<Evenements> eventArrayList;
    FirebaseFirestore db;

    public MyAdapterEvents(Context context, ArrayList<Evenements> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyAdapterEvents.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterEvents.MyViewHolder holder, int position) {
        Evenements evenements = eventArrayList.get(position);

        holder.titre.setText(evenements.titre);
        holder.date.setText(evenements.date);
        holder.localisation.setText(evenements.localisation);
        holder.groupeCible.setText(evenements.groupeCible);
        holder.host.setText(evenements.host);
        holder.dateLimite.setText(evenements.dateLimite);
        holder.description.setText(evenements.description);

        // Clique sur le lien de description d'un evenement
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(evenements.getDescription()));
                context.startActivity(browserIntent);
            }
        });

        // Clique sur le bouton d'inscription à un evenement
        holder.boutonSinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Définir l'action à réaliser ici
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre, date, localisation, groupeCible, host, dateLimite, description;
        Button boutonSinscrire;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.tvTitreEvent);
            date = itemView.findViewById(R.id.tvDateEvent);
            localisation = itemView.findViewById(R.id.tvLocalisationEvent);
            groupeCible = itemView.findViewById(R.id.tvGroupeCibleEvent);
            host = itemView.findViewById(R.id.tvHostEvent);
            dateLimite = itemView.findViewById(R.id.tvDateLimiteEvent);
            description = itemView.findViewById(R.id.tvDesctiptionEvent);
            boutonSinscrire = itemView.findViewById(R.id.boutonSinscrire);
        }
    }
}