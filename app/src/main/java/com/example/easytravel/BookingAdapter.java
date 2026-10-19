package com.example.easytravel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 注意这里：必须继承 RecyclerView.Adapter，不能是 AppCompatActivity
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
        notifyDataSetChanged(); // 必须加这一行，否则列表不会刷新
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
            // 确保你的 item_booking.xml 里面有这些 ID
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
                tvCancel.setVisibility(View.GONE);  // 已确认的不能取消
            } else if ("PENDING".equals(booking.getStatus())) {
                tvStatus.setTextColor(0xFFFF9800);  // 橙色
                tvCancel.setVisibility(View.VISIBLE); // 只有待定状态能取消
            } else if ("CANCELLED".equals(booking.getStatus())) {
                tvStatus.setTextColor(0xFFF44336);  // 红色
                tvCancel.setVisibility(View.GONE);
            } else {
                tvStatus.setTextColor(0xFF666666);  // 灰色
                tvCancel.setVisibility(View.GONE);
            }
        }
    }
}