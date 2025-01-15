package com.example.myfoodapp.activities.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.admin.db.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private Context context; // The context object used for inflating layouts and accessing resources.
    private List<Category> categoryList; // The list of Category objects to be displayed in the RecyclerView.
    private HandleCategoryClick clickListener;  // The listener interface to handle category item clicks, removal, and editing.

    // Constructor for the CategoryListAdapter
    public CategoryListAdapter(Context context, HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    // Method to set the category list and notify the adapter of the data change
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    // Create the ViewHolder by inflating the item layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    // Bind data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCategoryName.setText(this.categoryList.get(position).categoryName);

        // Set an OnClickListener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(categoryList.get(position));
            }
        });

        // Set an OnClickListener for the edit category button
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(categoryList.get(position));
            }
        });

        // Set an OnClickListener for the remove category button
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(categoryList.get(position));
            }
        });
    }

    // Return the number of items in the category list
    @Override
    public int getItemCount() {
        if(categoryList == null || categoryList.size() == 0)
            return 0;
        else
            return categoryList.size();
    }

    // ViewHolder class for holding the views of each item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView removeCategory;
        ImageView editCategory;

        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            removeCategory = view.findViewById(R.id.removeCategory);
            editCategory = view.findViewById(R.id.editCategory);

        }
    }

    // Interface to handle category item clicks, removal, and editing
    public interface  HandleCategoryClick {
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);
    }
}