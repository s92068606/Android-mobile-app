package com.example.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Menu;
import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener) {
        this.menuList = menuList;
        this.clickListener = clickListener;
    }

    // Method to update the data in the adapter
    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the menu_recycler_row layout for each item in the RecyclerView
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Bind the data to the views in each ViewHolder
        // Set the menu name and price
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: Rs."+menuList.get(position).getPrice());
        // Set a click listener for the "Add to Cart" button
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked menu item
                Menu menu  = menuList.get(position);
                menu.setTotalInCart(1);
                // Call the click listener's onAddToCartClick method
                clickListener.onAddToCartClick(menu);
                // Update the visibility of the layout and button
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                // Update the count text view
                holder.tvCount.setText(menu.getTotalInCart()+"");
            }
        });
        // Set a click listener for the minus icon
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked menu item
                Menu menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;
                if(total > 0 ) {
                    // Update the total in cart and call the click listener's onUpdateCartClick method
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    // Update the count text view
                    holder.tvCount.setText(total +"");
                } else {
                    // If the total becomes 0, hide the layout and show the "Add to Cart" button
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    // Update the total in cart and call the click listener's onRemoveFromCartClick method
                    menu.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });

        // Set a click listener for the plus icon
        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked menu item
                Menu menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if(total <= 10 ) {
                    // Update the total in cart and call the click listener's onUpdateCartClick method
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    // Update the count text view
                    holder.tvCount.setText(total +"");
                }
            }
        });

        // Load the image using Glide library
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the RecyclerView
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  menuName;
        TextView  menuPrice;
        TextView  addToCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView  tvCount;
        LinearLayout addMoreLayout;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);

            addMoreLayout  = view.findViewById(R.id.addMoreLayout);
        }
    }

    public interface MenuListClickListener {
        public void onAddToCartClick(Menu menu);
        public void onUpdateCartClick(Menu menu);
        public void onRemoveFromCartClick(Menu menu);
    }
}