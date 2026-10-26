package com.example.easytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookings = new ArrayList<>();
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onCancelClick(Booking booking);
    }

    public void setOnBookingClickListener(OnBookingClickListener listener) {
        this.listener = listener;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bind(booking);

        holder.tvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelClick(booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvPeople, tvTotal, tvStatus, tvCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBookingTitle);
            tvDate = itemView.findViewById(R.id.tvBookingDate);
            tvPeople = itemView.findViewById(R.id.tvBookingPeople);
            tvTotal = itemView.findViewById(R.id.tvBookingTotal);
            tvStatus = itemView.findViewById(R.id.tvBookingStatus);
            tvCancel = itemView.findViewById(R.id.tvCancelBooking);
        }

        public void bind(Booking booking) {
            tvTitle.setText(booking.getDealTitle());
            tvDate.setText("📅 " + booking.getDate());
            tvPeople.setText("👥 " + booking.getNumberOfPeople() + " people");
            tvTotal.setText("💰 $" + booking.getTotalPrice());
            tvStatus.setText("📌 " + booking.getStatus());

            // 根据状态显示不同颜色
            if ("CONFIRMED".equals(booking.getStatus())) {
                tvStatus.setTextColor(0xFF4CAF50);  // 绿色
            } else if ("PENDING".equals(booking.getStatus())) {
                tvStatus.setTextColor(0xFFFF9800);  // 橙色
            } else if ("CANCELLED".equals(booking.getStatus())) {
                tvStatus.setTextColor(0xFFF44336);  // 红色
            } else {
                tvStatus.setTextColor(0xFF666666);  // 灰色
            }

            // 只有 PENDING 状态才能取消
            if ("PENDING".equals(booking.getStatus())) {
                tvCancel.setVisibility(View.VISIBLE);
            } else {
                tvCancel.setVisibility(View.GONE);
            }
        }
    }
}