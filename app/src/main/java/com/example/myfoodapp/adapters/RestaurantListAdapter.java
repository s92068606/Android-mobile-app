package com.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.FoodModel;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    private List<FoodModel> foodModelList;
    private RestaurantListClickListener clickListener;

    public RestaurantListAdapter(List<FoodModel> foodModelList, RestaurantListClickListener clickListener) {
        this.foodModelList = foodModelList;
        this.clickListener = clickListener;
    }

    // Method to update the data in the adapter
    public void updateData(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the recycler_row layout for each item in the RecyclerView
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    // Bind the data to the views in each ViewHolder
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set the food name and visit time
        holder.foodName.setText(foodModelList.get(position).getName());

        holder.time.setText("Visit: " + foodModelList.get(position).getHours().getTodaysHours());

        // Set click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(foodModelList.get(position));
            }
        });
        // Load the image using Glide library
        Glide.with(holder.thumbImage)
                .load(foodModelList.get(position).getImage())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the RecyclerView
        return foodModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  foodName;

        TextView  time;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            foodName = view.findViewById(R.id.restaurantName);

            time = view.findViewById(R.id.restaurantHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface RestaurantListClickListener {
        public void onItemClick(FoodModel foodModel);
    }
}