package com.example.ayushwellness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayushwellness.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    // Define interface for click listeners
    public interface OnProductClickListener {
        void onProductImageClick(Product product);  // Handle image click (view product)
        void onAddToCartClick(Product product);     // Handle add to cart click
        void onBuyNowClick(Product product);        // Handle buy now click
        void onDeleteProductClick(Product product); // Handle delete product click
    }

    private List<Product> productList;
    private OnProductClickListener listener;

    // Constructor
    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (productList == null || listener == null) return;

        // Get the current product and bind its data to the UI components
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName() != null ? product.getName() : "Unknown Product");
        holder.priceTextView.setText("Price: " + (product.getPrice() != null ? product.getPrice() : "N/A"));

        if (product.getImageResId() != 0) {
            holder.productImageView.setImageResource(product.getImageResId());
        } else {
            holder.productImageView.setImageResource(R.drawable.placeholder_image); // Default image
        }

        // Set click listener for product image
        if (holder.productImageView != null) {
            holder.productImageView.setOnClickListener(v -> listener.onProductImageClick(product));
        }

        // Set click listeners for action buttons (Add to Cart, Buy Now, Delete)
        if (holder.addToCartButton != null) {
            holder.addToCartButton.setOnClickListener(v -> listener.onAddToCartClick(product));
        }

        if (holder.buyNowButton != null) {
            holder.buyNowButton.setOnClickListener(v -> listener.onBuyNowClick(product));
        }

        if (holder.deleteProductButton != null) {
            holder.deleteProductButton.setOnClickListener(v -> listener.onDeleteProductClick(product));
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;  // Return the total number of products
    }

    // ViewHolder to bind each item view in the RecyclerView
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView productImageView;
        ImageView addToCartButton, buyNowButton, deleteProductButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views from the item_product layout
            try {
                nameTextView = itemView.findViewById(R.id.tv_product_name);
                priceTextView = itemView.findViewById(R.id.tv_product_price);
                productImageView = itemView.findViewById(R.id.iv_product_image);
                addToCartButton = itemView.findViewById(R.id.iv_add_to_cart);
                buyNowButton = itemView.findViewById(R.id.iv_buy_now);
                deleteProductButton = itemView.findViewById(R.id.iv_delete_product);
            } catch (Exception e) {
                e.printStackTrace();
                // Log error for debugging
            }
        }
    }
}
