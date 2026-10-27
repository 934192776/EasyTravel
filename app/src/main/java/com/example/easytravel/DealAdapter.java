package com.example.easytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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

            // 地址判空
            String loc = deal.getLocation() != null ? deal.getLocation() : "Auckland";
            tvLocation.setText("📍 " + loc);

            // 评分判空（大于0显示真实评分，否则显示 4.5）
            double rating = deal.getRating();
            tvRating.setText("⭐ " + (rating > 0 ? rating : "4.5"));

            // 👈 直接调用智能方法，不需要再判断 null 了
            tvSupplier.setText("🏪 " + deal.getSupplierName());

            // 加载图片
            Glide.with(itemView.getContext())
                    .load(deal.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivDealImage);
        }
    }
}