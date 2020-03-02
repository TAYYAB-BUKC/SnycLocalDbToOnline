package com.example.synconlinedatabase;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<User> users = new ArrayList<>();

    public RecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textViewName.setText(users.get(position).getName());
        int syncStatus = users.get(position).getSyncStatus();
        if (syncStatus == DbContract.SYNC_STATUS_OK)
        {
            holder.imageViewSync.setImageResource(R.drawable.okicon);
        }
        else
        {
            holder.imageViewSync.setImageResource(R.drawable.syncicon);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
            ImageView  imageViewSync;
            TextView textViewName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSync = itemView.findViewById(R.id.ImageSync);
            textViewName = itemView.findViewById(R.id.txtName);
        }
    }
}
