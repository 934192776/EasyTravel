package com.example.easytravel.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easytravel.DealDetailActivity;
import com.example.easytravel.R;
import com.example.easytravel.model.Deal;
import java.util.ArrayList;
import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {

    private List<Deal> deals = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Deal deal);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deal, parent, false);
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Deal deal = deals.get(position);
        holder.tvTitle.setText(deal.getTitle());
        holder.tvDescription.setText(deal.getDescription());
        holder.tvPrice.setText("$" + deal.getPrice());
        holder.tvCategory.setText(deal.getCategory());
        holder.tvLocation.setText(deal.getLocation());

        // 如果 imageUrl 有值，可以用 Glide 加载图片
        // 目前先用默认图标
        if (deal.getImageUrl() != null && !deal.getImageUrl().isEmpty()) {
            // 后续用 Glide: Glide.with(holder.itemView.getContext()).load(deal.getImageUrl()).into(holder.ivImage);
        } else {
            holder.ivImage.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // 点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(deal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public static class DealViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvPrice, tvCategory, tvLocation;
        ImageView ivImage;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvDealTitle);
            tvDescription = itemView.findViewById(R.id.tvDealDesc);
            tvPrice = itemView.findViewById(R.id.tvDealPrice);
            tvCategory = itemView.findViewById(R.id.tvDealCategory);
            tvLocation = itemView.findViewById(R.id.tvDealLocation);
            ivImage = itemView.findViewById(R.id.ivDealImage);
        }
    }
}