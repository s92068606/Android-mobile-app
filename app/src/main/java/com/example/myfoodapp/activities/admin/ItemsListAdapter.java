package com.example.myfoodapp.activities.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.admin.db.Items;

import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyViewHolder> {

    private Context context;
    private List<Items> itemsList;
    private HandleItemsClick clickListener;

    // Constructor
    public ItemsListAdapter(Context context, HandleItemsClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    // Set the items list
    public void setCategoryList(List<Items> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    // Create and return a ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    // Bind data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set the item name to the corresponding TextView in the ViewHolder
        holder.tvItemName.setText(this.itemsList.get(position).itemName);

        // Set click listeners for the item view, edit category button, and remove category button
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the itemClick method of the clickListener with the clicked item as the parameter
                clickListener.itemClick(itemsList.get(position));
            }
        });

        // Set an OnClickListener for the edit button in the ViewHolder
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the editItem method of the clickListener with the clicked item as the parameter
                clickListener.editItem(itemsList.get(position));
            }
        });

        // Set an OnClickListener for the remove button in the ViewHolder
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the removeItem method of the clickListener with the clicked item as the parameter
                clickListener.removeItem(itemsList.get(position));
            }
        });

        // Check if the item is completed
        if(this.itemsList.get(position).completed) {
            // If completed, set the strike-through text style to the item name
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {  // If not completed, remove the strike-through text style from the item name
            holder.tvItemName.setPaintFlags(0);
        }
    }

    // Return the number of items in the list
    @Override
    public int getItemCount() {
        if(itemsList == null || itemsList.size() == 0)
            return 0;
        else
            return itemsList.size();
    }

    // ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        ImageView removeCategory;
        ImageView editCategory;

        public MyViewHolder(View view) {
            super(view);
            tvItemName = view.findViewById(R.id.tvCategoryName);
            removeCategory = view.findViewById(R.id.removeCategory);
            editCategory = view.findViewById(R.id.editCategory);

        }
    }

    // Interface for handling item clicks
    public interface  HandleItemsClick {
        void itemClick(Items item);
        void removeItem(Items item);
        void editItem(Items item);
    }
}