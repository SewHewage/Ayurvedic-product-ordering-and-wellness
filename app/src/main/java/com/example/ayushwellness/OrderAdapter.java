package com.example.ayushwellness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayushwellness.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public OrderAdapter(List<Order> orderList) {
    }

    // Interface for click listener (to handle delete functionality)
    public interface OnOrderClickListener {
        void onDeleteOrderClick(String orderId);
    }

    private List<Order> orderList;
    private OnOrderClickListener listener;

    // Constructor
    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Bind order data to the view
        holder.orderIdTextView.setText("Order ID: " + order.getOrderId());
        holder.productNameTextView.setText("Product: " + order.getProductName());
        holder.quantityTextView.setText("Quantity: " + order.getQuantity());
        holder.priceTextView.setText("Price: " + order.getPrice());
        holder.orderDateTextView.setText("Date: " + order.getOrderDate());

        // Set the delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            // Call the listener method when delete button is clicked
            listener.onDeleteOrderClick(order.getOrderId());
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class to hold references to the views
    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, productNameTextView, quantityTextView, priceTextView, orderDateTextView;
        Button deleteButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tv_order_id);
            productNameTextView = itemView.findViewById(R.id.tv_product_name);
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            priceTextView = itemView.findViewById(R.id.tv_price);
            orderDateTextView = itemView.findViewById(R.id.tv_order_date);
            deleteButton = itemView.findViewById(R.id.deleteOrderButton);
        }
    }
}
