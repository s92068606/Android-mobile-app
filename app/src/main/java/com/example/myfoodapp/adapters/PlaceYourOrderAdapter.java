package com.example.myfoodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Menu;
import java.util.List;

public class PlaceYourOrderAdapter extends RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder> {

    private List<Menu> menuList;

    public PlaceYourOrderAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    // Method to update the data in the adapter
    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the place_order_recycler_row layout for each item in the RecyclerView
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    // Bind the data to the views in each ViewHolder
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set the food name, price, and quantity
        holder.foodName.setText(menuList.get(position).getName());
        holder.foodPrice.setText("Price: Rs."+String.format("%.2f", menuList.get(position).getPrice()*menuList.get(position).getTotalInCart()));
        holder.foodQty.setText("Qty: " + menuList.get(position).getTotalInCart());

        // Load the image using Glide library
        Glide.with(holder.foodImage)
                .load(menuList.get(position).getUrl())
                .into(holder.foodImage);

    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the RecyclerView
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  foodName;
        TextView  foodPrice;
        TextView  foodQty;
        ImageView foodImage;

        public MyViewHolder(View view) {
            super(view);
            foodName = view.findViewById(R.id.menuName);
            foodPrice = view.findViewById(R.id.menuPrice);
            foodQty = view.findViewById(R.id.menuQty);
            foodImage = view.findViewById(R.id.thumbImage);
        }
    }
}