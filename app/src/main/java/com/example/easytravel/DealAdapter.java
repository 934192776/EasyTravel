package com.example.easytravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easytravel.Deal;
import com.example.easytravel.R;

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
        holder.bind(deal);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(deal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    static class DealViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDealImage;
        TextView tvTitle, tvCategory, tvPrice, tvLocation, tvRating, tvSupplier;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDealImage = itemView.findViewById(R.id.ivDealImage);
            tvTitle = itemView.findViewById(R.id.tvDealTitle);
            tvCategory = itemView.findViewById(R.id.tvDealCategory);
            tvPrice = itemView.findViewById(R.id.tvDealPrice);
            tvLocation = itemView.findViewById(R.id.tvDealLocation);
            tvRating = itemView.findViewById(R.id.tvDealRating);
            tvSupplier = itemView.findViewById(R.id.tvDealSupplier);
        }

        public void bind(Deal deal) {
            tvTitle.setText(deal.getTitle());
            tvCategory.setText(deal.getCategory());
            tvPrice.setText("$" + deal.getPrice());
            tvLocation.setText("📍 " + deal.getLocation());
            tvRating.setText("⭐ " + deal.getRating());
            tvSupplier.setText("🏪 " + deal.getSupplierName());

            // 用 Glide 加载网络图片
            Glide.with(itemView.getContext())
                    .load(deal.getImageUrl())
                    .placeholder(R.drawable.deal_placeholder)
                    .into(ivDealImage);
        }
    }
}