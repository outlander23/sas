package com.example.sas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.EntityViewHolder> {

    private Context context;
    private List<Integer> entityImages;

    public EntityAdapter(Context context, List<Integer> entityImages) {
        this.context = context;
        this.entityImages = entityImages;
    }

    @NonNull
    @Override
    public EntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_entity, parent, false);
        return new EntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityViewHolder holder, int position) {
        int imageResource = entityImages.get(position);
        holder.entityImageView.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return entityImages.size();
    }

    public static class EntityViewHolder extends RecyclerView.ViewHolder {
        ImageView entityImageView;

        public EntityViewHolder(@NonNull View itemView) {
            super(itemView);
            entityImageView = itemView.findViewById(R.id.entity_image_view);
        }
    }
}
